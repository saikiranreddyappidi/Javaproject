package Networking;

public class TransmissionDelay {
    // Assuming packet size in bytes and link speed in Mbps
    public static double calculateTransmissionDelay(double packetSizeBytes, double linkSpeedMbps) {
        // Convert link speed to bits per second
        double linkSpeedBps = linkSpeedMbps * 1_000_000;

        // Packetization Delay: Calculate the time to packetize the data
        double packetizationDelaySeconds = packetSizeBytes * 8 / linkSpeedBps;

        // Serialization Delay: Calculate the time to transmit each packet over the link
        double serializationDelaySeconds = packetSizeBytes * 8 / linkSpeedBps;

        // Aggregate Delay: Sum of Packetization Delay and Serialization Delay
        double transmissionDelaySeconds = packetizationDelaySeconds + serializationDelaySeconds;

        return transmissionDelaySeconds;
    }

    public static void main(String[] args) {
        // Example usage
        double packetSizeBytes = 1500; // Typical packet size in bytes
        double linkSpeedMbps = 300; // Aggregated link speed in Mbps

        double transmissionDelaySeconds = calculateTransmissionDelay(packetSizeBytes, linkSpeedMbps);
        System.out.println("Transmission Delay: " + transmissionDelaySeconds + " seconds");
    }
}
