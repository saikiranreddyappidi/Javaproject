package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static DataInputStream dataInputStream = null;

    public static void main(String[] args)
    {
        // Here we define Server Socket running on port 900
        try (ServerSocket serverSocket
                     = new ServerSocket(900)) {
            System.out.println(
                    "Server is Starting in Port 900");
            // Accept the Client request using accept method
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");
            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(
                    clientSocket.getOutputStream());
            // Here we call receiveFile define new for that
            // file
            receiveFile("test.pdf");

            dataInputStream.close();
            dataOutputStream.close();
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // receive file function is start here

    // Server.java
    private static void receiveFile(String fileName)
            throws Exception
    {
        int bytes = 0;
        long totalBytesReceived = 0;
        FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);

        long size
                = dataInputStream.readLong(); // read file size

        // Print the size of the file
        System.out.println("File size: " + size + " bytes");

        byte[] buffer = new byte[4 * 1024];
        long startTime = System.currentTimeMillis();

        // Print the starting time
        System.out.println("Start time: " + startTime);

        while (size > 0
                && (bytes = dataInputStream.read(
                buffer, 0,
                (int)Math.min(buffer.length, size)))
                != -1) {
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
        System.out.println("--------------File is received--------------");
        fileOutputStream.close();
    }
}