package com.SmithsModding.Armory.Common.Item;
/*
 *   ItemMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.SmithsModding.Armory.API.Item.IHeatableItem;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ItemMetalRing extends ItemResource implements IHeatableItem {

    public ItemMetalRing() {
        this.setMaxStackSize(64);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalRing);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, tMaterial.getMaterialID());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tStackCompound);

            HeatedItemFactory.getInstance().addHeatableItemstack(tMaterial.getInternalMaterialName(), tRingStack);

            pItemStacks.add(tRingStack);
        }
    }

    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.RING;
    }
}
