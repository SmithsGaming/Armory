package com.smithsmodding.armory.api.armor;

import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by Marc on 06.12.2015.
 */
public interface ILayeredArmorLayer {

    /**
     * Function to get the internal name
     *
     * @return The internal name of the Layer
     */
    String getUniqueID ();

    /**
     * Indicates if this layer is depending on a material or not.
     *
     * @return True when this layer is material dependent, false when not.
     */
    boolean isMaterialDependent ();

    /**
     * Method to get the texture location of the item when the armor is not broken.
     *
     * @return The texture location of the item when the armor is not broken.
     */
    @SideOnly(Side.CLIENT)
    ResourceLocation getItemWholeTextureLocation ();

    /**
     * Method to get the texture location of the item when the armor is broken.
     *
     * @return The texture location of the item when the armor is broken.
     */
    @SideOnly(Side.CLIENT)
    ResourceLocation getItemBrokenTextureLocation ();

    /**
     * Method to get the texture location when the item is worn and it is rendered on the character.
     *
     * @return The texture location when the item is worn and it is rendered on the character.
     */
    @SideOnly(Side.CLIENT)
    ResourceLocation getModelTextureLocation ();

}
