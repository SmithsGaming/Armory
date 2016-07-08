package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.helpers.IArmoryHelpers;
import com.smithsmodding.armory.api.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.helpers.IMedievalUpgradeConstructionHelper;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ArmoryHelpers implements IArmoryHelpers {

    private static ArmoryHelpers instance = new ArmoryHelpers();

    private ArmoryHelpers() {
    }

    public static ArmoryHelpers getInstance() {
        return instance;
    }

    @Override
    public IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper() {
        return MedievalUpgradeConstructionHelper.getInstance();
    }

    @Override
    public IMaterialConstructionHelper getMaterialConstructionHelper() {
        return MaterialConstructionHelper.getInstance();
    }
}
