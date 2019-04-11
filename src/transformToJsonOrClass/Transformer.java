package transformToJsonOrClass;

import com.google.gson.Gson;
import server.toRecive.NaveToReciveServer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class Transformer {
    public static String classToJson(Object object){
        return new Gson().toJson(object);
    }

    public static ArrayList jsonToArrayListNaves(String json) {
        return new Gson().fromJson(json, ArrayList.class);
    }

    public static String arrayByteToString(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    public static NaveToReciveServer jsonToNaveToReciveServer(String json) {
        return new Gson().fromJson(json, NaveToReciveServer.class);
    }
}
