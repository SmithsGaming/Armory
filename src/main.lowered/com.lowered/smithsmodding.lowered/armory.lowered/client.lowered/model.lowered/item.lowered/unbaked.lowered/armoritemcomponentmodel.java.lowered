package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.client.model.item.baked.BakedArmorComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorComponentModel;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;


/**
 * Author Marc (Created on: 12.06.2016)
 */
public class ArmorItemComponentModel extends ItemLayerModel {

    @Nonnull
    private final ImmutableMap<ResourceLocation, ResourceLocation> textures;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public ArmorItemComponentModel(@Nonnull ImmutableMap<ResourceLocation, ResourceLocation> textures, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.copyOf(textures.values()));
        this.textures = textures;
        this.transforms = transforms;
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<IMultiComponentArmorExtension, BakedComponentModel> componentBuilder = new ImmutableMap.Builder();

        textures.forEach((extensionName, texture) -> {
            IMultiComponentArmorExtension extension = ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(extensionName);
            if (extension == null)
                return;

            if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
                extension = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterialIndependentExtension();

            componentBuilder.put(extension, new ArmorComponentModel(ImmutableList.of(texture), transforms).generateBackedComponentModel(state, format, bakedTextureGetter));
        });

        return new BakedArmorComponentModel(super.bake(state, format, bakedTextureGetter), componentBuilder.build(), transforms);
    }
}
