/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api;
/*
*   References
*   Created by: Orion
*   Created on: 27-6-2014
*/

public class References {
    public static final class GuiIDs {
        public static int FORGEID = 0;
        public static int FIREPLACEID = 1;
        public static int ANVILID = 2;
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
            public static final class Anvil {
                public static final String STONE = "Vanilla.Stone";
                public static final String IRON = "Vanilla.Iron";
                public static final String OBSIDIAN = "Vanilla.Obsidian";
            }

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
            public static final String MEDIEVAL = "Amrory.Tiers.Armor";
            public static final String PLATED = "Armory.Tiers.Plated";
            public static final String QUANTUM = "Armory.Tiers.Quantum";
        }

        public static final class Items {
            public static final String ItemMetalRing = "Armory.Items.Components.MetalRing";
            public static final String ItemMetalChain = "Armory.Items.Components.MetalChain";
            public static final String ItemMetalNugget = "Armory.Items.Components.MetalNugget";
            public static final String ItemMetalPlate = "Armory.Items.Components.MetalPlate";
            public static final String ItemHeatedIngot = "Armory.Items.Components.HeatedIngots";
            public static final String ItemHammer = "Armory.Items.ItemHammer";
            public static final String ItemTongs = "Armory.Items.Tongs";
            public static final String ItemSmithingsGuide = "Armory.Items.SmithingsGuide";
            public static final String ItemArmorComponent = "Armory.Items.ArmorComponent";
        }

        public static final class Blocks {
            public static final String Forge = "Armory.Blocks.Forge";
            public static final String Fireplace = "Armory.Blocks.Fireplace";
            public static final String ArmorsAnvil = "Armory.Blocks.Anvil";
        }

        public static final class TileEntities {
            public static final String ForgeContainer = "Container.Armory.Forge";
            public static final String FireplaceContainer = "Container.Armory.Fireplace";
            public static final String ArmorsAnvil = "Container.Armory.Anvil";

            public static final class Structures {
                public static final String Forge = "Structures.Armory.Forge";
            }
        }

        public static final class HeatedItemTypes {
            public static final String INGOT = "Ingot";
            public static final String RING = "Ring";
            public static final String CHAIN = "Chain";
            public static final String NUGGET = "Nugget";
            public static final String PLATE = "Plate";
            public static final String BLOCK = "Block";
        }

        public static final class GUIComponents {
            public static final String TAB = "Armory.gui.Base.Tab.";

            public static final class FirePit {
                public static final String BACKGROUND = "Gui.Forge.Background";
                public static final String INVENTORY = "Gui.Forge.inventory.Player";
                public static final String SLOT = "Gui.Forge.inventory.slots.";

                public static final String FLAMEONE = "Gui.Forge.inventory.Flame1";
                public static final String FLAMETWO = "Gui.Forge.inventory.Flame2";
                public static final String FLAMETHREE = "Gui.Forge.inventory.Flame3";
                public static final String FLAMEFOUR = "Gui.Forge.inventory.Flame4";
                public static final String FLAMEFIVE = "Gui.Forge.inventory.Flame5";

                public static final String MELT = "Gui.Forge.inventory.Melt";

                public static final String MOLTENMETALSLEFT = "Gui.Forge.MoltenMetals.liquids.left";
                public static final String MOLTENMETALSRIGHT = "Gui.Forge.MoltenMetals.liquids.right";

                public static final String INFUSIONSTACKSBACKGROUND = "Gui.Forge.MoltenMetals.InfusionStacks.Background";

                public static final String PROGRESSMIXINGINLEFTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.In.Left.Horizontal";
                public static final String PROGRESSMIXINGINRIGHTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.In.Right.Horizontal";
                public static final String PROGRESSMIXINGINLEFTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.In.Left.Vertical";
                public static final String PROGRESSMIXINGINRIGHTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.In.Right.Vertical";

                public static final String PROGRESSMIXINGOUTLEFTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.Left.Horizontal";
                public static final String PROGRESSMIXINGOUTRIGHTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.Right.Horizontal";
                public static final String PROGRESSMIXINGOUTLEFTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.Left.Vertical";
                public static final String PROGRESSMIXINGOUTRIGHTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.Right.Vertical";

