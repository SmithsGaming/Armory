package com.Orion.Armory.Client.Util;
/*
*   TextureHelper
*   Created by: Orion
*   Created on: 27-6-2014
*/

import com.Orion.Armory.Util.References;

public class TextureHelper
{
    public static String getTextureAddress(String pAddress)
    {
        return getTextureAddressWithModID(References.General.MOD_ID, pAddress);
    }

    public static String getTextureAddressWithExtension(String pAddress, String pExtension)
    {
        return getTextureAddressWithModIDAndExtension(References.General.MOD_ID, pAddress, pExtension);
    }

    public static String getTextureAddressWithModID(String pModID, String pAddress)
    {
        return getTextureAddressWithModIDAndExtension(pModID, pAddress, "");
    }

    public static String getTextureAddressWithModIDAndExtension(String pModID, String pAddress, String pExtension)
    {
        return pModID.toLowerCase() + ":" + pAddress + pExtension;
    }
}
