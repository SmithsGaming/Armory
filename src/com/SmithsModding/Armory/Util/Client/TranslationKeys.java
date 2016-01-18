package com.smithsmodding.armory.util.Client;

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
        public static final String Blueprint = "armory.Tabs.Blueprint";
        public static final String Components = "armory.Tabs.Components";
        public static final String Medieval = "armory.Tabs.Medieval";
        public static final String MedievalUpgrade = "armory.Tabs.Medieval.Upgrade";
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
            public static final String Tier1 = "armory.knowledge.AnvilRecipeKnowledge.Tier1";
            public static final String Tier2 = "armory.knowledge.AnvilRecipeKnowledge.Tier2";
            public static final String Tier3 = "armory.knowledge.AnvilRecipeKnowledge.Tier3";
            public static final String Tier4 = "armory.knowledge.AnvilRecipeKnowledge.Tier4";
            public static final String Tier5 = "armory.knowledge.AnvilRecipeKnowledge.Tier4";
            public static final String CreativeTier = "armory.knowledge.AnvilRecipeKnowledge.Creative";
        }
    }

    public class Items {
        public final class Label {
            public static final String LabelName = "armory.Items.Label.GroupName";
            public static final String Logo = "armory.Items.Label.Logo";
        }

        public final class Blueprint {
            public static final String Quality = "armory.Items.BluePrint.Quality";
            public static final String Produces = "armory.Items.BluePrint.Produces";
            public static final String Material1 = "armory.Items.Blueprint.material.1";
            public static final String Material2 = "armory.Items.Blueprint.material.2";
            public static final String MedievalUpgrade1 = "armory.Items.Blueprint.MedievalUpgrade.1";
            public static final String MedievalUpgrade2 = "armory.Items.Blueprint.MedievalUpgrade.2";

            public static final String Upgrade1 = "armory.Items.BluePrint.Produces.Upgrade.1";
            public static final String Upgrade2 = "armory.Items.BluePrint.Produces.Upgrade.2";

            public static final String Tier1 = "armory.Items.Blueprint.Tier1";
            public static final String Tier2 = "armory.Items.Blueprint.Tier2";
            public static final String Tier3 = "armory.Items.Blueprint.Tier3";
            public static final String Tier4 = "armory.Items.Blueprint.Tier4";
            public static final String Tier5 = "armory.Items.Blueprint.Tier4";
            public static final String CreativeTier = "armory.Items.Blueprint.Creative";
        }

        public final class SmithingsGuide {
            public static final String Tooltip1 = "armory.Items.SmithingsGuide.Tooltip1";
            public static final String Tooltip2 = "armory.Items.SmithingsGuide.Tooltip2";
        }

        public class HeatedIngot {
            public static final String TemperatureTag = "armory.Items.HeatedIngot.Tooltip.Temperature";
        }

        public class MultiArmor {
            public class Upgrades {
                public class Helmet {
                    public static final String TopHead = "item.armory.MultiArmor.Upgrade.Helmet.Top";
                    public static final String RightEar = "item.armory.MultiArmor.Upgrade.Helmet.RightEar";
                    public static final String LeftEar = "item.armory.MultiArmor.Upgrade.Helmet.LeftEar";
                }

                public class Chestplate {
                    public static final String ShoulderLeft = "item.armory.MultiArmor.Upgrade.ChestPlate.ShoulderLeft";
                    public static final String ShoulderRight = "item.armory.MultiArmor.Upgrade.ChestPlate.ShoulderRight";
                    public static final String FrontLeft = "item.armory.MultiArmor.Upgrade.ChestPlate.FrontLeft";
                    public static final String FrontRight = "item.armory.MultiArmor.Upgrade.ChestPlate.FrontRight";
                    public static final String BackLeft = "item.armory.MultiArmor.Upgrade.ChestPlate.BackLeft";
                    public static final String BackRight = "item.armory.MultiArmor.Upgrade.ChestPlate.BackRight";

                }

                public class Leggings {
                    public static final String FrontLeft = "item.armory.MultiArmor.Upgrade.Leggings.FrontLeft";
                    public static final String FrontRight = "item.armory.MultiArmor.Upgrade.Leggings.FrontRight";
                    public static final String BackLeft = "item.armory.MultiArmor.Upgrade.Leggings.BackLeft";
                    public static final String BackRight = "item.armory.MultiArmor.Upgrade.Leggings.BackRight";
                }

                public class Shoes {
                    public static final String Left = "item.armory.MultiArmor.Upgrade.Shoes.Left";
                    public static final String Right = "item.armory.MultiArmor.Upgrade.Shoes.Right";
                }

            }
        }
    }

    public class Materials {

        public class VisibleNames {
            public static final String Iron = "armory.materials.Vanilla.Iron";
            public static final String Steel = "armory.materials.Vanilla.Chain";
            public static final String Obsidian = "armory.materials.Vanilla.Obsidian";

            public static final String Alumite = "armory.materials.TConstruct.Alumite";
            public static final String Ardite = "armory.materials.TConstruct.Ardite";
            public static final String Cobalt = "armory.materials.TConstruct.Cobalt";
            public static final String Manyullun = "armory.materials.TConstruct.Manyullun";

            public static final String Bronze = "armory.materials.common.Bronze";
        }
    }

    public class Blocks {

    }

    public class Fluids {
        public static final String MOLTEN = "armory.Fluids.Molten";
    }

    public class GUI {

        public static final String InformationTitel = "armory.GUI.InfoTitel";

        public class FirePit {

            public static final String InfoLine1 = "armory.GUI.FirePit.Ledger.InfoLine1";
            public static final String InfoLine2 = "armory.GUI.FirePit.Ledger.InfoLine2";
            public static final String InfoLine3 = "armory.GUI.FirePit.Ledger.InfoLine3";
            public static final String TempTitel = "armory.GUI.FirePit.Ledger.TempTitel";
            public static final String TempMax = "armory.GUI.FirePit.Ledger.TempMax";
            public static final String TempCurrent = "armory.GUI.FirePit.Ledger.TempCurrent";
            public static final String LastAdded = "armory.GUI.FirePit.Ledger.LastAdded";
        }

        public class Heater {
            public static final String InfoLine1 = "armory.GUI.Heater.Ledger.InfoLine1";
            public static final String InfoLine2 = "armory.GUI.Heater.Ledger.InfoLine2";
        }

        public class Anvil {
            public static final String InfoLine1 = "armory.GUI.Anvil.Ledger.InfoLine1";
            public static final String InfoLine2 = "armory.GUI.Anvil.Ledger.InfoLine2";
        }

        public class BookBinder {
            public static final String ToolTipTabBookBinder = "armory.gui.BookBinder.Tabs.BookBinder.ToolTip";
            public static final String ToolTipTabResearchStation = "armory.gui.BookBinder.Tabs.ResearchStation.ToolTip";

            public class Research {
                public static final String ResearchComplete = "armory.gui.Research.Complete";
                public static final String ResearchFailed = "armory.gui.Research.Failed";
                public static final String ResearchChangeTargetStackStart = "armory.gui.Research.Start.BeforeItemStackName";
                public static final String ResearchChangeTargetStackEnd = "armory.gui.Research.Start.AfterItemStackName";
                public static final String ResearchHeatTargetStack = "armory.gui.Research.HeatApplied";
                public static final String ResearchHammerTargetStack = "armory.gui.Research.Hammer";
                public static final String ResearchTongTargetStack = "armory.gui.Research.Tongs";
                public static final String ResearchAnalyseTargetStack = "armory.gui.Research.Analyze";
            }
        }

        public class NEI {
            public static final String AnvilRecipeName = "armory.NEI.Compatibility.ArmorsAnvilRecipe";
        }

        public class Components {
            public static final String PROGRESSFLAMEFUELLEFT = "armory.GUI.Components.Flame.ToolTip";
            public static final String PROGRESSBARPROGRESS = "armory.GUI.Components.ProgressBar.ToolTip";
            public static final String FLUIDTANKAMOUNT = "armory.gui.Components.FluidTank.ToolTip.Amount";
            public static final String FLUIDTANKCAPACITY = "armory.gui.Components.FluidTank.ToolTip.Capacity";
            public static final String FLUIDTANKNOLIQUID = "armory.gui.Components.FluidTank.ToolTip.NoLiquid";
        }
    }
}


