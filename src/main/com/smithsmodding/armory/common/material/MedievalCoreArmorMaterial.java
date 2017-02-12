package com.smithsmodding.armory.common.material;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by marcf on 1/2/2017.
 */
public class MedievalCoreArmorMaterial extends ICoreArmorMaterial.Impl<ICoreArmorMaterial> implements ICoreArmorMaterial {

    /**
     * Method to get the translation Key.
     *
     * @return The key to translate.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return null;
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
        return null;
    }

    /**
     * Setter for the RenderInfo of the current Provider.
     *
     * @param renderInfo The new renderinfo.
     * @implNote This method only exists on the Client Side.
     */
    @Override
    public ICoreArmorMaterial setRenderInfo(@Nonnull ITextureController renderInfo) {
        return null;
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
    public String getTextureOverrideIdentifier() {
        return null;
    }

    /**
     * Method to get the Identifier inside the OreDictionary for a Material.
     *
     * @return The String that identifies this material in the OreDictionary. IE: Iron, Stone etc.
     */
    @Nullable
    @Override
    public String getOreDictionaryIdentifier() {
        return null;
    }

    /**
     * Getter for the Melting Point of this material.
     *
     * @return The melting point of this material.
     */
    @Nonnull
    @Override
    public Float getMeltingPoint() {
        return null;
    }

    /**
     * Getter for the VaporizingPoint of this material.
     *
     * @return The vaporizing point og this material.
     */
    @Nonnull
    @Override
    public Float getVaporizingPoint() {
        return null;
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
        return null;
    }

    /**
     * Method to get all the default capabilities this ArmorMaterial provides.
     *
     * @param armor
     * @return All the default capabilities this ArmorMaterial provides.
     */
    @Nonnull
    @Override
    public HashMap<Capability<? extends IArmorCapability>, Object> getOverrideCoreMaterialCapabilities(IMultiComponentArmor armor) {
        return null;
    }
}
