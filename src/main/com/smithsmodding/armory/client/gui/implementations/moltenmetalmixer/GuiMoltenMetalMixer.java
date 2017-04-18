package com.smithsmodding.armory.client.gui.implementations.moltenmetalmixer;

import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentFluidTank;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentPlayerInventory;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;

import javax.annotation.Nonnull;

import static com.smithsmodding.armory.api.util.references.References.InternalNames.GUIComponents.MoltenMetalMixer.*;

/**
 * Created by marcf on 3/9/2017.
 */
public class GuiMoltenMetalMixer extends GuiContainerSmithsCore {

    @Nonnull
    public static Plane GUI = new Plane(0, 0, ComponentPlayerInventory.WIDTH, 200);

    public GuiMoltenMetalMixer(@Nonnull ContainerSmithsCore container) {
        super(container);
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerLedgers(@Nonnull IGUIBasedLedgerHost parent) {

    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(CPN_BACKGROUND, host, new Coordinate2D(0, 0), GUI.getWidth(), 111, com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(CPN_INVENTORY, host, new Coordinate2D(0, 107), com.smithsmodding.armory.api.util.client.Colors.DEFAULT, ((ContainerSmithsCore) inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentFluidTank(CPN_MOLTENMETALSLEFT, host, new CoreComponentState(), new Coordinate2D(7, 7), 20, 52, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(CPN_MOLTENMETALSRIGHT, host, new CoreComponentState(), new Coordinate2D(GUI.getWidth() - 7 - 20, 7), 20, 52, ComponentOrientation.VERTICALBOTTOMTOTOP));
        host.registerNewComponent(new ComponentFluidTank(CPN_MOLTENMETALSOUTPUT, host, new CoreComponentState(), new Coordinate2D(GUI.getWidth() / 2 - 10, 35), 20, 52, ComponentOrientation.VERTICALBOTTOMTOTOP));


        host.registerNewComponent(new ComponentProgressBar(CPN_PROGRESSMIXINGINLEFTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(29, 7), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILLEFTTORIGHTFULL));
        host.registerNewComponent(new ComponentProgressBar(CPN_PROGRESSMIXINGINLEFTVERTICAL, host, new CoreComponentState(), new Coordinate2D(70, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMLEFTCONNTECTORFULL));

        host.registerNewComponent(new ComponentProgressBar(CPN_PROGRESSMIXINGINRIGHTHORIZONTAL, host, new CoreComponentState(), new Coordinate2D(98, 7), ComponentOrientation.HORIZONTALRIGHTTOLEFT, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTEMPTY, Textures.Gui.Basic.Components.HORIZONTALTAILRIGHTTOLEFTFULL));
        host.registerNewComponent(new ComponentProgressBar(CPN_PROGRESSMIXINGINRIGHTVERTICAL, host, new CoreComponentState(), new Coordinate2D(91, 7), ComponentOrientation.VERTICALTOPTOBOTTOM, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTOREMPTY, Textures.Gui.Basic.Components.VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTORFULL));
    }
}
