package com.Orion.Armory.Common.Logic;
/*
*   ArmorBuilder
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;

public class ArmorBuilder
{
    //TODO: Create the initialize armor function.
    private static ArmorBuilder instance;

    public static void init()
    {
       instance = new ArmorBuilder();
    }

    public ItemStack buildArmor(ItemStack pBaseArmor, ArrayList<ArmorUpgrade> pUpgrades, ArrayList<ArmorModifier> pModifiers)
    {
        //Check if the given Armor needs to be modified.
        if (pUpgrades.isEmpty() && pModifiers.isEmpty())
        {
            return pBaseArmor;
        }

        //Grab the base component of the NBT Tag
        NBTTagCompound tBaseCompound = pBaseArmor.getTagCompound();
        ArrayList<ArmorModifier> tInstalledModifiers = ARegistry.iInstance.getInstalledModifiersOnItemStack(pBaseArmor);
        ArrayList<ArmorUpgrade> tInstalledUpgrades = ARegistry.iInstance.getInstalledArmorUpgradesOnItemStack(pBaseArmor);

        //Check if the maximum amount of modifiers is already installed
        if (tBaseCompound.getInteger("MaxModifiers") == tInstalledModifiers.size())
        {
            return null;
        }

        //Check if the new modifiers and upgrades are valid if not return an empty stack.
        for (ArmorUpgrade tUpgrade: pUpgrades)
        {
           if (!tUpgrade.validateCraftingForThisUpgrade(ARegistry.iInstance.getInstalledArmorUpgradesOnItemStack(pBaseArmor), pUpgrades))
           {
               return null;
           }
        }

        for (ArmorModifier tModifier: pModifiers)
        {
            if (!tModifier.validateCraftingForThisModifier(tInstalledModifiers, pModifiers))
            {
                return null;
            }
        }

        ArmorCore tCore = (ArmorCore) pBaseArmor.getItem();
        ItemStack tReturnStack = new ItemStack(tCore);

        //Create the parameters for the new NBTTag compound
        int tMaterialID = tBaseCompound.getInteger("MaterialID");
        int tNewBaseDurability = ARegistry.iInstance.getMaterial(tMaterialID).getBaseDurability(tCore.iArmorPart);
        int tTotalDurability = tBaseCompound.getInteger("TotalDurability");
        int tUsedDurability = tTotalDurability - tBaseCompound.getInteger("CurrentDurability");
        Float tNewMaxAbsorption = ARegistry.iInstance.getMaterial(tMaterialID).getBaseDamageAbsorption(tCore.iArmorPart);

        //Merge upgrades
        tInstalledUpgrades.addAll(pUpgrades);

        for (ArmorUpgrade tUpgrade: tInstalledUpgrades)
        {
            tNewBaseDurability += tUpgrade.iExtraDurability;
            tNewMaxAbsorption += (ARegistry.iInstance.getMaterial(tMaterialID).getBaseDamageAbsorption(tCore.iArmorPart) * tUpgrade.iProtection);
        }

        //Merge the modifiers (keeps the multiple level modifiers in mind by checking to old ones before adding new ones.)
        for (ArmorModifier tNewModifier: pModifiers) {
            boolean tInstalled = false;

            for (ArmorModifier tInstalledModifier : tInstalledModifiers) {
                if (tInstalledModifier.equals(tNewModifier)) {
                    tInstalledModifier.setInstalledAmount(tInstalledModifier.iInstalledAmount + tNewModifier.iInstalledAmount);
                    tInstalled = true;
                }
            }

            if (!tInstalled) {
                tInstalledModifiers.add(tNewModifier);
            }
        }

        //Creating the new NBTTagCompound for storing the data.
        NBTTagCompound tNewCompound = new NBTTagCompound();
        NBTTagCompound tNewRenderCompound = new NBTTagCompound();
        NBTTagCompound tNewUpgradesCompound = new NBTTagCompound();
        NBTTagCompound tNewModifiersCompound = new NBTTagCompound();

        //Setting the basic data for the Armor
        tNewCompound.setInteger("MaterialID", tMaterialID);
        tNewCompound.setInteger("TotalDurability", tNewBaseDurability);
        tNewCompound.setInteger("CurrentDurability", tNewBaseDurability-tUsedDurability);
        tNewCompound.setFloat("DamageAbsorption", tNewMaxAbsorption);
        tNewCompound.setInteger("MaxModifiers", ARegistry.iInstance.getMaterial(tMaterialID).getMaxModifiersOnPart(tCore.iArmorPart));

        //Add the renderpass data for the base armor
        tNewRenderCompound.setTag("RenderPass - 0", new NBTTagCompound());
        tNewRenderCompound.getCompoundTag("RenderPass - 0").setString("IconLocation", "Base");
        tNewRenderCompound.getCompoundTag("RenderPass - 0").setInteger("IconID", tMaterialID);

        //Adding the Upgrade data to the the upgrade and render part of the compound
        int tRenderPassCounter = 0;
        for (ArmorUpgrade tCurrentUpgrade: tInstalledUpgrades)
        {
            NBTTagCompound tRenderPassCompound = new NBTTagCompound();
            NBTTagCompound tUpgradeCompound = new NBTTagCompound();

            tRenderPassCounter++;

            tRenderPassCompound.setString("IconLocation", "Upgrades");
            tRenderPassCompound.setInteger("IconID", ARegistry.iInstance.getUpgradeTextureID(ARegistry.iInstance.getMaterial(tMaterialID).iInternalName, tCurrentUpgrade.iInternalName));

            tUpgradeCompound.setInteger("UpgradeID", ARegistry.iInstance.getUpgradeID(tCurrentUpgrade));

            tNewRenderCompound.setTag("RenderPass - " + tRenderPassCounter, tRenderPassCompound);
            tNewUpgradesCompound.setTag("Upgrade - " + tRenderPassCounter, tUpgradeCompound);
        }
        tNewCompound.setInteger("InstalledUpgrades", tRenderPassCounter);

        int tModifierCounter = 0;
        for (ArmorModifier tCurrentModifier: tInstalledModifiers)
        {
            NBTTagCompound tRenderPassCompound = new NBTTagCompound();
            NBTTagCompound tModifierCompound = new NBTTagCompound();

            tRenderPassCounter++;
            tModifierCounter++;

            tRenderPassCompound.setString("IconLocation", "Modifiers");
            tRenderPassCompound.setInteger("IconID", ARegistry.iInstance.getModifierTextureID(tCurrentModifier.iInternalName));

            tModifierCompound.setInteger("ModifierID", ARegistry.iInstance.getModifierID(tCurrentModifier));
            tModifierCompound.setInteger("Amount", tCurrentModifier.iInstalledAmount);

            tNewRenderCompound.setTag("RenderPass - " + tRenderPassCounter, tRenderPassCompound);
            tNewModifiersCompound.setTag("Modifier - " + tModifierCounter, tModifierCompound);
        }
        tNewCompound.setInteger("InstalledModifiers", tModifierCounter);
        tNewRenderCompound.setInteger("RenderPasses", tRenderPassCounter);

        //Adding the finished compounds to the root compound
        tNewCompound.setTag("RenderCompound", tNewRenderCompound);
        tNewCompound.setTag("Upgrades", tNewUpgradesCompound);
        tNewCompound.setTag("Modifiers", tNewModifiersCompound);

        //Adding the Tagcompound to the Stack and returning the finished stack.
        tReturnStack.setTagCompound(tNewCompound);
        return tReturnStack;
    }

    public ItemStack createInitialArmor(int pMaterialID, int pArmorID)
    {
        ArmorCore tCore = ARegistry.iInstance.getArmorMapping(pArmorID);
        ItemStack tReturnStack = new ItemStack(tCore);

        NBTTagCompound tBaseCompound = new NBTTagCompound();
        NBTTagCompound tRenderCompound = new NBTTagCompound();

        //Settting the base data on the base compound;
        tBaseCompound.setInteger("MaterialID", pMaterialID);
        tBaseCompound.setInteger("TotalDurability", ARegistry.iInstance.getMaterial(pMaterialID).getBaseDurability(pArmorID));
        tBaseCompound.setInteger("CurrentDurability", tBaseCompound.getInteger("TotalDurability"));
        tBaseCompound.setFloat("DamageAbsorption", ARegistry.iInstance.getMaterial(pMaterialID).getBaseDamageAbsorption(pArmorID));
        tBaseCompound.setInteger("MaxModifiers", ARegistry.iInstance.getMaterial(pMaterialID).getMaxModifiersOnPart(pArmorID));

        //Creating the initial Renderpass for the base armor layer.
        tBaseCompound.setInteger("RenderPasses", 0);
        tRenderCompound.setTag("RenderPass - 0", new NBTTagCompound());
        tRenderCompound.getCompoundTag("RenderPass - 0").setString("IconLocation", "Base");
        tRenderCompound.getCompoundTag("RenderPass - 0").setInteger("IconID", pMaterialID);

        //Creating the initial modifier and upgrade data
        tBaseCompound.setInteger("InstalledUpgrades", 0);
        tBaseCompound.setInteger("InstalledModifiers", 0);

        //Adding the initial RenderPass.
        tBaseCompound.setTag("RenderCompound", tRenderCompound);

        //Adding the initial BaseCompound to the itemstack
        tReturnStack.setTagCompound(tBaseCompound);

        return tReturnStack;
    }




}
