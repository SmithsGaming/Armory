package com.SmithsModding.Armory.Client.GUI.Components.Core;

import com.SmithsModding.Armory.Client.GUI.Components.ToolTips.ToolTipRenderer;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:32
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StandardComponentManager implements IComponentManager {
    protected IComponentHost iHost;
    ArrayList<IGUIComponent> iComponents = new ArrayList<IGUIComponent>();
    ArrayList<Integer> iForcedInputComponents = new ArrayList<Integer>();

    public StandardComponentManager(IComponentHost pHost) {
        this.iHost = pHost;
    }

    public ArrayList<IGUIComponent> getComponents() {
        return iComponents;
    }

    public void addComponent(IGUIComponent pNewComponent) {
        iComponents.add(pNewComponent);
        if (pNewComponent.requiresForcedInput()) {
            iForcedInputComponents.add(iComponents.size() - 1);
        }
    }

    public IGUIComponent getComponentAt(int pTargetX, int pTargetY) {
        for (int i = 0; i < iComponents.size(); i++) {
            IGUIComponent tComponent = iComponents.get(i);
            if (tComponent.checkIfPointIsInComponent(pTargetX, pTargetY)) {
                return tComponent;
            }
        }

        return null;
    }

    public void drawComponents() {
        for (int i = 0; i < iComponents.size(); i++) {
            IGUIComponent tComponent = iComponents.get(i);
            tComponent.onUpdate();

            tComponent.draw(iHost.getXOrigin(), iHost.getYOrigin());
        }
    }

    @Override
    public boolean drawComponentToolTips(int pMouseX, int pMouseY) {
        for (IGUIComponent tComponent : iComponents) {
            if (ToolTipRenderer.renderToolTip(tComponent, pMouseX, pMouseY))
                return true;
        }

        return false;
    }

    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        for (Integer tIndex : iForcedInputComponents) {
            (iComponents.get(tIndex)).handleMouseClicked(pMouseX, pMouseY, pMouseButton);
        }

        if (pMouseButton == 0) {

            IGUIComponent tComponent = this.getComponentAt(pMouseX, pMouseY);

            if (iForcedInputComponents.contains(iComponents.indexOf(tComponent)))
                return true;

            if (tComponent != null) {
                return tComponent.handleMouseClicked(pMouseX, pMouseY, pMouseButton);
            }
        }
        return false;
    }

    @Override
    public boolean handleKeyTyped(char pKey, int pPara) {
        boolean tInputHandled = false;
        for (Integer tIndex : iForcedInputComponents) {
            if ((iComponents.get(tIndex)).handleKeyTyped(pKey, pPara))
                tInputHandled = true;
        }

        if (tInputHandled)
            return true;

        for (int i = 0; i < iComponents.size(); i++) {
            IGUIComponent tComponent = iComponents.get(i);
            if (!iForcedInputComponents.contains(i)) {
                if (tComponent.handleKeyTyped(pKey, pPara))
                    return true;
            }
        }

        return false;
    }

    @Override
    public void onUpdate() {
        for (IGUIComponent tComponent : iComponents) {
            tComponent.onUpdate();
        }
    }
}
