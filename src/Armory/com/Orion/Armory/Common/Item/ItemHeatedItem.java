package com.Orion.Armory.Common.Item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.Iterator;
import java.util.List;

public class ItemHeatedItem extends Item
{
    public ItemHeatedItem()
    {
        setMaxStackSize(1);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemHeatedIngot);
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getIcon(tOriginalItemStack, pRenderPass);
    }

     @Override
     public IIcon getIconIndex(ItemStack pStack)
     {
         return getIcon(pStack, 0);
     }

    //Function that tells the renderer to use multiple layers
    @Override
    public boolean requiresMultipleRenderPasses() {
        return false;
    }

    //Function that tells the renderer to use a certain color
    @Override
    public int getColorFromItemStack(ItemStack pStack, int pRenderPass) {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getColorFromItemStack(tOriginalItemStack, pRenderPass);
    }

    @Override
    public boolean showDurabilityBar(ItemStack pStack)
    {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack pStack)
    {
        return  1 - (pStack.getTagCompound().getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE)) / (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(pStack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack pStack, EntityPlayer pPlayer, List pTooltipList, boolean pBoolean)
    {
        String tTemperatureLine = StatCollector.translateToLocal(TranslationKeys.Items.HeatedIngot.TemperatureTag);
        tTemperatureLine = tTemperatureLine + ": " + Math.round(getItemTemperature(pStack));

        pTooltipList.add(tTemperatureLine);
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pArmorCore, CreativeTabs pCreativeTab, List pItemStacks)
    {
        Iterator<ItemStack> tStackIter = HeatedItemFactory.getInstance().getAllMappedStacks().iterator();

        while (tStackIter.hasNext())
        {
            ItemStack tCooledStack = tStackIter.next();
            ItemStack tHeatedStack = HeatedItemFactory.getInstance().convertToHeatedIngot(tCooledStack);

            if (tHeatedStack != null)
            {
                pItemStacks.add(tHeatedStack);
            }
            else
            {
                GeneralRegistry.iLogger.info("Tried to create a HeatedIngot from: " + HeatedItemFactory.getInstance().getMaterialIDFromItemStack(tCooledStack) + " and failed!");
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack)
    {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getItemStackDisplayName(tOriginalItemStack);
    }

    public static void setItemTemperature(ItemStack pItemStack, float pNewTemp)
    {
        if (pItemStack.getItem() instanceof ItemHeatedItem)
        {
            pItemStack.getTagCompound().setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);
        }
    }

    public static float getItemTemperature(ItemStack pItemStack)
    {
        if (pItemStack.getItem() instanceof ItemHeatedItem)
        {
            return pItemStack.getTagCompound().getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }

        return 0F;
    }
}
