package com.smithsmodding.armory.common.handlers;

/**
 * Created by marcf on 2/1/2017.
 */

import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModHeatedObjectTypes;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.util.CapabilityHelper;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class VanillaMetalBlockConversionEventHandler {

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.HarvestDropsEvent breakEvent) {
        if (breakEvent.getState().getBlock() == Blocks.IRON_BLOCK) {
            breakEvent.getDrops().clear();
            breakEvent.getDrops().add(CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.IRON, 1, ModHeatedObjectTypes.BLOCK));
            breakEvent.setDropChance(1f);
        } else if (breakEvent.getState().getBlock() == Blocks.GOLD_BLOCK) {
            breakEvent.getDrops().clear();
            breakEvent.getDrops().add(CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.GOLD, 1, ModHeatedObjectTypes.BLOCK));
            breakEvent.setDropChance(1f);
        } else if (breakEvent.getState().getBlock() == Blocks.OBSIDIAN) {
            breakEvent.getDrops().clear();
            breakEvent.getDrops().add(CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.OBSIDIAN, 1, ModHeatedObjectTypes.BLOCK));
            breakEvent.setDropChance(1f);
        }
    }

    //@SubscribeEvent
    public static void onBlockPlaced(BlockEvent.PlaceEvent placeEvent) {
        if (placeEvent.getPlacedBlock().getBlock() == Blocks.IRON_BLOCK) {
            placeEvent.setCanceled(true);
            placeEvent.getWorld().setBlockState(placeEvent.getPos(), ModBlocks.BL_RESOURCE.getDefaultState());
            ModBlocks.BL_RESOURCE.onBlockPlacedBy(placeEvent.getWorld(),
                    placeEvent.getPos(),
                    placeEvent.getWorld().getBlockState(placeEvent.getPos()),
                    placeEvent.getPlayer(),
                    CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.IRON, 1, ModHeatedObjectTypes.BLOCK));
        } else if (placeEvent.getPlacedBlock().getBlock() == Blocks.GOLD_BLOCK) {
            placeEvent.setCanceled(true);
            placeEvent.getWorld().setBlockState(placeEvent.getPos(), ModBlocks.BL_RESOURCE.getDefaultState());
            ModBlocks.BL_RESOURCE.onBlockPlacedBy(placeEvent.getWorld(),
                    placeEvent.getPos(),
                    placeEvent.getWorld().getBlockState(placeEvent.getPos()),
                    placeEvent.getPlayer(),
                    CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.GOLD, 1, ModHeatedObjectTypes.BLOCK));
        } else if (placeEvent.getPlacedBlock().getBlock() == Blocks.OBSIDIAN) {
            placeEvent.setCanceled(true);
            placeEvent.getWorld().setBlockState(placeEvent.getPos(), ModBlocks.BL_RESOURCE.getDefaultState());
            ModBlocks.BL_RESOURCE.onBlockPlacedBy(placeEvent.getWorld(),
                    placeEvent.getPos(),
                    placeEvent.getWorld().getBlockState(placeEvent.getPos()),
                    placeEvent.getPlayer(),
                    CapabilityHelper.forceGenerateMaterializedStack(ModBlocks.BL_RESOURCE, ModMaterials.Armor.Core.OBSIDIAN, 1, ModHeatedObjectTypes.BLOCK));
        }
    }

}
