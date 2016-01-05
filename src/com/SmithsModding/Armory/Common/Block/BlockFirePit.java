/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Block;
/*
/  BlockFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Registry.*;
import com.SmithsModding.Armory.Common.TileEntity.State.*;
import com.SmithsModding.Armory.Common.TileEntity.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Client.Block.*;
import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.property.*;

import java.util.*;

public class BlockFirePit extends BlockArmoryInventory implements ICustomDebugInformationBlock {

    public static final PropertyBool BURNING = PropertyBool.create("burning");
    public static final PropertyBool ISMASTER = PropertyBool.create("master");

    protected static Map<String, EnumFacing> directionsMapping = new HashMap<String, EnumFacing>();

    static {
        directionsMapping.put("NegX", EnumFacing.WEST);
        directionsMapping.put("PosX", EnumFacing.EAST);
        directionsMapping.put("NegY", EnumFacing.SOUTH);
        directionsMapping.put("PosY", EnumFacing.NORTH);
    }

    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{OBJModel.OBJProperty.instance});

    public BlockFirePit () {
        super(References.InternalNames.Blocks.FirePit, Material.iron);
        setCreativeTab(CreativeTabs.tabCombat);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false).withProperty(ISMASTER, false));
    }

    public static void setBurningState (boolean burning, World worldIn, BlockPos pos) {
        IBlockState original = worldIn.getBlockState(pos);

        if (original == null)
            original = GeneralRegistry.Blocks.blockFirePit.getDefaultState();

        original = original.withProperty(BURNING, burning);

        worldIn.setBlockState(pos, original, 3);
    }

    public static void setMasterState (boolean isMaster, World worldIn, BlockPos pos) {
        IBlockState original = worldIn.getBlockState(pos);

        if (original == null)
            original = GeneralRegistry.Blocks.blockFirePit.getDefaultState();

        original = original.withProperty(ISMASTER, isMaster);

        worldIn.setBlockState(pos, original, 3);
    }

    @Override
    protected BlockState createBlockState () {
        return new BlockState(this, BURNING, ISMASTER);
    }
    
    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        TileEntityFirePit tTEFirePit = (TileEntityFirePit) worldIn.getTileEntity(pos);

        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityFirePit) {
                StructureManager.destroyStructureComponent(tTEFirePit);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityFirePit tTE = (TileEntityFirePit) worldIn.getTileEntity(pos);

        if (stack.hasDisplayName()) {
            tTE.setDisplayName(stack.getDisplayName());
        }

        if (!worldIn.isRemote) {
            if (tTE instanceof TileEntityFirePit) {
                StructureManager.createStructureComponent(tTE);
                worldIn.markBlockForUpdate(pos);
            }
        }
    }

    @Override
    public boolean canRenderInLayer (EnumWorldBlockLayer layer) {
        return layer == EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent () {
        return true;
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public boolean isFullCube () {
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
                Armory.getLogger().error("Could not map a ModelPart to the FirePit Structure. Skipping.");
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

            Class targetComparison = this.getClass();

            for (String sideName : directionsMapping.keySet())
            {
                if (name.contains(sideName))
                    relevantSides++;
            }

            if (name.toLowerCase().equals("Fuel".toLowerCase())) {
                TileEntityFirePit tileEntityFirePit = (TileEntityFirePit) world.getTileEntity(pos);

                if (tileEntityFirePit == null)
                    continue;

                if (tileEntityFirePit.getStructureData() == null || !( (FirePitState) tileEntityFirePit.getStructureData() ).isBurning()) {
                    continue;
                }
            }

            if (connectionType.toLowerCase().contains("always"))
            {
                requiredSides = 0;
            }
            else if (connectionType.toLowerCase().contains("air"))
            {
                targetComparison = BlockAir.class;
            }
            else if (connectionType.toLowerCase().contains("connected"))
            {
                targetComparison = this.getClass();
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

                        if (targetComparison.isAssignableFrom(comparisonBlock.getClass()))
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
        return ( (IExtendedBlockState) this.state.getBaseState() ).withProperty(OBJModel.OBJProperty.instance, retState);
    }

    @Override
    public TileEntity createNewTileEntity (World worldIn, int meta) {
        return new TileEntityFirePit();
    }

    @Override
    public int getRenderType () {
        return 3;
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return false;
        } else {
            if (!worldIn.isRemote) {
                if (worldIn.getTileEntity(pos) instanceof TileEntityFirePit) {
                    playerIn.openGui(Armory.instance, References.GuiIDs.FIREPITID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
    }


    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta (int meta) {
        int burningMeta = meta / 2;
        int masterMeta = meta % 2;

        return this.getDefaultState().withProperty(BURNING, burningMeta >= 1 ? true : false).withProperty(ISMASTER, masterMeta >= 1 ? true : false);
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

        TileEntityFirePit tileEntityFirePit = (TileEntityFirePit) worldIn.getTileEntity(pos);

        if (tileEntityFirePit.getStructureData() == null)
            return;

        Float burningTicks = (Float) tileEntityFirePit.getStructureData().getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);
        Float totalBurningTicks = (Float) tileEntityFirePit.getStructureData().getData(tileEntityFirePit, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);

        Float currentTemp = ( (FirePitState) tileEntityFirePit.getState() ).getCurrentTemperature();
        Float maxTemp = ( (FirePitState) tileEntityFirePit.getState() ).getMaxTemperature();

        float burningFormattingType = burningTicks / ( totalBurningTicks / 3 );
        float tempFormattingType = currentTemp / ( maxTemp / 3 );

        EnumChatFormatting burningTime;
        EnumChatFormatting temp;

        if (burningFormattingType < 1F && burningFormattingType > 0F)
            burningTime = EnumChatFormatting.RED;
        else if (burningFormattingType < 2F && burningFormattingType > 0F)
            burningTime = EnumChatFormatting.GOLD;
        else if (burningFormattingType <= 3f && burningFormattingType > 0F)
            burningTime = EnumChatFormatting.DARK_BLUE;
        else
            burningTime = EnumChatFormatting.RESET;

        if (tempFormattingType < 1F && tempFormattingType > 0F)
            temp = EnumChatFormatting.DARK_BLUE;
        else if (tempFormattingType < 2F && tempFormattingType > 0F)
            temp = EnumChatFormatting.GOLD;
        else if (tempFormattingType <= 3f && tempFormattingType > 0F)
            temp = EnumChatFormatting.RED;
        else
            temp = EnumChatFormatting.RESET;

        event.right.add("burning tick left:" + burningTime + burningTicks + EnumChatFormatting.RESET);
        event.right.add("temp:" + temp + currentTemp + EnumChatFormatting.RESET);
        event.right.add("molten metal count:" + tileEntityFirePit.getAllFluids().size());

        EnumChatFormatting slaveCount;
        int count;
        if (tileEntityFirePit.isSlaved()) {
            slaveCount = EnumChatFormatting.STRIKETHROUGH;
            count = -2;
        } else if (tileEntityFirePit.getSlaveCoordinates() == null) {
            slaveCount = EnumChatFormatting.UNDERLINE;
            count = -1;
        } else if (tileEntityFirePit.getSlaveCoordinates().size() == 0) {
            slaveCount = EnumChatFormatting.RED;
            count = 0;
        } else {
            slaveCount = EnumChatFormatting.GREEN;
            count = tileEntityFirePit.getSlaveCoordinates().size();
        }

        EnumChatFormatting masterTeLocation;
        String location;
        if (!tileEntityFirePit.isSlaved()) {
            masterTeLocation = EnumChatFormatting.STRIKETHROUGH;
            location = "current";
        } else if (tileEntityFirePit.getMasterLocation() == null) {
            masterTeLocation = EnumChatFormatting.RED;
            location = "unknown";
        } else {
            masterTeLocation = EnumChatFormatting.GREEN;
            location = tileEntityFirePit.getMasterLocation().toString();
        }

        event.right.add("slave count:" + slaveCount + count + EnumChatFormatting.RESET);
        event.right.add("masterlocation:" + masterTeLocation + location + EnumChatFormatting.RESET);

    }
}
