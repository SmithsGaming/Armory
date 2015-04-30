package com.Orion.Armory.Client.GUI.Components.Ledgers;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.SessionVars;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 25.04.2015
 * 12:48
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class Ledger extends AbstractGUIComponent
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

    public ResourceLocation TEXTURELEFT = new ResourceLocation(Textures.Gui.Basic.LEDGERLEFT.getPrimaryLocation());

    public Ledger(ArmoryBaseGui pGui, String pInternalName)
    {
        super(pGui,pInternalName, 0, 0, 0, 0);
    }

    @Override
    public void onUpdate()
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

        iWidth = iCurrentXExtension;
        iHeight = iCurrentYExtension;
    }

    public int getOriginOffSet()
    {
        if(iDirection == LedgerDirection.Left)
        {
            return iCurrentXExtension * -1;
        }

        return 4;
    }

    @Override
    public int getHeight()
    {
        return iCurrentYExtension;
    }

    @Override
    public int getWidth() {return iCurrentXExtension;}

    /*
     * parameter pX: always directly to the border of the Gui
     * parameter pY: always directly under the last rendered Ledger
     */
    @Override
    public void drawBackGround(int pX, int pY)
    {

        GL11.glColor3f(iBackgroundColor.getColorRedFloat(), iBackgroundColor.getColorGreenFloat(), iBackgroundColor.getColorBlueFloat());

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURELEFT);

        if (iDirection == LedgerDirection.Left)
        {
            drawTexturedModalRect(pX + getOriginOffSet(), pY, 0, 0, iCurrentXExtension, 4);
            drawTexturedModalRect(pX + getOriginOffSet(), pY + 3, 0, 3, iCurrentXExtension, iCurrentYExtension - 7);
            drawTexturedModalRect(pX + getOriginOffSet(), pY + iCurrentYExtension - 4, 0, 252, iCurrentXExtension, 4);
            drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 256 - iCurrentYExtension, 4, iCurrentYExtension);
            drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 0, 4, 4);

            iLeft = pX + getOriginOffSet();
            iTop = pY;
        }
        else
        {
            drawTexturedModalRect(pX, pY + 4, 4,4, iCurrentXExtension + 4, iCurrentYExtension - 8);
            drawTexturedModalRect(pX-4, pY, 0,0, iCurrentXExtension + 8, 4);
            drawTexturedModalRect(pX-4, pY + iCurrentYExtension - 4, 0, 252, iCurrentXExtension + 8, 4);
            drawTexturedModalRect(pX-4, pY + 4, 4,4, 4, iCurrentYExtension-8);
            drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY, 252, 0, 4, iCurrentYExtension);
            drawTexturedModalRect(pX + getOriginOffSet() + iCurrentXExtension, pY +iCurrentYExtension - 4, 252, 252, 4, 4);

            iLeft = pX;
            iTop = pY;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawHeaderIcon(int pX, int pY)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        drawTexturedModelRectFromIcon(pX + getOriginOffSet() + 4, pY + 4, iHeaderIcon, 16, 16);
    }

    public void drawHeaderText(int pX, int pY, FontRenderer pFont)
    {
        drawCenteredString(pFont, iHeader, pX + getOriginOffSet() + 24 + (pFont.getStringWidth(iHeader) / 2), pY + 4, iHeaderTextColor.getColor());
    }

    @Override
    public void draw(int pX, int pY)
    {
        drawBackGround(pX, pY);
        drawHeaderIcon(pX, pY);

        iLastXOrigin = pX + getOriginOffSet();
        iLastYOrigin = pY;

        if (iClosed)
        {
            return;
        }

        iGui.calcScaleFactor();

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((pX + getOriginOffSet() - 4) * iGui.iGuiScale, ((iGui.iDisplayHeight - pY - iCurrentYExtension) * iGui.iGuiScale), (iCurrentXExtension) * iGui.iGuiScale, (iCurrentYExtension) * iGui.iGuiScale);

        //Debug code for the scissor box position
        //mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        //drawTexturedModalRect(0, 0, 0, 0, iDisplayWidth, iDisplayHeight);

        drawHeaderText(pX, pY, Minecraft.getMinecraft().fontRenderer);
        drawForeGround(pX, pY);

        GL11.glPopAttrib();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public abstract void drawForeGround(int pX, int pY);

    public void drawToolTips(int pMouseX, int pMouseY)
    {

    }

    public void setFullyOpen()
    {
        iOpen = true;
        iCurrentXExtension = iMaxWidthOpen;
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

    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);


    protected enum LedgerDirection
    {
        Left, Right
    }
}

