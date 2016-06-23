/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import com.smithsmodding.smithscore.util.common.XPUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

public class VanillaAnvilRecipe extends AnvilRecipe {
    int iStackSizeToBeUsedInRepair;
    int iMaximumCost;
    TileEntityBlackSmithsAnvil iEntity;
    ItemStack iLeftInputStack;
    ItemStack iRightInputStack;
    ItemStack iOutputStack;

    int iLevelPerPlayer;


    public VanillaAnvilRecipe(TileEntityBlackSmithsAnvil pEntity) {
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

        for (UUID playerId : iEntity.getWatchingPlayers()) {
            if (PlayerManager.getInstance().getServerSidedJoinedMap().get(playerId).capabilities.isCreativeMode)
                tCreativePlayerConnected = true;
        }

        if (!tCreativePlayerConnected) {
            float tExperience = XPUtil.getExperienceForLevel(iMaximumCost);
            int tXPPerPlayer = (int) Math.ceil(tExperience / (float) iEntity.getConnectedPlayerCount());

            if (iEntity.getConnectedPlayerCount() == 0)
                return false;

            for (UUID playerId : iEntity.getWatchingPlayers()) {
                if (XPUtil.getPlayerXP(PlayerManager.getInstance().getServerSidedJoinedMap().get(playerId)) < tXPPerPlayer)
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
    public void onRecipeUsed(TileEntityBlackSmithsAnvil pEntity) {
        boolean tCreativePlayerConnected = false;

        for (UUID playerId : iEntity.getWatchingPlayers()) {
            if (PlayerManager.getInstance().getServerSidedJoinedMap().get(playerId).capabilities.isCreativeMode)
                tCreativePlayerConnected = true;
        }

        if (!tCreativePlayerConnected) {
            float tExperience = XPUtil.getExperienceForLevel(iMaximumCost);
            int tXPPerPlayer = (int) Math.ceil(tExperience / (float) pEntity.getConnectedPlayerCount());

            for (UUID playerId : pEntity.getWatchingPlayers()) {
                XPUtil.addPlayerXP(PlayerManager.getInstance().getServerSidedJoinedMap().get(playerId), -tXPPerPlayer);
            }
        }
    }

    public void ProcessPerformedCrafting() {
        iEntity.setInventorySlotContents(11, null);

        if (iRightInputStack != null) {
            if (iRightInputStack.getItem() instanceof ItemEnchantedBook) {
                iRightInputStack = null;
            } else {
                iRightInputStack.stackSize -= iStackSizeToBeUsedInRepair;
                if (iRightInputStack.stackSize <= 0) {
                    iRightInputStack = null;
                }
            }
        }

        iEntity.setInventorySlotContents(13, iRightInputStack);
        iEntity.getState().setItemName("");
    }

    public int getRequiredLevelsPerPlayer() {
        return iLevelPerPlayer;
    }

    private boolean checkForge(ItemStack pLeftStack, ItemStack pRightStack, String pName, int pBaseCost) {
        AnvilUpdateEvent e = new AnvilUpdateEvent(pLeftStack, pRightStack, pName, pBaseCost);
        if (MinecraftForge.EVENT_BUS.post(e)) return false;
        if (e.getOutput() == null) return true;

        this.iOutputStack = e.getOutput();
        this.iMaximumCost = e.getCost();
        this.iStackSizeToBeUsedInRepair = e.getMaterialCost();
        return false;
    }

    private void calculateValues() {
        this.iMaximumCost = 0;
        int i = 0;
        byte b0 = 0;
        int j = 0;

        boolean tWatchersAreCreative = false;
        for (UUID playerId : iEntity.getWatchingPlayers()) {
            if (PlayerManager.getInstance().getServerSidedJoinedMap().get(playerId).capabilities.isCreativeMode)
                tWatchersAreCreative = true;
        }

        if (iLeftInputStack == null) {
            this.iEntity.setInventorySlotContents(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, null);
            this.iMaximumCost = 0;
        } else {
            ItemStack tEventStack = iLeftInputStack.copy();
            Map<Enchantment, Integer> tEnchantmentsMap = EnchantmentHelper.getEnchantments(tEventStack);
            boolean tFlagIsEnchantableByBook = false;
            int tBaseLevelExperienceCost = b0 + iLeftInputStack.getRepairCost() + (iRightInputStack == null ? 0 : iRightInputStack.getRepairCost());
            this.iStackSizeToBeUsedInRepair = 0;
            int tRepairAmount;
            int l;
            int tEnchantmentID;
            int k1;

            if (iRightInputStack != null) {
                if (!checkForge(iLeftInputStack, iRightInputStack, iEntity.getState().getItemName(), tBaseLevelExperienceCost))
                    return;
                tFlagIsEnchantableByBook = iRightInputStack.getItem() == Items.ENCHANTED_BOOK && Items.ENCHANTED_BOOK.getEnchantments(iRightInputStack).tagCount() > 0;

                if (tEventStack.isItemStackDamageable() && tEventStack.getItem().getIsRepairable(iLeftInputStack, iRightInputStack)) {
                    tRepairAmount = Math.min(tEventStack.getItem().getDamage(tEventStack), tEventStack.getMaxDamage() / 4);

                    if (tRepairAmount <= 0) {
                        this.iOutputStack = null;
                        this.iMaximumCost = 0;
                        return;
                    }

                    for (l = 0; tRepairAmount > 0 && l < iRightInputStack.stackSize; ++l) {
                        tEnchantmentID = tEventStack.getItem().getDamage(tEventStack) - tRepairAmount;
                        tEventStack.setItemDamage(tEnchantmentID);
                        i += Math.max(1, tRepairAmount / 100) + tEnchantmentsMap.size();
                        tRepairAmount = Math.min(tEventStack.getItem().getDamage(tEventStack), tEventStack.getMaxDamage() / 4);
                    }

                    this.iStackSizeToBeUsedInRepair = l;
                } else {
                    if (!tFlagIsEnchantableByBook && (tEventStack.getItem() != iRightInputStack.getItem() || !tEventStack.isItemStackDamageable())) {
                        this.iOutputStack = null;
                        this.iMaximumCost = 0;
                        return;
                    }

                    if (tEventStack.isItemStackDamageable() && !tFlagIsEnchantableByBook) {
                        tRepairAmount = iLeftInputStack.getMaxDamage() - iLeftInputStack.getItem().getDamage(tEventStack);
                        l = iRightInputStack.getMaxDamage() - iRightInputStack.getItem().getDamage(tEventStack);
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

                    Map<Enchantment, Integer> tSecondStackEnchantments = EnchantmentHelper.getEnchantments(iRightInputStack);

                    for (Enchantment enchantment1 : tSecondStackEnchantments.keySet()) {
                        if (enchantment1 != null) {
                            int i3 = tEnchantmentsMap.containsKey(enchantment1) ? tEnchantmentsMap.get(enchantment1).intValue() : 0;
                            int j3 = tSecondStackEnchantments.get(enchantment1).intValue();
                            j3 = i3 == j3 ? j3 + 1 : Math.max(j3, i3);
                            boolean flag1 = enchantment1.canApply(tEventStack);

                            if (tWatchersAreCreative || tEventStack.getItem() == Items.ENCHANTED_BOOK) {
                                flag1 = true;
                            }

                            for (Enchantment enchantment : tEnchantmentsMap.keySet()) {
                                if (enchantment != enchantment1 && !(enchantment1.canApplyTogether(enchantment) && enchantment.canApplyTogether(enchantment1)))  //Forge BugFix: Let Both enchantments veto being together
                                {
                                    flag1 = false;
                                    ++i;
                                }
                            }

                            if (flag1) {
                                if (j3 > enchantment1.getMaxLevel()) {
                                    j3 = enchantment1.getMaxLevel();
                                }

                                tEnchantmentsMap.put(enchantment1, Integer.valueOf(j3));
                                int k3 = 0;

                                switch (enchantment1.getRarity()) {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (tFlagIsEnchantableByBook) {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j3;
                            }
                        }
                    }
                }
            }

            if (tFlagIsEnchantableByBook && !tEventStack.getItem().isBookEnchantable(tEventStack, iRightInputStack))
                tEventStack = null;

            if (StringUtils.isBlank(iEntity.getState().getItemName())) {
                if (iLeftInputStack.hasDisplayName()) {
                    j = 1;
                    i += j;
                    tEventStack.clearCustomName();
                }
            } else if (!iEntity.getState().getItemName().equals(iLeftInputStack.getDisplayName())) {
                j = 1;
                i += j;
                tEventStack.setStackDisplayName(iEntity.getState().getItemName());
            }

            this.iMaximumCost = j + i;

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
                int i2 = tEventStack.getRepairCost();

                if (iRightInputStack != null && i2 < iRightInputStack.getRepairCost()) {
                    i2 = iRightInputStack.getRepairCost();
                }

                if (j != i || j == 0) {
                    i2 = i2 * 2 + 1;
                }

                tEventStack.setRepairCost(i2);
                EnchantmentHelper.setEnchantments(tEnchantmentsMap, tEventStack);
            }

            iOutputStack = tEventStack;
        }
    }
}
