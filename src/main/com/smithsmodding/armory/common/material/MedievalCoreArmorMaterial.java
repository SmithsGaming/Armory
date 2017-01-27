package com.smithsmodding.armory.common.material;

import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/2/2017.
 */
public abstract class MedievalCoreArmorMaterial extends ICoreArmorMaterial.Impl<ICoreArmorMaterial> implements ICoreArmorMaterial {

    private final String translationKey;
    private final String textFormatting;
    private final String oreDictionaryIdentifier;

    private final Float meltingPoint;
    private final Float vaporizingPoint;
    private final Integer meltingTime;
    private final Integer vaporizingTime;
    private final Float heatCoefficient;

    private final String textureOverrideIdentifier;

    private ITextureController renderInfo;

    public MedievalCoreArmorMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient) {
        this.translationKey = translationKey;
        this.textFormatting = textFormatting;
        this.oreDictionaryIdentifier = oreDictionaryIdentifier;
        this.meltingPoint = meltingPoint;
        this.vaporizingPoint = vaporizingPoint;
        this.meltingTime = meltingTime;
        this.vaporizingTime = vaporizingTime;
        this.heatCoefficient = heatCoefficient;

        this.textureOverrideIdentifier = oreDictionaryIdentifier;
    }


    /**
     * Method to getCreationRecipe the translation Key.
     *
     * @return The key to translate.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Method to getCreationRecipe the markup.
     *
     * @return The markup. Default is TextFormatting.Reset
     */
    @Nonnull
    @Override
    public String getTextFormatting() {
        return textFormatting;
    }

    /**
     * Method to getCreationRecipe the Identifier inside the OreDictionary for a Material.
     *
     * @return The String that identifies this material in the OreDictionary. IE: TK_ANVIL_IRON, TK_ANVIL_STONE etc.
     */
    @Nullable
    @Override
    public String getOreDictionaryIdentifier() {
        return oreDictionaryIdentifier;
    }

    /**
     * Getter for the RenderInfo of the current Provider.
     *
     * @return The current renderinfo.
     * @implNote This method only exist on the Client Side.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
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
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ICoreArmorMaterial setRenderInfo(@Nonnull ITextureController renderInfo) {
        this.renderInfo = renderInfo;
        return this;
    }

    /**
     * Method to getCreationRecipe the Override for the texture.
     * Has to be supplied so that resourcepack makers can override the behaviour if they fell the need to do it.
     *
     * @return The override identifier for overloading the programmatic behaviour of the RenderInfo.
     * @implNote This method only exists on the Client Side.
     */
    @Nonnull
    @Override
    public String getTextureOverrideIdentifier() {
        return textureOverrideIdentifier;
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
     * Getter for the melting time of this Material
     *
     * @return The melting time of this material
     */
    @Nonnull
    @Override
    public Integer getMeltingTime() {
        return meltingTime;
    }

    /**
     * Getter for the vaporizing time of this Material
     *
     * @return The vaporizing time of this material
     */
    @Nonnull
    @Override
    public Integer getVaporizingTime() {
        return vaporizingTime;
    }

    @Nonnull
    @Override
    public Float getHeatCoefficient() {
        return heatCoefficient;
    }
}
