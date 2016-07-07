package com.smithsmodding.armory.client.gui.implementations.forge;

import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
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
 * Created by Marc on 25.01.2016.
 */
public class TabForgeMoltenMetal extends CoreTab {

    GuiForge firePit;

    public TabForgeMoltenMetal(String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiForge) root;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Forge.BACKGROUND, host, new Coordinate2D(0, 0), GuiForge.GUI.getWidth(), 111, com.smithsmodding.armory.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Forge.INVENTORY, host, new Coordinate2D(0, 107), com.smithsmodding.armory.util.client.Colors.DEFAULT, ((ContainerSmithsCore) firePit.inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.Forge.MOLTENMETALSLEFT, host, new CoreComponentState(), new Coordinate2D(7, 7), 20, 80, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.Forge.MOLTENMETALSRIGHT, host, new CoreComponentState(), new Coordinate2D(GuiForge.GUI.getWidth() - 7 - 20, 7), 20, 80, ComponentOrientation.VERTICALBOTTOMTOTOP));

        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Forge.INFUSIONSTACKSBACKGROUND, host, new Coordinate2D(53, 33), 10 + 3 * 18 + 6, 18 + 10, new MinecraftColor(MinecraftColor.WHITE), ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));

        for (int tSlotIndex = 0; tSlotIndex < (TileEntityForge.INFUSIONSTACK_AMOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Forge.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, new Coordinate2D(slot.xDisplayPosition - 1, slot.yDisplayPosition - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.util.client.Colors.DEFAULT));
        }

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGINLEFTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(29, 7), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGINLEFTVERTICAL, host, new CoreComponentState(), new Coordinate2D(70, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGINRIGHTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(98, 7), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGINRIGHTVERTICAL, host, new CoreComponentState(), new Coordinate2D(91, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGOUTLEFTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(29, 71), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.HORIZONTALHEADRIGHTTOLEFTEMPTY, Textures.Gui.Basic.Components.HORIZONTALHEADRIGHTTOLEFTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGOUTLEFTVERTICAL, host, new CoreComponentState(), new Coordinate2D(76, 61), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMLEFTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMLEFTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGOUTRIGHTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(98, 71), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.HORIZONTALHEADLEFTTORIGHTEMPTY, Textures.Gui.Basic.Components.HORIZONTALHEADLEFTTORIGHTFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.PROGRESSMIXINGOUTRIGHTVERTICAL, host, new CoreComponentState(), new Coordinate2D(97, 61), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTORFULL));
    }
}
