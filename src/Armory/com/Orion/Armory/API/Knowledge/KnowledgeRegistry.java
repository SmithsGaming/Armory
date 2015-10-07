/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class KnowledgeRegistry {
    private static KnowledgeRegistry INSTANCE;

    private HashMap<String, Class<? extends IKnowledgedGameElement>> iKnowableGameElements = new HashMap<String, Class<? extends IKnowledgedGameElement>>();
    private HashMap<UUID, KnowledgeEntityProperty> iSavedKnowledge = new HashMap<UUID, KnowledgeEntityProperty>();
    private HashMap<String, IResearchTreeComponent> iRootTreeComponents = new HashMap<String, IResearchTreeComponent>();

    public static KnowledgeRegistry getInstance() {
        if (INSTANCE == null)
            INSTANCE = new KnowledgeRegistry();

        return INSTANCE;
    }

    public Set<String> getRegisteredKnowledgeNames() {
        return iKnowableGameElements.keySet();
    }

    public IKnowledgedGameElement getNewKnowledgedGameElement(String pKnowledgeName) {
        if (!iKnowableGameElements.containsKey(pKnowledgeName))
            return null;

        Class iExperiencedCraftingClass = iKnowableGameElements.get(pKnowledgeName);
        try {
            return (IKnowledgedGameElement) iExperiencedCraftingClass.getConstructor().newInstance();
        } catch (Exception ex) {
            GeneralRegistry.iLogger.error("Could not create a Knowledge for: " + pKnowledgeName + " the class associated with the Knowledge does not have a empty constructor.", ex);
            return null;
        }
    }

    public void registerNewPossibleKnowledge(IKnowledgedGameElement pRecipe) {
        iKnowableGameElements.put(pRecipe.getSaveKey(), pRecipe.getClass());
    }

    public void storePlayerKnowledge(EntityPlayer pPlayer, KnowledgeEntityProperty pProperty) {
        iSavedKnowledge.put(pPlayer.getGameProfile().getId(), pProperty);
    }

    public KnowledgeEntityProperty retrievePlayerKnowledge(EntityPlayer pPlayer) {
        return iSavedKnowledge.remove(pPlayer.getGameProfile().getId());
    }

    public void registerNewRootElement(IResearchTreeComponent pNewRootElement)
    {
        iRootTreeComponents.put(pNewRootElement.getID(), pNewRootElement);
    }

    public IResearchTreeComponent getRootElement(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer)
    {
        for (IResearchTreeComponent tRoot : iRootTreeComponents.values())
        {
            if (tRoot.matchesInput(pTargetStack, pInputID, pPlayer))
            {
                return tRoot;
            }
        }

        return null;
    }

    public void registerNewResearchBranch(IResearchTreeComponent pRootComponent) {
        HashMap<String, IResearchTreeComponent> tComponents = iRootTreeComponents;

        IResearchTreeComponent tLastVisitedNode = pRootComponent;
        IResearchTreeComponent tCurrentNode = pRootComponent;

        while (tComponents.values().contains(tCurrentNode) && !tCurrentNode.isFinalComponentInBranch()) {
            tComponents = tComponents.get(tCurrentNode.getID()).getFollowupTreeComponent();
            tLastVisitedNode = tCurrentNode;
            tCurrentNode = (IResearchTreeComponent) tCurrentNode.getFollowupTreeComponent().values().toArray()[0];
        }

        if (tCurrentNode.isFinalComponentInBranch()) {
            GeneralRegistry.iLogger.warn("A research branch is being overriden! A endbranch has been reach while the old branch had nodes left. Overriding!");
            tLastVisitedNode.getFollowupTreeComponent().clear();
            tLastVisitedNode.registerNewFollowupTreeComponent(tCurrentNode);
        } else {
            for (IResearchTreeComponent tBranchComponent : tComponents.values()) {
                if (tBranchComponent.isFinalComponentInBranch()) {
                    GeneralRegistry.iLogger.warn("Could not register new research branch. The registry has already a Branch path that terminates Earlier.");
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
