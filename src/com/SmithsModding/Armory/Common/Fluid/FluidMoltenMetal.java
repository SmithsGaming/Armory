package com.SmithsModding.Armory.Common.Fluid;

import com.SmithsModding.Armory.Common.Material.*;
import com.SmithsModding.Armory.Util.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;

/**
 * Created by Marc on 19.12.2015.
 */
public class FluidMoltenMetal extends Fluid {

    public FluidMoltenMetal () {

        //TODO: Add metal Icons.
        super(References.InternalNames.Fluids.MOLTENMETAL, new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"));
    }

    @Override
    public int getColor (FluidStack stack) {
        return MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL)).getRenderInfo().getVertexColor().getRGB();
    }

}
