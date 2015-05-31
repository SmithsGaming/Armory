package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
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
    private String iMaterialName;

    public HeatedAnvilRecipeComponent(String pInternalName, String pInternalType, float pMinTemp, float pMaxTemp)
    {
        iMaterialName = pInternalName;
        iInternalType = pInternalType;

        iMinTemp = pMinTemp;
        iMaxTemp = pMaxTemp;
    }

    @Override
    public ItemStack getComponentTargetStack() {
        return HeatedItemFactory.getInstance().generateHeatedItem(iMaterialName, iInternalType, ((iMinTemp + iMaxTemp) / 2));
    }

    @Override
    public HeatedAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        if (!(pNewTargetStack.getItem() instanceof ItemHeatedItem))
        {
            GeneralRegistry.iLogger.error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString( pNewTargetStack));
        }

        iMaterialName = HeatedItemFactory.getInstance().getMaterialIDFromItemStack(pNewTargetStack);
        iInternalType = HeatedItemFactory.getInstance().getType(pNewTargetStack);

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        return 0;
    }

    @Override
    public HeatedAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        return this;
    }

    @Override
    public boolean isValidComponentForSlot(ItemStack pComparedItemStack) {
        if (pComparedItemStack == null)
            return false;

        if (!(pComparedItemStack.getItem() instanceof ItemHeatedItem))
        {
            return false;
        }

        return ((iInternalType.equals(HeatedItemFactory.getInstance().getType(pComparedItemStack))) && (iMaterialName.equals(HeatedItemFactory.getInstance().getMaterialIDFromItemStack(pComparedItemStack))) && ((iMinTemp <= ItemHeatedItem.getItemTemperature(pComparedItemStack)) && (iMaxTemp >= ItemHeatedItem.getItemTemperature(pComparedItemStack))));
        }
        }
