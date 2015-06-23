package com.Orion.Armory.Client.GUI.Components.Core;
/*
/  IGUIComponent
/  Created by : Orion
/  Created on : 27-4-2015
*/

import com.Orion.Armory.Client.GUI.Components.ToolTips.IToolTipProvider;

public interface IGUIComponent extends IToolTipProvider
{
    public abstract void onUpdate();

    public String getInternalName();

    public int getHeight();

    public int getWidth();

    public void draw(int pX, int pY);

    public abstract void drawForeGround(int pX, int pY);

    public abstract void drawBackGround(int pX, int pY);

    public abstract void drawToolTips(int pMouseX, int pMouseY);

    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY);

    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);

    public boolean handleKeyTyped(char pKey, int pPara);

    public boolean requiresForcedInput();
}
