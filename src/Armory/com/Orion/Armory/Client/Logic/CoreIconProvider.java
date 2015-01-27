package com.Orion.Armory.Client.Logic;
/*
 *   CoreIconProvider
 *   Created by: Orion
 *   Created on: 24-1-2015
 */

import com.Orion.Armory.Util.Client.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class CoreIconProvider
{
    public static final int INFO = 0;
    public static final int THERMOMETER = 1;
    private static final int MAX = 2;

    private IIcon[] iIcons;

    private static CoreIconProvider INSTANCE;

    public static CoreIconProvider getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new CoreIconProvider();
        }

        return INSTANCE;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int pIconID)
    {
        return iIcons[pIconID];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister pRegistrar)
    {
        iIcons = new IIcon[MAX];

        iIcons[INFO] = pRegistrar.registerIcon(Textures.Gui.Basic.INFOICON);
        iIcons[THERMOMETER] = pRegistrar.registerIcon(Textures.Gui.THERMOMETER);
    }
}
