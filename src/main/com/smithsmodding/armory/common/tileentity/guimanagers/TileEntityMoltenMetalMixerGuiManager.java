package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalMixer;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Author Orion (Created on: 26.07.2016)
 */
public class TileEntityMoltenMetalMixerGuiManager extends TileStorageBasedGUIManager {

    @NotNull
    private final TileEntityMoltenMetalMixer moltenMetalMixer;

    public TileEntityMoltenMetalMixerGuiManager(TileEntityMoltenMetalMixer moltenMetalMixer) {
        this.moltenMetalMixer = moltenMetalMixer;
    }

    @Nullable
    @Override
    public ArrayList<FluidStack> getTankContents(@Nonnull IGUIComponent component) {
        ArrayList<FluidStack> contents = new ArrayList<>();

        if (component.getID().toLowerCase().contains("left") && moltenMetalMixer.getState().getLeftTank().getFluid() != null)
            contents.add(moltenMetalMixer.getState().getLeftTank().getFluid());

        if (component.getID().toLowerCase().contains("right") && moltenMetalMixer.getState().getRightTank().getFluid() != null)
            contents.add(moltenMetalMixer.getState().getRightTank().getFluid());

        if (component.getID().toLowerCase().contains("output") && moltenMetalMixer.getState().getOutputTank().getFluid() != null)
            contents.add(moltenMetalMixer.getState().getOutputTank().getFluid());

        return contents;
    }

    @Override
    public int getTotalTankContents(@Nonnull IGUIComponent component) {
        if (component.getID().toLowerCase().contains("left"))
            return moltenMetalMixer.getState().getLeftTank().getCapacity();

        if (component.getID().toLowerCase().contains("right"))
            return moltenMetalMixer.getState().getRightTank().getCapacity();

        if (component.getID().toLowerCase().contains("out"))
            return moltenMetalMixer.getState().getOutputTank().getCapacity();

        return 1;
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue(@Nonnull IGUIComponent component) {
        if (component.getID().toLowerCase().contains("mixingprogress")) {
            if (moltenMetalMixer.getState().getCurrentProgress() == 0)
                return 0;

            Float mixingprogress = moltenMetalMixer.getState().getCurrentProgress() / (float) moltenMetalMixer.getState().getCurrentRecipe().getProcessingTime();

            if (component.getID().endsWith("Horizontal")) {
                if (mixingprogress <= 0F)
                    return 0F;

                if (mixingprogress >= 0.5F)
                    return 1F;

                return mixingprogress * 2;
            }

            if (component.getID().endsWith("Vertical")) {
                if (mixingprogress <= 0.5F)
                    return 0F;

                if (mixingprogress >= 1F)
                    return 1F;

                return (mixingprogress - 0.5F) * 2;
            }
        }

        return super.getProgressBarValue(component);
    }

    @Nonnull
    @Override
    public String getCustomToolTipDisplayString(IGUIComponent component) {
        if (!(component instanceof ComponentProgressBar))
            return "";

        if (component.getID().toLowerCase().contains("mixingprogress")) {
            if (moltenMetalMixer.getState().getCurrentRecipe() == null)
                return I18n.format(TranslationKeys.GUI.PROGRESS) + ": 0 %";

            Float mixingprogress = moltenMetalMixer.getState().getCurrentProgress() / (float) moltenMetalMixer.getState().getCurrentRecipe().getProcessingTime();

            if (mixingprogress <= 0F)
                return I18n.format(TranslationKeys.GUI.PROGRESS) + ": 0 %";

            if (mixingprogress >= 1F)
                return I18n.format(TranslationKeys.GUI.PROGRESS) + ": 100 %";

            return I18n.format(TranslationKeys.GUI.PROGRESS) + ": " + Math.round(mixingprogress * 100) + " %";
        }

        return "";
    }
}
