package com.smithsmodding.armory.common.material.fluidmodifiers;

import com.smithsmodding.armory.api.events.common.*;
import com.smithsmodding.armory.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.eventhandler.*;

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
