package com.smithsmodding.Armory.API.Registries;

import com.smithsmodding.Armory.API.Materials.*;
import net.minecraftforge.fluids.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface ILiquidMeterialRegistry {

    void registerMaterialFluid (IArmorMaterial material, Fluid fluid);

    Fluid getRegsiteredFluidFromMaterial (IArmorMaterial material);
}
