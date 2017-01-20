package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import org.jetbrains.annotations.NotNull;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityFireplaceGuiManager extends TileEntityForgeBaseGuiManager<TileEntityFireplace> {

    public TileEntityFireplaceGuiManager(TileEntityFireplace tileEntity) {
        super(tileEntity);
    }

    @Override
    public float getProgressBarValue(IGUIComponent component) {
        if (!(component instanceof ComponentProgressBar))
            return 0F;

        float superValue = super.getProgressBarValue(component);
        if (superValue != 0f)
            return superValue;

        if (component.getID().toLowerCase().contains("cooking")) {
            Float progress = getTileEntity().getState().getCookingProgress();

            if (progress > 1F)
                return 1F;

            if (progress < 0F)
                return 0f;

            return progress;
        }

        return 0F;
    }

    @Override
    public String getLabelContents(@Nonnull IGUIComponent component) {
        if (component.getID().endsWith(".CurrentTemperature")) {
            return laf.format(getTileEntity().getState().getCurrentTemp()) + " C";
        } else if (component.getID().endsWith(".MaxTemperature")) {
            return Math.floor(getTileEntity().getState().getMaxTemp()) + " C";
        } else if (component.getID().endsWith(".LastChange")) {
            return laf.format(getTileEntity().getState().getLastChange()) + " C";
        } else if (component.getID().endsWith(".CookingMultiplier")) {
            return laf.format(getTileEntity().getState().getCookingSpeedMultiplier()) + "x";
        }

        return super.getLabelContents(component);
    }
}
