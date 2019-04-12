package transformToJsonOrClass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.toRecive.NaveToReciveServer;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class Transformer {
    public static String classToJson(Object object){
        return new Gson().toJson(object);
    }

    public static ArrayList<NaveToReciveServer> jsonToArrayListNaves(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<NaveToReciveServer>>(){}.getType());
    }

    public static String packetDataToString(DatagramPacket packet) throws UnsupportedEncodingException {
        //return String.valueOf(new String(data, StandardCharsets.UTF_8));
        return new String(packet.getData(),
                packet.getOffset(),
                packet.getLength(), "UTF-8");
    }

    public static NaveToReciveServer jsonToNaveToReciveServer(String json) {
        return new Gson().fromJson(json, NaveToReciveServer.class);
    }
}
