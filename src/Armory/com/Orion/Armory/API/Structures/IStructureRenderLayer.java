package com.Orion.Armory.API.Structures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Orion
 * Created on 07.07.2015
 * 10:09
 * <p/>
 * Copyrighted according to Project specific license
 */
@SideOnly(Side.CLIENT)
public interface IStructureRenderLayer
{

    String getStructureType();
}
