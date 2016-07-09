package com.smithsmodding.armory.client;

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.handler.ClientDisconnectedFromServerEventHandler;
import com.smithsmodding.armory.client.logic.ArmoryClientInitializer;
import com.smithsmodding.armory.client.model.loaders.*;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.ArmoryCommonProxy;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.structure.forge.StructureFactoryForge;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy {

    private static ArmorComponentModelLoader armorComponentModelLoader = new ArmorComponentModelLoader();
    private static MultiLayeredArmorModelLoader multiLayeredArmorModelLoader = new MultiLayeredArmorModelLoader();
    private static HeatedItemModelLoader heatedItemModelLoader = new HeatedItemModelLoader();
    private static AnvilModelLoader anvilBlockModelLoader = new AnvilModelLoader();
    private static MaterializedItemModelLoader materializedItemModelLoader = new MaterializedItemModelLoader();

    public static void registerBlockModel(Block block) {
        Item blockItem = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(blockItem, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    public static ResourceLocation registerMaterializedItemModel(Item item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "materialized/" + itemLocation.getResourcePath() + MaterializedItemModelLoader.EXTENSION;

        return registerMaterializedItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerComponentItemModel(ItemArmorComponent item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "component/" + itemLocation.getResourcePath() + ArmorComponentModelLoader.EXTENSION;

        return registerComponentItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerArmorItemModel(MultiLayeredArmor item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "armor/" + itemLocation.getResourcePath() + MultiLayeredArmorModelLoader.EXTENSION;

        return registerArmorItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerHeatedItemItemModel(ItemHeatedItem item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "heateditem/" + itemLocation.getResourcePath() + HeatedItemModelLoader.EXTENSION;

        return registerHeatedItemItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerMaterializedItemModel(Item item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MaterializedItemModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The materialized-model " + location.toString() + " does not end with '"
                    + MaterializedItemModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MaterializedItemModelLoader.EXTENSION);
    }

    public static ResourceLocation registerComponentItemModel(ItemArmorComponent item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(ArmorComponentModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The component-model " + location.toString() + " does not end with '"
                    + ArmorComponentModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, ArmorComponentModelLoader.EXTENSION);
    }

    public static ResourceLocation registerArmorItemModel(MultiLayeredArmor item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiLayeredArmorModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The armor-model " + location.toString() + " does not end with '"
                    + MultiLayeredArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MultiLayeredArmorModelLoader.EXTENSION);
    }

    public static ResourceLocation registerHeatedItemItemModel(ItemHeatedItem item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(HeatedItemModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The heated-model " + location.toString() + " does not end with '"
                    + HeatedItemModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, HeatedItemModelLoader.EXTENSION);
    }

    public static ResourceLocation registerItemModelDefinition(Item item, final ResourceLocation location, String requiredExtension) {
        if (!location.getResourcePath().endsWith(requiredExtension)) {
            ModLogger.getInstance().error("The item-model " + location.toString() + " does not end with '"
                    + requiredExtension
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation (ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to read the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelBakery.registerItemVariants(item, location);

        ModLogger.getInstance().info("Added model definition for: " + item.getUnlocalizedName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }

    @Override
    public void preInitializeArmory() {
        ModelLoaderRegistry.registerLoader(multiLayeredArmorModelLoader);
        ModelLoaderRegistry.registerLoader(heatedItemModelLoader);
        ModelLoaderRegistry.registerLoader(anvilBlockModelLoader);
        ModelLoaderRegistry.registerLoader(armorComponentModelLoader);
        ModelLoaderRegistry.registerLoader(materializedItemModelLoader);

        ArmoryClientInitializer.InitializeClient();
    }

    @Override
    public void initializeArmory() {

    }

    @Override
    public void initializeStructures() {
        super.initializeStructures();
        StructureRegistry.getClientInstance().registerStructureFactory(new StructureFactoryForge());
    }

    @Override
    public EntityPlayer getPlayer(MessageContext pContext) {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MaterializedTextureCreator materializedTextureCreator = new MaterializedTextureCreator();
        MinecraftForge.EVENT_BUS.register(materializedTextureCreator);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(materializedTextureCreator);

        MinecraftForge.EVENT_BUS.register(new com.smithsmodding.armory.api.util.client.Textures());
        MinecraftForge.EVENT_BUS.register(new ClientDisconnectedFromServerEventHandler());

    }
}
