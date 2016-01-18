package com.smithsmodding.armory.api.materials;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 14:52
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IArmorConfigurator {
    void loadIDs();

    void loadIsBaseArmorMaterial();

    void loadActiveParts();

    void loadBaseDamageAbsorptions();

    void loadPartModifiers();

    void loadBaseDurability();

    void loadTemperatureCoefficient();

    void loadMeltingPoint();

    void loadColorSettings();
}
