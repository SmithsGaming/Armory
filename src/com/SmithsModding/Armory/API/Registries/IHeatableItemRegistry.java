package com.smithsmodding.Armory.API.Registries;

import com.smithsmodding.Armory.API.Materials.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;

import java.util.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IHeatableItemRegistry {

    ItemStack getBaseStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack (ItemStack stackToBeMolten);


    void addBaseStack (IArmorMaterial material, ItemStack stack);

    void addBaseStack (IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack);


    boolean isHeatable (ItemStack stack);

    IArmorMaterial getMaterialFromStack (ItemStack stack);

    IArmorMaterial getMaterialFromHeatedStack (ItemStack stack);

    IArmorMaterial getMaterialFromCooledStack (ItemStack stack);

    IArmorMaterial getMaterialFromMoltenStack (FluidStack stack);



    ArrayList<ItemStack> getAllMappedItems ();
}
