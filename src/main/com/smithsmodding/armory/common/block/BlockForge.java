/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.block;
/*
/  BlockForge
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.block.ICustomDebugInformationBlock;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockForge extends BlockArmoryInventory implements ICustomDebugInformationBlock {

    public static final PropertyBool BURNING = PropertyBool.create("armory.burning");
    public static final PropertyBool ISMASTER = PropertyBool.create("armory.master");

    protected static Map<String, EnumFacing> directionsMapping = new HashMap<String, EnumFacing>();

    static {
        directionsMapping.put("NegX", EnumFacing.WEST);
        directionsMapping.put("PosX", EnumFacing.EAST);
        directionsMapping.put("NegY", EnumFacing.SOUTH);
        directionsMapping.put("PosY", EnumFacing.NORTH);
    }

    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{OBJModel.OBJProperty.INSTANCE, Properties.AnimationProperty});

    public BlockForge() {
        super(References.InternalNames.Blocks.Forge, Material.IRON);
        setCreativeTab(ModCreativeTabs.generalTab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false).withProperty(ISMASTER, false));
    }

    public static void setBurningState (boolean burning, World worldIn, BlockPos pos) {
        IBlockState original = worldIn.getBlockState(pos);

        if (original == null)
            original = ModBlocks.blockForge.getDefaultState();

        original = original.withProperty(BURNING, burning);

        worldIn.setBlockState(pos, original, 3);
    }

    public static void setMasterState (boolean isMaster, World worldIn, BlockPos pos) {
        IBlockState original = worldIn.getBlockState(pos);

        if (original == null)
            original = ModBlocks.blockForge.getDefaultState();

        original = original.withProperty(ISMASTER, isMaster);

        worldIn.setBlockState(pos, original, 3);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING, ISMASTER);
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        TileEntityForge forge = (TileEntityForge) worldIn.getTileEntity(pos);

        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityForge) {
                TileEntityForge tileEntityForge = (TileEntityForge) worldIn.getTileEntity(pos);

                tileEntityForge.getStructure().getController().onPartDestroyed(tileEntityForge);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityForge forge = (TileEntityForge) worldIn.getTileEntity(pos);

        if (stack.hasDisplayName()) {
            forge.setDisplayName(stack.getDisplayName());
        }

        if (!worldIn.isRemote) {
            if (forge instanceof TileEntityForge) {
                StructureRegistry.getInstance().onStructurePartPlaced(forge);

                worldIn.markChunkDirty(pos, forge);
            }
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque () {
        return false;
    }

    @Override
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {
        ItemStack blockStack = new ItemStack(Item.getItemFromBlock(this));

        OBJModel model = ((OBJModel.OBJBakedModel) Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(blockStack)).getModel();
        Map<String, OBJModel.Group> groups = model.getMatLib().getGroups();

        List<String> visibleParts = new ArrayList<String>();

        for (String key : groups.keySet())
        {
            //Internal OBJ Parts for the model can be auto added and skipped.
            if (key.startsWith("OBJModel"))
            {
                visibleParts.add(key);
                continue;
            }

            String[] data = key.split("_");

            if (data.length != 3)
            {
                ModLogger.getInstance().error("Could not map a ModelPart to the Forge Structure. Skipping.");
                continue;
            }


            ////The actual render data is stored in the name of the groups
            //Name of the group (with relevant sides)
            String name = data[0];
            //When to render: ALWAYS, When CONNECTED, When next to AIR
            String connectionType = data[1];
            //Check type: ALL side true, AtLeast One side True
            String renderCase = data[2];

            int relevantSides = 0;
            int requiredSides = 0;
            int foundSides = 0;

            Block targetBlock = this;

            for (String sideName : directionsMapping.keySet())
            {
                if (name.contains(sideName))
                    relevantSides++;
            }

            if (name.toLowerCase().equals("Fuel".toLowerCase())) {
                TileEntityForge forge = (TileEntityForge) world.getTileEntity(pos);

                if (forge == null)
                    continue;

                if (forge.getStructure() == null)
                    continue;

                if (!(forge.getStructure().getData().isBurning())) {
                    continue;
                }
            }

            if (connectionType.toLowerCase().contains("always"))
            {
                requiredSides = 0;
            }
            else if (connectionType.toLowerCase().contains("air"))
            {
                targetBlock = Blocks.AIR;
            }
            else if (connectionType.toLowerCase().contains("connected"))
            {
                targetBlock = this;
            }

            if (renderCase.toLowerCase().contains("all"))
            {
                requiredSides = relevantSides;
            }
            else if (renderCase.toLowerCase().contains("alo"))
            {
                requiredSides = 1;
            } else if (renderCase.toLowerCase().contains("not")) {
                requiredSides = 7;
            }

            if (requiredSides > 0)
            {
                for(String sideName : directionsMapping.keySet())
                {
                    if (name.contains(sideName))
                    {
                        Block comparisonBlock = world.getBlockState(pos.add(directionsMapping.get(sideName).getDirectionVec())).getBlock();

                        if (targetBlock == comparisonBlock)
                        {
                            foundSides ++;
                        }
                    }
                }
            }

            if (requiredSides == 0 || foundSides >= requiredSides)
            {
                visibleParts.add(key);
            }
        }

        OBJModel.OBJState retState = new OBJModel.OBJState(visibleParts, true);
        return ((IExtendedBlockState) this.state.getBaseState()).withProperty(Properties.AnimationProperty, retState);
    }

    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {
        return new TileEntityForge();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityForge) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.FORGEID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }

    /**
     * Convert the given metadata into a BlockState for this block
     */
    public IBlockState getStateFromMeta (int meta) {
        int burningMeta = meta / 2;
        int masterMeta = meta % 2;

        return this.getDefaultState().withProperty(BURNING, burningMeta >= 1).withProperty(ISMASTER, masterMeta >= 1);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState (IBlockState state) {
        boolean burningValue = state.getValue(BURNING);
        boolean masterValue = state.getValue(ISMASTER);

        int meta = 0;

        meta = ( masterValue ? 1 : 0 ) | ( burningValue ? 2 : 0 );

        return meta;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(BURNING, false);
    }

    /**
     * Method to handle displaying or removing of additional information on the F3 Screen.
     *
     * @param event   The event with the displayed data.
     * @param worldIn The world
     * @param pos     Position of the block the player is looking at.
     */
    @Override
    public void handleDebugInformation (RenderGameOverlayEvent.Text event, World worldIn, BlockPos pos) {
        if (!SmithsCore.isInDevenvironment() && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            return;

        TileEntityForge forge = (TileEntityForge) worldIn.getTileEntity(pos);

        if (forge.getStructure() == null)
            return;

        TextFormatting slaveCount;
        int count;
        if (!forge.getStructure().getMasterLocation().equals(forge.getLocation())) {
            slaveCount = TextFormatting.STRIKETHROUGH;
            count = -2;
        } else if (forge.getStructure().getPartLocations() == null) {
            slaveCount = TextFormatting.UNDERLINE;
            count = -1;
        } else if (forge.getStructure().getPartLocations().size() == 1) {
            slaveCount = TextFormatting.RED;
            count = 0;
        } else {
            slaveCount = TextFormatting.GREEN;
            count = forge.getStructure().getPartLocations().size() - 1;
        }

        TextFormatting masterTeLocation;
        String location;
        if (forge.getStructure().getMasterLocation().equals(forge.getLocation())) {
            masterTeLocation = TextFormatting.STRIKETHROUGH;
            location = "current";
        } else if (forge.getStructure().getMasterLocation() == null) {
            masterTeLocation = TextFormatting.RED;
            location = "unknown";
        } else {
            masterTeLocation = TextFormatting.GREEN;
            location = forge.getStructure().getMasterLocation().toString();
        }

        event.getRight().add("slave count:" + slaveCount + count + TextFormatting.RESET);
        event.getRight().add("masterlocation:" + masterTeLocation + location + TextFormatting.RESET);

    }
}
