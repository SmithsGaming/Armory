package com.Orion.Armory.Common.Crafting.Anvil;

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMedieval;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorUpgradeMedieval;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Util.Armor.NBTHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.item.ItemStack;

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

        if ((iHammerUsage > 0) && (pHammerUsagesLeft) < iHammerUsage)
            return false;

        if ((iTongUsage > 0) && (pTongsUsagesLeft < iTongUsage))
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
                if (iComponents[tSlotID] == null) {
                    return false;
                } else if (!iComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (iComponents[tSlotID] != null) {
                return false;
            }
        }

        for (int tSlotID = 0; tSlotID < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null) {
                if (iAdditionalComponents[tSlotID] == null) {
                    return false;
                } else if (!iAdditionalComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (iAdditionalComponents[tSlotID] != null) {
                return false;
            }
        }

        HashMap<MLAAddon, Integer> tNewAddons = new HashMap<MLAAddon, Integer>();
        for (Integer tIndex : iUpgradeComponents)
        {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
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
                    return MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(iArmorType), new HashMap<MLAAddon, Integer>(), MedievalRegistry.getInstance().getMaterial(iArmorMaterial).getBaseDurability(iArmorType), iArmorMaterial);
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

        return iComponents[pComponentIndex];
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

        iComponents[pSlotIndex] = pComponent;

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
    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents)
    {
        HashMap<MLAAddon, Integer> tNewAddons = new HashMap<MLAAddon, Integer>();
        for (Integer tIndex : iUpgradeComponents)
        {
            ItemStack tUpgradeStack = pCraftingSlotContents[tIndex];
            MLAAddon tAddon = MedievalRegistry.getInstance().getUpgrade(tUpgradeStack.getTagCompound().getString(References.NBTTagCompoundData.Addons.AddonID));
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
