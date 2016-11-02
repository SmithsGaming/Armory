package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.types.EnumPumpType;
import com.smithsmodding.armory.common.tileentity.state.TileEntityPumpState;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public class TileEntityPump extends TileEntitySmithsCore<TileEntityPumpState, TileStorageBasedGUIManager> {

    EnumPumpType type;

    public TileEntityPump(EnumPumpType type) {
        this.type = type;
    }

    @Override
    protected TileStorageBasedGUIManager getInitialGuiManager() {
        return new TileStorageBasedGUIManager();
    }

    @Override
    protected TileEntityPumpState getInitialState() {
        return new TileEntityPumpState();
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.Pump + "-" + getLocation().toString();
    }

    public EnumPumpType getType() {
        return type;
    }
}
