package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.armory.common.tileentity.state.FireplaceState;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;

import java.text.DecimalFormat;

/**
 * Created by Marc on 27.02.2016.
 */
public class FireplaceGuiManager extends TileStorageBasedGUIManager {

    private static final DecimalFormat laf = new DecimalFormat("###.##");
    private TileEntityFireplace tileEntityFireplace;

    public FireplaceGuiManager(TileEntityFireplace tileEntityFirePit) {
        this.tileEntityFireplace = tileEntityFirePit;
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue(IGUIComponent component) {

        if (!(component instanceof ComponentProgressBar))
            return 0F;

        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        if (component.getID().toLowerCase().contains("flame")) {
            Float burningTime = (Float) state.getData(tileEntityFireplace, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);

            if (burningTime < 1F)
                return 0F;

            return burningTime / (Float) state.getData(tileEntityFireplace, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);
        }

        if (component.getID().toLowerCase().contains("cooking")) {
            Float progress = state.getCookingProgress();

            if (progress > 1F)
                return 1F;

            if (progress < 0F)
                return 0f;

            return progress;
        }

        return 0F;
    }

    /**
     * Method used by the rendering system to dynamically update a Label.
     *
     * @param component The component requesting the content.
     * @return THe string that should be displayed.
     */
    @Override
    public String getLabelContents(IGUIComponent component) {
        if (component.getID().endsWith(".CurrentTemperature")) {
            return Math.floor(getCurrentTemperature()) + " C";
        } else if (component.getID().endsWith(".MaxTemperature")) {
            return Math.floor(getMaxTemperature()) + " C";
        } else if (component.getID().endsWith(".LastChange")) {
            return laf.format(getLastAddedHeat()) + " C";
        } else if (component.getID().endsWith(".CookingMultiplier")) {
            return laf.format(getCurrentCookingMultiplier()) + "x";
        }

        return super.getLabelContents(component);
    }

    public float getLastAddedHeat() {
        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        return state.getLastAddedHeat();
    }

    public float getLastTemperature() {
        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        return state.getLastTemperature();
    }

    public float getMaxTemperature() {
        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        return state.getMaxTemperature();
    }

    public float getCurrentTemperature() {
        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        return state.getCurrentTemperature();
    }

    public float getCurrentCookingMultiplier() {
        FireplaceState state = (FireplaceState) tileEntityFireplace.getState();

        return state.getCookingSpeedMultiplier();
    }

}
