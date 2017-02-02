package com.smithsmodding.armory.common.logic.initialization;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.common.creativetabs.ArmorTab;
import com.smithsmodding.armory.common.creativetabs.ComponentsTab;
import com.smithsmodding.armory.common.creativetabs.GeneralTabs;
import com.smithsmodding.armory.common.creativetabs.HeatedItemTab;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.armory.util.CapabilityHelper;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonSystemInitializer extends IInitializationComponent.Impl implements IInitializationComponent {

    private static final CommonSystemInitializer INSTANCE = new CommonSystemInitializer();

    public static CommonSystemInitializer getInstance() {
        return INSTANCE;
    }

    private CommonSystemInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        registerFluids();
        registerTileEntities();
        registerCreativeTabs();
    }

    @Override
    public void onPostInit(@Nonnull FMLPostInitializationEvent event) {
        removeRecipes();
        initializeOreDict();
    }

    private void registerFluids() {
        ModFluids.moltenMetal = new FluidMoltenMetal();

        FluidRegistry.registerFluid(ModFluids.moltenMetal);
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityForge.class, References.InternalNames.TileEntities.ForgeContainer);
        GameRegistry.registerTileEntity(TileEntityFireplace.class, References.InternalNames.TileEntities.FireplaceContainer);
        GameRegistry.registerTileEntity(TileEntityBlackSmithsAnvil.class, References.InternalNames.TileEntities.ArmorsAnvil);
        GameRegistry.registerTileEntity(TileEntityConduit.class, References.InternalNames.TileEntities.Conduit);
        GameRegistry.registerTileEntity(TileEntityMoltenMetalTank.class, References.InternalNames.TileEntities.Tank);
        GameRegistry.registerTileEntity(TileEntityPump.class, References.InternalNames.TileEntities.Pump);
    }

    private static void registerCreativeTabs() {
        ModCreativeTabs.GENERAL = new GeneralTabs();
        ModCreativeTabs.COMPONENTS = new ComponentsTab();
        ModCreativeTabs.HEATEDITEM = new HeatedItemTab();
        ModCreativeTabs.ARMOR = new ArmorTab();

        ModItems.IT_CHAIN.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_GUIDE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_HAMMER.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_INGOT.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_NUGGET.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_PLATE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_RING.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_TONGS.setCreativeTab(ModCreativeTabs.GENERAL);

        ModItems.IT_COMPONENT.setCreativeTab(ModCreativeTabs.COMPONENTS);

        ModItems.IT_HEATEDITEM.setCreativeTab(ModCreativeTabs.HEATEDITEM);

        ModItems.Armor.IT_CHESTPLATE.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_HELMET.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_LEGGINGS.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_SHOES.setCreativeTab(ModCreativeTabs.ARMOR);

        ModBlocks.BL_ANVIL.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_CONDUIT.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_FIREPLACE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_FORGE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_PUMP.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_TANK.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_RESOURCE.setCreativeTab(ModCreativeTabs.GENERAL);
    }

    private static void removeRecipes() {
        if (!ArmoryConfig.enableHardModeNuggetRemoval)
            return;

        ListIterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().listIterator();
        while (iterator.hasNext()) {
            IRecipe recipe = iterator.next();
            tryRemoveRecipeFromGame(recipe, iterator);
        }
    }

    private static void tryRemoveRecipeFromGame(@Nonnull IRecipe recipe, @Nonnull Iterator iterator) {
        if (recipe.getRecipeOutput().isEmpty())
            return;

        if (recipe.getRecipeOutput().getItem() == null)
            return;

        int[] oreIds = OreDictionary.getOreIDs(recipe.getRecipeOutput());

        for (int Id : oreIds) {
            String oreName = OreDictionary.getOreName(Id);
            if (oreName.contains("nugget")) {
                for (ICoreArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        } catch (IllegalStateException ex) {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }

                for (IAddonArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        } catch (IllegalStateException ex) {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }

                for (IAnvilMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        } catch (IllegalStateException ex) {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void initializeOreDict() {
        NonNullList<ItemStack> chains = NonNullList.create();
        NonNullList<ItemStack> rings = NonNullList.create();
        NonNullList<ItemStack> plates = NonNullList.create();
        NonNullList<ItemStack> nuggets = NonNullList.create();
        NonNullList<ItemStack> ingots = NonNullList.create();
        NonNullList<ItemStack> blocks = NonNullList.create();

        ModItems.IT_CHAIN.getSubItems(ModItems.IT_CHAIN, null, chains);
        ModItems.IT_NUGGET.getSubItems(ModItems.IT_NUGGET, null, rings);
        ModItems.IT_PLATE.getSubItems(ModItems.IT_PLATE, null, plates);
        ModItems.IT_RING.getSubItems(ModItems.IT_RING, null, nuggets);
        ModItems.IT_INGOT.getSubItems(ModItems.IT_INGOT, null, ingots);
        ModBlocks.BL_RESOURCE.getSubBlocks(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), null, blocks);

        for (ItemStack chain : chains) {
            OreDictionary.registerOre("chain" + CapabilityHelper.getMaterialFromMaterializedStack(chain).getOreDictionaryIdentifier(), chain);
        }

        for (ItemStack ring : rings) {
            OreDictionary.registerOre("ring" + CapabilityHelper.getMaterialFromMaterializedStack(ring).getOreDictionaryIdentifier(), ring);
        }

        for (ItemStack plate : plates) {
            OreDictionary.registerOre("plate" + CapabilityHelper.getMaterialFromMaterializedStack(plate).getOreDictionaryIdentifier(), plate);
        }

        for (ItemStack nugget : nuggets) {
            OreDictionary.registerOre("nugget" + CapabilityHelper.getMaterialFromMaterializedStack(nugget).getOreDictionaryIdentifier(), nugget);
        }

        for (ItemStack ingot : ingots) {
            OreDictionary.registerOre("ingot" + CapabilityHelper.getMaterialFromMaterializedStack(ingot).getOreDictionaryIdentifier(), ingot);
        }

        for (ItemStack block : blocks) {
            OreDictionary.registerOre("block" + CapabilityHelper.getMaterialFromMaterializedStack(block).getOreDictionaryIdentifier(), block);
        }

        OreDictionary.registerOre("blockObsidian", Blocks.OBSIDIAN);
        OreDictionary.registerOre("blockIron", Blocks.IRON_BLOCK);
        OreDictionary.registerOre("blockGold", Blocks.GOLD_BLOCK);
        OreDictionary.registerOre("blockStone", Blocks.STONE);
    }
}
