package com.smithsmodding.armory.client.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.client.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.smithscore.util.common.Pair;
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
public class MultiLayeredArmorModelDeserializer implements JsonDeserializer<Pair<HashMap<String, ResourceLocation>, HashMap<String, ResourceLocation>>> {
    public static final MultiLayeredArmorModelDeserializer instance = new MultiLayeredArmorModelDeserializer();

    private static final Type layerBrokenType = new TypeToken<Pair<HashMap<String, ResourceLocation>, HashMap<String, ResourceLocation>>>() {
    }.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(layerBrokenType, instance).create();

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

        Pair<HashMap<String, ResourceLocation>, HashMap<String, ResourceLocation>> layerData = gson.fromJson(reader, layerBrokenType);

        return new MultiLayeredArmorModelDefinition(layerData.getKey(), layerData.getValue());
    }

    @Override
    public Pair<HashMap<String, ResourceLocation>, HashMap<String, ResourceLocation>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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

        return new Pair<>(layersLocations, brokenLocations);
    }
}
