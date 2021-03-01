import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.util.Timer;
import java.util.TimerTask;

public class Frame extends Application {


    Stage window;
    Button login= new Button("Login");
    PasswordField pw= new PasswordField();
    Label chatroom= new Label("CHAT ROOM");
    Scene scene_login;
    Label lname= new Label("NAME             ");
    Label lpw= new Label("PASSWORD           ");
    Label error = new Label("");
    String code="123";
    TextField tname= new TextField();
    Scene chat_scene;
    String name="";
    Button disconnect= new Button("Disconnect");
    ListView listView1 = new ListView();
    ObservableList<Connected> connecteds = FXCollections.observableArrayList();

    String str="";
    int x=0;


     Client cl= new Client();

    public static void main(String[] args) {
        System.out.println("hello");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window=primaryStage;
        window.setTitle("CLIENT_APP");
        window.setResizable(false);
        window.setMinHeight(400);
        window.setMaxHeight(400);
        window.setMaxWidth(600);
        window.setMinWidth(600);

        tname.setPromptText("write your name here");

        GridPane grid= new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);

        HBox hb= new HBox();

        hb.getChildren().add(chatroom);
        hb.setAlignment(Pos.CENTER);
        hb.setStyle("-fx-background-color: #d7d7d8");

        chatroom.setFont(Font.font(null, FontWeight.BOLD, 32));
        chatroom.setStyle("-fx-text-fill: #293133");

        pw.setPromptText("write your password");

        lname.setFont(new Font("Arial",17));
        lpw.setFont(new Font("Arial",17));
        login.setFont(new Font("Arial",15));
        error.setFont(new Font("Arial",15));

        lname.setStyle("-fx-text-fill: #FFFFFF");
        lpw.setStyle("-fx-text-fill: #FFFFFF");

        activeEnter(login);

        GridPane.setConstraints(lname,0,0);
        GridPane.setConstraints(lpw,0,1);
        GridPane.setConstraints(tname,1,0);
        GridPane.setConstraints(pw,1,1);
        GridPane.setConstraints(login,2,2);
        GridPane.setConstraints(error,0,2);


        grid.getChildren().addAll(lname,lpw,tname,pw,login,error);
        grid.setAlignment(Pos.CENTER);
        BorderPane border= new BorderPane();
        border.setTop(hb);


        border.setCenter(grid);
        border.styleProperty().set("-fx-background-color: #293133");


        scene_login= new Scene(border);
        window.setScene(scene_login);
        window.show();

        //--------------------------------------------------------------------------


        login.setOnAction(e->{
            if (pw.getText().equals(code)) {
                name = tname.getText();


                Button send = new Button("SEND");
                send.setFont(new Font("Arial", 15));
                TextField text = new TextField("");

                BorderPane borderPane = new BorderPane();
                HBox hb1 = new HBox();
                hb1.setSpacing(5);
                hb1.setPadding(new Insets(5, 5, 5, 5));
                hb1.getChildren().addAll(text, send);
                hb1.setStyle("-fx-background-color: #1c79c0");
                hb1.setAlignment(Pos.CENTER);

                borderPane.setBottom(hb1);

                HBox hb2 = new HBox();

                Label welcome = new Label("         " + name + " is connected !");
                welcome.setFont(Font.font(null, FontWeight.BOLD, 28));
                welcome.setStyle("-fx-text-fill: #FFFFFF");

                hb2.setPadding(new Insets(10, 10, 10, 10));
                hb2.setSpacing(50);
                hb2.setStyle("-fx-background-color: #1c79c0");
                hb2.getChildren().addAll(welcome, disconnect);
                hb2.setAlignment(Pos.CENTER);
                borderPane.setTop(hb2);

                text.setMinWidth(text.getWidth() + 400);


                window.setOnCloseRequest(e2 -> {
                    System.out.println("hello kahaw");

                    try {
                        cl.Send(" ");
                        cl.Send(name);
                        System.out.println("hello1");
                        System.exit(0);
                    } catch (IOException ex) {
                        System.exit(0);
                    }
                    catch (java.lang.NullPointerException e5){
                        System.exit(0);
                    }
                });

                ListView listView = new ListView();

                ObservableList<Message> messages = FXCollections.observableArrayList();

                listView.setItems(messages);




                activeEnter(send);

                disconnect.setOnAction(e2 -> {
                    try {
                        cl.Send(" ");
                        cl.Send(name);
                        messages.add(new Message("     -----------DISCONNECTED--------------"));

                        cl.close();

                    } catch (IOException ex) {
                        System.out.println("disconnection error");
                    }

                });

                send.setOnAction(e1 -> {

                    if (!(text.getText().equals(""))) {

                        try {

                            boolean v= listView1.getSelectionModel().isEmpty();
                            if (v){
                                cl.Send(text.getText()+" ");
                            }else if (clean_word(listView1.getSelectionModel().getSelectedItems().toString()).equals("All")) {

                                cl.Send(text.getText()+" ");
                            }else{
                                cl.Send("* "+clean_word(listView1.getSelectionModel().getSelectedItems().toString())+ " " + text.getText());
                            }  } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        text.clear();

                    }
                });

                borderPane.setCenter(listView);



                VBox vBox= new VBox();
                vBox.setSpacing(2);




                Label l= new Label("  Connected ");

                HBox h= new HBox();
                h.getChildren().add(l);
                h.setMaxWidth(100);
                listView1.setMaxWidth(100);
                connecteds.add(new Connected("All"));

                vBox.getChildren().addAll(listView1,h);
                l.setFont(Font.font(null, FontWeight.BOLD, 15));
                l.setStyle("-fx-text-fill: #008000");


                borderPane.setRight(vBox);


                chat_scene = new Scene(borderPane, scene_login.getWidth(), scene_login.getHeight());

                chat_scene.getStylesheets().add("Theme.css");
                   window.setScene(chat_scene);


                Timer chrono = new Timer();



                chrono.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        if (!(name.equals(""))) {
                            try {
                                cl.connecti("localhost");
                                cl.Send(name);
                                  cancel();
                                while (!(str.equals("stop"))) {

                                    try {
                                        str = cl.receive();

                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                        break;
                                    }
                                   if (str.equals("*")){
                                        String ch=cl.receive();
                                        connecteds.add(new Connected(ch));
                                       listView1.setItems(connecteds);
                                    }else if (getword1(str).equals("d")){

                                       System.out.println("elimination is gonna start");
                                       messages.add(new Message("----------------"+getword2(str)+ " DISCONNECTED----------------"));

                                   }
                                    else if (!(str.equals(""))) {
                                        messages.add(new Message(str));
                                    }
                                }

                            } catch (IOException e3) {
                                messages.add(new Message("Connection problem happened, please check your connection to the server"));
                                cancel();
                                e3.printStackTrace();
                            }
                        }
                    }
                }, 1000, 1000);


            }
            else{

                error.setText("WRONG PASSWORD");
                error.setTextFill(Color.rgb(210, 39, 30));
            }



        });

    }

    public void activeEnter(Button b){

        b.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        b.fire();
                    }
                }
        );
    }




    public String clean_word(String ch){

        String sh="";

        for (int i=1;i<(ch.length()-1);i++){

            sh+=ch.charAt(i);

        }

        return sh;
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




}
