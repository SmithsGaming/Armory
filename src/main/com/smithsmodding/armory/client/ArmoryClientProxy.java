package com.smithsmodding.armory.client;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.logic.initialization.*;
import com.smithsmodding.armory.client.model.loaders.ArmorComponentModelLoader;
import com.smithsmodding.armory.client.model.loaders.HeatedItemModelLoader;
import com.smithsmodding.armory.client.model.loaders.MaterializedItemModelLoader;
import com.smithsmodding.armory.client.model.loaders.MultiLayeredArmorModelLoader;
import com.smithsmodding.armory.common.ArmoryCommonProxy;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import javax.annotation.Nonnull;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy {



    public static void registerBlockModel(@Nonnull Block block) {
        Item blockItem = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(blockItem, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    public static ResourceLocation registerMaterializedItemModel(@Nonnull Item item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "materialized/" + itemLocation.getResourcePath() + MaterializedItemModelLoader.EXTENSION;

        return registerMaterializedItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerComponentItemModel(@Nonnull ItemArmorComponent item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "component/" + itemLocation.getResourcePath() + ArmorComponentModelLoader.EXTENSION;

        return registerComponentItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerArmorItemModel(@Nonnull Item item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "armor/" + itemLocation.getResourcePath() + MultiLayeredArmorModelLoader.EXTENSION;

        return registerArmorItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerHeatedItemItemModel(@Nonnull ItemHeatedItem item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "heateditem/" + itemLocation.getResourcePath() + HeatedItemModelLoader.EXTENSION;

        return registerHeatedItemItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    @Nonnull
    public static ResourceLocation registerMaterializedItemModel(@Nonnull Item item, @Nonnull final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MaterializedItemModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The materialized-model " + location.toString() + " does not end with '"
                    + MaterializedItemModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MaterializedItemModelLoader.EXTENSION);
    }

    @Nonnull
    public static ResourceLocation registerComponentItemModel(@Nonnull ItemArmorComponent item, @Nonnull final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(ArmorComponentModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The component-model " + location.toString() + " does not end with '"
                    + ArmorComponentModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, ArmorComponentModelLoader.EXTENSION);
    }

    @Nonnull
    public static ResourceLocation registerArmorItemModel(@Nonnull Item item, @Nonnull final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiLayeredArmorModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The armor-model " + location.toString() + " does not end with '"
                    + MultiLayeredArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MultiLayeredArmorModelLoader.EXTENSION);
    }

    @Nonnull
    public static ResourceLocation registerHeatedItemItemModel(@Nonnull ItemHeatedItem item, @Nonnull final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(HeatedItemModelLoader.EXTENSION)) {
            ModLogger.getInstance().error("The heated-model " + location.toString() + " does not end with '"
                    + HeatedItemModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, HeatedItemModelLoader.EXTENSION);
    }

    @Nonnull
    public static ResourceLocation registerItemModelDefinition(@Nonnull Item item, @Nonnull final ResourceLocation location, @Nonnull String requiredExtension) {
        if (!location.getResourcePath().endsWith(requiredExtension)) {
            ModLogger.getInstance().error("The item-model " + location.toString() + " does not end with '"
                    + requiredExtension
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Nonnull
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to read the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelBakery.registerItemVariants(item, location);

        ModLogger.getInstance().info("Added model definition for: " + item.getUnlocalizedName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext pContext) {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void registerInitializationComponents(IForgeRegistry<IInitializationComponent> registry) {
        super.registerInitializationComponents(registry);

        registry.register(ClientEventHandlerInitialization.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Client.EVENTHANDLER));
        registry.register(ClientModelLoaderInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Client.MODELLOADER));
        registry.register(ClientStructureInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Client.STRUCTURE));
        registry.register(ClientSystemInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Client.SYSTEM));
        registry.register(ClientMedievalInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Client.MEDIEVAL));
    }
}
