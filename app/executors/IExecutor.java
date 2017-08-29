package executors;

import devices.ICommand;


public interface IExecutor {
    public void execute(String method, ICommand command, String deviceId);
}
