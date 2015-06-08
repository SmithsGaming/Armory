package com.Orion.Armory.Common.Item.Armor.TierMedieval;
/*
 *   ArmorMedieval
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Client.Models.ModelAExtendedChain;
import com.Orion.Armory.Common.Addons.ArmorUpgradeMedieval;
import com.Orion.Armory.Common.Addons.MedievalAddonRegistry;
import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class ArmorMedieval extends MultiLayeredArmor {
    public ArmorMedieval(String pInternalName, int pArmorPart) {
        super(pInternalName, GeneralRegistry.iArmorMaterial, 1, pArmorPart,15);
        this.setUnlocalizedName(pInternalName);
        this.setMaxStackSize(1);
        this.iInternalName = pInternalName;
        this.iArmorPart = pArmorPart;
        this.setCreativeTab(GeneralRegistry.iTabMedievalArmor);
    }

    //Functions for ISpecialArmor. TODO: Needs to be implemented.
    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, double pDamage, int pSlot) {
        com.Orion.Armory.Common.Material.ArmorMaterial tBaseMaterial = (com.Orion.Armory.Common.Material.ArmorMaterial) NBTHelper.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getInternalName());

        for(ArmorUpgradeMedieval tUpgrade: NBTHelper.getInstalledArmorMedievalUpgradesOnItemStack(pStack).keySet())
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return new ISpecialArmor.ArmorProperties(0, tDamageRatio , (int) (2 * tDamageRatio));
    }

    @Override
    public int getArmorDisplay(EntityPlayer pEntity, ItemStack pStack, int pSlot)
    {
        com.Orion.Armory.Common.Material.ArmorMaterial tBaseMaterial = (com.Orion.Armory.Common.Material.ArmorMaterial) NBTHelper.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getInternalName());

        for(ArmorUpgradeMedieval tUpgrade: NBTHelper.getInstalledArmorMedievalUpgradesOnItemStack(pStack).keySet())
        {
            tDamageRatio += tUpgrade.iProtection;
        }

        return (int) tDamageRatio;
    }

    @Override
    public void damageArmor(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, int pDamage, int pSlot)    {
        com.Orion.Armory.Common.Material.ArmorMaterial tArmorMaterial = (com.Orion.Armory.Common.Material.ArmorMaterial) NBTHelper.getBaseMaterialOfItemStack(pStack);
        pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.CurrentDurability) - pDamage);

        pStack.setItemDamage((int) ((float)pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.CurrentDurability)/ pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.TotalDurability) * 100));

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
        for(IArmorMaterial tMaterial: MaterialRegistry.getInstance().getArmorMaterials().values()){
            if (!tMaterial.isBaseArmorMaterial())
                continue;

            ItemStack tStandardArmor = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(this.getInternalName()), tMaterial.getInternalMaterialName());

            pItemStacks.add(tStandardArmor);
        }

        if (this.getInternalName().equals(References.InternalNames.Armor.MEDIEVALHELMET)) {
            HashMap<MLAAddon, Integer> tHelmetAddons = new HashMap<MLAAddon, Integer>();
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonHelmet = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tHelmetAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getInternalName()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonHelmet);
        } else if (this.getInternalName().equals(References.InternalNames.Armor.MEDIEVALCHESTPLATE)) {
            HashMap<MLAAddon, Integer> tChestplateAddons = new HashMap<MLAAddon, Integer>();
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + References.InternalNames.Materials.Vanilla.IRON), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + References.InternalNames.Materials.Vanilla.IRON), 1);

            ItemStack tAddonChestplate = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tChestplateAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getInternalName()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonChestplate);
        } else if (this.getInternalName().equals(References.InternalNames.Armor.MEDIEVALLEGGINGS)) {
            HashMap<MLAAddon, Integer> tLeggingAddons = new HashMap<MLAAddon, Integer>();
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonLeggins = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tLeggingAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getInternalName()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonLeggins);
        } else if (this.getInternalName().equals(References.InternalNames.Armor.MEDIEVALSHOES)) {
            HashMap<MLAAddon, Integer> tShoesAddons = new HashMap<MLAAddon, Integer>();
            tShoesAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tShoesAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonShoes = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tShoesAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getInternalName()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonShoes);
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

    @Override
    public void registerAddon(MLAAddon pNewAddon)
    {
        if (!(pNewAddon instanceof ArmorUpgradeMedieval))
        {
            throw new InvalidParameterException("The given addon is not allowed on medieval armor.");
        }

        super.registerAddon(pNewAddon);
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));

        return tMaterial.getVisibleNameColor() + StatCollector.translateToLocal(tMaterial.getVisibleName()) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }
}
