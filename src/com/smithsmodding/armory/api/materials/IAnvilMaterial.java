package com.smithsmodding.armory.api.materials;

import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IAnvilMaterial
{
    String getID();

    int durability();

    String translatedDisplayName();

    TextFormatting translatedDisplayNameColor();

    ITextureController getRenderInfo();

    void setRenderInfo(ITextureController info);
}
