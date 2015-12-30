package com.SmithsModding.Armory.Client.Model.Event;

import com.SmithsModding.Armory.Common.TileEntity.*;
import com.SmithsModding.SmithsCore.Common.Event.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 30.12.2015.
 */
public class FIrePitUpdateHandler {

    @SubscribeEvent
    public void handleUpdataEvent (TileEntityDataUpdatedEvent event) {
        if (!( event.getTileEntitySmithsCore() instanceof TileEntityFirePit ))
            return;

        event.getTileEntitySmithsCore().getWorld().markBlockForUpdate(event.getTileEntitySmithsCore().getPos());
    }
}
