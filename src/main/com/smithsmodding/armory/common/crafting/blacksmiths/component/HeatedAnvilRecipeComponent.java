package com.smithsmodding.armory.common.crafting.blacksmiths.component;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.references.ModLogger;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 *
 * Copyrighted according to Project specific license
 */
public class HeatedAnvilRecipeComponent implements IAnvilRecipeComponent {

    private float minTemp;
    private float maxTemp;

    private String internalType;
    private String materialName;

    public HeatedAnvilRecipeComponent(String pInternalName, String pInternalType, float pMinTemp, float pMaxTemp) {
        materialName = pInternalName;
        internalType = pInternalType;

        minTemp = pMinTemp;
        maxTemp = pMaxTemp;
    }

    @Override
    public ItemStack getComponentTargetStack() {
        return HeatedItemFactory.getInstance().generateHeatedItem(MaterialRegistry.getInstance().getMaterial(materialName), internalType, ((minTemp + maxTemp) / 2));
    }

    @Override
    public HeatedAnvilRecipeComponent setComponentTargetStack(ItemStack pNewTargetStack) {
        if (!(pNewTargetStack.getItem() instanceof ItemHeatedItem)) {
            ModLogger.getInstance().error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString(pNewTargetStack));
        }

        materialName = HeatableItemRegistry.getInstance().getMaterialFromStack(pNewTargetStack).getUniqueID();
        internalType = HeatableItemRegistry.getInstance().getInternalTypeFromItemStack(pNewTargetStack).get(0);

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

        return ((internalType.equals(HeatableItemRegistry.getInstance().getInternalTypeFromItemStack(pComparedItemStack).get(0))) && (materialName.equals(HeatableItemRegistry.getInstance().getMaterialFromStack(pComparedItemStack).getUniqueID())) && ((minTemp <= HeatableItemRegistry.getInstance().getItemTemperature(pComparedItemStack))) && (maxTemp >= HeatableItemRegistry.getInstance().getItemTemperature(pComparedItemStack)));
    }
}
