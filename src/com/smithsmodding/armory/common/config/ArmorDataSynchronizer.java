package com.smithsmodding.armory.common.config;

import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.addons.MedievalAddonRegistry;
import com.smithsmodding.armory.common.event.config.MaterialPropertyValueEvent;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.smithscore.common.events.network.NetworkableEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:48
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmorDataSynchronizer {

    public void loadIDs(EntityPlayer connectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MaterialPropertyValueEvent(tMaterial.getUniqueID(), "setItemDamageMaterialIndex", new String[]{"Integer"}, new Object[]{tMaterial.getItemDamageMaterialIndex()}), (EntityPlayerMP) connectingPlayer);
    }

    public void loadIsBaseArmorMaterial(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setIsBaseArmorMaterial", new String[]{"Boolean"}, new Object[]{material.getIsBaseArmorMaterial()}), (EntityPlayerMP) connectingPlayer);
    }

    public void loadActiveParts(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Iterator iter = MedievalAddonRegistry.getInstance().getPartStatesForMaterial(material).entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<MLAAddon, Boolean> entry = (Map.Entry<MLAAddon, Boolean>) iter.next();

                sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "modifyPartState", new String[]{"String", "Boolean"}, new Object[]{entry.getKey().getUniqueID(), entry.getValue()}), (EntityPlayerMP) connectingPlayer);
            }
        }
    }

    public void loadBaseDamageAbsorptions(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Iterator iter = material.getAllBaseDamageAbsorptionValues().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Float> entry = (Map.Entry<String, Float>) iter.next();

                sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setBaseDamageAbsorption", new String[]{"String", "Float"}, new Object[]{entry.getKey(), entry.getValue()}), (EntityPlayerMP) connectingPlayer);
            }
        }
    }

    public void loadPartModifiers(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Iterator iter = material.getAllMaxModifiersAmounts().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();

                sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setMaxModifiersOnPart", new String[]{"String", "Integer"}, new Object[]{entry.getKey(), entry.getValue()}), (EntityPlayerMP) connectingPlayer);
            }
        }
    }

    public void loadBaseDurability(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            Iterator iter = material.getAllBaseDurabilityValues().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();

                sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setBaseDurability", new String[]{"String", "Integer"}, new Object[]{entry.getKey(), entry.getValue()}), (EntityPlayerMP) connectingPlayer);
            }
        }
    }

    public void loadTemperatureCoefficient(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setHeatCoefficient", new String[]{"Float"}, new Object[]{material.getHeatCoefficient()}), (EntityPlayerMP) connectingPlayer);
    }

    public void loadMeltingPoint(EntityPlayer connectingPlayer) {
        for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MaterialPropertyValueEvent(material.getUniqueID(), "setMeltingPoint", new String[]{"Float"}, new Object[]{material.getMeltingPoint()}), (EntityPlayerMP) connectingPlayer);
    }

    public void sendMessage(NetworkableEvent eventMessage, EntityPlayerMP player) {
        if (player == null) {
            eventMessage.handleClientToServerSide();
            return;
        }

        eventMessage.handleServerToClient(player);
    }
}
