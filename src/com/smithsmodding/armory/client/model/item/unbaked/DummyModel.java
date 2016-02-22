package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Dummy model to be returned on the initial load to silence the missing model messages.
 * It's never actually used and gets replaced with the real models when the resource manager reloads.
 * <p/>
 * Thanks to TinkersConstruct as a resource of how to load multilayered objects.
 */
public class DummyModel implements IModel {

    public static final DummyModel INSTANCE = new DummyModel();

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ResourceLocation> getTextures () {
        return Collections.EMPTY_LIST;
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format,
                                     Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return ModelLoaderRegistry.getMissingModel().bake(ModelLoaderRegistry.getMissingModel().getDefaultState(), format, bakedTextureGetter);
    }

    @Override
    public IModelState getDefaultState () {
        return ModelLoaderRegistry.getMissingModel().getDefaultState();
    }
}
