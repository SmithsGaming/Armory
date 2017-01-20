package com.smithsmodding.armory.common.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 28.05.2015
 * 21:24
 * <p>
 * Copyrighted according to Project specific license
 */
public class ArmorUpgradeAnvilRecipe extends AnvilRecipe {
    private IMultiComponentArmor armor;
    private ICoreArmorMaterial coreArmorMaterial;

    @Nonnull
    private ArrayList<Integer> upgradeComponents = new ArrayList<Integer>(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS);

    public ArmorUpgradeAnvilRecipe(IMultiComponentArmor armor, ICoreArmorMaterial coreArmorMaterial) {
        this.armor = armor;
        this.coreArmorMaterial = coreArmorMaterial;
    }

    @Override
    public boolean matchesRecipe(@Nonnull ItemStack[] craftingSlotContents, @Nonnull ItemStack[] additionalSlotContents, int hammerUsagesLeft, int tongsUsagesLeft) {
        ItemStack armorStack = craftingSlotContents[12];

        if (!armorStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return false;

        IMultiComponentArmorCapability capability = armorStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        if (!capability.getArmorType().equals(armor))
            return false;

        if (!capability.getMaterial().equals(coreArmorMaterial))
            return false;

        if (hammerUsagesLeft == 0)
            hammerUsagesLeft = 150;

        if (tongsUsagesLeft == 0)
            tongsUsagesLeft = 150;

        if ((getUsesHammer()) && (hammerUsagesLeft) < getHammerUsage())
            return false;

        if ((getUsesTongs()) && (tongsUsagesLeft < getTongsUsage()))
            return false;

        if (craftingSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (additionalSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        for (int slot = 0; slot < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; slot++) {
            if (slot == 12)
                continue;

            ItemStack slotContent = craftingSlotContents[slot];

            if (slotContent != null) {
                if (getComponents()[slot] == null) {
                    return false;
                } else if (!getComponent(slot).isValidComponentForSlot(slotContent)) {
                    return false;
                }
            } else if (getComponents()[slot] != null) {
                return false;
            }
        }

        for (int slot = 0; slot < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; slot++) {
            ItemStack slotContent = additionalSlotContents[slot];

            if (slotContent != null) {
                if (getAdditionalComponents()[slot] == null) {
                    return false;
                } else if (!getAdditionalComponents()[slot].isValidComponentForSlot(slotContent)) {
                    return false;
                }
            } else if (getAdditionalComponents()[slot] != null) {
                return false;
            }
        }

        try {
            ItemStack newArmorStack = buildItemStack(craftingSlotContents, additionalSlotContents);
            return newArmorStack != null;
        } catch (IllegalArgumentException argEx) {
            return false;
        }
    }

    @Nullable
    @Override
    public IAnvilRecipeComponent getComponent(int componentIndex) {
        if (componentIndex == 12) {
            return new StandardAnvilRecipeComponent(new ItemStack(ModItems.metalRing)) {
                @Nullable
                @Override
                public ItemStack getComponentTargetStack() {
                    return ArmorFactory.getInstance().buildNewMLAArmor(armor, new ArrayList<>(), coreArmorMaterial.getBaseDurabilityForArmor(armor), coreArmorMaterial);
                }

                @Override
                public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
                    return 0;
                }
            };
        }

        if (componentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return getComponents()[componentIndex];
    }

    @Nullable
    @Override
    public ArmorUpgradeAnvilRecipe setCraftingSlotContent(int slotIndex, IAnvilRecipeComponent component) {
        if (slotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (slotIndex == 12)
            return null;

        getComponents()[slotIndex] = component;

        return this;
    }

    @Nullable
    public ArmorUpgradeAnvilRecipe setUpgradeCraftingSlotComponent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        if (!upgradeComponents.contains(pSlotIndex))
            upgradeComponents.add(pSlotIndex);

        return setCraftingSlotContent(pSlotIndex, pComponent);
    }

    @Nullable
    @Override
    public ItemStack getResult(ItemStack[] craftingSlotContents, ItemStack[] additionalSlotContents) {
        return buildItemStack(craftingSlotContents, additionalSlotContents);
    }

    private ItemStack buildItemStack(ItemStack[] craftingSlotContents, ItemStack[] additionalSlotContents) {
        ItemStack armorStack = craftingSlotContents[12];

        if (!armorStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            throw new IllegalArgumentException("ArmorStack is not Armor");

        IMultiComponentArmorCapability capability = armorStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        ArrayList<IMultiComponentArmorExtensionInformation> extensionInformationData = ArmorNBTHelper.getAddonMap(armorStack);
        ArrayList<IMultiComponentArmorExtensionInformation> newExtensionInformationData = new ArrayList<>();

        for (Integer index : upgradeComponents) {
            ItemStack upgradeStack = craftingSlotContents[index];

            if (!upgradeStack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
                throw new IllegalArgumentException("ADDONS not a Addon");

            IArmorComponentStackCapability upgradeCapability = upgradeStack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null);
            newExtensionInformationData.add(new IMultiComponentArmorExtensionInformation.Impl().setExtension(upgradeCapability.getExtension())
                    .setPosition(upgradeCapability.getExtension().getPosition())
                    .setCount(upgradeStack.getCount()));
        }

        ArrayList<IMultiComponentArmorExtensionInformation> compressedInformation = ArmorFactory.getInstance().compressInformation(extensionInformationData, newExtensionInformationData);

        Integer newMaxDurability = capability.getMaximalDurability();
        for (IMultiComponentArmorExtensionInformation extensionInformation : compressedInformation) {
            newMaxDurability += extensionInformation.getExtension().getAdditionalDurability();
        }

        return ArmorFactory.getInstance().buildMLAArmor(capability.getArmorType(), craftingSlotContents[12], newExtensionInformationData, newMaxDurability, capability.getMaterial(), "");
    }
}
