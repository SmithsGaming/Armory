package com.Orion.Armory.Client.Gui;
/*
/  ArmoryBaseGui
/  Created by : Orion
/  Created on : 15/01/2015
*/

import com.Orion.Armory.Util.Client.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;

public class ArmoryBaseGui extends GuiContainer
{
    LedgerManager iLedgers = new LedgerManager(this);
    ResourceLocation iBackGroundTexture;
    int iDisplayHeight;
    int iDisplayWidth;
    int iGuiScale;


    public ArmoryBaseGui(Container pTargetedContainer) {
        super(pTargetedContainer);
        calcGUIProperties();
    }

    protected void calcGUIProperties()
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        iDisplayWidth = sc.getScaledWidth();
        iDisplayHeight = sc.getScaledHeight();
        iGuiScale = sc.getScaleFactor();
    }

    private void calcScaleFactor(int guiScale)
    {
        this.iGuiScale = 1;
        if (guiScale == 0)
            guiScale = 1000;

        while (this.iGuiScale < guiScale && this.iDisplayWidth / (this.iGuiScale + 1) >= 320
                && this.iDisplayHeight / (this.iGuiScale + 1) >= 240)
            ++this.iGuiScale;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float pFloat, int pMouseX, int pMouseY) {
        //Render ledgers background at a lower level then the rest!
        GL11.glPushMatrix();
        //this.zLevel -= 2;
        iLedgers.drawLedgers();
        //this.zLevel += 2;
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft, guiTop, 0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(iBackGroundTexture);
        this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int mX = mouseX - guiLeft;
        int mY = mouseY - guiTop;

        super.mouseClicked(mouseX, mouseY, mouseButton);

        // / Handle ledger clicks
        if (iLedgers.handleMouseClicked(mouseX, mouseY, mouseButton)) { return; }
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
            pNewLedger.iDirection = LedgerDirection.Left;
            ledgersLeft.add(pNewLedger);
        }

        public void addLedgerRight(Ledger pNewLedger)
        {
            pNewLedger.iDirection = LedgerDirection.Right;
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

        public void drawLedgers()
        {
            int tYPos = guiTop + 8;
            for(int i = 0; i < ledgersLeft.size(); i++)
            {
                Ledger tLedger = ledgersLeft.get(i);
                tLedger.update();

                tLedger.draw(guiLeft, tYPos);
                tYPos += tLedger.getHeight();
            }

            tYPos = guiTop + 8;
            for(int i = 0; i < ledgersRight.size(); i++)
            {
                Ledger tLedger = ledgersRight.get(i);
                tLedger.update();

                tLedger.draw(guiLeft+xSize, tYPos);
                tYPos += tLedger.getHeight();
            }
        }

        public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton)
        {

            if (pMouseButton == 0) {

                Ledger ledger = this.getLedgetAt(pMouseX, pMouseY);

                // Default action only if the mouse click was not handled by the
                // ledger itself.
                if (ledger != null && !ledger.handleMouseClicked(pMouseX, pMouseY, pMouseButton)) {

                    for (Ledger other : ledgersLeft) {
                        if (other != ledger && other.isOpen()) {
                            other.toggleOpenState();
                        }
                    }
                    for (Ledger other : ledgersRight) {
                        if (other != ledger && other.isOpen()) {
                            other.toggleOpenState();
                        }
                    }
                    ledger.toggleOpenState();
                    return true;
                }
            }
            return false;
        }
    }

    protected abstract class Ledger
    {
        public int iCurrentXExtension = 24;
        public int iCurrentYExtension = 24;
        public int iLastXOrigin = 0;
        public int iLastYOrigin = 0;

        public Color iBackgroundColor = Colors.Ledgers.DEFAULT;
        public IIcon iHeaderIcon;
        public Color iHeaderTextColor = Colors.Ledgers.BLACK;
        public String iHeader = "";
        public Boolean iOpen = false;
        public Boolean iClosed = true;
        public LedgerDirection iDirection;

        public int iLimitWidth = 256;
        public int iLimitHeight = 256;

        public int iMaxHeightOpen = 124;
        public int iMaxHeightClosed = 24;
        public int iMaxWidthOpen = 124;
        public int iMaxWidthClosed = 24;

        public ResourceLocation TEXTURELEFT = new ResourceLocation(Textures.Gui.Basic.LEDGERLEFT);

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

            if (iCurrentYExtension > iMaxHeightClosed)
            {
                iClosed = false;
            }
            else
            {
                iClosed = true;
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

        public int getOriginOffSet()
        {
            if(iDirection == LedgerDirection.Left)
            {
                return iCurrentXExtension * -1;
            }

            return 4;
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

            mc.renderEngine.bindTexture(TEXTURELEFT);

            if (iDirection == LedgerDirection.Left)
            {
                drawTexturedModalRect(pX + getOriginOffSet(), pY, 0, 0, iCurrentXExtension, 4);
                drawTexturedModalRect(pX + getOriginOffSet(), pY + 3, 0, 3, iCurrentXExtension, iCurrentYExtension - 7);
                drawTexturedModalRect(pX + getOriginOffSet(), pY + iCurrentYExtension - 4, 0, 252, iCurrentXExtension, 4);
                drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 256 - iCurrentYExtension, 4, iCurrentYExtension);
                drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 0, 4, 4);
            }
            else
            {
                drawTexturedModalRect(pX, pY + 4, 4,4, iCurrentXExtension + 4, iCurrentYExtension - 8);
                drawTexturedModalRect(pX-4, pY, 0,0, iCurrentXExtension + 8, 4);
                drawTexturedModalRect(pX-4, pY + iCurrentYExtension - 4, 0, 252, iCurrentXExtension + 8, 4);
                drawTexturedModalRect(pX-4, pY + 4, 4,4, 4, iCurrentYExtension-8);
                drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 0, 4, iCurrentYExtension);
                drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY +iCurrentYExtension - 4, 252, 252, 4, 4);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        public void drawHeaderIcon(int pX, int pY)
        {
            mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
            drawTexturedModelRectFromIcon(pX + getOriginOffSet() + 4, pY + 4, iHeaderIcon, 16, 16);
        }

        public void drawHeaderText(int pX, int pY, FontRenderer pFont)
        {
            drawCenteredString(pFont, iHeader, pX + getOriginOffSet() + 24 + (pFont.getStringWidth(iHeader) / 2), pY + 4, iHeaderTextColor.getColor());
        }

        public void draw(int pX, int pY)
        {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
            GL11.glScissor((pX-4) * iGuiScale, (iDisplayHeight) - (( pY + iCurrentYExtension) * iGuiScale), (4 + iCurrentXExtension) * iGuiScale, (iCurrentYExtension) * iGuiScale);

            drawBackGround(pX, pY);
            drawHeaderIcon(pX, pY);

            iLastXOrigin = pX + getOriginOffSet();
            iLastYOrigin = pY;

            if (iClosed)
            {
                GL11.glPopAttrib();
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                return;
            }


            drawHeaderText(pX, pY, mc.fontRenderer);
            drawForeGround(pX, pY);
            GL11.glPopAttrib();
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
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

        public boolean isOpen()
        {
            return iOpen;
        }

        public boolean checkIfPointIsInLedger(int pTargetX, int pTargetY)
        {
            if ((iLastXOrigin <= pTargetX) && ((iLastXOrigin + iCurrentXExtension) >= pTargetX))
            {
                if((iLastYOrigin <= pTargetY) && ((iLastYOrigin + iCurrentYExtension) >= pTargetY))
                {
                    return true;
                }
            }

            return false;
        }

        public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);
    }

    protected class InfoLedger extends Ledger
    {
        final String[] iTranslatedInfoText;

        public InfoLedger(String pTitel, String[] pUntranslatedInfotext, IIcon pIcon) {
            iHeader = StatCollector.translateToLocal(pTitel);
            iHeaderIcon = pIcon;
            iBackgroundColor = Colors.Ledgers.RED;
            ArrayList<String> tTranslationsWithSplit = new ArrayList<String>();

            int tMaxWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(iHeader);

            for(int tRule = 0; tRule < pUntranslatedInfotext.length; tRule++)
            {
                Collections.addAll(tTranslationsWithSplit, StringUtils.SplitString((StatCollector.translateToLocal(pUntranslatedInfotext[tRule])), tMaxWidth));
            }

            iMaxWidthOpen = 48 + tMaxWidth;
            iMaxHeightOpen = 24 + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3) * tTranslationsWithSplit.size() + 8;
            iTranslatedInfoText = tTranslationsWithSplit.toArray(new String[0]);
        }

        @Override
        public void drawForeGround(int pX, int pY) {
            for (int tRule = 0; tRule < iTranslatedInfoText.length; tRule++)
            {
                int iDrawingX = pX + 24 + getOriginOffSet();
                int iDrawingY = pY + 24 + (mc.fontRenderer.FONT_HEIGHT + 3) * tRule;

                if ((iDrawingY + mc.fontRenderer.FONT_HEIGHT + 3) <= (pY + iCurrentYExtension - 8))
                {
                    drawString(mc.fontRenderer, iTranslatedInfoText[tRule],iDrawingX, iDrawingY, Colors.Ledgers.BLACK.getColor());
                }
            }
        }

        @Override
        public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
            return false;
        }
    }

    protected enum LedgerDirection
    {
        Left, Right;
    }

}
