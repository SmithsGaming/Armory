package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.api.armor.ISingleComponentItem;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.api.item.ISingleMaterialItem;
import com.smithsmodding.armory.common.addons.MedievalAddonRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * @Author Marc (Created on: 11.06.2016)
 */
public class ItemArmorComponent extends Item implements ISingleMaterialItem, ISingleComponentItem {

    public ItemArmorComponent() {
        this.setMaxStackSize(1);
        this.setCreativeTab(GeneralRegistry.CreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemArmorComponent);
        this.setRegistryName(References.General.MOD_ID, References.InternalNames.Items.ItemArmorComponent);
    }

    @Override
    public String getMaterialInternalName(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemArmorComponent))
            return "";

        if (!stack.hasTagCompound())
            return "";

        if (!stack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.ItemComponent.MATERIAL))
            return "";

        return stack.getTagCompound().getString(References.NBTTagCompoundData.Item.ItemComponent.MATERIAL);
    }

    @Override
    public String getComponentTypeFromItemStack(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemArmorComponent))
            return "";

        if (!stack.hasTagCompound())
            return "";

        if (!stack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.ItemComponent.TYPE))
            return "";

        return stack.getTagCompound().getString(References.NBTTagCompoundData.Item.ItemComponent.TYPE);
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        if (!pStack.hasTagCompound())
            return I18n.format(this.getUnlocalizedName() + ".name");

        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        MLAAddon addon = MedievalAddonRegistry.getInstance().getUpgrade(getComponentTypeFromItemStack(pStack) + "-" + getMaterialInternalName(pStack));

        return addon.getDisplayName();
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (MLAAddon addon : MedievalAddonRegistry.getInstance().getUpgrades().values()) {
            ItemStack stack = new ItemStack(itemIn);
            NBTTagCompound data = new NBTTagCompound();

            if (addon instanceof MaterialDependentMLAAddon && addon.isMaterialDependent())
                data.setString(References.NBTTagCompoundData.Item.ItemComponent.TYPE, ((MaterialDependentMLAAddon) addon).getMaterialIndependentID());
            else
                data.setString(References.NBTTagCompoundData.Item.ItemComponent.TYPE, addon.getUniqueID());

            if (addon instanceof MaterialDependentMLAAddon && addon.isMaterialDependent())
                data.setString(References.NBTTagCompoundData.Item.ItemComponent.MATERIAL, ((MaterialDependentMLAAddon) addon).getUniqueMaterialID());

            stack.setTagCompound(data);
            subItems.add(stack);
        }
    }
}
