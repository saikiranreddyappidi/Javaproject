package Networking;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientServer {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(8888);
            byte[] datagramBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(datagramBuffer, datagramBuffer.length);
            datagramSocket.receive(packet);
            String broadcastMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + broadcastMessage);
            if (broadcastMessage.equals("SERVER_IP_BROADCAST")) {
                String serverIP = packet.getAddress().getHostAddress();
                System.out.println("Server IP is: " + serverIP);

                Scanner scanner = new Scanner(System.in);

                while (true) {
                    Socket socket = new Socket(serverIP, 900);

                    try (socket; DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
                        System.out.println("Enter the type of data you want to send (text/file):");
                        String dataType = scanner.nextLine();
                        dataOutputStream.writeUTF(dataType);

                        switch (dataType) {
                            case "text" -> {
                                System.out.println("Enter the text message:");
                                String message = scanner.nextLine();
                                dataOutputStream.writeUTF(message);
                                double transmissionDelayText = calculateTransmissionDelay(message.getBytes().length, 300);
                                System.out.println("Transmission Delay for text: " + transmissionDelayText + " seconds");
                            }
                            case "file" -> {
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
                                Thread.sleep(1);
                                double speed = 0;
                                while ((bytes = fileInputStream.read(buffer)) != -1) {
                                    dataOutputStream.write(buffer, 0, bytes);
                                    totalBytesSent += bytes;
                                    long currentTime = System.currentTimeMillis();
                                    speed = (double) totalBytesSent / ((currentTime - startTime) / 1000.0);
                                    System.out.print("-");
                                    double transmissionDelayChunk = calculateTransmissionDelay(bytes, 300);
                                    System.out.println("Transmission Delay for this chunk: " + transmissionDelayChunk + " seconds");
                                }
                                long endTime = System.currentTimeMillis();
                                double transmissionRate = (double) totalBytesSent / ((endTime - startTime) / 1000.0);
                                System.out.println("\nFile sent at speed: " + speed + " bytes/s");
                                System.out.println("Transmission rate: " + transmissionRate + " bytes/s");
                                double transmissionDelayFile = calculateTransmissionDelay(fileSize, 300);
                                System.out.println("Transmission Delay for file: " + transmissionDelayFile + "ms");
                                fileInputStream.close();
                            }
                            default -> System.out.println("Unknown data type. Please enter 'text' or 'file'.");
                        }

                    } catch (IOException e) {
                        System.out.println("Error sending data. Attempting to reconnect...");
                        e.fillInStackTrace();
                    }
                }
            }
            datagramSocket.close();
        } catch (Exception e) {
            System.out.println("Error connecting to server");
            e.fillInStackTrace();
        }
    }

    public static double calculateTransmissionDelay(double packetSizeBytes, double linkSpeedMbps) {
        double linkSpeedBps = linkSpeedMbps * 1_000_000;
        double packetizationDelaySeconds = packetSizeBytes * 8 / linkSpeedBps;
        double serializationDelaySeconds = packetSizeBytes * 8 / linkSpeedBps;
        return packetizationDelaySeconds + serializationDelaySeconds;
    }
}