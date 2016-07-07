package com.smithsmodding.armory.common.fluid;

import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

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

        return I18n.format(TranslationKeys.Fluids.MOLTEN) + " " + material.getNameColor() + I18n.format(material.getTranslationKey()) + TextFormatting.RESET;
    }
}
