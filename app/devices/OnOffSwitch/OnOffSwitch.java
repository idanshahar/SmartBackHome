package devices.OnOffSwitch;

import com.google.gson.JsonObject;
import devices.ICommand;
import devices.Device;
import enums.States;

/**
 * Created by idan on 3/17/16.
 */

public class OnOffSwitch extends Device {

    public OnOffSwitch(String id, int status) {
        super(id,status);
    }

    public static Class<OnOffSwitch> getDeviceClass() {
        return OnOffSwitch.class;
    }

    public OnOffSwitch(JsonObject json){
        super(json.get("id").getAsString(),json.get("status").getAsInt());
    }

    @Override
    public ICommand generateCommand(JsonObject json) {
        ICommand command= new OnOffCommand(json.get("id").getAsString(),json.get("status").getAsInt());
        return command;
    }

    public String turnOn(){
        super.setStatus(States.ON);
        return "turnOn";
    }
    public String turnOff(){
        super.setStatus(States.OFF);
        return "turnOff";
    }

}
