/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge.Research;

import com.Orion.Armory.API.Knowledge.IResearchTreeComponent;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public abstract class StandardResearchTreeComponent implements IResearchTreeComponent {

    protected ItemStack iTargetStack;
    protected String iInputID;

    private ArrayList<IResearchTreeComponent> iFollowupTreeComponents = new ArrayList<IResearchTreeComponent>();

    public StandardResearchTreeComponent(ItemStack pTargetStack, String pInputID) {
        iTargetStack = pTargetStack.copy();
        iInputID = pInputID;
    }

    @Override
    public ArrayList<IResearchTreeComponent> getFollowupTreeComponent() {
        return iFollowupTreeComponents;
    }

    @Override
    public IResearchTreeComponent registerNewFollowupTreeComponent(IResearchTreeComponent pNewComponent) {
        iFollowupTreeComponents.add(pNewComponent);

        return pNewComponent;
    }

    @Override
    public IResearchTreeComponent getFollowupComponent(ItemStack pTargetStack, String iInputID, EntityPlayer pPlayer) {
        for (IResearchTreeComponent tBranch : iFollowupTreeComponents) {
            if (tBranch.matchesInput(pTargetStack, iInputID, pPlayer))
                return tBranch;
        }

        return null;
    }

    @Override
    public boolean isFinalComponentInBranch() {
        return false;
    }

    @Override
    public ItemStack getBranchResult() {
        return null;
    }

    @Override
    public boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        return ItemStackHelper.equalsIgnoreStackSize(pTargetStack, iTargetStack) && iInputID.equals(pInputID);
    }
}
