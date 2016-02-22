package com.smithsmodding.armory.client.model.loaders;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.smithsmodding.armory.*;
import com.smithsmodding.armory.client.model.block.events.*;
import com.smithsmodding.armory.client.model.item.unbaked.*;
import com.smithsmodding.armory.client.model.item.unbaked.components.*;
import com.smithsmodding.armory.client.textures.*;
import com.smithsmodding.armory.common.tileentity.guimanagers.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.fml.common.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".Anvil-armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // HeatedItem armory extension. Foo.HI-armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            AnvilModelDefinition modelDefinition = AnvilModelDefinition.loadModel(modelLocation);

            IModel objModel = ModelLoaderRegistry.getModel(new ResourceLocation(modelDefinition.modelPath));

            BlackSmithsAnvilModelTextureLoadEvent event = new BlackSmithsAnvilModelTextureLoadEvent();
            event.PostClient();

            modelDefinition.texturePaths.putAll(event.getAdditionalTextureLayers());



        } catch (IOException e) {
            Armory.getLogger().error("Could not load Anvil-Model {}", modelLocation.toString());
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();

    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }

    private static class AnvilModelDefinition{
        String modelPath;
        Map<String, String> texturePaths;

        private static final Type type = new TypeToken<String>() {
        }.getType();

        private static final Gson
                GSON =
                new GsonBuilder().registerTypeAdapter(type, AnvilModelDeserializer.instance).create();

        private AnvilModelDefinition(String modelPath, Map<String, String> texturePaths)
        {
            this.modelPath = modelPath;
            this.texturePaths = texturePaths;
        }

        public static AnvilModelDefinition loadModel(ResourceLocation modelLocation) throws IOException {
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);

            // get the json
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
            Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

            return new AnvilModelDefinition(GSON.fromJson(reader, type), textures);
        }

        public static class AnvilModelDeserializer implements JsonDeserializer<String> {
            public static AnvilModelDeserializer instance = new AnvilModelDeserializer();

            private static final Gson GSON = new Gson();

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
             *
             * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
             *
             * @throws JsonParseException if json is not in the expected format of {@code typeofT}
             */
            @Override
            public String deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject obj = json.getAsJsonObject();
                JsonElement texElem = obj.get("model");

                if (texElem == null) {
                    throw new JsonParseException("Missing model entry in json");
                }

                return GSON.fromJson(texElem, type);
            }
        }
    }
}
