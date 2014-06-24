package com.Orion.Armory.Common.Armor;

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Logic.ArmorBuilder;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.OrionsBelt.Common.Util.Armor.MultiLayeredArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

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
    public ArmorProperties getProperties(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, double pDamage, int pSlot) {
        com.Orion.Armory.Common.Armor.ArmorMaterial tBaseMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(((ArmorCore) pStack.getItem()).iArmorPart);

        for(ArmorUpgrade tUpgrade: ARegistry.iInstance.getInstalledArmorUpgradesOnItemStack(pStack))
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return new ArmorProperties(0, tDamageRatio , (int) (2 * tDamageRatio));
    }

    @Override
    public int getArmorDisplay(EntityPlayer pEntity, ItemStack pStack, int pSlot)
    {
        com.Orion.Armory.Common.Armor.ArmorMaterial tBaseMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(((ArmorCore) pStack.getItem()).iArmorPart);

        for(ArmorUpgrade tUpgrade: ARegistry.iInstance.getInstalledArmorUpgradesOnItemStack(pStack))
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return (int) tDamageRatio;
    }

    @Override
    public void damageArmor(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, int pDamage, int pSlot)    {
        com.Orion.Armory.Common.Armor.ArmorMaterial tArmorMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
        pStack.getTagCompound().setInteger("CurrentDurability", pStack.getTagCompound().getInteger("CurrentDurability") - pDamage);

        pStack.setItemDamage((int) ((float)pStack.getTagCompound().getInteger("CurrentDurability")/ (float)pStack.getTagCompound().getInteger("TotalDurability") * 100));

        ((EntityPlayer) pEntity).inventory.armorInventory[pSlot] = pStack;
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pArmorCore, CreativeTabs pCreativeTab, List pItemStacks)
    {
        for(com.Orion.Armory.Common.Armor.ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials()){
            ItemStack tStandardArmor = ArmorBuilder.instance.createInitialArmor(ARegistry.iInstance.getMaterialID(tMaterial), ((ArmorCore) pArmorCore).iArmorPart);

            pItemStacks.add(tStandardArmor);
        }
    }


}