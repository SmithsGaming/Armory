package com.Orion.Armory.Client.GUI.Components.Core;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:32
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentManager
{
    protected ArmoryBaseGui iGui;
    ArrayList<Component> iComponents = new ArrayList<Component>();

    public ComponentManager(ArmoryBaseGui pGui)
    {
        this.iGui = pGui;
    }

    public ArrayList<Component> getComponents()
    {
        return iComponents;
    }

    public void addComponent(Component pNewComponent)
    {
        iComponents.add(pNewComponent);
    }

    public Component getComponentAt(int pTargetX, int pTargetY)
    {
        for(int i = 0; i < iComponents.size(); i++)
        {
            Component tComponent = iComponents.get(i);
            if (tComponent.checkIfPointIsInComponent(pTargetX, pTargetY)) { return tComponent; }
        }

        return null;
    }

    public void drawComponents()
    {
        for(int i = 0; i < iComponents.size(); i++) {
            Component tComponent = iComponents.get(i);
            tComponent.onUpdate();

            tComponent.draw(iGui.guiLeft, iGui.guiTop);
        }
    }

    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton)
    {

        if (pMouseButton == 0) {

            Component tComponent = this.getComponentAt(pMouseX, pMouseY);

            if (tComponent != null) {
                return tComponent.handleMouseClicked(pMouseX, pMouseY, pMouseButton);
            }
        }
        return false;
    }
}
