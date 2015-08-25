package com.Orion.Armory.Client.GUI.Components.ToolTips;

import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
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
    IComponentHost getComponentHost();

    String getInternalName();

    Rectangle getToolTipVisibileArea();

    void setToolTipVisibleArea(Rectangle pNewArea);

    Rectangle getOccupiedArea();

    ArrayList<IToolTip> getToolTipLines();
}
