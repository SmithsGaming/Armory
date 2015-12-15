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

import com.SmithsModding.Armory.Util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.model.obj.*;
import net.minecraftforge.common.property.*;

import java.util.*;

public class BlockFirePit extends Block {

    protected static Map<String, EnumFacing> directionsMapping = new HashMap<String, EnumFacing>();

    static {
        directionsMapping.put("NegX", EnumFacing.WEST);
        directionsMapping.put("PosX", EnumFacing.EAST);
        directionsMapping.put("NegY", EnumFacing.SOUTH);
        directionsMapping.put("PosY", EnumFacing.NORTH);
    }

    private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{OBJModel.OBJProperty.instance});

    public BlockFirePit () {
        super(Material.iron);
        setUnlocalizedName(References.InternalNames.Blocks.FirePit);
        setCreativeTab(CreativeTabs.tabCombat);
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

        /*
        OBJModel model = ((OBJModel.OBJBakedModel) Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(blockStack)).getModel();
        Map<String, OBJModel.Group> groups = model.getMatLib().getGroups();

        List<String> visibleParts = new ArrayList<String>();

        for (String name : groups.keySet())
        {
            boolean allSides = true;

            for(String direction : directionsMapping.keySet())
            {
                if (name.contains(direction) && allSides)
                {
                    if (!(world.getBlockState(new BlockPos(pos.add(directionsMapping.get(direction).getDirectionVec()))).getBlock() instanceof BlockFirePit))
                    {
                        allSides = false;
                    }
                }
            }

            if (allSides)
            {
                visibleParts.add(name);
            }
        }
        */

        OBJModel.OBJState retState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true);
        return ( (IExtendedBlockState) this.state.getBaseState() ).withProperty(OBJModel.OBJProperty.instance, retState);
    }
}
