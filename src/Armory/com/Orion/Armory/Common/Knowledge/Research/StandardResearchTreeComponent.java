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

import java.util.HashMap;

public abstract class StandardResearchTreeComponent implements IResearchTreeComponent {

    protected ItemStack iTargetStack;
    protected String iInputID;

    private HashMap<String, IResearchTreeComponent> iFollowupTreeComponents = new HashMap<String, IResearchTreeComponent>();

    public StandardResearchTreeComponent(ItemStack pTargetStack, String pInputID) {
        iTargetStack = pTargetStack.copy();
        iInputID = pInputID;
    }

    @Override
    public String getInputId() {
        return iInputID;
    }

    @Override
    public String getID() {
        return iInputID + "-" + ItemStackHelper.toString(iTargetStack);
    }

    @Override
    public HashMap<String, IResearchTreeComponent> getFollowupTreeComponent() {
        return iFollowupTreeComponents;
    }

    @Override
    public IResearchTreeComponent registerNewFollowupTreeComponent(IResearchTreeComponent pNewComponent) {
        iFollowupTreeComponents.put(pNewComponent.getID(), pNewComponent);

        return pNewComponent;
    }

    @Override
    public IResearchTreeComponent getFollowupComponent(ItemStack pTargetStack, String iInputID, EntityPlayer pPlayer) {
        for (IResearchTreeComponent tBranch : iFollowupTreeComponents.values()) {
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
    public ItemStack getBranchResult(EntityPlayer pPlazer) {
        return null;
    }

    @Override
    public ItemStack getTargetStack() {
        return iTargetStack;
    }

    @Override
    public boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        return ItemStackHelper.equalsIgnoreStackSize(pTargetStack, iTargetStack) && iInputID.equals(pInputID);
    }

    @Override
    public int hashCode() {
        return iInputID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IResearchTreeComponent))
            return false;

        return getID().equals(((IResearchTreeComponent) obj).getID()) && ItemStackHelper.equalsIgnoreStackSize(iTargetStack, ((IResearchTreeComponent) obj).getTargetStack());
    }
}
