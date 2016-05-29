package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.util.References;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

/**
 * Created by Marc on 23.02.2016.
 */
public class ItemBlockBlackSmithsAnvil extends ItemBlock
{

    public ItemBlockBlackSmithsAnvil (Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return "";

        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IAnvilMaterial tMaterial = AnvilMaterialRegistry.getInstance().getAnvilMaterial(pStack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL));

        return tMaterial.translatedDisplayNameColor() + tMaterial.translatedDisplayName() + " " + TextFormatting.RESET + I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }
}
