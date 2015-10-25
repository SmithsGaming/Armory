/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Knowledge.Research;

import com.SmithsModding.Armory.API.Knowledge.IResearchTreeComponent;
import com.SmithsModding.Armory.Common.Item.Knowledge.ItemSmithingsGuide;
import com.SmithsModding.Armory.Common.Item.Knowledge.LabelledBlueprintGroup;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.Core.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugResearchTreeComponent implements IResearchTreeComponent {

    protected ItemStack iTargetStack;
    protected String iInputID;

    private HashMap<String, IResearchTreeComponent> iFollowupTreeComponents = new HashMap<String, IResearchTreeComponent>();

    public DebugResearchTreeComponent(String pInputID) {
        iTargetStack = new ItemStack(GeneralRegistry.Items.iSmithingsGuide, 1);
        iInputID = pInputID;
    }

    @Override
    public String getID() {
        return iInputID + ".Debug-" + ItemStackHelper.toString(iTargetStack);
    }

    @Override
    public String getInputId() {
        return iInputID;
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
        return true;
    }

    @Override
    public ItemStack getBranchResult(EntityPlayer pPlayer) {
        ItemStack tCheatStack = new ItemStack(GeneralRegistry.Items.iSmithingsGuide);
        GeneralRegistry.Items.iSmithingsGuide.initializeContainer(tCheatStack);

        ArrayList<ItemStack> tBlueprints = new ArrayList<ItemStack>();
        GeneralRegistry.Items.iBlueprints.getSubItems(null, null, tBlueprints);

        ArrayList<LabelledBlueprintGroup> tGroups = GeneralRegistry.Items.iSmithingsGuide.getBlueprintGroups(tCheatStack);
        tGroups.get(0).Stacks = tBlueprints;

        GeneralRegistry.Items.iSmithingsGuide.writeBlueprintGroupsToStack(tCheatStack, tGroups);

        return tCheatStack;
    }

    @Override
    public boolean matchesInput(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        return pTargetStack.getItem() instanceof ItemSmithingsGuide && iInputID.equals(pInputID) && (GeneralRegistry.iIsInDevEnvironment || pPlayer.getGameProfile().getName().equals("OrionOnline"));
    }

    @Override
    public String getDisplayString() {
        return "DEBUG Completed Succesfully!";
    }

    @Override
    public ItemStack getTargetStack() {
        return iTargetStack;
    }

    @Override
    public void renderComponent() {

    }
}
