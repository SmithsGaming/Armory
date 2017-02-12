/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.util.references;
/*
*   References
*   Created by: Orion
*   Created on: 27-6-2014
*/

import net.minecraft.util.ResourceLocation;

public class References {
    public static final class GuiIDs {
        public static int FORGEID = 0;
        public static int FIREPLACEID = 1;
        public static int ANVILID = 2;
    }

    public static final class General {
        public static final String MOD_ID = "armory";
        public static final String VERSION = "@VERSION@";
        public static final String MC_VERSION = "@MCVERSION@";
        public static final String API_VERSION = "@APIVERSION@";

        public static final int FLUID_INGOT = 144;
    }

    public static final class OreDictionaryIdentifiers {
        public static final String ODI_INGOT = "ingot";
        public static final String ODI_BLOCK = "block";
        public static final String ODI_RING = "ring";
        public static final String ODI_NUGGET = "nugget";
        public static final String ODI_PLATE = "plate";
        public static final String ODI_CHAIN = "chain";
    }

    public static final class InternalNames {
        public static final class Armor {
            public static final String MEDIEVALHELMET = "armory.helmet.medieval";
            public static final String MEDIEVALCHESTPLATE = "armory.chestplate.medieval";
            public static final String MEDIEVALLEGGINGS = "armory.leggings.medieval";
            public static final String MEDIEVALSHOES = "armory.shoes.medieval";
        }

        public static final class Materials {
            public static final class Anvil {
                public static final ResourceLocation AN_STONE = new ResourceLocation(General.MOD_ID ,"Stone.Anvil");
                public static final ResourceLocation AN_IRON = new ResourceLocation(General.MOD_ID ,"Iron.Anvil");
                public static final ResourceLocation AN_OBSIDIAN = new ResourceLocation(General.MOD_ID ,"Obsidian.Anvil");
            }

            public static final class Core {
                public static final ResourceLocation CMN_IRON = new ResourceLocation(General.MOD_ID , "Iron.Core");
                public static final ResourceLocation CMN_OBSIDIAN = new ResourceLocation(General.MOD_ID , "Obsidian.Core");
                public static final ResourceLocation CMN_GOLD = new ResourceLocation(General.MOD_ID , "Gold.Core");
                public static final ResourceLocation CMN_STEEL = new ResourceLocation(General.MOD_ID , "Steel.Core");
            }

            public static final class Addon {
                public static final ResourceLocation AMN_IRON = new ResourceLocation(General.MOD_ID , "Iron.Addon");
                public static final ResourceLocation AMN_OBSIDIAN = new ResourceLocation(General.MOD_ID , "Obsidian.Addon");
                public static final ResourceLocation AMN_GOLD = new ResourceLocation(General.MOD_ID , "Gold.Addon");
                public static final ResourceLocation AMN_STEEL = new ResourceLocation(General.MOD_ID , "Steel.Addon");
            }
        }

        public static final class Fluids {
            public static final String MOLTENMETAL = "Armory.Fluids.Molten";
        }

        public static final class AddonPositions {

