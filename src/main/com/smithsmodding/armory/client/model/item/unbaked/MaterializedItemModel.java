package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.client.model.item.baked.BakedMaterializedModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModel extends ItemLayerModel {

    @Nonnull
    private final ResourceLocation coreTexture;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MaterializedItemModel(@Nonnull ResourceLocation coreTexture, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.of(coreTexture));
        this.coreTexture = coreTexture;
        this.transforms = transforms;
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IBakedModel parent = super.bake(state, format, bakedTextureGetter);

        ImmutableMap.Builder<IMaterial, IBakedModel> modelBuilder = new ImmutableMap.Builder<>();

        Map<ResourceLocation, TextureAtlasSprite> materializedTextures = MaterializedTextureCreator.getBuildSprites().get(parent.getParticleTexture().getIconName());

        for (ICoreArmorMaterial material : ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getRegistryName()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        for (IAddonArmorMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getRegistryName()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        for (IAnvilMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getRegistryName()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        return new BakedMaterializedModel(parent, modelBuilder.build(), transforms);
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
