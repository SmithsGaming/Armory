package com.smithsmodding.armory.api.client.model.deserializers.definition;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.armory.api.client.model.deserializers.AnvilBottomTextureDeserializer;
import com.smithsmodding.armory.api.client.model.deserializers.AnvilModelDeserializer;
import com.smithsmodding.armory.api.client.model.deserializers.AnvilTopTextureDeserializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class AnvilModelDefinition {
    static final Type maptype = new TypeToken<Map<ResourceLocation, String>>() {

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

    private final String modelPath;
    private final Map<ResourceLocation, String> textureTopPaths;
    private final Map<ResourceLocation, String> textureBottomPaths;

    private AnvilModelDefinition(String modelPath, Map<ResourceLocation, String> textureTopPaths, Map<ResourceLocation, String> textureBottomPaths) {
        this.modelPath = modelPath;
        this.textureTopPaths = textureTopPaths;
        this.textureBottomPaths = textureBottomPaths;
    }

    @NotNull
    public static AnvilModelDefinition loadModel(@NotNull ResourceLocation modelLocation) throws IOException {
        return new AnvilModelDefinition(loadModelDefinition(modelLocation), loadModelTexturesForTop(modelLocation), loadModelTexturesForBottom(modelLocation));
    }

    public static String loadModelDefinition(@NotNull ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return GSONMODEL.fromJson(reader, stringType);
    }

    public static Map<ResourceLocation, String> loadModelTexturesForTop(@NotNull ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return GSONTOP.fromJson(reader, maptype);
    }

    public static Map<ResourceLocation, String> loadModelTexturesForBottom(@NotNull ResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return GSONBOTTOM.fromJson(reader, maptype);
    }

    public String getModelPath() {
        return modelPath;
    }

    public Map<ResourceLocation, String> getTextureTopPaths() {
        return textureTopPaths;
    }

    public Map<ResourceLocation, String> getTextureBottomPaths() {
        return textureBottomPaths;
    }

}
