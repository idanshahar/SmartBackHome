package devices.OnOffSwitch;

import devices.ICommand;
import devices.Device;
import executors.IExecutor;

/**
 * Created by idan on 3/20/16.
 */
public class OnOffCommand implements ICommand {

    private String command;


    public OnOffCommand(String id, String command) {
        this.command = command;

    }

    @Override
    public void execute(IExecutor executor, Device device) throws Exception {
        OnOffSwitch dev = (OnOffSwitch) device;
        String deviceId = device.getId();
        switch (command) {
            case "turnOn":
                executor.execute(dev.turnOn(),this, deviceId);
                break;

            case "turnOff":
                executor.execute(dev.turnOff(),this, deviceId);
                break;
            default:
                throw new Exception("command does not exists");

        }

    }


    public String getCommand(){
        return command;
    }

    @Override
    public String setCommand(String status) {
        return status;
    }
}
