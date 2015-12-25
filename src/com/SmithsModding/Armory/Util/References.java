/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Util;
/*
*   References
*   Created by: Orion
*   Created on: 27-6-2014
*/

public class References {
    public static final class GuiIDs {
        public static int FIREPITID = 0;
        public static int HEATERID = 1;
        public static int ANVILID = 2;
        public static int BOOKBINDERID = 3;
        public static int SMITHINGSGUIDE = 50;
        public static int SMITHINGSGUIDEINVENTORY = 51;
    }

    public static final class General {
        public static final String MOD_ID = "Armory";
        public static final String VERSION = "@VERSION@";
        public static final String MC_VERSION = "@MCVERSION@";
        public static final String API_VERSION = "@APIVERSION@";

        public static final int FLUID_INGOT = 144;
    }

    public static final class InternalNames {
        public static final class Armor {
            public static final String MEDIEVALHELMET = "Armory.Helmet.Medieval";
            public static final String MEDIEVALCHESTPLATE = "Armory.Chestplate.Medieval";
            public static final String MEDIEVALLEGGINGS = "Armory.Leggings.Medieval";
            public static final String MEDIEVALSHOES = "Armory.Shoes.Medieval";
        }

        public static final class Materials {
            public static final class Vanilla {
                public static final String IRON = "Vanilla.Iron";
                public static final String OBSIDIAN = "Vanilla.Obsidian";
            }
        }

        public static final class Fluids {
            public static final String MOLTENMETAL = "Armory.Fluids.Molten";
        }

        public static final class AddonPositions {

            public static final class Helmet {
                public static final String BASE = Armor.MEDIEVALHELMET;
                public static final String TOP = "Armory.TopHelmet";
                public static final String LEFT = "Armory.LeftHelmet";
                public static final String RIGHT = "Armory.RightHelmet";
                public static final String AQUABREATHING = "Armory.AquaBreathingHelmet";
                public static final String NIGHTSIGHT = "Armory.NightsightHelmet";
                public static final String THORNS = "Armory.ThornsHelmet";
                public static final String AUTOREPAIR = "Armory.AutoRepairHelmet";
                public static final String REINFORCED = "Armory.ReinforcedHelmet";
                public static final String ELECTRIC = "Armory.ElectricHelmet";
            }

            public static final class Chestplate {
                public static final String BASE = Armor.MEDIEVALCHESTPLATE;
                public static final String SHOULDERLEFT = "Armory.ShoulderLeftChestplate";
                public static final String SHOULDERRIGHT = "Armory.ShoulderRightChestplate";
                public static final String FRONTLEFT = "Armory.FrontLeftChestplate";
                public static final String FRONTRIGHT = "Armory.FrontRightChestplate";
                public static final String BACKLEFT = "Armory.BackLeftChestplate";
                public static final String BACKRIGHT = "Armory.BackrightChestplate";
                public static final String STRENGTH = "Armory.StrengthChestplate";
                public static final String HASTE = "Armory.HasteChestplate";
                public static final String FLYING = "Armory.FlyingChestplate";
                public static final String THORNS = "Armory.ThornsChestplate";
                public static final String AUTOREPAIR = "Armory.AutoRepairChestplate";
                public static final String REINFORCED = "Armory.ReinforcedChestplate";
                public static final String ELECTRIC = "Armory.ElectricChestplate";
            }

            public static final class Leggings {
                public static final String BASE = Armor.MEDIEVALLEGGINGS;
                public static final String FRONTLEFT = "Armory.FrontLeftLeggings";
                public static final String FRONTRIGHT = "Armory.FrontRightLeggings";
                public static final String BACKLEFT = "Armory.BackLeftLeggings";
                public static final String BACKRIGHT = "Armory.BackRightLeggings";
                public static final String SPEED = "Armory.SpeedLeggings";
                public static final String JUMPASSIST = "Armory.JumpAssistLeggings";
                public static final String UPHILLASSIST = "Armory.UpHillAssistLeggings";
                public static final String THORNS = "Armory.ThornsLeggings";
                public static final String AUTOREPAIR = "Armory.AutoRepairLeggings";
                public static final String REINFORCED = "Armory.ReinforcedLeggings";
                public static final String ELECTRIC = "Armory.ElectricLeggings";
            }

