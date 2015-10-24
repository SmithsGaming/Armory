/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Blueprint;

import com.SmithsModding.Armory.Common.Config.ArmoryConfig;

public class HardBlueprint extends BasicBlueprint {

    public HardBlueprint(String pID, String pRecipeID) {
        super(pID, pRecipeID);

        this.iMinFloatValue = 0F;
        this.iMaxFloatValue = 0.65F;
    }

    @Override
    public float getQualityDecrementOnTick(boolean pInGuide) {
        if (pInGuide)
            return ArmoryConfig.hardBlueprintDeteriation;

        return ArmoryConfig.hardBlueprintDeteriationInInventory;
    }
}
