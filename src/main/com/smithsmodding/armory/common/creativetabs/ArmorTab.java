package com.smithsmodding.armory.common.creativetabs;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModArmor;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.smithsmodding.armory.api.util.references.References.InternalNames.Upgrades.Helmet.*;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ArmorTab extends CreativeTabs {

    public ArmorTab() {
        super(TranslationKeys.CreativeTabs.Armor);
    }

    @Nonnull
    @Override
    public String getTranslatedTabLabel() {
        return getTabLabel();
    }

    @Override
    public ItemStack getTabIconItem() {
        //TODO: UPDATE
        ArrayList<IMultiComponentArmorExtensionInformation> components = new ArrayList<>();
        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
            .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_TOP.getResourcePath() + ModMaterials.Armor.Addon.OBSIDIAN.getRegistryName().getResourcePath()))));

        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
                .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_RIGHT.getResourcePath() + ModMaterials.Armor.Addon.GOLD.getRegistryName().getResourcePath()))));

        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
                .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_LEFT.getResourcePath() + ModMaterials.Armor.Addon.GOLD.getRegistryName().getResourcePath()))));

        ItemStack stack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, components, ModMaterials.Armor.Core.IRON.getBaseDurabilityForArmor(ModArmor.Medieval.HELMET), ModMaterials.Armor.Core.IRON);
        return stack;
    }
}
