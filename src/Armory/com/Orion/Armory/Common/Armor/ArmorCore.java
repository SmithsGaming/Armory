package com.Orion.Armory.Common.Armor;

import com.Orion.Armory.Client.ArmoryResource;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.OrionsBelt.Util.Armor.MultiLayeredArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion on 26-3-2014
 */
public class ArmorCore extends MultiLayeredArmor implements ISpecialArmor {
    public final int iArmorPart;
    public String iInternalName;

    public ArmorCore(String pInternalName, int pArmorPart) {
        super("Armory.MultiArmor", pArmorPart, ARegistry.iArmorMaterial);
        this.setUnlocalizedName(pInternalName);
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

}