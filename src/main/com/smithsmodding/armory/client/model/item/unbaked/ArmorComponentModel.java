package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.client.model.item.baked.BakedArmorComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Map;


/**
 * Author Marc (Created on: 12.06.2016)
 */
public class ArmorComponentModel extends ItemLayerModel {

    private final ImmutableMap<String, ResourceLocation> textures;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public ArmorComponentModel(ImmutableMap<String, ResourceLocation> textures, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.copyOf(textures.values()));
        this.textures = textures;
        this.transforms = transforms;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<String, BakedSubComponentModel> componentBuilder = new ImmutableMap.Builder();

        for (Map.Entry<String, ResourceLocation> entry : textures.entrySet()) {
            ImmutableList.Builder<ResourceLocation> textureBuilder = new ImmutableList.Builder<>();
            textureBuilder.add(entry.getValue());

            componentBuilder.put(entry.getKey(), (BakedSubComponentModel) new ArmorSubComponentModel(textureBuilder.build(), transforms).bake(state, format, bakedTextureGetter));
        }

        return new BakedArmorComponentModel(super.bake(state, format, bakedTextureGetter), componentBuilder.build(), transforms);
    }
}
