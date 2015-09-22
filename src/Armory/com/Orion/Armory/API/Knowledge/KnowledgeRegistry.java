/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import com.Orion.Armory.Common.Registry.GeneralRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class KnowledgeRegistry {
    private static KnowledgeRegistry INSTANCE;

    private HashMap<String, Class<? extends IKnowledgedGameElement>> iKnowableGameElements = new HashMap<String, Class<? extends IKnowledgedGameElement>>();
    private HashMap<UUID, KnowledgeEntityProperty> iSavedKnowledge = new HashMap<UUID, KnowledgeEntityProperty>();
    private ArrayList<IResearchTreeComponent> iRootTreeComponents = new ArrayList<IResearchTreeComponent>();

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

    void storePlayerKnowledge(EntityPlayer pPlayer, KnowledgeEntityProperty pProperty) {
        iSavedKnowledge.put(pPlayer.getGameProfile().getId(), pProperty);
    }

    KnowledgeEntityProperty retrievePlayerKnowledge(EntityPlayer pPlayer) {
        return iSavedKnowledge.remove(pPlayer.getGameProfile().getId());
    }

    void registerNewRootElement(IResearchTreeComponent pNewRootElement)
    {
        iRootTreeComponents.add(pNewRootElement);
    }

    IResearchTreeComponent getRootElement(ItemStack pTargetStack, String pInputID, EntityPlayer pPlayer)
    {
        for (IResearchTreeComponent tRoot : iRootTreeComponents)
        {
            if (tRoot.matchesInput(pTargetStack, pInputID, pPlayer))
            {
                return tRoot;
            }
        }

        return null;
    }
}
