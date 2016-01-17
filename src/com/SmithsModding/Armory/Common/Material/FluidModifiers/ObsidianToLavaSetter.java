package com.smithsmodding.Armory.Common.Material.FluidModifiers;

import com.smithsmodding.Armory.API.Events.Common.*;
import com.smithsmodding.Armory.Util.*;
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
