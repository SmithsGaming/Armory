package com.Orion.Armory.Client.GUI;

import com.Orion.Armory.Client.GUI.Components.*;
import com.Orion.Armory.Client.GUI.Components.Ledgers.InfoLedger;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentExtendedCraftingGrid;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiDirection;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import org.lwjgl.input.Keyboard;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 12:20
 * <p/>
 * Copyrighted according to Project specific license
 */
public class GuiArmorsAnvilStandard extends ArmoryBaseGui {
    public GuiArmorsAnvilStandard(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 266;
        this.ySize = 255;

    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);

        if (iComponents.getComponents().size() > 0)
        {
            return;
        }

        iComponents.addComponent(new ComponentBorder(this, "Gui.Anvil.Background", 0, 0, xSize, ySize - 80, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        //iComponents.addComponent(new ComponentPlayerInventory(this, "Gui.Anvil.Player", 44, 172, (TileEntityArmorsAnvil.MAX_SLOTS), ComponentBorder.CornerTypes.Outwarts));

        iComponents.addComponent(new ComponentPlayerInventory(this, "Gui.Anvil.Player", 45, 172, (26), ComponentBorder.CornerTypes.Outwarts));

        //TODO: Add the information Ledger
        this.iLedgers.addLedgerLeft(new InfoLedger(this, TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.FirePit.InfoLine1, "", TranslationKeys.GUI.FirePit.InfoLine2, "", TranslationKeys.GUI.FirePit.InfoLine3}, Textures.Gui.Basic.INFOICON.getIcon()));

        iComponents.addComponent(new ComponentExtendedCraftingGrid(this, "Gui.Anvil.ExtendedCrafting", 10, 51, 0, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.Tools.Slot.Hammer", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS, 18, 18, 147, 58, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT, Textures.Gui.Anvil.HAMMERSLOT));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.Tools.Slot.Tongs", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS, 18, 18, 147, 130, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT, Textures.Gui.Anvil.TONGSSLOT));

        iComponents.addComponent(new ComponentBorder(this, "Gui.Anvil.AdditionalStacks.Background", 180, 51, 76, 32, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.AdditionalStacks.Slots.1", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS, 18,18,187,58, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.AdditionalStacks.Slots.2", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + 1, 18,18,209,58, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.AdditionalStacks.Slots.3", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + 2, 18,18,231,58, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));

        iComponents.addComponent(new ComponentBorder(this, "Gui.Anvil.Cooling.Background", 180, 91, 76, 64, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentFluidTank(this, "Gui.Anvil.Cooling.Tank", 187, 98, 62, 18, Colors.DEFAULT, 4000, GuiDirection.HORIZONTAL));
        iComponents.addComponent(new ComponentProgressBar(this, "Gui.Anvil.Cooling.Progress", 200, 124, Textures.Gui.Basic.Images.ARROWRIGHTGRAY, Textures.Gui.Basic.Images.ARROWRIGHTWHITE, Colors.DEFAULT, Colors.General.BLUE));
        iComponents.addComponent(new ComponentSlot(this, "Gui.Anvil.Cooling.Slot", TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS, 18,18, 231, 130, Textures.Gui.Basic.Slots.DEFAULT, Colors.DEFAULT));

        iComponents.addComponent(new ComponentImage(this, "Gui.Anvil.Logo", 17, 7, Textures.Gui.Anvil.HAMMER));
        iComponents.addComponent(new ComponentBorder(this, "Gui,Anvil.Name.Border", 61, 7, 150, 30, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentTextbox(this, "Gui.Anvil.Name.Textbox", Minecraft.getMinecraft().fontRenderer, 65, 11, 142, 22));
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
}
