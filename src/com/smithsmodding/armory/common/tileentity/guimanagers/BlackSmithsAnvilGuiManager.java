package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.smithscore.client.gui.management.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlackSmithsAnvilGuiManager extends TileStorageBasedGUIManager
{

    TileEntityBlackSmithsAnvil anvil;

    public BlackSmithsAnvilGuiManager(TileEntityBlackSmithsAnvil anvil)
    {
        this.anvil = anvil;
    }
}
