package com.SmithsModding.Armory.Client.Gui;

import com.SmithsModding.Armory.Common.TileEntity.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations.*;
import com.SmithsModding.SmithsCore.Client.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
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
        registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, this, new Coordinate2D(0, 0), GUI.getWidth(), GUI.getHeigth() - ( ComponentPlayerInventory.HEIGHT - 3 ), Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, this, new Coordinate2D(0, 76), Colors.DEFAULT, ( (ContainerSmithsCore) inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEONE, new CoreComponentState(null), this, new Coordinate2D(44, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETWO, new CoreComponentState(null), this, new Coordinate2D(62, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMETHREE, new CoreComponentState(null), this, new Coordinate2D(80, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFOUR, new CoreComponentState(null), this, new Coordinate2D(98, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));
        registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.FirePit.FLAMEFIVE, new CoreComponentState(null), this, new Coordinate2D(116, 40), ComponentDirection.VERTICALBOTTOMTOTOP, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < ( TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT ); tSlotIndex++) {
            Slot slot = inventorySlots.inventorySlots.get(tSlotIndex);

            registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.FirePit.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ( (ContainerSmithsCore) inventorySlots ).getContainerInventory(), null), this, slot, Colors.DEFAULT));
        }

    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerLedgers (IGUIBasedLedgerHost parent) {

    }

    /**
     * Method used to register a new Component to this Host.
     *
     * @param ledger The new component.
     */
    @Override
    public void registerNewLedger (IGUILedger ledger) {

    }

    /**
     * Function to get all the Components registered to this Host.
     *
     * @return A ID to ledger map that holds all the Components (but not their SubComponents) of this Host.
     */
    @Override
    public LinkedHashMap<String, IGUILedger> getAllLedgers () {
        return null;
    }

    /**
     * Method to get the LedgerManager for this host.
     *
     * @return The LedgerManager of this host.
     */
    @Override
    public ILedgerManager getLedgerManager () {
        return null;
    }
}
