package com.smithsmodding.armory.common.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.references.ModInventories;
import com.smithsmodding.armory.api.references.ModItems;
import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.registry.MedievalAddonRegistry;
import com.smithsmodding.armory.common.factory.MedievalArmorFactory;
import com.smithsmodding.armory.common.item.armor.tiermedieval.ArmorMedieval;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Orion
 * Created on 28.05.2015
 * 21:24
 *
 * Copyrighted according to Project specific license
 */
public class ArmorUpgradeAnvilRecipe extends AnvilRecipe {
    private String iArmorType;
    private String iArmorMaterial;
    private ArrayList<Integer> iUpgradeComponents = new ArrayList<Integer>(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS);

    private String iTranslatedUpgrades = "";

    public ArmorUpgradeAnvilRecipe(String pArmorType, String pArmorMaterial) {
        iArmorType = pArmorType;
        iArmorMaterial = pArmorMaterial;
    }

    @Override
    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft) {
        if (pCraftingSlotContents[12] == null)
            return false;

        if (!(pCraftingSlotContents[12].getItem() instanceof ArmorMedieval))
            return false;

        if (!((ArmorMedieval) pCraftingSlotContents[12].getItem()).getUniqueID().equals(iArmorType))
            return false;

        if (!ArmorNBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]).equals(iArmorMaterial))
            return false;

        if (pHammerUsagesLeft == 0)
            pHammerUsagesLeft = 150;

        if (pTongsUsagesLeft == 0)
            pTongsUsagesLeft = 150;

        if ((getUsesHammer()) && (pHammerUsagesLeft) < getHammerUsage())
            return false;

        if ((getUsesTongs()) && (pTongsUsagesLeft < getTongsUsage()))
            return false;

        if (pCraftingSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (pAdditionalSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        for (int tSlotID = 0; tSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
            if (tSlotID == 12)
                continue;

            ItemStack tSlotContent = pCraftingSlotContents[tSlotID];

            if (tSlotContent != null) {
                if (getComponents()[tSlotID] == null) {
                    return false;
                } else if (!getComponent(tSlotID).isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (getComponents()[tSlotID] != null) {
                return false;
            }
        }

        for (int tSlotID = 0; tSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null) {
                if (getAdditionalComponents()[tSlotID] == null) {
                    return false;
                } else if (!getAdditionalComponents()[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (getAdditionalComponents()[tSlotID] != null) {
                return false;
            }
        }

        HashMap<MLAAddon, Integer> tNewAddons = new HashMap<MLAAddon, Integer>();
        for (Integer tIndex : iUpgradeComponents) {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalAddonRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
            if (tAddon == null)
                return false;

            if (tNewAddons.containsKey(tAddon)) {
                Integer tNewValue = tNewAddons.get(tAddon) + tUpgradeStack.stackSize;
                tNewAddons.remove(tAddon);
                tNewAddons.put(tAddon, tNewValue);
            } else {
                tNewAddons.put(tAddon, tUpgradeStack.stackSize);
            }
        }

        Integer tNewMaxDamage = pCraftingSlotContents[12].getMaxDamage();
        for (MLAAddon tAddon : tNewAddons.keySet()) {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            tNewMaxDamage += tUpgrade.getExtraDurability();
        }

        ItemStack tArmorStack = MedievalArmorFactory.getInstance().buildMLAArmor((MultiLayeredArmor) pCraftingSlotContents[12].getItem(), pCraftingSlotContents[12], tNewAddons, tNewMaxDamage, ArmorNBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]), "");

        return (!(tArmorStack == null));
    }

    @Override
    public IAnvilRecipeComponent getComponent(int pComponentIndex) {
        if (pComponentIndex == 12) {
            return new StandardAnvilRecipeComponent(new ItemStack(ModItems.metalRing)) {
                @Override
                public ItemStack getComponentTargetStack() {
                    return MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(iArmorType), new HashMap<MLAAddon, Integer>(), MaterialRegistry.getInstance().getMaterial(iArmorMaterial).getBaseDurability(iArmorType), iArmorMaterial);
                }

                @Override
                public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
                    return 0;
                }
            };
        }

        if (pComponentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return getComponents()[pComponentIndex];
    }

    @Override
    public ArmorUpgradeAnvilRecipe setCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        getComponents()[pSlotIndex] = pComponent;

        return this;
    }

    public ArmorUpgradeAnvilRecipe setUpgradeCraftingSlotComponent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        if (!iUpgradeComponents.contains(pSlotIndex))
            iUpgradeComponents.add(pSlotIndex);

        return setCraftingSlotContent(pSlotIndex, pComponent);
    }

    @Override
    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents) {
        HashMap<MLAAddon, Integer> tNewAddons = new HashMap<MLAAddon, Integer>();
        for (Integer tIndex : iUpgradeComponents) {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalAddonRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID) + "-" + tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Material));
            if (tAddon == null)
                return null;

            if (tNewAddons.containsKey(tAddon)) {
                Integer tNewValue = tNewAddons.get(tAddon) + tUpgradeStack.stackSize;
                tNewAddons.remove(tAddon);
                tNewAddons.put(tAddon, tNewValue);
            } else {
                tNewAddons.put(tAddon, tUpgradeStack.stackSize);
            }
        }

        Integer tNewMaxDamage = pCraftingSlotContents[12].getMaxDamage();
        for (MLAAddon tAddon : tNewAddons.keySet()) {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) tAddon;
            tNewMaxDamage += tUpgrade.getExtraDurability();
        }

        return MedievalArmorFactory.getInstance().buildMLAArmor((MultiLayeredArmor) pCraftingSlotContents[12].getItem(), pCraftingSlotContents[12], tNewAddons, tNewMaxDamage, ArmorNBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]));
    }
}
