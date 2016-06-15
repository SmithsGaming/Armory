package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.client.model.item.baked.BakedArmorComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

import java.util.Map;


/**
 * @Author Marc (Created on: 12.06.2016)
 */
public class ArmorComponentModel extends ItemLayerModel {

    private final ImmutableMap<String, ResourceLocation> textures;

    public ArmorComponentModel(ImmutableMap<String, ResourceLocation> textures) {
        super(ImmutableList.copyOf(textures.values()));
        this.textures = textures;
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<String, BakedSubComponentModel> componentBuilder = new ImmutableMap.Builder();

        for (Map.Entry<String, ResourceLocation> entry : textures.entrySet()) {
            ImmutableList.Builder<ResourceLocation> textureBuilder = new ImmutableList.Builder<>();
            textureBuilder.add(entry.getValue());

            componentBuilder.put(entry.getKey(), (BakedSubComponentModel) new ArmorSubComponentModel(textureBuilder.build()).bake(state, format, bakedTextureGetter));
        }

        return new BakedArmorComponentModel(super.bake(state, format, bakedTextureGetter), componentBuilder.build());
    }
}
