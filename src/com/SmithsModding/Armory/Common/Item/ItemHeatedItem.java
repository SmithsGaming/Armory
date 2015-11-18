package com.SmithsModding.Armory.Common.Item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.SmithsModding.Armory.Common.Config.ArmoryConfig;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemHeatedItem extends Item {
    public ItemHeatedItem() {
        setMaxStackSize(1);
        setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        setUnlocalizedName(References.InternalNames.Items.ItemHeatedIngot);
    }

    public static void setItemTemperature(ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            pItemStack.getTagCompound().setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);
        }
    }

    public static float getItemTemperature(ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            return pItemStack.getTagCompound().getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }

        return 0F;
    }

    public static boolean areStacksEqualExceptTemp(ItemStack pFirstStack, ItemStack pSecondStack) {
        if (!(pFirstStack.getItem() instanceof ItemHeatedItem)) {
            return false;
        }

        if (!(pSecondStack.getItem() instanceof ItemHeatedItem)) {
            return false;
        }

        if (!pFirstStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE).equals(pSecondStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE)))
            return false;

        return pFirstStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID).equals(pSecondStack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));

    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getIcon(tOriginalItemStack, pRenderPass);
    }

    @Override
    public IIcon getIconIndex(ItemStack pStack) {
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
    public boolean showDurabilityBar(ItemStack pStack) {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack pStack) {
        return 1 - (pStack.getTagCompound().getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE)) / (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(pStack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack pStack, EntityPlayer pPlayer, List pTooltipList, boolean pBoolean) {
        String tTemperatureLine = StatCollector.translateToLocal(TranslationKeys.Items.HeatedIngot.TemperatureTag);
        tTemperatureLine = tTemperatureLine + ": " + Math.round(getItemTemperature(pStack));

        pTooltipList.add(tTemperatureLine);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pItem, CreativeTabs pCreativeTab, List pItemStacks) {
        Iterator<ItemStack> tStackIter = HeatedItemFactory.getInstance().getAllMappedStacks().iterator();

        while (tStackIter.hasNext()) {
            ItemStack tCooledStack = tStackIter.next();
            ItemStack tHeatedStack = HeatedItemFactory.getInstance().convertToHeatedIngot(tCooledStack);

            if (tHeatedStack != null) {
                pItemStacks.add(tHeatedStack);
            } else {
                GeneralRegistry.iLogger.info("Tried to create a HeatedIngot from: " + HeatedItemFactory.getInstance().getMaterialIDFromItemStack(tCooledStack) + " and failed!");
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        ItemStack tOriginalItemStack = ItemStack.loadItemStackFromNBT(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.HeatedIngot.ORIGINALITEM));
        return tOriginalItemStack.getItem().getItemStackDisplayName(tOriginalItemStack);
    }

    @Override
    public void onUpdate(ItemStack pStack, World pWorldObj, Entity pEntity, int pSlotIndex, boolean pSelected) {

        if (!(pEntity instanceof EntityPlayer))
            return;

        if (!ArmoryConfig.enableTemperatureDecay)
            return;

        EntityPlayer tPlayer = (EntityPlayer) pEntity;

        float tCurrentTemp = getItemTemperature(pStack);
        float tNewTemp = tCurrentTemp - 0.2F;

        setItemTemperature(pStack, tNewTemp);

        if (tNewTemp < 20F) {
            tPlayer.inventory.mainInventory[pSlotIndex] = HeatedItemFactory.getInstance().convertToCooledIngot(pStack);
        } else {
            for (ItemStack tStack : tPlayer.inventory.mainInventory) {
                if (tStack == null)
                    continue;

                if (tStack.getItem() instanceof ItemTongs)
                    return;
            }

            tPlayer.setFire(1);
        }
    }
}