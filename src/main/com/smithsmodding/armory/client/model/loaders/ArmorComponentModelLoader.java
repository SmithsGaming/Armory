package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.item.unbaked.ArmorItemComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

/**
 * Author Marc (Created on: 12.06.2016)
 */
public class ArmorComponentModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".AC-Armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // HeatedItem armory extension. Foo.AC-armory.json
    }

    @Override
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Load the default definition of the model as defined by the registrar first.
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);

            //Create the final list builder.
            ImmutableMap.Builder<ResourceLocation, ResourceLocation> pairBuilder = new ImmutableMap.Builder<>();

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * AddonID (Component texture used when the armor is not broken)
            for (Map.Entry<String, String> entry : textures.entrySet()) {
                ResourceLocation name = new ResourceLocation(entry.getKey());
                ResourceLocation location = new ResourceLocation(entry.getValue());

                pairBuilder.put(name, location);
            }

            if (pairBuilder.build().size() == 0) {
                ModLogger.getInstance().error("A given model definition for an ArmorItemComponentModel did not contain any Components.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new ArmorItemComponentModel(pairBuilder.build(), transforms);

            // Load all textures we need in to the creator.
            MaterializedTextureCreator.registerBaseTexture(pairBuilder.build().values());

            return output;
        } catch (IOException e) {
            ModLogger.getInstance().error(String.format("Could not load ArmorItemComponentModel {}", modelLocation.toString()));
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
