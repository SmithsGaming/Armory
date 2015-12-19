package com.SmithsModding.Armory.Common.Registry;

import com.SmithsModding.Armory.API.Item.*;
import com.SmithsModding.Armory.API.Materials.*;
import com.SmithsModding.Armory.API.Registries.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Material.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Util.Common.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;

import java.util.*;

/**
 * Created by Marc on 19.12.2015.
 */
public class HeatableItemRegistry implements IHeatableItemRegistry {

    private static HeatableItemRegistry INSTANCE = new HeatableItemRegistry();

    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();
    HashMap<IArmorMaterial, HashMap<String, ItemStack>> mappedOreDictionaryStacks = new HashMap<IArmorMaterial, HashMap<String, ItemStack>>();

    public static HeatableItemRegistry getInstance () {
        return INSTANCE;
    }

    @Override
    public ItemStack getBaseStack (IArmorMaterial material, String internalType) {
        if (!mappedStacks.containsKey(material))
            mappedStacks.put(material, new HashMap<String, ItemStack>());

        return mappedStacks.get(material).get(internalType);
    }

    @Override
    public void addBaseStack (IArmorMaterial material, ItemStack stack) {
        if (stack.getItem() instanceof IHeatableItem)
            this.addBaseStack(material, stack, ( (IHeatableItem) stack.getItem() ).getInternalType());
        else
            this.addBaseStack(material, stack, References.InternalNames.HeatedItemTypes.INGOT);
    }

    @Override
    public void addBaseStack (IArmorMaterial material, ItemStack stack, String internalType) {
        if (!mappedStacks.containsKey(material))
            mappedStacks.put(material, new HashMap<String, ItemStack>());

        mappedStacks.get(material).put(internalType, stack);
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
    public IArmorMaterial getMaterialFromHeatedStack (ItemStack stack) {
        if (!( stack.getItem() instanceof ItemHeatedItem ))
            return null;

        return MaterialRegistry.getInstance().getMaterial(stack.getTagCompound().getString(References.NBTTagCompoundData.HeatedIngot.MATERIALID));
    }

    @Override
    public IArmorMaterial getMaterialFromCooledStack (ItemStack stack) {
        return null;
    }

    public void reloadOreDictionary () {
        for (HashMap<String, ItemStack> oldTypeMappings : mappedOreDictionaryStacks.values())
            oldTypeMappings.clear();

        mappedOreDictionaryStacks.clear();

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
                String type;

                if (stack.getItem() instanceof IHeatableItem)
                    type = ( (IHeatableItem) stack.getItem() ).getInternalType();
                else
                    type = References.InternalNames.HeatedItemTypes.INGOT;

                if (!mappedOreDictionaryStacks.containsKey(material))
                    mappedOreDictionaryStacks.put(material, new HashMap<String, ItemStack>());

                mappedOreDictionaryStacks.get(material).put(type, stack);
            }
        }
    }

}
