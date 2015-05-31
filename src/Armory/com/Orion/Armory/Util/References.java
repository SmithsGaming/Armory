package com.Orion.Armory.Util;
/*
*   References
*   Created by: Orion
*   Created on: 27-6-2014
*/

import cpw.mods.fml.client.registry.RenderingRegistry;

import java.security.PublicKey;

public class References
{
    public class General
    {
        public static final String MOD_ID = "Armory";
        public static final String VERSION = "@VERSION@";
        public static final String MC_VERSION = "@MCVERSION";
    }
    
    public class InternalNames
    {
        public class Armor
        {
            public static final String MEDIEVALHELMET = "Armory.Helmet.Medieval";
            public static final String MEDIEVALCHESTPLATE = "Armory.Chestplate.Medieval";
            public static final String MEDIEVALLEGGINGS = "Armory.Leggins.Medieval";
            public static final String MEDIEVALSHOES = "Armory.Shoes.Medieval";
        }

        public class Materials
        {
            public class Vanilla
            {
                public static final String IRON = "Vanilla.Iron";
                public static final String CHAIN = "Vanilla.Chain";
                public static final String OBSIDIAN = "Vanilla.Obsidian";
            }

            public class Common
            {
                public static final String BRONZE = "Common.Bronze";
            }

            public class ModMaterials
            {
                public class TinkersConstruct
                {
                    public static final String ALUMITE = "TConstruct.Alumite";
                    public static final String ARDITE = "TConstruct.Ardite";
                    public static final String COBALT = "TConstruct.Cobalt";
                    public static final String MANYULLUN = "TConstruct.Manyullun";
                }
            }
        }

        public class AddonPositions
        {
            public class Helmet
            {
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
            
            public class Chestplate
            {
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
            
            public class Leggings
            {
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
            
            public class Shoes
            {
                public static final String LEFT = "Armory.LeftShoes";
                public static final String RIGHT = "Armory.RightShoes";
                public static final String FALLASSIST = "Armory.FallAssistShoes";
                public static final String SWIMASSIST = "Armory.SwimAssistShoes";
                public static final String AUTOREPAIR = "Armory.AutoRepairShoes";
                public static final String REINFORCED = "Armory.ReinforcedShoes";
                public static final String ELECTRIC = "Armory.ElectricShoes";
            }
        }

        public class Upgrades
        {
            public class Helmet
            {
                public static final String TOP = "Armory.TopHelmet";
                public static final String LEFT = "Armory.LeftHelmet";
                public static final String RIGHT = "Armory.RightHelmet";
            }

            public class Chestplate
            {
                public static final String SHOULDERLEFT = "Armory.ShoulderLeftChestplate";
                public static final String SHOULDERRIGHT = "Armory.ShoulderRightChestplate";
                public static final String FRONTLEFT = "Armory.FrontLeftChestplate";
                public static final String FRONTRIGHT = "Armory.FrontRightChestplate";
                public static final String BACKLEFT = "Armory.BackLeftChestplate";
                public static final String BACKRIGHT = "Armory.BackrightChestplate";
            }

            public class Leggings
            {
                public static final String FRONTLEFT = "Armory.FrontLeftLeggings";
                public static final String FRONTRIGHT = "Armory.FrontRightLeggings";
                public static final String BACKLEFT = "Armory.BackLeftLeggings";
                public static final String BACKRIGHT = "Armory.BackRightLeggings";
            }

            public class Shoes
            {
                public static final String LEFT = "Armory.LeftShoes";
                public static final String RIGHT = "Armory.RightShoes";
            }
        }

        public class Modifiers
        {
            public class Helmet
            {
                public static final String AQUABREATHING = "Armory.AquaBreathingHelmet";
                public static final String NIGHTSIGHT = "Armory.NightsightHelmet";
                public static final String THORNS = "Armory.ThornsHelmet";
                public static final String AUTOREPAIR = "Armory.AutoRepairHelmet";
                public static final String REINFORCED = "Armory.ReinforcedHelmet";
                public static final String ELECTRIC = "Armory.ElectricHelmet";
            }

            public class Chestplate
            {
                public static final String STRENGTH = "Armory.StrengthChestplate";
                public static final String HASTE = "Armory.HasteChestplate";
                public static final String FLYING = "Armory.FlyingChestplate";
                public static final String THORNS = "Armory.ThornsChestplate";
                public static final String AUTOREPAIR = "Armory.AutoRepairChestplate";
                public static final String REINFORCED = "Armory.ReinforcedChestplate";
                public static final String ELECTRIC = "Armory.ElectricChestplate";
            }

            public class Leggins
            {
                public static final String SPEED = "Armory.SpeedLegigns";
                public static final String JUMPASSIST = "Armory.JumpAssistLeggins";
                public static final String UPHILLASSIST = "Armory.UpHillAssistLeggins";
                public static final String THORNS = "Armory.ThornsLeggins";
                public static final String AUTOREPAIR = "Armory.AutoRepairLeggins";
                public static final String REINFORCED = "Armory.ReinforcedLeggins";
                public static final String ELECTRIC = "Armory.ElectricLeggins";
            }

            public class Shoes
            {
                public static final String FALLASSIST = "Armory.FallAssistShoes";
                public static final String SWIMASSIST = "Armory.SwimAssistShoes";
                public static final String AUTOREPAIR = "Armory.AutoRepairShoes";
                public static final String REINFORCED = "Armory.ReinforcedShoes";
                public static final String ELECTRIC = "Armory.ElectricShoes";
            }
        }

