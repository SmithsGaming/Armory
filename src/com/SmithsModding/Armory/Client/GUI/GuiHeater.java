package com.SmithsModding.Armory.Client.GUI;

import com.SmithsModding.Armory.Client.GUI.Components.ComponentBorder;
import com.SmithsModding.Armory.Client.GUI.Components.ComponentSlot;
import com.SmithsModding.Armory.Client.GUI.Components.Ledgers.InfoLedger;
import com.SmithsModding.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

/**
 * Created by Orion
 * Created on 24.04.2015
 * 22:36
 * <p/>
 * Copyrighted according to Project specific license
 */
public class GuiHeater extends ArmoryBaseGui {
    public GuiHeater(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 175;
        this.ySize = 137;
    }

    @Override
    public void initGui() {
        super.initGui();

        if (iComponents.getComponents().size() > 0) {
            return;
        }

        iComponents.addComponent(new ComponentBorder(this, "Gui.Heater.Background", 0, 0, xSize, ySize, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentPlayerInventory(this, "Gui.FirePit.Player", 0, 48, 1, ComponentBorder.CornerTypes.StraightVertical));


        iLedgers.addLedgerLeft(new InfoLedger(this, TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.Heater.InfoLine1, "", TranslationKeys.GUI.Heater.InfoLine2}, Textures.Gui.Basic.INFOICON.getIcon()));

        Slot tSlot = (Slot) inventorySlots.inventorySlots.get(0);
        iComponents.addComponent(new ComponentSlot(this, "Gui.GuiFirePit.Slots." + tSlot.slotNumber, tSlot));
    }
}
