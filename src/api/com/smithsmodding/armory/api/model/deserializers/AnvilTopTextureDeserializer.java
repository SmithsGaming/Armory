package com.smithsmodding.armory.api.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class AnvilTopTextureDeserializer implements JsonDeserializer<Map<String, String>> {
    public static final AnvilTopTextureDeserializer instance = new AnvilTopTextureDeserializer();
    private static final Type maptype = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Gson GSON = new Gson();

    @Override
    public Map<String, String> deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        JsonElement texElem = obj.get("textures-top");

        if (texElem == null) {
            throw new JsonParseException("Missing top textures entry in json");
        }

        return GSON.fromJson(texElem, maptype);
    }
}