                public static final String PROGRESSSOLIDIFYING = "Gui.Forge.MoltenMetals.SolidifyingProgress";
            }

            public static final class Fireplace {
                public static final String BACKGROUND = "Gui.Fireplace.Background";
                public static final String INVENTORY = "Gui.Fireplace.inventory.Player";
                public static final String SLOT = "Gui.Fireplace.inventory.slots.";
                public static final String FLAMEONE = "Gui.Fireplace.inventory.Flame1";
                public static final String FLAMETWO = "Gui.Fireplace.inventory.Flame2";
                public static final String FLAMETHREE = "Gui.Fireplace.inventory.Flame3";

                public static final String COOKINGPROGRESS = "Gui.Fireplace.cooking.progress";
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
            }
        }

        public static final class Commands {
            public static final String BASECOMMAND = General.MOD_ID.toLowerCase();
            public static final String GIVEHEATED = "give-heated-item";
            public static final String ENABLEDECAY = "temp-decay";
        }

        public static final class Recipes {
            public static final class Anvil {
                public static final String ANVIL = "Recipes.Anvil.Anvil";

                public static final String FORGE = "Recipes.Anvil.Forge";
                public static final String FIREPLACE = "Recipes.Anvil.Fireplace";

                public static final String TONGS = "Recipes.Anvil.Tongs";
                public static final String HAMMER = "Recipes.Anvil.Hammer";

                public static final String RING = "Recipes.Anvil.Armor.Ring.";
                public static final String CHAIN = "Recipes.Anvil.Armor.Chain.";
                public static final String PLATE = "Recipes.Anvil.Armor.Plate.";
                public static final String NUGGET = "Recipes.Anvil.Armor.Nugget.";

                public static final String HELMET = "Recipes.Anvil.Armor.Helmet.";
                public static final String CHESTPLATE = "Recipes.Anvil.Armor.Chestplate.";
                public static final String LEGGINGS = "Recipes.Anvil.Armor.Leggings.";
                public static final String SHOES = "Recipes.Anvil.Armor.Shoes.";

                public static final String HELMETTOP = "Recipes.Anvil.Armor.Helmet.Protection.Top.";
                public static final String HELMETLEFT = "Recipes.Anvil.Armor.Helmet.Protection.Left.";
                public static final String HELMETRIGHT = "Recipes.Anvil.Armor.Helmet.Protection.Right.";

                public static final String CHESTPLATESHOULDERLEFT = "Recipes.Anvil.Armor.Chestplate.Protection.Shoulder.Left.";
                public static final String CHESTPLATESHOULDERRIGHT = "Recipes.Anvil.Armor.Chestplate.Protection.Shoulder.Right.";
                public static final String CHESTPLATEBACKLEFT = "Recipes.Anvil.Armor.Chestplate.Protection.Back.Left.";
                public static final String CHESTPLATEBACKRIGHT = "Recipes.Anvil.Armor.Chestplate.Protection.Back.Right.";
                public static final String CHESTPLATEFRONTLEFT = "Recipes.Anvil.Armor.Chestplate.Protection.Front.Left.";
                public static final String CHESTPLATEFRONTRIGHT = "Recipes.Anvil.Armor.Chestplate.Protection.Front.Right.";

                public static final String LEGGINGSBACKLEFT = "Recipes.Anvil.Armor.Leggings.Protection.Back.Left.";
                public static final String LEGGINGSBACKRIGHT = "Recipes.Anvil.Armor.Leggings.Protection.Back.Right.";
                public static final String LEGGINGSFRONTLEFT = "Recipes.Anvil.Armor.Leggings.Protection.Front.Left.";
                public static final String LEGGINGSFRONTRIGHT = "Recipes.Anvil.Armor.Leggings.Protection.Front.Right.";

                public static final String SHOESLEFT = "Recipes.Anvil.Armor.Shoes.Protection.Left.";
                public static final String SHOESRIGHT = "Recipes.Anvil.Armor.Shoes.Protection.Right.";

