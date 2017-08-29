package devices;

import devices.Device;
import executors.IExecutor;

import java.util.concurrent.CompletionStage;

/**
 * Created by idan on 3/20/16.
 */
public interface ICommand {

    void execute(IExecutor executor, Device device);
    int getStatus();
    int setStatus(int status);
}
