package com.SmithsModding.Armory.Client.Gui;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations.*;
import com.SmithsModding.SmithsCore.Client.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class GuiFirePit extends GuiContainerSmithsCore {

    public GuiFirePit (ContainerSmithsCore container) {
        super(container);
    }

    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        registerNewComponent(new ComponentBorder("test", host, new Coordinate2D(0, 0), 150, 150, new MinecraftColor(MinecraftColor.WHITE), ComponentBorder.CornerTypes.StraightHorizontal, ComponentBorder.CornerTypes.StraightHorizontal, ComponentBorder.CornerTypes.StraightHorizontal, ComponentBorder.CornerTypes.StraightHorizontal));
    }
}
