package com.SmithsModding.Armory.Common.Registry;

import com.SmithsModding.Armory.API.Events.Common.HeatableItemRegisteredEvent;
import com.SmithsModding.Armory.API.Item.*;
import com.SmithsModding.Armory.API.Materials.*;
import com.SmithsModding.Armory.API.Registries.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Material.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Util.Common.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.*;

import java.util.*;

/**
 * Created by Marc on 19.12.2015.
 */
public class HeatableItemRegistry implements IHeatableItemRegistry {

    private static HeatableItemRegistry INSTANCE = new HeatableItemRegistry();

    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();
    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedOreDictionaryStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();

    HashMap<IArmorMaterial, HashMap<String, FluidStack>> mappedMoltenStacks = new HashMap<IArmorMaterial, HashMap<String, FluidStack>>();
    HashMap<IArmorMaterial, HashMap<String, FluidStack>> mappedOreDictionaryMoltenStacks = new HashMap<IArmorMaterial, HashMap<String, FluidStack>>();

    HashMap<String, HashMap<ItemStack, IArmorMaterial>> reverseTypeMappedStacks = new HashMap<String, HashMap<ItemStack, IArmorMaterial>>();
    HashMap<String, HashMap<ItemStack, IArmorMaterial>> reverseTypeMappedOreDictionaryStacks = new HashMap<String, HashMap<ItemStack, IArmorMaterial>>();

    public static HeatableItemRegistry getInstance () {
        return INSTANCE;
    }

    @Override
    public ItemStack getBaseStack (IArmorMaterial material, String internalType) {
        if (!mappedStacks.containsKey(material))
            mappedStacks.put(material, new HashMap<String, ItemStack>());

        if (mappedStacks.get(material).containsKey(internalType))
            return mappedStacks.get(material).get(internalType);

        if (!mappedOreDictionaryStacks.containsKey(material))
            mappedOreDictionaryStacks.put(material, new HashMap<String, ItemStack>());

        return mappedOreDictionaryStacks.get(material).get(internalType);
    }

    @Override
    public FluidStack getMoltenStack (IArmorMaterial material, String internalType) {
        if (!mappedMoltenStacks.containsKey(material))
            mappedMoltenStacks.put(material, new HashMap<String, FluidStack>());

        if (mappedMoltenStacks.get(material).containsKey(internalType))
            return mappedMoltenStacks.get(material).get(internalType);

        if (!mappedOreDictionaryMoltenStacks.containsKey(material))
            mappedOreDictionaryMoltenStacks.put(material, new HashMap<String, FluidStack>());

        return mappedOreDictionaryMoltenStacks.get(material).get(internalType);
    }

    @Override
    public void addBaseStack (IArmorMaterial material, ItemStack stack) {
        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        if (stack.getItem() instanceof IHeatableItem)
            this.addBaseStack(material, stack, ( (IHeatableItem) stack.getItem() ).getInternalType(), new FluidStack(GeneralRegistry.Fluids.moltenMetal, ( (IHeatableItem) stack.getItem() ).getMoltenMilibucket(), fluidCompound));
        else
            this.addBaseStack(material, stack, References.InternalNames.HeatedItemTypes.INGOT, new FluidStack(GeneralRegistry.Fluids.moltenMetal, References.General.FLUID_INGOT, fluidCompound));
    }

    @Override
    public void addBaseStack (IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack) {
        addStackToMap(mappedStacks, reverseTypeMappedStacks, mappedMoltenStacks, material, stack, internalType, moltenStack);
    }

    @Override
    public boolean isHeatable (ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem)
            return true;

        for (IArmorMaterial material : mappedStacks.keySet()) {
            for (ItemStack mappedStack : mappedStacks.get(material).values()) {
                if (ItemStackHelper.equalsIgnoreStackSize(stack, mappedStack))
                    return true;
            }
        }

        for (IArmorMaterial material : mappedOreDictionaryStacks.keySet()) {
            for (ItemStack mappedStack : mappedOreDictionaryStacks.get(material).values()) {
                if (ItemStackHelper.equalsIgnoreStackSize(stack, mappedStack))
                    return true;
            }
        }

