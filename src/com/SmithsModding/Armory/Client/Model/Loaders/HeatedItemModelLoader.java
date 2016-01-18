package com.smithsmodding.Armory.Client.Model.Loaders;


import com.google.common.collect.*;
import com.smithsmodding.Armory.*;
import com.smithsmodding.Armory.Client.Model.Item.Unbaked.Components.*;
import com.smithsmodding.Armory.Client.Model.Item.Unbaked.*;
import com.smithsmodding.Armory.Client.Textures.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.fml.common.*;

import java.io.*;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class HeatedItemModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".HI-Armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // HeatedItem Armory extension. Foo.HI-Armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Load the default definition of the model as defined by the registrar first.
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);

            //Create the final list builder.
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * Temp (Component texture used when the armor is not broken)
            for (Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();

                ResourceLocation location = null;
                TemperatureBarComponentModel partModel = null;

                try {
                    if (name.startsWith("temp")) {
                        //Standard Layer
                        location = new ResourceLocation(entry.getValue());
                    } else {
                        //Unknown layer, warning and skipping.
                        Armory.getLogger().warn("HeatedItemModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                        continue;
                    }
                    //If the texture was added to any layer, add it to the list of used textures.
                    if (location != null) {
                        builder.add(location);
                    }
                } catch (NumberFormatException e) {
                    Armory.getLogger().error("HeatedItemModel{} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                }
            }

            if (builder.build().size() == 0) {
                Armory.getLogger().error("A given model definition for the HeatedItems did not contain any gauges.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new HeatedItemItemModel(builder.build());

            // Load all textures we need in to the creator.
            MaterializedTextureCreator.registerBaseTexture(builder.build());

            return output;
        } catch (IOException e) {
            Armory.getLogger().error("Could not load multimodel {}", modelLocation.toString());
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    private void addComponentToList (List<ArmorComponentModel> list, int index, ArmorComponentModel model) {
        while (list.size() <= index) {
            list.add(null);
        }
        list.set(index, model);
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }
}
