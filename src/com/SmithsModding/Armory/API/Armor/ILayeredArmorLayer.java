package com.SmithsModding.Armory.API.Armor;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Marc on 06.12.2015.
 */
public interface ILayeredArmorLayer {

    /**
     * Function to get the internal name
     *
     * @return The internal name of the Layer
     */
    String getInternalName ();

    /**
     * Indicates if this layer is depending on a material or not.
     *
     * @return True when this layer is Material dependent, false when not.
     */
    boolean isMaterialDependent ();

    @SideOnly(Side.CLIENT)
    ResourceLocation getModelTextureLocation ();

    @SideOnly(Side.CLIENT)
    ResourceLocation getModelBrokenTextureLocation ();

}
