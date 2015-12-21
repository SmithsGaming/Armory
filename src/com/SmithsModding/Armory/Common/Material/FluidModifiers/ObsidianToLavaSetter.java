package com.SmithsModding.Armory.Common.Material.FluidModifiers;

import com.SmithsModding.Armory.API.Events.Common.HeatableItemRegisteredEvent;
import com.SmithsModding.Armory.Util.References;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by marcf on 12/21/2015.
 */
public class ObsidianToLavaSetter
{

    @SubscribeEvent
    public void onHeatableItemAdded(HeatableItemRegisteredEvent event)
    {
        if (!event.getMaterial().getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN))
            return;

        event.setMoltenStack(new FluidStack(FluidRegistry.LAVA, event.getMoltenStack().amount));
    }

}
