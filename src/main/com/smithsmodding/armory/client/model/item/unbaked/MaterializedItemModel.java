package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.client.model.item.baked.BakedMaterializedModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Map;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModel extends ItemLayerModel {

    private final ResourceLocation coreTexture;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MaterializedItemModel(ResourceLocation coreTexture, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.of(coreTexture));
        this.coreTexture = coreTexture;
        this.transforms = transforms;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IBakedModel parent = super.bake(state, format, bakedTextureGetter);

        ImmutableMap.Builder<IArmorMaterial, IBakedModel> builder = new ImmutableMap.Builder<>();
        Map<String, TextureAtlasSprite> materializedTextures = MaterializedTextureCreator.getBuildSprites().get(parent.getParticleTexture().getIconName());

        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            builder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getUniqueID()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        return new BakedMaterializedModel(parent, builder.build(), transforms);
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
