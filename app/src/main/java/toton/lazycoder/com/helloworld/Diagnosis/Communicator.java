package toton.lazycoder.com.helloworld.Diagnosis;

import org.json.JSONObject;

public interface Communicator{
    public enum Response{
        CONTINUE,
        BACK,
        RESET
    }

    public void communicate(Response response, JSONObject info);
}