/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Recipe;

import com.SmithsModding.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.SmithsModding.Armory.Util.Core.XPUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;

public class VanillaAnvilRecipe extends AnvilRecipe {
    int iStackSizeToBeUsedInRepair;
    int iMaximumCost;
    TileEntityArmorsAnvil iEntity;
    ItemStack iLeftInputStack;
    ItemStack iRightInputStack;
    ItemStack iOutputStack;

    int iLevelPerPlayer;


    public VanillaAnvilRecipe(TileEntityArmorsAnvil pEntity) {
        iEntity = pEntity;
        iLeftInputStack = iEntity.getStackInSlot(11);
        iRightInputStack = iEntity.getStackInSlot(13);
        iOutputStack = null;
        iMaximumCost = 0;

        calculateValues();

        int tExperience = XPUtil.getExperienceForLevel(iMaximumCost);
        iLevelPerPlayer = XPUtil.getLevelForExperience((int) Math.ceil(tExperience / (float) iEntity.getConnectedPlayerCount()));
    }

    @Override
    public int getMinimumProgress() {
        return 3;
    }

    @Override
    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents) {
        return iOutputStack;
    }

    @Override
    public String getInternalID() {
        return "VanillaRepair";
    }

    @Override
    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft) {
        boolean tCreativePlayerConnected = false;

        for (EntityPlayer tPlayer : iEntity.getWatchingPlayers()) {
            if (tPlayer.capabilities.isCreativeMode)
                tCreativePlayerConnected = true;
        }

        if (!tCreativePlayerConnected) {
            float tExperience = XPUtil.getExperienceForLevel(iMaximumCost);
            int tXPPerPlayer = (int) Math.ceil(tExperience / (float) iEntity.getConnectedPlayerCount());

            if (iEntity.getConnectedPlayerCount() == 0)
                return false;

            for (EntityPlayer tPlayer : iEntity.getWatchingPlayers()) {
                if (XPUtil.getPlayerXP(tPlayer) < tXPPerPlayer)
                    return false;
            }
        }


        return iOutputStack != null;
    }

    @Override
    public boolean getUsesHammer() {
        return false;
    }

    @Override
    public boolean getUsesTongs() {
        return false;
    }

    @Override
    public void onRecipeUsed(TileEntityArmorsAnvil pEntity) {
        boolean tCreativePlayerConnected = false;

        for (EntityPlayer tPlayer : pEntity.getWatchingPlayers()) {
            if (tPlayer.capabilities.isCreativeMode)
                tCreativePlayerConnected = true;
        }

        if (!tCreativePlayerConnected) {
            float tExperience = XPUtil.getExperienceForLevel(iMaximumCost);
            int tXPPerPlayer = (int) Math.ceil(tExperience / (float) pEntity.getConnectedPlayerCount());

            for (EntityPlayer tPlayer : pEntity.getWatchingPlayers()) {
                XPUtil.addPlayerXP(tPlayer, -tXPPerPlayer);
            }
        }
    }

    public void ProcessPerformedCrafting() {
        iEntity.setInventorySlotContents(11, null);

        if (iRightInputStack != null) {
            iRightInputStack.stackSize -= iStackSizeToBeUsedInRepair;
            if (iRightInputStack.stackSize <= 0) {
                iRightInputStack = null;
            }
        }

        iEntity.setInventorySlotContents(13, iRightInputStack);
    }

    public int getRequiredLevelsPerPlayer() {
        return iLevelPerPlayer;
    }

    private boolean checkForge(ItemStack pLeftStack, ItemStack pRightStack, String pName, int pBaseCost) {
        AnvilUpdateEvent e = new AnvilUpdateEvent(pLeftStack, pRightStack, pName, pBaseCost);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        if (e.output == null) return true;

        this.iOutputStack = e.output;
        this.iMaximumCost = e.cost;
        this.iStackSizeToBeUsedInRepair = e.materialCost;
        return false;
    }

    private void calculateValues() {
        this.iMaximumCost = 0;
        int i = 0;
        byte b0 = 0;
        int j = 0;

        boolean tWatchersAreCreative = false;
        for (EntityPlayer tPlayer : iEntity.getWatchingPlayers()) {
            if (tPlayer.capabilities.isCreativeMode)
                tWatchersAreCreative = true;
        }

        if (iLeftInputStack == null) {
            this.iEntity.setInventorySlotContents(TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, null);
            this.iMaximumCost = 0;
        } else {
            ItemStack tEventStack = iLeftInputStack.copy();
            Map tEnchantmentsMap = EnchantmentHelper.getEnchantments(tEventStack);
            boolean tFlagIsEnchantableByBook = false;
            int tBaseLevelExperienceCost = b0 + iLeftInputStack.getRepairCost() + (iRightInputStack == null ? 0 : iRightInputStack.getRepairCost());
            this.iStackSizeToBeUsedInRepair = 0;
            int tRepairAmount;
            int l;
            int tEnchantmentID;
            int k1;
            int l1;
            Iterator tSecondStackEnchantmentIterator;
            Enchantment tEnchantment;

            if (iRightInputStack != null) {
                if (!checkForge(iLeftInputStack, iRightInputStack, iEntity.getOutputStackName(), tBaseLevelExperienceCost))
                    return;
                tFlagIsEnchantableByBook = iRightInputStack.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(iRightInputStack).tagCount() > 0;

                if (tEventStack.isItemStackDamageable() && tEventStack.getItem().getIsRepairable(iLeftInputStack, iRightInputStack)) {
                    tRepairAmount = Math.min(tEventStack.getItemDamageForDisplay(), tEventStack.getMaxDamage() / 4);

                    if (tRepairAmount <= 0) {
                        this.iOutputStack = null;
                        this.iMaximumCost = 0;
                        return;
                    }

                    for (l = 0; tRepairAmount > 0 && l < iRightInputStack.stackSize; ++l) {
                        tEnchantmentID = tEventStack.getItemDamageForDisplay() - tRepairAmount;
                        tEventStack.setItemDamage(tEnchantmentID);
                        i += Math.max(1, tRepairAmount / 100) + tEnchantmentsMap.size();
                        tRepairAmount = Math.min(tEventStack.getItemDamageForDisplay(), tEventStack.getMaxDamage() / 4);
                    }

                    this.iStackSizeToBeUsedInRepair = l;
                } else {
                    if (!tFlagIsEnchantableByBook && (tEventStack.getItem() != iRightInputStack.getItem() || !tEventStack.isItemStackDamageable())) {
                        this.iOutputStack = null;
                        this.iMaximumCost = 0;
                        return;
                    }

                    if (tEventStack.isItemStackDamageable() && !tFlagIsEnchantableByBook) {
                        tRepairAmount = iLeftInputStack.getMaxDamage() - iLeftInputStack.getItemDamageForDisplay();
                        l = iRightInputStack.getMaxDamage() - iRightInputStack.getItemDamageForDisplay();
                        tEnchantmentID = l + tEventStack.getMaxDamage() * 12 / 100;
                        int j1 = tRepairAmount + tEnchantmentID;
                        k1 = tEventStack.getMaxDamage() - j1;

                        if (k1 < 0) {
                            k1 = 0;
                        }

                        if (k1 < tEventStack.getItemDamage()) {
                            tEventStack.setItemDamage(k1);
                            i += Math.max(1, tEnchantmentID / 100);
                        }
                    }

                    Map tSecondStackEnchantments = EnchantmentHelper.getEnchantments(iRightInputStack);
                    tSecondStackEnchantmentIterator = tSecondStackEnchantments.keySet().iterator();

                    while (tSecondStackEnchantmentIterator.hasNext()) {
                        tEnchantmentID = ((Integer) tSecondStackEnchantmentIterator.next()).intValue();
                        tEnchantment = Enchantment.enchantmentsList[tEnchantmentID];
                        k1 = tEnchantmentsMap.containsKey(Integer.valueOf(tEnchantmentID)) ? ((Integer) tEnchantmentsMap.get(Integer.valueOf(tEnchantmentID))).intValue() : 0;
                        l1 = ((Integer) tSecondStackEnchantments.get(Integer.valueOf(tEnchantmentID))).intValue();
                        int i3;

                        if (k1 == l1) {
                            ++l1;
                            i3 = l1;
                        } else {
                            i3 = Math.max(l1, k1);
                        }

                        l1 = i3;
                        int i2 = l1 - k1;
                        boolean tCanEnchant = tEnchantment.canApply(iLeftInputStack);


                        if (tWatchersAreCreative || iLeftInputStack.getItem() == Items.enchanted_book) {
                            tCanEnchant = true;
                        }

                        Iterator iterator = tEnchantmentsMap.keySet().iterator();

                        while (iterator.hasNext()) {
                            int j2 = ((Integer) iterator.next()).intValue();

                            Enchantment e2 = Enchantment.enchantmentsList[j2];
                            if (j2 != tEnchantmentID && !(tEnchantment.canApplyTogether(e2) && e2.canApplyTogether(tEnchantment))) //Forge BugFix: Let Both enchantments veto being together
                            {
                                tCanEnchant = false;
                                i += i2;
                            }
                        }

                        if (tCanEnchant) {
                            if (l1 > tEnchantment.getMaxLevel()) {
                                l1 = tEnchantment.getMaxLevel();
                            }

                            tEnchantmentsMap.put(Integer.valueOf(tEnchantmentID), Integer.valueOf(l1));
                            int l2 = 0;

                            switch (tEnchantment.getWeight()) {
                                case 1:
                                    l2 = 8;
                                    break;
                                case 2:
                                    l2 = 4;
                                case 3:
                                case 4:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                default:
                                    break;
                                case 5:
                                    l2 = 2;
                                    break;
                                case 10:
                                    l2 = 1;
                            }

                            if (tFlagIsEnchantableByBook) {
                                l2 = Math.max(1, l2 / 2);
                            }

                            i += l2 * i2;
                        }
                    }
                }
            }

            if (StringUtils.isBlank(iEntity.getOutputStackName())) {
                if (iLeftInputStack.hasDisplayName()) {
                    j = iLeftInputStack.isItemStackDamageable() ? 7 : iLeftInputStack.stackSize * 5;
                    i += j;
                    tEventStack.func_135074_t();
                }
            } else if (!iEntity.getOutputStackName().equals(iLeftInputStack.getDisplayName())) {
                j = iLeftInputStack.isItemStackDamageable() ? 7 : iLeftInputStack.stackSize * 5;
                i += j;

                if (iLeftInputStack.hasDisplayName()) {
                    tBaseLevelExperienceCost += j / 2;
                }

                tEventStack.setStackDisplayName(iEntity.getOutputStackName());
            }

            tRepairAmount = 0;

            for (tSecondStackEnchantmentIterator = tEnchantmentsMap.keySet().iterator(); tSecondStackEnchantmentIterator.hasNext(); tBaseLevelExperienceCost += tRepairAmount + k1 * l1) {
                tEnchantmentID = ((Integer) tSecondStackEnchantmentIterator.next()).intValue();
                tEnchantment = Enchantment.enchantmentsList[tEnchantmentID];
                k1 = ((Integer) tEnchantmentsMap.get(Integer.valueOf(tEnchantmentID))).intValue();
                l1 = 0;
                ++tRepairAmount;

                switch (tEnchantment.getWeight()) {
                    case 1:
                        l1 = 8;
                        break;
                    case 2:
                        l1 = 4;
                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    default:
                        break;
                    case 5:
                        l1 = 2;
                        break;
                    case 10:
                        l1 = 1;
                }

                if (tFlagIsEnchantableByBook) {
                    l1 = Math.max(1, l1 / 2);
                }
            }

            if (tFlagIsEnchantableByBook) {
                tBaseLevelExperienceCost = Math.max(1, tBaseLevelExperienceCost / 2);
            }

            if (tFlagIsEnchantableByBook && !tEventStack.getItem().isBookEnchantable(tEventStack, iRightInputStack))
                tEventStack = null;

            this.iMaximumCost = tBaseLevelExperienceCost + i;

            if (i <= 0) {
                tEventStack = null;
            }

            if (j == i && j > 0 && this.iMaximumCost >= 40) {
                this.iMaximumCost = 39;
            }

            if (this.iMaximumCost >= 40 && !tWatchersAreCreative) {
                tEventStack = null;
            }

            if (tEventStack != null) {
                l = tEventStack.getRepairCost();

                if (iRightInputStack != null && l < iRightInputStack.getRepairCost()) {
                    l = iRightInputStack.getRepairCost();
                }

                if (tEventStack.hasDisplayName()) {
                    l -= 9;
                }

                if (l < 0) {
                    l = 0;
                }

                l += 2;
                tEventStack.setRepairCost(l);
                EnchantmentHelper.setEnchantments(tEnchantmentsMap, tEventStack);
            }

            iOutputStack = tEventStack;
        }
    }
}
