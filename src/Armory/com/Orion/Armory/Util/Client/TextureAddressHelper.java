package com.Orion.Armory.Util.Client;
/*
/  TextureAddressHelper
/  Created by : Orion
/  Created on : 27/06/2014
*/

import com.Orion.Armory.Util.References;

public class TextureAddressHelper {
    public static String getTextureAddress(String pAdress) {
        return getTextureAddressWithModID(References.General.MOD_ID, pAdress);
    }

    public static String getTextureAddressWithExtension(String pAdress, String pExtenstion) {
        return getTextureAddressWithModIDAndExtension(References.General.MOD_ID, pAdress, pExtenstion);
    }

    public static String getTextureAddressWithModID(String pModID, String pAdress) {
        return getTextureAddressWithModIDAndExtension(pModID, pAdress, "");
    }

    public static String getTextureAddressWithModIDAndExtension(String pModID, String pAdress, String pExtension) {
        return pModID.toLowerCase() + ":" + pAdress + pExtension;
    }
}
