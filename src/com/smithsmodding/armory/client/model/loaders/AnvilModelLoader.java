package com.smithsmodding.armory.client.model.loaders;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.client.model.block.events.BlackSmithsAnvilModelTextureLoadEvent;
import com.smithsmodding.armory.client.model.block.unbaked.BlackSmithsAnvilModel;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".Anvil-armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // Anvil armory extension. Foo.Anvil-armory.json
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

            modelDefinition.textureTopPaths.putAll(event.getAdditionalTopTextureLayers());
            modelDefinition.textureBottomPaths.putAll(event.getAdditionalBottomTextureLayers());

            BlackSmithsAnvilModel model = new BlackSmithsAnvilModel(objModel);

            for(IAnvilMaterial material : AnvilMaterialRegistry.getInstance().getAllRegisteredAnvilMaterials().values())
            {
                if (modelDefinition.textureTopPaths.containsKey(material.getID()) || modelDefinition.textureBottomPaths.containsKey(material.getID()))
                {
                    ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

                    if (modelDefinition.textureTopPaths.containsKey(material.getID()))
                        builder.put("#Anvil", modelDefinition.textureTopPaths.get(material.getID()));

                    if (modelDefinition.textureBottomPaths.containsKey(material.getID()))
                        builder.put("#Bottom", modelDefinition.textureBottomPaths.get(material.getID()));


                    model.registerNewMaterializedModel(((OBJModel) objModel).retexture(builder.build()), material.getID());
                }
                else
                {
                    OBJModel newModel = (OBJModel) ModelHelper.forceLoadOBJModel(new ResourceLocation(modelDefinition.modelPath));

                    OBJModel.Material materialOBJ = newModel.getMatLib().getMaterial("Anvil");

                    Vector4f colorVec = new Vector4f();
                    colorVec.w = 1F;
                    colorVec.x = material.getRenderInfo().getVertexColor().getRedFloat();
                    colorVec.y = material.getRenderInfo().getVertexColor().getGreenFloat();
                    colorVec.z = material.getRenderInfo().getVertexColor().getBlueFloat();

                    materialOBJ.setColor(colorVec);

                    model.registerNewMaterializedModel(newModel, material.getID());
                }
            }

            return model;
        } catch (Exception e) {
            Armory.getLogger().error("Could not load Anvil-Model {}", modelLocation.toString());
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();

    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }

    public static class AnvilModelDefinition{
        static final Type maptype = new TypeToken<Map<String, String>>() {
        }.getType();
        private static final Type stringType = new TypeToken<String>() {
        }.getType();
        private static final Gson
                GSONMODEL =
                new GsonBuilder().registerTypeAdapter(stringType, AnvilModelDeserializer.instance).create();
        private static final Gson
                GSONTOP =
                new GsonBuilder().registerTypeAdapter(maptype, AnvilTopTextureDeserializer.instance).create();
        private static final Gson
                GSONBOTTOM =
                new GsonBuilder().registerTypeAdapter(maptype, AnvilBottomTextureDeserializer.instance).create();
        String modelPath;
        Map<String, String> textureTopPaths;
        Map<String, String> textureBottomPaths;

        private AnvilModelDefinition(String modelPath, Map<String, String> textureTopPaths, Map<String, String> textureBottomPaths)
        {
            this.modelPath = modelPath;
            this.textureTopPaths = textureTopPaths;
            this.textureBottomPaths = textureBottomPaths;
        }

        public static AnvilModelDefinition loadModel(ResourceLocation modelLocation) throws IOException {
            return new AnvilModelDefinition(loadModelDefinition(modelLocation), loadModelTexturesForTop(modelLocation), loadModelTexturesForBottom(modelLocation));
        }

        public static String loadModelDefinition(ResourceLocation modelLocation) throws IOException {
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
            Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

            return GSONMODEL.fromJson(reader, stringType);
        }

        public static Map<String, String> loadModelTexturesForTop(ResourceLocation modelLocation) throws IOException {
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
            Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

            return GSONTOP.fromJson(reader, maptype);
        }

        public static Map<String, String> loadModelTexturesForBottom(ResourceLocation modelLocation) throws IOException {
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
            Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

            return GSONBOTTOM.fromJson(reader, maptype);
        }

        public static class AnvilModelDeserializer implements JsonDeserializer<String> {
            private static final Gson GSON = new Gson();
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

                return GSON.fromJson(texElem, stringType);
            }
        }

        public static class AnvilBottomTextureDeserializer implements JsonDeserializer<Map<String, String>> {

            public static final AnvilBottomTextureDeserializer instance = new AnvilBottomTextureDeserializer();

            private static final Gson GSON = new Gson();

            @Override
            public Map<String, String> deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {

                JsonObject obj = json.getAsJsonObject();
                JsonElement texElem = obj.get("textures-bottom");

                if (texElem == null) {
                    throw new JsonParseException("Missing bottom textures entry in json");
                }

                return GSON.fromJson(texElem, maptype);
            }
        }

        public static class AnvilTopTextureDeserializer implements JsonDeserializer<Map<String, String>> {

            public static final AnvilTopTextureDeserializer instance = new AnvilTopTextureDeserializer();

            private static final Gson GSON = new Gson();

            @Override
            public Map<String, String> deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {

                JsonObject obj = json.getAsJsonObject();
                JsonElement texElem = obj.get("textures-top");

                if (texElem == null) {
                    throw new JsonParseException("Missing top textures entry in json");
                }

                return GSON.fromJson(texElem, maptype);
            }
        }
    }
}
