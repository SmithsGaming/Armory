package com.smithsmodding.Armory.Common.Factory;
/*
/  IMLAFactory
/  Created by : Orion
/  Created on : 03/07/2014
*/

import com.smithsmodding.Armory.API.Armor.*;
import net.minecraft.item.*;

import java.util.*;

public interface IMLAFactory {
    /**
     * Function used to modify an existing MLA.
     * The function will add the Addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factory can take different environmental values in effect.
     *
     * @param pBaseArmor     The base armor used to create the new Armor.
     * @param pBaseItemStack The already existing ItemStack on to which Addons should be added
     * @param pNewAddons     The new addons stored in a HashMap, with as key the new Addons and as Value the new installed amount.
     * @param pData          Extra data for your factory
     * @return An Itemstack containing your now modified armor.
     */
    ItemStack buildMLAArmor(MultiLayeredArmor pBaseArmor, ItemStack pBaseItemStack, HashMap<MLAAddon, Integer> pNewAddons, Integer pNewTotalDurability, String iInternalMaterialName, Object... pData);

    /**
     * Function used to create a new Armor ItemStack
     *
     * @param pBaseArmor The base armor used to create the new Armor.
     * @param pAddons    The new addons stored in a ArrayList
     * @param pData      Extra data for your factory
     * @return A new ItemStack with full durability
     */
    ItemStack buildNewMLAArmor(MultiLayeredArmor pBaseArmor, HashMap<MLAAddon, Integer> pAddons, Integer pTotalDurability, String iInternalMaterialName, Object... pData);

    String getArmorGivenName(ItemStack pStack);
}
