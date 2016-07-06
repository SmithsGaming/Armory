package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IHeatableItemRegistry {

    ItemStack getBaseStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack (ItemStack stackToBeMolten);


    void addBaseStack (IArmorMaterial material, ItemStack stack);

    void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, int fluidSize);

    void addBaseStack (IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack);


    boolean isHeatable (ItemStack stack);

    IArmorMaterial getMaterialFromStack (ItemStack stack);

    IArmorMaterial getMaterialFromHeatedStack (ItemStack stack);

    IArmorMaterial getMaterialFromCooledStack (ItemStack stack);

    IArmorMaterial getMaterialFromMoltenStack (FluidStack stack);



    ArrayList<ItemStack> getAllMappedItems ();
}
