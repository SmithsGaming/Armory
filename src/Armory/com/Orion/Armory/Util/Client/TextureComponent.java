package com.Orion.Armory.Util.Client;

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

    public UIRotation iRotation = new UIRotation(false, false, false, 0F);

    public String iAddress = TextureMap.locationBlocksTexture.getResourcePath();

    public TextureComponent(String pAddress, int pU, int pV, int pWidth, int pHeight, UIRotation pRotation)
    {
        iAddress = pAddress;
        iU = pU;
        iV = pV;
        iWidth = pWidth;
        iHeight = pHeight;
        iRotation = pRotation;
    }

    public TextureComponent(CustomResource pResource, UIRotation pRotation)
    {
        iAddress = pResource.getPrimaryLocation();
        iU = pResource.getU();
        iV = pResource.getV();
        iWidth = pResource.getWidth();
        iHeight = pResource.getHeigth();
        iRotation = pRotation;
    }
}
