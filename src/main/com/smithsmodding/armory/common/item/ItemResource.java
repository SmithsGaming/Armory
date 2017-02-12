/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ItemResource extends Item {

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return CoreClientProxy.getMultiColoredFontRenderer();
    }

    @NotNull
    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        String tMaterialID = "";

        if (pStack.getTagCompound() == null) {
            if (pStack.getItemDamage() == 0)
                pStack.setItemDamage(1);

            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getItemDamageMaterialIndex() == pStack.getItemDamage()) {
                    tMaterialID = tMaterial.getUniqueID();
                    break;
                }
            }
        } else {
            tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.Material);
        }

        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(tMaterialID);

        return tMaterial.getNameColor() + I18n.translateToLocal(tMaterial.getTranslationKey()) + " " + TextFormatting.RESET + I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        buildSubItemList(itemIn, tab, subItems);
    }

    public abstract void buildSubItemList(Item item, CreativeTabs tabs, List<ItemStack> subItems);
}
