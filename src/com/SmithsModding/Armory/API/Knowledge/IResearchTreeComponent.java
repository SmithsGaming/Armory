/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.knowledge;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

public interface IResearchTreeComponent {

    String getID ();

    String getInputId ();

    HashMap<String, IResearchTreeComponent> getFollowupTreeComponent ();

    IResearchTreeComponent registerNewFollowupTreeComponent (IResearchTreeComponent pNewComponent);

    IResearchTreeComponent getFollowupComponent (ItemStack pTargetStack, String iInputID, EntityPlayer pPlayer);

    boolean isFinalComponentInBranch ();

    ItemStack getBranchResult (EntityPlayer pPlayer);

    boolean matchesInput (ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer);

    String getDisplayString ();

    int hashCode ();

    boolean equals (Object pComponent);

    ItemStack getTargetStack ();

    @SideOnly(Side.CLIENT)
    void renderComponent ();
}
