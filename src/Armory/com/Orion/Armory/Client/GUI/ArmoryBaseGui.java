package com.Orion.Armory.Client.Gui;
/*
/  ArmoryBaseGui
/  Created by : Orion
/  Created on : 15/01/2015
*/

import com.Orion.Armory.Util.Client.*;
import com.Orion.Armory.Util.References;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ArmoryBaseGui extends GuiContainer
{
    LedgerManager iLedgers = new LedgerManager(this);
    ResourceLocation iBackGroundTexture;


    public ArmoryBaseGui(Container pTargetedContainer) {
        super(pTargetedContainer);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float pFloat, int pMouseX, int pMouseY) {
        //Render ledgers background at a lower level then the rest!
        GL11.glPushMatrix();
        this.zLevel -= 2;
        iLedgers.drawBackgroundOfLedgers();
        this.zLevel += 2;
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft, guiTop, 0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(iBackGroundTexture);
        this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);
        GL11.glPopMatrix();
    }


    protected class LedgerManager
    {
        ArmoryBaseGui gui;
        ArrayList<Ledger> ledgersLeft = new ArrayList<Ledger>();
        ArrayList<Ledger> ledgersRight = new ArrayList<Ledger>();

        public LedgerManager(ArmoryBaseGui pGui)
        {
            this.gui = pGui;
        }

        public void addLedgerLeft(Ledger pNewLedger)
        {
            ledgersLeft.add(pNewLedger);
        }

        public void addLedgerRight(Ledger pNewLedger)
        {
            ledgersRight.add(pNewLedger);
        }

        public Ledger getLedgetAt(int pTargetX, int pTargetY)
        {
            for(int i = 0; i < ledgersLeft.size(); i++)
            {
                Ledger tLedger = ledgersLeft.get(i);
                if (tLedger.checkIfPointIsInLedger(pTargetX, pTargetY)) { return tLedger; }
            }

            for(int i = 0; i < ledgersRight.size(); i++)
            {
                Ledger tLedger = ledgersRight.get(i);
                if (tLedger.checkIfPointIsInLedger(pTargetX, pTargetY)) { return tLedger; }
            }

            return null;
        }

        public void drawBackgroundOfLedgers()
        {
            int tYPos = guiTop + 8;
            for(int i = 0; i < ledgersLeft.size(); i++)
            {
                Ledger tLedger = ledgersLeft.get(i);
                tLedger.update();

                tLedger.drawBackGround(guiLeft, tYPos);
                tYPos += tLedger.getHeight();
            }

            tYPos = guiTop + 8;
            for(int i = 0; i < ledgersRight.size(); i++)
            {
                Ledger tLedger = ledgersRight.get(i);
                tLedger.update();

                tLedger.drawBackGround(guiLeft+xSize, tYPos);
                tYPos += tLedger.getHeight();
            }
        }

        public void drawForegroundOfLedgers(int pMouseX, int pMouseY)
        {
            int tYPos = guiTop + 8;
            for(int i = 0; i < ledgersLeft.size(); i++)
            {
                Ledger tLedger = ledgersLeft.get(i);

                tLedger.drawForeGround(guiLeft, tYPos);
                if (tLedger.checkIfPointIsInLedger(pMouseX, pMouseY)) { tLedger.drawToolTips(pMouseX, pMouseY); }
                tYPos += tLedger.getHeight();
            }

            tYPos = guiTop + 8;
            for(int i = 0; i < ledgersRight.size(); i++)
            {
                Ledger tLedger = ledgersRight.get(i);

                tLedger.drawForeGround(guiLeft+xSize, tYPos);
                if (tLedger.checkIfPointIsInLedger(pMouseX, pMouseY)) { tLedger.drawToolTips(pMouseX, pMouseY); }
                tYPos += tLedger.getHeight();
            }
        }
    }

    protected abstract class Ledger
    {
        public int iCurrentXExtension;
        public int iCurrentYExtension;
        public int iLastXOrigin = 0;
        public int iLastYOrigin = 0;

        public Color iBackgroundColor = Colors.Ledgers.DEFAULT;
        public ResourceLocation iHeaderIcon;
        public Color iHeaderTextColor;
        public String iHeader = "";
        public Boolean iOpen = false;
        public LedgerDirection iDirection;

        public int iLimitWidth = 128;
        public int iLimitHeight = 128;

        public int iMaxHeightOpen = 124;
        public int iMaxHeightClosed = 24;
        public int iMaxWidthOpen = 124;
        public int iMaxWidthClosed = 24;

        public ResourceLocation TEXTURELEFT = new ResourceLocation(References.General.MOD_ID.toLowerCase(), Textures.Gui.Basic.LEDGERLEFT);
        public ResourceLocation TEXTURERIGHT = new ResourceLocation(References.General.MOD_ID.toLowerCase(), Textures.Gui.Basic.LEDGERRIGHT);

        public void update()
        {
            if (iOpen && iCurrentXExtension < iMaxWidthOpen)
            {
                iCurrentXExtension += 4;
            }
            else if( !iOpen && iCurrentXExtension > iMaxWidthClosed)
            {
                iCurrentXExtension -= 4;
            }

            if (iOpen && iCurrentYExtension < iMaxHeightOpen)
            {
                iCurrentYExtension += 4;
            }
            else if(!iOpen && iCurrentYExtension > iMaxHeightClosed)
            {
                iCurrentYExtension -= 4;
            }
        }

        public int getWidth()
        {
            if(iDirection == LedgerDirection.Left)
            {
                return iCurrentXExtension * -1;
            }

            return iCurrentXExtension;
        }

        public int getHeight()
        {
            return iCurrentYExtension;
        }

        /*
         * parameter pX: always directly to the border of the Gui
         * parameter pY: always directly under the last rendered Ledger
         */
        public void drawBackGround(int pX, int pY)
        {
            GL11.glColor3f(iBackgroundColor.getColorRedFloat(), iBackgroundColor.getColorGreenFloat(), iBackgroundColor.getColorBlueFloat());

            if(iDirection == LedgerDirection.Left)
            {
                mc.renderEngine.bindTexture(TEXTURELEFT);
            }
            else
            {
                mc.renderEngine.bindTexture(TEXTURERIGHT);
            }

            drawTexturedModalRect(pX + getWidth(), pY, 0, 256, iCurrentXExtension, iCurrentYExtension);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        public void drawHeaderIcon(int pX, int pY)
        {
            mc.renderEngine.bindTexture(iHeaderIcon);
            drawTexturedModalRect(pX + getWidth() + 4, pY + 4, 0, 16, 16, 16);
        }

        public void drawHeaderText(int pX, int pY, FontRenderer pFont)
        {
            drawCenteredString(pFont, iHeader, (iMaxWidthOpen - 48) / 2, 4, iHeaderTextColor.getColor());
        }

        public void draw(int pX, int pY)
        {
            drawBackGround(pX, pY);
            drawHeaderIcon(pX, pY);

            if (!iOpen)
            {
                return;
            }

            drawHeaderText(pX, pY, mc.fontRenderer);
            drawForeGround(pX, pY);

            iLastXOrigin = pX;
            iLastYOrigin = pY;
        }

        public abstract void drawForeGround(int pX, int pY);

        public void drawToolTips(int pMouseX, int pMouseY)
        {

        }

        public void setFullyOpen()
        {
            iOpen = true;
            iCurrentXExtension=iMaxWidthOpen;
            iCurrentYExtension = iMaxHeightOpen;
        }

        public void toggleOpenState()
        {
            if(iOpen)
            {
                iOpen = false;
                SessionVars.setLastOpenenedLedger(null);
            }
            else
            {
                iOpen = true;
                SessionVars.setLastOpenenedLedger(this.getClass());
            }
        }

        public boolean checkIfPointIsInLedger(int pTargetX, int pTargetY)
        {
            if ((iLastXOrigin <= pTargetX) && ((iLastXOrigin + getWidth()) >= pTargetX))
            {
                if((iLastYOrigin <= pTargetY) && ((iLastYOrigin + getHeight()) >= pTargetY))
                {
                    return true;
                }
            }

            return false;
        }
    }

    protected class InfoLedger extends Ledger
    {
        final String[] iTranslatedInfoText;

        public InfoLedger(String pTitel, String[] pUntranslatedInfotext, ResourceLocation pIconLocation) {
            iHeader = pTitel;
            iHeaderIcon = pIconLocation;
            iTranslatedInfoText = new String[pUntranslatedInfotext.length];

            for(int tRule = 0; tRule < pUntranslatedInfotext.length; tRule++)
            {
                iTranslatedInfoText[tRule] = StatCollector.translateToLocal(pUntranslatedInfotext[tRule]);
            }

            iMaxWidthOpen = 48 + StringUtils.GetMininumWidth(iTranslatedInfoText, mc.fontRenderer);
            iMaxHeightOpen = 24 * (pUntranslatedInfotext.length + 1);
        }


        @Override
        public void drawForeGround(int pX, int pY) {
            for (int tRule = 0; tRule < iTranslatedInfoText.length; tRule++)
            {
                drawString(mc.fontRenderer, iTranslatedInfoText[tRule],pX + 24, pY + 24 * (tRule + 1), Colors.Ledgers.BLACK.getColor());
            }
        }
    }

    protected enum LedgerDirection
    {
        Left, Right;
    }

}
