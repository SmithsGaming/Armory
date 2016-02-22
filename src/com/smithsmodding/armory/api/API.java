package com.smithsmodding.armory.api;

import com.smithsmodding.armory.api.registries.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class API
{
    private static IAnvilMaterialRegistry anvilMaterialRegistry;

    public static IAnvilMaterialRegistry getAnvilMaterialRegistry()
    {
        return anvilMaterialRegistry;
    }
}
