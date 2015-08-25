package com.Orion.Armory.Util.Client.GUI;

import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Core.Coordinate;

/**
 * Created by Orion
 * Created on 12.05.2015
 * 15:02
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MultiComponentTexture
{
    TextureComponent iCenterComponent;
    TextureComponent[] iCornerComponents = new TextureComponent[4];
    TextureComponent[] iSideComponents = new TextureComponent[4];

    public MultiComponentTexture(TextureComponent pCenterComponent, TextureComponent[] pCornerComponents, TextureComponent[] pSideComponents)
    {
        iCenterComponent = pCenterComponent;
        iCornerComponents = pCornerComponents;
        iSideComponents = pSideComponents;
    }

    public MultiComponentTexture(CustomResource pSource, int pTotalWidth, int pTotalHeight, int pCornerWidth, int pCornerHeight)
    {
        iCenterComponent = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth, pSource.getV() + pCornerHeight, pTotalWidth - (pCornerWidth * 2), pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate(0,0,0));

        iCornerComponents[0] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU(), pSource.getV(), pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        iCornerComponents[1] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - pCornerWidth, pSource.getV(), pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        iCornerComponents[2] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - pCornerWidth, pSource.getV() + pTotalHeight - pCornerHeight, pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        iCornerComponents[3] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU(), pSource.getV() + pTotalHeight - pCornerHeight, pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));

        iSideComponents[0] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth                    , pSource.getV()                                   , pTotalWidth - (pCornerWidth * 2)  , pCornerHeight                     , new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        iSideComponents[1] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - (pCornerWidth), pSource.getV() + pCornerHeight, pCornerWidth, pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate(0, 0, 0));
        iSideComponents[2] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth, pSource.getV() + pTotalHeight - pCornerHeight, pTotalWidth - (pCornerWidth * 2), pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate(0, 0, 0));
        iSideComponents[3] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU()                                   , pSource.getV() + pCornerHeight                   , pCornerWidth                      , pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
    }

}
