import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Scanner sc= new Scanner(System.in);
    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;
    private BufferedReader br;

        public Client(){

        }

        public void Send(String ch) throws IOException {
            System.out.println("CLIENT: "+ch);
            dout.writeUTF(ch);
            dout.flush();
        }

        public String receive() throws IOException {
            String str2=din.readUTF();
            System.out.println("SERVER: "+str2);
            return str2;
        }

        public void close() throws IOException {

            dout.close();
            din.close();
            s.close();

        }

        public void connecti(String ch) throws IOException {

            System.out.println("Connecting...");
            s=new Socket(ch,3333);
            System.out.println("connected");
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());

        }


}

