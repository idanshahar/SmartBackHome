package services;

import com.typesafe.config.ConfigFactory;
import play.api.Play;


public class Config {

    public static String getIotHubConnectionString() {
        return ConfigFactory.load().getString("iothub.connectionString");
    }

    public static String getIotHubDeviceId() {
        return ConfigFactory.load().getString("iothub.deviceId");
    }

}
