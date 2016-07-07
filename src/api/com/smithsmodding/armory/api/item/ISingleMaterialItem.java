package com.smithsmodding.armory.api.item;

import net.minecraft.item.*;

/**
 * Created by Marc on 06.12.2015.
 *
 * Interface declaring an item that is made up out of a single material
 * A good example are the Components of armor Upgrade.
 */
public interface ISingleMaterialItem {

    String getMaterialInternalName (ItemStack stack);
}
