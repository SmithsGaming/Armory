package com.Orion.Armory.Client.Models;

import com.Orion.Armory.Util.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 00:26
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ModelAnvil
{
    public IModelCustom iModelCustom;

    public ModelAnvil()
    {
        this.iModelCustom = AdvancedModelLoader.loadModel(new ResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + References.Models.ModelLocations.Anvil));
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
