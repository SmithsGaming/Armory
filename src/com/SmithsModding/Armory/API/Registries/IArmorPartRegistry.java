package com.SmithsModding.Armory.API.Registries;

import com.SmithsModding.Armory.API.Armor.*;
import com.SmithsModding.Armory.API.Materials.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IArmorPartRegistry {

    void setPartStateForMaterial (IArmorMaterial material, MLAAddon addon, boolean state);

    boolean getPartStateForMaterial (IArmorMaterial material, MLAAddon addon);
}
