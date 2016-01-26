package com.smithsmodding.armory.client.gui.firepit;

import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.client.gui.tabs.implementations.*;
import com.smithsmodding.smithscore.common.inventory.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 25.01.2016.
 */
public class TabFirePitMoltenMetal extends CoreTab {

    GuiFirePit firePit;

    public TabFirePitMoltenMetal (String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiFirePit) root;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.FirePit.BACKGROUND, host, new Coordinate2D(0, 0), GuiFirePit.GUI.getWidth(), GuiFirePit.GUI.getHeigth() - ( ComponentPlayerInventory.HEIGHT - 3 ), com.smithsmodding.armory.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.FirePit.INVENTORY, host, new Coordinate2D(0, 76), com.smithsmodding.armory.util.client.Colors.DEFAULT, ( (ContainerSmithsCore) firePit.inventorySlots ).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentFluidTank(References.InternalNames.GUIComponents.FirePit.MOLTENMETALS, host, new CoreComponentState(), new Coordinate2D(7, 7), 35, 70, ComponentOrientation.VERTICALBOTTOMTOTOP));
    }
}
