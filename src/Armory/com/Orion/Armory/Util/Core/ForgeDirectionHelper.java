package com.Orion.Armory.Util.Core;
/*
 *   ForgeDirectionHelper
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import net.minecraftforge.common.util.ForgeDirection;

public class ForgeDirectionHelper
{
        //{DOWN, UP, NORTH, SOUTH, WEST, EAST}
    public static int ConvertToInt(ForgeDirection pDirection)
    {
        switch (pDirection)
        {
            case DOWN:
                return 0;
            case UP:
                return 1;
            case NORTH:
                return 2;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            case EAST:
                return 5;
            default:
                return -1;
        }
    }
}
