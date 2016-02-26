package com.smithsmodding.armory.util.client;

import com.smithsmodding.armory.util.*;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 * <p/>
 * Copyrighted according to Project specific license
 */
public class TranslationKeys {

    public static final class CreativeTabs {
        public static final String Blueprint = "Armory.Tabs.Blueprint";
        public static final String Components = "Armory.Tabs.Components";
        public static final String Medieval = "Armory.Tabs.Medieval";
        public static final String MedievalUpgrade = "Armory.Tabs.Medieval.Upgrade";
    }

    public static final class Messages {
        public static final class Commands {
            public static final String BASEUSAGE = References.InternalNames.Commands.BASECOMMAND + ".usage";
            public static final String GIVEHEATEDUSAGE = References.InternalNames.Commands.GIVEHEATED + ".usage";
            public static final String TEMPDECAYUSAGE = References.InternalNames.Commands.ENABLEDECAY + ".usage";
        }

    }

    public static final class Knowledge {
        public static final class AnvilRecipeKnowledge {
            public static final String Tier1 = "Armory.Kownledge.AnvilRecipeKnowledge.Tier1";
            public static final String Tier2 = "Armory.Kownledge.AnvilRecipeKnowledge.Tier2";
            public static final String Tier3 = "Armory.Kownledge.AnvilRecipeKnowledge.Tier3";
            public static final String Tier4 = "Armory.Kownledge.AnvilRecipeKnowledge.Tier4";
            public static final String Tier5 = "Armory.Kownledge.AnvilRecipeKnowledge.Tier4";
            public static final String CreativeTier = "Armory.Kownledge.AnvilRecipeKnowledge.Creative";
        }
    }

    public class Items {
        public final class Label {
            public static final String LabelName = "Armory.Items.Label.GroupName";
            public static final String Logo = "Armory.Items.Label.Logo";
        }

        public final class Blueprint {
            public static final String Quality = "Armory.Items.BluePrint.Quality";
            public static final String Produces = "Armory.Items.BluePrint.Produces";
            public static final String Material1 = "Armory.Items.Blueprint.material.1";
            public static final String Material2 = "Armory.Items.Blueprint.material.2";
            public static final String MedievalUpgrade1 = "Armory.Items.Blueprint.MedievalUpgrade.1";
            public static final String MedievalUpgrade2 = "Armory.Items.Blueprint.MedievalUpgrade.2";

            public static final String Upgrade1 = "Armory.Items.BluePrint.Produces.Upgrade.1";
            public static final String Upgrade2 = "Armory.Items.BluePrint.Produces.Upgrade.2";

            public static final String Tier1 = "Armory.Items.Blueprint.Tier1";
            public static final String Tier2 = "Armory.Items.Blueprint.Tier2";
            public static final String Tier3 = "Armory.Items.Blueprint.Tier3";
            public static final String Tier4 = "Armory.Items.Blueprint.Tier4";
            public static final String Tier5 = "Armory.Items.Blueprint.Tier4";
            public static final String CreativeTier = "Armory.Items.Blueprint.Creative";
        }

        public final class SmithingsGuide {
            public static final String Tooltip1 = "Armory.Items.SmithingsGuide.Tooltip1";
            public static final String Tooltip2 = "Armory.Items.SmithingsGuide.Tooltip2";
        }

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

    public class Blocks {

    }

    public class Fluids {
        public static final String MOLTEN = "Armory.Fluids.Molten";
    }

    public class Gui {

        public static final String InformationTitel = "Armory.GUI.InfoTitel";

        public class FirePit {

            public static final String InfoLine1 = "Armory.GUI.FirePit.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.FirePit.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.FirePit.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.FirePit.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.FirePit.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.FirePit.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.FirePit.Ledger.LastAdded";
        }

        public class Heater {
            public static final String InfoLine1 = "Armory.GUI.Heater.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Heater.Ledger.InfoLine2";
        }

        public class Anvil {
            public static final String InfoLine1 = "Armory.GUI.Anvil.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Anvil.Ledger.InfoLine2";
        }

        public class BookBinder {
            public static final String ToolTipTabBookBinder = "Armory.GUI.BookBinder.Tabs.BookBinder.ToolTip";
            public static final String ToolTipTabResearchStation = "Armory.GUI.BookBinder.Tabs.ResearchStation.ToolTip";

            public class Research {
                public static final String ResearchComplete = "Armory.GUI.Research.Complete";
                public static final String ResearchFailed = "Armory.GUI.Research.Failed";
                public static final String ResearchChangeTargetStackStart = "Armory.GUI.Research.Start.BeforeItemStackName";
                public static final String ResearchChangeTargetStackEnd = "Armory.GUI.Research.Start.AfterItemStackName";
                public static final String ResearchHeatTargetStack = "Armory.GUI.Research.HeatApplied";
                public static final String ResearchHammerTargetStack = "Armory.GUI.Research.Hammer";
                public static final String ResearchTongTargetStack = "Armory.GUI.Research.Tongs";
                public static final String ResearchAnalyseTargetStack = "Armory.GUI.Research.Analyze";
            }
        }

        public class NEI {
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


