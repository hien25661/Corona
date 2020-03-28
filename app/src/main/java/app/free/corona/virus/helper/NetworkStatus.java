package app.free.corona.virus.helper;

public class NetworkStatus {

    public static final int OK = 1;
    public static final int DISCONNECTED = -1;
    public static final int INTERNET_CONNECTING = 0;

    private int status;

    public NetworkStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        switch (status) {
            case OK:
                return "OK";
            case DISCONNECTED:
                return "STT_DISCONNECTED";
            case INTERNET_CONNECTING:
                return "STT_CONNECTING";
        }

        return "INTERNET_NOT_SPECIFIED";
    }
}
