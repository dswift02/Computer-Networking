/**
 * Created by darrianswift on 12/03/2017.
 */
import java.io.* ;
import java.net.* ;


public class Client {
    public static void main(String[] args) throws Exception {
        //declare variables
        int port = 4001 ;
        String s ;


        try {
            DatagramSocket socket = new DatagramSocket();
            BufferedReader scan = new BufferedReader( new InputStreamReader(System.in)) ;
            InetAddress address = InetAddress.getByName("localhost") ;

            while(true) {
                //  client prompts for a string s from the user
                System.out.println("Enter Message: ");
                s = (String) scan.readLine();
                s = s.replaceAll("\\s+$","") ; //remove trailing space(if there is one)
                //byte[] buff = s.getBytes();


                //split the string s into s1 and s2
                final int mid ;
                if(s.length() % 2 == 0 ) { //even
                    mid = s.length()/2 ;
                } else { //odd
                    mid = ((s.length()+1)/2) ;
                }
                String s1 = s.substring(0,mid) ;
                String s2 = s.substring(mid) ;
                System.out.println("Client: String S has been split into Strings s1 and s2, '"+ s1 + "' and '" + s2 + "' respectively.\n");


                //send both s1 and s2 as UDP packets
                byte[] buff1 = s1.getBytes() ;
                byte[] buff2 = s2.getBytes() ;
                DatagramPacket p1 = new DatagramPacket(buff1, 0,  buff1.length, address, port);
                socket.send(p1);
                DatagramPacket p2 = new DatagramPacket(buff2, 0,  buff2.length, address, port);
                socket.send(p2);
                System.out.println("Client: Strings s1 and s2 has been sent to the Server.\n") ;
                //wait for string r from server
                System.out.println("Client: Waiting for a response from the Server.\n");

                //receive inverted response
                byte[] receiveBuff = new byte[1024];
                DatagramPacket response = new DatagramPacket(receiveBuff, receiveBuff.length);
                socket.receive(response);
                String r = new String(response.getData(),0,response.getLength()) ;
                System.out.println("Client: A UDP packet has been received from the Server. \n");
                //check if r is equal to s inverted
                int flag = 0 ;
                int size = s.length()   ;
                for(int i = 0 ; i < size ; i ++) {
                    if(s.charAt(i) == r.charAt(size - 1 - i)) {
                        flag = 1 ;
                    } else {
                        flag = 0 ;
                        break ; //not equal, exit
                    }
                }

                //display result
                if (flag == 1) { //if true
                    System.out.println("Server: " + r + "\n") ;
                } else {
                    System.out.println("" + r + "\n") ;
                }
            }

        } catch (IOException ioe) {
            System.out.println(ioe) ;
        }
    }
}
