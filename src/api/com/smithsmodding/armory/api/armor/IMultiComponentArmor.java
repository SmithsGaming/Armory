package com.smithsmodding.armory.api.armor;

import com.smithsmodding.armory.api.capability.armor.ArmorCapabilityManager;
import com.smithsmodding.armory.api.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import javafx.util.Pair;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IMultiComponentArmor extends IForgeRegistryEntry<IMultiComponentArmor>, ITranslateable, ICapabilityProvider {

    /**
     * Method to get all possible extension positions for this Armor.
     * @return The positions on the armor where an extension can be installed.
     */
    @Nonnull
    List<IMultiComponentArmorExtensionPosition> getPossibleExtensionPositions();

    /**
     * Method to get all possible extensions for this armor.
     * @return The extensions that can be installed on this armor.
     */
    @Nonnull
    List<IMultiComponentArmorExtension> getPossibleExtensions();

    /**
     * Method to get all the default capabilities this Armor provides.
     * @return All the default capabilities this Armor provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getDefaultArmorCapabilities();
}
