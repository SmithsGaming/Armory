package com.smithsmodding.armory.common.registry;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.api.events.common.HeatableItemRegisteredEvent;
import com.smithsmodding.armory.api.item.IHeatableItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.registries.IHeatableItemRegistry;
import com.smithsmodding.armory.api.util.references.ModFluids;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import com.smithsmodding.smithscore.util.common.NBTHelper;
import com.smithsmodding.smithscore.util.common.Pair;
import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.HashingStrategy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc on 19.12.2015.
 */
public class HeatableItemRegistry implements IHeatableItemRegistry {

    private static IHeatableItemRegistry instance = new HeatableItemRegistry();

    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();
    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedOreDictionaryStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();

    HashMap<IArmorMaterial, HashMap<String, FluidStack>> mappedMoltenStacks = new HashMap<IArmorMaterial, HashMap<String, FluidStack>>();
    HashMap<IArmorMaterial, HashMap<String, FluidStack>> mappedOreDictionaryMoltenStacks = new HashMap<IArmorMaterial, HashMap<String, FluidStack>>();

    TCustomHashMap<ItemStack, Pair<IArmorMaterial, ArrayList<String>>> reverseStackToMaterialMap = new TCustomHashMap<>(new ItemStackHashingStrategy());
    TCustomHashMap<ItemStack, Pair<IArmorMaterial, ArrayList<String>>> reverseOreDicStackToMaterialMap = new TCustomHashMap<>(new ItemStackHashingStrategy());

    TCustomHashMap<FluidStack, Pair<IArmorMaterial, ArrayList<String>>> reverseFluidStackToMaterialMap = new TCustomHashMap<>(new FluidStackHashingStrategy());
    TCustomHashMap<FluidStack, Pair<IArmorMaterial, ArrayList<String>>> reverseOreDicFluidStackToMaterialMap = new TCustomHashMap<>(new FluidStackHashingStrategy());

    public static IHeatableItemRegistry getInstance () {
        return instance;
    }

    @Override
    public float getItemTemperature(ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            return stackCompound.getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }


