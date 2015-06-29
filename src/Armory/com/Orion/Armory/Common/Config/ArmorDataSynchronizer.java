package com.Orion.Armory.Common.Config;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Network.ConfigNetworkManager;
import com.Orion.Armory.Network.Messages.Config.MessageMaterialPropertyValue;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
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
public class ArmorDataSynchronizer{

    public void loadIsBaseArmorMaterial(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setIsBaseArmorMaterial", new String[]{"Boolean"}, new Object[]{tMaterial.getIsBaseArmorMaterial()}), (EntityPlayerMP) pConnectingPlayer);
    }

    public void loadActiveParts(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Iterator tIter = tMaterial.getAllPartStates().entrySet().iterator();
            while (tIter.hasNext())
            {
                Map.Entry<String, Boolean> tEntry = (Map.Entry<String, Boolean>) tIter.next();

                sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "modifyPartState", new String[]{"String", "Boolean"}, new Object[]{tEntry.getKey(), tEntry.getValue()}), (EntityPlayerMP) pConnectingPlayer);
            }
        }
    }

    public void loadBaseDamageAbsorptions(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Iterator tIter = tMaterial.getAllBaseDamageAbsorbtionValues().entrySet().iterator();
            while (tIter.hasNext())
            {
                Map.Entry<String, Float> tEntry = (Map.Entry<String, Float>) tIter.next();

                sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setBaseDamageAbsorption", new String[]{"String", "Float"}, new Object[]{tEntry.getKey(), tEntry.getValue()}), (EntityPlayerMP) pConnectingPlayer);
            }
        }
    }

    public void loadPartModifiers(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Iterator tIter = tMaterial.getAllMaxModifiersAmounts().entrySet().iterator();
            while (tIter.hasNext())
            {
                Map.Entry<String, Integer> tEntry = (Map.Entry<String, Integer>) tIter.next();

                sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setMaxModifiersOnPart", new String[]{"String", "Integer"}, new Object[]{tEntry.getKey(), tEntry.getValue()}), (EntityPlayerMP) pConnectingPlayer);
            }
        }
    }

    public void loadBaseDurability(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            Iterator tIter = tMaterial.getAllBaseDurabilityValues().entrySet().iterator();
            while (tIter.hasNext())
            {
                Map.Entry<String, Integer> tEntry = (Map.Entry<String, Integer>) tIter.next();

                sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setBaseDurability", new String[]{"String", "Integer"}, new Object[]{tEntry.getKey(), tEntry.getValue()}), (EntityPlayerMP) pConnectingPlayer);
            }
        }
    }

    public void loadTemperatureCoefficient(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setHeatCoefficient", new String[]{"Float"}, new Object[]{tMaterial.getHeatCoefficient()}), (EntityPlayerMP) pConnectingPlayer);
    }

    public void loadMeltingPoint(EntityPlayer pConnectingPlayer) {
        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
            sendMessage(new MessageMaterialPropertyValue(tMaterial.getInternalMaterialName(), "setMeltingPoint", new String[]{"Float"}, new Object[]{tMaterial.getMeltingPoint()}), (EntityPlayerMP) pConnectingPlayer);
    }

    public void sendMessage(IMessage pMessage, EntityPlayerMP pPlayer)
    {
        if (pPlayer == null)
        {
            ConfigNetworkManager.INSTANCE.sendToServer(pMessage);
            return;
        }
        
        ConfigNetworkManager.INSTANCE.sendTo(pMessage, pPlayer);
    }
}
