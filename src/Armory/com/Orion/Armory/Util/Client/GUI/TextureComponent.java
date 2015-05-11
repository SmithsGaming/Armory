package com.Orion.Armory.Util.Client.GUI;

import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.GUI.UIRotation;
import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.client.renderer.texture.TextureMap;

/**
 * Created by Orion
 * Created on 06.05.2015
 * 19:17
 * <p/>
 * Copyrighted according to Project specific license
 */
public class TextureComponent
{

    public int iU = 0;
    public int iV = 0;
    public int iWidth = 0;
    public int iHeight = 0;

    public Coordinate iRelativeTranslation = new Coordinate(0,0,0);

    public UIRotation iRotation = new UIRotation(false, false, false, 0F);

    public String iAddress = TextureMap.locationBlocksTexture.getResourcePath();

    public TextureComponent(String pAddress, int pU, int pV, int pWidth, int pHeight, UIRotation pRotation, Coordinate pRelateTranslation)
    {
        iAddress = pAddress;
        iU = pU;
        iV = pV;
        iWidth = pWidth;
        iHeight = pHeight;
        iRotation = pRotation;
        iRelativeTranslation = pRelateTranslation;
    }

    public TextureComponent(CustomResource pResource, UIRotation pRotation, Coordinate pRelativeTranslation)
    {
        iAddress = pResource.getPrimaryLocation();
        iU = pResource.getU();
        iV = pResource.getV();
        iWidth = pResource.getWidth();
        iHeight = pResource.getHeigth();
        iRotation = pRotation;
        iRelativeTranslation = pRelativeTranslation;
    }
}
