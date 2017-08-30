package controllers;


import javax.inject.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    @Inject
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;

        this.deviceService = deviceService.addExecutor(new AzureHubExecutor());
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
        String json =  request().body().asJson().toString();
        return ok(deviceService.addDevice(json));                   

    }

    public Result control() throws Exception {
        try {
            String json =  request().body().asJson().toString();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            String deviceId = jsonObject.get("id").getAsString();
            if(deviceService.isExists(deviceId)) {
                deviceService.executeCommand(json);
                return ok();
            }
            else
                return notFound("device not found");
        } catch (Exception e) {

            Logger.error("Can't control device", e);
            return internalServerError("Ops something bad happened :(");
        }
    }

}
