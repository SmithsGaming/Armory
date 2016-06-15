package com.smithsmodding.armory.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDeserializer implements JsonDeserializer<MultiLayeredArmorModelDefinition> {
    public static final MultiLayeredArmorModelDeserializer instance = new MultiLayeredArmorModelDeserializer();

    private static final Type definitionType = new TypeToken<MultiLayeredArmorModelDefinition>() {
    }.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(definitionType, instance).create();

    private MultiLayeredArmorModelDeserializer() {
    }

    /**
     * Method deserializes the given ModelLocation  into a MultiComponentModel.
     * The returned definition will hold all the SubModels in a Map.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    public MultiLayeredArmorModelDefinition deserialize(ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return gson.fromJson(reader, definitionType);
    }

    @Override
    public MultiLayeredArmorModelDefinition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject layers = jsonObject.get("layers").getAsJsonObject();
        JsonObject broken = jsonObject.get("broken").getAsJsonObject();

        HashMap<String, ResourceLocation> layersLocations = new HashMap<>();
        HashMap<String, ResourceLocation> brokenLocations = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : layers.entrySet()) {
            layersLocations.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
        }

        for (Map.Entry<String, JsonElement> entry : broken.entrySet()) {
            brokenLocations.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
        }

        ResourceLocation baseLocation = null;

        if (json.getAsJsonObject().has("base"))
            baseLocation = new ResourceLocation(json.getAsJsonObject().get("base").getAsString());

        return new MultiLayeredArmorModelDefinition(baseLocation, layersLocations, brokenLocations);
    }
}
