package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import convertors.JsonConverter;
import devices.Device;
import executors.IExecutor;
import models.Devices;
import play.Logger;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Singleton
public class DeviceService {

    private Devices devices = new Devices();
    private JsonConverter jsonConverter = new JsonConverter();
    private IExecutor executor;
    private Gson gson = new Gson();

    public DeviceService addExecutor(IExecutor executor) {
        this.executor = executor;
        return this;
    }

    public String addDevice(String json) throws Exception {
        JsonObject jsonObject = (JsonObject)jsonConverter.fromJson(json,JsonObject.class);
        String className = jsonObject.get("type").getAsString();
        Device dev = gson.fromJson("{}", (Class<Device>) Class.forName(String.format("devices.%s.%s",className,className)));
        devices.addDevice(dev);
        return dev.getId();

    }
    public CompletableFuture<String> getDevicesJson() throws Exception {
        try {
            return CompletableFuture.completedFuture(devices.getDevicesJson());
        } catch (Exception e) {
            Logger.info("Cannot return device list " + e.getStackTrace());
            throw e;
        }
    }

    public void executeCommand(String json) throws Exception {
        JsonObject jsonObject = (JsonObject)jsonConverter.fromJson(json, JsonObject.class);
        String deviceId = jsonObject.get("id").getAsString();
        Device dev = devices.getDevice(deviceId);
        dev.generateCommand(jsonObject).execute(executor,dev);
    }
}
