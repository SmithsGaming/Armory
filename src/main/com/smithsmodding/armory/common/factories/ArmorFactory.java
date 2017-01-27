package com.smithsmodding.armory.common.factories;
/*
/  StandardMLAFactory
/  Created by : Orion
/  Created on : 04/07/2014
*/

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.factories.IMLAFactory;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ArmorFactory implements IMLAFactory {
    private static ArmorFactory iInstance;

    public static ArmorFactory getInstance() {
        if (iInstance == null) iInstance = new ArmorFactory();
        return iInstance;
    }

    /**
     * Function used to modify an existing MLA.
     * The function will add the addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factories can take different environmental values in effect.
     *
     * @param armor              The base armor used to create the new armor.
     * @param baseStack          The already existing ItemStack on to which addons should be added
     * @param newAddons          The new addons stored in a HashMap, with as key the new addons and as Value the new installed amount.
     * @param newTotalDurability
     * @param coreMaterial
     * @param data               Extra data for your factories
     * @return An Itemstack containing your now modified armor.
     */
    @Nonnull
    @Override
    public ItemStack buildMLAArmor(IMultiComponentArmor armor, ItemStack baseStack, ArrayList<IMultiComponentArmorExtensionInformation> newAddons, Integer newTotalDurability, ICoreArmorMaterial coreMaterial, Object... data) {
        if (!baseStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            throw new IllegalArgumentException("The given armor base stack is not a Armor!");

        ArrayList<IMultiComponentArmorExtensionInformation> existingExtensions = ArmorNBTHelper.getAddonMap(baseStack);

        if (!validateOldAgainstNewAddons(existingExtensions, newAddons))
            throw new IllegalArgumentException("ADDONS not compatible");

        IMultiComponentArmorCapability existingCapability = baseStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        ItemStack combinedArmor = buildNewMLAArmor(armor, compressInformation(existingExtensions, newAddons), newTotalDurability, coreMaterial);

        IMultiComponentArmorCapability newCapability = combinedArmor.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        newCapability.setCurrentDurability(existingCapability.getCurrentDurability() + (newTotalDurability - existingCapability.getMaximalDurability()));

        return combinedArmor;
    }

    /**
     * Function used to create a new armor ItemStack
     *
     * @param armor           The base armor used to create the new armor.
     * @param addons          The new addons stored in a ArrayList
     * @param totalDurability
     * @param coreMaterial
     * @param data            Extra data for your factories  @return A new ItemStack with full getDurability
     */
    @Nonnull
    @Override
    public ItemStack buildNewMLAArmor(IMultiComponentArmor armor, ArrayList<IMultiComponentArmorExtensionInformation> addons, Integer totalDurability, ICoreArmorMaterial coreMaterial, Object... data) throws IllegalArgumentException {
        if (!validateNewAgainstNewAddons(addons))
            throw new IllegalArgumentException("ADDONS not compatible");

        ItemStack armorStack =  new ItemStack(armor.getItem(), 1);
        IMultiComponentArmorCapability capability = new IMultiComponentArmorCapability.Impl()
                .setArmorType(armor)
                .setMaterial(coreMaterial)
                .setInstalledExtensions(addons)
                .setMaximalDurability(totalDurability)
                .setCurrentDurability(totalDurability);

        armorStack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher().registerCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, capability);

        return armorStack;
    }

    @Nullable
    @Override
    public String getArmorGivenName(ItemStack pStack) {
        return null;
    }

    private boolean validateOldAgainstNewAddons(@Nonnull ArrayList<IMultiComponentArmorExtensionInformation> oldAddons, @Nonnull ArrayList<IMultiComponentArmorExtensionInformation> newAddons) {
        boolean continueCrafting = true;
        Iterator<IMultiComponentArmorExtensionInformation> installedIterator = oldAddons.iterator();

        while (continueCrafting && installedIterator.hasNext()) {
            IMultiComponentArmorExtensionInformation information = installedIterator.next();
            IMultiComponentArmorExtension extension = information.getExtension();

            Iterator<IMultiComponentArmorExtensionInformation> additionIterator = newAddons.iterator();
            while (continueCrafting && additionIterator.hasNext()) {
                IMultiComponentArmorExtensionInformation newInformation = additionIterator.next();
                IMultiComponentArmorExtension newExtension = newInformation.getExtension();

                if (extension == newExtension) {
                    if (information.getCount() + newInformation.getCount() > extension.getMaximalInstallationCount()) {
                        return false;
                    }
                } else {
                    continueCrafting = (extension.validateCrafting(newExtension, true) && newExtension.validateCrafting(extension, false));
                }
            }
        }

        return continueCrafting;
    }

    private boolean validateNewAgainstNewAddons(@Nonnull ArrayList<IMultiComponentArmorExtensionInformation> newAddons) {
        boolean continueCrafting = true;
        Iterator<IMultiComponentArmorExtensionInformation> externalIterator = newAddons.iterator();

        while (continueCrafting && externalIterator.hasNext()) {
            Iterator<IMultiComponentArmorExtensionInformation> internalIterator = newAddons.iterator();
            IMultiComponentArmorExtension externalExtension = externalIterator.next().getExtension();

            while (continueCrafting && internalIterator.hasNext()) {
                IMultiComponentArmorExtension internalExtension = internalIterator.next().getExtension();
                continueCrafting = externalExtension.validateCrafting(internalExtension, false);
            }
        }

        return continueCrafting;
    }

    public ArrayList<IMultiComponentArmorExtensionInformation> compressInformation(ArrayList<IMultiComponentArmorExtensionInformation> oldExtensions, ArrayList<IMultiComponentArmorExtensionInformation> newExtensions) {
        HashMap<IMultiComponentArmorExtension, Integer> countMap = new HashMap<>();

        oldExtensions.forEach((i)-> {
            if (!countMap.containsKey(i.getExtension()))
                countMap.put(i.getExtension(), i.getCount());
        });

        newExtensions.forEach((i)-> {
            if (!countMap.containsKey(i.getExtension()))
                countMap.put(i.getExtension(), i.getCount());
            else
                countMap.put(i.getExtension(), countMap.get(i.getExtension()) + i.getCount());
        });

        ArrayList<IMultiComponentArmorExtensionInformation> compressedList = new ArrayList<>();

        countMap.forEach((e, c) -> {
            compressedList.add(new IMultiComponentArmorExtensionInformation.Impl()
                    .setExtension(e)
                    .setPosition(e.getPosition())
                    .setCount(c));
        });

        return compressedList;
    }

}
