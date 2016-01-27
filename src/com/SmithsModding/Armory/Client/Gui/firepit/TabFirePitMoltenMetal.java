package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.armory.util.client.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.client.gui.tabs.implementations.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 25.01.2016.
 */
public class TabFirePitMoltenMetal extends CoreTab {

    GuiFirePit firePit;

    public TabFirePitMoltenMetal (String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiFirePit) root;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, host, new Coordinate2D(0, 0), GuiFirePit.GUI.getWidth(), 111, com.smithsmodding.armory.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 107), com.smithsmodding.armory.util.client.Colors.DEFAULT, ( (ContainerSmithsCore) firePit.inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALSLEFT, host, new CoreComponentState(), new Coordinate2D(17, 17), 20, 50, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALSRIGHT, host, new CoreComponentState(), new Coordinate2D(GuiFirePit.GUI.getWidth() - 37, 17), 20, 50, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALSOUT, host, new CoreComponentState(), new Coordinate2D(GuiFirePit.GUI.getWidth() /2 - 25, 65), 50, 40, ComponentOrientation.VERTICALBOTTOMTOTOP));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGLEFT, host, new CoreComponentState(), new Coordinate2D(44, 31), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.PROGRESSHORIZONTALEMPTY, Textures.Gui.Basic.Components.PROGRESSHORIZONTALFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.PROGRESSMIXINGRIGHT, host, new CoreComponentState(), new Coordinate2D(GuiFirePit.GUI.getWidth() /2 - 25 - 7 - 25, 31), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.PROGRESSHORIZONTALEMPTY, Textures.Gui.Basic.Components.PROGRESSHORIZONTALFULL));


        Slot slot = firePit.inventorySlots.inventorySlots.get(0);

        host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT, new SlotComponentState(null, TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT, ( (ContainerSmithsCore) firePit.inventorySlots ).getContainerInventory(), null), host, new Coordinate2D(slot.xDisplayPosition - 1, slot.yDisplayPosition - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.util.client.Colors.DEFAULT));
    }
}
