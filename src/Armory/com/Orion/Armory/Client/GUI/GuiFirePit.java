package com.Orion.Armory.Client.Gui;
/*
 *   GuiFirePit
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFirePit extends ArmoryBaseGui
{

    public GuiFirePit(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 175;
        this.ySize = 165;

        this.iBackGroundTexture = new ResourceLocation(Textures.Gui.FIREPIT);
    }
}
