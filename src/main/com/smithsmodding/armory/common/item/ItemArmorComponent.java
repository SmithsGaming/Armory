package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.capability.IArmorComponentStackCapability;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Author Marc (Created on: 11.06.2016)
 */
public class ItemArmorComponent extends Item {

    public ItemArmorComponent() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemArmorComponent);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.ItemArmorComponent);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return CoreClientProxy.getMultiColoredFontRenderer();
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
            return "Stack without the ArmorComponent capability.";

        IArmorComponentStackCapability capability = stack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null);
        IMultiComponentArmorExtension extension = capability.getExtension();

        return extension.getTextFormatting() + I18n.translateToLocal(extension.getTranslationKey());
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (IMultiComponentArmorExtension addon : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry()) {
            ItemStack stack = new ItemStack(itemIn);

            IArmorComponentStackCapability capability = new IArmorComponentStackCapability.Impl()
                    .setExtension(addon);

            stack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher()
                    .registerCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, capability);

            subItems.add(stack);
        }
    }
}
