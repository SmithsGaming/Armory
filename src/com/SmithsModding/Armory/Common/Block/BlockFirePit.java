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
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockFirePit extends Block {

    public BlockFirePit () {
        super(Material.iron);
        setUnlocalizedName(References.InternalNames.Blocks.FirePit);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer () {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
