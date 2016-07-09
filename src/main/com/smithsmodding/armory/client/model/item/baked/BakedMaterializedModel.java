package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.ArrayList;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class BakedMaterializedModel extends BakedWrappedModel.PerspectiveAware {

    private final ImmutableMap<IArmorMaterial, IBakedModel> childModels;
    private final Overrides overrides = new Overrides();


    public BakedMaterializedModel(IBakedModel parentModel, ImmutableMap<IArmorMaterial, IBakedModel> childModels, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations) {
        super(parentModel, transformations);
        this.childModels = childModels;
    }

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
            if (!stack.hasTagCompound())
                return originalModel;

            if (!stack.getTagCompound().hasKey(References.NBTTagCompoundData.Material))
                return originalModel;

            IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.Material));

            if (material == null)
                return originalModel;

            if (!childModels.containsKey(material))
                return originalModel;

            return childModels.get(material);
        }
    }
}
