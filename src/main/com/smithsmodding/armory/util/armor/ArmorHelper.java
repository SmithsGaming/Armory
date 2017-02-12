package com.smithsmodding.armory.util.armor;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/29/2017.
 */
public class ArmorHelper {

    public static IMultiComponentArmor getArmorForModel(@Nonnull ResourceLocation modelLocation) {
        String[] pathComponents = modelLocation.getResourcePath().split("/");
        String nameComponent = pathComponents[pathComponents.length - 1].substring(0, pathComponents[pathComponents.length - 1].lastIndexOf("."));

        ResourceLocation itemLocation = new ResourceLocation(modelLocation.getResourceDomain(), nameComponent);

        return getArmorForItemName(itemLocation);
    }

    public static IMultiComponentArmor getArmorForItem(@Nonnull Item itemIn) {
        return getArmorForItemName(itemIn.getRegistryName());
    }

    public static IMultiComponentArmor getArmorForItemName(@Nonnull ResourceLocation itemName) {
        for (IMultiComponentArmor armor : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry()) {
            if (armor.getItem().getRegistryName().equals(itemName)) {
                return armor;
            }
        }

        return null;
    }
}
