package exceptions;

public class NotCompatibleDeviceException extends Exception{
    @Override
    public String getMessage() {
        return "Device Doesn't Compatible";
    }
}
