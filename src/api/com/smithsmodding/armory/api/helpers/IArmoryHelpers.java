package com.smithsmodding.armory.api.helpers;

import com.smithsmodding.armory.api.factories.IFactoryController;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IArmoryHelpers {

    IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper();

    IMaterialConstructionHelper getMaterialConstructionHelper();

    IFactoryController getFactories();
}
