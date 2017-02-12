package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IHeatableItemRegistry {
    float getItemTemperature (ItemStack pItemStack);

    void setItemTemperature(ItemStack pItemStack, float pNewTemp);

    @NotNull ItemStack getBaseStack(IArmorMaterial material, String internalType);

    FluidStack getMoltenStack(IArmorMaterial material, String internalType);

    @Nullable FluidStack getMoltenStack(ItemStack stackToBeMolten);

    void addBaseStack(IArmorMaterial material, ItemStack stack);

    void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, int fluidSize);

    void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack);

    boolean isHeatable(ItemStack stack);

    @Nullable IArmorMaterial getMaterialFromStack(ItemStack stack);

    @Nullable IArmorMaterial getMaterialFromHeatedStack(ItemStack stack);

    @Nullable IArmorMaterial getMaterialFromCooledStack(ItemStack stack);

    @Nullable IArmorMaterial getMaterialFromMoltenStack(FluidStack stack);

    List<String> getInternalTypeFromItemStack(ItemStack stack);

    @Nullable String getInternalTypeFromHeatedStack(ItemStack stack);

    List<String> getInternalTypeFromCooledStack(ItemStack stack);

    @NotNull ArrayList<ItemStack> getAllMappedItems();

    @NotNull HashSet<String> getAllHeatableItemTypes();

    void reloadOreDictionary();

    void reloadItemStackOreDictionary(IArmorMaterial material, ItemStack originalStack);
}
