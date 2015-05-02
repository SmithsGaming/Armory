package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p/>
 * Copyrighted according to Project specific license
 */
public class HeatedAnvilRecipeComponent implements IAnvilRecipeComponent
{

    private float iMinTemp;
    private float iMaxTemp;

    private String iInternalType;
    private String iInternalName;

    HeatedAnvilRecipeComponent(String pInternalName, String pInternalType, float pMinTemp, float pMaxTemp)
    {
        iInternalName = pInternalName;
        iInternalType = pInternalType;

        iMinTemp = pMinTemp;
        iMaxTemp = pMaxTemp;
    }

    @Override
    public ItemStack getComponentTargetStack() {
        return null;
    }

    @Override
    public void setComponentTargetStack(ItemStack pNewTargetStack) {
        if (!(pNewTargetStack.getItem() instanceof ItemHeatedItem))
        {
            GeneralRegistry.iLogger.error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString( pNewTargetStack));
        }

        iInternalName = HeatedItemFactory.getInstance().getMaterialIDFromItemStack(pNewTargetStack);
        iInternalType = HeatedItemFactory.getInstance().getType(pNewTargetStack);
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        return 1;
    }

    @Override
    public void setComponentStackUsage(int pNewUsage) {
        return;
    }

    @Override
    public boolean isValidComponentForSlot(ItemStack pComparedItemStack) {
        if (!(pComparedItemStack.getItem() instanceof ItemHeatedItem))
        {
            return false;
        }

        return (((iInternalType == HeatedItemFactory.getInstance().getType(pComparedItemStack)) && (iInternalName == HeatedItemFactory.getInstance().getMaterialIDFromItemStack(pComparedItemStack))) && ((iMinTemp <= ItemHeatedItem.getItemTemperature(pComparedItemStack)) && (iMaxTemp >= ItemHeatedItem.getItemTemperature(pComparedItemStack))));
    }
}
