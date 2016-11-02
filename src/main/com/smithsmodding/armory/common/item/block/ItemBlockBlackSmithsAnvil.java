package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Marc on 23.02.2016.
 */
public class ItemBlockBlackSmithsAnvil extends ItemBlock {

    public ItemBlockBlackSmithsAnvil(@NotNull Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return super.getFontRenderer(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return "";

        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IAnvilMaterial tMaterial = AnvilMaterialRegistry.getInstance().getAnvilMaterial(pStack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL));

        return tMaterial.translatedDisplayNameColor() + tMaterial.translatedDisplayName() + " " + TextFormatting.RESET + I18n.format(this.getUnlocalizedName() + ".name");
    }
}
