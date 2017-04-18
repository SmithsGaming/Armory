package com.smithsmodding.armory.client.gui.implementations.forge;

import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
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

import javax.annotation.Nonnull;


/**
 * Created by Marc on 24.01.2016.
 */
public class TabForgeMeltingMetal extends CoreTab {

    GuiForge firePit;

    public TabForgeMeltingMetal(String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiForge) root;
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Forge.BACKGROUND, host, new Coordinate2D(0, 0), GuiForge.GUI.getWidth(), GuiForge.GUI.getHeigth() - (ComponentPlayerInventory.HEIGHT - 3), com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Forge.INVENTORY, host, new Coordinate2D(0, 80), com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ((ContainerSmithsCore) firePit.inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEONE, host, new CoreComponentState(null), new Coordinate2D(44, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMETWO, host, new CoreComponentState(null), new Coordinate2D(62, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMETHREE, host, new CoreComponentState(null), new Coordinate2D(80, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEFOUR, host, new CoreComponentState(null), new Coordinate2D(98, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEFIVE, host, new CoreComponentState(null), new Coordinate2D(116, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < (TileEntityForge.FUELSTACK_AMOUNT + TileEntityForge.INGOTSTACKS_AMOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            if (tSlotIndex < TileEntityForge.INGOTSTACKS_AMOUNT) {
                host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.MELT + "." + tSlotIndex, host, new CoreComponentState(null), new Coordinate2D(slot.xPos + 4, slot.yPos + 19 - getTabManager().getDisplayAreaVerticalOffset()), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.FirePit.DROPEMPTY, Textures.Gui.FirePit.DROPFULL));
            }

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Forge.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, new Coordinate2D(slot.xPos - 1, slot.yPos - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.api.util.client.Colors.DEFAULT));
        }
    }
}
