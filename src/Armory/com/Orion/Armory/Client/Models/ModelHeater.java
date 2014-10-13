package com.Orion.Armory.Client.Models;
/*
 *   ModelHeater
 *   Created by: Orion
 *   Created on: 12-10-2014
 */

import com.Orion.Armory.Util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelHeater
{
    public IModelCustom iModelCustom;

    public ModelHeater()
    {
        this.iModelCustom = AdvancedModelLoader.loadModel(new ResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.Models.ModelLocations.FirePit));
    }

    public void renderAll()
    {
        iModelCustom.renderAll();
    }

    public void renderPart(String pPartName)
    {
        iModelCustom.renderPart(pPartName);
    }
}