            public static final class Shoes {
                public static final String BASE = Armor.MEDIEVALSHOES;
                public static final String LEFT = "Armory.LeftShoes";
                public static final String RIGHT = "Armory.RightShoes";
                public static final String FALLASSIST = "Armory.FallAssistShoes";
                public static final String SWIMASSIST = "Armory.SwimAssistShoes";
                public static final String AUTOREPAIR = "Armory.AutoRepairShoes";
                public static final String REINFORCED = "Armory.ReinforcedShoes";
                public static final String ELECTRIC = "Armory.ElectricShoes";
            }
        }

        public static final class Upgrades {
            public static final class Helmet {
                public static final String TOP = "Armory.TopHelmet";
                public static final String LEFT = "Armory.LeftHelmet";
                public static final String RIGHT = "Armory.RightHelmet";
            }

            public static final class Chestplate {
                public static final String SHOULDERLEFT = "Armory.ShoulderLeftChestplate";
                public static final String SHOULDERRIGHT = "Armory.ShoulderRightChestplate";
                public static final String FRONTLEFT = "Armory.FrontLeftChestplate";
                public static final String FRONTRIGHT = "Armory.FrontRightChestplate";
                public static final String BACKLEFT = "Armory.BackLeftChestplate";
                public static final String BACKRIGHT = "Armory.BackrightChestplate";
            }

            public static final class Leggings {
                public static final String FRONTLEFT = "Armory.FrontLeftLeggings";
                public static final String FRONTRIGHT = "Armory.FrontRightLeggings";
                public static final String BACKLEFT = "Armory.BackLeftLeggings";
                public static final String BACKRIGHT = "Armory.BackRightLeggings";
            }

            public static final class Shoes {
                public static final String LEFT = "Armory.LeftShoes";
                public static final String RIGHT = "Armory.RightShoes";
            }
        }

        public static final class Modifiers {
            public static final class Helmet {
                public static final String AQUABREATHING = "Armory.AquaBreathingHelmet";
                public static final String NIGHTSIGHT = "Armory.NightsightHelmet";
                public static final String THORNS = "Armory.ThornsHelmet";
                public static final String AUTOREPAIR = "Armory.AutoRepairHelmet";
                public static final String REINFORCED = "Armory.ReinforcedHelmet";
                public static final String ELECTRIC = "Armory.ElectricHelmet";
            }

            public static final class Chestplate {
                public static final String STRENGTH = "Armory.StrengthChestplate";
                public static final String HASTE = "Armory.HasteChestplate";
                public static final String FLYING = "Armory.FlyingChestplate";
                public static final String THORNS = "Armory.ThornsChestplate";
                public static final String AUTOREPAIR = "Armory.AutoRepairChestplate";
                public static final String REINFORCED = "Armory.ReinforcedChestplate";
                public static final String ELECTRIC = "Armory.ElectricChestplate";
            }

            public static final class Leggins {
                public static final String SPEED = "Armory.SpeedLegigns";
                public static final String JUMPASSIST = "Armory.JumpAssistLeggins";
                public static final String UPHILLASSIST = "Armory.UpHillAssistLeggins";
                public static final String THORNS = "Armory.ThornsLeggins";
                public static final String AUTOREPAIR = "Armory.AutoRepairLeggins";
                public static final String REINFORCED = "Armory.ReinforcedLeggins";
                public static final String ELECTRIC = "Armory.ElectricLeggins";
            }

            public static final class Shoes {
                public static final String FALLASSIST = "Armory.FallAssistShoes";
                public static final String SWIMASSIST = "Armory.SwimAssistShoes";
                public static final String AUTOREPAIR = "Armory.AutoRepairShoes";
                public static final String REINFORCED = "Armory.ReinforcedShoes";
                public static final String ELECTRIC = "Armory.ElectricShoes";
            }
        }

        public static final class Tiers {
            public static final String MEDIEVAL = "Amrory.Tiers.Medieval";
            public static final String PLATED = "Armory.Tiers.Plated";
            public static final String QUANTUM = "Armory.Tiers.Quantum";
        }

        public static final class Items {
            public static final String ItemMetalRing = "Armory.Items.Components.MetalRing";
            public static final String ItemMetalChain = "Armory.Items.Components.MetalChain";
            public static final String ItemHeatedIngot = "Armory.Items.Components.HeatedIngots";
            public static final String ItemFan = "Armory.Items.HeatedFan";
            public static final String ItemHammer = "Armory.Items.ItemHammer";
            public static final String ItemTongs = "Armory.Items.Tongs";
            public static final String ItemNugget = "Armory.Items.Nugget";
            public static final String ItemPlate = "Armory.Items.Plate";
            public static final String ItemMedievalUpdrade = "Armory.Items.Medieval.Upgrade";
            public static final String ItemSmithingsGuide = "Armory.Items.SmithingsGuide";
            public static final String ItemBlueprint = "Armory.Items.Blueprint";
            public static final String ItemGuideLabel = "Armory.Items.Tab";
        }

