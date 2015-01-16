package com.Orion.Armory.Util.Core;
/*
 *   ItemStackHelper
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ItemStackHelper
{
    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize)
    {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.stackSize = stackSize;
        return clonedItemStack;
    }

    public static Comparator<ItemStack> comparator = new Comparator<ItemStack>()
    {
        public int compare(ItemStack itemStack1, ItemStack itemStack2)
        {
            if (itemStack1 != null && itemStack2 != null)
            {
                // Sort on itemID
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
                {
                    // Sort on item
                    if (itemStack1.getItem() == itemStack2.getItem())
                    {
                        // Then sort on meta
                        if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                        {
                            // Then sort on NBT
                            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                            {
                                // Then sort on stack size
                                if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                                {
                                    return (itemStack1.stackSize - itemStack2.stackSize);
                                }
                                else
                                {
                                    return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                                }
                            }
                            else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound())
                            {
                                return -1;
                            }
                            else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound()))
                            {
                                return 1;
                            }
                            else
                            {
                                return (itemStack1.stackSize - itemStack2.stackSize);
                            }
                        }
                        else
                        {
                            return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                        }
                    }
                    else
                    {
                        return itemStack1.getItem().getUnlocalizedName(itemStack1).compareToIgnoreCase(itemStack2.getItem().getUnlocalizedName(itemStack2));
                    }
                }
                else
                {
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
                }
            }
            else if (itemStack1 != null)
            {
                return -1;
            }
            else if (itemStack2 != null)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    };

    public static boolean equals(ItemStack first, ItemStack second)
    {
        return (comparator.compare(first, second) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2)
    {
        if (itemStack1 != null && itemStack2 != null)
        {
            // Sort on itemID
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
            {
                // Sort on item
                if (itemStack1.getItem() == itemStack2.getItem())
                {
                    // Then sort on meta
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                    {
                        // Then sort on NBT
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                        {
                            // Then sort on stack size
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static int compare(ItemStack itemStack1, ItemStack itemStack2)
    {
        return comparator.compare(itemStack1, itemStack2);
    }

    public static String toString(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            return String.format("%sxitemStack[%s@%s]", itemStack.stackSize, itemStack.getUnlocalizedName(), itemStack.getItemDamage());
        }

        return "null";
    }

}
