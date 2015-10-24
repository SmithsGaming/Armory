package com.SmithsModding.Armory.Client.CreativeTab;

import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ComponentsTab extends CreativeTabs {
    private ItemStack iItemStack;

    public ComponentsTab() {
        super(CreativeTabs.getNextID(), "Armory - Components Tab");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (iItemStack == null) {
            iItemStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1);
            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, References.InternalNames.Materials.Vanilla.OBSIDIAN);

            iItemStack.setTagCompound(tStackCompound);
        }

        return iItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return StatCollector.translateToLocal(TranslationKeys.CreativeTabs.Components);
    }

    @Override
    public Item getTabIconItem() {
        return null;
    }
}
