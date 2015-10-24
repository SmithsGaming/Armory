package com.SmithsModding.Armory.Client.Models;
/*
 *   ModelFirePit
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.SmithsModding.Armory.Util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelFirePit {
    public IModelCustom iModelCustom;

    public ModelFirePit() {
        this.iModelCustom = AdvancedModelLoader.loadModel(new ResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.Models.ModelLocations.FirePit));
    }

    public void renderAll() {
        iModelCustom.renderAll();
    }

    public void renderPart(String pPartName) {
        iModelCustom.renderPart(pPartName);
    }
}
