package com.smithsmodding.armory.common.material.fluidmodifiers;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.api.events.common.HeatableItemRegisteredEvent;
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

        //event.setMoltenStack(new FluidStack(FluidRegistry.LAVA, event.getMoltenStack().amount));
    }

}
