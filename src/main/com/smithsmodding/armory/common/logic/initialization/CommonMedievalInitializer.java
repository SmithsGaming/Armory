package com.smithsmodding.armory.common.logic.initialization;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.component.OreDicAnvilRecipeComponent;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import com.smithsmodding.armory.util.CapabilityHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;

import static com.smithsmodding.armory.api.util.references.References.InternalNames.Recipes.Anvil.*;

/**
 * Created by marcf on 1/20/2017.
 */
public final class CommonMedievalInitializer extends IInitializationComponent.Impl {

    private final static CommonMedievalInitializer INSTANCE = new CommonMedievalInitializer();

    public static CommonMedievalInitializer getInstance() {
        return INSTANCE;
    }

    private CommonMedievalInitializer() {
    }

    @Override
    public void onLoadCompleted(@Nonnull FMLLoadCompleteEvent event) {
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().getValues().clear();
        initializeAnvilRecipes();
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.IT_HAMMER, 1, 150), "  B", " S ", "S  ", 'B', new ItemStack(Blocks.IRON_BLOCK), 'S', new ItemStack(Items.STICK));
        registerHeatableOverrides();
    }

    private static void initializeAnvilRecipes() {
        ItemStack fireplaceStack = new ItemStack(ModBlocks.BL_FIREPLACE);
        IAnvilRecipe fireplaceRecipe = new AnvilRecipe()
                .setCraftingSlotContent(0, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(1, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(2, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(3, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(4, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(5, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(6, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(8, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(9, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(10, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(12, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(14, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(15, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(16, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(18, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(19, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(20, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(21, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(22, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(23, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(24, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setHammerUsage(0).setTongUsage(0).setResult(fireplaceStack).setProgress(10).setRegistryName(RN_FIREPLACE);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(fireplaceRecipe);

        ItemStack forgeStack = new ItemStack(ModBlocks.BL_FORGE);
        IAnvilRecipe forgeRecipe = new AnvilRecipe()
                .setCraftingSlotContent(0, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(4, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.95F))
                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.95F))
                .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(22, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(22, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setHammerUsage(15).setTongUsage(25).setResult(forgeStack).setProgress(60).setRegistryName(RN_FORGE);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(forgeRecipe);

        ItemStack hammerStack = new ItemStack(ModItems.IT_HAMMER, 1);
        hammerStack.setItemDamage(150);
        IAnvilRecipe hammerRecipe = new AnvilRecipe()
                .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(8, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(12, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(16, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(20, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setProgress(4).setResult(hammerStack).setHammerUsage(4).setTongUsage(0).setRegistryName(RN_HAMMER);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(hammerRecipe);

        ItemStack tongStack = new ItemStack(ModItems.IT_TONGS, 1);
        tongStack.setItemDamage(150);
        IAnvilRecipe tongRecipe = new AnvilRecipe()
                .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setProgress(4).setResult(tongStack).setHammerUsage(4).setTongUsage(0).setRegistryName(RN_TONGS);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(tongRecipe);

        initializeAnvilCreationAnvilRecipes();
        initializeMedievalArmorAnvilRecipes();
        initializeMedievalUpgradeAnvilRecipes();
        initializeUpgradeRecipeSystem();
    }

    private static void initializeAnvilCreationAnvilRecipes() {
        for (IAnvilMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
            IAnvilRecipe recipe = material.getRecipeForAnvil();

            if (recipe == null)
                continue;

            recipe.setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_ANVIL.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(recipe);
        }

        IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
    }

    private static void initializeMedievalArmorAnvilRecipes() {
        for (ICoreArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            ItemStack chestPlateStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.CHESTPLATE, new ArrayList<>(), material.getBaseDurabilityForArmor(ModArmor.Medieval.CHESTPLATE), material);
            IAnvilRecipe chestPlateRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(chestPlateStack).setHammerUsage(38).setTongUsage(24).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_CHESTPLATE.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(chestPlateRecipe);

            ItemStack helmetStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, new ArrayList<>(), material.getBaseDurabilityForArmor(ModArmor.Medieval.HELMET), material);
            IAnvilRecipe helmetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(helmetStack).setHammerUsage(28).setTongUsage(16).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_HELMET.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(helmetRecipe);

            ItemStack leggingsStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.LEGGINGS, new ArrayList<>(), material.getBaseDurabilityForArmor(ModArmor.Medieval.LEGGINGS), material);
            IAnvilRecipe leggingsRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(leggingsStack).setHammerUsage(28).setTongUsage(16).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_LEGGINGS.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(leggingsRecipe);

            ItemStack shoeStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.SHOES, new ArrayList<>(), material.getBaseDurabilityForArmor(ModArmor.Medieval.SHOES), material);
            IAnvilRecipe shoeRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(shoeStack).setHammerUsage(18).setTongUsage(12).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_SHOES.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(shoeRecipe);
        }
    }

    private static void initializeMedievalUpgradeAnvilRecipes() {
        for(IMultiComponentArmorExtension extension : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry()) {
            if (!extension.hasItemStack())
                continue;

            IAnvilRecipe recipe = extension.getRecipeCallback().getCreationRecipe(extension);

            if (IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(recipe.getRegistryName()))
                continue;

            if (recipe != null)
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(recipe);
        }
    }

    private static void initializeUpgradeRecipeSystem() {
        IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry().forEach(iMultiComponentArmor -> {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(iCoreArmorMaterial -> {
                IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().forEach(extension -> {
                    if (!extension.hasItemStack())
                        return;

                    IAnvilRecipe upgradeRecipe = extension.getRecipeCallback().getAttachingRecipe(extension, iMultiComponentArmor, iCoreArmorMaterial);

                    if (IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(upgradeRecipe.getRegistryName()))
                        return;

                    if (upgradeRecipe != null)
                        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(upgradeRecipe);
                });
            });
        });
    }

    private static void registerHeatableOverrides() {
        registerHeatableOverrideForItems(ModMaterials.Armor.Core.IRON, new ItemStack(Items.IRON_INGOT), null, new ItemStack(Blocks.IRON_BLOCK), null, null, null);
        registerHeatableOverrideForItems(ModMaterials.Armor.Addon.IRON, new ItemStack(Items.IRON_INGOT), null, new ItemStack(Blocks.IRON_BLOCK), null, null, null);
        registerHeatableOverrideForItems(ModMaterials.Anvil.IRON, new ItemStack(Items.IRON_INGOT), null, new ItemStack(Blocks.IRON_BLOCK), null, null, null);

        registerHeatableOverrideForItems(ModMaterials.Armor.Core.OBSIDIAN, null, null, new ItemStack(Blocks.OBSIDIAN), null, null, null);
        registerHeatableOverrideForItems(ModMaterials.Armor.Addon.OBSIDIAN, null, null, new ItemStack(Blocks.OBSIDIAN), null, null, null);
        registerHeatableOverrideForItems(ModMaterials.Anvil.OBSIDIAN, null, null, new ItemStack(Blocks.OBSIDIAN), null, null, null);

        registerHeatableOverrideForItems(ModMaterials.Armor.Core.GOLD, new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET), new ItemStack(Blocks.GOLD_BLOCK), null, null, null);
        registerHeatableOverrideForItems(ModMaterials.Armor.Addon.GOLD, new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_NUGGET), new ItemStack(Blocks.GOLD_BLOCK), null, null, null);
    }

    private static void registerHeatableOverrideForItems(@Nonnull IMaterial material
            , @Nullable ItemStack ingotItem
            , @Nullable ItemStack nuggetItem
            , @Nullable ItemStack blockItem
            , @Nullable ItemStack chainItem
            , @Nullable ItemStack ringItem
            , @Nullable ItemStack plateItem) {

        if (ingotItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                , ModHeatedObjectTypes.INGOT
                , material
                , ingotItem);

        if (nuggetItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                , ModHeatedObjectTypes.NUGGET
                , material
                , nuggetItem);

        if (blockItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                , ModHeatedObjectTypes.BLOCK
                , material
                , blockItem);

        if (chainItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                    , ModHeatedObjectTypes.CHAIN
                    , material
                    , chainItem);

        if (ringItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                    , ModHeatedObjectTypes.RING
                    , material
                    , ringItem);

        if (plateItem != null)
            IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(ModHeatableObjects.ITEMSTACK
                    , ModHeatedObjectTypes.PLATE
                    , material
                    , plateItem);
    }

    private static class MaterializedResourceRecipeCreationHandler implements Consumer<IMaterial> {

        /**
         * Performs this operation on the given argument.
         *
         * @param material the input argument
         */
        @Override
        public void accept(IMaterial material) {
            ItemStack ringStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_RING, material, 1);
            IAnvilRecipe ringRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.65F, material.getMeltingPoint() * 0.75F)))
                    .setProgress(9).setResult(ringStack).setHammerUsage(4).setTongUsage(0).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_RING.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(ringRecipe);

            ItemStack plateStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_RING, material, 1);

            IAnvilRecipe plateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.65F, material.getMeltingPoint() * 0.75F)))
                    .setProgress(15).setResult(plateStack).setHammerUsage(15).setTongUsage(2).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_PLATE.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(plateRecipe);

            ItemStack nuggetStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_RING, material, 1);

            IAnvilRecipe nuggetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.5F) * 0.65F, (material.getMeltingPoint() * 0.5F) * 0.75F)))
                    .setProgress(6).setResult(nuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_NUGGET.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(nuggetRecipe);

            ItemStack chainStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_RING, material, 1);

            IAnvilRecipe chainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setProgress(10).setResult(chainStack).setHammerUsage(16).setTongUsage(16)
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_CHAIN.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(chainRecipe);
        }
    }

}
