package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.smithsmodding.armory.api.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.armory.api.events.client.model.item.MultiLayeredArmorModelTextureLoadEvent;
import com.smithsmodding.armory.api.references.ModLogger;
import com.smithsmodding.armory.client.model.item.unbaked.MultiLayeredArmorItemModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                ModLogger.getInstance().error("The given model: " + modelLocation.toString() + " Is not registered to any armor known to armory.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Load the default definition of the model as defined by the registrar first.
            MultiLayeredArmorModelDefinition definition = MultiLayeredArmorModelDeserializer.instance.deserialize(modelLocation);

            //Fire the TextureLoadEvent to allow third parties to add additional layers to the model if necessary
            MultiLayeredArmorModelTextureLoadEvent textureLoadEvent = new MultiLayeredArmorModelTextureLoadEvent(armor);
            textureLoadEvent.PostClient();

            //Combine the original with the added
            ImmutableMap.Builder<String, ResourceLocation> combineLayeredBuilder = new ImmutableMap.Builder<>();
            ImmutableMap.Builder<String, ResourceLocation> combineBrokenBuilder = new ImmutableMap.Builder<>();
            ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> transformBuilder = new ImmutableMap.Builder<>();

            ResourceLocation baseLocation = definition.getBaseLocation();
            combineLayeredBuilder.putAll(definition.getLayerLocations());
            combineBrokenBuilder.putAll(definition.getBrokenLocations());
            transformBuilder.putAll(definition.getTransforms());
            for (MultiLayeredArmorModelDefinition subDef : textureLoadEvent.getAdditionalTextureLayers()) {
                combineLayeredBuilder.putAll(subDef.getLayerLocations());
                combineBrokenBuilder.putAll(subDef.getBrokenLocations());
                transformBuilder.putAll(subDef.getTransforms());

                if (subDef.getBaseLocation() != null)
                    baseLocation = subDef.getBaseLocation();
            }
            definition = new MultiLayeredArmorModelDefinition(baseLocation, combineLayeredBuilder.build(), combineBrokenBuilder.build(), transformBuilder.build());

            if (definition.getBaseLocation() == null)
                throw new IllegalArgumentException("The given model does not have a Base assigned.");

            //Create the final list builder.
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            //Define the model structure components.
            ArmorSubComponentModel base = null;
            HashMap<String, ArmorSubComponentModel> parts = new HashMap<String, ArmorSubComponentModel>();
            HashMap<String, ArmorSubComponentModel> brokenParts = new HashMap<String, ArmorSubComponentModel>();

            ResourceLocation location = null;
            ArmorSubComponentModel partModel = null;
            String name = "";

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * layer (Component texture used when the armor is not broken)
            //    * broken (Component texture used when the armor is broken)
            //    * base (The base layer of a armor (in case of MedievalArmor it is the chain base layer texture))

            //Base layer
            try {
                name = "Base";
                location = definition.getBaseLocation();
                partModel = new ArmorSubComponentModel(ImmutableList.of(location));
                base = partModel;
                if (location != null)
                    builder.add(location);


                for (Map.Entry<String, ResourceLocation> entry : definition.getLayerLocations().entrySet()) {
                    name = entry.getKey();

                    location = entry.getValue();
                    partModel = new ArmorSubComponentModel(ImmutableList.of(location));

                    parts.put(location.toString(), partModel);

                    if (location != null) {
                        builder.add(location);
                    }
                }

                for (Map.Entry<String, ResourceLocation> entry : definition.getBrokenLocations().entrySet()) {
                    name = entry.getKey();

                    location = entry.getValue();
                    partModel = new ArmorSubComponentModel(ImmutableList.of(location));

                    brokenParts.put(location.toString(), partModel);

                    if (location != null) {
                        builder.add(location);
                    }
                }
            } catch (Exception ex) {
                ModLogger.getInstance().error(String.format("MLAModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name));
            }

            //Check if at least a base layer is found.
            if (base == null) {
                ModLogger.getInstance().error(String.format("Tried to load a MLAModel {} without a base layer.", modelLocation));
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new MultiLayeredArmorItemModel(MaterialRegistry.getInstance().getArmor(armorInternalName), builder.build(), base, parts, brokenParts, ImmutableMap.copyOf(definition.getTransforms()));

            // Load all textures we need in to the creator.
            MaterializedTextureCreator.registerBaseTexture(builder.build());

            return output;
        } catch (IOException e) {
            ModLogger.getInstance().error(String.format("Could not load multimodel {}", modelLocation.toString()));
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }
}
