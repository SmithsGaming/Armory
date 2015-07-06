package com.Orion.Armory.Client.GUI;
/*
 *   GuiFirePit
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Client.GUI.Components.ComponentBorder;
import com.Orion.Armory.Client.GUI.Components.ComponentProgressFlame;
import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Ledgers.InfoLedger;
import com.Orion.Armory.Client.GUI.Components.Ledgers.Ledger;
import com.Orion.Armory.Client.GUI.Components.MultiComponents.ComponentPlayerInventory;
import com.Orion.Armory.Common.Inventory.ContainerFirepit;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.StringUtils;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class GuiFirePit extends com.Orion.Armory.Client.GUI.ArmoryBaseGui
{
    private static final int FLAMEXPOS = 176;
    private static final int FLAMEYPOS = 0;

    public GuiFirePit(Container pTargetedContainer) {
        super(pTargetedContainer);

        this.xSize = 175;
        this.ySize = 165;

        }

    @Override
    public void initGui()
    {
        super.initGui();

        if (iComponents.getComponents().size() > 0)
        {
            return;
        }

        iComponents.addComponent(new ComponentBorder(this, "Gui.FirePit.Background", 0, 0, xSize, ySize - 80, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwarts));
        iComponents.addComponent(new ComponentPlayerInventory(this, "Gui.FirePit.Player", 0, 76, (TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT), ComponentBorder.CornerTypes.StraightVertical));

        iComponents.addComponent(new ComponentProgressFlame(this, References.InternalNames.GUIComponents.FirePit.FLAMEONE, 44, 40, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentProgressFlame(this, References.InternalNames.GUIComponents.FirePit.FLAMETWO, 62, 40, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentProgressFlame(this, References.InternalNames.GUIComponents.FirePit.FLAMETHREE, 80, 40, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentProgressFlame(this, References.InternalNames.GUIComponents.FirePit.FLAMEFOUR, 98, 40, Colors.DEFAULT, Colors.DEFAULT));
        iComponents.addComponent(new ComponentProgressFlame(this, References.InternalNames.GUIComponents.FirePit.FLAMEFIVE, 116, 40, Colors.DEFAULT, Colors.DEFAULT));

        this.iLedgers.addLedgerLeft(new InfoLedger(this, TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.FirePit.InfoLine1, "", TranslationKeys.GUI.FirePit.InfoLine2, "", TranslationKeys.GUI.FirePit.InfoLine3}, Textures.Gui.Basic.INFOICON.getIcon()));
        this.iLedgers.addLedgerRight(new TemperatureLedger(this));

        for(int tSlotIndex = 0;tSlotIndex < (TileEntityFirePit.FUELSTACK_AMOUNT + TileEntityFirePit.INGOTSTACKS_AMOUNT); tSlotIndex++)
        {
            Slot tSlot = (Slot) inventorySlots.inventorySlots.get(tSlotIndex);

            iComponents.addComponent(new ComponentSlot(this, "Gui.GuiFirePit.Slots." + tSlot.slotNumber, tSlot));
        }

       }


    protected class TemperatureLedger extends Ledger {
        String[] iTranslatedInfoText;

        public TemperatureLedger(GuiFirePit pGui) {
            super(pGui, "Gui.GuiFirePit.Ledgers.Temperature");

            iHeader = StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.TempTitel);
            iHeaderIcon = Textures.Gui.FirePit.THERMOMETERICON.getIcon();
            iBackgroundColor = Colors.Ledgers.YELLOW;
            ArrayList<String> tTranslationsWithSplit = new ArrayList<String>();

            int tMaxWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(iHeader) - Minecraft.getMinecraft().fontRenderer.getStringWidth("  10000 C");

            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.TempCurrent), tMaxWidth));
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.TempMax), tMaxWidth));
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.LastAdded), tMaxWidth));

            iMaxWidthOpen = 48 + tMaxWidth + Minecraft.getMinecraft().fontRenderer.getStringWidth("  10000 C");
            iMaxHeightOpen = 24 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) * tTranslationsWithSplit.size() + 8;
            iTranslatedInfoText = tTranslationsWithSplit.toArray(new String[0]);
        }


        @Override
        public void drawForeGround(int pX, int pY) {
            ArrayList<String> tTranslationsWithSplit = new ArrayList<String>();

            int tMaxWidth = iMaxWidthOpen - 48;
            TileEntityFirePit teFirePit = ((ContainerFirepit) inventorySlots).GetTileEntity();

            DecimalFormat laf = new DecimalFormat("###.##");

            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.TempCurrent) + " " + Math.round(teFirePit.iCurrentTemperature) + "C", tMaxWidth));
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.TempMax) + " " + teFirePit.iMaxTemperature + "C", tMaxWidth));
            Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString(StatCollector.translateToLocal(TranslationKeys.GUI.FirePit.LastAdded) + " " + laf.format(teFirePit.iLastAddedHeat) + "C", tMaxWidth));

            iTranslatedInfoText = tTranslationsWithSplit.toArray(new String[0]);

            for (int tRule = 0; tRule < iTranslatedInfoText.length; tRule++) {
                int iDrawingX = pX + 24 + getOriginOffSet();
                int iDrawingY = pY + 24 + (mc.fontRenderer.FONT_HEIGHT + 3) * tRule;

                if ((iDrawingY + mc.fontRenderer.FONT_HEIGHT + 3) <= (pY + iCurrentYExtension - 8)) {
                    drawString(mc.fontRenderer, iTranslatedInfoText[tRule], iDrawingX, iDrawingY, Colors.Ledgers.BLACK.getColor());
                }
            }
        }

        @Override
        public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
            return false;
        }
    }
}
