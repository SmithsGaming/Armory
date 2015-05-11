package com.Orion.Armory.Util.Client.GUI;

import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 06.05.2015
 * 19:18
 * <p/>
 * Copyrighted according to Project specific license
 */
public class UIRotation
{

    public float iRotateX = 0F;
    public float iRotateY = 0F;
    public float iRotateZ = 0F;

    public float iAngle = 0F;


    public UIRotation(boolean pRotateX, boolean pRotateY, boolean pRotateZ, float pAngle)
    {
        if (pRotateX)
            iRotateX = 1F;

        if (pRotateY)
            iRotateY = 1F;

        if (pRotateZ)
            iRotateZ = 1F;

        iAngle = pAngle;

    }

    public void performGLRotation()
    {
        GL11.glRotatef(iAngle, iRotateX, iRotateY, iRotateZ);
    }

    public void performGLRotationReset()
    {
        GL11.glRotatef(-iAngle, iRotateX, iRotateY, iRotateZ);
    }

}
