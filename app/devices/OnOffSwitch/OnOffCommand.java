package devices.OnOffSwitch;

import devices.ICommand;
import enums.States;
import devices.Device;
import executors.IExecutor;

/**
 * Created by idan on 3/20/16.
 */
public class OnOffCommand implements ICommand {

    private int status;


    public OnOffCommand(String id, int status) {
        this.status = status;

    }

    @Override
    public void execute(IExecutor executor, Device device) {
        OnOffSwitch dev = (OnOffSwitch) device;
        String deviceId = device.getId();
        switch (status) {
            case States.OFF:
                executor.execute(dev.turnOff(),this, deviceId);
                break;

            case States.ON:
                executor.execute(dev.turnOn(),this, deviceId);
                break;
        }

    }

    public int getStatus(){
        return status;
    }

    @Override
    public int setStatus(int status) {
        return 0;
    }
}
