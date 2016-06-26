package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForgeGuiManager extends TileEntityForgeBaseGuiManager<TileEntityForge> {

    public TileEntityForgeGuiManager(TileEntityForge tileEntity) {
        super(tileEntity);
    }

    @Override
    public float getProgressBarValue(IGUIComponent component) {

        if (!(component instanceof ComponentProgressBar))
            return 0F;

        if (component.getID().toLowerCase().contains("flame")) {
            return getTileEntity().getFuelData().getBurningTicksLeftOnCurrentFuel() / (float) getTileEntity().getFuelData().getTotalBurningTicksOnCurrentFuel();
        }

        if (component.getID().contains(References.InternalNames.GUIComponents.FirePit.MELT)) {
            int id = Integer.parseInt(component.getID().substring(component.getID().length() - 1));

            return getTileEntity().getState().getMeltingProgess(id);
        }

        if (component.getID().toLowerCase().contains("mixingprogress")) {
            Float mixingprogress = getTileEntity().getState().getMixingProgress();

            if (component.getID().endsWith("In.Left.Horizontal") || component.getID().endsWith("In.Right.Horizontal")) {
                if (mixingprogress <= 0F)
                    return 0F;

                if (mixingprogress >= 1F)
                    return 1F;

                return mixingprogress;
            }

            if (component.getID().endsWith("In.Left.Vertical") || component.getID().endsWith("In.Right.Vertical")) {
                if (mixingprogress <= 1F)
                    return 0F;

                if (mixingprogress >= 2F)
                    return 1F;

                return mixingprogress - 1F;
            }

            if (component.getID().endsWith("Out.Left.Vertical") || component.getID().endsWith("Out.Right.Vertical")) {
                if (mixingprogress <= 2F)
                    return 0F;

                if (mixingprogress >= 3F)
                    return 1F;

                return mixingprogress - 2F;
            }

            if (component.getID().endsWith("Out.Left.Horizontal") || component.getID().endsWith("Out.Right.Horizontal")) {
                if (mixingprogress <= 3F)
                    return 0F;

                if (mixingprogress >= 4F)
                    return 1F;

                return mixingprogress - 3F;
            }
        }

        return 0F;
    }

    @Override
    public String getCustomToolTipDisplayString(IGUIComponent component) {
        if (!(component instanceof ComponentProgressBar))
            return "";

        if (component.getID().toLowerCase().contains("mixingprogress")) {
            Float mixingprogress = getTileEntity().getState().getMixingProgress();

            if (mixingprogress <= 0F)
                return I18n.format(TranslationKeys.GUI.PROGRESS) + ": 0 %";

            if (mixingprogress >= 4F)
                return I18n.format(TranslationKeys.GUI.PROGRESS) + ": 100 %";

            return I18n.format(TranslationKeys.GUI.PROGRESS) + ": " + Math.round(mixingprogress / 4F) + " %";
        }

        return "";
    }

    @Override
    public int getTotalTankContents(IGUIComponent component) {
        return getTileEntity().getTankSize();
    }

    @Override
    public String getLabelContents(IGUIComponent component) {
        if (component.getID().endsWith(".CurrentTemperature")) {
            //Armory.getLogger().error(getTileEntity().getState().getCurrentTemp());
            return laf.format(getTileEntity().getState().getCurrentTemp()) + " C";
        } else if (component.getID().endsWith(".MaxTemperature")) {
            return Math.floor(getTileEntity().getState().getMaxTemp()) + " C";
        } else if (component.getID().endsWith(".LastChange")) {
            return laf.format(getTileEntity().getState().getLastChange()) + " C";
        }

        return super.getLabelContents(component);
    }

    @Override
    public ArrayList<FluidStack> getTankContents(IGUIComponent component) {
        return getTileEntity().getAllFluids();
    }
}
