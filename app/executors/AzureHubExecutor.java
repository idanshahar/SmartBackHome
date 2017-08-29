package executors;


import com.google.gson.Gson;
import devices.ICommand;
import play.Logger;

import javax.inject.Singleton;
import com.microsoft.azure.sdk.iot.service.*;
import services.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Singleton
public class AzureHubExecutor implements IExecutor {

    private Config config;

    private Gson gson;

    private ServiceClient serviceClient;

    private static FeedbackReceiver feedbackReceiver = null;

    public AzureHubExecutor() {

        gson = new Gson();
        try {
            serviceClient = ServiceClient.createFromConnectionString(config.getIotHubConnectionString(),
                    IotHubServiceClientProtocol.AMQPS);
            Logger.info(String.format("AzureIotHub executor configured with connectionString: %s", config.getIotHubConnectionString()));

            serviceClient.openAsync().get();

            serviceClient.getFeedbackReceiver().openAsync().get();
            System.out.println("********* Successfully opened FeedbackReceiver...");

        } catch (IOException | InterruptedException | ExecutionException e) {
            Logger.error("ServiceClient initialization failed",e);
        }
    }

    @Override
    public void execute(String method,ICommand command, String deviceId) {

        try {


            Map<String, String> propertiesToSend = new HashMap<String, String>();

            Message messageToSend = new Message(gson.toJson(command));

            messageToSend.setDeliveryAcknowledgement(DeliveryAcknowledgement.NegativeOnly);
            messageToSend.setMessageId(java.util.UUID.randomUUID().toString());

            propertiesToSend.put("command", method);

            messageToSend.setProperties(propertiesToSend);

            System.out.println("Message id set: " + messageToSend.getMessageId());

            // send the message
            serviceClient.sendAsync(deviceId, messageToSend).get();

            Logger.info(String.format("execute command %s on device: %s ", method, deviceId));
        } catch (Exception e) {
            Logger.error("error occurred while executing command", e);
        }

    }

}
