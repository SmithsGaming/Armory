package com.Orion.Armory.Common.Logic;
/*
*   ArmorRecipe
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ArmorChangeRecipe implements IRecipe
{


    @Override
    public boolean matches(InventoryCrafting pCraftingGrid, World pWorld) {
        if (this.getCraftingResult(pCraftingGrid) != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting pCraftingGrid) {
        ArrayList<ArmorUpgrade> tNewUpgrades = new ArrayList<ArmorUpgrade>();
        ArrayList<ArmorModifier> tNewModifiers = new ArrayList<ArmorModifier>();

        if ((pCraftingGrid.getStackInRowAndColumn(1,1) == null) || (!(pCraftingGrid.getStackInRowAndColumn(1,1).getItem() instanceof ArmorCore)))
        {
            return null;
        }

        for(int tIter = 0; tIter < 9; tIter++){
            ItemStack pCurrentCraftingStack = pCraftingGrid.getStackInSlot(tIter);

            if ((pCurrentCraftingStack != null) && (!(pCurrentCraftingStack.getItem() instanceof ArmorCore)))
            {
                ArmorUpgrade tUpgrade = ARegistry.iInstance.getUpgrade(pCurrentCraftingStack);
                if (tUpgrade != null) {
                    tNewUpgrades.add(tUpgrade);
                }
                else
                {
                    ArmorModifier tModifier = ARegistry.iInstance.getModifier(pCurrentCraftingStack);
                    if (tModifier != null)
                    {
                        tNewModifiers.add(tModifier);
                    }
                    else
                    {
                        return null;
                    }
                }

            }
        }

        return ArmorBuilder.instance.buildArmor(pCraftingGrid.getStackInRowAndColumn(1,1), tNewUpgrades, tNewModifiers);
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
