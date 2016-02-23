package com.smithsmodding.armory.api.materials;

import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IAnvilMaterial
{
    String getID();

    int durability();

    String translatedDisplayName();

    EnumChatFormatting translatedDisplayNameColor();

    IMaterialRenderInfo getRenderInfo();

    void setRenderInfo(IMaterialRenderInfo info);
}
