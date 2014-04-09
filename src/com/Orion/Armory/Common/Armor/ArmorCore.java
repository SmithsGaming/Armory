package com.Orion.Armory.Common.Armor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;

import com.Orion.Armory.Common.ARegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Orion on 26-3-2014
 */
public class ArmorCore extends ItemArmor implements ISpecialArmor
{
    public final int iArmorPart;
    public String iInternalName;

    //Hashmaps for storing the Icons
    public HashMap<Integer, IIcon> iBaseIcons = new HashMap<Integer, IIcon>();
    public HashMap<Integer, IIcon> iUpgradeIcons = new HashMap<Integer, IIcon>();
    public HashMap<Integer, IIcon> iModifierIcons = new HashMap<Integer, IIcon>();

    //Hashmaps to target the texture files
    public HashMap<Integer, String[]> iBaseStrings = new HashMap<Integer, String[]>();
    public HashMap<Integer, String[]> iUpgradeStrings = new HashMap<Integer, String[]>();
    public HashMap<Integer, String[]> iModifierStrings = new HashMap<Integer, String[]>();

    public ArmorCore(String pInternalName, int pArmorPart) {
        super(ARegistry.iArmorMaterial, 0, pArmorPart);
        this.setMaxStackSize(1);
        this.iInternalName = pInternalName;
        this.iArmorPart = pArmorPart;
        this.setCreativeTab(ARegistry.iTabArmoryArmor);
    }

    //Functions for ISpecialArmor. TODO: Needs to be implemented.
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        return;
    }


    //Special functions for the registering of the individual texture location for both Icon ([0]) and model [1]
    public void registerBaseTexture(int ID, String[] pBaseTextureLocations)
    {
        iBaseStrings.put(ID, pBaseTextureLocations);
    }

    public void registerUpgradeTexture(int ID, String [] pUpgradeTextureLocations)
    {
        iUpgradeStrings.put(ID, pUpgradeTextureLocations);
    }

    public void registerModifierTexture(int ID, String[] pModifierTextureLocations)
    {
        iModifierStrings.put(ID, pModifierTextureLocations);
    }

    //Function called when registering the item to register the Icons.
    @Override
    public void registerIcons(IIconRegister pIconRegister)
    {
        //Adding the base icons
        Iterator tBaseIter = iBaseStrings.entrySet().iterator();
        while (tBaseIter.hasNext())
        {
            Map.Entry<Integer, String[]> tCurrentTexture = (Map.Entry<Integer, String[]>) tBaseIter.next();
            iBaseIcons.put(tCurrentTexture.getKey(), pIconRegister.registerIcon(tCurrentTexture.getValue()[0]));
        }

        //Adding the upgrade icons
        Iterator tUpgradeIter = iUpgradeStrings.entrySet().iterator();
        while (tUpgradeIter.hasNext())
        {
            Map.Entry<Integer, String[]> tCurrentTexture = (Map.Entry<Integer, String[]>) tUpgradeIter.next();
            iUpgradeIcons.put(tCurrentTexture.getKey(), pIconRegister.registerIcon(tCurrentTexture.getValue()[0]));
        }

        //Adding the modifier icons
        Iterator tModifierIter = iModifierStrings.entrySet().iterator();
        while (tModifierIter.hasNext())
        {
            Map.Entry<Integer, String[]> tCurrentTexture = (Map.Entry<Integer, String[]>) tModifierIter.next();
            iModifierIcons.put(tCurrentTexture.getKey(), pIconRegister.registerIcon(tCurrentTexture.getValue()[0]));
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (ItemStack stack, int renderPass)
    {
        NBTTagCompound tItemCompound = stack.getTagCompound();
        NBTTagCompound tRenderCompound = tItemCompound.getCompoundTag("RenderCompound");
        NBTTagCompound tCurrentRenderCompound = tRenderCompound.getCompoundTag("RenderPass " + renderPass);
        String tIconLocation = tCurrentRenderCompound.getString("IconLocation");
        int tIconID = tCurrentRenderCompound.getInteger("IconID");

        if (tIconLocation.equals("Base")) {
            return iBaseIcons.get(tIconID);
        } else if (tIconLocation.equals("Upgrades")) {
            return iUpgradeIcons.get(tIconID);
        } else if (tIconLocation.equals("Modifiers")) {
            return iModifierIcons.get(tIconID);
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public String getArmorTextureLocation(ItemStack stack, int renderPass)
    {
        NBTTagCompound tItemCompound = stack.getTagCompound();
        NBTTagCompound tRenderCompound = tItemCompound.getCompoundTag("RenderCompound");
        NBTTagCompound tCurrentRenderCompound = tRenderCompound.getCompoundTag("RenderPass " + renderPass);
        String tIconLocation = tCurrentRenderCompound.getString("IconLocation");
        int tIconID = tCurrentRenderCompound.getInteger("TextureID");

        if (tIconLocation.equals("Base")) {
            return iBaseStrings.get(tIconID)[1];
        } else if (tIconLocation.equals("Upgrade")) {
            return iUpgradeStrings.get(tIconID)[1];
        } else if (tIconLocation.equals("Modifier")) {
            return iModifierStrings.get(tIconID)[1];
        }

        return "";
    }

}
