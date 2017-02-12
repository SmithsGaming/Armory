package com.smithsmodding.armory.client.model.loaders;

import com.smithsmodding.armory.api.client.model.deserializers.MaterializedBlockModelDeserializer;
import com.smithsmodding.armory.api.client.model.deserializers.definition.MaterializedBlockModelDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.block.unbaked.MaterializedBlockModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/31/2017.
 */
public class MaterializedBlockModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".mbm-armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        modelLocation = ModelHelper.getModelLocation(modelLocation);

        try {
            MaterializedBlockModelDefinition definition = MaterializedBlockModelDeserializer.INSTANCE.deserialize(modelLocation);

            IModel parentModel = ModelLoaderRegistry.getModelOrLogError(definition.getCoreModel(), String.format("Could not load MaterializedModel %s. Parent model %s does not exist.", modelLocation, definition.getCoreModel()));
            MaterializedTextureCreator.registerBaseTexture(definition.getCoreTexture());

            return new MaterializedBlockModel((IRetexturableModel) parentModel, definition.getCoreTexture(), definition.getMaterialOverrides(), definition.getTransforms());
        } catch (Exception ex) {
            ModLogger.getInstance().error(String.format("Could not load {} as MaterializedBlockModel", modelLocation.toString()));
        }

        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
