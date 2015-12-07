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
    String getUniqueID ();

    /**
     * Indicates if this layer is depending on a material or not.
     *
     * @return True when this layer is Material dependent, false when not.
     */
    boolean isMaterialDependent ();

    /**
     * Method to get the texture location of the item when the Armor is not broken.
     *
     * @return The texture location of the item when the Armor is not broken.
     */
    @SideOnly(Side.CLIENT)
    ResourceLocation getItemWholeTextureLocation ();

    /**
     * Method to get the texture location of the item when the Armor is broken.
     *
     * @return The texture location of the item when the Armor is broken.
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
