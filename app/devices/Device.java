package devices;
import com.google.gson.JsonObject;

/**
 * Created by idan on 3/13/16.
 */

public abstract class Device {


    private String id;
    private String status;


    public Device(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public String getCurrentStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    abstract public ICommand generateCommand(String command);

}
