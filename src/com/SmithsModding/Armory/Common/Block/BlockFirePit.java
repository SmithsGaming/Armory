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
import com.SmithsModding.Armory.Common.TileEntity.State.*;
import com.SmithsModding.Armory.Common.TileEntity.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Common.Structures.*;
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
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.property.*;

import java.util.*;

public class BlockFirePit extends BlockArmoryInventory {

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
        //OBJModel.OBJState retState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true);
        //return ( (IExtendedBlockState) this.state.getBaseState() ).withProperty(OBJModel.OBJProperty.instance, retState);


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

                if (!( (FirePitState) ( ( (TileEntityFirePit) tileEntityFirePit.getMasterEntity() ).getState() ) ).isBurning()) {
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
}
