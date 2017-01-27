package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class BakedMaterializedModel extends BakedWrappedModel.PerspectiveAware {

    private final ImmutableMap<IMaterial, IBakedModel> models;
    private final Overrides overrides = new Overrides();


    public BakedMaterializedModel(IBakedModel parentModel, ImmutableMap<IMaterial, IBakedModel> models, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations) {
        super(parentModel, transformations);
        this.models = models;
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

            if (!models.containsKey(capability.getMaterial()))
                return originalModel;

            return models.get(capability.getMaterial());
        }
    }
}
