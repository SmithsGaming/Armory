package com.smithsmodding.armory.api.common.fluid;

import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 19.12.2015.
 */
public class FluidMoltenMetal extends Fluid {

    @Nonnull
    private final IMaterial material;

    public FluidMoltenMetal(@Nonnull IMaterial material) {
        super(material.getRegistryName().toString().toLowerCase(), new ResourceLocation(Textures.Blocks.LiquidMetalStill.getPrimaryLocation()), new ResourceLocation(Textures.Blocks.LiquidMetalFlow.getPrimaryLocation()));
        this.material = material;
    }

    @Override
    public int getColor(@Nullable FluidStack stack) {
        return material.getRenderInfo().getLiquidColor().getRGB();
    }

    @Override
    public int getTemperature(@Nullable FluidStack stack) {
        return Math.round(material.getMeltingPoint());
    }


    @Nonnull
    @Override
    public String getLocalizedName(@Nullable FluidStack stack) {
        return I18n.format(TranslationKeys.Fluids.MOLTEN) + " " + I18n.format(material.getTranslationKey());
    }
}
