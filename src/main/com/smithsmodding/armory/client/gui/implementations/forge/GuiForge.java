package com.smithsmodding.armory.client.gui.implementations.forge;

import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.legders.core.LedgerConnectionSide;
import com.smithsmodding.smithscore.client.gui.legders.implementations.CoreLedger;
import com.smithsmodding.smithscore.client.gui.legders.implementations.InformationLedger;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.LedgerComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.CustomResource;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by Marc on 22.12.2015.
 */
public class GuiForge extends GuiContainerSmithsCore {

    @Nonnull
    public static Plane GUI = new Plane(0, 0, ComponentPlayerInventory.WIDTH, 200);

    public GuiForge(@Nonnull ContainerSmithsCore container) {
        super(container);
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Forge.BACKGROUND, host, new Coordinate2D(0, 0), GuiForge.GUI.getWidth(), GuiForge.GUI.getHeigth() - (ComponentPlayerInventory.HEIGHT - 3), com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Forge.INVENTORY, host, new Coordinate2D(0, 80), com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ((ContainerSmithsCore) inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));


        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEONE, host, new CoreComponentState(null), new Coordinate2D(44, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMETWO, host, new CoreComponentState(null), new Coordinate2D(62, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMETHREE, host, new CoreComponentState(null), new Coordinate2D(80, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEFOUR, host, new CoreComponentState(null), new Coordinate2D(98, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.FLAMEFIVE, host, new CoreComponentState(null), new Coordinate2D(116, 44), ComponentOrientation.VERTICALBOTTOMTOTOP, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.smithsmodding.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));


        for (int tSlotIndex = 0; tSlotIndex < (TileEntityForge.FUELSTACK_AMOUNT + TileEntityForge.INGOTSTACKS_AMOUNT); tSlotIndex++) {
            Slot slot = inventorySlots.inventorySlots.get(tSlotIndex);

            if (tSlotIndex < TileEntityForge.INGOTSTACKS_AMOUNT) {
                host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Forge.MELT + "." + tSlotIndex, host, new CoreComponentState(null), new Coordinate2D(slot.xPos + 4, slot.yPos + 19 - getTabManager().getDisplayAreaVerticalOffset()), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.FirePit.DROPEMPTY, Textures.Gui.FirePit.DROPFULL));
            }

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Forge.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ((ContainerSmithsCore) inventorySlots).getContainerInventory(), null), host, new Coordinate2D(slot.xPos - 1, slot.yPos - getTabManager().getDisplayAreaVerticalOffset() - 1), com.smithsmodding.armory.api.util.client.Colors.DEFAULT));
        }
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerLedgers(IGUIBasedLedgerHost parent) {
        ArrayList<String> information = new ArrayList<String>();

        information.add(I18n.format(TranslationKeys.Gui.Forge.InfoLine1));
        information.add(I18n.format(TranslationKeys.Gui.Forge.InfoLine2));
        information.add(I18n.format(TranslationKeys.Gui.Forge.InfoLine3));

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.LEFT, I18n.format(TranslationKeys.Gui.InformationTitel), new MinecraftColor(MinecraftColor.YELLOW), information));
        registerNewLedger(new TemperatureLedger(getID() + ".Ledgers.Temperature", this, LedgerConnectionSide.RIGHT, Textures.Gui.FirePit.THERMOMETERICON, I18n.format(TranslationKeys.Gui.Forge.TempTitel), new MinecraftColor(MinecraftColor.RED)));
    }

    public class TemperatureLedger extends CoreLedger {

        Plane maxArea;
        @Nonnull
        private String currentTemperatureLabel = "";
        @Nonnull
        private String maxTemperatureLabel = "";
        @Nonnull
        private String lastAddedLabel = "";

        public TemperatureLedger(String uniqueID, IGUIBasedLedgerHost root, LedgerConnectionSide side, @Nonnull CustomResource ledgerIcon, String translatedLedgerHeader, MinecraftColor color) {
            super(uniqueID, new LedgerComponentState(), root, side, ledgerIcon, translatedLedgerHeader, color);
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon width that describes the maximum width of the Ledger when expanded.
         */
        @Override
        public int getMaxWidth() {
            return maxArea.getWidth();
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon height that describes the maximum height of the Ledger when expanded.
         */
        @Override
        public int getMaxHeight() {
            return maxArea.getHeigth();
        }

        /**
         * Function used to register the sub components of this ComponentHost
         *
         * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
         */
        @Override
        public void registerComponents(IGUIBasedComponentHost host) {
            super.registerComponents(host);

            currentTemperatureLabel = I18n.format(TranslationKeys.Gui.Forge.TempCurrent.toLowerCase()) + ": 10000C";
            maxTemperatureLabel = I18n.format(TranslationKeys.Gui.Forge.TempMax) + ": 10000C";
            lastAddedLabel = I18n.format(TranslationKeys.Gui.Forge.LastAdded) + ": 10000C";

            registerNewComponent(new ComponentLabel(getID() + ".CurrentTemperature", host, new CoreComponentState(), new Coordinate2D(10, 26), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, currentTemperatureLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Forge.TempCurrent) + ": " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".MaxTemperature", host, new CoreComponentState(), new Coordinate2D(10, 26 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, maxTemperatureLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Forge.TempMax) + ": " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".LastChange", host, new CoreComponentState(), new Coordinate2D(10, 26 + 2 * (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3)), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, lastAddedLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Forge.LastAdded) + ": " + getManager().getLabelContents(this);
                }
            });

            Plane area = new Plane(0, 0, 0, 0);

            for (IGUIComponent component : getAllComponents().values()) {
                area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
            }

            maxArea = new Plane(new Coordinate2D(0, 0), area.getWidth() + 10, area.getHeigth() + 10);
        }
    }
}
