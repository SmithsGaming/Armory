package com.smithsmodding.armory.api.factories;
/*
/  IMLAFactory
/  Created by : Orion
/  Created on : 03/07/2014
*/

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public interface IMLAFactory {
    /**
     * Function used to modify an existing MLA.
     * The function will add the addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factories can take different environmental values in effect.
     *
     * @param armor     The base armor used to create the new armor.
     * @param baseStack The already existing ItemStack on to which addons should be added
     * @param newAddons     The new addons stored in a HashMap, with as key the new addons and as Value the new installed amount.
     * @param data          Extra data for your factories
     * @return An Itemstack containing your now modified armor.
     */
    @Nonnull ItemStack buildMLAArmor(IMultiComponentArmor armor, ItemStack baseStack, ArrayList<IMultiComponentArmorExtensionInformation> newAddons, Integer newTotalDurability, ICoreArmorMaterial coreMaterial, Object... data);

    /**
     * Function used to create a new armor ItemStack
     *
     * @param armor The base armor used to create the new armor.
     * @param addons    The new addons stored in a ArrayList
     * @param data      Extra data for your factories
     * @return A new ItemStack with full getDurability
     * @throws IllegalArgumentException When the Addons are not compatible.
     */
    @Nonnull ItemStack buildNewMLAArmor(IMultiComponentArmor armor, ArrayList<IMultiComponentArmorExtensionInformation> addons, Integer totalDurability, ICoreArmorMaterial coreMaterial, Object... data) throws IllegalArgumentException;

    @Nullable
    String getArmorGivenName(ItemStack stack);
}
