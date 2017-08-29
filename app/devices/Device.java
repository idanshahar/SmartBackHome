package devices;
import com.google.gson.JsonObject;

/**
 * Created by idan on 3/13/16.
 */

public abstract class Device {


    private String id;
    private int status;


    public Device(String id,int status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }
    public int getCurrentStatus(){
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    abstract public ICommand generateCommand(JsonObject Json);

}
