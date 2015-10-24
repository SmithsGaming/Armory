package com.SmithsModding.Armory.Util.Core;
/*
/  Coordinate
/  Created by : Orion
/  Created on : 27-4-2015
*/

import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.util.ForgeDirection;

public class Coordinate {
    int iXCoord;
    int iYCoord;
    int iZCoord;

    public Coordinate(int pXCoord, int pYCoord) {
        this(pXCoord, pYCoord, 0);
    }


    public Coordinate(int pXCoord, int pYCoord, int pZCoord) {
        iXCoord = pXCoord;
        iYCoord = pYCoord;
        iZCoord = pZCoord;
    }

    public static Coordinate fromBytes(ByteBuf pData) {
        return new Coordinate(pData.readInt(), pData.readInt(), pData.readInt());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "iXCoord=" + iXCoord +
                ", iYCoord=" + iYCoord +
                ", iZCoord=" + iZCoord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (iXCoord != that.iXCoord) return false;
        if (iYCoord != that.iYCoord) return false;
        return iZCoord == that.iZCoord;

    }

    @Override
    public int hashCode() {
        return getXComponent() + getYComponent() + getZComponent();
    }

    public int getXComponent() {
        return iXCoord;
    }

    public int getYComponent() {
        return iYCoord;
    }

    public int getZComponent() {
        return iZCoord;
    }

    public Coordinate moveCoordiante(ForgeDirection pDirection, int pDistance) {
        return new Coordinate(getXComponent() + (pDistance * pDirection.offsetX), getYComponent() + (pDistance * pDirection.offsetY), getZComponent() + (pDistance * pDirection.offsetZ));
    }

    public float getDistanceTo(Coordinate pCoordinate) {
        return (float) Math.sqrt(Math.pow(getXComponent() - pCoordinate.getXComponent(), 2) + Math.pow(getYComponent() - pCoordinate.getYComponent(), 2) + Math.pow(getZComponent() - pCoordinate.getZComponent(), 2));
    }

    public void toBytes(ByteBuf pDataOut) {
        pDataOut.writeInt(getXComponent());
        pDataOut.writeInt(getYComponent());
        pDataOut.writeInt(getZComponent());
    }
}

