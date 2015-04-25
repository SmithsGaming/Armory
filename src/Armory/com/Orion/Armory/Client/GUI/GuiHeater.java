package com.Orion.Armory.Client.GUI;

import com.Orion.Armory.Client.Logic.CoreIconProvider;
import com.Orion.Armory.Common.Inventory.ContainerFirepit;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.StringUtils;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

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

        this.iBackGroundTexture = new ResourceLocation(Textures.Gui.HEATER);

        this.iLedgers.addLedgerLeft(new ArmoryBaseGui.InfoLedger(TranslationKeys.GUI.InformationTitel, new String[]{TranslationKeys.GUI.Heater.InfoLine1, "", TranslationKeys.GUI.Heater.InfoLine2}, CoreIconProvider.getInstance().getIcon(0)));
    }
}
