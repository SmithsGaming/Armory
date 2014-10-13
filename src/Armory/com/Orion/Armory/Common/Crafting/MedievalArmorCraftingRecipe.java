package com.Orion.Armory.Common.Crafting;
/*
 *   ArmorCraftingRecipe
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.Orion.Armory.Common.Item.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.Common.Item.ItemMetalChain;
import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.HashMap;

public class MedievalArmorCraftingRecipe implements IRecipe
{
    @Override
    public boolean matches(InventoryCrafting pCraftingGrid, World pWorld) {
        return (validateUpperHelmet(pCraftingGrid) || validateLowerHelmet(pCraftingGrid) ||validateChestplate(pCraftingGrid) || validateLeggings(pCraftingGrid) || validateUpperShoes(pCraftingGrid) || validateLowerShoes(pCraftingGrid));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting pCraftingGrid) {
        if (!this.matches(pCraftingGrid, null)) return null;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) != null && !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return null;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        ItemStack tReturnStack = null;

        if (validateUpperHelmet(pCraftingGrid) || validateLowerHelmet(pCraftingGrid))
        {
            tReturnStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), new HashMap<MLAAddon, Integer>(), MedievalRegistry.getInstance().getMaterial(tMaterial).getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), tMaterial);
        }
        else if(validateChestplate(pCraftingGrid))
        {
            tReturnStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE), new HashMap<MLAAddon, Integer>(), MedievalRegistry.getInstance().getMaterial(tMaterial).getBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE), tMaterial);
        }
        else if(validateLeggings(pCraftingGrid))
        {
            tReturnStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS), new HashMap<MLAAddon, Integer>(), MedievalRegistry.getInstance().getMaterial(tMaterial).getBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS), tMaterial);
        }
        else if(validateUpperShoes(pCraftingGrid) || validateLowerShoes(pCraftingGrid))
        {
            tReturnStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES), new HashMap<MLAAddon, Integer>(), MedievalRegistry.getInstance().getMaterial(tMaterial).getBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES), tMaterial);
        }

        return tReturnStack;
    }

    @Override
    public int getRecipeSize() {
        return 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    public boolean validateUpperHelmet(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) == null || !(pCraftingGrid.getStackInRowAndColumn(0,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) == null || !(pCraftingGrid.getStackInRowAndColumn(1,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) == null || !(pCraftingGrid.getStackInRowAndColumn(2,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) != null) return false;

        return true;
    }

    public boolean validateLowerHelmet(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) == null || !(pCraftingGrid.getStackInRowAndColumn(1,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) == null || !(pCraftingGrid.getStackInRowAndColumn(0,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) == null || !(pCraftingGrid.getStackInRowAndColumn(2,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;

        return true;
    }

    public boolean validateChestplate(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) == null || !(pCraftingGrid.getStackInRowAndColumn(0,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) == null || !(pCraftingGrid.getStackInRowAndColumn(1,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) == null || !(pCraftingGrid.getStackInRowAndColumn(2,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) == null || !(pCraftingGrid.getStackInRowAndColumn(1,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) == null || !(pCraftingGrid.getStackInRowAndColumn(0,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) == null || !(pCraftingGrid.getStackInRowAndColumn(1,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) == null || !(pCraftingGrid.getStackInRowAndColumn(2,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;

        return true;
    }

    public boolean validateLeggings(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) == null || !(pCraftingGrid.getStackInRowAndColumn(0,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) == null || !(pCraftingGrid.getStackInRowAndColumn(1,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(1,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) == null || !(pCraftingGrid.getStackInRowAndColumn(2,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) == null || !(pCraftingGrid.getStackInRowAndColumn(0,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) == null || !(pCraftingGrid.getStackInRowAndColumn(2,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;

        return true;
    }

    public boolean validateUpperShoes(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) == null || !(pCraftingGrid.getStackInRowAndColumn(0,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) == null || !(pCraftingGrid.getStackInRowAndColumn(2,0).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,0).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) != null) return false;

        return true;
    }

    public boolean validateLowerShoes(InventoryCrafting pCraftingGrid)
    {
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain)) return false;
        String tMaterial = pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial);

        if (pCraftingGrid.getStackInRowAndColumn(0,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,0) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,1) == null || !(pCraftingGrid.getStackInRowAndColumn(0,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,1) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,1) == null || !(pCraftingGrid.getStackInRowAndColumn(2,1).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,1).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(0,2) == null || !(pCraftingGrid.getStackInRowAndColumn(0,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(0,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;
        if (pCraftingGrid.getStackInRowAndColumn(1,2) != null) return false;
        if (pCraftingGrid.getStackInRowAndColumn(2,2) == null || !(pCraftingGrid.getStackInRowAndColumn(2,2).getItem() instanceof ItemMetalChain) || !(pCraftingGrid.getStackInRowAndColumn(2,2).getTagCompound().getString(References.NBTTagCompoundData.RingMaterial).equals(tMaterial))) return false;

        return true;
    }
}
