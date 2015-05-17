package com.Orion.Armory.Common.Crafting;
/*
 *   ChainCraftingRecipe
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.Orion.Armory.Common.Item.ItemMetalRing;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ChainCraftingRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting pCraftingGrid, World pWorld) {
        for (int IColumIter = 0; IColumIter < 3; IColumIter++)
        {
            if ((pCraftingGrid.getStackInRowAndColumn(IColumIter, 0) == null) || (pCraftingGrid.getStackInRowAndColumn(IColumIter, 1) == null) || (pCraftingGrid.getStackInRowAndColumn(IColumIter, 2) == null)) continue;

            if (!(pCraftingGrid.getStackInRowAndColumn(IColumIter, 0).getItem() instanceof ItemMetalRing) || !(pCraftingGrid.getStackInRowAndColumn(IColumIter, 1).getItem() instanceof ItemMetalRing) || !(pCraftingGrid.getStackInRowAndColumn(IColumIter, 2).getItem() instanceof ItemMetalRing)) continue;

            String tMaterialUpper = pCraftingGrid.getStackInRowAndColumn(IColumIter, 0).getTagCompound().getString(References.NBTTagCompoundData.Material);
            String tMaterialMiddle = pCraftingGrid.getStackInRowAndColumn(IColumIter, 1).getTagCompound().getString(References.NBTTagCompoundData.Material);
            String tMaterialLower = pCraftingGrid.getStackInRowAndColumn(IColumIter, 2).getTagCompound().getString(References.NBTTagCompoundData.Material);

            if (tMaterialUpper.equals(tMaterialMiddle) && tMaterialUpper.equals(tMaterialLower)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting pCraftingGrid) {
        for (int IColumIter = 0; IColumIter < 3; IColumIter++)
        {
            if ((pCraftingGrid.getStackInRowAndColumn(IColumIter, 0) == null) || (pCraftingGrid.getStackInRowAndColumn(IColumIter, 1) == null) || (pCraftingGrid.getStackInRowAndColumn(IColumIter, 2) == null)) continue;

            if (!(pCraftingGrid.getStackInRowAndColumn(IColumIter, 0).getItem() instanceof ItemMetalRing) || !(pCraftingGrid.getStackInRowAndColumn(IColumIter, 1).getItem() instanceof ItemMetalRing) || !(pCraftingGrid.getStackInRowAndColumn(IColumIter, 2).getItem() instanceof ItemMetalRing)) continue;

            String tMaterialUpper = pCraftingGrid.getStackInRowAndColumn(IColumIter, 0).getTagCompound().getString(References.NBTTagCompoundData.Material);
            String tMaterialMiddle = pCraftingGrid.getStackInRowAndColumn(IColumIter, 1).getTagCompound().getString(References.NBTTagCompoundData.Material);
            String tMaterialLower = pCraftingGrid.getStackInRowAndColumn(IColumIter, 2).getTagCompound().getString(References.NBTTagCompoundData.Material);

            if (tMaterialUpper.equals(tMaterialMiddle) && tMaterialUpper.equals(tMaterialLower)) {
                ItemStack tReturnStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1);
                tReturnStack.setTagCompound(pCraftingGrid.getStackInRowAndColumn(IColumIter, 0).getTagCompound());

                return tReturnStack;
            }
        }

        return null;
    }

    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
