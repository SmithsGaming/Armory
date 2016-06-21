package com.smithsmodding.armory.util.client;

import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.client.textures.HolographicTexture;
import com.smithsmodding.smithscore.client.textures.TextureCreator;
import com.smithsmodding.smithscore.util.client.CustomResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
    public void registerTexturesToMap (TextureStitchEvent.Pre event) {
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWPICKAXE.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWBOOK.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWHAMMER.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWTONGS.getPrimaryLocation()));

        //Only run the creation once, after all mods have been loaded.
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }

        Gui.Anvil.LOGO.addIcon(event.getMap().registerSprite(new ResourceLocation(Gui.Anvil.LOGO.getPrimaryLocation())));
        Gui.FirePit.THERMOMETERICON.addIcon(event.getMap().registerSprite(new ResourceLocation(Gui.FirePit.THERMOMETERICON.getPrimaryLocation())));
        Blocks.LiquidMetalFlow.addIcon(event.getMap().registerSprite(new ResourceLocation(Blocks.LiquidMetalFlow.getPrimaryLocation())));
        Blocks.LiquidMetalStill.addIcon(event.getMap().registerSprite(new ResourceLocation(Blocks.LiquidMetalStill.getPrimaryLocation())));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void loadTexturesAfterCreation (TextureStitchEvent.Post event) {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }

        //Only run the creation once, after all mods have been loaded.
        Gui.Anvil.HOLOWPICKAXE.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWPICKAXE.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWBOOK.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWBOOK.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWHAMMER.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWHAMMER.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWTONGS.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWTONGS.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
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
                private static final String RANDOMTEXTUREFILE = GUITEXTUREPATH + "Components/RandomElements.png";

                public static final CustomResource HORIZONTALTAILLEFTTORIGHTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.LTR.Empty", RANDOMTEXTUREFILE, 32, 3, 50, 4);
                public static final CustomResource HORIZONTALTAILLEFTTORIGHTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.LTR.Full", RANDOMTEXTUREFILE, 32, 0, 50, 3);

                public static final CustomResource HORIZONTALTAILRIGHTTOLEFTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.RTL.Empty", RANDOMTEXTUREFILE, 32, 10, 50, 4);
                public static final CustomResource HORIZONTALTAILRIGHTTOLEFTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.RTL.Full", RANDOMTEXTUREFILE, 32, 7, 50, 3);

                public static final CustomResource HORIZONTALHEADLEFTTORIGHTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.LTR.Empty", RANDOMTEXTUREFILE, 82, 16, 50, 16);
                public static final CustomResource HORIZONTALHEADLEFTTORIGHTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.LTR.Full", RANDOMTEXTUREFILE, 132, 16, 50, 16);

                public static final CustomResource HORIZONTALHEADRIGHTTOLEFTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.RTL.Empty", RANDOMTEXTUREFILE, 82, 0, 50, 16);
                public static final CustomResource HORIZONTALHEADRIGHTTOLEFTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.RTL.Full", RANDOMTEXTUREFILE, 132, 0, 50, 16);

                public static final CustomResource VERTICALHEADTOPTOBOTTOMLEFTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.Left.Head.TTB.Empty", RANDOMTEXTUREFILE, 182, 0, 16, 26);
                public static final CustomResource VERTICALHEADTOPTOBOTTOMLEFTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.Left.Head.TTB.Full", RANDOMTEXTUREFILE, 198, 0, 16, 26);

                public static final CustomResource VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.Right.Head.TTB.Empty", RANDOMTEXTUREFILE, 214, 0, 16, 26);
                public static final CustomResource VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.Right.Head.TTB.Full", RANDOMTEXTUREFILE, 230, 0, 16, 26);

                public static final CustomResource VERTICALTAILTOPTOBOTTOMLEFTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.Left.Tail.TTB.Empty", RANDOMTEXTUREFILE, 0, 38, 4, 20);
                public static final CustomResource VERTICALTAILTOPTOBOTTOMLEFTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.Left.Tail.TTB.Full", RANDOMTEXTUREFILE, 4, 38, 4, 20);

                public static final CustomResource VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.Right.Tail.TTB.Empty", RANDOMTEXTUREFILE, 8, 38, 4, 20);
                public static final CustomResource VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.Right.Tail.TTB.Full", RANDOMTEXTUREFILE, 12, 38, 4, 20);
            }


            public static class Images {
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "Images/";

                public static CustomResource HAMMER = new CustomResource("Gui.Anvil.Image.Hammer", Basic.Images.IMAGETEXTUREPATH + "AnvilHammer", Colors.DEFAULT, 0, 0, 30, 30);
            }
        }

        public static class FirePit {
            public static CustomResource THERMOMETERICON = new CustomResource("Gui.FirePit.Thermometer", "armory:gui/Images/16x ThermoALT", Colors.DEFAULT, 0, 0, 16, 16);
        }

        public static class Anvil {
            public static CustomResource LOGO = new CustomResource("Gui.Anvil.Image.Logo", "armory:gui/Images/AnvilHammer", Colors.DEFAULT, 0, 0, 15, 15);
            public static CustomResource EXPERIENCEORB = new CustomResource("Gui.Anvil.Image.ExperienceOrb", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 0, 16, 16);
            public static CustomResource HOLOWPICKAXE = new CustomResource("Gui.Anvil.SlotHolo.Pickaxe", "minecraft:items/iron_pickaxe", Colors.DEFAULT, 0, 0, 16, 16);
            public static CustomResource HOLOWBOOK = new CustomResource("Gui.Anvil.SlotHolo.Book", "minecraft:items/book_normal", Colors.DEFAULT, 0,0, 16, 16);
            public static CustomResource HOLOWHAMMER = new CustomResource("Gui.Anvil.SlotHolo.Hammer", "armory:items/basic/16x Work Hammer", Colors.DEFAULT, 0,0,16,16);
            public static CustomResource HOLOWTONGS = new CustomResource("Gui.Anvil.SlotHolo.Tongs", "armory:items/basic/16x Tongs", Colors.DEFAULT, 0,0,16,16);
        }

        public static class Compatibility {
            public static class JEI {
                public static class ArmorsAnvil {
                    public static CustomResource GUI = new CustomResource("Gui.Compatibility.NEI.Anvil.gui", GUITEXTUREPATH + "JEI/anvil.png", 0, 0, 162, 133);
                }
            }
        }
    }

    public static class Blocks {
        public static final CustomResource LiquidMetalFlow = new CustomResource("Armory.General.LiquidMetal.Flow", "armory:blocks/LiquidMetal/flow");
        public static final CustomResource LiquidMetalStill = new CustomResource("Armory.General.LiquidMetal.Still", "armory:blocks/LiquidMetal/still");
    }

}
