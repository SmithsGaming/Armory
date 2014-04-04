package com.Orion.Armory.Common.Armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import sun.org.mozilla.javascript.internal.ast.IfStatement;

import java.util.*;

/**
 * Created by Marc on 26-3-2014.
 */
public class ArmorBase extends ArmorCore
{
    public static List<ArmorUpgrade> armorUpgrades;
    public static List<ArmorModifier> armorModifiers;
    public static String iBaseModelTextureLocation;
    public static String iBaseIconLocation;

    public ArmorBase(ArmorMaterial pMaterial, int pArmorPart, int pMaxDamage, String pBaseIconLocation, String pBaseModelTextureLocation ,List<ArmorUpgrade> pArmorUpgrades, List<ArmorModifier> pArmorModifiers)
    {
        super(pMaterial, pArmorPart);
        this.setMaxDamage(pMaxDamage);

        armorUpgrades = pArmorUpgrades;
        armorModifiers = pArmorModifiers;
        iBaseModelTextureLocation = pBaseModelTextureLocation;
        iBaseIconLocation = pBaseIconLocation;

        this.modelTextureLocations = new String[(armorUpgrades.size()+armorModifiers.size()+1)];
        this.iconLocations = new String[(armorUpgrades.size()+armorModifiers.size()+1)];
        this.iconTextures = new IIcon[(armorUpgrades.size()+armorModifiers.size()+1)];

        getIcons();
    }


    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
    {
        return 5;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        ArmorProperties currentProperties = new ArmorProperties(2, 0.5, 10);
        return currentProperties;
    }

    private void getIcons()
    {
        int renderPass = 0;
        this.addIconToIconTextures(0, false, iBaseIconLocation, iBaseModelTextureLocation);

        for (renderPass=1; renderPass<=armorUpgrades.size(); renderPass++)
        {
            ArmorUpgrade currentUpgrade = armorUpgrades.get(renderPass-1);
            this.addIconToIconTextures(renderPass, false, currentUpgrade.getIconLocation(), currentUpgrade.getModelTextureLocation());
        }

        for (int modifiers=1; modifiers<=armorModifiers.size(); modifiers++ )
        {
            ArmorModifier currentModifier = armorModifiers.get(modifiers - 1);
            this.addIconToIconTextures((renderPass+modifiers-1), false, currentModifier.getIconLocation(), currentModifier.getModelTextureLocation());
        }
    }

}
