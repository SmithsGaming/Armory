package com.smithsmodding.armory.client.logic;

/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.client.*;
import com.smithsmodding.armory.common.anvil.*;
import com.smithsmodding.armory.common.logic.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.armory.util.client.*;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.obj.*;

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
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setRenderInfo(new IMaterialRenderInfo.Metal(0xcacaca, 0f, 0.3f, 0f) {
                @Override
                public MinecraftColor getLiquidColor () {
                    return new MinecraftColor(MinecraftColor.RED);
                }
            });
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setTranslationKey(TranslationKeys.Materials.VisibleNames.Iron);

            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setRenderInfo(new IMaterialRenderInfo.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
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

            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.STONE).setRenderInfo(new IMaterialRenderInfo.BlockTexture("minecraft:blocks/stone"));
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.IRON).setRenderInfo(iron.getRenderInfo());
            AnvilMaterialRegistry.getInstance().getAnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN).setRenderInfo(obsidian.getRenderInfo());
        }
    }

    public static class SystemInit {
        public static void registerIIR() {
            ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

            for (MultiLayeredArmor armor : MaterialRegistry.getInstance().getAllRegisteredArmors().values()) {
                proxy.registerArmorItemModel(armor);
            }

            proxy.registerHeatedItemItemModel(GeneralRegistry.Items.heatedItem);

            OBJLoader.instance.addDomain(References.General.MOD_ID.toLowerCase());
            ModelLoader.setCustomModelResourceLocation(GeneralRegistry.Items.guide, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));
        }


        public static void registerTESR() {
            Item itemBlockFirePit = Item.getItemFromBlock(GeneralRegistry.Blocks.blockFirePit);
            Item itemBlockBlackSmithsAnvil = Item.getItemFromBlock(GeneralRegistry.Blocks.blockBlackSmithsAnvil);

            ModelLoader.setCustomModelResourceLocation(itemBlockFirePit, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.InternalNames.Blocks.FirePit, "inventory"));
            ModelLoader.setCustomModelResourceLocation(itemBlockBlackSmithsAnvil, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.InternalNames.Blocks.ArmorsAnvil, "inventory"));
        }
    }


}
