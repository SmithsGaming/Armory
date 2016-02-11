package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.materials.*;
import net.minecraftforge.fluids.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface ILiquidMeterialRegistry {

    void registerMaterialFluid (IArmorMaterial material, Fluid fluid);

    Fluid getRegsiteredFluidFromMaterial (IArmorMaterial material);
}
