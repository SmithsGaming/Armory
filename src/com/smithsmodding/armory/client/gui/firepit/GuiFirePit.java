package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.legders.core.*;
import com.smithsmodding.smithscore.client.gui.legders.implementations.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.Colors;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class GuiFirePit extends GuiContainerSmithsCore {

    public static Plane GUI = new Plane(0, 0, ComponentPlayerInventory.WIDTH, 245);

    public GuiFirePit (ContainerSmithsCore container) {
        super(container);
    }

    /**
     * Method called by the gui system to initialize this tab host.
     *
     * @param host The host for the tabs.
     */
    @Override
    public void registerTabs (IGUIBasedTabHost host) {
        registerNewTab(new TabFirePitMeltingMetal(getID() + ".Tabs.Inventory", host, new ItemStack(Items.iron_ingot), Colors.DEFAULT, "Melting ingots"));
        registerNewTab(new TabFirePitMoltenMetal(getID() + ".Tabs.MoltenMetal", host, new ItemStack(Items.lava_bucket), Colors.DEFAULT, "Molten metals."));
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerLedgers (IGUIBasedLedgerHost parent) {
        ArrayList<String> information = new ArrayList<String>();

        information.add(StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.InfoLine1));
        information.add(StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.InfoLine2));
        information.add(StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.InfoLine3));

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.LEFT, StatCollector.translateToLocal(TranslationKeys.Gui.InformationTitel), new MinecraftColor(MinecraftColor.YELLOW), information));
        registerNewLedger(new TemperatureLedger(getID() + ".Ledgers.Temperature", this, LedgerConnectionSide.RIGHT, Textures.Gui.FirePit.THERMOMETERICON, StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.TempTitel), new MinecraftColor(MinecraftColor.RED)));
    }

    public class TemperatureLedger extends CoreLedger {

        Plane maxArea;
        private String currentTemperatureLabel = "";
        private String maxTemperatureLabel = "";
        private String lastAddedLabel = "";

        public TemperatureLedger (String uniqueID, IGUIBasedLedgerHost root, LedgerConnectionSide side, CustomResource ledgerIcon, String translatedLedgerHeader, MinecraftColor color) {
            super(uniqueID, new LedgerComponentState(), root, side, ledgerIcon, translatedLedgerHeader, color);
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon width that describes the maximum width of the Ledger when expanded.
         */
        @Override
        public int getMaxWidth () {
            return maxArea.getWidth();
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon height that describes the maximum height of the Ledger when expanded.
         */
        @Override
        public int getMaxHeight () {
            return maxArea.getHeigth();
        }

        /**
         * Function used to register the sub components of this ComponentHost
         *
         * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
         */
        @Override
        public void registerComponents (IGUIBasedComponentHost host) {
            super.registerComponents(host);

            currentTemperatureLabel = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.TempCurrent) + ": 10000C";
            maxTemperatureLabel = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.TempMax) + ": 10000C";
            lastAddedLabel = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.LastAdded) + ": 10000C";

            registerNewComponent(new ComponentLabel(getID() + ".CurrentTemperature", host, new CoreComponentState(), new Coordinate2D(10, 26), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, currentTemperatureLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update (int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.TempCurrent) + " " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".MaxTemperature", host, new CoreComponentState(), new Coordinate2D(10, 26 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, maxTemperatureLabel){
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update (int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.TempMax) + " " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".LastChange", host, new CoreComponentState(), new Coordinate2D(10, 26 + 2 *(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3)), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, lastAddedLabel){
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update (int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = StatCollector.translateToLocal(TranslationKeys.Gui.FirePit.LastAdded) + " " + getManager().getLabelContents(this);
                }
            });

            Plane area = new Plane(0, 0, 0, 0);

            for (IGUIComponent component : getAllComponents().values()) {
                area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
            }

            maxArea = new Plane(new Coordinate2D(0,0), area.getWidth() + 10, area.getHeigth() + 10);
        }
    }
}
