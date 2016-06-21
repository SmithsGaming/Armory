package com.smithsmodding.armory.client.gui.blacksmithsanvil;

import com.smithsmodding.armory.client.gui.components.ComponentBlackSmithsAnvilCraftingGrid;
import com.smithsmodding.armory.client.gui.components.ComponentExperienceLabel;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.legders.core.LedgerConnectionSide;
import com.smithsmodding.smithscore.client.gui.legders.implementations.InformationLedger;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.client.gui.state.TextboxComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.color.Colors;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;

/**
 * @Author Marc (Created on: 16.06.2016)
 */
public class GuiBlacksmithsAnvil extends GuiContainerSmithsCore {

    public GuiBlacksmithsAnvil(ContainerSmithsCore container) {
        super(container);
    }

    @Override
    public void registerLedgers(IGUIBasedLedgerHost parent) {
        ArrayList<String> information = new ArrayList<String>();

        information.add(I18n.format(TranslationKeys.Gui.Anvil.InfoLine1));
        information.add(I18n.format(TranslationKeys.Gui.Anvil.InfoLine2));

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.LEFT, I18n.format(TranslationKeys.Gui.InformationTitel), new MinecraftColor(MinecraftColor.YELLOW), information));
    }

    @Override
    public void registerComponents(IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.BACKGROUND, host, new Coordinate2D(0,0), 215, 175, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Anvil.PLAYERINVENTORY, host, new Coordinate2D(20, 172), Colors.DEFAULT, ((ContainerSmithsCore) inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWSMALLER));
        host.registerNewComponent(new ComponentBlackSmithsAnvilCraftingGrid(References.InternalNames.GUIComponents.Anvil.EXTENDEDCRAFTING, host, new CoreComponentState(), new Coordinate2D(10, 51), 0, TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, (ContainerSmithsCore) inventorySlots));
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.TEXTBOXBORDER, host, new Coordinate2D(61, 7), 111, 30, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.TOOLSLOTBORDER, host, new Coordinate2D(178, 103), 30, 52, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Anvil.HAMMERSLOT, new SlotComponentState(null, inventorySlots.getSlot(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS), ((ContainerSmithsCore) inventorySlots).getContainerInventory(), Textures.Gui.Anvil.HOLOWHAMMER.getIcon()), host, inventorySlots.getSlot(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS), Colors.DEFAULT));
        host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Anvil.TONGSLOT, new SlotComponentState(null, inventorySlots.getSlot(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS), ((ContainerSmithsCore) inventorySlots).getContainerInventory(), Textures.Gui.Anvil.HOLOWTONGS.getIcon()), host, inventorySlots.getSlot(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_TONGSLOTS), Colors.DEFAULT));
        host.registerNewComponent(new ComponentImage(References.InternalNames.GUIComponents.Anvil.LOGO, new CoreComponentState(), host, new Coordinate2D(17,7), Textures.Gui.Anvil.LOGO));
        host.registerNewComponent(new ComponentExperienceLabel(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL, host, new CoreComponentState(), new Coordinate2D(115, 78)));
        host.registerNewComponent(new ComponentTextbox(References.InternalNames.GUIComponents.Anvil.TEXTBOX, new TextboxComponentState(null), host, new Coordinate2D(65,11), 102,22));
    }
}
