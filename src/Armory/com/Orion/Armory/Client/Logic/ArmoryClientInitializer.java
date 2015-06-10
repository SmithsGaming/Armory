package com.Orion.Armory.Client.Logic;
/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Events.Client.RegisterItemResourcesEvent;
import com.Orion.Armory.API.Events.Client.RegisterMaterialResourceEvent;
import com.Orion.Armory.Client.Renderer.Items.ItemHeatedIngotRenderer;
import com.Orion.Armory.Client.Renderer.Items.ItemRendererAnvil;
import com.Orion.Armory.Client.Renderer.Items.ItemRendererFirePit;
import com.Orion.Armory.Client.Renderer.Items.ItemRendererHeater;
import com.Orion.Armory.Client.Renderer.TileEntities.AnvilTESR;
import com.Orion.Armory.Client.Renderer.TileEntities.FirePitTESR;
import com.Orion.Armory.Client.Renderer.TileEntities.HeaterTESR;
import com.Orion.Armory.Common.Addons.MedievalAddonRegistry;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ArmoryClientInitializer extends ArmoryInitializer
{
    public static void InitializeClient()
    {
        MedievalInitialization.Initialize();
        MedievalClientInitialization.registerMaterialResources();
        MedievalClientInitialization.registerUpgradeResources();
        ArmoryInitializer.SystemInit.RegisterItems();
        ArmoryInitializer.SystemInit.RegisterBlocks();
        ArmoryInitializer.SystemInit.RegisterTileEntities();
        SystemInit.registerTESR();
        SystemInit.registerIIR();
        MedievalClientInitialization.registerRingResources();
        MedievalClientInitialization.registerChainResources();
        MedievalClientInitialization.registerNuggetResources();
        MedievalClientInitialization.registerPlateResources();
        MedievalInitialization.prepareGame();
    }
    
    public static class MedievalClientInitialization
    {
        public static void registerMaterialResources()
        {
            for(IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                for (MultiLayeredArmor tArmor : MaterialRegistry.getInstance().getAllRegisteredArmors().values())
                {
                    MinecraftForge.EVENT_BUS.post(new RegisterMaterialResourceEvent(tMaterial, tArmor));
                }
            }
        }

        public static void registerUpgradeResources()
        {
            //ArmorPieces
            MultiLayeredArmor tHelmet = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
            MultiLayeredArmor tChestplate =  MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE);
            MultiLayeredArmor tLeggings = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS);
            MultiLayeredArmor tShoes = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES);

            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                //Helmet resources
                CustomResource tTopHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Helmet_TopHead", "armory:textures/models/multiarmor/upgrades/armory.Helmet_TopHead.png", tMaterial.getColor());
                CustomResource tLeftHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Left", "armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Left.png", tMaterial.getColor());
                CustomResource tRightHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Right", "armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Right.png", tMaterial.getColor());
                tHelmet.registerResource(tTopHelmetResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.getInternalMaterialName()).setResource(tTopHelmetResource);
                tHelmet.registerResource(tLeftHelmetResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftHelmetResource);
                tHelmet.registerResource(tRightHelmetResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightHelmetResource);

                //Chestplate resources
                CustomResource tLeftShoulderChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left.png", tMaterial.getColor());
                CustomResource tRightShoulderChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right.png", tMaterial.getColor());
                CustomResource tLeftFrontChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Left.png", tMaterial.getColor());
                CustomResource tRightFrontChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Right.png", tMaterial.getColor());
                CustomResource tLeftBackChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Left.png", tMaterial.getColor());
                CustomResource tRightBackChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Right.png", tMaterial.getColor());
                tChestplate.registerResource(tLeftShoulderChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftShoulderChestplateResource);
                tChestplate.registerResource(tRightShoulderChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightShoulderChestplateResource);
                tChestplate.registerResource(tLeftFrontChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftFrontChestplateResource);
                tChestplate.registerResource(tRightFrontChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightFrontChestplateResource);
                tChestplate.registerResource(tLeftBackChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftBackChestplateResource);
                tChestplate.registerResource(tRightBackChestplateResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightBackChestplateResource);

                //Legging resources
                CustomResource tLeftFrontLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Left", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Left.png", tMaterial.getColor());
                CustomResource tRightFrontLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Right", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Right.png", tMaterial.getColor());
                CustomResource tLeftBackLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Left", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Left.png", tMaterial.getColor());
                CustomResource tRightBackLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Right", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Right.png", tMaterial.getColor());
                tLeggings.registerResource(tLeftFrontLeggingsResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftFrontLeggingsResource);
                tLeggings.registerResource(tRightFrontLeggingsResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightFrontLeggingsResource);
                tLeggings.registerResource(tLeftBackLeggingsResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftBackLeggingsResource);
                tLeggings.registerResource(tRightBackLeggingsResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightBackLeggingsResource);

                //Shoes resources
                CustomResource tLeftShoeResource = new CustomResource(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Shoes_Protection_Left", "armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Left.png", tMaterial.getColor());
                CustomResource tRightShoeResource = new CustomResource(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.getInternalMaterialName(), "armory:multiarmor/upgrades/armory.Shoes_Protection_Right", "armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Right.png", tMaterial.getColor());
                tShoes.registerResource(tLeftShoeResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.getInternalMaterialName()).setResource(tLeftShoeResource);
                tShoes.registerResource(tRightShoeResource);
                MedievalAddonRegistry.getInstance().getUpgrade(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.getInternalMaterialName()).setResource(tRightShoeResource);
            }
        }
        
        public static void registerRingResources()
        {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                MinecraftForge.EVENT_BUS.post(new RegisterItemResourcesEvent(tMaterial, GeneralRegistry.Items.iMetalRing));
            }

        }
        
        public static void registerChainResources()
        {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                MinecraftForge.EVENT_BUS.post(new RegisterItemResourcesEvent(tMaterial, GeneralRegistry.Items.iMetalChain));
            }

        }

        public static void registerNuggetResources()
        {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                MinecraftForge.EVENT_BUS.post(new RegisterItemResourcesEvent(tMaterial, GeneralRegistry.Items.iNugget));
            }

        }

        public static void registerPlateResources()
        {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            {
                MinecraftForge.EVENT_BUS.post(new RegisterItemResourcesEvent(tMaterial, GeneralRegistry.Items.iPlate));
            }
        }
        
    }

    public static class SystemInit
    {
        public static void registerIIR()
        {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GeneralRegistry.Blocks.iBlockFirePit), new ItemRendererFirePit());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GeneralRegistry.Blocks.iBlockHeater), new ItemRendererHeater());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GeneralRegistry.Blocks.iBlockAnvil), new ItemRendererAnvil());
            MinecraftForgeClient.registerItemRenderer(GeneralRegistry.Items.iHeatedIngot, new ItemHeatedIngotRenderer());
        }


        public static void registerTESR()
        {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFirePit.class, new FirePitTESR());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeater.class, new HeaterTESR());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArmorsAnvil.class, new AnvilTESR());
        }
    }
    

}
