package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IArmorPartRegistry {

    void setPartStateForMaterial (IArmorMaterial material, MLAAddon addon, boolean state);

    boolean getPartStateForMaterial (IArmorMaterial material, MLAAddon addon);
}
