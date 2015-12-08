package com.SmithsModding.Armory.Client.Model.Item.Baked.Components;

import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraftforge.client.model.*;

/**
 * Created by Marc on 08.12.2015.
 */
public abstract class MultiTextureBakedComponentModelLayer extends IFlexibleBakedModel.Wrapper implements ISmartItemModel {
    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public MultiTextureBakedComponentModelLayer (IFlexibleBakedModel base) {
        super(base, base.getFormat());
    }


    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        return null;
    }
}