package com.smithsmodding.armory.api.materials;

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

    IMaterialRenderInfo getRenderInfo();

    void setRenderInfo(IMaterialRenderInfo info);
}
