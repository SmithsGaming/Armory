/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.knowledge;

import com.smithsmodding.armory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import java.util.*;

public class KnowledgeRegistry {
    private static KnowledgeRegistry INSTANCE;

    private HashMap<String, Class<? extends IKnowledgedGameElement>> iKnowableGameElements = new HashMap<String, Class<? extends IKnowledgedGameElement>>();
    private HashMap<UUID, KnowledgeEntityProperty> iSavedKnowledge = new HashMap<UUID, KnowledgeEntityProperty>();
    private HashMap<String, IResearchTreeComponent> iRootTreeComponents = new HashMap<String, IResearchTreeComponent>();

    public static KnowledgeRegistry getInstance () {
        if (INSTANCE == null)
            INSTANCE = new KnowledgeRegistry();

        return INSTANCE;
    }

    public Set<String> getRegisteredKnowledgeNames () {
        return iKnowableGameElements.keySet();
    }

    public IKnowledgedGameElement getNewKnowledgedGameElement (String pKnowledgeName) {
        if (!iKnowableGameElements.containsKey(pKnowledgeName))
            return null;

        Class iExperiencedCraftingClass = iKnowableGameElements.get(pKnowledgeName);
        try {
            return (IKnowledgedGameElement) iExperiencedCraftingClass.getConstructor().newInstance();
        } catch (Exception ex) {
            Armory.getLogger().error("Could not create a knowledge for: " + pKnowledgeName + " the class associated with the knowledge does not have a empty constructor.", ex);
            return null;
        }
    }

    public void registerNewPossibleKnowledge (IKnowledgedGameElement pRecipe) {
        iKnowableGameElements.put(pRecipe.getSaveKey(), pRecipe.getClass());
    }

    public void storePlayerKnowledge (EntityPlayer pPlayer, KnowledgeEntityProperty pProperty) {
        iSavedKnowledge.put(pPlayer.getGameProfile().getId(), pProperty);
    }

    public KnowledgeEntityProperty retrievePlayerKnowledge (EntityPlayer pPlayer) {
        return iSavedKnowledge.remove(pPlayer.getGameProfile().getId());
    }

    public void registerNewRootElement (IResearchTreeComponent pNewRootElement) {
        iRootTreeComponents.put(pNewRootElement.getID(), pNewRootElement);
    }

    public IResearchTreeComponent getRootElement (ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer) {
        for (IResearchTreeComponent tRoot : iRootTreeComponents.values()) {
            if (tRoot.matchesInput(pTargetStack, pInputID, pPlayer)) {
                return tRoot;
            }
        }

        return null;
    }

    public void registerNewResearchBranch (IResearchTreeComponent pRootComponent) {
        HashMap<String, IResearchTreeComponent> tComponents = iRootTreeComponents;

        IResearchTreeComponent tLastVisitedNode = pRootComponent;
        IResearchTreeComponent tCurrentNode = pRootComponent;

        while (tComponents.values().contains(tCurrentNode) && !tCurrentNode.isFinalComponentInBranch()) {
            tComponents = tComponents.get(tCurrentNode.getID()).getFollowupTreeComponent();
            tLastVisitedNode = tCurrentNode;
            tCurrentNode = (IResearchTreeComponent) tCurrentNode.getFollowupTreeComponent().values().toArray()[0];
        }

        if (tCurrentNode.isFinalComponentInBranch()) {
            Armory.getLogger().warn("A research branch is being overriden! A endbranch has been reach while the old branch had nodes left. Overriding!");
            tLastVisitedNode.getFollowupTreeComponent().clear();
            tLastVisitedNode.registerNewFollowupTreeComponent(tCurrentNode);
        } else {
            for (IResearchTreeComponent tBranchComponent : tComponents.values()) {
                if (tBranchComponent.isFinalComponentInBranch()) {
                    Armory.getLogger().warn("Could not register new research branch. The registry has already a Branch path that terminates Earlier.");
                    return;
                }
            }

            if (pRootComponent.equals(tCurrentNode)) {
                registerNewRootElement(pRootComponent);
            } else {
                tLastVisitedNode.registerNewFollowupTreeComponent(tCurrentNode);
            }
        }
    }
}
