package old.Common.Logic;
/*
*   ArmorRecipe
*   Created by: Orion
*   Created on: 4-4-2014
*/

import old.Common.ARegistry;
import old.Common.Armor.ArmorCore;
import old.Common.Armor.ArmorUpgrade;
import old.Common.Armor.Modifiers.ArmorModifier;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ArmorUpgradeRecipeHandler implements IRecipe
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

        ArmorUpgrade tNewUpgrade = ARegistry.iInstance.getUpgrade(pCraftingGrid);
        if (tNewUpgrade != null)
        {
            tNewUpgrades.add(tNewUpgrade);
            return ArmorBuilder.instance.buildArmor(pCraftingGrid.getStackInRowAndColumn(1,1), tNewUpgrades, tNewModifiers);
        }

        for(int tIter = 0; tIter < 9; tIter++){
            ItemStack pCurrentCraftingStack = pCraftingGrid.getStackInSlot(tIter);

            if ((pCurrentCraftingStack != null) && (!(pCurrentCraftingStack.getItem() instanceof ArmorCore)))
            {
                ArmorModifier tModifier = ARegistry.iInstance.getModifier(pCurrentCraftingStack);
                if (tModifier != null)
                {
                   tNewModifiers.add(tModifier);
                }
            }
        }

        if (tNewUpgrades.size() != 0 || tNewModifiers.size() != 0) {
            return ArmorBuilder.instance.buildArmor(pCraftingGrid.getStackInRowAndColumn(1, 1), tNewUpgrades, tNewModifiers);
        }

        return null;
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
