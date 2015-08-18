/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge;

public class HardBlueprint extends BasicBlueprint {

    public HardBlueprint(String pID, String pRecipeID) {
        super(pID, pRecipeID);

        this.iMinFloatValue = 0F;
        this.iMaxFloatValue = 0.65F;
    }
}
