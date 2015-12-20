package com.SmithsModding.Armory.API.Registries;

import com.SmithsModding.Armory.API.Materials.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;

import java.util.*;

/**
 * Created by Marc on 19.12.2015.
 */
public interface IHeatableItemRegistry {

    ItemStack getBaseStack (IArmorMaterial material, String internalType);

    FluidStack getMoltenStack (IArmorMaterial material, String internalType);


    void addBaseStack (IArmorMaterial material, ItemStack stack);

    void addBaseStack (IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack);


    boolean isHeatable (ItemStack stack);

    IArmorMaterial getMaterialFromStack (ItemStack stack);

    IArmorMaterial getMaterialFromHeatedStack (ItemStack stack);

    IArmorMaterial getMaterialFromCooledStack (ItemStack stack);


    ArrayList<ItemStack> getAllMappedItems ();
}