        public static final class Blocks {
            public static final String FirePit = "Armory.Blocks.FirePit";
            public static final String Heater = "Armory.Blocks.Heater";
            public static final String ArmorsAnvil = "Armory.Blocks.Anvil";
            public static final String BookBinder = "Armory.Blocks.Binder";
        }

        public static final class TileEntities {
            public static final String FirePitContainer = "container.Armory.FirePit";
            public static final String HeaterComponent = "container.Armory.Heater";
            public static final String ArmorsAnvil = "container.Armory.Anvil";
            public static final String BookBinder = "conainer.Armory.BookBinder";

            public static final class Structures {
                public static final String FirePit = "Structures.Armory.FirePit";
            }
        }

        public static final class HeatedItemTypes {
            public static final String INGOT = "Ingot";
            public static final String RING = "Ring";
            public static final String CHAIN = "Chain";
            public static final String NUGGET = "Nugget";
            public static final String PLATE = "Plate";
        }

        public static final class GUIComponents {
            public static final String TAB = "Armory.Gui.BAse.Tab.";

            public static final class FirePit {
                public static final String BACKGROUND = "Gui.FirePit.Background";
                public static final String INVENTORY = "Gui.FirePit.Inventory.Player";
                public static final String SLOT = "Gui.FirePit.Slots.";
                public static final String FLAMEONE = "Flame1";
                public static final String FLAMETWO = "Flame2";
                public static final String FLAMETHREE = "Flame3";
                public static final String FLAMEFOUR = "Flame4";
                public static final String FLAMEFIVE = "Flame5";
            }

            public static final class Anvil {
                public static final String BACKGROUND = "Gui.Anvil.Background";
                public static final String PLAYERINVENTORY = "Gui.Anvil.Player";
                public static final String EXTENDEDCRAFTING = "Gui.Anvil.ExtendedCrafting";
                public static final String EXPERIENCELABEL = "Gui.Anvil.Label.Experience";
                public static final String TOOLSLOTBORDER = "Gui.Anvil.Tools.Border";
                public static final String HAMMERSLOT = "Gui.Anvil.Tools.Slot.Hammer";
                public static final String TONGSLOT = "Gui.Anvil.Tools.Slot.Tongs";
                public static final String LOGO = "Gui.Anvil.Logo";
                public static final String TEXTBOXBORDER = "Gui,Anvil.Name.Border";
                public static final String TEXTBOX = "Gui.Anvil.Name.Textbox";
                public static final String CRAFTINGPROGRESS = "CraftingProgress";
                public static final String SMITHINGSGUIDEBORDER = "Gui,Anvil.Guide.Border";
                public static final String SMITHINGSGUIDESLOT = "Gui.Anvil.Guide.Slot";
            }

            public static final class BookBinder {
                public static final String TabBookBinder = "Gui.BookBinder.Tab.BookBinder";
                public static final String TabResearchStation = "Gui.BookBinder.Tab.ReasearchStation";

                public static final class TabBook {
                    public static final String BACKGROUND = "Gui.BookBinder.TabBookBinder.Background";

                    public static final String BORDERBOOKSLOT = "Gui.BookBinder.TabBookBinder.Border.BookSlot";
                    public static final String BORDERBLUEPRINTS = "Gui.BookBinder.TabBookBinder.Border.Blueprints";

                    public static final String PROGRESSARROW = "Gui.BookBinder.TabBookBinder.ProgressArrow";

                    public static final String BOOKSLOT = "Gui.BookBinder.TabBookBinder.BookSlot";
                    public static final String BLUEPRINTBACKGROUND = "Gui.BookBinder.TabBookBinder.BluePrintBackground";
                    public static final String BLUEPRINTSLOT = "Gui.BookBinder.TabBookBinder.BlueprintSlot";

                    public static final String PLAYERINVENTORY = "Gui.BookBinder.TabBookBinder.PlayerInventory";
                }

                public static final class TabResearch {
                    public static final String BACKGROUND = "Gui.BookBinder.TabResearchStation.Background";

