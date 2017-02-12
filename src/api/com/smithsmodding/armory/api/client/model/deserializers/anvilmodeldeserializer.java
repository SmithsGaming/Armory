package com.smithsmodding.armory.api.client.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class AnvilModelDeserializer implements JsonDeserializer<String> {
    private static final Type stringType = new TypeToken<String>() {
    }.getType();

    private static final Gson GSON = new Gson();
    @NotNull
    public static AnvilModelDeserializer instance = new AnvilModelDeserializer();

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the specified type. <p>In
     * the implementation of this call-back method, you should consider invoking {@link
     * JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects for any non-trivial field of
     * the returned object. However, you should never invoke it on the the same type passing {@code json} since that
     * will cause an infinite loop (Gson will call your call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public String deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        JsonElement texElem = obj.get("model");

        if (texElem == null) {
            throw new JsonParseException("Missing model entry in json");
        }

        return GSON.fromJson(texElem, stringType);
    }
}
