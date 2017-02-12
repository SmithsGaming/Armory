package com.smithsmodding.armory.api.client.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.client.model.deserializers.definition.MaterializedBlockModelDefinition;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedBlockModelDeserializer implements JsonDeserializer<MaterializedBlockModelDefinition> {

    public static final MaterializedBlockModelDeserializer INSTANCE = new MaterializedBlockModelDeserializer();

    static final Type resourceMapType = new TypeToken<Map<ResourceLocation, ResourceLocation>>() {}.getType();
    static final Type stringMapType = new TypeToken<Map<String, String>>() {}.getType();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(MaterializedBlockModelDefinition.class, INSTANCE)
            .registerTypeAdapter(resourceMapType, TextureOverrideDeserializer.INSTANCE)
            .create();

    private MaterializedBlockModelDeserializer() {
    }

    public MaterializedBlockModelDefinition deserialize(@NotNull ResourceLocation modelLocation) throws IOException {
        return gson.fromJson(ModelHelper.getReaderForResource(ModelHelper.getModelLocation(modelLocation)), MaterializedBlockModelDefinition.class);
    }

    @NotNull
    @Override
    public MaterializedBlockModelDefinition deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String coreResource = jsonObject.get("parent").getAsString();
        String coreTexture = jsonObject.get("texture").getAsString();
        Map<ResourceLocation, ResourceLocation> materialOverrides = gson.fromJson(json, resourceMapType);

        return new MaterializedBlockModelDefinition(new ResourceLocation(coreResource), new ResourceLocation(coreTexture), materialOverrides, ModelHelper.TransformDeserializer.INSTANCE.deserialize(json, typeOfT, context));
    }

    public static class TextureOverrideDeserializer implements JsonDeserializer<Map<ResourceLocation, ResourceLocation>> {

        public static final TextureOverrideDeserializer INSTANCE = new TextureOverrideDeserializer();

        private static final Gson GSON = new Gson();

        @Override
        @Nonnull
        public Map<ResourceLocation, ResourceLocation> deserialize(@Nonnull JsonElement json, @Nonnull Type typeOfT, @Nonnull JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject obj = json.getAsJsonObject();
            JsonElement texElem = obj.get("overrides");

            if (texElem == null) {
                throw new JsonParseException("Missing overrides entry in json");
            }

            Map<String, String> stringMap = GSON.fromJson(texElem, stringMapType);
            Map<ResourceLocation, ResourceLocation> result = new HashMap<>();

            stringMap.forEach((name, location) -> {
                result.put(new ResourceLocation(name), new ResourceLocation(location));
            });

            return result;
        }
    }
}
