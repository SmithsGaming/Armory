package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IHeatableItemRegistry {
    float getItemTemperature (ItemStack pItemStack);

    void setItemTemperature(ItemStack pItemStack, float pNewTemp);

    ItemStack getBaseStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack(IArmorMaterial material, String internalType);

    FluidStack getMoltenStack(ItemStack stackToBeMolten);

    void addBaseStack(IArmorMaterial material, ItemStack stack);

    void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, int fluidSize);

    void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack);

    boolean isHeatable(ItemStack stack);

    IArmorMaterial getMaterialFromStack(ItemStack stack);

    IArmorMaterial getMaterialFromHeatedStack(ItemStack stack);

    IArmorMaterial getMaterialFromCooledStack(ItemStack stack);

    IArmorMaterial getMaterialFromMoltenStack(FluidStack stack);

    List<String> getInternalTypeFromItemStack(ItemStack stack);

    String getInternalTypeFromHeatedStack(ItemStack stack);

    List<String> getInternalTypeFromCooledStack(ItemStack stack);

    ArrayList<ItemStack> getAllMappedItems();

    HashSet<String> getAllHeatableItemTypes();

    void reloadOreDictionary();

    void reloadItemStackOreDictionary(IArmorMaterial material, ItemStack originalStack);
}
