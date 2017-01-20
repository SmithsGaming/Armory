package com.smithsmodding.armory.common.material;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/14/2017.
 */
public class StandardArmorMaterial extends IForgeRegistryEntry.Impl<ICoreArmorMaterial> implements ICoreArmorMaterial {

    /**
     * Method to get the translation Key.
     *
     * @return The key to translate.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Method to get the markup.
     *
     * @return The markup. Default is TextFormatting.Reset
     */
    @Nonnull
    @Override
    public String getTextFormatting() {
        return textFormatting;
    }

    /**
     * Getter for the RenderInfo of the current Provider.
     *
     * @return The current renderinfo.
     * @implNote This method only exist on the Client Side.
     */
    @Nonnull
    @Override
    public ITextureController getRenderInfo() {
        return renderInfo;
    }

    /**
     * Setter for the RenderInfo of the current Provider.
     *
     * @param renderInfo The new renderinfo.
     * @implNote This method only exists on the Client Side.
     */
    @Override
    public ICoreArmorMaterial setRenderInfo(@Nonnull ITextureController renderInfo) {
        this.renderInfo = renderInfo;
        return this;
    }

    /**
     * Method to get the Override for the texture.
     * Has to be supplied so that resourcepack makers can override the behaviour if they fell the need to do it.
     *
     * @return The override identifier for overloading the programmatic behaviour of the RenderInfo.
     * @implNote This method only exists on the Client Side.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public String getTextureOverrideIdentifier() {
        return textureOverrideIdentifier;
    }

    /**
     * Method to get the Identifier inside the OreDictionary for a Material.
     *
     * @return The String that identifies this material in the OreDictionary. IE: Iron, Stone etc.
     */
    @Nullable
    @Override
    public String getOreDictionaryIdentifier() {
        return oreDictionaryIdentifier;
    }

    /**
     * Getter for the Melting Point of this material.
     *
     * @return The melting point of this material.
     */
    @Nonnull
    @Override
    public Float getMeltingPoint() {
        return meltingPoint;
    }

    /**
     * Getter for the VaporizingPoint of this material.
     *
     * @return The vaporizing point og this material.
     */
    @Nonnull
    @Override
    public Float getVaporizingPoint() {
        return vaporizingPoint;
    }

    /**
     * Method to get the BaseDurability of a piece of armor made out of this material.
     *
     * @param armor The armor to get the base durability for.
     * @return The durability of a piece of armor made out of this material.
     */
    @Nonnull
    @Override
    public Integer getBaseDurabilityForArmor(@Nonnull IMultiComponentArmor armor) {

    }
}
