package com.smithsmodding.armory.common.handlers.config;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.events.common.config.MaterialPropertyValueEvent;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:40
 *
 * Copyrighted according to Project specific license
 */
public class MaterialPropertyValueEventHandler {

    @SubscribeEvent
    public void onMessage(MaterialPropertyValueEvent event) {
        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(event.getMaterialName());
        if (tMaterial == null)
            return;

        for (Method tMethod : tMaterial.getClass().getDeclaredMethods()) {
            if (tMethod.getName().equals(event.getPropertyName())) {
                if (tMethod.getGenericParameterTypes().length == event.getServerSidedValue().length) {
                    try {
                        tMethod.setAccessible(true);
                        tMethod.invoke(tMaterial, event.getServerSidedValue());

                        break;
                    } catch (InvocationTargetException x) {
                        return;
                    } catch (IllegalAccessException e) {
                        return;
                    }
                }
            }
        }
    }
}
