package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentPlayerInventory;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentSlot;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.client.gui.tabs.implementations.CoreTab;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


/**
 * Created by Marc on 24.01.2016.
 */
public class TabFirePitMeltingMetal extends CoreTab {

    GuiFirePit firePit;

    public TabFirePitMeltingMetal (String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
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
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 80), com.smithsmodding.armory.util.client.Colors.DEFAULT, ((ContainerSmithsCore) firePit.inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEONE, host, new CoreComponentState(null), new Coordinate2D(44, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETWO, host, new CoreComponentState(null), new Coordinate2D(62, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETHREE, host, new CoreComponentState(null), new Coordinate2D(80, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFOUR, host, new CoreComponentState(null), new Coordinate2D(98, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFIVE, host, new CoreComponentState(null), new Coordinate2D(116, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < ( TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT ); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            if (tSlotIndex < TileEntityFirePit.INGOTSTACKS_AMOUNT) {
                host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.MELT + "." + tSlotIndex, host, new CoreComponentState(null), new Coordinate2D(slot.xDisplayPosition + 4, slot.yDisplayPosition + 19 - getTabManager().getDisplayAreaVerticalOffset()), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.FirePit.DROPEMPTY, Textures.Gui.FirePit.DROPFULL));
            }

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ( (ContainerSmithsCore) firePit.inventorySlots ).getContainerInventory(), null), host, new Coordinate2D(slot.xDisplayPosition - 1, slot.yDisplayPosition - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.util.client.Colors.DEFAULT));
        }
    }
}
