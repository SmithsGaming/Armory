package com.smithsmodding.armory.client;

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.client.logic.*;
import com.smithsmodding.armory.client.model.loaders.*;
import com.smithsmodding.armory.client.textures.*;
import com.smithsmodding.armory.common.*;
import com.smithsmodding.armory.common.item.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy {

    private static MultiLayeredArmorModelLoader multiLayeredArmorModelLoader = new MultiLayeredArmorModelLoader();
    private static HeatedItemModelLoader heatedItemModelLoader = new HeatedItemModelLoader();
    private static AnvilModelLoader anvilBlockModelLoader = new AnvilModelLoader();

    @Override
    public void preInitializeArmory() {
        ModelLoaderRegistry.registerLoader(multiLayeredArmorModelLoader);
        ModelLoaderRegistry.registerLoader(heatedItemModelLoader);
        ModelLoaderRegistry.registerLoader(anvilBlockModelLoader);

        MaterializedTextureCreator materializedTextureCreator = new MaterializedTextureCreator();
        MinecraftForge.EVENT_BUS.register(materializedTextureCreator);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(materializedTextureCreator);

        ArmoryClientInitializer.InitializeClient();

        ModelLoader.setCustomStateMapper(GeneralRegistry.Blocks.blockFirePit, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation (IBlockState state) {
                return new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.InternalNames.Blocks.FirePit, "normal");
            }
        });

        ModelLoader.setCustomStateMapper(GeneralRegistry.Blocks.blockBlackSmithsAnvil, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation (IBlockState state) {
                return new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.InternalNames.Blocks.ArmorsAnvil, "normal");
            }
        });

        MinecraftForge.EVENT_BUS.register(new com.smithsmodding.armory.util.client.Textures());
    }

    @Override
    public void initializeArmory() {

    }

    @Override
    public EntityPlayer getPlayer(MessageContext pContext) {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
    }

    public void registerBlockModel (Block block) {
        Item blockItem = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(blockItem, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + block.getUnlocalizedName(), "inventory"));
    }

    public ResourceLocation registerArmorItemModel (MultiLayeredArmor item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "armor/" + itemLocation.getResourcePath() + MultiLayeredArmorModelLoader.EXTENSION;

        return registerArmorItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public ResourceLocation registerHeatedItemItemModel (ItemHeatedItem item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "HeatedItem/" + itemLocation.getResourcePath() + HeatedItemModelLoader.EXTENSION;

        return registerHeatedItemItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public ResourceLocation registerArmorItemModel (MultiLayeredArmor item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiLayeredArmorModelLoader.EXTENSION)) {
            Armory.getLogger().error("The material-model " + location.toString() + " does not end with '"
                    + MultiLayeredArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MultiLayeredArmorModelLoader.EXTENSION);
    }

    public ResourceLocation registerHeatedItemItemModel (ItemHeatedItem item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(HeatedItemModelLoader.EXTENSION)) {
            Armory.getLogger().error("The material-model " + location.toString() + " does not end with '"
                    + HeatedItemModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, HeatedItemModelLoader.EXTENSION);
    }


    public ResourceLocation registerItemModelDefinition (Item item, final ResourceLocation location, String requiredExtension) {
        if (!location.getResourcePath().endsWith(requiredExtension)) {
            Armory.getLogger().error("The item-model " + location.toString() + " does not end with '"
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
        ModelBakery.addVariantName(item, location.toString());

        Armory.getLogger().info("Added model definition for: " + item.getUnlocalizedName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }
}