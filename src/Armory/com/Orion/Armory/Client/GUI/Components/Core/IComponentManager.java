package com.Orion.Armory.Client.GUI.Components.Core;
/*
/  IComponentManager
/  Created by : Orion
/  Created on : 27-4-2015
*/

import java.util.ArrayList;

public interface IComponentManager
{
    public ArrayList<AbstractGUIComponent> getComponents();

    public void addComponent(AbstractGUIComponent pNewComponent);

    public AbstractGUIComponent getComponentAt(int pTargetX, int pTargetY);

    public void drawComponents();

    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);
}
