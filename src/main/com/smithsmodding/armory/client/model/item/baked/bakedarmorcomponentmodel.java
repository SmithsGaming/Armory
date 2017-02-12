package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMaterializableMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.client.model.item.baked.components.BakedComponentModel;
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
 * Author Marc (Created on: 12.06.2016)
 */
public class BakedArmorComponentModel extends BakedWrappedModel.PerspectiveAware {

    private final ImmutableMap<IMultiComponentArmorExtension, BakedComponentModel> typeModels;

    @Nonnull
    private final Override overrides;

    public BakedArmorComponentModel(IBakedModel parentModel, ImmutableMap<IMultiComponentArmorExtension, BakedComponentModel> typeModels, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(parentModel, transforms);
        this.typeModels = typeModels;
        overrides = new Override(this);
    }

    @Nonnull
    @java.lang.Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static class Override extends ItemOverrideList {

        private final BakedArmorComponentModel parent;

        public Override(BakedArmorComponentModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @java.lang.Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (!stack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
                return originalModel;

            IArmorComponentStackCapability extensionCapability = stack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null);

            IMultiComponentArmorExtension extension = extensionCapability.getExtension();
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension) {
                extension = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterialIndependentExtension();
            }

            if (parent.typeModels.containsKey(extension)) {
                IBakedModel returnModel = parent.typeModels.get(extension);

                if (extension instanceof IMaterializableMultiComponentArmorExtension) {
                    returnModel = ((BakedComponentModel) returnModel).getModelByIdentifier(((IMaterialDependantMultiComponentArmorExtension) extensionCapability.getExtension()).getMaterial().getRegistryName());
                }

                return returnModel;
            }

            return originalModel;
        }
    }
}
