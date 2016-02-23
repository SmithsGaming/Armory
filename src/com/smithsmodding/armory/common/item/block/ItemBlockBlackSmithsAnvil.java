package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

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
        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IAnvilMaterial tMaterial = AnvilMaterialRegistry.getInstance().getAnvilMaterial(pStack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL));

        return tMaterial.translatedDisplayNameColor() + tMaterial.translatedDisplayName() + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }
}
