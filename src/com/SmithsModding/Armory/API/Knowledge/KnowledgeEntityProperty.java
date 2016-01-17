/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.Armory.API.Knowledge;

import com.smithsmodding.Armory.API.Entity.*;
import com.smithsmodding.Armory.Util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import java.util.*;

public class KnowledgeEntityProperty implements ICustomEntityProperty {

    private HashMap<String, IKnowledgedGameElement> iKnownGameElements = new HashMap<String, IKnowledgedGameElement>();
    private Entity iParentEntity;

    @Override
    public Entity getEntity () {
        return iParentEntity;
    }

    @Override
    public void register (Entity pEntity) {
        if (pEntity instanceof EntityPlayer) {
            pEntity.registerExtendedProperties(getSaveKey(), this);
            iParentEntity = pEntity;
        }
    }

    @Override
    public <T extends IExtendedEntityProperties> T get (Entity pEntity) {
        if (pEntity instanceof EntityPlayer) {
            return (T) pEntity.getExtendedProperties(getSaveKey());
        }

        return null;
    }

    @Override
    public String getSaveKey () {
        return References.InternalNames.ExtendedEntityProperties.KNOWLEDGE;
    }

    @Override
    public void saveProxyData () {
        KnowledgeRegistry.getInstance().storePlayerKnowledge((EntityPlayer) getEntity(), this);
    }

    @Override
    public void loadProxyData () {
        KnowledgeEntityProperty tOldData = KnowledgeRegistry.getInstance().retrievePlayerKnowledge((EntityPlayer) iParentEntity);
        this.iKnownGameElements = tOldData.iKnownGameElements;
    }

    @Override
    public void syncProperties () {
        for (IKnowledgedGameElement tElement : iKnownGameElements.values()) {
            //NetworkManager.INSTANCE.sendTo(new MessageKnowledgeUpdate(tElement.getSaveKey(), tElement.getExperienceLevel().toString()), (EntityPlayerMP) getEntity());
        }
    }

    @Override
    public void saveNBTData (NBTTagCompound compound) {
        NBTTagCompound tKnowledgeCompound = new NBTTagCompound();

        for (IKnowledgedGameElement tElement : iKnownGameElements.values()) {
            tElement.saveToNBT(tKnowledgeCompound);
        }

        compound.setTag(getSaveKey(), tKnowledgeCompound);
    }

    @Override
    public void loadNBTData (NBTTagCompound compound) {
        NBTTagCompound tKnowledgeCompound = compound.getCompoundTag(References.InternalNames.ExtendedEntityProperties.KNOWLEDGE);

        for (String tKnowledgeName : KnowledgeRegistry.getInstance().getRegisteredKnowledgeNames()) {
            if (tKnowledgeCompound.hasKey(tKnowledgeName)) {
                IKnowledgedGameElement tElement = KnowledgeRegistry.getInstance().getNewKnowledgedGameElement(tKnowledgeName);
                tElement.readFromNBT(tKnowledgeCompound);
                iKnownGameElements.put(tElement.getSaveKey(), tElement);
            }
        }
    }

    @Override
    public void init (Entity entity, World world) {
        iParentEntity = entity;
    }

    public IKnowledgedGameElement getKnowledge (String pKnowledgeName) {
        return iKnownGameElements.get(pKnowledgeName);
    }

    public void setKnowledge (IKnowledgedGameElement pKnowledge) {
        iKnownGameElements.put(pKnowledge.getSaveKey(), pKnowledge);
        //NetworkManager.INSTANCE.sendTo(new MessageKnowledgeUpdate(pKnowledge.getSaveKey(), pKnowledge.getExperienceLevel().toString()), (EntityPlayerMP) getEntity());
    }
}
