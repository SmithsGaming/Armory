package com.smithsmodding.armory.client.model.block.baked;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.common.block.BlockHeatableResource;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by marcf on 1/31/2017.
 */
public class MaterializedBlockBakedModel extends BakedWrappedModel.PerspectiveAware {

    private final Overrides overrides = new Overrides();
    private final Map<IMaterial, IBakedModel> bakedMaterializedModels;

    public MaterializedBlockBakedModel(@Nonnull IBakedModel parentModel, @Nonnull ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations, Map<IMaterial, IBakedModel> bakedMaterializedModels) {
        super(parentModel, transformations);
        this.bakedMaterializedModels = bakedMaterializedModels;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        IMaterial material = ((IExtendedBlockState) state).getValue(BlockHeatableResource.PROPERTY_HEATABLE_MATERIAL);
        if (material != null && bakedMaterializedModels.containsKey(material))
            return bakedMaterializedModels.get(material).getQuads(state, side, rand);

        return super.getQuads(state, side, rand);
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    public class Overrides extends ItemOverrideList {

        public Overrides() {
            super(new ArrayList<>());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (!stack.hasCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null))
                return originalModel;

            IMaterializedStackCapability capability = stack.getCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null);

            if (!bakedMaterializedModels.containsKey(capability.getMaterial()))
                return originalModel;

            return bakedMaterializedModels.get(capability.getMaterial());
        }
    }
}
