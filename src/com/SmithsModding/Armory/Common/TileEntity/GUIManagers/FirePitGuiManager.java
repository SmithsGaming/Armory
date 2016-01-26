package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.common.tileentity.state.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import net.minecraftforge.fluids.*;

import java.util.*;

/**
 * Created by Marc on 25.12.2015.
 */
public class FirePitGuiManager extends TileStorageBasedGUIManager {

    private TileEntityFirePit tileEntityFirePit;

    public FirePitGuiManager (TileEntityFirePit tileEntityFirePit) {
        this.tileEntityFirePit = tileEntityFirePit;
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue (IGUIComponent component) {
        if (!( component instanceof ComponentProgressBar ))
            return 0F;

        FirePitState state = (FirePitState) tileEntityFirePit.getState();
        Float burningTime = (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);

        if (burningTime < 1F)
            return 0F;


        return burningTime / (Float) state.getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);
    }

    @Override
    public int getTotalTankContents (IGUIComponent component) {
        return tileEntityFirePit.getTankSize();
    }

    @Override
    public ArrayList<FluidStack> getTankContents (IGUIComponent component) {
        return tileEntityFirePit.getAllFluids();
    }
}
