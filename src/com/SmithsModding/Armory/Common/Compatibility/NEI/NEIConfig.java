package com.SmithsModding.Armory.Common.Compatibility.NEI;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 30.05.2015
 * 14:28
 * <p/>
 * Copyrighted according to Project specific license
 */
public class NEIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        ArmorsAnvilNEIHandler tHandler = new ArmorsAnvilNEIHandler();

        API.registerRecipeHandler(tHandler);
        API.registerUsageHandler(tHandler);

        ArrayList<ItemStack> tHeatableStacks = new ArrayList<ItemStack>();
        GeneralRegistry.Items.iHeatedIngot.getSubItems(null, null, tHeatableStacks);

        for (ItemStack tStack : tHeatableStacks) {
            API.hideItem(tStack);
        }
    }

    @Override
    public String getName() {
        return References.General.MOD_ID;
    }

    @Override
    public String getVersion() {
        return References.General.VERSION;
    }
}
