package Networking;

import java.io.*;
import java.net.Socket;

public class Client {
    private static DataOutputStream dataOutputStream = null;

    public static void main(String[] args)
    {
        // Create Client Socket connect to port 900
        try (Socket socket = new Socket("192.168.0.105", 900)) {

            DataInputStream dataInputStream = new DataInputStream(
                    socket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            System.out.println(
                    "Sending the File to the Server");
            // Call SendFile Method
            sendFile(
                    "C:\\Users\\saiki\\OneDrive\\Documents\\IDP\\Automatic_Text_Summarization_and_Keyword_Extraction_using_Natural_Language_Processing.pdf");

            dataInputStream.close();
            dataInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // sendFile function define here
   // Client.java
    private static void sendFile(String path)
            throws Exception
    {
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);

        // Print the size of the file
        System.out.println("File size: " + file.length() + " bytes");

        // Print the starting time
        long startTime = System.currentTimeMillis();
        System.out.println("Start time: " + startTime);

        // Here we send the File to Server
        dataOutputStream.writeLong(file.length());
        // Here we break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            // Send the file to Server Socket
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        // close the file here
        fileInputStream.close();

        // Print the ending time
        long endTime = System.currentTimeMillis();
        System.out.println("End time: " + endTime);
    }
}

