package com.smithsmodding.armory.common.armor;

import com.smithsmodding.armory.api.common.armor.callback.IDefaultCapabilitiesRetrievalCallback;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.common.capability.armor.ArmorCapabilityManager;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import com.smithsmodding.smithscore.util.common.IBuilder;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;


/**
 * Created by marcf on 1/21/2017.
 */
public class MedievalArmor extends IForgeRegistryEntry.Impl<IMultiComponentArmor> implements IMultiComponentArmor {


    private final String translationKey;
    private final String textFormatting;
    private final Integer defaultDurability;
    private final Item item;
    private final Integer equipmentSlot;
    private final List<IMultiComponentArmorExtensionPosition> possibleExtensionPositions;
    private final List<IMultiComponentArmorExtension> possibleExtensions;
    private final ArmorCapabilityManager capabilityManager;
    private ModelRenderer modelRenderer;
    private ModelTransforms modelTransforms;

    private MedievalArmor(Builder builder) {
        this.translationKey = builder.getTranslationKey();
        this.textFormatting = builder.getTextFormatting();
        this.defaultDurability = builder.getDefaultDurability();
        this.item = builder.getItem();
        this.equipmentSlot = builder.getEquipmentSlot();
        this.possibleExtensionPositions = builder.getPossibleExtensionPositions();
        this.possibleExtensions = builder.getPossibleExtensions();
        this.capabilityManager = builder.getManager();

        for (IMultiComponentArmorExtension extension : possibleExtensions) {
            extension.setArmor(this);
        }
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
     * Method to getCreationRecipe the default durability before material properties and upgrades are taken into account.
     *
     * @return The default durability.
     */
    @Nonnull
    @Override
    public Integer getDefaultDurability() {
        return defaultDurability;
    }

    /**
     * Method to getCreationRecipe all possible extension positions for this Armor.
     *
     * @return The positions on the armor where an extension can be installed.
     */
    @Nonnull
    @Override
    public List<IMultiComponentArmorExtensionPosition> getPossibleExtensionPositions() {
        return possibleExtensionPositions;
    }

    /**
     * Method to getCreationRecipe all possible extensions for this armor.
     *
     * @return The extensions that can be installed on this armor.
     */
    @Nonnull
    @Override
    public List<IMultiComponentArmorExtension> getPossibleExtensions() {
        return possibleExtensions;
    }

    /**
     * Method to getCreationRecipe all the default capabilities this Armor provides.
     *
     * @return All the default capabilities this Armor provides.
     */
    @Nonnull
    @Override
    public HashMap<Capability<? extends IArmorCapability>, Object> getDefaultArmorCapabilities() {
        return capabilityManager.getCapabilities();
    }

    /**
     * Method to get the Item for the Armor.
     *
     * @return The Item for the Armor.
     */
    @Override
    public Item getItem() {
        return item;
    }

    /**
     * The index of the Equipmentslot.
     * 0-5 Are reserved for the normal Vanilla equipment slots.
     *
     * @return The equipmentslot.
     */
    @Override
    public Integer getEquipmentSlotIndex() {
        return equipmentSlot;
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
        return capabilityManager.hasCapability(capability, facing);
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
        return capabilityManager.getCapability(capability, facing);
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
    public Object setRendererForArmor(@Nonnull ModelRenderer renderer) {
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
    public Object setRenderTransforms(@Nonnull ModelTransforms transforms) {
        this.modelTransforms = transforms;
        return this;
    }

    public static class Builder implements IBuilder<MedievalArmor> {
        private final String translationKey;
        private final String textFormatting;
        private final Integer defaultDurability;
        private final Item item;
        private final Integer equipmentSlot;
        private final List<IMultiComponentArmorExtensionPosition> possibleExtensionPositions;
        private final List<IMultiComponentArmorExtension> possibleExtensions;
        private final ArmorCapabilityManager manager;

        public Builder(String translationKey, String textFormatting, Integer defaultDurability, Item item, Integer equipmentSlot, List<IMultiComponentArmorExtensionPosition> possibleExtensionPositions, List<IMultiComponentArmorExtension> posibleExtensions, IDefaultCapabilitiesRetrievalCallback callback) {
            this(translationKey, textFormatting, defaultDurability, item, equipmentSlot, possibleExtensionPositions, posibleExtensions, callback.get());
        }

        public Builder(String translationKey, String textFormatting, Integer defaultDurability, Item item, Integer equipmentSlot, List<IMultiComponentArmorExtensionPosition> possibleExtensionPositions, List<IMultiComponentArmorExtension> posibleExtensions, HashMap<Capability<? extends IArmorCapability>, Object> defaultCapabilities) {
            this(translationKey, textFormatting, defaultDurability, item, equipmentSlot, possibleExtensionPositions, posibleExtensions, new ArmorCapabilityManager(defaultCapabilities));
        }

        public Builder(String translationKey, String textFormatting, Integer defaultDurability, Item item, Integer equipmentSlot, List<IMultiComponentArmorExtensionPosition> possibleExtensionPositions, List<IMultiComponentArmorExtension> posibleExtensions, ArmorCapabilityManager manager) {
            this.translationKey = translationKey;
            this.textFormatting = textFormatting;
            this.defaultDurability = defaultDurability;
            this.item = item;
            this.equipmentSlot = equipmentSlot;
            this.possibleExtensionPositions = possibleExtensionPositions;
            this.possibleExtensions = posibleExtensions;
            this.manager = manager;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public String getTextFormatting() {
            return textFormatting;
        }

        public Integer getDefaultDurability() {
            return defaultDurability;
        }

        public Item getItem() {
            return item;
        }

        public Integer getEquipmentSlot() {
            return equipmentSlot;
        }

        public List<IMultiComponentArmorExtensionPosition> getPossibleExtensionPositions() {
            return possibleExtensionPositions;
        }

        public List<IMultiComponentArmorExtension> getPossibleExtensions() {
            return possibleExtensions;
        }

        public ArmorCapabilityManager getManager() {
            return manager;
        }


        /**
         * Method to complete the building process of T
         *
         * @return A completed instance of T
         */
        @Override
        public MedievalArmor build() {
            return new MedievalArmor(this);
        }
    }
}
