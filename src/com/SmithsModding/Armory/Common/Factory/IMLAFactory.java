package com.smithsmodding.armory.common.factory;
/*
/  IMLAFactory
/  Created by : Orion
/  Created on : 03/07/2014
*/

import com.smithsmodding.armory.api.armor.*;
import net.minecraft.item.*;

import java.util.*;

public interface IMLAFactory {
    /**
     * Function used to modify an existing MLA.
     * The function will add the addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factory can take different environmental values in effect.
     *
     * @param pBaseArmor     The base armor used to create the new armor.
     * @param pBaseItemStack The already existing ItemStack on to which addons should be added
     * @param pNewAddons     The new addons stored in a HashMap, with as key the new addons and as Value the new installed amount.
     * @param pData          Extra data for your factory
     * @return An Itemstack containing your now modified armor.
     */
    ItemStack buildMLAArmor(MultiLayeredArmor pBaseArmor, ItemStack pBaseItemStack, HashMap<MLAAddon, Integer> pNewAddons, Integer pNewTotalDurability, String iInternalMaterialName, Object... pData);

    /**
     * Function used to create a new armor ItemStack
     *
     * @param pBaseArmor The base armor used to create the new armor.
     * @param pAddons    The new addons stored in a ArrayList
     * @param pData      Extra data for your factory
     * @return A new ItemStack with full durability
     */
    ItemStack buildNewMLAArmor(MultiLayeredArmor pBaseArmor, HashMap<MLAAddon, Integer> pAddons, Integer pTotalDurability, String iInternalMaterialName, Object... pData);

    String getArmorGivenName(ItemStack pStack);
}
