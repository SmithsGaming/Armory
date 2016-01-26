package com.smithsmodding.armory.common.fluid;

import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.armory.util.client.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;

/**
 * Created by Marc on 19.12.2015.
 */
public class FluidMoltenMetal extends Fluid {

    public FluidMoltenMetal () {

        //TODO: Add metal Icons.
        super(References.InternalNames.Fluids.MOLTENMETAL, new ResourceLocation(Textures.Blocks.LiquidMetalStill.getPrimaryLocation()), new ResourceLocation(Textures.Blocks.LiquidMetalFlow.getPrimaryLocation()));
    }

    @Override
    public int getColor (FluidStack stack) {
        return MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL)).getRenderInfo().getLiquidColor().getRGB();
    }

    @Override
    public int getTemperature (FluidStack stack) {
        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

        return (int) material.getMeltingPoint();
    }

    @Override
    public String getLocalizedName (FluidStack stack) {
        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

        return StatCollector.translateToLocal(TranslationKeys.Fluids.MOLTEN) + " " + material.getNameColor() + StatCollector.translateToLocal(material.getTranslationKey()) + EnumChatFormatting.RESET;
    }
}
