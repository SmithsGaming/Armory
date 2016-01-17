package com.smithsmodding.Armory.Common.Fluid;

import com.smithsmodding.Armory.API.Materials.*;
import com.smithsmodding.Armory.Common.Material.*;
import com.smithsmodding.Armory.Util.Client.*;
import com.smithsmodding.Armory.Util.*;
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
