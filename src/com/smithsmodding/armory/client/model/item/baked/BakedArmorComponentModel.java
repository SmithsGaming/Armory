package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.armor.ISingleComponentItem;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @Author Marc (Created on: 12.06.2016)
 */
public class BakedArmorComponentModel extends BakedWrappedModel {

    private final ImmutableMap<String, BakedSubComponentModel> typeModels;
    private final Override overrides;

    public BakedArmorComponentModel(IBakedModel parentModel, ImmutableMap<String, BakedSubComponentModel> typeModels) {
        super(parentModel);
        this.typeModels = typeModels;
        overrides = new Override(this);
    }

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
            if (stack.getItem() instanceof ISingleComponentItem) {
                String id = ((ISingleComponentItem) stack.getItem()).getComponentTypeFromItemStack(stack);
                if (parent.typeModels.containsKey(id))
                    return parent.typeModels.get(id);

                return DummyModel.BAKED_MODEL;
            }
            return originalModel;
        }
    }
}
