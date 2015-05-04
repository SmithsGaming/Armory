package com.Orion.Armory.Util.Client;
/*
*   Textures
*   Created by: Orion
*   Created on: 27-6-2014
*/

import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.lang.management.BufferPoolMXBean;

public class Textures
{
    public static class MultiArmor
    {
        public static class Materials {
            public static class Iron {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.IRON);
            }

            public static class Chain {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.CHAIN);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.CHAIN);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.CHAIN);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.CHAIN);

            }

            public static class Obsidian {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.OBSIDIAN);
            }

            public static class Bronze {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.BRONZE);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.BRONZE);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.BRONZE);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.BRONZE);
            }

            public static class Alumite {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ALUMITE);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ALUMITE);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ALUMITE);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ALUMITE);
            }

            public static class Ardite {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ARDITE);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ARDITE);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ARDITE);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.ARDITE);
            }

            public static class Cobalt {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.COBALT);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.COBALT);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.COBALT);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.COBALT);
            }

            public static class Manyullun {
                public static CustomResource tHelmetResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:multiarmor/base/armory.Helmet_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.MANYULLUN);
                public static CustomResource tChestplateResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:multiarmor/base/armory.Chestplate_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.MANYULLUN);
                public static CustomResource tLegginsResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:multiarmor/base/armory.Leggins_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.MANYULLUN);
                public static CustomResource tShoesResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:multiarmor/base/armory.Shoes_Base", "armory:textures/models/multiarmor/base/Base.png", Colors.Metals.MANYULLUN);
            }
        }
    }
    
    public static class Items
    {
        public static class ItemRing
        {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Ring", "", Colors.Metals.IRON);
            public static CustomResource ChainResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:basic/16x Ring", "", Colors.Metals.CHAIN);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Ring", "", Colors.Metals.OBSIDIAN);
            public static CustomResource BronzeResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:basic/16x Ring", "", Colors.Metals.BRONZE);
            public static CustomResource AlumiteResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:basic/16x Ring", "", Colors.Metals.ALUMITE);
            public static CustomResource ManyullunResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:basic/16x Ring", "", Colors.Metals.MANYULLUN);
            public static CustomResource CobaltResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:basic/16x Ring", "", Colors.Metals.COBALT);
            public static CustomResource ArditeResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:basic/16x Ring", "", Colors.Metals.ARDITE);
        }

        public static class ItemChain
        {
            public static CustomResource IronResource = new CustomResource(References.InternalNames.Materials.Vanilla.IRON, "armory:basic/16x Chain", "", Colors.Metals.IRON);
            public static CustomResource ChainResource = new CustomResource(References.InternalNames.Materials.Vanilla.CHAIN, "armory:basic/16x Chain", "", Colors.Metals.CHAIN);
            public static CustomResource ObsidianResource = new CustomResource(References.InternalNames.Materials.Vanilla.OBSIDIAN, "armory:basic/16x Chain", "", Colors.Metals.OBSIDIAN);
            public static CustomResource BronzeResource = new CustomResource(References.InternalNames.Materials.Common.BRONZE, "armory:basic/16x Chain", "", Colors.Metals.BRONZE);
            public static CustomResource AlumiteResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "armory:basic/16x Chain", "", Colors.Metals.ALUMITE);
            public static CustomResource ManyullunResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "armory:basic/16x Chain", "", Colors.Metals.MANYULLUN);
            public static CustomResource CobaltResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "armory:basic/16x Chain", "", Colors.Metals.COBALT);
            public static CustomResource ArditeResource = new CustomResource(References.InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "armory:basic/16x Chain", "", Colors.Metals.ARDITE);
        }
    }

    public static class Gui
    {
        private static String GUITEXTUREPATH = "armory:textures/gui/";

        public static class Basic
        {
            private static String BASICTEXTUREPATH = GUITEXTUREPATH + "Basic/";
            public static CustomResource LEDGERLEFT = new CustomResource("Gui.Basic.Ledgers.Left", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT);
            public static CustomResource INFOICON = new CustomResource("Gui.Basic.Ledgers.InfoIon", "armory:Gui-Icons/16x Info icon", Colors.DEFAULT);

            public static class Slots
            {
                public static CustomResource DEFAULT = new CustomResource("Gui.Basic.Slots.Default",BASICTEXTUREPATH + "slot.png", Colors.DEFAULT);
            }

            public static class Border
            {
                private static String BORDERTEXTUREPATH = BASICTEXTUREPATH + "Border/";
                public static CustomResource CENTER = new CustomResource("Gui.Basic.Border.Center", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 4, 4, 248, 248);
                public static CustomResource STRAIGHTBORDERLIGHT = new CustomResource("Gui.Basic.Border.Border.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 0, 250, 3);
                public static CustomResource STRAIGHTBORDERDARK = new CustomResource("Gui.Basic.Border.Border.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 3, 253, 250, 3);
                public static CustomResource INWARTSCORNERLIGHT = new CustomResource("Gui.Basic.Border.Corner.Inwarts.Ligth", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 0, 0, 3, 3);
                public static CustomResource INWARTSCORNERDARK = new CustomResource("Gui.Basic.Border.Corner.Inwarts.Dark", BASICTEXTUREPATH + "Ledger/ledger.png", Colors.DEFAULT, 252, 252, 4, 4);
                public static CustomResource OUTWARTSCORNER = new CustomResource("Gui.Basic.Border.Corner.Outwarts", BORDERTEXTUREPATH + "OutwartsCornerBig.png", Colors.DEFAULT, 0, 0, 3, 3);
            }

            public static class Components
            {
                private static String COMPONENTTEXTUREPATH = GUITEXTUREPATH + "Components/";
                public static CustomResource ARROWEMPTY = new CustomResource("Gui.Basic.Components.Arrow.Empty", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 0,0,22, 16);
                public static CustomResource ARROWFULL = new CustomResource("Gui.Basic.Components.Arrow.Full", COMPONENTTEXTUREPATH + "ProgressBars.png", Colors.DEFAULT, 22,0,22, 16);
            }

            public static class Images
            {
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "Images/";
                public static CustomResource HAMMER = new CustomResource("Gui.Basic.Image.Hammer", IMAGETEXTUREPATH + "AnvilHammer.png", Colors.DEFAULT, 0,0,30, 30);
            }
        }

        public static class FirePit
        {
            public static CustomResource BACKGROUND = new CustomResource("Gui.FirePit.Background", GUITEXTUREPATH + "firepit.png", Colors.DEFAULT);
            public static CustomResource THERMOMETERICON = new CustomResource("Gui.FirePit.Thermometer", TextureAddressHelper.getTextureAddress("Gui-Icons/16x Thermo"), Colors.DEFAULT);
        }

        public static class Heater
        {
            public static CustomResource BACKGROUND = new CustomResource("Gui.Heater.Background", GUITEXTUREPATH + "Heater.png", Colors.DEFAULT);
        }
    }

    public static void registerIcons(IIconRegister pRegistrar)
    {
        Gui.Basic.INFOICON.addIcon(pRegistrar.registerIcon(Gui.Basic.INFOICON.getPrimaryLocation()));
        Gui.FirePit.THERMOMETERICON.addIcon(pRegistrar.registerIcon(Gui.FirePit.THERMOMETERICON.getPrimaryLocation()));
    }
}
