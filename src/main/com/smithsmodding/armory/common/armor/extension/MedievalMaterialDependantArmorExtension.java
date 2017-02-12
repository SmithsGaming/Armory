package com.smithsmodding.armory.common.armor.extension;

import com.smithsmodding.armory.api.common.armor.*;
import com.smithsmodding.armory.api.common.armor.callback.IDefaultCapabilitiesRetrievalCallback;
import com.smithsmodding.armory.api.common.capability.armor.ArmorCapabilityManager;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/22/2017.
 */
public class MedievalMaterialDependantArmorExtension extends MedievalArmorExtension implements IMaterialDependantMultiComponentArmorExtension {

    private final IMaterializableMultiComponentArmorExtension materialIndependentExtension;
    private final IAddonArmorMaterial addonMaterial;

    private MedievalMaterialDependantArmorExtension(Builder builder) {
        super(builder);

        this.materialIndependentExtension = (IMaterializableMultiComponentArmorExtension) builder.getMaterialIndependentArmorExtension().setHasItemStack(false);
        this.addonMaterial = builder.getMaterial();
    }

    /**
     * Getter for the MultiComponentArmor that this Extension can be installed on.
     *
     * @return The Armor that this Extension can be installed on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmor getArmor() {
        return getMaterialIndependentExtension().getArmor();
    }

    /**
     * Getter for the material independent extension.
     *
     * @return The material independent extension.
     */
    @Nonnull
    @Override
    public IMaterializableMultiComponentArmorExtension getMaterialIndependentExtension() {
        return materialIndependentExtension;
    }

    /**
     * Setter for the MultiComponentArmor that this Extension can be installed on.
     *
     * @param armor The new Armor type this Extension can be installed on.
     * @return The instance this method was called on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtension setArmor(@Nonnull IMultiComponentArmor armor) {
        return super.setArmor(armor);
    }

    /**
     * Setter for the hasItemStack property of this Extension.
     *
     * @param hasItemStack True when a stack is buildable for this extension. False when not.
     * @return The instance this method was called on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtension setHasItemStack(@Nonnull Boolean hasItemStack) {
        return super.setHasItemStack(hasItemStack);
    }

    /**
     * Method to set the renderer of this Renderable.
     *
     * @param renderer The new renderer of this Renderable
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public IMultiComponentArmorExtension setRendererForArmor(@Nonnull ModelRenderer renderer) {
        return super.setRendererForArmor(renderer);
    }

    /**
     * Method to set the transforms of this Renderable
     *
     * @param transforms The new transforms of this Renderable.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public IMultiComponentArmorExtension setRenderTransforms(@Nonnull ModelTransforms transforms) {
        return super.setRenderTransforms(transforms);
    }

    /**
     * @return
     */
    @Nonnull
    @Override
    public IAddonArmorMaterial getMaterial() {
        return addonMaterial;
    }

    public static class Builder extends MedievalArmorExtension.Builder {

        private final IMaterializableMultiComponentArmorExtension materialIndependentArmorExtension;
        private final IAddonArmorMaterial material;

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, IMaterializableMultiComponentArmorExtension materialIndependentArmorExtension, IAddonArmorMaterial material, IDefaultCapabilitiesRetrievalCallback capabilitiesRetrievalCallback) {
            this(translationKey, textFormatting, position, additionalDurability, materialIndependentArmorExtension, material, capabilitiesRetrievalCallback.get());
        }

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, IMaterializableMultiComponentArmorExtension materialIndependentArmorExtension, IAddonArmorMaterial material, HashMap<Capability<? extends IArmorCapability>, Object> defaultCapabilities) {
            this(translationKey, textFormatting, position, additionalDurability, materialIndependentArmorExtension, material, new ArmorCapabilityManager(defaultCapabilities));
        }

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, IMaterializableMultiComponentArmorExtension materialIndependentArmorExtension, IAddonArmorMaterial material, ArmorCapabilityManager capabilityManager) {
            super(translationKey, textFormatting, position, additionalDurability, capabilityManager, materialIndependentArmorExtension.getRecipeCallback());

            this.materialIndependentArmorExtension = materialIndependentArmorExtension;
            this.material = material;
        }

        public IMaterializableMultiComponentArmorExtension getMaterialIndependentArmorExtension() {
            return materialIndependentArmorExtension;
        }

        public IAddonArmorMaterial getMaterial() {
            return material;
        }

        /**
         * Method to complete the building process of T
         *
         * @return A completed instance of T
         */
        @Override
        public MedievalMaterialDependantArmorExtension build() {
            return new MedievalMaterialDependantArmorExtension(this);
        }
    }
}
