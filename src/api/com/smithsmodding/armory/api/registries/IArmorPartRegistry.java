package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.materials.IArmorMaterial;

import java.util.HashMap;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IArmorPartRegistry extends IMLAAddonRegistry {

    HashMap<MLAAddon, Boolean> getPartStatesForMaterial(IArmorMaterial material);

    void setPartStateForMaterial (IArmorMaterial material, MLAAddon addon, boolean state);

    void setPartStateForMaterial(IArmorMaterial material, String addonId, boolean state);

    boolean getPartStateForMaterial (IArmorMaterial material, MLAAddon addon);

    boolean getPartStateForMaterial(IArmorMaterial material, String materialIndependantId);
}
