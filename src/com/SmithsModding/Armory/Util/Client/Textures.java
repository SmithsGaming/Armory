package com.smithsmodding.armory.util.Client;

import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.gui.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class Textures {

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
        public static CustomResource Blueprint = new CustomResource(References.InternalNames.Items.ItemBlueprint, "armory:basic/32x Blueprint", "", Colors.DEFAULT);
        public static CustomResource TabSmithingsGuide = new CustomResource(References.InternalNames.Items.ItemGuideLabel, "armory:basic/32x Label", "", Colors.DEFAULT);

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
            public static CustomResource INFOICON = new CustomResource("gui.Basic.Ledgers.InfoIon", "armory:gui-Icons/16x Info icon", Colors.DEFAULT);
            private static String BASICTEXTUREPATH = GUITEXTUREPATH + "Basic/";
            public static CustomResource LEDGERLEFT = new CustomResource("gui.Basic.Ledgers.Left", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT);

            public static class Slots {
                public static CustomResource DEFAULT = new CustomResource("gui.Basic.slots.Default", BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 0, 0, 18, 18);
            }

            public static class Border {
                public static CustomResource CENTER = new CustomResource("gui.Basic.Border.Center", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 4, 4, 248, 248);
                public static CustomResource STRAIGHTBORDERLIGHT = new CustomResource("gui.Basic.Border.Border.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 0, 250, 3);
                public static CustomResource STRAIGHTBORDERDARK = new CustomResource("gui.Basic.Border.Border.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 253, 250, 3);
                public static CustomResource INWARTSCORNERLIGHT = new CustomResource("gui.Basic.Border.Corner.Inwarts.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 0, 0, 3, 3);
                public static CustomResource INWARTSCORNERDARK = new CustomResource("gui.Basic.Border.Corner.Inwarts.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 252, 252, 4, 4);
                private static String BORDERTEXTUREPATH = BASICTEXTUREPATH + "Border/";
                public static CustomResource OUTWARTSCORNER = new CustomResource("gui.Basic.Border.Corner.Outwarts", BORDERTEXTUREPATH + "OutwartsCornerBig.png", Colors.DEFAULT, 0, 0, 3, 3);
            }

            public static class Components {
                public static CustomResource ARROWEMPTY = new CustomResource("gui.Basic.Components.Arrow.Empty", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 0, 0, 22, 16);
                public static CustomResource ARROWFULL = new CustomResource("gui.Basic.Components.Arrow.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 22, 0, 22, 16);
                public static CustomResource FLAMEEMPTY = new CustomResource("gui.Basic.Components.Flame.Empty", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 44, 0, 16, 16);
                public static CustomResource FLAMEFULL = new CustomResource("gui.Basic.Components.Flame.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 60, 0, 16, 16);

                public static class Button {
                    public static final CustomResource DOWNARROW = new CustomResource("gui.Basic.Components.Button.DownArrow", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 0, 0, 5, 7);
                    public static final CustomResource UPARROW = new CustomResource("gui.Basic.Components.Button.UpArrow", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 5, 0, 5, 7);
                    public static final CustomResource SCROLLBAR = new CustomResource("gui.Basic.Components.Button.ScrollButton", COMPONENTTEXTUREPATH + "Buttons.png", Colors.DEFAULT, 10, 0, 5, 7);
                    protected static String WIDGETFILEPATH = "textures/gui/widgets.png";

                    public static class Disabled {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("gui.Basic.Components.Button.Disabled.Corners.LeftTop", WIDGETFILEPATH, 1, 47, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("gui.Basic.Components.Button.Disabled.Corners.RightTop", WIDGETFILEPATH, 198, 47, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("gui.Basic.Components.Button.Disabled.Corners.LeftBottom", WIDGETFILEPATH, 1, 63, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("gui.Basic.Components.Button.Disabled.Corners.RightBottom", WIDGETFILEPATH, 198, 63, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("gui.Basic.Components.Button.Disabled.Side.Top", WIDGETFILEPATH, 1, 47, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("gui.Basic.Components.Button.Disabled.Side.Left", WIDGETFILEPATH, 1, 48, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("gui.Basic.Components.Button.Disabled.Side.Right", WIDGETFILEPATH, 198, 48, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("gui.Basic.Components.Button.Disabled.Side.Bottom", WIDGETFILEPATH, 1, 63, 196, 2);

                        public static CustomResource CENTER = new CustomResource("gui.Basic.Components.Button.Disabled.Center", WIDGETFILEPATH, 2, 48, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }

                    public static class Standard {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("gui.Basic.Components.Button.Standard.Corners.LeftTop", WIDGETFILEPATH, 1, 67, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("gui.Basic.Components.Button.Standard.Corners.RightTop", WIDGETFILEPATH, 198, 67, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("gui.Basic.Components.Button.Standard.Corners.LeftBottom", WIDGETFILEPATH, 1, 83, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("gui.Basic.Components.Button.Standard.Corners.RightBottom", WIDGETFILEPATH, 198, 83, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("gui.Basic.Components.Button.Standard.Side.Top", WIDGETFILEPATH, 1, 67, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("gui.Basic.Components.Button.Standard.Side.Left", WIDGETFILEPATH, 1, 68, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("gui.Basic.Components.Button.Standard.Side.Right", WIDGETFILEPATH, 198, 68, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("gui.Basic.Components.Button.Standard.Side.Bottom", WIDGETFILEPATH, 1, 83, 196, 2);

                        public static CustomResource CENTER = new CustomResource("gui.Basic.Components.Button.Standard.Center", WIDGETFILEPATH, 2, 68, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }

                    public static class Clicked {
                        public static CustomResource CORNERLEFTTOP = new CustomResource("gui.Basic.Components.Button.Clicked.Corners.LeftTop", WIDGETFILEPATH, 1, 87, 1, 1);
                        public static CustomResource CORNERRIGHTTOP = new CustomResource("gui.Basic.Components.Button.Clicked.Corners.RightTop", WIDGETFILEPATH, 198, 87, 1, 1);
                        public static CustomResource CORNERLEFTBOTTOM = new CustomResource("gui.Basic.Components.Button.Clicked.Corners.LeftBottom", WIDGETFILEPATH, 1, 103, 1, 2);
                        public static CustomResource CORNERRIGHTBOTTOM = new CustomResource("gui.Basic.Components.Button.Clicked.Corners.RightBottom", WIDGETFILEPATH, 198, 103, 1, 2);

                        public static CustomResource SIDETOP = new CustomResource("gui.Basic.Components.Button.Clicked.Side.Top", WIDGETFILEPATH, 1, 87, 196, 1);
                        public static CustomResource SIDELEFT = new CustomResource("gui.Basic.Components.Button.Clicked.Side.Left", WIDGETFILEPATH, 1, 88, 1, 15);
                        public static CustomResource SIDERIGHT = new CustomResource("gui.Basic.Components.Button.Clicked.Side.Right", WIDGETFILEPATH, 198, 88, 1, 15);
                        public static CustomResource SIDEBOTTOM = new CustomResource("gui.Basic.Components.Button.Clicked.Side.Bottom", WIDGETFILEPATH, 1, 103, 196, 2);

                        public static CustomResource CENTER = new CustomResource("gui.Basic.Components.Button.Clicked.Center", WIDGETFILEPATH, 2, 88, 196, 15);

                        public static MultiComponentTexture TEXTURE = new MultiComponentTexture(new TextureComponent(CENTER), new TextureComponent[]{new TextureComponent(CORNERLEFTTOP), new TextureComponent(CORNERRIGHTTOP), new TextureComponent(CORNERRIGHTBOTTOM), new TextureComponent(CORNERLEFTBOTTOM)}, new TextureComponent[]{new TextureComponent(SIDETOP), new TextureComponent(SIDERIGHT), new TextureComponent(SIDEBOTTOM), new TextureComponent(SIDELEFT)});
                    }
                }
            }


            public static class Images {
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "Images/";
                public static CustomResource ARROWRIGHTGRAY = new CustomResource("gui.Basic.Iamge.Arrow.Gray", IMAGETEXTUREPATH + "ArrowImage.png", Colors.DEFAULT, 0, 0, 22, 22);
                public static CustomResource ARROWRIGHTWHITE = new CustomResource("gui.Basic.Iamge.Arrow.White", IMAGETEXTUREPATH + "ArrowImage.png", Colors.DEFAULT, 22, 0, 22, 23);
            }
        }

        public static class FirePit {
            public static CustomResource THERMOMETERICON = new CustomResource("gui.FirePit.Thermometer", TextureAddressHelper.getTextureAddress("armory", "gui-Icons/16x Thermo"), Colors.DEFAULT);
        }

        public static class Anvil {
            public static CustomResource HAMMER = new CustomResource("gui.Anvil.Image.Hammer", Basic.Images.IMAGETEXTUREPATH + "AnvilHammer.png", Colors.DEFAULT, 0, 0, 30, 30);
            public static CustomResource HAMMERSLOT = new CustomResource("gui.Anvil.Slot.Hammer", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 18, 0, 18, 18);
            public static CustomResource TONGSSLOT = new CustomResource("gui.Anvil.Slot.Tongs", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 36, 0, 18, 18);
            public static CustomResource BOOKSLOT = new CustomResource("gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 55, 1, 16, 16);
            public static CustomResource UPGRADETOOLSLOT = new CustomResource("gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 73, 1, 16, 16);
            public static CustomResource UPGRADEPAYMENTSLOT = new CustomResource("gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 91, 1, 16, 16);
            public static CustomResource EXPERIENCEORB = new CustomResource("gui.Anvil.Image.ExperienceOrb", GUITEXTUREPATH + "Components/RandomElements.png", Colors.DEFAULT, 16, 0, 16, 16);

        }

        public static class BookBinder {
            public static class TabBookBinding {
                public static CustomResource BINDINGPROGRESSBACKGROUND = new CustomResource("gui.BookBinder.TabBookbinding.BindingProgress.Background", GUITEXTUREPATH + "Components/" + "ProgressBars.png", Colors.DEFAULT, 0, 16, 32, 32);
                public static CustomResource BINDINGPROGRESSFOREGROUND = new CustomResource("gui.BookBinder.TabBookbinding.BindingProgress.Foreground", GUITEXTUREPATH + "Components/" + "ProgressBars.png", Colors.DEFAULT, 32, 16, 32, 32);
                public static CustomResource BLUEPRINTSLOT = new CustomResource("gui.BookBinder.TabBookbinding.Slot.Blueprint", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 109, 1, 16, 16);
            }

            public static class TabResearchStation {
                public static CustomResource MAGNIFIER = new CustomResource("gui.BookBinder.TabResearchStation.Magnifier", GUITEXTUREPATH + "ResearchStation/" + "ResearchButtons.png", Colors.DEFAULT, 0, 0, 16, 16);
                public static CustomResource FLAMEFULL = new CustomResource("gui.BookBinder.TabResearchStation.Flame.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 60, 0, 16, 16);
                public static CustomResource TONGS = new CustomResource("gui.BookBinder.TabResearchStation.Tongs", GUITEXTUREPATH + "ResearchStation/" + "ResearchButtons.png", Colors.DEFAULT, 16, 0, 16, 16);
                public static CustomResource HAMMER = new CustomResource("gui.BookBinder.TabResearchStation.Hammer", GUITEXTUREPATH + "ResearchStation/" + "ResearchButtons.png", Colors.DEFAULT, 32, 0, 16, 16);

                public static CustomResource BLUEPRINTSLOT = new CustomResource("gui.BookBinder.TabResearchStation.Slot.Blueprint", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 109, 1, 16, 16);
                public static CustomResource BOOKSLOT = new CustomResource("gui.BookBinder.TabResearchStation.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 55, 1, 16, 16);
            }
        }

        public static class Compatibility {
            public static class NEI {
                public static class ArmorsAnvil {
                    public static CustomResource GUI = new CustomResource("gui.Compatibility.NEI.Anvil.gui", GUITEXTUREPATH + "NEI/ArmorsAnvil.png");
                }
            }
        }
    }

}
