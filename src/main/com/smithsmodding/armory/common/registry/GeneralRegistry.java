package com.smithsmodding.armory.common.registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.helpers.IArmoryHelpers;
import com.smithsmodding.armory.api.registries.*;
import com.smithsmodding.armory.common.helpers.ArmoryHelpers;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Properties;

public class GeneralRegistry implements IArmoryAPI {
    public static boolean isInDevEnvironment = false;
    @NotNull
    private static GeneralRegistry instance = new GeneralRegistry();

    @NotNull
    private HashMap<String, String> requestedAPICallbacks = new HashMap<>();

    private GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        isInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("armory.Dev", "false"));
    }

    @NotNull
    public static GeneralRegistry getInstance() {
        return instance;
    }

    @NotNull
    public HashMap<String, String> getRequestedAPICallbacks() {
        return requestedAPICallbacks;
    }

    public void registerAPICallback(String method, String modId) {
        this.requestedAPICallbacks.put(method, modId);
    }

    @NotNull
    @Override
    public IAnvilMaterialRegistry getAnvilMaterialRegistry() {
        return AnvilMaterialRegistry.getInstance();
    }

    @NotNull
    @Override
    public IAnvilRecipeRegistry getAnvilRecipeRegistry() {
        return AnvilRecipeRegistry.getInstance();
    }

    @NotNull
    @Override
    public IHeatableItemRegistry getHeatableItemRegistry() {
        return HeatableItemRegistry.getInstance();
    }

    @NotNull
    @Override
    public IArmorPartRegistry getMedievalArmorPartRegistry() {
        return MedievalAddonRegistry.getInstance();
    }

    @Override
    public IMaterialRegistry getArmorMaterialRegistry() {
        return MaterialRegistry.getInstance();
    }

    @Override
    public IArmorRegistry getArmorRegistry() {
        return ArmorRegistry.getInstance();
    }

    @NotNull
    @Override
    public IArmoryHelpers getHelpers() {
        return ArmoryHelpers.getInstance();
    }
}
