import javafx.beans.property.SimpleStringProperty;

public class Connected {

    private SimpleStringProperty connected;

    public String getConnected() {
        return connected.get();
    }

    public SimpleStringProperty connectedProperty() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected.set(connected);
    }

    public String toString(){

        return getConnected();

    }

    public Connected(String connected) {

        this.connected = new SimpleStringProperty(connected);

    }
}
