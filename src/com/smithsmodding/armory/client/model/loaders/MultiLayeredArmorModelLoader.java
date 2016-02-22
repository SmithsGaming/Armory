package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.*;
import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.*;
import com.smithsmodding.armory.client.model.item.events.*;
import com.smithsmodding.armory.client.model.item.unbaked.*;
import com.smithsmodding.armory.client.model.item.unbaked.components.*;
import com.smithsmodding.armory.client.textures.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.fml.common.*;
import org.apache.commons.io.*;

import java.io.*;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".MLA-armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // MLA armory extension. Foo.MLA-armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Retrieve the Name of the armor.
            //The file name without the extension has to be equal to the Name used in Armories registry.
            String armorInternalName = FilenameUtils.getBaseName(modelLocation.getResourcePath());
            MultiLayeredArmor armor = MaterialRegistry.getInstance().getArmor(armorInternalName);

            //If none is registered return missing model and print out an error.
            if (armor == null) {
                Armory.getLogger().error("The given model: " + modelLocation.toString() + " Is not registered to any armor known to armory.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Load the default definition of the model as defined by the registrar first.
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);

            //Fire the TextureLoadEvent to allow third parties to add additional layers to the model if necessary
            MultiLayeredArmorModelTextureLoadEvent textureLoadEvent = new MultiLayeredArmorModelTextureLoadEvent(armor);
            textureLoadEvent.PostClient();

            //Add the additional textures to the list to load.
            textures.putAll(textureLoadEvent.getAdditionalTextureLayers());

            //Create the final list builder.
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            //Define the model structure components.
            ArmorComponentModel base = null;
            HashMap<String, ArmorComponentModel> parts = new HashMap<String, ArmorComponentModel>();
            HashMap<String, ArmorComponentModel> brokenParts = new HashMap<String, ArmorComponentModel>();

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * layer (Component texture used when the armor is not broken)
            //    * broken (Component texture used when the armor is broken)
            //    * base (The base layer of a armor (in case of MedievalArmor it is the chain base layer texture))
            for (Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();

                ResourceLocation location = null;
                ArmorComponentModel partModel = null;

                try {
                    if (name.startsWith("layer")) {
                        //Standard Layer
                        location = new ResourceLocation(entry.getValue());
                        partModel = new ArmorComponentModel(ImmutableList.of(location));

                        parts.put(location.toString(), partModel);
                    } else if (name.startsWith("broken")) {
                        //Broken layer
                        location = new ResourceLocation(entry.getValue());
                        partModel = new ArmorComponentModel(ImmutableList.of(location));

                        brokenParts.put(location.toString(), partModel);
                    } else if (name.startsWith("base")) {
                        //Base layer
                        location = new ResourceLocation(entry.getValue());
                        partModel = new ArmorComponentModel(ImmutableList.of(location));

                        base = partModel;
                    }
                    else {
                        //Unknown layer, warning and skipping.
                        Armory.getLogger().warn("MLAModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                        continue;
                    }


                    //If the texture was added to any layer, add it to the list of used textures.
                    if (location != null) {
                        builder.add(location);
                    }
                } catch (NumberFormatException e) {
                    Armory.getLogger().error("MLAModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                }
            }

            //Check if at least a base layer is found.
            if (base == null) {
                Armory.getLogger().error("Tried to load a MLAModel {} without a base layer.", modelLocation);
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new MultiLayeredArmorItemModel(MaterialRegistry.getInstance().getArmor(armorInternalName), builder.build(), base, parts, brokenParts);

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
