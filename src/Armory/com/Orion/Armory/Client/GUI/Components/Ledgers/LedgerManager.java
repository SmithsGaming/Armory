package com.Orion.Armory.Client.GUI.Components.Ledgers;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:43
 * <p/>
 * Copyrighted according to Project specific license
 */
public class LedgerManager
{
    StandardComponentManager iLeftLedgerManager;
    StandardComponentManager iRightLedgerManager;

    ArmoryBaseGui iGui;

    public LedgerManager(ArmoryBaseGui pGui) {
        iGui = pGui;

        iLeftLedgerManager = new StandardComponentManager(pGui);
        iRightLedgerManager = new StandardComponentManager(pGui);
    }

    public ArrayList<IGUIComponent> getLeftLedgers()
    {
        return iLeftLedgerManager.getComponents();
    }

    public ArrayList<IGUIComponent> getRightLedgers()
    {
        return iRightLedgerManager.getComponents();
    }

    public void addLedgerLeft(Ledger pNewLedger)
    {
        pNewLedger.iDirection = Ledger.LedgerDirection.Left;
        iLeftLedgerManager.addComponent(pNewLedger);
    }

    public void addLedgerRight(Ledger pNewLedger)
    {
        pNewLedger.iDirection = Ledger.LedgerDirection.Right;
        iRightLedgerManager.addComponent(pNewLedger);
    }

    public Ledger getLedgetAt(int pTargetX, int pTargetY)
    {
        Ledger tLeftFoundLedger = (Ledger) iLeftLedgerManager.getComponentAt(pTargetX, pTargetY);

        if (tLeftFoundLedger != null)
        {
            return tLeftFoundLedger;
        }

        Ledger tRightFoundLedger = (Ledger) iRightLedgerManager.getComponentAt(pTargetX, pTargetY);

        if (tRightFoundLedger != null)
        {
            return tRightFoundLedger;
        }

        return null;
    }

    public void drawLedgers()
    {
        int tYPos = iGui.guiTop + 8;
        for(int i = 0; i < iLeftLedgerManager.getComponents().size(); i++)
        {
            Ledger tLedger = (Ledger) iLeftLedgerManager.getComponents().get(i);
            tLedger.onUpdate();

            tLedger.draw(iGui.guiLeft, tYPos);
            tYPos += tLedger.getHeight();
        }

        tYPos = iGui.guiTop + 8;
        for(int i = 0; i < iRightLedgerManager.getComponents().size(); i++)
        {
            Ledger tLedger = (Ledger) iRightLedgerManager.getComponents().get(i);
            tLedger.onUpdate();

            tLedger.draw(iGui.guiLeft + iGui.xSize, tYPos);
            tYPos += tLedger.getHeight();
        }
    }

    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton)
    {

        if (pMouseButton == 0) {

            Ledger ledger = this.getLedgetAt(pMouseX, pMouseY);

            // Default action only if the mouse click was not handled by the
            // ledger itself.
            if (ledger != null && !ledger.handleMouseClicked(pMouseX, pMouseY, pMouseButton)) {

                for(int i = 0; i < iLeftLedgerManager.getComponents().size(); i++)
                {
                    Ledger tLedger = (Ledger) iLeftLedgerManager.getComponents().get(i);

                    if (tLedger != ledger && tLedger.isOpen()) {
                        tLedger.toggleOpenState();
                    }
                }

                for(int i = 0; i < iRightLedgerManager.getComponents().size(); i++)
                {
                    Ledger tLedger = (Ledger) iRightLedgerManager.getComponents().get(i);
                    if (tLedger != ledger && tLedger.isOpen()) {
                        tLedger.toggleOpenState();
                    }
                }

                ledger.toggleOpenState();
                return true;
            }
        }
        return false;
    }
}
