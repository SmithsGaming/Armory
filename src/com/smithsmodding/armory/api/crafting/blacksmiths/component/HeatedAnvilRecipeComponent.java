package com.smithsmodding.armory.api.crafting.blacksmiths.component;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p/>
 * Copyrighted according to Project specific license
 */
public class HeatedAnvilRecipeComponent implements IAnvilRecipeComponent {

    private float iMinTemp;
    private float iMaxTemp;

    private String iInternalType;
    private String iMaterialName;

    public HeatedAnvilRecipeComponent(String pInternalName, String pInternalType, float pMinTemp, float pMaxTemp) {
        iMaterialName = pInternalName;
        iInternalType = pInternalType;

        iMinTemp = pMinTemp;
        iMaxTemp = pMaxTemp;
    }

    @Override
    public ItemStack getComponentTargetStack() {
        return HeatedItemFactory.getInstance().generateHeatedItem(MaterialRegistry.getInstance().getMaterial(iMaterialName), iInternalType, ((iMinTemp + iMaxTemp) / 2));
    }

    @Override
    public HeatedAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        if (!(pNewTargetStack.getItem() instanceof ItemHeatedItem)) {
            Armory.getLogger().error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString(pNewTargetStack));
        }

        iMaterialName = HeatableItemRegistry.getInstance().getMaterialFromStack(pNewTargetStack).getUniqueID();
        iInternalType = HeatableItemRegistry.getInstance().getInternalTypeFromItemStack(pNewTargetStack).get(0);

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

        if (!(pComparedItemStack.getItem() instanceof ItemHeatedItem)) {
            return false;
        }

        return ((iInternalType.equals(HeatableItemRegistry.getInstance().getInternalTypeFromItemStack(pComparedItemStack).get(0))) && (iMaterialName.equals(HeatableItemRegistry.getInstance().getMaterialFromStack(pComparedItemStack).getUniqueID())) && ((iMinTemp <= HeatableItemRegistry.getInstance().getItemTemperature(pComparedItemStack))) && (iMaxTemp >= HeatableItemRegistry.getInstance().getItemTemperature(pComparedItemStack)));
    }
}
