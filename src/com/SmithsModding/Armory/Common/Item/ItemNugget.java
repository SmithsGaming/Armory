package com.SmithsModding.Armory.Common.Item;

import com.SmithsModding.Armory.API.Item.IHeatableItem;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemNugget extends ItemResource implements IHeatableItem {

    public ItemNugget() {
        this.setMaxStackSize(64);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemNugget);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iNugget, 1, tMaterial.getMaterialID());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
            tNuggetStack.setTagCompound(tStackCompound);

            HeatedItemFactory.getInstance().addHeatableItemstack(tMaterial.getInternalMaterialName(), tNuggetStack);

            pItemStacks.add(tNuggetStack);
        }
    }

    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.NUGGET;
    }
}
