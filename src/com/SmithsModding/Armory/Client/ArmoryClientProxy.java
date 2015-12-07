package com.SmithsModding.Armory.Client;

import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.Logic.ArmoryClientInitializer;
import com.SmithsModding.Armory.Client.Model.Loaders.MedievalComponentModelLoader;
import com.SmithsModding.Armory.Client.Model.Loaders.MultiLayeredArmorModelLoader;
import com.SmithsModding.Armory.Client.Textures.MaterializedTextureCreator;
import com.SmithsModding.Armory.Common.ArmoryCommonProxy;
import com.SmithsModding.SmithsCore.Util.Client.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
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

    private static MedievalComponentModelLoader medievalComponentModelLoader = new MedievalComponentModelLoader();
    private static MultiLayeredArmorModelLoader multiLayeredArmorModelLoader = new MultiLayeredArmorModelLoader();

    @Override
    public void preInitializeArmory() {
        ModelLoaderRegistry.registerLoader(medievalComponentModelLoader);
        ModelLoaderRegistry.registerLoader(multiLayeredArmorModelLoader);

        MaterializedTextureCreator materializedTextureCreator = new MaterializedTextureCreator();
        MinecraftForge.EVENT_BUS.register(materializedTextureCreator);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(materializedTextureCreator);

        ArmoryClientInitializer.InitializeClient();
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

    public ResourceLocation registerArmorItemModel (MultiLayeredArmor item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "Armor/" + itemLocation.getResourcePath() + MultiLayeredArmorModelLoader.EXTENSION;

        return registerArmorItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public ResourceLocation registerArmorItemModel (MultiLayeredArmor item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiLayeredArmorModelLoader.EXTENSION)) {
            Armory.getLogger().error("The material-model " + location.toString() + " does not end with '"
                    + MultiLayeredArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerArmorItemModelDefinition(item, location);
    }


    public ResourceLocation registerArmorItemModelDefinition (MultiLayeredArmor armor, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiLayeredArmorModelLoader.EXTENSION)) {
            Armory.getLogger().error("The Armor-Item-Model " + location.toString() + " does not end with '"
                    + MultiLayeredArmorModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        ModelLoader.setCustomMeshDefinition(armor, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation (ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to read the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelBakery.addVariantName(armor, location.toString());

        Armory.getLogger().info("Added model definition for: " + armor.getInternalName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }
}
