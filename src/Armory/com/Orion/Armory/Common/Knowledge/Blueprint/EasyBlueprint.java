/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge.Blueprint;

import com.Orion.Armory.Common.Config.ArmoryConfig;

public class EasyBlueprint extends BasicBlueprint {

    public EasyBlueprint(String pID, String pRecipeID) {
        super(pID, pRecipeID);

        this.iMinFloatValue = 0.45F;
        this.iMaxFloatValue = 0.89F;
    }

    @Override
    public float getQualityDecrementOnTick(boolean pInGuide) {
        if (pInGuide)
            return ArmoryConfig.easyBlueprintDeteriation;

        return ArmoryConfig.easyBlueprintDeteriationInInventory;
    }
}