        public class Tiers
        {
            public static final String MEDIEVAL = "Amrory.Tiers.Medieval";
            public static final String PLATED = "Armory.Tiers.Plated";
            public static final String QUANTUM = "Armory.Tiers.Quantum";
        }

        public class Items
        {
            public static final String ItemMetalRing = "Armory.Items.Components.MetalRing";
            public static final String ItemMetalChain = "Armory.Items.Components.MetalChain";
            public static final String ItemHeatedIngot = "Armory.Items.Components.HeatedIngots";
            public static final String ItemFan = "Armory.Items.HeatedFan";
            public static final String ItemHammer = "Armory.Items.ItemHammer";
            public static final String ItemTongs = "Armory.Items.Tongs";
            public static final String ItemNugget = "Armory.Items.Nugget";
            public static final String ItemPlate = "Armory.Items.Plate";
            public static final String ItemMedievalUpdrade = "Armory.Items.Medieval.Upgrade";
        }

        public class Blocks
        {
            public static final String FirePit = "Armory.Blocks.FirePit";
            public static final String Heater = "Armory.Blocks.Heater";
            public static final String ArmorsAnvil = "Armory.Blocks.Anvil";
        }

        public class TileEntities
        {
            public static final String FirePitContainer = "container.Armory.FirePit";
            public static final String HeaterComponent = "container.Armory.Heater";
            public static final String ArmorsAnvil = "container.Armory.Anvil";
        }

        public class HeatedItemTypes
        {
            public static final String INGOT = "Ingot";
            public static final String RING = "Ring";
            public static final String CHAIN = "Chain";
            public static final String NUGGET = "Nugget";
            public static final String PLATE = "Plate";
        }

        public class GUIComponents
    {
        public class FirePit
        {
            public static final String FLAMEONE = "Flame1";
            public static final String FLAMETWO = "Flame2";
            public static final String FLAMETHREE = "Flame3";
            public static final String FLAMEFOUR = "Flame4";
            public static final String FLAMEFIVE = "Flame5";
        }

        public class Anvil
        {
            public static final String CRAFTINGPROGRESS = "CraftingProgress";
        }
    }
    }

    //General model addresses
    public class Models
    {
        public class ModelLocations
        {
            protected final static String iModelLocation = "models/";

            public final static String FirePit = iModelLocation + "FirePit.obj";
            public final static String Heater = iModelLocation + "Heater.obj";
            public final static String Anvil = iModelLocation + "ArmorsAnvil.obj";
        }
    }

    //NBTTag compound version
    public class NBTTagCompoundData {
        //Stack addon naming
        public static final String InstalledAddons = "InstalledAddons";
        public static final String ArmorData = "ArmorData";
        public static final String RenderCompound = "RenderCompound";
        public static final String Material = "Material";

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
        }

        //Used when setting the Rendering compound values.
        public class Rendering {
            public static final String MaxRenderPasses = "RenderPasses";
            public static final String ResourceIDs = "ResourceIDs";
        }

        public class HeatedIngot
        {
            public static final String ORIGINALITEM = "ORIGINALITEM";
            public static final String MATERIALID = "OriginalMaterial";
            public static final String CURRENTTEMPERATURE = "CURRENTTEMPERATURE";
            public static final String TYPE = "Type";
        }

        public class TE
        {
            public class Basic
            {
                public static final String DIRECTION = "Direction";
                public static final String NAME = "Name";
                public static final String SLOT = "Slot";
            }

            public class FirePit
            {
                public static final String MAXTEMPERATURE = "MaxTemperature";
                public static final String CURRENTTEMPERATURE = "CurrentTemperature";
                public static final String CURRENTLYBURNING = "IsBurning";
                public static final String LASTADDEDHEAT = "LastAddedHeat";
                public static final String INGOTITEMSTACKS = "Ingots";
                public static final String FUELITEMSTACKS = "FuelStacks";
                public static final String FUELSTACKBURNINGTIME = "FuelStackBurningTime";
                public static final String FUELSTACKFUELAMOUNT = "FuelStackFuelAmount";
            }

            public class Heater
            {
                public static final String FANSTACK = "FANSTACK";
            }

            public class Anvil
            {
                public static final String CRAFTINGSTACKS = "Craftingstacks";
                public static final String OUTPUTSTACKS = "Outputstacks";
                public static final String HAMMERSTACKS = "Hammerstacks";
                public static final String TONGSTACKS = "Tongstacks";
                public static final String ADDITIONALSTACKS = "Additionalstacks";
                public static final String COOLSTACKS = "Coolingstacks";
                public static final String CRAFTINGPROGRESS = "CraftingProgress";
            }
        }
    }

    public static class RenderIDs
    {
        public static final int FirePitID = RenderingRegistry.getNextAvailableRenderId();
        public static final int HeaterID = RenderingRegistry.getNextAvailableRenderId();
        public static final int AnvilID = RenderingRegistry.getNextAvailableRenderId();
    }

    public static class GuiIDs
    {
        public static int FIREPITID = 0;
        public static int HEATERID = 1;
        public static int ANVILID = 2;
    }

}



//Arrays for testing, these contain all the basic upgrades and modifiers
//public String[] iArmorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
//public String[] iArmorModifiers = {"helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
//        "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
//        "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
//        "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};
