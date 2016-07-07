package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.references.ModCreativeTabs;
import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 *
 * Copyrighted according to Project specific license
 */
public class ItemPlate extends ItemResource implements IHeatableItem {

    public ItemPlate() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalPlate);
        this.setRegistryName(References.InternalNames.Items.ItemMetalPlate.toLowerCase());
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
            ItemStack tNuggetStack = new ItemStack(this, 1, tMaterial.getItemDamageMaterialIndex());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
            tNuggetStack.setTagCompound(tStackCompound);

            if (!HeatableItemRegistry.getInstance().isHeatable(tNuggetStack))
                HeatableItemRegistry.getInstance().addBaseStack(tMaterial, tNuggetStack);

            pItemStacks.add(tNuggetStack);
        }
    }

    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.PLATE;
    }

    @Override
    public int getMoltenMilibucket() {
        return 144;
    }
}
