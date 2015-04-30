package com.Orion.Armory.Client.GUI.Plugins.NEI;

import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.api.INEIGuiAdapter;
import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Ledgers.Ledger;
import net.minecraft.client.gui.inventory.GuiContainer;

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

            for(int i = 0; i < abg.Ledgers().getLeftLedgers().size(); i++)
            {
                Ledger tLedger = (Ledger) abg.Ledgers().getLeftLedgers().get(i);

                Rectangle4i bounds = new Rectangle4i(tLedger.iLastXOrigin + tLedger.getOriginOffSet(), tLedger.iLastYOrigin, tLedger.getWidth(), tLedger.getHeight());
                if (bounds.intersects(rect)){
                    return true;
                }
            }

            for(int i = 0; i < abg.Ledgers().getRightLedgers().size(); i++)
            {
                Ledger tLedger = (Ledger) abg.Ledgers().getRightLedgers().get(i);

                Rectangle4i bounds = new Rectangle4i(tLedger.iLastXOrigin + tLedger.getOriginOffSet(), tLedger.iLastYOrigin, tLedger.getWidth(), tLedger.getHeight());
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
