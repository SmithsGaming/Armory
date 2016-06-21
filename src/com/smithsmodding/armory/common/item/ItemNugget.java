package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.util.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemNugget extends ItemResource implements IHeatableItem {

    public ItemNugget() {
        this.setMaxStackSize(64);
        this.setCreativeTab(GeneralRegistry.CreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalNugget);
        this.setRegistryName(References.InternalNames.Items.ItemMetalNugget);
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
        return References.InternalNames.HeatedItemTypes.NUGGET;
    }

    @Override
    public int getMoltenMilibucket() {
        return 144 / 9;
    }
}
