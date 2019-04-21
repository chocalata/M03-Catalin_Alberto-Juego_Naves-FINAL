package transformmer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import formatClasses.NaveToRecive;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.util.ArrayList;

public abstract class Transformer {
    public static String classToJson(Object object){
        return new Gson().toJson(object);
    }

    public static ArrayList<NaveToRecive> jsonToArrayListNaves(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<NaveToRecive>>(){}.getType());
    }

    public static String packetDataToString(DatagramPacket packet) throws UnsupportedEncodingException {
        //return String.valueOf(new String(data, StandardCharsets.UTF_8));
        return new String(packet.getData(),
                packet.getOffset(),
                packet.getLength(), "UTF-8");
    }

    public static NaveToRecive jsonToNaveToRecive(String json) {
        return new Gson().fromJson(json, NaveToRecive.class);
    }
}
