package com.Orion.Armory.Common.Armor.TierChain;

import com.Orion.Armory.Client.Models.ModelAExtendedChain;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Factory.StandardMLAFactory;
import com.Orion.Armory.Common.Armor.Core.MultiLayeredArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import javax.print.attribute.standard.Sides;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Orion on 26-3-2014
 */

public class ArmorChain extends MultiLayeredArmor {
    public ArmorChain(String pInternalName, int pArmorPart) {
        super(pInternalName, ARegistry.iArmorMaterial, 1, pArmorPart,15);
        this.setUnlocalizedName(pInternalName);
        this.setMaxStackSize(1);
        this.iInternalName = pInternalName;
        this.iArmorPart = pArmorPart;
        this.setCreativeTab(ARegistry.iTabArmoryArmor);
    }

    //Functions for ISpecialArmor. TODO: Needs to be implemented.
    @Override
    public ArmorProperties getProperties(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, double pDamage, int pSlot) {
        com.Orion.Armory.Common.Armor.Core.ArmorMaterial tBaseMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getInternalName());

        for(ArmorChainUpgrade tUpgrade: ARegistry.iInstance.getInstalledArmorChainUpgradesOnItemStack(pStack).keySet())
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return new ArmorProperties(0, tDamageRatio , (int) (2 * tDamageRatio));
    }

    @Override
    public int getArmorDisplay(EntityPlayer pEntity, ItemStack pStack, int pSlot)
    {
        com.Orion.Armory.Common.Armor.Core.ArmorMaterial tBaseMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getInternalName());

        for(ArmorChainUpgrade tUpgrade: ARegistry.iInstance.getInstalledArmorChainUpgradesOnItemStack(pStack).keySet())
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return (int) tDamageRatio;
    }

    @Override
    public void damageArmor(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, int pDamage, int pSlot)    {
        com.Orion.Armory.Common.Armor.Core.ArmorMaterial tArmorMaterial = ARegistry.iInstance.getBaseMaterialOfItemStack(pStack);
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
        for(com.Orion.Armory.Common.Armor.Core.ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values()){
            ItemStack tStandardArmor = StandardMLAFactory.iInstance.buildNewMLAArmor(this, new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(this.getInternalName()), tMaterial.iInternalName);

            pItemStacks.add(tStandardArmor);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel (EntityLivingBase pEntityLiving, ItemStack pItemStack, int pArmorSlot)
    {
        ModelAExtendedChain tModel = new ModelAExtendedChain(1F, pItemStack);

        switch(pArmorSlot)
        {
            case 0:
                tModel.bipedHead.showModel = true;
                tModel.bipedBody.showModel = tModel.bipedRightArm.showModel = tModel.bipedLeftArm.showModel = false;
                tModel.bipedWaist.showModel = tModel.bipedRightLeg.showModel = tModel.bipedLeftLeg.showModel = false;
                tModel.bipedRightFoot.showModel = tModel.bipedLeftFoot.showModel = false;
                break;
            case 1:
                tModel.bipedHead.showModel = false;
                tModel.bipedBody.showModel = tModel.bipedRightArm.showModel = tModel.bipedLeftArm.showModel = true;
                tModel.bipedWaist.showModel = tModel.bipedRightLeg.showModel = tModel.bipedLeftLeg.showModel = false;
                tModel.bipedRightFoot.showModel = tModel.bipedLeftFoot.showModel = false;
                break;
            case 2:
                tModel.bipedHead.showModel = false;
                tModel.bipedBody.showModel = tModel.bipedRightArm.showModel = tModel.bipedLeftArm.showModel = false;
                tModel.bipedWaist.showModel = tModel.bipedRightLeg.showModel = tModel.bipedLeftLeg.showModel = true;
                tModel.bipedRightFoot.showModel = tModel.bipedLeftFoot.showModel = false;
                break;
            case 3:
                tModel.bipedHead.showModel = false;
                tModel.bipedBody.showModel = tModel.bipedRightArm.showModel = tModel.bipedLeftArm.showModel = false;
                tModel.bipedWaist.showModel = tModel.bipedRightLeg.showModel = tModel.bipedLeftLeg.showModel = false;
                tModel.bipedRightFoot.showModel = tModel.bipedLeftFoot.showModel = true;
                break;
            default:
                tModel.bipedHead.showModel = false;
                tModel.bipedBody.showModel = tModel.bipedRightArm.showModel = tModel.bipedLeftArm.showModel = false;
                tModel.bipedWaist.showModel = tModel.bipedRightLeg.showModel = tModel.bipedLeftLeg.showModel = false;
                tModel.bipedRightFoot.showModel = tModel.bipedLeftFoot.showModel = false;
                break;
        }

        return tModel;
    }


}