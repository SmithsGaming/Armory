package com.Orion.Armory.Common.Item.Armor.TierMedieval;

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Addons.ArmorUpgradeMedieval;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Orion
 * Created on 26.05.2015
 * 15:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemUpgradeMedieval extends Item
{
    private HashMap<String, CustomResource> iResources = new HashMap<String, CustomResource>();

    public ItemUpgradeMedieval()
    {
        this.setMaxStackSize(16);
        this.setCreativeTab(GeneralRegistry.iTabMedievalUpgrades);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMedievalUpdrade);
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        MultiLayeredArmor tArmor = MaterialRegistry.getInstance().getArmor(pStack.getTagCompound().getString(References.NBTTagCompoundData.Armor.ArmorID));
        ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tArmor.getAddon(pStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));

        return tUpgrade.getResource().getIcon();
    }

    //Function to get the amount of RenderPasses. Used in the vanilla rendering of the itemstack.
    @Override
    public int getRenderPasses(int pMeta) {
        return 1;
    }

    //Function that tells the renderer to use multiple layers
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    //Function that tells the renderer to use a certain color
    @Override
    public int getColorFromItemStack(ItemStack pStack, int pRenderPass) {
        MultiLayeredArmor tArmor = MaterialRegistry.getInstance().getArmor(pStack.getTagCompound().getString(References.NBTTagCompoundData.Armor.ArmorID));
        ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tArmor.getAddon(pStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));

        return tUpgrade.getResource().getColor().getColor();
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pUpgrade, CreativeTabs pCreativeTab, List pItemStacks)
    {
        MultiLayeredArmor tHelmet = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
        for (MLAAddon tAddon: tHelmet.getAllowedAddons())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            ItemStack tUpgradeStack = new ItemStack(pUpgrade, 1);
            NBTTagCompound pUpgradeCompound = new NBTTagCompound();
            pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
            pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, tHelmet.getInternalName());
            pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

            tUpgradeStack.setTagCompound(pUpgradeCompound);
            pItemStacks.add(tUpgradeStack);
        }

        MultiLayeredArmor tChestPlate = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE);
        for (MLAAddon tAddon: tChestPlate.getAllowedAddons())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            ItemStack tUpgradeStack = new ItemStack(pUpgrade, 1);
            NBTTagCompound pUpgradeCompound = new NBTTagCompound();
            pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
            pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, tChestPlate.getInternalName());
            pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

            tUpgradeStack.setTagCompound(pUpgradeCompound);
            pItemStacks.add(tUpgradeStack);
        }

        MultiLayeredArmor tLeggins = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS);
        for (MLAAddon tAddon: tLeggins.getAllowedAddons())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            ItemStack tUpgradeStack = new ItemStack(pUpgrade, 1);
            NBTTagCompound pUpgradeCompound = new NBTTagCompound();
            pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
            pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, tLeggins.getInternalName());
            pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

            tUpgradeStack.setTagCompound(pUpgradeCompound);
            pItemStacks.add(tUpgradeStack);
        }

        MultiLayeredArmor tSHoes = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES);
        for (MLAAddon tAddon: tSHoes.getAllowedAddons())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            ItemStack tUpgradeStack = new ItemStack(pUpgrade, 1);
            NBTTagCompound pUpgradeCompound = new NBTTagCompound();
            pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
            pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, tSHoes.getInternalName());
            pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

            tUpgradeStack.setTagCompound(pUpgradeCompound);
            pItemStacks.add(tUpgradeStack);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack)
    {
        MultiLayeredArmor tArmor = MaterialRegistry.getInstance().getArmor(pStack.getTagCompound().getString(References.NBTTagCompoundData.Armor.ArmorID));
        ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tArmor.getAddon(pStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
        String tMaterialID = tUpgrade.iMaterialInternalName;
        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(tMaterialID);

        return tMaterial.getVisibleNameColor() + StatCollector.translateToLocal(tMaterial.getVisibleName()) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal(tUpgrade.iVisibleName + ".name");
    }

}
