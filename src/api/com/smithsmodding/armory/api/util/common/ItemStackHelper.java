package com.smithsmodding.armory.api.util.common;

import net.minecraft.item.ItemStack;

/**
 * Created by marcf on 1/25/2017.
 */
public class ItemStackHelper {

    public static void InitializeItemStackArray(ItemStack[] stacks) {
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = ItemStack.EMPTY;
        }
    }
}
