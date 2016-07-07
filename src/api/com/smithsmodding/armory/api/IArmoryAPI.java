package com.smithsmodding.armory.api;

import com.smithsmodding.armory.api.registries.*;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IArmoryAPI
{
    IAnvilMaterialRegistry getAnvilMaterialRegistry();

    IAnvilRecipeRegistry getAnvilRecipeRegistry();

    IHeatableItemRegistry getHeatableItemRegistry();

    IArmorPartRegistry getMedievalArmorPartRegistry();

    IMaterialRegistry getArmorMaterialRegistry();
}
