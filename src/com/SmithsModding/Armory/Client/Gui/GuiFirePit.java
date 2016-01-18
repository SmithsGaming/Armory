package com.smithsmodding.armory.client.gui;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.armory.util.client.Colors;
import com.smithsmodding.armory.util.client.*;
import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.legders.core.*;
import com.smithsmodding.smithscore.client.gui.legders.implementations.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.inventory.*;

import java.util.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class GuiFirePit extends GuiContainerSmithsCore {

    public static Plane GUI = new Plane(0, 0, ComponentPlayerInventory.WIDTH, 245);

    public GuiFirePit (ContainerSmithsCore container) {
        super(container);
    }

    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, host, new Coordinate2D(0, 0), GUI.getWidth(), GUI.getHeigth() - ( ComponentPlayerInventory.HEIGHT - 3 ), Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 76), Colors.DEFAULT, ( (ContainerSmithsCore) inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEONE, new CoreComponentState(null), host, new Coordinate2D(44, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETWO, new CoreComponentState(null), host, new Coordinate2D(62, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETHREE, new CoreComponentState(null), host, new Coordinate2D(80, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFOUR, new CoreComponentState(null), host, new Coordinate2D(98, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFIVE, new CoreComponentState(null), host, new Coordinate2D(116, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < ( TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT ); tSlotIndex++) {
            Slot slot = inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ( (ContainerSmithsCore) inventorySlots ).getContainerInventory(), null), host, slot, Colors.DEFAULT));
        }

    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerLedgers (IGUIBasedLedgerHost parent) {
        ArrayList<String> information = new ArrayList<String>();

        information.add("test");
        information.add("Lorei ipsum");

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.RIGHT, "FirePit", new MinecraftColor(MinecraftColor.YELLOW), information));
        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information2", this, LedgerConnectionSide.LEFT, "FirePit", new MinecraftColor(MinecraftColor.ORANGE), information));
        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information3", this, LedgerConnectionSide.LEFT, "FirePit", new MinecraftColor(MinecraftColor.RED), information));
    }
}
