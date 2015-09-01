/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Util.Core;

import net.minecraft.entity.player.EntityPlayer;

public class XPUtil {

    //CREDITS: crazypants and the EnderIO Team: https://raw.githubusercontent.com/SleepyTrousers/EnderIO/master/src/main/java/crazypants/enderio/xp/XpUtil.java
    //Values taken from OpenBlocks to ensure compatibility

    public static final int XP_PER_BOTTLE = 8;
    public static final int RATIO = 20;
    public static final int LIQUID_PER_XP_BOTTLE = XP_PER_BOTTLE * RATIO;


    public static int liquidToExperience(int pLiquidAmount) {
        return pLiquidAmount / RATIO;
    }

    public static int experienceToLiquid(int pXPAmount) {
        return pXPAmount * RATIO;
    }

    public static int getLiquidForLevel(int pLevelAmount) {
        return experienceToLiquid(getExperienceForLevel(pLevelAmount));
    }

    public static int getExperienceForLevel(int pLevel) {
        if (pLevel == 0) {
            return 0;
        }
        if (pLevel > 0 && pLevel < 16) {
            return pLevel * 17;
        } else if (pLevel > 15 && pLevel < 31) {
            return (int) (1.5 * Math.pow(pLevel, 2) - 29.5 * pLevel + 360);
        } else {
            return (int) (3.5 * Math.pow(pLevel, 2) - 151.5 * pLevel + 2220);
        }
    }

    public static int getXpBarCapacity(int pLevel) {
        return pLevel >= 30 ? 62 + (pLevel - 30) * 7 : (pLevel >= 15 ? 17 + (pLevel - 15) * 3 : 17);
    }

    public static int getLevelForExperience(int pExperienceAmount) {
        int i = 0;
        while (getExperienceForLevel(i) <= pExperienceAmount) {
            i++;
        }
        return i - 1;
    }

    public static int getPlayerXP(EntityPlayer pPlayer) {
        return (int) (getExperienceForLevel(pPlayer.experienceLevel) + (pPlayer.experience * pPlayer.xpBarCap()));
    }

    public static void addPlayerXP(EntityPlayer pPlayer, int pDelta) {
        int experience = getPlayerXP(pPlayer) + pDelta;
        pPlayer.experienceTotal = experience;
        pPlayer.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(pPlayer.experienceLevel);
        pPlayer.experience = (float) (experience - expForLevel) / (float) pPlayer.xpBarCap();
    }
}
