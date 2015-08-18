package com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe;

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Components.IAnvilRecipeComponent;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Components.StandardAnvilRecipeComponent;
import com.Orion.Armory.Common.Addons.ArmorUpgradeMedieval;
import com.Orion.Armory.Common.Addons.MedievalAddonRegistry;
import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMedieval;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Orion
 * Created on 28.05.2015
 * 21:24
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmorUpgradeAnvilRecipe extends AnvilRecipe
{
    private String iArmorType;
    private String iArmorMaterial;
    private ArrayList<Integer> iUpgradeComponents = new ArrayList<Integer>(TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS);

    private String iTranslatedUpgrades = "";

    public ArmorUpgradeAnvilRecipe(String pArmorType, String pArmorMaterial)
    {
        iArmorType = pArmorType;
        iArmorMaterial = pArmorMaterial;
    }

    @Override
    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft)
    {
        if (pCraftingSlotContents[12] == null)
            return false;

        if (!(pCraftingSlotContents[12].getItem() instanceof ArmorMedieval))
            return false;

        if (!((ArmorMedieval) pCraftingSlotContents[12].getItem()).getInternalName().equals(iArmorType))
            return false;

        if (!NBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]).equals(iArmorMaterial))
            return false;

        if (pHammerUsagesLeft == 0)
            pHammerUsagesLeft = 150;

        if (pTongsUsagesLeft == 0)
            pTongsUsagesLeft = 150;

        if ((getUsesHammer()) && (pHammerUsagesLeft) < getHammerUsage())
            return false;

        if ((getUsesTongs()) && (pTongsUsagesLeft < getTongsUsage()))
            return false;

        if (pCraftingSlotContents.length > TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (pAdditionalSlotContents.length > TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        for (int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
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

        for (int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
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
        for (Integer tIndex : iUpgradeComponents)
        {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalAddonRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
            if (tAddon == null )
                    return false;

            if (tNewAddons.containsKey(tAddon))
            {
                Integer tNewValue = tNewAddons.get(tAddon) + tUpgradeStack.stackSize;
                tNewAddons.remove(tAddon);
                tNewAddons.put(tAddon, tNewValue);
            }
            else {
                tNewAddons.put(tAddon, tUpgradeStack.stackSize);
            }
        }

        Integer tNewMaxDamage = pCraftingSlotContents[12].getMaxDamage();
        for(MLAAddon tAddon : tNewAddons.keySet())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval)tAddon;
            tNewMaxDamage += tUpgrade.iExtraDurability;
        }

        ItemStack tArmorStack = MedievalArmorFactory.getInstance().buildMLAArmor((MultiLayeredArmor) pCraftingSlotContents[12].getItem(), pCraftingSlotContents[12], tNewAddons, tNewMaxDamage, NBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]), "");

        return (!(tArmorStack == null));
    }

    @Override
    public IAnvilRecipeComponent getComponent(int pComponentIndex)
    {
        if( pComponentIndex == 12)
        {
            return new StandardAnvilRecipeComponent(new ItemStack(GeneralRegistry.Items.iMetalRing))
            {
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

        if (pComponentIndex >= TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return null;
        }

        return getComponents()[pComponentIndex];
    }

    @Override
    public ArmorUpgradeAnvilRecipe setCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent)
    {
        if (pSlotIndex >= TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        getComponents()[pSlotIndex] = pComponent;

        return this;
    }

    public ArmorUpgradeAnvilRecipe setUpgradeCraftingSlotComponent(int pSlotIndex, IAnvilRecipeComponent pComponent)
    {
        if (pSlotIndex >= TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS)
        {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        if (!iUpgradeComponents.contains(pSlotIndex))
            iUpgradeComponents.add(pSlotIndex);

        return setCraftingSlotContent(pSlotIndex, pComponent);
    }

    @Override
    public String getTranslateResultName() {
        if (iResult == null) {
            iResult = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(iArmorType), new HashMap<MLAAddon, Integer>(), MaterialRegistry.getInstance().getMaterial(iArmorMaterial).getBaseDurability(iArmorType), iArmorMaterial);
        }

        if (iTranslatedUpgrades == "") {
            for (int tStackIndex : iUpgradeComponents) {
                ItemStack tStack = (getComponent(tStackIndex)).getComponentTargetStack();

                iTranslatedUpgrades += tStack.getItem().getItemStackDisplayName(tStack);

                if ((iUpgradeComponents.get(tStackIndex) + 1) != iUpgradeComponents.size()) {
                    iTranslatedUpgrades += ", ";
                }
            }
        }


        return StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Upgrade1) + " " + iResult.getItem().getItemStackDisplayName(iResult) + " " + StatCollector.translateToLocal(TranslationKeys.Knowledge.Blueprint.Upgrade2) + " " + iTranslatedUpgrades;
    }

    @Override
    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents)
    {
        HashMap<MLAAddon, Integer> tNewAddons = new HashMap<MLAAddon, Integer>();
        for (Integer tIndex : iUpgradeComponents)
        {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalAddonRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
            if (tAddon == null )
                return null;

            if (tNewAddons.containsKey(tAddon))
            {
                Integer tNewValue = tNewAddons.get(tAddon) + tUpgradeStack.stackSize;
                tNewAddons.remove(tAddon);
                tNewAddons.put(tAddon, tNewValue);
            }
            else {
                tNewAddons.put(tAddon, tUpgradeStack.stackSize);
            }
        }

        Integer tNewMaxDamage = pCraftingSlotContents[12].getMaxDamage();
        for(MLAAddon tAddon : tNewAddons.keySet())
        {
            ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval)tAddon;
            tNewMaxDamage += tUpgrade.iExtraDurability;
        }

        return MedievalArmorFactory.getInstance().buildMLAArmor((MultiLayeredArmor) pCraftingSlotContents[12].getItem(), pCraftingSlotContents[12], tNewAddons, tNewMaxDamage, NBTHelper.getArmorBaseMaterialName(pCraftingSlotContents[12]));
    }
}