                    public static final String RESEARCHBACKGROUND = "Gui.BookBinder.TabResearchStation.Borders.Research";

                    public static final String BUTTONAPPLYHEAT = "Gui.BookBinder.TabResearchStation.Button.PerformResearch.ApplyHeat";
                    public static final String BUTTONCUTSTACK = "Gui.BookBinder.TabResearchStation.Button.CutStack";
                    public static final String BUTTONHITSTACK = "Gui.BookBinder.TabResearchStation.Button.HitStack";
                    public static final String BUTTONANALYZESTACK = "Gui.BookBinder.TabResearchStation.Button.AnalyzeStack";

                    public static final String SLOTTARGETSTACK = "Gui.BookBinder.TabResearchStation.Slot.TargetStack";

                    public static final String SLOTPAPER = "Gui.BookBinder.TabResearchStation.Slot.Paper";
                    public static final String SLOTBLUEPRINT = "Gui.BookBinder.TabResearchStation.Slot.Output";
                    public static final String IMAGEARROW = "Gui.BookBinder.TabResearchStation.Image.Arrow";

                    public static final String RESEARCHHISTORYBACKGROUND = "Gui.BookBinder.TabResearchStation.Borders.ResearchHistory";

                    public static final String RESEARCHHISTORY = "Gui.BookBinder.TabReseachStation.Research.History";

                    public static final String PLAYERINVENTORY = "Gui.BookBinder.TabResearch.PlayerInventory";
                }
            }
        }

        public static final class InputHandlers {
            public static final class Components {
                public static final String BUTTONCLICK = "ButtonClick";
                public static final String SCROLL = "Scrolling";
                public static final String SLOTFILLED = "SlotFilled";
                public static final String SLOTEMPTIED = "SlotEmptied";
                public static final String SLOTCHANGED = "SlotChanged";
                public static final String TABCHANGED = "TabChanged";
            }

            public static final class Anvil {
                public static final String ITEMNAME = "Input.Anvil.ItemName";
                public static final String PLAYEROPENGUI = "Input.Anvil.OpenGUI";
                public static final String PLAYERCLOSEGUI = "Inpur.Anvil.CloseGUI";
            }

            public static final class BookBinder {
                public static final String INPUTSWITCH = "Input.Inventory.Research.Stack.TargetSwitch";
                public static final String HEAT = "Input.Research.Heat";
                public static final String HAMMER = "Input.Research.Hammer";
                public static final String TONGS = "Input.Research.Tongs";
                public static final String ANALYZE = "Input.Research.Analyze";
                public static final String COMPLETE = "Input.Research.Complete";
            }


        }

        public static final class ExtendedEntityProperties {
            public static final String KNOWLEDGE = "Armory.Crafting.Knowledge";
            public static final String ANVILRECIPEEXPERIENCE = "Armory.Crafting.Knowledge.AnvilRecipeExperience";

        }

        public static final class Commands {
            public static final String BASECOMMAND = General.MOD_ID.toLowerCase();
            public static final String GIVEHEATED = "give-heated-item";
            public static final String ENABLEDECAY = "temp-decay";
        }

        public static final class Recipes {
            public static final class Anvil {
                public static final String TONGS = "Recipes.Anvil.Tongs";
                public static final String HAMMER = "Recipes.Anvil.Hammer";
                public static final String HEATER = "Recipes.Anvil.Heater";
                public static final String FAN = "Recipes.Anvil.Fan";

                public static final String RING = "Recipes.Anvil.Medieval.Ring.";
                public static final String CHAIN = "Recipes.Anvil.Medieval.Chain.";
                public static final String PLATE = "Recipes.Anvil.Medieval.Plate.";
                public static final String NUGGET = "Recipes.Anvil.Medieval.Nugget.";

                public static final String HELMET = "Recipes.Anvil.Medieval.Helmet.";
                public static final String CHESTPLATE = "Recipes.Anvil.Medieval.Chestplate.";
                public static final String LEGGINGS = "Recipes.Anvil.Medieval.Leggings.";
                public static final String SHOES = "Recipes.Anvil.Medieval.Shoes.";

                public static final String HELMETTOP = "Recipes.Anvil.Medieval.Helmet.Protection.Top.";
                public static final String HELMETLEFT = "Recipes.Anvil.Medieval.Helmet.Protection.Left.";
                public static final String HELMETRIGHT = "Recipes.Anvil.Medieval.Helmet.Protection.Right.";

