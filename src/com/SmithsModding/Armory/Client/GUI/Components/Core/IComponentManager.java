package com.SmithsModding.Armory.Client.GUI.Components.Core;
/*
/  IComponentManager
/  Created by : Orion
/  Created on : 27-4-2015
*/

import java.util.ArrayList;

public interface IComponentManager {
    ArrayList<IGUIComponent> getComponents();

    void addComponent(IGUIComponent pNewComponent);

    IGUIComponent getComponentAt(int pTargetX, int pTargetY);

    void drawComponents();

    boolean drawComponentToolTips(int pMouseX, int pMouseY);

    boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);

    boolean handleKeyTyped(char pKey, int pPara);

    void onUpdate();
}
