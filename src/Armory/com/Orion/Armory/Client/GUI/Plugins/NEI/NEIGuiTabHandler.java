package com.Orion.Armory.Client.GUI.Plugins.NEI;

import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.api.INEIGuiAdapter;
import codechicken.nei.api.INEIGuiHandler;
import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.Sys;

/**
 * Created by Orion
 * Created on 22.04.2015
 * 21:42
 * <p/>
 * Copyrighted according to Project specific license
 */
public class NEIGuiTabHandler extends INEIGuiAdapter
{
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
        Rectangle4i rect;
        if ((gui instanceof ArmoryBaseGui)) {
            ArmoryBaseGui abg = (ArmoryBaseGui) gui;

            rect = new Rectangle4i(x, y, w, h);

            for (ArmoryBaseGui.Ledger Ledger : abg.Ledgers().getLeftLedgers()) {
                Rectangle4i bounds = new Rectangle4i(Ledger.iLastXOrigin + Ledger.getOriginOffSet(), Ledger.iLastYOrigin, Ledger.getWidth(), Ledger.getHeight());
                if (bounds.intersects(rect)){
                    return true;
                }
            }

            for (ArmoryBaseGui.Ledger Ledger : abg.Ledgers().getRightLedgers()) {
                Rectangle4i bounds = new Rectangle4i(Ledger.iLastXOrigin + Ledger.getOriginOffSet(), Ledger.iLastYOrigin, Ledger.getWidth(), Ledger.getHeight());
                if (bounds.intersects(rect)) {
                    return true;
                }
            }

            Rectangle4i bounds = new Rectangle4i(abg.guiLeft, gui.guiTop, ((ArmoryBaseGui) gui).getWidth(), ((ArmoryBaseGui) gui).getHeigth());
            if (bounds.intersects(rect)) {
                return true;
            }
        }

        return false;
    }
}
