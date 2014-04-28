package com.Orion.Armory.Client.Render;
/*
*   RenderPlayerEventHook
*   Created by: Orion
*   Created on: 1-4-2014
*/

import com.Orion.Armory.Common.Armor.ArmorCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderPlayerEventHook
{
    CustomArmorRenderer armorRendererInstance;
    double renderDataX;
    double renderDataY;
    double renderDataZ;
    float partialTickTime;
    RenderManager renderManager;

    //Used to do the actual rendering of the current armormodel
    @SubscribeEvent
    public void SetArmorModelCalled(RenderPlayerEvent.SetArmorModel event)
    {
        if (!(event.entity instanceof EntityPlayer))
        {
            return;
        }

        if (armorRendererInstance == null)
        {
            armorRendererInstance = new CustomArmorRenderer();
        }

        if (event.stack == null)
        {
            return;
        }

        if (!(event.stack.getItem() instanceof ArmorCore))
        {
            return;
        }

        armorRendererInstance.doRender((net.minecraft.entity.EntityLivingBase) event.entity, renderDataX, renderDataY, renderDataZ, event.stack.getItem(), event.stack, event.partialRenderTick);

        event.result = -2;
    }

    //Used for grabbing the rotation data.
    @SubscribeEvent
    public void PreRenderPlayerCalled(RenderLivingEvent.Pre event)
    {
        if (!(event.entity instanceof AbstractClientPlayer))
        {
            return;
        }

        this.renderDataX = event.x;
        this.renderDataY = event.y;
        this.renderDataZ = event.z;

    }



}
