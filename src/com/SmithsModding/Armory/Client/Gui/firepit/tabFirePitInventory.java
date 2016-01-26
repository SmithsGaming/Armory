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
 * Created by Marc on 24.01.2016.
 */
public class TabFirePitInventory extends CoreTab {

    GuiFirePit firePit;

    public TabFirePitInventory (String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
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
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, host, new Coordinate2D(0, 0), GuiFirePit.GUI.getWidth(), GuiFirePit.GUI.getHeigth() - ( ComponentPlayerInventory.HEIGHT - 3 ), com.smithsmodding.armory.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 76), com.smithsmodding.armory.util.client.Colors.DEFAULT, ( (ContainerSmithsCore) firePit.inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEONE, new CoreComponentState(null), host, new Coordinate2D(44, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETWO, new CoreComponentState(null), host, new Coordinate2D(62, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETHREE, new CoreComponentState(null), host, new Coordinate2D(80, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFOUR, new CoreComponentState(null), host, new Coordinate2D(98, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFIVE, new CoreComponentState(null), host, new Coordinate2D(116, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < ( TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT ); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ( (ContainerSmithsCore) firePit.inventorySlots ).getContainerInventory(), null), host, new Coordinate2D(slot.xDisplayPosition - 1, slot.yDisplayPosition - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.util.client.Colors.DEFAULT));
        }
    }
}
