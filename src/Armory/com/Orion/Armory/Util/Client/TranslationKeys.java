package com.Orion.Armory.Util.Client;

import com.Orion.Armory.Util.References;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
public class TranslationKeys
{

    public class Items
    {

        public class HeatedIngot
        {
            public static final String TemperatureTag = "Armory.Items.HeatedIngot.Tooltip.Temperature";
        }

        public class MultiArmor
        {
            public class Upgrades
            {
                public class Helmet {
                    public static final String TopHead = "item.Armory.MultiArmor.Upgrade.Helmet.Top";
                    public static final String RightEar = "item.Armory.MultiArmor.Upgrade.Helmet.RightEar";
                    public static final String LeftEar = "item.Armory.MultiArmor.Upgrade.Helmet.LeftEar";
                }

                public class Chestplate {
                    public static final String ShoulderLeft = "item.Armory.MultiArmor.Upgrade.ChestPlate.ShoulderLeft";
                    public static final String ShoulderRight = "item.Armory.MultiArmor.Upgrade.ChestPlate.ShoulderRight";
                    public static final String FrontLeft = "item.Armory.MultiArmor.Upgrade.ChestPlate.FrontLeft";
                    public static final String FrontRight = "item.Armory.MultiArmor.Upgrade.ChestPlate.FrontRight";
                    public static final String BackLeft = "item.Armory.MultiArmor.Upgrade.ChestPlate.BackLeft";
                    public static final String BackRight = "item.Armory.MultiArmor.Upgrade.ChestPlate.BackRight";

                }

                public class Leggings {
                    public static final String FrontLeft = "item.Armory.MultiArmor.Upgrade.Leggings.FrontLeft";
                    public static final String FrontRight = "item.Armory.MultiArmor.Upgrade.Leggings.FrontRight";
                    public static final String BackLeft = "item.Armory.MultiArmor.Upgrade.Leggings.BackLeft";
                    public static final String BackRight = "item.Armory.MultiArmor.Upgrade.Leggings.BackRight";
                }

                public class Shoes
                {
                    public static final String Left = "item.Armory.MultiArmor.Upgrade.Shoes.Left";
                    public static final String Right = "item.Armory.MultiArmor.Upgrade.Shoes.Right";
                }

            }
        }
    }

    public class Materials
    {

        public class VisibleNames
        {
            public static final String Iron = "Armory.Materials.Vanilla.Iron";
            public static final String Steel = "Armory.Materials.Vanilla.Chain";
            public static final String Obsidian = "Armory.Materials.Vanilla.Obsidian";

            public static final String Alumite = "Armory.Materials.TConstruct.Alumite";
            public static final String Ardite = "Armory.Materials.TConstruct.Ardite";
            public static final String Cobalt = "Armory.Materials.TConstruct.Cobalt";
            public static final String Manyullun = "Armory.Materials.TConstruct.Manyullun";

            public static final String Bronze = "Armory.Materials.Common.Bronze";
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

            public static final String InfoLine1 = "Armory.GUI.FirePit.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.FirePit.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.FirePit.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.FirePit.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.FirePit.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.FirePit.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.FirePit.Ledger.LastAdded";
        }

        public class Heater
        {
            public static final String InfoLine1 = "Armory.GUI.Heater.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Heater.Ledger.InfoLine2";
        }

        public class Anvil
        {
            public static final String InfoLine1 = "Armory.GUI.Anvil.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Anvil.Ledger.InfoLine2";
        }

        public class NEI
        {
            public static final String AnvilRecipeName = "Armory.NEI.Compatibility.ArmorsAnvilRecipe";
        }
    }

    public static final class Messages
    {
        public static final class Commands
        {
            public static final String BASEUSAGE = References.InternalNames.Commands.BASECOMMAND + ".usage";
            public static final String GIVEHEATEDUSAGE = References.InternalNames.Commands.GIVEHEATED + ".usage";
        }

    }
}

