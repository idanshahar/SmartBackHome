package convertors;


import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * Created by idan on 3/17/16.
 */
@Service
public class JsonConverter {
    private Gson gson;
    public JsonConverter() {
        gson = new Gson();
    }

    public String toJson(Object obj, Class cls) throws Exception{
       return gson.toJson(obj,cls);
    }
    public Object fromJson(String json, Class cls) throws Exception{
        return gson.fromJson(json,cls);
    }
}
