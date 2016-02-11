package com.smithsmodding.armory.common.registry;

import com.smithsmodding.armory.api.events.common.*;
import com.smithsmodding.armory.api.item.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.api.registries.*;
import com.smithsmodding.armory.*;
import com.smithsmodding.armory.common.item.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.util.*;
import com.smithsmodding.smithscore.util.common.*;
import gnu.trove.map.hash.*;
import gnu.trove.strategy.*;
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

    TCustomHashMap<ItemStack, IArmorMaterial> reverseStackToMaterialMap = new TCustomHashMap<ItemStack, IArmorMaterial>(new ItemStackHashingStrategy());
    TCustomHashMap<ItemStack, IArmorMaterial> reverseOreDicStackToMaterialMap = new TCustomHashMap<ItemStack, IArmorMaterial>(new ItemStackHashingStrategy());

    TCustomHashMap<FluidStack, IArmorMaterial> reverseFluidStackToMaterialMap = new TCustomHashMap<FluidStack, IArmorMaterial>(new FluidStackHashingStrategy());
    TCustomHashMap<FluidStack, IArmorMaterial> reverseOreDicFluidStackToMaterialMap = new TCustomHashMap<FluidStack, IArmorMaterial>(new FluidStackHashingStrategy());

    public static HeatableItemRegistry getInstance () {
        return INSTANCE;
    }

    public float getItemTemperature (ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            return stackCompound.getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }


        return 20F;
    }

    public void setItemTemperature (ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            if (pNewTemp < 20F)
                pNewTemp = 20F;

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            stackCompound.setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);

            pItemStack.setTagCompound(stackCompound);
        }
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
    public FluidStack getMoltenStack (ItemStack stackToBeMolten) {
        IArmorMaterial material;
        String internalType;

        if (stackToBeMolten.getItem() instanceof ItemHeatedItem) {
            material = getMaterialFromHeatedStack(stackToBeMolten);
            internalType = stackToBeMolten.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE);
        } else if (stackToBeMolten.getItem() instanceof IHeatableItem) {
            material = getMaterialFromCooledStack(stackToBeMolten);

            if (material == null)
                return null;

            internalType = ( (IHeatableItem) stackToBeMolten.getItem() ).getInternalType();
        } else {
            return null;
        }

        return getMoltenStack(material, internalType);
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
        addStackToMap(mappedStacks, reverseStackToMaterialMap, mappedMoltenStacks, reverseFluidStackToMaterialMap, material, stack, internalType, moltenStack);
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


        if (reverseStackToMaterialMap.containsKey(stack))
            return reverseStackToMaterialMap.get(stack);

        if (reverseOreDicStackToMaterialMap.containsKey(stack))
            return reverseOreDicStackToMaterialMap.get(stack);

        return null;
    }

    @Override
    public IArmorMaterial getMaterialFromMoltenStack (FluidStack stack) {
        if (reverseFluidStackToMaterialMap.containsKey(stack))
            return reverseFluidStackToMaterialMap.get(stack);

        if (reverseOreDicFluidStackToMaterialMap.containsKey(stack))
            return reverseOreDicFluidStackToMaterialMap.get(stack);

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

        reverseOreDicStackToMaterialMap.clear();

        reverseOreDicFluidStackToMaterialMap.clear();

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

                addStackToMap(mappedOreDictionaryStacks, reverseOreDicStackToMaterialMap, mappedOreDictionaryMoltenStacks, reverseOreDicFluidStackToMaterialMap, material, stack, type, new FluidStack(GeneralRegistry.Fluids.moltenMetal, fluidAmount, fluidCompound));
            }
        }
    }


    private void addStackToMap (HashMap<IArmorMaterial, HashMap<String, ItemStack>> mapStack, TCustomHashMap<ItemStack, IArmorMaterial> reverseMapStack,
                                HashMap<IArmorMaterial, HashMap<String, FluidStack>> mapFluid, TCustomHashMap<FluidStack, IArmorMaterial> reverseFluidMap, IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack) {

        HeatableItemRegisteredEvent event = new HeatableItemRegisteredEvent(material, internalType, stack, moltenStack, mapStack == mappedStacks?false:true);
        event.PostCommon();

        if (event.isCanceled())
            return;

        stack = event.getHeatableStack();
        moltenStack = event.getMoltenStack();

        if (!mapStack.containsKey(material))
            mapStack.put(material, new HashMap<String, ItemStack>());

        mapStack.get(material).put(internalType, stack);

        if (!reverseMapStack.containsKey(stack))
            reverseMapStack.put(stack, material);
        else
            Armory.getLogger().warn("Could not register: " + ItemStackHelper.toString(stack) + " to material: " + material.getUniqueID() + " it already is mapped!");

        if (!mapFluid.containsKey(material))
            mapFluid.put(material, new HashMap<String, FluidStack>());

        mapFluid.get(material).put(internalType, moltenStack);

        if (!reverseFluidMap.containsKey(moltenStack))
            reverseFluidMap.put(moltenStack, material);
        else
            Armory.getLogger().warn("Could not register: " + moltenStack.toString() + " to material: " + material.getUniqueID() + " it already is mapped!");
    }

    private class ItemStackHashingStrategy implements HashingStrategy<ItemStack> {
        @Override
        public int computeHashCode (ItemStack object) {
            int hash = object.getItem().hashCode() ^ object.getMetadata();

            if (object.getTagCompound() != null)
                hash ^= object.getTagCompound().hashCode();

            return hash;
        }

        @Override
        public boolean equals (ItemStack o1, ItemStack o2) {
            return ItemStackHelper.equalsIgnoreStackSize(o1, o2);
        }
    }

    private class FluidStackHashingStrategy implements HashingStrategy<FluidStack> {
        @Override
        public int computeHashCode (FluidStack object) {
            int hash = object.getFluid().hashCode();

            if (object.tag != null)
                hash ^= object.tag.hashCode();

            return hash;
        }

        public boolean equals (FluidStack o1, FluidStack o2) {
            return FluidStackHelper.equalsIgnoreStackSize(o1, o2);
        }
    }


}

