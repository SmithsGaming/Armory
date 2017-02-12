package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.events.client.model.item.MultiLayeredArmorModelTextureLoadEvent;
import com.smithsmodding.armory.api.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.smithsmodding.armory.api.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.item.unbaked.MultiLayeredArmorItemModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorAddonComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorCoreComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.api.ArmoryAPI;
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

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".MLA-Armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // MLA armory extension. Foo.MLA-armory.json
    }

    @Override
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Retrieve the Name of the armor.
            //The file name without the extension has to be equal to the Name used in Armories registry.
            IMultiComponentArmor armor = ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorRegistry().getValue(modelLocation);

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
            ImmutableMap.Builder<ResourceLocation, ResourceLocation> combineLayeredBuilder = new ImmutableMap.Builder<>();
            ImmutableMap.Builder<ResourceLocation, ResourceLocation> combineBrokenBuilder = new ImmutableMap.Builder<>();
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
            ArmorCoreComponentModel base = null;
            HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> parts = new HashMap<>();
            HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenParts = new HashMap<>();

            base = new ArmorCoreComponentModel(ImmutableList.of(definition.getBaseLocation()));
            builder.add(definition.getBaseLocation());

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * layer (Component texture used when the armor is not broken)
            //    * broken (Component texture used when the armor is broken)
            //    * base (The base layer of a armor (in case of MedievalArmor it is the chain base layer texture))

            //Base layer
            try {
                //Process each layer both as whole and as broken.
                definition.getLayerLocations().forEach(new ModelMappingProcessingConsumer(modelLocation, parts, builder));
                definition.getBrokenLocations().forEach(new ModelMappingProcessingConsumer(modelLocation, brokenParts, builder));
            } catch (Exception ex) {
                ModLogger.getInstance().error(String.format("MLAModel {0} has invalid texture entry; Skipping layer.", modelLocation));
            }

            //Check if at least a base layer is found.
            if (base == null) {
                ModLogger.getInstance().error(String.format("Tried to load a MLAModel {0} without a base layer.", modelLocation));
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new MultiLayeredArmorItemModel(armor, builder.build(), base, parts, brokenParts, ImmutableMap.copyOf(definition.getTransforms()));

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
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    private class ModelMappingProcessingConsumer implements BiConsumer<ResourceLocation, ResourceLocation> {

        @Nonnull
        private final ResourceLocation modelLocation;

        @Nonnull
        private final HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> modelMap;

        @Nonnull
        private final ImmutableList.Builder<ResourceLocation> builder;

        public ModelMappingProcessingConsumer(@Nonnull ResourceLocation modelLocation, @Nonnull HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> modeMap, @Nonnull ImmutableList.Builder<ResourceLocation> builder) {
            this.modelLocation = modelLocation;
            this.modelMap = modeMap;
            this.builder = builder;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param extensionName  the first input argument
         * @param texture the second input argument
         */
        @Override
        public void accept(ResourceLocation extensionName, ResourceLocation texture) {
            IMultiComponentArmorExtension extension = ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(extensionName);
            if (extension == null) {
                ModLogger.getInstance().warn(String.format("Attempted to load model: {0}, with a layer: {1}, that has no registered Extension!", modelLocation, extensionName));
                return;
            }

            ArmorSubComponentModel componentModel;
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension) {
                componentModel = new ArmorAddonComponentModel(ImmutableList.of(texture));
            } else {
                componentModel = new ArmorSubComponentModel(ImmutableList.of(texture));
            }

            modelMap.put(extension, componentModel);
            builder.add(texture);
        }
    }

}
