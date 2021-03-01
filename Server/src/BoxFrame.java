import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.*;
import java.util.Timer;

public class BoxFrame extends Application {

    ServerSocket sv;
    static Map<String,ClientHandler> clientList;
    Stage window;
    Scene scene;
    Button helo =new Button("SERVER ACTIVATED ");

    public static void main(String[] args) throws IOException {
        launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        window=primaryStage;

        window.setMinWidth(600);
        window.setMaxWidth(600);
        window.setMinHeight(400);
        window.setMaxHeight(400);
        window.setTitle("Server_App");

        HBox hb= new HBox();
        hb.setMinHeight(40);
        HBox hb1= new HBox();
        hb1.setMinHeight(40);

        hb.setStyle("-fx-background-color: #181818");
        hb1.setStyle("-fx-background-color: #181818");

        BorderPane borderPane=new BorderPane();
        borderPane.setTop(hb);
        borderPane.setBottom(hb1);

        VBox vBox= new VBox();
        vBox.getChildren().add(helo);


        borderPane.setCenter(vBox);
        vBox.setStyle("-fx-background-color: #293133");
        vBox.setAlignment(Pos.CENTER);

        helo.setAlignment(Pos.CENTER);
        helo.setFont(Font.font(null, FontWeight.BOLD, 32));

        helo.setStyle("-fx-text-fill: #01AAFE");

        scene= new Scene(borderPane);
        window.setScene(scene);
        window.show();

        System.out.println("hello there");

        sv= new ServerSocket(3333);

            clientList= new HashMap<>();


            helo.setOnAction(e->{

                while(true){

                    Socket s= null;
                    try {
                        s = sv.accept();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Thread clh= new ClientHandler(s,this);

                    clh.start();

                }


            });



        }

}
