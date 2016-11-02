package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 * <p>
 * Copyrighted according to Project specific license
 */
public class ItemNugget extends ItemResource implements IHeatableItem {

    public ItemNugget() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.componentsTab);
        this.setUnlocalizedName(References.InternalNames.Items.ItemMetalNugget);
        this.setRegistryName(References.InternalNames.Items.ItemMetalNugget);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void buildSubItemList(Item item, CreativeTabs tabs, List<ItemStack> subItems) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            ItemStack tNuggetStack = new ItemStack(this, 1, tMaterial.getItemDamageMaterialIndex());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
            tNuggetStack.setTagCompound(tStackCompound);

            if (!HeatableItemRegistry.getInstance().isHeatable(tNuggetStack))
                HeatableItemRegistry.getInstance().addBaseStack(tMaterial, tNuggetStack);

            subItems.add(tNuggetStack);
        }
    }

    @NotNull
    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.NUGGET;
    }

    @Override
    public int getMoltenMilibucket() {
        return 144 / 9;
    }
}
