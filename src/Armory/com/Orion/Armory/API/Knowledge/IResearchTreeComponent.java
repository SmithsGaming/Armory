/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IResearchTreeComponent {

    ArrayList<IResearchTreeComponent> getFollowupTreeComponent();

    IResearchTreeComponent registerNewFollowupTreeComponent(IResearchTreeComponent pNewComponent);

    IResearchTreeComponent getFollowupComponent(ItemStack pTargetStack, String iInputID, EntityPlayer pPlayer);

    boolean isFinalComponentInBranch();

    ItemStack getBranchResult();

    boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer);

    String getDisplayString();
}
