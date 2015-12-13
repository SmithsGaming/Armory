/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Knowledge;

import java.util.*;

public class BlueprintRegistry {
    private static BlueprintRegistry iInstance = new BlueprintRegistry();

    HashMap<String, IBlueprint> iBlueprints = new HashMap<String, IBlueprint>();
    HashMap<String, IBlueprint> iMappedBlueprints = new HashMap<String, IBlueprint>();

    public static BlueprintRegistry getInstance () {
        return iInstance;
    }

    public void registerNewBluePrint (IBlueprint pNewBlueprint) {
        iBlueprints.put(pNewBlueprint.getID(), pNewBlueprint);
        iMappedBlueprints.put(pNewBlueprint.getRecipeID(), pNewBlueprint);
    }

    public IBlueprint getBluePrintForRecipe (String pRecipeID) {
        return iMappedBlueprints.get(pRecipeID);
    }

    public IBlueprint getBlueprint (String pBlueprintID) {
        return iBlueprints.get(pBlueprintID);
    }

    public Collection<IBlueprint> getBlueprints () {
        return iBlueprints.values();
    }
}