        return 20F;
    }

    @Override
    public void setItemTemperature(ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            if (pNewTemp < 20F)
                pNewTemp = 20F;

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            stackCompound.setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);

            pItemStack.setTagCompound(stackCompound);
        }
    }

    
    @Override
    public ItemStack getBaseStack(IArmorMaterial material, String internalType) {
        if (!mappedStacks.containsKey(material))
            mappedStacks.put(material, new HashMap<String, ItemStack>());

        if (mappedStacks.get(material).containsKey(internalType))
            return mappedStacks.get(material).get(internalType).copy();

        if (!mappedOreDictionaryStacks.containsKey(material))
            mappedOreDictionaryStacks.put(material, new HashMap<String, ItemStack>());

        return mappedOreDictionaryStacks.get(material).get(internalType).copy();
    }

    
    @Override
    public FluidStack getMoltenStack(IArmorMaterial material, String internalType) {
        if (!mappedMoltenStacks.containsKey(material))
            mappedMoltenStacks.put(material, new HashMap<String, FluidStack>());

        if (mappedMoltenStacks.get(material).containsKey(internalType))
            return mappedMoltenStacks.get(material).get(internalType).copy();

        if (!mappedOreDictionaryMoltenStacks.containsKey(material))
            mappedOreDictionaryMoltenStacks.put(material, new HashMap<String, FluidStack>());

        return mappedOreDictionaryMoltenStacks.get(material).get(internalType).copy();
    }

    
    @Override
    public FluidStack getMoltenStack(ItemStack stackToBeMolten) {
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
    public void addBaseStack(IArmorMaterial material, ItemStack stack) {
        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        if (stack.getItem() instanceof IHeatableItem)
            this.addBaseStack(material, stack.copy(), ((IHeatableItem) stack.getItem()).getInternalType(), new FluidStack(ModFluids.moltenMetal, ((IHeatableItem) stack.getItem()).getMoltenMilibucket(), fluidCompound));
        else {
            int[] oreIds = OreDictionary.getOreIDs(stack);
            for (int oreId : oreIds) {
                if (OreDictionary.getOreName(oreId).contains("block")) {
                    this.addBaseStack(material, stack.copy(), References.InternalNames.HeatedItemTypes.BLOCK, new FluidStack(ModFluids.moltenMetal, References.General.FLUID_INGOT * 9, fluidCompound));
                    return;
                }
            }

            this.addBaseStack(material, stack.copy(), References.InternalNames.HeatedItemTypes.INGOT, new FluidStack(ModFluids.moltenMetal, References.General.FLUID_INGOT, fluidCompound));
        }
    }

    
    @Override
    public void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, int fluidSize) {
        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        this.addBaseStack(material, stack.copy(), internalType, new FluidStack(ModFluids.moltenMetal, fluidSize, fluidCompound));
    }

    
    @Override
    public void addBaseStack(IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack) {
        addStackToMap(mappedStacks, reverseStackToMaterialMap, mappedMoltenStacks, reverseFluidStackToMaterialMap, material, stack, internalType, moltenStack);
    }

    
    @Override
    public boolean isHeatable(ItemStack stack) {
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
    public IArmorMaterial getMaterialFromStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem)
            return getMaterialFromHeatedStack(stack);

        return getMaterialFromCooledStack(stack);
    }

    
    @Override
    public IArmorMaterial getMaterialFromHeatedStack(ItemStack stack) {
        if (!( stack.getItem() instanceof ItemHeatedItem ))
            return null;

        return MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));
    }

    
    @Override
    public IArmorMaterial getMaterialFromCooledStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem) {
            return MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));
        }

        if (reverseStackToMaterialMap.containsKey(stack))
            return reverseStackToMaterialMap.get(stack).getKey();

        if (reverseOreDicStackToMaterialMap.containsKey(stack))
            return reverseOreDicStackToMaterialMap.get(stack).getKey();

        return null;
    }

    
    @Override
    public IArmorMaterial getMaterialFromMoltenStack(FluidStack stack) {
        if (reverseFluidStackToMaterialMap.containsKey(stack))
            return reverseFluidStackToMaterialMap.get(stack).getKey();

        if (reverseOreDicFluidStackToMaterialMap.containsKey(stack))
            return reverseOreDicFluidStackToMaterialMap.get(stack).getKey();

        return null;
    }

    @Override
    public List<String> getInternalTypeFromItemStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem) {
            return ImmutableList.of(getInternalTypeFromHeatedStack(stack));
        }

        return ImmutableList.copyOf(getInternalTypeFromCooledStack(stack));
    }

    @Override
    public String getInternalTypeFromHeatedStack(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemHeatedItem))
            return null;

        return stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.TYPE);
    }

    @Override
    public List<String> getInternalTypeFromCooledStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemHeatedItem) {
            return ImmutableList.of(getInternalTypeFromHeatedStack(stack));
        }

        if (reverseStackToMaterialMap.containsKey(stack))
            return reverseStackToMaterialMap.get(stack).getValue();

        if (reverseOreDicStackToMaterialMap.containsKey(stack))
            return reverseOreDicStackToMaterialMap.get(stack).getValue();

        return new ArrayList<>();
    }

    
    @Override
    public ArrayList<ItemStack> getAllMappedItems() {
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

    @Override
    public void reloadOreDictionary() {
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

    @Override
    public void reloadItemStackOreDictionary(IArmorMaterial material, ItemStack originalStack) {
        int[] oreIDs = OreDictionary.getOreIDs(originalStack);

        for (int oreID : oreIDs) {
            for (ItemStack stack : OreDictionary.getOres(OreDictionary.getOreName(oreID))) {
                if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                    continue;

                String type = References.InternalNames.HeatedItemTypes.INGOT;
                int fluidAmount = References.General.FLUID_INGOT;

                if (stack.getItem() instanceof IHeatableItem) {
                    type = ( (IHeatableItem) stack.getItem() ).getInternalType();
                    fluidAmount = ( (IHeatableItem) stack.getItem() ).getMoltenMilibucket();
                }

                NBTTagCompound fluidCompound = new NBTTagCompound();
                fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

                addStackToMap(mappedOreDictionaryStacks, reverseOreDicStackToMaterialMap, mappedOreDictionaryMoltenStacks, reverseOreDicFluidStackToMaterialMap, material, stack, type, new FluidStack(ModFluids.moltenMetal, fluidAmount, fluidCompound));
            }
        }
    }


    private void addStackToMap(HashMap<IArmorMaterial, HashMap<String, ItemStack>> materialStack, TCustomHashMap<ItemStack, Pair<IArmorMaterial, ArrayList<String>>> reverseMaterialStack,
                               HashMap<IArmorMaterial, HashMap<String, FluidStack>> fluidStack, TCustomHashMap<FluidStack, Pair<IArmorMaterial, ArrayList<String>>> reverseFluidStack, IArmorMaterial material, ItemStack stack, String internalType, FluidStack moltenStack) {

        HeatableItemRegisteredEvent event = new HeatableItemRegisteredEvent(material, internalType, stack, moltenStack, materialStack != mappedStacks);
        event.PostCommon();

        if (event.isCanceled())
            return;

        stack = event.getHeatableStack();
        moltenStack = event.getMoltenStack();

        if (!materialStack.containsKey(material))
            materialStack.put(material, new HashMap<>());

        materialStack.get(material).put(internalType, stack);

        if (!reverseMaterialStack.containsKey(stack)) {
            reverseMaterialStack.put(stack, new Pair<>(material, new ArrayList<>()));
            reverseMaterialStack.get(stack).getValue().add(internalType);
        }
        else
            reverseMaterialStack.get(stack).getValue().add(internalType);

        if (!fluidStack.containsKey(material))
            fluidStack.put(material, new HashMap<>());

        fluidStack.get(material).put(internalType, moltenStack);

        if (!reverseFluidStack.containsKey(moltenStack)) {
            reverseFluidStack.put(moltenStack, new Pair<>(material, new ArrayList<>()));
            reverseFluidStack.get(moltenStack).getValue().add(internalType);
        } else if (!reverseFluidStack.get(moltenStack).getValue().contains(internalType))
            reverseFluidStack.get(moltenStack).getValue().add(internalType);
    }

    private class ItemStackHashingStrategy implements HashingStrategy<ItemStack> {
        
        public int computeHashCode (ItemStack object) {
            int hash = object.getItem().hashCode() ^ object.getMetadata();

            if (object.getTagCompound() != null)
                hash ^= object.getTagCompound().hashCode();

            return hash;
        }

        
        public boolean equals (ItemStack o1, ItemStack o2) {
            return ItemStackHelper.equalsIgnoreStackSize(o1, o2);
        }
    }

    private class FluidStackHashingStrategy implements HashingStrategy<FluidStack> {
        
        public int computeHashCode (FluidStack object) {
            int hash = object.getFluid().hashCode() + object.amount;

            if (object.tag != null)
                hash ^= object.tag.hashCode();

            return hash;
        }

        public boolean equals (FluidStack o1, FluidStack o2) {
            return o1.isFluidEqual(o2) && o1.amount == o2.amount;
        }
    }


}

