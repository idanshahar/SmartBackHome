package models;



import com.google.gson.Gson;
import devices.Device;

import java.util.*;

/**
 * Created by idan on 3/13/16.
 */
public class Devices {
    private ArrayList<Device> devices;
    private HashMap<String,Device> deviceHash;
    public Devices() {
        devices = new ArrayList<Device>();
        deviceHash = new HashMap<String, Device>();
    }

    public void addDevice(Device dev){
        dev.setId(UUID.randomUUID().toString());
        devices.add(dev);
        deviceHash.put(dev.getId(), dev);
    }
    public void removeDevice(Device dev){
        devices.remove(dev);
    }

    public String getDevicesJson(){
        if(devices != null) {
            Gson gson = new Gson();
            return gson.toJson(deviceHash);
        }
        else
            return "{}";
    }

    public ArrayList<Device> getDevices(){
            return devices;
    }
    public Device getDevice(String id){
      return deviceHash.get(id);
    }

}
