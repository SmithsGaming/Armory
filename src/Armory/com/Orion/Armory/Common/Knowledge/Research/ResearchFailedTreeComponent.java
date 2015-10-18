/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.Knowledge.Research;

import com.Orion.Armory.API.Knowledge.IResearchTreeComponent;
import com.Orion.Armory.Util.Client.TranslationKeys;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.HashMap;

public class ResearchFailedTreeComponent implements IResearchTreeComponent {

    @Override
    public String getID() {
        return "Research.Failed";
    }

    @Override
    public String getInputId() {
        return "Research.Failed";
    }

    @Override
    public HashMap<String, IResearchTreeComponent> getFollowupTreeComponent() {
        return new HashMap<String, IResearchTreeComponent>();
    }

    @Override
    public IResearchTreeComponent registerNewFollowupTreeComponent(IResearchTreeComponent pNewComponent) {
        return this;
    }

    @Override
    public IResearchTreeComponent getFollowupComponent(ItemStack pTargetStack, String iInputID, EntityPlayer pPlayer) {
        return this;
    }

    @Override
    public boolean isFinalComponentInBranch() {
        return true;
    }

    @Override
    public ItemStack getBranchResult(EntityPlayer pPlayer) {
        return null;
    }

    @Override
    public boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        return false;
    }

    @Override
    public String getDisplayString() {
        return StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.Research.ResearchFailed);
    }

    @Override
    public ItemStack getTargetStack() {
        return null;
    }
}
