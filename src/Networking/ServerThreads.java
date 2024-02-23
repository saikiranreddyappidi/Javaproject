package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

public class ServerThreads {

    public static void main(String[] args)
    {
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                String message = "SERVER_IP_BROADCAST";
                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName("255.255.255.255"), 8888);
                while (true) {
                    socket.send(packet);
//                    System.out.println("Broadcast message sent"); // print statement
                    try {
                        Thread.sleep(5000); // sleep for 5 seconds before sending the next broadcast
                    } catch (InterruptedException e) {
                        e.fillInStackTrace();
                    }
                }
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }).start();

        // Here we define Server Socket running on port 900
        try (ServerSocket serverSocket = new ServerSocket(900)) {
            System.out.println("Server is Starting in Port 900");
            while (true) {
                // Accept the Client request using accept method
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");

                // Create a new thread for each client
                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }).start();
            }
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            String dataType = dataInputStream.readUTF(); // read data type from client

            switch (dataType) {
                case "text" -> {
                    String message = dataInputStream.readUTF(); // read message from client
                    System.out.println("Received message: " + message + " from " + clientSocket.getRemoteSocketAddress());
                }
                case "file" -> {
//                    save file name with ip address like ''192.168.0.105_received_file.pdf''
                    String fileName = clientSocket.getRemoteSocketAddress().toString().replace("/", "").replace(":", "_") + "_received_file.pdf";
                    receiveFile(fileName, dataInputStream); // read file from client
                }
                // Add more cases here to handle other types of data
                default ->
                        System.out.println("Unknown data type received from " + clientSocket.getRemoteSocketAddress());
            }

            dataInputStream.close();
            dataOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error handling client " + clientSocket.getRemoteSocketAddress());
            e.fillInStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // receive file function is start here
    private static void receiveFile(String fileName, DataInputStream dataInputStream)
            throws Exception
    {
        int bytes = 0;
        long totalBytesReceived = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong(); // read file size

        // Print the size of the file
        System.out.println("File size: " + size + " bytes");

        byte[] buffer = new byte[4 * 1024];
        long startTime = System.currentTimeMillis();

        // Print the starting time
        System.out.println("Start time: " + startTime);

        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
            totalBytesReceived += bytes;
        }

        long endTime = System.currentTimeMillis();

        // Print the ending time
        System.out.println("End time: " + endTime);

        // Here we received file
        System.out.println("File is Received");

        long delay = endTime - startTime;
        long loss = size; // Corrected here
        double throughput = (double) totalBytesReceived / delay;

        System.out.println("Delay: " + delay + " ms");
        System.out.println("Loss: " + loss + " bytes");
        System.out.println("Throughput: " + throughput + " bytes/ms");

        fileOutputStream.close();
    }
}