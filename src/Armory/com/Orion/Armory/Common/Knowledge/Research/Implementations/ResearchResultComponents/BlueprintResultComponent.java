package com.Orion.Armory.Common.Knowledge.Research.Implementations.ResearchResultComponents;

import com.Orion.Armory.API.Knowledge.IKnowledgedGameElement;
import com.Orion.Armory.API.Knowledge.KnowledgeEntityProperty;
import com.Orion.Armory.Common.Knowledge.Research.FinalResearchTreeComponent;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * Created by marcf on 9/29/2015.
 */
public class BlueprintResultComponent extends FinalResearchTreeComponent {

    ArrayList<String> iRequiredKnowledge = new ArrayList<String>();
    String iResultBlueprintID;

    public BlueprintResultComponent(ItemStack pTargetStack, String pResultBlueprintID) {
        super(pTargetStack);

        iResultBlueprintID = pResultBlueprintID;
    }

    public BlueprintResultComponent regsisterRequiredKnowledge(String iKnowledgeID) {
        iRequiredKnowledge.add(iKnowledgeID);

        return this;
    }

    @Override
    public ItemStack getBranchResult(EntityPlayer pPlayer) {
        float tTotalKnowledge = 0F;

        if (iRequiredKnowledge.size() > 0) {
            KnowledgeEntityProperty tKnowledge = (new KnowledgeEntityProperty()).get(pPlayer);

            if (tKnowledge == null)
                return null;


            for (String tKnowledgeID : iRequiredKnowledge) {
                IKnowledgedGameElement tBlueprintKnowledge = tKnowledge.getKnowledge(tKnowledgeID);

                if (tBlueprintKnowledge == null)
                    return null;

                tTotalKnowledge += tBlueprintKnowledge.getExperienceLevel();
            }

            if (tTotalKnowledge <= 0F)
                return null;

            tTotalKnowledge /= iRequiredKnowledge.size();
        } else {
            tTotalKnowledge = 0.55F;
        }

        ItemStack tStack = new ItemStack(GeneralRegistry.Items.iBlueprints, 1);
        NBTTagCompound tCompound = new NBTTagCompound();
        tCompound.setDouble(References.NBTTagCompoundData.Item.Blueprints.FLOATVALUE, tTotalKnowledge);
        tCompound.setString(References.NBTTagCompoundData.Item.Blueprints.BLUEPRINTID, iResultBlueprintID);

        tStack.setTagCompound(tCompound);

        return tStack;
    }
}
