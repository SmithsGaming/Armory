package com.Orion.Armory.API.Materials;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 14:52
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IArmorConfigurator
{

    void loadIsBaseArmorMaterial();

    void loadActiveParts();

    void loadBaseDamageAbsorptions();

    void loadPartModifiers();

    void loadBaseDurability();

    void loadTemperatureCoefficient();

    void loadMeltingPoint();
}
