package com.SmithsModding.Armory.Client.GUI.Components.ToolTips;

/**
 * Created by Orion
 * Created on 22.06.2015
 * 12:15
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StandardToolTip implements IToolTip {

    private IToolTipProvider iProvider;
    private String iToolTipLine;

    public StandardToolTip(IToolTipProvider pProvider, String pToolTipLine) {
        iProvider = pProvider;
        iToolTipLine = pToolTipLine;
    }

    @Override
    public IToolTipProvider getProvider() {
        return iProvider;
    }

    @Override
    public String getToolTipLine() {
        return iToolTipLine;
    }
}
