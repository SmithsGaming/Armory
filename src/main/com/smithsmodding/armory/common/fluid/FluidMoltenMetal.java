package com.smithsmodding.armory.common.fluid;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Marc on 19.12.2015.
 */
public class FluidMoltenMetal extends Fluid {

    public FluidMoltenMetal () {
        super(References.InternalNames.Fluids.MOLTENMETAL, new ResourceLocation(Textures.Blocks.LiquidMetalStill.getPrimaryLocation()), new ResourceLocation(Textures.Blocks.LiquidMetalFlow.getPrimaryLocation()));
    }

    @Override
    public int getColor (FluidStack stack) {
        if (stack == null || stack.tag == null)
            return getColor();

        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

        if (material == null)
            return getColor();

        return material.getRenderInfo().getLiquidColor().getRGB();
    }

    @Override
    public int getTemperature (FluidStack stack) {
        if (stack == null || stack.tag == null)
            return 20;

        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

        if (material == null)
            return 20;

        return (int) material.getMeltingPoint();
    }



    @Override
    public String getLocalizedName (FluidStack stack) {
        if (stack == null || stack.tag == null)
            return "Undefined molten Metal.";

        IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(stack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

        if (material == null)
            return "Undefined molten Metal.";

        return I18n.format(TranslationKeys.Fluids.MOLTEN) + " " + I18n.format(material.getTranslationKey());
    }
}
