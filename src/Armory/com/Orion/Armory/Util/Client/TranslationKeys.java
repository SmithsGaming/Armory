package com.Orion.Armory.Util.Client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
@SideOnly(Side.CLIENT)
public class TranslationKeys
{

    public class Items
    {

        public class HeatedIngot
        {
            public static final String TemperatureTag = "Armory.Items.HeatedIngot.Tooltip.Temperature";
        }
    }

    public class Blocks
    {

    }

    public class GUI
    {

        public static final String InformationTitel = "Armory.GUI.InfoTitel";

        public class FirePit
        {

            public static final String InfoLine1 = "Armory.GUI.FirePit.LEdger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.FirePit.LEdger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.FirePit.LEdger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.FirePit.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.FirePit.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.FirePit.Ledger.TempCurrent";
            public static final String LastAdded = "Armor.GUI.FirePit.Ledger.LastAdded";
        }
    }
}

