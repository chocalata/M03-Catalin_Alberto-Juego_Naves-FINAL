package transformToJsonOrClass;

import com.google.gson.Gson;
import game.model.Nave;
import game.model.toSend.NaveToSend;

import java.util.ArrayList;

public class Transformer {
    public static String classToJson(Object object){
        return new Gson().toJson(object);
    }
    public static ArrayList<NaveToSend> jsonToClass(String json){
        return new Gson().fromJson(json, ArrayList.class);
    }
}
