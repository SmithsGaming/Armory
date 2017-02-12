package com.smithsmodding.armory.common.material.fluidmodifiers;

import com.smithsmodding.armory.api.events.common.HeatableItemRegisteredEvent;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by marcf on 12/21/2015.
 */
public class ObsidianToLavaSetter {

    @SubscribeEvent
    public void onHeatableItemAdded(@NotNull HeatableItemRegisteredEvent event) {
        if (!event.getMaterial().getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN))
            return;

        //event.setMoltenStack(new FluidStack(FluidRegistry.LAVA, event.getMoltenStack().amount));
    }

}
