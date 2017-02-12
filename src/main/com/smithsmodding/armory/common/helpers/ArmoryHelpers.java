package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.helpers.IArmoryHelpers;
import com.smithsmodding.armory.api.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.helpers.IMedievalUpgradeConstructionHelper;
import org.jetbrains.annotations.NotNull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ArmoryHelpers implements IArmoryHelpers {

    @NotNull
    private static ArmoryHelpers instance = new ArmoryHelpers();

    private ArmoryHelpers() {
    }

    @NotNull
    public static ArmoryHelpers getInstance() {
        return instance;
    }

    @NotNull
    @Override
    public IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper() {
        return MedievalUpgradeConstructionHelper.getInstance();
    }

    @NotNull
    @Override
    public IMaterialConstructionHelper getMaterialConstructionHelper() {
        return MaterialConstructionHelper.getInstance();
    }
}
