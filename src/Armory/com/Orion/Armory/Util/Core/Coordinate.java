package com.Orion.Armory.Util.Core;
/*
/  Coordinate
/  Created by : Orion
/  Created on : 27-4-2015
*/

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
}
