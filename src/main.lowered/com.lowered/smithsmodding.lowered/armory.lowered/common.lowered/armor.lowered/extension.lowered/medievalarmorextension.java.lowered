package com.smithsmodding.armory.common.armor.extension;

import com.smithsmodding.armory.api.common.armor.IMaterializableMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.common.armor.callback.IDefaultCapabilitiesRetrievalCallback;
import com.smithsmodding.armory.api.common.armor.callback.IExtensionRecipeRetrievalCallback;
import com.smithsmodding.armory.api.common.capability.armor.ArmorCapabilityManager;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import com.smithsmodding.smithscore.util.common.IBuilder;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * Created by marcf on 1/21/2017.
 */
public class MedievalArmorExtension extends IForgeRegistryEntry.Impl<IMultiComponentArmorExtension> implements IMaterializableMultiComponentArmorExtension {

    private final String translationKey;
    private final String textFormatting;

    private final IMultiComponentArmorExtensionPosition position;
    private final Integer additionalDurability;
    private final ArmorCapabilityManager capabilityManager;

    private IMultiComponentArmor armor;
    private Boolean hasItemStack;

    private ModelRenderer modelRenderer;
    private ModelTransforms modelTransforms;
    private IExtensionRecipeRetrievalCallback recipeCallback;

    protected MedievalArmorExtension(Builder builder) {
        this.translationKey = builder.getTranslationKey();
        this.textFormatting = builder.getTextFormatting();
        this.position = builder.getPosition();
        this.additionalDurability = builder.getAdditionalDurability();
        this.capabilityManager = builder.getCapabilityManager();
        this.recipeCallback = builder.getExtensionRecipeRetrievalCallback();

        this.armor = null;
        this.hasItemStack = true;
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
     * Determines if this object has support for the capability in question on the specific side.
     * The return value of this MIGHT change during runtime if this object gains or looses support
     * for a capability.
     * <p>
     * Example:
     * A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     * <p>
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return false;
    }

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return The requested capability. Returns null when {@link #hasCapability(Capability, EnumFacing)} would return false.
     */
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return null;
    }

    /**
     * Getter for the MultiComponentArmor that this Extension can be installed on.
     *
     * @return The Armor that this Extension can be installed on.
     */
    @Nonnull
    @Override
    public IMultiComponentArmor getArmor() {
        return armor;
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
        this.armor = armor;
        return this;
    }

    /**
     * Getter for the MultiComponentArmorExtensionPosition that this Extension can be installed in.
     *
     * @return The Position that is Extension can be installed in.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtensionPosition getPosition() {
        return position;
    }

    /**
     * Method to getCreationRecipe the maximum count that a Extension can be installed on a ArmorInstance.
     *
     * @return the maximum count that a Extension can be installed on a ArmorInstance.
     */
    @Nonnull
    @Override
    public Integer getMaximalInstallationCount() {
        return 1;
    }

    /**
     * Method to getCreationRecipe the additional durability this extension provides.
     *
     * @return The extra durability.
     */
    @Nonnull
    @Override
    public Integer getAdditionalDurability() {
        return additionalDurability;
    }

    /**
     * Method to check weither a other Extension can be installed together with this extension.
     *
     * @param other            The other extension.
     * @param alreadyInstalled True when this extension is already installed on the armor, false when not.
     * @return True when allowed, false when not.
     */
    @Nonnull
    @Override
    public Boolean validateCrafting(IMultiComponentArmorExtension other, Boolean alreadyInstalled) {
        return !((this.getPosition().equals(other.getPosition())) && (!this.equals(other)));
    }

    /**
     * Method to check whether or not this Extension has an ItemStack.
     *
     * @return True when the a stack is buildable for this Extension. False when not.
     */
    @Nonnull
    @Override
    public Boolean hasItemStack() {
        return hasItemStack;
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
        this.hasItemStack = hasItemStack;
        return this;
    }

    /**
     * Method to getCreationRecipe all the default capabilities this Component provides.
     * The Capabilities stored here override those stored in the Armor and in its CoreMaterial.
     *
     * @return All the default capabilities this Component provides.
     */
    @Nonnull
    @Override
    public HashMap<Capability<? extends IArmorCapability>, Object> getDefaultComponentCapabilities() {
        return capabilityManager.getCapabilities();
    }

    /**
     * Method to getCreationRecipe the renderer that is used to render the Armor on the entity.
     *
     * @return The in world renderer.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ModelRenderer getRendererForArmor() {
        return modelRenderer;
    }

    /**
     * Method to getCreationRecipe the transforms for the in world rendering.
     *
     * @return The transforms for the in world rendering.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ModelTransforms getRenderTransforms() {
        return modelTransforms;
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
        this.modelRenderer = renderer;
        return this;
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
        this.modelTransforms = transforms;
        return null;
    }

    /**
     * Method used to retrieve tha recipe creation callback used during initialization to create recipes
     * of this extension and its subkinds if applicable. Method is only called when hasItemStack is true.
     * <p>
     * If the callback returns null on this getCreationRecipe method then no recipe will be registered.
     */
    @Nonnull
    @Override
    public IExtensionRecipeRetrievalCallback getRecipeCallback() {
        return recipeCallback;
    }

    public static class Builder implements IBuilder<MedievalArmorExtension> {
        private final String translationKey;
        private final String textFormatting;

        private final IMultiComponentArmorExtensionPosition position;
        private final Integer additionalDurability;
        private final ArmorCapabilityManager capabilityManager;
        private final IExtensionRecipeRetrievalCallback extensionRecipeRetrievalCallback;

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, IDefaultCapabilitiesRetrievalCallback capabilitiesRetrievalCallback, IExtensionRecipeRetrievalCallback extensionRecipeRetrievalCallback) {
            this(translationKey, textFormatting, position, additionalDurability, capabilitiesRetrievalCallback.get(), extensionRecipeRetrievalCallback);
        }

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, HashMap<Capability<? extends IArmorCapability>, Object> defaultCapabilities, IExtensionRecipeRetrievalCallback extensionRecipeRetrievalCallback) {
            this(translationKey, textFormatting, position, additionalDurability, new ArmorCapabilityManager(defaultCapabilities), extensionRecipeRetrievalCallback);
        }

        public Builder(String translationKey, String textFormatting, IMultiComponentArmorExtensionPosition position, Integer additionalDurability, ArmorCapabilityManager capabilityManager, IExtensionRecipeRetrievalCallback extensionRecipeRetrievalCallback) {
            this.translationKey = translationKey;
            this.textFormatting = textFormatting;
            this.position = position;
            this.additionalDurability = additionalDurability;
            this.capabilityManager = capabilityManager;
            this.extensionRecipeRetrievalCallback = extensionRecipeRetrievalCallback;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public String getTextFormatting() {
            return textFormatting;
        }

        public IMultiComponentArmorExtensionPosition getPosition() {
            return position;
        }

        public Integer getAdditionalDurability() {
            return additionalDurability;
        }

        public ArmorCapabilityManager getCapabilityManager() {
            return capabilityManager;
        }

        public IExtensionRecipeRetrievalCallback getExtensionRecipeRetrievalCallback() {
            return extensionRecipeRetrievalCallback;
        }

        /**
         * Method to complete the building process of T
         *
         * @return A completed instance of T
         */
        @Override
        public MedievalArmorExtension build() {
            return new MedievalArmorExtension(this);
        }
    }
}
