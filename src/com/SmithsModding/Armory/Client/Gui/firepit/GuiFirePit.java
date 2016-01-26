package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.legders.core.*;
import com.smithsmodding.smithscore.client.gui.legders.implementations.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

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

        information.add("test");
        information.add("Lorei ipsum");

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.RIGHT, "FirePit", new MinecraftColor(MinecraftColor.YELLOW), information));
        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information2", this, LedgerConnectionSide.LEFT, "FirePit", new MinecraftColor(MinecraftColor.ORANGE), information));
        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information3", this, LedgerConnectionSide.LEFT, "FirePit", new MinecraftColor(MinecraftColor.RED), information));
    }
}
