package com.SmithsModding.Armory.Util.Client.Color;

import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;


/**
 * Created by Orion
 * Created on 14.06.2015
 * 12:15
 * <p/>
 * Copyrighted according to Project specific license
 */
public class IconPlaceHolder implements IIcon {

    public ResourceLocation iIconLocation;

    public IconPlaceHolder(ResourceLocation tLocation) {
        iIconLocation = new ResourceLocation(tLocation.getResourceDomain(), "textures/items/" + tLocation.getResourcePath() + ".png");
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }

    @Override
    public float getMinU() {
        return 0;
    }

    @Override
    public float getMaxU() {
        return 0;
    }

    @Override
    public float getInterpolatedU(double p_94214_1_) {
        return 0;
    }

    @Override
    public float getMinV() {
        return 0;
    }

    @Override
    public float getMaxV() {
        return 0;
    }

    @Override
    public float getInterpolatedV(double p_94207_1_) {
        return 0;
    }

    @Override
    public String getIconName() {
        return null;
    }
}
