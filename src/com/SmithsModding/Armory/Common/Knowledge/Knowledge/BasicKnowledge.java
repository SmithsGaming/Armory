/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Knowledge;

import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipeKnowledge;

public class BasicKnowledge extends AnvilRecipeKnowledge {

    public BasicKnowledge() {
        super();

        this.iMinimalExperienceLevel = 0F;
        this.iMaximalExperienceLevel = 1F;
    }
}
