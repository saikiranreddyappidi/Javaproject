package Networking;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientServer {

    public static void main(String[] args) {
        try {
            // Listen for the server's IP broadcast
            DatagramSocket datagramSocket = new DatagramSocket(8888);
            byte[] datagramBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(datagramBuffer, datagramBuffer.length);
            datagramSocket.receive(packet);
            String broadcastMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + broadcastMessage); // print statement
            if (broadcastMessage.equals("SERVER_IP_BROADCAST")) {
                String serverIP = packet.getAddress().getHostAddress();
                System.out.println("Server IP is: " + serverIP);

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    // Connect to the server
                    Socket socket = new Socket(serverIP, 900);
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    try {
                        System.out.println("Enter the type of data you want to send (text/file):");
                        String dataType = scanner.nextLine();
                        dataOutputStream.writeUTF(dataType);

                        switch (dataType) {
                            case "text":
                                System.out.println("Enter the text message:");
                                String message = scanner.nextLine();
                                dataOutputStream.writeUTF(message);
                                break;
                            case "file":
                                System.out.println("Enter the file path:");
                                String filePath = scanner.nextLine();
                                File file = new File(filePath);
                                long fileSize = file.length();
                                dataOutputStream.writeLong(fileSize);
                                FileInputStream fileInputStream = new FileInputStream(file);
                                byte[] buffer = new byte[4 * 1024];
                                int bytes;
                                long totalBytesSent = 0;
                                long startTime = System.currentTimeMillis();
                                Thread.sleep(1); // Add a small delay to avoid division by zero
                                double speed = 0;
                                while ((bytes = fileInputStream.read(buffer)) != -1) {
                                    dataOutputStream.write(buffer, 0, bytes);
                                    totalBytesSent += bytes;
                                    long currentTime = System.currentTimeMillis();
                                    speed = (double) totalBytesSent / ((currentTime - startTime) / 1000.0);
                                    System.out.print("-"); // Print a hyphen for each chunk of data sent
                                }
                                System.out.println("\nFile sent at speed: " + speed + " bytes/s");
                                fileInputStream.close();
                                break;
                            default:
                                System.out.println("Unknown data type. Please enter 'text' or 'file'.");
                                break;
                        }

                    } catch (IOException e) {
                        System.out.println("Error sending data. Attempting to reconnect...");
                        e.printStackTrace();
                    } finally {
                        dataOutputStream.close();
                        socket.close();
                    }
                }
            }
            datagramSocket.close();
        } catch (Exception e) {
            System.out.println("Error connecting to server");
            e.printStackTrace();
        }
    }
}