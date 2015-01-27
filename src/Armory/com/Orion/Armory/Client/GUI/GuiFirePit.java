package com.Orion.Armory.Client.Gui;
/*
 *   GuiFirePit
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Client.Logic.CoreIconProvider;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.StringUtils;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collections;

public class GuiFirePit extends ArmoryBaseGui
{
    protected class TemperatureLedger extends Ledger
    {
        final String[] iTranslatedInfoText;

        public TemperatureLedger()
        {
            iHeader = StatCollector.translateToLocal("com.Armory.Orion.FirePit.TempTitel");
            iHeaderIcon = CoreIconProvider.getInstance().getIcon(CoreIconProvider.THERMOMETER);
            iBackgroundColor = Colors.Ledgers.YELLOW;
            ArrayList<String> tTranslationsWithSplit = new ArrayList<String>();

            int tMaxWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(iHeader) - Minecraft.getMinecraft().fontRenderer.getStringWidth("  10000 C");

            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal("com.Orion.Armory.FirePit.Temp.Current"), tMaxWidth));
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal("com.Orion.Armory.FirePit.Temp.LastAdded"), tMaxWidth));

            iMaxWidthOpen = 48 + tMaxWidth + Minecraft.getMinecraft().fontRenderer.getStringWidth("   10000 C");
            iMaxHeightOpen = 24 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) * tTranslationsWithSplit.size() + 8;
            iTranslatedInfoText = tTranslationsWithSplit.toArray(new String[0]);
        }


        @Override
        public void drawForeGround(int pX, int pY) {

        }

        @Override
        public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
            return false;
        }
    }

    public GuiFirePit(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 175;
        this.ySize = 165;

        this.iBackGroundTexture = new ResourceLocation(Textures.Gui.FIREPIT);

        this.iLedgers.addLedgerLeft(new InfoLedger("com.Orion.Armory.Gui.Basic.InfoTitel", new String[]{"com.Orion.Armory.FirePit.Info.S1", "", "com.Orion.Armory.FirePit.Info.S2", "", "com.Orion.Armory.FirePit.Info.S3"}, CoreIconProvider.getInstance().getIcon(0)));
        this.iLedgers.addLedgerRight(new TemperatureLedger());
    }
}
