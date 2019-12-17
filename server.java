//Created by darrianswift on 12/03/2017.
import java.io.* ;
import java.net.* ;

public class Server {
    public static void main(String[] argv) throws Exception {
        int PORT = 4001;
        String r ;
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Server socket created.") ;
            while (true) {
                //Wait for incoming UDP packets
                System.out.println("Waiting for incoming data... \n");

                //Buffer to receive incoming data
                byte[] buff1 = new byte[1024];
                byte[] buff2 = new byte[1024];
                DatagramPacket p1 = new DatagramPacket(buff1, buff1.length);
                socket.receive(p1);
                DatagramPacket p2 = new DatagramPacket(buff2, buff2.length);
                socket.receive(p2);

                //extract data, ip and port
                int port = p1.getPort();
                InetAddress address = p1.getAddress();

                String s1 = new String(p1.getData(), 0, p1.getLength());
                String s2 = new String(p2.getData(), 0, p2.getLength());
                System.out.println("Client" + address + ": " + s1 + "-" + s2 + "\n");

                //checks to see if two packets has been received
                if(s1.length() == 0 || s2.length() == 0) {
                    System.out.println("Server: Two packets have not been received from the client. \n") ;
                    r = "Two packets have not been received.\n" ; //sends to client
                }else {
                    // join s1 and s2
                    r = s1 + s2;
                    System.out.println("Server: Two packets have been received from the client.\n") ;
                    //reverse string r using reverseString function
                    r = reverseString(r.trim());
                    System.out.println("Server: String R has been reversed: " + r + ".\n ");
                }

                //send  UDP packet to client
                byte[] sendBuff = r.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuff, 0, sendBuff.length, address, port);
                socket.send(sendPacket);
                System.out.println("Server: A UDP packet containing R has been sent to the client. \n") ;
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private static String reverseString(String in) {
        StringBuilder revBuff = new StringBuilder(in) ;
        return revBuff.reverse().toString() ;

    }
}
