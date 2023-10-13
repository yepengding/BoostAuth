package org.veritasopher.boostauth.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

import java.io.IOException;

/**
 * Validators
 *
 * @author Yepeng Ding
 */
public class Validators {

    private static final TypeAdapter<JsonElement> strictJSONAdapter = new Gson().getAdapter(JsonElement.class);

    /**
     * Validate JSON format
     *
     * @param str string
     * @return true if the given string is valid json format
     */
    public static boolean isJSON(String str) {
        try {
            strictJSONAdapter.fromJson(str);
        } catch (JsonSyntaxException | IOException e) {
            return false;
        }
        return true;
    }

}
