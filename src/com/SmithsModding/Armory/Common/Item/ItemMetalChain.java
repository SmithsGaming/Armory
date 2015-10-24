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

public class ItemMetalChain extends ItemResource implements IHeatableItem {

    public ItemMetalChain() {
        this.setMaxStackSize(16);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalChain);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }


    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, tMaterial.getMaterialID());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
            tChainStack.setTagCompound(tStackCompound);

            HeatedItemFactory.getInstance().addHeatableItemstack(tMaterial.getInternalMaterialName(), tChainStack);

            pItemStacks.add(tChainStack);
        }
    }

    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.CHAIN;
    }
}
