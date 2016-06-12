package com.smithsmodding.armory.client.logic;

/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.materials.MaterialRenderControllers;
import com.smithsmodding.armory.client.ArmoryClientProxy;
import com.smithsmodding.armory.common.logic.ArmoryInitializer;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ArmoryClientInitializer extends ArmoryInitializer {
    public static void InitializeClient() {
        ArmoryInitializer.SystemInit.RegisterFluids();
        MedievalInitialization.Initialize();
        GlobalInitialization.RegisterAnvilMaterials();
        ArmoryInitializer.SystemInit.RegisterBlocks();
        ArmoryInitializer.SystemInit.RegisterItems();
        ArmoryInitializer.SystemInit.RegisterTileEntities();
        SystemInit.registerIIR();
        SystemInit.registerTESR();
        MedievalClientInitialization.registerMaterialResources();
        MedievalClientInitialization.registerUpgradeResources();
        MedievalClientInitialization.registerRingResources();
        MedievalClientInitialization.registerChainResources();
        MedievalClientInitialization.registerNuggetResources();
        MedievalClientInitialization.registerPlateResources();
        MedievalInitialization.prepareGame();
        GlobalClientInitialization.RegisterAnvilMaterialRenderInfo();
        ArmoryInitializer.SystemInit.initializeOreDic();
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

        public static void registerUpgradeResources() {

        }

        public static void registerRingResources() {

        }

        public static void registerChainResources() {

        }

        public static void registerNuggetResources() {

        }

        public static void registerPlateResources() {

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

            MaterialRegistry.getInstance().getAllRegisteredArmors().values().forEach(ArmoryClientProxy::registerArmorItemModel);

            ArmoryClientProxy.registerHeatedItemItemModel(GeneralRegistry.Items.heatedItem);
            ArmoryClientProxy.registerComponentItemModel(GeneralRegistry.Items.armorComponent);

            OBJLoader.INSTANCE.addDomain(References.General.MOD_ID.toLowerCase());
            ModelLoader.setCustomModelResourceLocation(GeneralRegistry.Items.guide, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));
        }


        public static void registerTESR() {
            ArmoryClientProxy.registerBlockModel(GeneralRegistry.Blocks.blockFirePit);
            ArmoryClientProxy.registerBlockModel(GeneralRegistry.Blocks.blockBlackSmithsAnvil);
            ArmoryClientProxy.registerBlockModel(GeneralRegistry.Blocks.blockFirePlace);
        }
    }


}
