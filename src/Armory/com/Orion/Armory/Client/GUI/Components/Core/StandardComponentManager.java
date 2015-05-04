package com.Orion.Armory.Client.GUI.Components.Core;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:32
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StandardComponentManager implements IComponentManager
{
    protected ArmoryBaseGui iGui;
    ArrayList<IGUIComponent> iComponents = new ArrayList<IGUIComponent>();

    public StandardComponentManager(ArmoryBaseGui pGui)
    {
        this.iGui = pGui;
    }

    public ArrayList<IGUIComponent> getComponents()
    {
        return iComponents;
    }

    public void addComponent(IGUIComponent pNewComponent)
    {
        iComponents.add(pNewComponent);
    }

    public IGUIComponent getComponentAt(int pTargetX, int pTargetY)
    {
        for(int i = 0; i < iComponents.size(); i++)
        {
            IGUIComponent tComponent = iComponents.get(i);
            if (tComponent.checkIfPointIsInComponent(pTargetX, pTargetY)) { return tComponent; }
        }

        return null;
    }

    public void drawComponents()
    {
        for(int i = 0; i < iComponents.size(); i++) {
            IGUIComponent tComponent = iComponents.get(i);
            tComponent.onUpdate();

            tComponent.draw(iGui.guiLeft, iGui.guiTop);
        }
    }

    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton)
    {

        if (pMouseButton == 0) {

            IGUIComponent tComponent = this.getComponentAt(pMouseX, pMouseY);

            if (tComponent != null) {
                return tComponent.handleMouseClicked(pMouseX, pMouseY, pMouseButton);
            }
        }
        return false;
    }
}
