package com.smithsmodding.armory.client.logic;

/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.*;
import com.smithsmodding.armory.client.*;
import com.smithsmodding.armory.common.logic.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.util.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.obj.*;

public class ArmoryClientInitializer extends ArmoryInitializer {
    public static void InitializeClient() {
        ArmoryInitializer.SystemInit.RegisterFluids();
        MedievalInitialization.Initialize();
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
        ArmoryInitializer.SystemInit.initializeOreDic();
    }

    public static class MedievalClientInitialization {
        public static void registerMaterialResources() {
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).setRenderInfo(new IMaterialRenderInfo.Metal(0xcacaca, 0f, 0.3f, 0f));
            MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN).setRenderInfo(new IMaterialRenderInfo.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
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
            ModelLoader.setCustomModelResourceLocation(itemBlockFirePit, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.InternalNames.Blocks.FirePit, "inventory"));
        }
    }


}
