package com.Orion.Armory.Util.Core;
/*
/  Coordinate
/  Created by : Orion
/  Created on : 27-4-2015
*/

import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Coordinate
{
    int iXCoord;
    int iYCoord;
    int iZCoord;

    public Coordinate(int pXCoord, int pYCoord)
    {
        this(pXCoord, pYCoord, 0);
    }

    public Coordinate(int pXCoord, int pYCoord, int pZCoord)
    {
        iXCoord = pXCoord;
        iYCoord = pYCoord;
        iZCoord = pZCoord;
    }

    public int getXComponent() { return iXCoord; }

    public int getYComponent() { return iYCoord; }

    public int getZComponent() { return iZCoord; }

    public ArrayList<ForgeDirection> getDirection(Coordinate pCenter)
    {
        if ((this.getXComponent() == pCenter.getXComponent()) && (this.getYComponent() == pCenter.getYComponent()) && (this.getZComponent() == pCenter.getZComponent()))
            return (ArrayList<ForgeDirection>) Arrays.asList(new ForgeDirection[] {ForgeDirection.UNKNOWN});

        Coordinate tNormalizedVector = new Coordinate(this.getXComponent() - pCenter.getXComponent(), this.getYComponent() - pCenter.getYComponent(), this.getZComponent() - pCenter.getZComponent());

        int tSignX = (int) Math.signum(tNormalizedVector.getXComponent());
        int tSignY = (int) Math.signum(tNormalizedVector.getYComponent());
        int tSignZ = (int) Math.signum(tNormalizedVector.getZComponent());

        ArrayList<ForgeDirection> tDirectionList = new ArrayList<ForgeDirection>();

        for(ForgeDirection tDirection : ForgeDirection.values())
        {
            if (((tSignX != 0) && (tDirection.offsetX == tSignX)) || ((tSignY != 0) && (tDirection.offsetY == tSignY)) || ((tSignZ != 0) && (tDirection.offsetZ == tSignZ)))
                tDirectionList.add(tDirection);
        }

        if (tDirectionList.size() == 0)
            tDirectionList.add(ForgeDirection.UNKNOWN);

        return tDirectionList;
    }
}

