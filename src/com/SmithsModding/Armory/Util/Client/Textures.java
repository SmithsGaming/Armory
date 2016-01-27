package com.smithsmodding.armory.util.client;

import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class Textures {

    /**
     * Actual construction method is called from the ForgeEvent system. This method kicks the creation of the textures
     * of and provided a map to put the textures in.
     *
     * @param event The events fired before the TextureSheet is stitched. TextureStitchEvent.Pre instance.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void loadTextures (TextureStitchEvent.Pre event) {
        //Only run the creation once, after all mods have been loaded.
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }

        Gui.FirePit.THERMOMETERICON.addIcon(event.map.registerSprite(new ResourceLocation(Gui.FirePit.THERMOMETERICON.getPrimaryLocation())));
        Blocks.LiquidMetalFlow.addIcon(event.map.registerSprite(new ResourceLocation(Blocks.LiquidMetalFlow.getPrimaryLocation())));
        Blocks.LiquidMetalStill.addIcon(event.map.registerSprite(new ResourceLocation(Blocks.LiquidMetalStill.getPrimaryLocation())));
    }

    public static class MultiArmor {
        public static class Materials {
            public static class Iron {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:items/multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:items/multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:items/multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:items/multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
            }

            public static class Obsidian {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
            }
        }
    }

    public static class Items {
        public static class ItemRing {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Ring", "", Colors.Metals.IRON);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Ring", "", Colors.Metals.OBSIDIAN);
        }

        public static class ItemChain {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Chain", "", Colors.Metals.IRON);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Chain", "", Colors.Metals.OBSIDIAN);
        }

        public static class ItemNugget {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Nugget", "", Colors.Metals.IRON);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Nugget", "", Colors.Metals.OBSIDIAN);
        }

        public static class ItemPlate {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Plate", "", Colors.Metals.IRON);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Plate", "", Colors.Metals.OBSIDIAN);
        }
    }

    public static class Gui {
        private static String GUITEXTUREPATH = "armory:textures/gui/";
        private static String COMPONENTTEXTUREPATH = GUITEXTUREPATH + "Components/";

        public static class Basic {
            private static String BASICTEXTUREPATH = GUITEXTUREPATH + "Basic/";

            public static class Slots {
                public static CustomResource HAMMERSLOT = new CustomResource("Gui.Anvil.Slot.Hammer", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 18, 0, 18, 18);
                public static CustomResource TONGSSLOT = new CustomResource("Gui.Anvil.Slot.Tongs", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 36, 0, 18, 18);
                public static CustomResource BOOKSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 55, 1, 16, 16);
                public static CustomResource UPGRADETOOLSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 73, 1, 16, 16);
                public static CustomResource UPGRADEPAYMENTSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 91, 1, 16, 16);
            }

            public static class Components {
                public static CustomResource PROGRESSHORIZONTALEMPTY = new CustomResource("Gui.Components.ProgressBars.Horizontal.Empty", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 16, 25, 16);
                public static CustomResource PROGRESSHORIZONTALFULL = new CustomResource("Gui.Components.ProgressBars.Horizontal.Full", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 39, 25, 16);
                public static CustomResource PROGRESSDOWNEMPTY = new CustomResource("Gui.Components.ProgressBars.Down.Empty", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 64, 15, 23);
                public static CustomResource PROGRESSDOWNFULL = new CustomResource("Gui.Components.ProgressBars.Down.Empty", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 79, 16, 23);
            }


            public static class Images {
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "Images/";

                public static CustomResource HAMMER = new CustomResource("Gui.Anvil.Image.Hammer", Basic.Images.IMAGETEXTUREPATH + "AnvilHammer.png", Colors.DEFAULT, 0, 0, 30, 30);
            }
        }

        public static class FirePit {
            public static CustomResource THERMOMETERICON = new CustomResource("Gui.FirePit.Thermometer", TextureAddressHelper.getTextureAddress("armory", "Gui-Icons/16x Thermo"), Colors.DEFAULT);
        }

        public static class Anvil {
            public static CustomResource EXPERIENCEORB = new CustomResource("Gui.Anvil.Image.ExperienceOrb", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 0, 16, 16);
        }

        public static class Compatibility {
            public static class NEI {
                public static class ArmorsAnvil {
                    public static CustomResource GUI = new CustomResource("Gui.Compatibility.NEI.Anvil.gui", GUITEXTUREPATH + "NEI/ArmorsAnvil.png");
                }
            }
        }
    }

    public static class Blocks {
        public static final CustomResource LiquidMetalFlow = new CustomResource("Armory.Blocks.LiquidMetal.Flow", "armory:blocks/LiquidMetal/flow");
        public static final CustomResource LiquidMetalStill = new CustomResource("Armory.Blocks.LiquidMetal.Still", "armory:blocks/LiquidMetal/still");
    }

}