                public static final String CHESTPLATESHOULDERLEFT = "Recipes.Anvil.Medieval.Chestplate.Protection.Shoulder.Left.";
                public static final String CHESTPLATESHOULDERRIGHT = "Recipes.Anvil.Medieval.Chestplate.Protection.Shoulder.Right.";
                public static final String CHESTPLATEBACKLEFT = "Recipes.Anvil.Medieval.Chestplate.Protection.Back.Left.";
                public static final String CHESTPLATEBACKRIGHT = "Recipes.Anvil.Medieval.Chestplate.Protection.Back.Right.";
                public static final String CHESTPLATEFRONTLEFT = "Recipes.Anvil.Medieval.Chestplate.Protection.Front.Left.";
                public static final String CHESTPLATEFRONTRIGHT = "Recipes.Anvil.Medieval.Chestplate.Protection.Front.Right.";

                public static final String LEGGINGSBACKLEFT = "Recipes.Anvil.Medieval.Leggings.Protection.Back.Left.";
                public static final String LEGGINGSBACKRIGHT = "Recipes.Anvil.Medieval.Leggings.Protection.Back.Right.";
                public static final String LEGGINGSFRONTLEFT = "Recipes.Anvil.Medieval.Leggings.Protection.Front.Left.";
                public static final String LEGGINGSFRONTRIGHT = "Recipes.Anvil.Medieval.Leggings.Protection.Front.Right.";

                public static final String SHOESLEFT = "Recipes.Anvil.Medieval.Shoes.Protection.Left.";
                public static final String SHOESRIGHT = "Recipes.Anvil.Medieval.Shoes.Protection.Right.";

                public static final String HELMETUPGRADETOP = "Recipes.Anvil.Medieval.Helmet.Protection.Top.";
                public static final String HELMETUPGRADELEFT = "Recipes.Anvil.Medieval.Helmet.Protection.Left.";
                public static final String HELMETUPGRADERIGHT = "Recipes.Anvil.Medieval.Helmet.Protection.Right.";

                public static final String CHESTPLATEUPGRADESHOULDERLEFT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Shoulder.Left.";
                public static final String CHESTPLATEUPGRADESHOULDERRIGHT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Shoulder.Right.";
                public static final String CHESTPLATEUPGRADEBACKLEFT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Back.Left.";
                public static final String CHESTPLATEUPGRADEBACKRIGHT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Back.Right.";
                public static final String CHESTPLATEUPGRADEFRONTLEFT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Front.Left.";
                public static final String CHESTPLATEUPGRADEFRONTRIGHT = "Recipes.Anvil.Medieval.Chestplate.Upgrade.Front.Right.";

                public static final String LEGGINGSUPGRADEBACKLEFT = "Recipes.Anvil.Medieval.Leggings.Upgrade.Back.Left.";
                public static final String LEGGINGSUPGRADEBACKRIGHT = "Recipes.Anvil.Medieval.Leggings.Upgrade.Back.Right.";
                public static final String LEGGINGSUPGRADEFRONTLEFT = "Recipes.Anvil.Medieval.Leggings.Upgrade.Front.Left.";
                public static final String LEGGINGSUPGRADEFRONTRIGHT = "Recipes.Anvil.Medieval.Leggings.Upgrade.Front.Right.";

                public static final String SHOESUPGRADELEFT = "Recipes.Anvil.Medieval.Shoes.Upgrade.Left.";
                public static final String SHOESUPGRADERIGHT = "Recipes.Anvil.Medieval.Shoes.Upgrade.Right.";
            }
        }
    }

    public class Models {
        public class ModelLocations {
            protected final static String iModelLocation = "models/";

            public final static String FirePit = iModelLocation + "FirePit.obj";
            public final static String Heater = iModelLocation + "Heater.obj";
            public final static String Anvil = iModelLocation + "ArmorsAnvil.obj";
            public final static String SmithingsGuide = iModelLocation + "Book - Single Sided.obj";
            public final static String BookBinder = iModelLocation + "Binder.obj";
        }
    }

    public class NBTTagCompoundData {
        //Stack addon naming
        public static final String InstalledAddons = "InstalledAddons";
        public static final String ArmorData = "ArmorData";
        public static final String RenderCompound = "RenderCompound";
        public static final String Material = "Material";
        public static final String CustomName = "Name";

