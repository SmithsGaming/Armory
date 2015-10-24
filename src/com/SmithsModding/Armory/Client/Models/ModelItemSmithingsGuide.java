/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.Models;

import com.SmithsModding.Armory.Util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelItemSmithingsGuide {

    public IModelCustom iModelCustom;

    public ModelItemSmithingsGuide() {
        this.iModelCustom = AdvancedModelLoader.loadModel(new ResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.Models.ModelLocations.SmithingsGuide));
    }

    public void renderAll() {
        iModelCustom.renderAll();
    }

    public void renderPart(String pPartName) {
        iModelCustom.renderPart(pPartName);
    }
}