        return false;
    }

    @Override
    public IArmorMaterial getMaterialFromStack (ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem)
            return getMaterialFromHeatedStack(stack);

        return getMaterialFromCooledStack(stack);
    }

    @Override
    public IArmorMaterial getMaterialFromHeatedStack (ItemStack stack) {
        if (!( stack.getItem() instanceof ItemHeatedItem ))
            return null;

        return MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));
    }

    @Override
    public IArmorMaterial getMaterialFromCooledStack (ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem) {
            return MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));
        }

        String type = References.InternalNames.HeatedItemTypes.INGOT;

        if (stack.getItem() instanceof IHeatableItem) {
            type = ( (IHeatableItem) stack.getItem() ).getInternalType();
        }


        for (ItemStack mappedStack : reverseTypeMappedStacks.get(type).keySet()) {
            if (ItemStackHelper.equalsIgnoreStackSize(mappedStack, stack))
                return reverseTypeMappedStacks.get(type).get(mappedStack);
        }

        for (ItemStack mappedStack : reverseTypeMappedOreDictionaryStacks.get(type).keySet()) {
            if (ItemStackHelper.equalsIgnoreStackSize(mappedStack, stack))
                return reverseTypeMappedOreDictionaryStacks.get(type).get(mappedStack);
        }

        return null;
    }

    @Override
    public ArrayList<ItemStack> getAllMappedItems () {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        for (IArmorMaterial material : mappedStacks.keySet()) {
            for (ItemStack stack : mappedStacks.get(material).values()) {
                result.add(stack);
            }
        }

        for (IArmorMaterial material : mappedOreDictionaryStacks.keySet()) {
            for (ItemStack stack : mappedOreDictionaryStacks.get(material).values()) {
                result.add(stack);
            }
        }

        return result;
    }

    public void reloadOreDictionary () {
        for (HashMap<String, ItemStack> oldTypeMappings : mappedOreDictionaryStacks.values())
            oldTypeMappings.clear();

        mappedOreDictionaryStacks.clear();

        for (HashMap<String, FluidStack> oldTypeMappings : mappedOreDictionaryMoltenStacks.values())
            oldTypeMappings.clear();

        mappedOreDictionaryMoltenStacks.clear();

        for (HashMap<ItemStack, IArmorMaterial> oldReverseMapping : reverseTypeMappedOreDictionaryStacks.values())
            oldReverseMapping.clear();

        reverseTypeMappedOreDictionaryStacks.clear();

        for (IArmorMaterial material : mappedStacks.keySet()) {
            for (ItemStack stack : mappedStacks.get(material).values()) {
                reloadItemStackOreDictionary(material, stack);
            }
        }
    }

    public void reloadItemStackOreDictionary (IArmorMaterial material, ItemStack originalStack) {
        int[] oreIDs = OreDictionary.getOreIDs(originalStack);

        for (int oreID : oreIDs) {
            for (ItemStack stack : OreDictionary.getOres(OreDictionary.getOreName(oreID))) {
                String type = References.InternalNames.HeatedItemTypes.INGOT;
                int fluidAmount = References.General.FLUID_INGOT;

                if (stack.getItem() instanceof IHeatableItem) {
                    type = ( (IHeatableItem) stack.getItem() ).getInternalType();
                    fluidAmount = ( (IHeatableItem) stack.getItem() ).getMoltenMilibucket();
                }

                NBTTagCompound fluidCompound = new NBTTagCompound();
                fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

                addStackToMap(mappedOreDictionaryStacks, reverseTypeMappedOreDictionaryStacks, mappedOreDictionaryMoltenStacks, material, stack, type, new FluidStack(GeneralRegistry.Fluids.moltenMetal, fluidAmount, fluidCompound));
            }
        }
    }


    private void addStackToMap (HashMap<IArmorMaterial, HashMap<String, ItemStack>> mapStack, HashMap<String, HashMap<ItemStack, IArmorMaterial>> reverseMapStack,
                                HashMap<IArmorMaterial, HashMap<String, FluidStack>> mapFluid, IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack) {

        HeatableItemRegisteredEvent event = new HeatableItemRegisteredEvent(material, internalType, stack, moltenStack, mapStack == mappedStacks?false:true);
        event.PostCommon();

        stack = event.getHeatableStack();
        moltenStack = event.getMoltenStack();

        if (!mapStack.containsKey(material))
            mapStack.put(material, new HashMap<String, ItemStack>());

        mapStack.get(material).put(internalType, stack);

        if (!reverseMapStack.containsKey(internalType))
            reverseMapStack.put(internalType, new HashMap<ItemStack, IArmorMaterial>());

        reverseMapStack.get(internalType).put(stack, material);

        if (!mapFluid.containsKey(material))
            mapFluid.put(material, new HashMap<String, FluidStack>());

        mapFluid.get(material).put(internalType, moltenStack);
    }

}

