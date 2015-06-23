package com.Orion.Armory.Client.GUI.Components.ToolTips;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Util.Core.Rectangle;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 22.06.2015
 * 12:08
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IToolTipProvider
{
    ArmoryBaseGui getBaseGui();

    String getInternalName();

    Rectangle getToolTipVisibileArea();

    void setToolTipVisibleArea(Rectangle pNewArea);

    Rectangle getOccupiedArea();

    ArrayList<IToolTip> getToolTipLines();
}
