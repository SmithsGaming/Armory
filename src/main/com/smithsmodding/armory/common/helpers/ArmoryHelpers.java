package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.factories.IFactoryController;
import com.smithsmodding.armory.api.helpers.IArmoryHelpers;
import com.smithsmodding.armory.api.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.helpers.IMedievalUpgradeConstructionHelper;
import com.smithsmodding.armory.common.factories.FactoryController;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ArmoryHelpers implements IArmoryHelpers {

    @Nonnull
    private static ArmoryHelpers instance = new ArmoryHelpers();

    private ArmoryHelpers() {
    }

    @Nonnull
    public static ArmoryHelpers getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper() {
        return MedievalUpgradeConstructionHelper.getInstance();
    }

    @Nonnull
    @Override
    public IMaterialConstructionHelper getMaterialConstructionHelper() {
        return MaterialConstructionHelper.getInstance();
    }

    @Override
    public IFactoryController getFactories() {
        return FactoryController.getInstance();
    }
}
