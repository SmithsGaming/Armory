package com.smithsmodding.armory.common.anvil;

import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilMaterial implements IAnvilMaterial {

    String id;
    int durability;
    String translatedDisplayName;
    EnumChatFormatting translatedDisplayNameColor;
    IMaterialRenderInfo info;

    public AnvilMaterial (String id, int durability, String translatedDisplayName) {
        this.id = id;
        this.durability = durability;
        this.translatedDisplayName = translatedDisplayName;
        translatedDisplayNameColor = null;
    }

    @Override
    public String getID () {
        return id;
    }

    @Override
    public int durability () {
        return durability;
    }

    @Override
    public String translatedDisplayName () {
        return translatedDisplayName;
    }

    @Override
    public EnumChatFormatting translatedDisplayNameColor () {
        if (translatedDisplayNameColor == null)
            translatedDisplayNameColor = ColorSampler.getChatMinecraftColorSample(info.getVertexColor());

        return translatedDisplayNameColor;
    }

    @Override
    public IMaterialRenderInfo getRenderInfo () {
        return info;
    }

    @Override
    public void setRenderInfo (IMaterialRenderInfo info) {
        this.info = info;
    }

}
