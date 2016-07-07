package com.smithsmodding.armory.util.client;

import com.smithsmodding.armory.api.references.References;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 *
 * Copyrighted according to Project specific license
 */
public class TranslationKeys {

    public static final class CreativeTabs {
        public static final String General = "Armory.Tabs.General";
        public static final String Components = "Armory.Tabs.Components";
        public static final String Armor = "Armory.Tabs.Armor";
        public static final String HeatedItems = "Armory.Tabs.HeatedItem";
    }

    public static final class Messages {
        public static final class Commands {
            public static final String BASEUSAGE = References.InternalNames.Commands.BASECOMMAND + ".usage";
            public static final String GIVEHEATEDUSAGE = References.InternalNames.Commands.GIVEHEATED + ".usage";
            public static final String TEMPDECAYUSAGE = References.InternalNames.Commands.ENABLEDECAY + ".usage";
        }

    }

    public class Items {
        public class HeatedIngot {
            public static final String TemperatureTag = "Armory.Items.HeatedIngot.Tooltip.Temperature";
        }

        public class MultiArmor {
            public class Upgrades {
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

                public class Shoes {
                    public static final String Left = "item.Armory.MultiArmor.Upgrade.Shoes.Left";
                    public static final String Right = "item.Armory.MultiArmor.Upgrade.Shoes.Right";
                }

            }
        }
    }

    public class Materials {
        public class Anvil {
            public static final String Stone = "Armory.Materials.AnvilOnly.Vanilla.Stone";
            public static final String Iron = "Armory.Materials.Vanilla.Iron";
            public static final String Obsidian = "Armory.Materials.Vanilla.Obsidian";
        }

        public class VisibleNames {
            public static final String Iron = "Armory.Materials.Vanilla.Iron";
            public static final String Obsidian = "Armory.Materials.Vanilla.Obsidian";
        }
    }

    public class Fluids {
        public static final String MOLTEN = "Armory.Fluids.Molten";
    }

    public class Gui {

        public static final String InformationTitel = "Armory.GUI.InfoTitel";

        public class Forge {
            public static final String InfoLine1 = "Armory.GUI.Forge.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Forge.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.Forge.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.Forge.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.Forge.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.Forge.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.Forge.Ledger.LastAdded";
        }

        public class Fireplace {
            public static final String InfoLine1 = "Armory.GUI.Fireplace.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Fireplace.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.Fireplace.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.Fireplace.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.Fireplace.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.Fireplace.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.Fireplace.Ledger.LastAdded";
            public static final String CookingMultiplier = "Armory.GUI.Fireplace.Ledger.CookingMultiplier";
        }

        public class Anvil {
            public static final String InfoLine1 = "Armory.GUI.Anvil.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Anvil.Ledger.InfoLine2";
        }

        public class JEI {
            public static final String AnvilRecipeName = "Armory.NEI.Compatibility.ArmorsAnvilRecipe";
        }

        public class Components {
            public static final String PROGRESSFLAMEFUELLEFT = "Armory.GUI.Components.Flame.ToolTip";
            public static final String PROGRESSBARPROGRESS = "Armory.GUI.Components.ProgressBar.ToolTip";
            public static final String FLUIDTANKAMOUNT = "Armory.GUI.Components.FluidTank.ToolTip.Amount";
            public static final String FLUIDTANKCAPACITY = "Armory.GUI.Components.FluidTank.ToolTip.Capacity";
            public static final String FLUIDTANKNOLIQUID = "Armory.GUI.Components.FluidTank.ToolTip.NoLiquid";
        }
    }
}


