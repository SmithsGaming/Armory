package com.smithsmodding.armory.common.item;
/*
 *   ItemMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemMetalChain extends ItemResource implements IHeatableItem {

    public ItemMetalChain() {
        this.setMaxStackSize(16);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalChain);
        this.setRegistryName(References.InternalNames.Items.ItemMetalChain);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void buildSubItemList(Item item, CreativeTabs tabs, List<ItemStack> subItems) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            ItemStack tChainStack = new ItemStack(this, 1, tMaterial.getItemDamageMaterialIndex());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
            tChainStack.setTagCompound(tStackCompound);

            if (!HeatableItemRegistry.getInstance().isHeatable(tChainStack))
                HeatableItemRegistry.getInstance().addBaseStack(tMaterial, tChainStack);

            subItems.add(tChainStack);
        }
    }

    @NotNull
    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.CHAIN;
    }

    @Override
    public int getMoltenMilibucket() {
        return 9 * (144 / 9);
    }
}
