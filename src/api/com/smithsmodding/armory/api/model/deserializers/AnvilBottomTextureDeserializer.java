package com.smithsmodding.armory.api.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class AnvilBottomTextureDeserializer implements JsonDeserializer<Map<ResourceLocation, String>> {
    public static final AnvilBottomTextureDeserializer instance = new AnvilBottomTextureDeserializer();
    private static final Type maptype = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Gson GSON = new Gson();

    @Override
    public Map<ResourceLocation, String> deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        JsonElement texElem = obj.get("textures-bottom");

        if (texElem == null) {
            throw new JsonParseException("Missing bottom textures entry in json");
        }

        Map<String, String> loadedMap = GSON.fromJson(texElem, maptype);
        Map<ResourceLocation, String> convertedMap = new HashMap<>();

        loadedMap.forEach((k,v) -> convertedMap.put(new ResourceLocation(k), v));

        return convertedMap;
    }
}
