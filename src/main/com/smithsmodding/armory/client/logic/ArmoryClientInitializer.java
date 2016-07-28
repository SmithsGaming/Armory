package com.smithsmodding.armory.client.logic;

/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.materials.MaterialRenderControllers;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.ArmoryClientProxy;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererConduit;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererForge;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererMoltenMetalTank;
import com.smithsmodding.armory.common.block.BlockConduit;
import com.smithsmodding.armory.common.block.BlockMoltenMetalTank;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.common.registry.ArmorRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalTank;
import com.smithsmodding.smithscore.client.model.loader.MultiComponentModelLoader;
import com.smithsmodding.smithscore.client.model.loader.SmithsCoreOBJLoader;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ArmoryClientInitializer extends ArmoryInitializer {
    public static void InitializeClient() {
        ArmoryInitializer.SystemInit.registerCreativeTabs();
        ArmoryInitializer.SystemInit.registerFluids();
        MedievalInitialization.preInitialize();
        GlobalInitialization.registerAnvilMaterials();
        ArmoryInitializer.SystemInit.registerBlocks();
        ArmoryInitializer.SystemInit.registerItems();
        ArmoryInitializer.SystemInit.registerTileEntities();
        SystemInit.registerIIR();
        SystemInit.registerTESR();
        MedievalClientInitialization.registerMaterialResources();
        GlobalClientInitialization.RegisterAnvilMaterialRenderInfo();
    }

    public static class MedievalClientInitialization {
        public static void registerMaterialResources() {
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setRenderInfo(new MaterialRenderControllers.Metal(0xcacaca, 0f, 0.3f, 0f) {
                @Override
                public MinecraftColor getLiquidColor () {
                    return new MinecraftColor(MinecraftColor.RED);
                }
            });
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setTranslationKey(TranslationKeys.Materials.VisibleNames.Iron);

            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setRenderInfo(new MaterialRenderControllers.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setTranslationKey(TranslationKeys.Materials.VisibleNames.Obsidian);
        }
    }

    public static class GlobalClientInitialization
    {
        public static void RegisterAnvilMaterialRenderInfo()
        {
            IArmorMaterial iron = MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON);
            IArmorMaterial obsidian = MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN);

            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.STONE).setRenderInfo(new MaterialRenderControllers.BlockTexture("minecraft:blocks/stone"));
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.IRON).setRenderInfo(iron.getRenderInfo());
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN).setRenderInfo(obsidian.getRenderInfo());
        }
    }

    public static class SystemInit {
        public static void registerIIR() {
            ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

            ArmorRegistry.getInstance().getAllRegisteredArmors().values().forEach(ArmoryClientProxy::registerArmorItemModel);

            ArmoryClientProxy.registerHeatedItemItemModel((ItemHeatedItem) ModItems.heatedItem);
            ArmoryClientProxy.registerComponentItemModel((ItemArmorComponent) ModItems.armorComponent);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalChain);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalPlate);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalRing);
            ArmoryClientProxy.registerMaterializedItemModel(ModItems.metalNugget);

            SmithsCoreOBJLoader.INSTANCE.addDomain(References.General.MOD_ID.toLowerCase());
            ModelLoader.setCustomModelResourceLocation(ModItems.guide, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));

            MultiComponentModelLoader.instance.registerDomain(References.General.MOD_ID);
            CoreClientProxy.registerMultiComponentItemModel(ModItems.tongs, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
            CoreClientProxy.registerMultiComponentItemModel(ModItems.hammer, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));

            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.blockConduit), new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    if (stack.getMetadata() == 1)
                        return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "normal_conduit"), "inventory");

                    if (stack.getMetadata() == 2)
                        return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "light_conduit"), "inventory");

                    return null;
                }
            });

            ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.blockConduit), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "normal_conduit"), "inventory"),
                    new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "light_conduit"), "inventory"));

            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.blockMoltenMetalTank), new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    if (stack.getMetadata() == 1)
                        return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "normal_mmtank"), "inventory");

                    if (stack.getMetadata() == 2)
                        return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "light_mmtank"), "inventory");

                    return null;
                }
            });

            ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.blockMoltenMetalTank), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "normal_mmtank"), "inventory"),
                    new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "light_mmtank"), "inventory"));
        }


        public static void registerTESR() {
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockForge);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockBlackSmithsAnvil);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockFirePlace);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockConduit);
            ArmoryClientProxy.registerBlockModel(ModBlocks.blockMoltenMetalTank);

            ModelLoader.setCustomStateMapper(ModBlocks.blockConduit, new StateMap.Builder().withName(BlockConduit.TYPE).withSuffix("_conduit").build());
            ModelLoader.setCustomStateMapper(ModBlocks.blockMoltenMetalTank, new StateMap.Builder().withName(BlockMoltenMetalTank.TYPE).withSuffix("_mmtank").build());

            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityRendererForge());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConduit.class, new TileEntityRendererConduit());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalTank.class, new TileEntityRendererMoltenMetalTank());
        }
    }


}