            public static final class Helmet {
                public static final ResourceLocation PN_TOP = new ResourceLocation(General.MOD_ID, "Top");
                public static final ResourceLocation PN_LEFT = new ResourceLocation(General.MOD_ID,  "Left");
                public static final ResourceLocation PN_RIGHT = new ResourceLocation(General.MOD_ID,  "Right");
                public static final ResourceLocation PN_AQUABREATHING = new ResourceLocation(General.MOD_ID,  "AquaBreathing");
                public static final ResourceLocation PN_NIGHTSIGHT = new ResourceLocation(General.MOD_ID,  "Nightsight");
                public static final ResourceLocation PN_THORNS =  new ResourceLocation(General.MOD_ID, "Thorns.Helmet");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "AutoRepair.Helmet");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "Reinforced.Helmet");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "Electric.Helmet");
            }

            public static final class Chestplate {
                public static final ResourceLocation PN_SHOULDERLEFT = new ResourceLocation(General.MOD_ID,  "ShoulderLeft");
                public static final ResourceLocation PN_SHOULDERRIGHT = new ResourceLocation(General.MOD_ID,  "ShoulderRight");
                public static final ResourceLocation PN_STOMACHLEFT = new ResourceLocation(General.MOD_ID,  "StomachLeft");
                public static final ResourceLocation PN_STOMACHRIGHT =  new ResourceLocation(General.MOD_ID, "StomachRight");
                public static final ResourceLocation PN_BACKLEFT = new ResourceLocation(General.MOD_ID,  "BackLeft");
                public static final ResourceLocation PN_BACKRIGHT = new ResourceLocation(General.MOD_ID,  "BackRight");
                public static final ResourceLocation PN_STRENGTH = new ResourceLocation(General.MOD_ID,  "Strength");
                public static final ResourceLocation PN_HASTE = new ResourceLocation(General.MOD_ID,  "Haste");
                public static final ResourceLocation PN_FLYING = new ResourceLocation(General.MOD_ID,  "Flying");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID,  "Thorns.ChestPlate");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "AutoRepair.ChestPlate");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "Reinforced.ChestPlate");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "Electric.ChestPlate");
            }

            public static final class Leggings {
                public static final ResourceLocation PN_SHINLEFT = new ResourceLocation(General.MOD_ID,  "ShinLeft");
                public static final ResourceLocation PN_SHINRIGHT = new ResourceLocation(General.MOD_ID,  "ShinRight");
                public static final ResourceLocation PN_CALFLEFT = new ResourceLocation(General.MOD_ID,  "CalfLeft");
                public static final ResourceLocation PN_CALFRIGHT = new ResourceLocation(General.MOD_ID,  "CalfRight");
                public static final ResourceLocation PN_SPEED = new ResourceLocation(General.MOD_ID,  "Speed");
                public static final ResourceLocation PN_JUMPASSIST = new ResourceLocation(General.MOD_ID,  "JumpAssist");
                public static final ResourceLocation PN_UPHILLASSIST = new ResourceLocation(General.MOD_ID,  "UpHillAssist");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID,  "Thorns.Leggings");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "AutoRepair.Leggings");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "Reinforced.Leggings");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "Electric.Leggings");
            }

            public static final class Shoes {
                public static final ResourceLocation PN_LACESLEFT = new ResourceLocation(General.MOD_ID,  "LacesLeft");
                public static final ResourceLocation PN_LACESRIGHT = new ResourceLocation(General.MOD_ID,  "LacesRight");
                public static final ResourceLocation PN_FALLASSIST = new ResourceLocation(General.MOD_ID,  "FallAssist");
                public static final ResourceLocation PN_SWIMASSIST = new ResourceLocation(General.MOD_ID,  "SwimAssist");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID, "Thorns.Shoes");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "AutoRepair.Shoes");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "Reinforced.Shoes");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "Electric.Shoes");
            }
        }

        public static final class Upgrades {
            public static final class Helmet {
                public static final ResourceLocation UN_TOP =  new ResourceLocation(General.MOD_ID,  "Top");
                public static final ResourceLocation UN_LEFT = new ResourceLocation(General.MOD_ID,  "Left");
                public static final ResourceLocation UN_RIGHT = new ResourceLocation(General.MOD_ID,  "Right");
            }

            public static final class Chestplate {
                public static final ResourceLocation UN_SHOULDERLEFT = new ResourceLocation(General.MOD_ID,  "ShoulderLeft");
                public static final ResourceLocation UN_SHOULDERRIGHT = new ResourceLocation(General.MOD_ID,  "ShoulderRight");
                public static final ResourceLocation UN_STOMACHLEFT = new ResourceLocation(General.MOD_ID,  "StomachLeft");
                public static final ResourceLocation UN_STOMACHRIGHT = new ResourceLocation(General.MOD_ID,  "StomachRight");
                public static final ResourceLocation UN_BACKLEFT = new ResourceLocation(General.MOD_ID,  "BackLeft");
                public static final ResourceLocation UN_BACKRIGHT = new ResourceLocation(General.MOD_ID,  "BackRight");
            }

            public static final class Leggings {
            public static final ResourceLocation UN_SHINLEFT = new ResourceLocation(General.MOD_ID,  "ShinLeft");
            public static final ResourceLocation UN_SHINRIGHT = new ResourceLocation(General.MOD_ID,  "ShinRight");
            public static final ResourceLocation UN_CALFLEFT = new ResourceLocation(General.MOD_ID,  "CalfLeft");
            public static final ResourceLocation UN_CALFRIGHT = new ResourceLocation(General.MOD_ID,  "CalfRight");
        }

            public static final class Shoes {
                public static final ResourceLocation UN_LACESLEFT = new ResourceLocation(General.MOD_ID,  "LacesLeft");
                public static final ResourceLocation UN_LACESRIGHT = new ResourceLocation(General.MOD_ID,  "LacesRight");
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
            public static final String IN_METALRING = "armory.items.components.metalring";
            public static final String IN_METALCHAIN = "armory.items.components.metalchain";
            public static final String IN_METALINGOT = "armory.items.components.metalingot";
            public static final String IN_METALNUGGET = "armory.items.components.metalnugget";
            public static final String IN_METALPLATE = "armory.items.components.metalplate";
            public static final String IN_HEATEDINGOT = "armory.items.components.heatedingots";
            public static final String IN_HAMMER = "armory.items.itemhammer";
            public static final String IN_TONGS = "armory.items.tongs";
            public static final String IN_GUIDE = "armory.items.smithingsguide";
            public static final String IN_ARMOR_COMPONENT = "armory.items.armorcomponent";

            public static final class Armor {
                public static final ResourceLocation IN_HELMET = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.helmet");
                public static final ResourceLocation IN_CHESTPLATE = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.chestplate");
                public static final ResourceLocation IN_LEGGINGS = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.leggings");
                public static final ResourceLocation IN_SHOES = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.shoes");
            }

        }

        public static final class Blocks {
            public static final String Forge = "armory.blocks.forge";
            public static final String Fireplace = "armory.blocks.fireplace";
            public static final String ArmorsAnvil = "armory.blocks.anvil";
            public static final String Conduit = "armory.blocks.conduit";
            public static final String Tank = "armory.blocks.tank";
            public static final String ConduitPump = "armory.blocks.pump";
            public static final String RedstonePumpGenerator = "armory.blocks.redstonegenerator";
            public static final String Resource = "armory.blocks.resource";
        }

        public static final class TileEntities {
            public static final String ForgeContainer = "Container.Armory.Forge";
            public static final String FireplaceContainer = "Container.Armory.Fireplace";
            public static final String ArmorsAnvil = "Container.Armory.Anvil";
            public static final String Conduit = "Container.Armory.Conduit";
            public static final String Tank = "Container.Armory.Tank";
            public static final String Pump = "Container.Armory.Pump";

            public static final class Structures {
                public static final String Forge = "Structures.Armory.Forge";
            }
        }

        public static final class HeatedObjectTypeNames {
            public static final ResourceLocation HTN_INGOT = new ResourceLocation(General.MOD_ID.toLowerCase(), "Ingot");
            public static final ResourceLocation HTN_RING = new ResourceLocation(General.MOD_ID.toLowerCase(), "Ring");
            public static final ResourceLocation HTN_CHAIN = new ResourceLocation(General.MOD_ID.toLowerCase(), "Chain");
            public static final ResourceLocation HTN_NUGGET = new ResourceLocation(General.MOD_ID.toLowerCase(), "Nugget");
            public static final ResourceLocation HTN_PLATE = new ResourceLocation(General.MOD_ID.toLowerCase(), "Plate");
            public static final ResourceLocation HTN_BLOCK = new ResourceLocation(General.MOD_ID.toLowerCase(), "Block");
        }
        
        public static final class HeatableObjectNames {
            public static final ResourceLocation HON_ITEMSTACK = new ResourceLocation(General.MOD_ID, "ItemStack");
            //TODO: Create Others.
        }
        
        public static final class InitializationComponents {
            public static final class Common {
                public static final ResourceLocation COMMAND = new ResourceLocation(General.MOD_ID, "Common.Command");
                public static final ResourceLocation CONFIG = new ResourceLocation(General.MOD_ID, "Common.Config");
                public static final ResourceLocation EVENTHANDLER = new ResourceLocation(General.MOD_ID, "Common.EventHandler");
                public static final ResourceLocation MEDIEVAL = new ResourceLocation(General.MOD_ID, "Common.Medieval");
                public static final ResourceLocation STRUCTURE = new ResourceLocation(General.MOD_ID, "Common.Structure");
                public static final ResourceLocation SYSTEM = new ResourceLocation(General.MOD_ID, "Common.System");
            }

            public static final class Client {
                public static final ResourceLocation MODELLOADER = new ResourceLocation(General.MOD_ID, "Client.ModelLoader");
                public static final ResourceLocation EVENTHANDLER = new ResourceLocation(General.MOD_ID, "Client.EventHandler");
                public static final ResourceLocation STRUCTURE = new ResourceLocation(General.MOD_ID, "Client.Structure");
                public static final ResourceLocation MEDIEVAL = new ResourceLocation(General.MOD_ID, "Client.Medieval");
                public static final ResourceLocation SYSTEM = new ResourceLocation(General.MOD_ID, "Client.System");
            }
        }

        public static final class GUIComponents {
            public static final String TAB = "Armory.gui.Base.Tab.";

            public static final class Forge {
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

                public static final String PROGRESSMIXINGINLEFTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.In.TK_LACESLEFT.Horizontal";
                public static final String PROGRESSMIXINGINRIGHTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.In.TK_LACESRIGHT.Horizontal";
                public static final String PROGRESSMIXINGINLEFTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.In.TK_LACESLEFT.Vertical";
                public static final String PROGRESSMIXINGINRIGHTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.In.TK_LACESRIGHT.Vertical";

                public static final String PROGRESSMIXINGOUTLEFTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.TK_LACESLEFT.Horizontal";
                public static final String PROGRESSMIXINGOUTRIGHTHORIZONTAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.TK_LACESRIGHT.Horizontal";
                public static final String PROGRESSMIXINGOUTLEFTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.TK_LACESLEFT.Vertical";
                public static final String PROGRESSMIXINGOUTRIGHTVERTICAL = "Gui.Forge.MoltenMetals.MixingProgress.Out.TK_LACESRIGHT.Vertical";

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
                public static final ResourceLocation RN_ANVIL = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Anvil");

                public static final ResourceLocation RN_FORGE = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Forge");
                public static final ResourceLocation RN_FIREPLACE = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Fireplace");

                public static final ResourceLocation RN_TONGS = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Tongs");
                public static final ResourceLocation RN_HAMMER = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Hammer");

                public static final ResourceLocation RN_RING = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Ring");
                public static final ResourceLocation RN_CHAIN = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Chain");
                public static final ResourceLocation RN_PLATE = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Plate");
                public static final ResourceLocation RN_NUGGET = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Nugget");

                public static final ResourceLocation RN_HELMET = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet");
                public static final ResourceLocation RN_CHESTPLATE = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate");
                public static final ResourceLocation RN_LEGGINGS = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings");
                public static final ResourceLocation RN_SHOES = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Shoes");

                public static final ResourceLocation RN_HELMETTOP = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Protection.Top");
                public static final ResourceLocation RN_HELMETLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Protection.Left");
                public static final ResourceLocation RN_HELMETRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Protection.Right");

                public static final ResourceLocation RN_CHESTPLATESHOULDERLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Shoulder.Left");
                public static final ResourceLocation RN_CHESTPLATESHOULDERRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Shoulder.Right");
                public static final ResourceLocation RN_CHESTPLATEBACKLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Back.Left");
                public static final ResourceLocation RN_CHESTPLATEBACKRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Back.Right");
                public static final ResourceLocation RN_CHESTPLATESTOMACHLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Stomach.Left");
                public static final ResourceLocation RN_CHESTPLATESTOMACHRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Protection.Stomach.Right");

                public static final ResourceLocation RN_LEGGINGSCALFLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Protection.Calf.Left");
                public static final ResourceLocation RN_LEGGINGSCALFRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Protection.Calf.Right");
                public static final ResourceLocation RN_LEGGINGSSHINLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Protection.Shin.Left");
                public static final ResourceLocation RN_LEGGINGSSHINRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Protection.Shin.Right");

                public static final ResourceLocation RN_SHOESLACESLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Shoes.Protection.Laces.Left");
                public static final ResourceLocation RN_SHOESLACESRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Shoes.Protection.Laces.Right");

                public static final ResourceLocation RN_HELMETUPGRADETOP = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Upgrade.Top");
                public static final ResourceLocation RN_HELMETUPGRADELEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Upgrade.Left");
                public static final ResourceLocation RN_HELMETUPGRADERIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Helmet.Upgrade.Right");

                public static final ResourceLocation RN_CHESTPLATEUPGRADESHOULDERLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Shoulder.Left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESHOULDERRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Shoulder.Right");
                public static final ResourceLocation RN_CHESTPLATEUPGRADEBACKLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Back.Left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADEBACKRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Back.Right");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESTOMACHLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Stomach.Left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESTOMACHRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.ChestPlate.Upgrade.Stomach.Right");

                public static final ResourceLocation RN_LEGGINGSUPGRADECALFLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Upgrade.Calf.Left");
                public static final ResourceLocation RN_LEGGINGSUPGRADECALFRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Upgrade.Calf.Right");
                public static final ResourceLocation RN_LEGGINGSUPGRADESHINLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Upgrade.Shin.Left");
                public static final ResourceLocation RN_LEGGINGSUPGRADESHINRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Leggings.Upgrade.Shin.Right");

                public static final ResourceLocation RN_SHOESUPGRADELACESLEFT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Shoes.Upgrade.Laces.Left");
                public static final ResourceLocation RN_SHOESUPGRADELACESRIGHT = new ResourceLocation(General.MOD_ID, "Recipes.Anvil.Armor.Shoes.Upgrade.Laces.Right");
            }
        }

        public static class TextureCreation {
            public static final ResourceLocation TCN_CORE = new ResourceLocation(General.MOD_ID, "Core");
            public static final ResourceLocation TCN_ADDON = new ResourceLocation(General.MOD_ID, "Addon");
            public static final ResourceLocation TCN_ANVIL = new ResourceLocation(General.MOD_ID, "Anvil");

        }
    }

    public class NBTTagCompoundData {
        //Stack addon naming
        public static final String InstalledAddons = "InstalledAddons";
        public static final String ArmorData = "ArmorData";
        public static final String RenderCompound = "RenderCompound";
        public static final String CoreMaterial = "CoreMaterial";
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
            public static final String AddonMaterial = "AddonMaterial";
        }

        //Used when storing data from the armor
        public class Armor {
            public static final String NAME = "ArmorName";
            public static final String CORE_MATERIAL = "Material";
            public static final String ADDONS = "Addons";
            public static final String CURRENT_DURABILITY = "CurrentDurability";
            public static final String TOTAL_DURABILITY = "TotalDurability";
            public static final String IS_BROKEN = "IsBroken";
            public static final String CAPABILITY_DATA = "CapabilityData";
        }

        public class Item {
            public class ItemComponent {
                public static final String MATERIAL = "CoreMaterial";
                public static final String EXTENSION = "Extension";
            }
        }

        public class HeatedObject {
            public static final String HEATEDTYPE = "Type";
            public static final String HEATEDOBJECT = "Object";
            public static final String HEATEDTEMP = "Temperature";
            public static final String HEATEDSTACK = "Stack";
        }

        public class MaterializedStack {
            public static final String MATERIAL = "Material";
        }

        public class Fluids {
            public class MoltenMetal {
                public static final String MATERIAL = "CORE_MATERIAL";
            }

            public class MoltenMetalTank {
                public static final String MAXLIQUIDCOUNT = "MAX";
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
                public static final String MATERIAL = "CoreMaterial";
                public static final String ITEMNAME = "ItemName";
                public static final String PROCESSING = "Processing";
            }

            public class Conduit {
                public static final String CONTENTS = "Contents";

                public class Structure {
                    public static final String DATA = "Data";
                    public static final String PARTS = "Parts";
                    public static final String FLUIDS = "Fluids";
                    public static final String FLUIDSIZE = "Size";
                    public static final String TYPE = "Type";
                    public static final String OUTPUTCONNECTIONS = "Outputs";
                    public static final String INPUTCONNECTIONS = "Inputs";

                    public class Connections {
                        public static final String COORDINATE = "Coordinate";
                        public static final String SIDES = "Sides";
                    }
                }
            }

            public class Pump {
                public static final String FLUIDS = "Fluids";
                public static final String DELAY = "Delay";
            }

            public class MoltenMetalTank {
                public static final String TYPE = "Type";
                public static final String CONTENTS = "Contents";
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
