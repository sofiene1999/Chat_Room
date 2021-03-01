import javafx.beans.property.SimpleStringProperty;

public class Message {



        private SimpleStringProperty message;

        public String getMessage() {
            return message.get();
        }

        public SimpleStringProperty messageProperty() {
            return message;
        }

        public void setMessage(String message) {
            this.message.set(message);
        }

        public String toString(){

            return getMessage();

        }

        public Message(String message) {

            this.message = new SimpleStringProperty(message);

        }

}
