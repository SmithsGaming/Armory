package com.smithsmodding.armory.client.model.block.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.client.model.block.baked.MaterializedBlockBakedModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedBlockModel implements IModel {

    private final IRetexturableModel parent;
    private final ResourceLocation materializableTexture;
    private final Map<ResourceLocation, ResourceLocation> materialOverrides;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations;

    public MaterializedBlockModel(IRetexturableModel parent, ResourceLocation materializableTexture, Map<ResourceLocation, ResourceLocation> materialOverrides, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations) {
        this.parent = parent;
        this.materializableTexture = materializableTexture;
        this.materialOverrides = materialOverrides;
        this.transformations = transformations;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.singleton(materializableTexture);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<IMaterial, IBakedModel> subModelBuilder = new ImmutableMap.Builder<>();

        Map<ResourceLocation, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(materializableTexture);

        for(RegistryMaterialWrapper materialWrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()) {
            if (!sprites.containsKey(materialWrapper.getRegistryName()) && !materialOverrides.containsKey(materialWrapper.getRegistryName())) {
                continue;
            }

            TextureAtlasSprite retexturedSprite;
            if (!materialOverrides.containsKey(materialWrapper.getRegistryName())) {
                retexturedSprite = sprites.get(materialWrapper.getRegistryName());
            } else {
                retexturedSprite = bakedTextureGetter.apply(materialOverrides.get(materialWrapper.getRegistryName()));
            }

            if (retexturedSprite == null)
                retexturedSprite = bakedTextureGetter.apply(TextureMap.LOCATION_MISSING_TEXTURE);

            subModelBuilder.put(materialWrapper.getWrapped(), parent.retexture(getRetextureMap(retexturedSprite.getIconName())).bake(state, format, bakedTextureGetter));
        }

        return new MaterializedBlockBakedModel(parent.retexture(getRetextureMap(bakedTextureGetter.apply(materializableTexture).getIconName())).bake(state, format, bakedTextureGetter), transformations, subModelBuilder.build());
    }

    private ImmutableMap<String, String> getRetextureMap(String newTexture) {
        ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

        builder.put("all", newTexture);
        builder.put("up", newTexture);
        builder.put("down", newTexture);
        builder.put("north", newTexture);
        builder.put("south", newTexture);
        builder.put("west", newTexture);
        builder.put("east", newTexture);

        return builder.build();
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_BLOCK_STATE;
    }
}
