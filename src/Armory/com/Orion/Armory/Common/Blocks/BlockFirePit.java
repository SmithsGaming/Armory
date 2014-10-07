package com.Orion.Armory.Common.Blocks;
/*
/  BlockFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Logic.Multiblock.IMultiBlockPart;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.References;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFirePit extends BlockContainer implements IMultiBlockPart
{
    protected String iInternalName = References.InternalNames.Blocks.FirePit;

    protected BlockFirePit() {
        super(Material.fire);
    }

    @Override
    public String getInternalName() {
        return this.iInternalName;
    }

    @Override
    public boolean validatePart(Integer pXCoord, Integer pYCoord, Integer pZCoord, World pWorld) {
        if (pWorld.getBlock(pXCoord, pYCoord, pZCoord) == this)
        {
            return true;
        }

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityFirePit();
    }
}
