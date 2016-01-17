package com.smithsmodding.Armory.API.Item;

import net.minecraft.item.*;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * Interface declaring an Item that is made up out of a single Material
 * A good example are the Components of Armor Upgrade.
 */
public interface ISingleMaterialItem {

    String getMaterialInternalName (ItemStack stack);
}
