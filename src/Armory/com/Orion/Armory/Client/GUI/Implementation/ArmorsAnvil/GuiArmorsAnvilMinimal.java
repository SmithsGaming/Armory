package com.Orion.Armory.Client.GUI.Implementation.ArmorsAnvil;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentImage;
import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.ComponentTextbox;
import com.Orion.Armory.Client.GUI.Components.Ledgers.InfoLedger;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentExtendedCraftingGrid;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Network.Messages.MessageCustomInput;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import org.lwjgl.input.Keyboard;

/**
 * Created by Orion
 * Created on 15.05.2015
 * 12:35
 * <p/>
 * Copyrighted according to Project specific license
 */
public class GuiArmorsAnvilMinimal extends ArmoryBaseGui {
    public GuiArmorsAnvilMinimal(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 215;
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

        iComponents.addComponent(new ComponentBorder(this, References.InternalNames.GUIComponents.Anvil.BACKGROUND, 0, 0, xSize, ySize - 80, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentPlayerInventory(this, References.InternalNames.GUIComponents.Anvil.PLAYERINVENTORY, 20, 172, (29), ComponentBorder.CornerTypes.Outwarts));
        iComponents.addComponent(new ComponentExtendedCraftingGrid(this, References.InternalNames.GUIComponents.Anvil.EXTENDEDCRAFTING, 10, 51, 0, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentBorder(this, References.InternalNames.GUIComponents.Anvil.SMITHINGSGUIDEBORDER, 178, 51, 30, 30, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentBorder(this, References.InternalNames.GUIComponents.Anvil.TEXTBOXBORDER, 61, 7, 111, 30, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentBorder(this, References.InternalNames.GUIComponents.Anvil.TOOLSLOTBORDER, 178, 103, 30, 52, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentSlot(this, References.InternalNames.GUIComponents.Anvil.HAMMERSLOT, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS, 18, 18, 184, 109, Textures.Gui.Anvil.HAMMERSLOT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentSlot(this, References.InternalNames.GUIComponents.Anvil.TONGSLOT, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS, 18, 18, 184, 131, Textures.Gui.Anvil.TONGSSLOT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentImage(this, References.InternalNames.GUIComponents.Anvil.LOGO, 17, 7, Textures.Gui.Anvil.HAMMER));
        iComponents.addComponent(new ComponentSlot(this, References.InternalNames.GUIComponents.Anvil.SMITHINGSGUIDESLOT, TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS + TileEntityArmorsAnvil.MAX_OUTPUTSLOTS + TileEntityArmorsAnvil.MAX_HAMMERSLOTS + TileEntityArmorsAnvil.MAX_TONGSLOTS + TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS + TileEntityArmorsAnvil.MAX_COOLSLOTS, 18, 18, 184, 57, Textures.Gui.Anvil.BOOKSLOT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentTextbox(this, References.InternalNames.GUIComponents.Anvil.TEXTBOX, Minecraft.getMinecraft().fontRenderer, 65, 11, 102, 22, References.InternalNames.InputHandlers.Anvil.ITEMNAME));

        getLedgerManager().addLedgerLeft(new InfoLedger(this, TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.Anvil.InfoLine1, "", TranslationKeys.GUI.Anvil.InfoLine2}, Textures.Gui.Basic.INFOICON.getIcon()));

        NetworkManager.INSTANCE.sendToServer(new MessageCustomInput("Gui.Connect", Minecraft.getMinecraft().thePlayer.getGameProfile().getId().toString()));
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        NetworkManager.INSTANCE.sendToServer(new MessageCustomInput("Gui.Disconnect", Minecraft.getMinecraft().thePlayer.getGameProfile().getId().toString()
        ));
    }
}