                public static final String HELMETUPGRADETOP = "Recipes.Anvil.Armor.Helmet.Protection.Top.";
                public static final String HELMETUPGRADELEFT = "Recipes.Anvil.Armor.Helmet.Protection.Left.";
                public static final String HELMETUPGRADERIGHT = "Recipes.Anvil.Armor.Helmet.Protection.Right.";

                public static final String CHESTPLATEUPGRADESHOULDERLEFT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Shoulder.Left.";
                public static final String CHESTPLATEUPGRADESHOULDERRIGHT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Shoulder.Right.";
                public static final String CHESTPLATEUPGRADEBACKLEFT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Back.Left.";
                public static final String CHESTPLATEUPGRADEBACKRIGHT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Back.Right.";
                public static final String CHESTPLATEUPGRADEFRONTLEFT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Front.Left.";
                public static final String CHESTPLATEUPGRADEFRONTRIGHT = "Recipes.Anvil.Armor.Chestplate.Upgrade.Front.Right.";

                public static final String LEGGINGSUPGRADEBACKLEFT = "Recipes.Anvil.Armor.Leggings.Upgrade.Back.Left.";
                public static final String LEGGINGSUPGRADEBACKRIGHT = "Recipes.Anvil.Armor.Leggings.Upgrade.Back.Right.";
                public static final String LEGGINGSUPGRADEFRONTLEFT = "Recipes.Anvil.Armor.Leggings.Upgrade.Front.Left.";
                public static final String LEGGINGSUPGRADEFRONTRIGHT = "Recipes.Anvil.Armor.Leggings.Upgrade.Front.Right.";

                public static final String SHOESUPGRADELEFT = "Recipes.Anvil.Armor.Shoes.Upgrade.Left.";
                public static final String SHOESUPGRADERIGHT = "Recipes.Anvil.Armor.Shoes.Upgrade.Right.";
            }
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

        public class Item {
            public class ItemComponent {
                public static final String MATERIAL = "Material";
                public static final String TYPE = "AddonID";
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
            }

            public class ForgeBase {
                public static final String MAXTEMPERATURE = "MaxTemperature";
                public static final String CURRENTTEMPERATURE = "CurrentTemperature";
                public static final String CURRENTLYBURNING = "IsBurning";
                public static final String LASTADDEDHEAT = "LastAddedHeat";
                public static final String LASTTEMPERATURE = "LastTemperature";
                public static final String FUELSTACKBURNINGTIME = "FuelStackBurningTime";
                public static final String FUELSTACKFUELAMOUNT = "FuelStackFuelAmount";
                public static final String LASTPOSITIVEINFLUENCE = "PositiveInfluence";
                public static final String LASTNEGATIVEINFLUENCE = "NegativeInfluence";
            }

            public class Forge {
                public static final String MIXINGPROGRESS = "MixingProgress";
                public static final String MELTINGPROGRESS = "MeltingProgress";

                public class Structure {
                    public static final String DATA = "Data";
                    public static final String PARTS = "Parts";
                    public static final String FLUIDS = "Fluids";
                }
            }

            public class Fireplace {
                public static final String MAXTEMPERATURE = "MaxTemperature";
                public static final String CURRENTTEMPERATURE = "CurrentTemperature";
                public static final String LASTADDEDHEAT = "LastAddedHeat";
                public static final String LASTTEMPERATURE = "LastTemperature";
                public static final String FUELSTACKBURNINGTIME = "FuelStackBurningTime";
                public static final String FUELSTACKFUELAMOUNT = "FuelStackFuelAmount";
                public static final String COOKINGPROGRESS = "CookingProgress";
                public static final String COOKINGSPEED = "CookingSpeed";
            }

            public class Anvil {
                public static final String CRAFTINGPROGRESS = "CraftingProgress";
                public static final String MATERIAL = "Material";
                public static final String ITEMNAME = "ItemName";
                public static final String PROCESSING = "Processing";
            }
        }
    }

    public class Compatibility {
        public class JEI {
            public class RecipeTypes {
                public static final String ANVIL = "Armory.Compat.JEI.RecipesTypes.Anvil";
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
