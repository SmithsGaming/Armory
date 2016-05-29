package com.smithsmodding.armory.common.item.armor.tiermedieval;

/*
 *   ArmorMedieval
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.addons.MedievalAddonRegistry;
import com.smithsmodding.armory.common.factory.MedievalArmorFactory;
import com.smithsmodding.armory.common.material.ChainLayer;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

public class ArmorMedieval extends MultiLayeredArmor {

    public ArmorMedieval(String pInternalName, int pArmorPart) {
        super(pInternalName, 1, pArmorPart);
        this.setUnlocalizedName(pInternalName);
        this.setMaxStackSize(1);
        this.uniqueID = pInternalName;
        this.armorIndex = pArmorPart;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    //Functions for ISpecialArmor. TODO: Needs to be implemented.
    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, double pDamage, int pSlot) {
        IArmorMaterial tBaseMaterial = ArmorNBTHelper.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getUniqueID());

        for (ArmorUpgradeMedieval tUpgrade : ArmorNBTHelper.getInstalledArmorMedievalUpgradesOnItemStack(pStack).keySet()) {
            tDamageRatio += tUpgrade.iProtection;
        }

        return new ISpecialArmor.ArmorProperties(0, tDamageRatio, (int) (2 * tDamageRatio));
    }

    @Override
    public int getArmorDisplay(EntityPlayer pEntity, ItemStack pStack, int pSlot) {
        IArmorMaterial tBaseMaterial = ArmorNBTHelper.getBaseMaterialOfItemStack(pStack);
        float tDamageRatio = tBaseMaterial.getBaseDamageAbsorption(this.getUniqueID());

        for (ArmorUpgradeMedieval tUpgrade : ArmorNBTHelper.getInstalledArmorMedievalUpgradesOnItemStack(pStack).keySet()) {
            tDamageRatio += tUpgrade.iProtection;
        }

        return (int) tDamageRatio;
    }

    @Override
    public void damageArmor(EntityLivingBase pEntity, ItemStack pStack, DamageSource pSource, int pDamage, int pSlot) {
        IArmorMaterial tArmorMaterial = ArmorNBTHelper.getBaseMaterialOfItemStack(pStack);
        pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).setInteger(References.NBTTagCompoundData.Armor.CurrentDurability, pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.CurrentDurability) - pDamage);

        pStack.setItemDamage((int) ((float) pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.CurrentDurability) / pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getInteger(References.NBTTagCompoundData.Armor.TotalDurability) * 100));

        ((EntityPlayer) pEntity).inventory.armorInventory[pSlot] = pStack;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pArmorCore, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            if (!tMaterial.getIsBaseArmorMaterial())
                continue;

            ItemStack tStandardArmor = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(this.getUniqueID()), tMaterial.getUniqueID());

            pItemStacks.add(tStandardArmor);
        }

        if (this.getUniqueID().equals(References.InternalNames.Armor.MEDIEVALHELMET)) {
            HashMap<MLAAddon, Integer> tHelmetAddons = new HashMap<MLAAddon, Integer>();
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tHelmetAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonHelmet = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tHelmetAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getUniqueID()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonHelmet);
        } else if (this.getUniqueID().equals(References.InternalNames.Armor.MEDIEVALCHESTPLATE)) {
            HashMap<MLAAddon, Integer> tChestplateAddons = new HashMap<MLAAddon, Integer>();
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + References.InternalNames.Materials.Vanilla.IRON), 1);
            tChestplateAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + References.InternalNames.Materials.Vanilla.IRON), 1);

            ItemStack tAddonChestplate = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tChestplateAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getUniqueID()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonChestplate);
        } else if (this.getUniqueID().equals(References.InternalNames.Armor.MEDIEVALLEGGINGS)) {
            HashMap<MLAAddon, Integer> tLeggingAddons = new HashMap<MLAAddon, Integer>();
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tLeggingAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonLeggins = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tLeggingAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getUniqueID()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonLeggins);
        } else if (this.getUniqueID().equals(References.InternalNames.Armor.MEDIEVALSHOES)) {
            HashMap<MLAAddon, Integer> tShoesAddons = new HashMap<MLAAddon, Integer>();
            tShoesAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.LEFT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);
            tShoesAddons.put(MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + References.InternalNames.Materials.Vanilla.OBSIDIAN), 1);

            ItemStack tAddonShoes = MedievalArmorFactory.getInstance().buildNewMLAArmor(this, tShoesAddons, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getBaseDurability(this.getUniqueID()), References.InternalNames.Materials.Vanilla.IRON);

            pItemStacks.add(tAddonShoes);
        }

    }

    @Override
    public void registerAddon(MLAAddon pNewAddon) {
        if (!(pNewAddon instanceof ArmorUpgradeMedieval) && !(pNewAddon instanceof ChainLayer)) {
            throw new InvalidParameterException("The given addon is not allowed on medieval armor.");
        }

        super.registerAddon(pNewAddon);
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.ArmorData).getString(References.NBTTagCompoundData.Armor.MaterialID));

        return tMaterial.getNameColor() + I18n.translateToLocal(tMaterial.getTranslationKey()) + " " + TextFormatting.RESET + I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }
}
