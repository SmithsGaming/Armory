package com.Orion.Armory.Client.GUI;

import com.Orion.Armory.Client.GUI.Components.Ledgers.InfoLedger;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Orion
 * Created on 24.04.2015
 * 22:36
 * <p/>
 * Copyrighted according to Project specific license
 */
public class GuiHeater extends com.Orion.Armory.Client.GUI.ArmoryBaseGui
{
    public GuiHeater(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 175;
        this.ySize = 137;

        this.iBackGroundTexture = new ResourceLocation(Textures.Gui.Heater.BACKGROUND.getPrimaryLocation());

        this.iLedgers.addLedgerLeft(new InfoLedger(this, TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.Heater.InfoLine1, "", TranslationKeys.GUI.Heater.InfoLine2}, Textures.Gui.Basic.INFOICON.getIcon()));
    }
}
