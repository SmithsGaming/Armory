package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.ArmoryClientProxy;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererConduit;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererForge;
import com.smithsmodding.armory.client.render.tileentity.TileEntityRendererMoltenMetalTank;
import com.smithsmodding.armory.common.block.BlockConduit;
import com.smithsmodding.armory.common.block.BlockMoltenMetalTank;
import com.smithsmodding.armory.common.block.BlockPump;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.tileentity.TileEntityConduit;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalTank;
import com.smithsmodding.smithscore.client.block.statemap.ExtendedStateMap;
import com.smithsmodding.smithscore.client.model.loader.MultiComponentModelLoader;
import com.smithsmodding.smithscore.client.model.loader.SmithsCoreOBJLoader;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientSystemInitializer extends IInitializationComponent.Impl {

    private static final ClientSystemInitializer INSTANCE = new ClientSystemInitializer();

    public static ClientSystemInitializer getInstance () {
        return INSTANCE;
    }

    private ClientSystemInitializer () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        registerIIR();
        registerTESR();
    }

    public static void registerIIR() {
        ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

        IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry().forEach(iMultiComponentArmor -> {
            ArmoryClientProxy.registerArmorItemModel(iMultiComponentArmor.getItem());
        });

        ArmoryClientProxy.registerHeatedItemItemModel((ItemHeatedItem) ModItems.IT_HEATEDITEM);
        ArmoryClientProxy.registerComponentItemModel((ItemArmorComponent) ModItems.IT_COMPONENT);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_CHAIN);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_PLATE);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_RING);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_NUGGET);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_INGOT);

        SmithsCoreOBJLoader.INSTANCE.addDomain(References.General.MOD_ID.toLowerCase());
        ModelLoader.setCustomModelResourceLocation(ModItems.IT_GUIDE, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));

        MultiComponentModelLoader.instance.registerDomain(References.General.MOD_ID);
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_TONGS, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_HAMMER, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_CONDUIT), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (stack.getMetadata() == 1)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory");

                if (stack.getMetadata() == 2)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory");

                if (stack.getMetadata() == 3)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_CONDUIT), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_TANK), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (stack.getMetadata() == 1)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory");

                if (stack.getMetadata() == 2)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_TANK), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_PUMP), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (stack.getMetadata() == 1)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory");

                if (stack.getMetadata() == 2)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_PUMP), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory");
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory"));
    }


    public static void registerTESR() {
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FORGE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_ANVIL);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FIREPLACE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_RESOURCE);

        ModelLoader.setCustomStateMapper(ModBlocks.BL_CONDUIT, new ExtendedStateMap.Builder().withName(BlockConduit.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Conduit.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_TANK, new ExtendedStateMap.Builder().withName(BlockMoltenMetalTank.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Tank.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_PUMP, new ExtendedStateMap.Builder().withName(BlockPump.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Pump.").build());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityRendererForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConduit.class, new TileEntityRendererConduit());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalTank.class, new TileEntityRendererMoltenMetalTank());
    }
}
