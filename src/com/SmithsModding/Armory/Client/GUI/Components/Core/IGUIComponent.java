package com.SmithsModding.Armory.Client.GUI.Components.Core;
/*
/  IGUIComponent
/  Created by : Orion
/  Created on : 27-4-2015
*/

import com.SmithsModding.Armory.Client.GUI.Components.ToolTips.IToolTipProvider;

public interface IGUIComponent extends IToolTipProvider {
    void onUpdate();

    String getInternalName();

    int getHeight();

    int getWidth();

    void draw(int pX, int pY);

    void drawForeGround(int pX, int pY);

    void drawBackGround(int pX, int pY);

    boolean checkIfPointIsInComponent(int pTargetX, int pTargetY);

    boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);

    boolean handleKeyTyped(char pKey, int pPara);

    boolean requiresForcedInput();
}
