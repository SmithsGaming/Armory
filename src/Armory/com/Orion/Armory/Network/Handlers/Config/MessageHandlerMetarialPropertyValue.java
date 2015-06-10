package com.Orion.Armory.Network.Handlers.Config;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Network.Messages.Config.MessageMaterialPropertyValue;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:40
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerMetarialPropertyValue implements IMessageHandler<MessageMaterialPropertyValue, IMessage> {
    @Override
    public IMessage onMessage(MessageMaterialPropertyValue message, MessageContext ctx) {
        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(message.iMaterialName);
        if (tMaterial == null)
            return null;

        for(Method tMethod : tMaterial.getClass().getDeclaredMethods())
        {
            if (tMethod.getName().equals(message.iPropertyName))
            {
                if (tMethod.getGenericParameterTypes().length == message.iServerSidedValue.length)
                {
                    try {
                        tMethod.setAccessible(true);
                        tMethod.invoke(tMaterial, message.iServerSidedValue);

                        return null;
                    } catch (InvocationTargetException x) {
                        return null;
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                }
            }
        }

        return null;
    }
}
