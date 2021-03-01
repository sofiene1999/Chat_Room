import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

public class ClientHandler extends Thread {

    Socket socket;
    BoxFrame boxFrame;
    DataOutputStream dos;
    DataInputStream dis;
    boolean v=false;
    String str2;

    public ClientHandler(Socket s, BoxFrame boxFrame) {

        this.socket=s;
        this.boxFrame=boxFrame;

    }

    public void dialogue() throws IOException {

         dos= new DataOutputStream(socket.getOutputStream());
         dis= new DataInputStream(socket.getInputStream());

        String ch= null;
        try {
            ch = dis.readUTF();
        } catch (IOException e) {
            System.err.println("input output problems..");
        }
        sendnames();

        sendmsg("*");
        sendmsg(ch);

        BoxFrame.clientList.put(ch,this);


        while (true) {

                str2 = dis.readUTF();
                System.out.println(str2);


            String ch2="";
            String ch3="";
            String ch1="";
            if (!(str2.equals(" "))){
                ch1= getword1(str2);
                if (ch1.equals("*")) {
                    ch2 = getword1(getword2(str2));
                    ch3 = getword2(getword2(str2));
                }}
                if (!(str2.equals(""))) {
                    if (str2.equals(" ")){

                        String sh=dis.readUTF();


                        System.out.println("client is gonna be deleted");
                        deleteclient(sh);


                    }else if (ch1.equals("*")){

                        for (Map.Entry<String,ClientHandler> client : BoxFrame.clientList.entrySet()) {

                            if (client.getKey().equals(ch2)){
                                client.getValue().send(getclient(this)+": "+ch3);
                            }

                        }
                        send(getclient(this)+": "+ch3);
                    }
                    else{
                            System.out.println("hello here");
                            sendToAll(str2);

                    }
                }


        }
    }



    public void send(String ch) throws IOException {

        System.out.println("send is working");
        dos.writeUTF(ch);
        dos.flush();


    }

    public void sendToAll(String ch) throws IOException {

        String sh= getclient(this);

        for (Map.Entry<String,ClientHandler> client : BoxFrame.clientList.entrySet()){

            if (client.getValue()!=null)
            client.getValue().send(sh+": "+ch);

        }
    }

    public void deleteclient(String ch) throws IOException {


                sendmsg("d "+ch);

        for (Map.Entry<String,ClientHandler> client : BoxFrame.clientList.entrySet()){

            if (client.getKey().equals(ch))
                client.setValue(null);

        }



    }

    public void sendmsg(String ch) {

        for (Map.Entry<String,ClientHandler> client : BoxFrame.clientList.entrySet()){

            if (((client.getValue()!=null))&&(!((client.getKey()).equals(getword2(ch))))) {
                try {
                    client.getValue().send(ch);
                    System.out.println("send deleted "+ch+" to "+client.getKey());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void sendnames() throws IOException {

        String ch="";

        for (Map.Entry<String, ClientHandler> client : BoxFrame.clientList.entrySet()) {

            if (client.getValue()!=null){

                ch= client.getKey();
                send("*");
                send(ch);
            }
        }
    }




    private String getclient(ClientHandler clientHandler) {

        String ch="";

        for (Map.Entry<String,ClientHandler> client : BoxFrame.clientList.entrySet()){

            if (client.getValue()==clientHandler)
                ch=client.getKey();
        }
        return ch;
    }

    public String getword1(String ch){
        int i=0;
        String r="";
        while (ch.charAt(i)!=' '){
            r+=ch.charAt(i);
            i++;
        }
        return r;

    }


    public String getword2(String ch){

        String r="";
        for(int i=0;i<ch.length();i++){

            if(ch.charAt(i)==' '){
                for (int j=i+1;j<ch.length();j++){

                    r+=ch.charAt(j);

                }
                return r;
            }
        }
        return "";
    }


    public String cleanword(String ch){

        String sh="";

        for (int i=0;i<((ch.length())-2);i++){

            sh+=ch.charAt(i);

        }

        return sh;

    }

    public void run(){

        try {
            dialogue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
