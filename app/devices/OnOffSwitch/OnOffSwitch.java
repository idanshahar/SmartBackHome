package devices.OnOffSwitch;

import com.google.gson.JsonObject;
import devices.ICommand;
import devices.Device;


/**
 * Created by idan on 3/17/16.
 */

public class OnOffSwitch extends Device {

    public OnOffSwitch(String id, String status) {
        super(id,status);
    }

    public static Class<OnOffSwitch> getDeviceClass() {
        return OnOffSwitch.class;
    }

    public OnOffSwitch(JsonObject json){
        super(json.get("id").getAsString(), json.get("status").getAsString());
    }

    @Override
    public ICommand generateCommand(String command) {
        ICommand onOfSwitchCommand= new OnOffCommand(getId(), command);
        return onOfSwitchCommand;
    }

    public String turnOn(){
        super.setStatus("ON");
        return "turnOn";
    }
    public String turnOff(){
        super.setStatus("OFF");
        return "turnOff";
    }

}
