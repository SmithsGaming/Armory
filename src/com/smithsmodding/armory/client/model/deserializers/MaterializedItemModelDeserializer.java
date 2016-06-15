package com.smithsmodding.armory.client.model.deserializers;

import com.google.gson.*;
import com.smithsmodding.armory.client.model.deserializers.definition.MaterializedItemModelDefinition;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModelDeserializer implements JsonDeserializer<MaterializedItemModelDefinition> {

    public static final MaterializedItemModelDeserializer INSTANCE = new MaterializedItemModelDeserializer();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(MaterializedItemModelDefinition.class, INSTANCE)
            .create();

    private MaterializedItemModelDeserializer() {
    }

    public MaterializedItemModelDefinition deserialize(ResourceLocation modelLocation) throws IOException {
        return gson.fromJson(ModelHelper.getReaderForResource(ModelHelper.getModelLocation(modelLocation)), MaterializedItemModelDefinition.class);
    }

    @Override
    public MaterializedItemModelDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String coreResource = jsonObject.get("texture").getAsString();

        return new MaterializedItemModelDefinition(new ResourceLocation(coreResource), ModelHelper.TransformDeserializer.INSTANCE.deserialize(json, typeOfT, context));
    }
}
