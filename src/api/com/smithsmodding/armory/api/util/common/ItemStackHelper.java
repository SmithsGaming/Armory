package com.smithsmodding.armory.api.util.common;

import net.minecraft.item.ItemStack;

/**
 * Created by marcf on 1/25/2017.
 */
public class ItemStackHelper {

    public static final int CONSTANT_ITEMSTACK_DEFAULT_MAX = 64;

    public static void InitializeItemStackArray(ItemStack[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = ItemStack.EMPTY;
        }
    }
}