        //Versioning used when there is a change in the NBT tag structure.
        public class Versioning {
            public static final int NBTTagVersion = 1;
            public static final String NBTImpVersion = "NBTVersion";
        }

        //Used when storing the addon data.
        public class Addons {
            public static final String AddonID = "AddonID";
            public static final String ParentID = "ParentID";
            public static final String AddonPositionID = "AddonPositionID";
            public static final String AddonInstalledAmount = "AddonInstalledAmount";
            public static final String AddonMaxInstalledAmount = "AddonMaxInstalledAmount";
            public static final String Addon = "InstalledAddon - ";
        }

        //Used when storing data from the armor
        public class Armor {
            public static final String ArmorID = "ArmorID";
            public static final String ArmorTier = "ArmorTier";
            public static final String ArmorPart = "ArmorSlot";
            public static final String MaterialID = "MaterialID";
            public static final String Addons = "InstalledAddons";
            public static final String CurrentDurability = "CurrentDurability";
            public static final String TotalDurability = "TotalDurability";
            public static final String IsBroken = "IsBroken";
        }

        //Used when setting the Rendering compound values.
        public class Rendering {
            public static final String MaxRenderPasses = "RenderPasses";
            public static final String ResourceIDs = "ResourceIDs";
            public static final String OpenState = "ItemOpen";
            public static final String TicksSinceOpen = "SinceOpen";
            public static final String TicksSinceClose = "SinceClose";
        }

        public class KnowledgeCrafting {
            public static final String ExperienceCompound = "CraftingExperience";
        }

        public class Item {
            public class ItemInventory {
                public static final String ID = "UUID";
                public static final String OPEN = "Open";
                public static final String INVENTORY = "Inventory";
                public static final String SLOTID = "SlotIndex";
                public static final String STACK = "StackData";
            }

            public class Blueprints {
                public static final String BLUEPRINTID = "BlueprintID";
                public static final String FLOATVALUE = "Float";
            }

            public class Labels {
                public static final String LOGOSTACK = "LogoStack";
            }

            public class SmithingsGuide {
                public static final String LABELSTACKS = "Labels";
                public static final String GROUPSDATA = "Groups";
            }
        }

        public class HeatedIngot {
            public static final String ORIGINALITEM = "ORIGINALITEM";
            public static final String MATERIALID = "OriginalMaterial";
            public static final String CURRENTTEMPERATURE = "CURRENTTEMPERATURE";
            public static final String TYPE = "Type";
        }

        public class Fluids {
            public class MoltenMetal {
                public static final String MATERIAL = "MaterialID";
            }
        }

        public class TE {
            public class Basic {
                public static final String DIRECTION = "Direction";
                public static final String NAME = "Name";
                public static final String SLOT = "Slot";
                public static final String STRUCTUREDATA = "Structures";
                public static final String INVENTORY = "Inventory";

                public class Structures {
                    public static final String COORDINATES = "Coordinates";
                    public static final String ISSLAVE = "IsSlave";
                    public static final String MASTERTE = "MasterTE";
                    public static final String DATA = "StructureData";
                }

                public class Coordinate {
                    public static final String XCOORD = "XCoord";
                    public static final String YCOORD = "YCoord";
                    public static final String ZCOORD = "ZCoord";
                }
            }

            public class FirePit {
                public static final String MAXTEMPERATURE = "MaxTemperature";
                public static final String CURRENTTEMPERATURE = "CurrentTemperature";
                public static final String CURRENTLYBURNING = "IsBurning";
                public static final String LASTADDEDHEAT = "LastAddedHeat";
                public static final String LASTTEMPERATURE = "LastTemperature";
                public static final String INGOTITEMSTACKS = "Ingots";
                public static final String FUELITEMSTACKS = "FuelStacks";
                public static final String FUELSTACKBURNINGTIME = "FuelStackBurningTime";
                public static final String FUELSTACKFUELAMOUNT = "FuelStackFuelAmount";
                public static final String MELTINGPROGRESS = "MeltingProgress";
            }

            public class Heater {
                public static final String TICKSINSLOT = "STACKTIMEINSLOT";
            }

            public class Anvil {
                public static final String CRAFTINGPROGRESS = "CraftingProgress";
            }

            public class BookBinder {
                public static final String MODE = "BlockMode";
            }
        }
    }

}


//Arrays for testing, these contain all the basic upgrades and modifiers
//public String[] iArmorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
//public String[] iArmorModifiers = {"helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
//        "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
//        "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
//        "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};
