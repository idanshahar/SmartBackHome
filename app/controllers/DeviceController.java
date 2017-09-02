package controllers;


import javax.inject.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import exceptions.NotCompatibleDeviceException;
import executors.AzureHubExecutor;
import org.apache.http.HttpStatus;
import play.Logger;
import play.libs.Json;
import play.mvc.*;

import services.DeviceService;

import java.util.concurrent.CompletionStage;

@Singleton
public class DeviceController extends Controller {

    private DeviceService deviceService;
    private Gson gson;

    @Inject
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;

        this.deviceService = deviceService.addExecutor(new AzureHubExecutor());
        gson = new Gson();
    }

    public CompletionStage<Result> devices() throws Exception {
        return deviceService.getDevicesJson().thenApplyAsync(devices -> {
            if(devices == null) {
                return ok();
            } else {
                return ok(devices);
            }
        });
    }

    public Result addDevice() throws Exception {
        try {
            String json = request().body().asJson().toString();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            String deviceType = jsonObject.get("type").getAsString();
            return ok(deviceService.addDevice(deviceType));
        }
        catch (NotCompatibleDeviceException e) {
            return notFound("Couldn't find device type");
        }
        catch (Exception e) {
            Logger.warn("Could not add device", e);
            return internalServerError("Could not add device");
        }


    }

    public Result control() throws Exception {
        try {
            String json =  request().body().asJson().toString();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            String deviceId = jsonObject.get("id").getAsString();
            String command = jsonObject.get("command").getAsString();

            if(deviceService.isExists(deviceId)) {
                deviceService.executeCommand(deviceId, command);
                return ok();
            }
            else
                return notFound("device not found");
        }
        catch (Exception e) {
            Logger.error("Can't control device", e);
            return internalServerError("Ops something bad happened. Reason:" + e.getMessage());
        }
    }

}
