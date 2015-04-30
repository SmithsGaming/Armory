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

    int getXComponent() { return iXCoord; }

    int getYComponent() { return iYCoord; }

    int getZComponent() { return iZCoord; }
}
