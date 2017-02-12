package com.smithsmodding.armory.common.tileentity.state;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IForgeFuelDataContainer {
    boolean isBurning();

    void setBurning(boolean burning);

    int getTotalBurningTicksOnCurrentFuel();

    void setTotalBurningTicksOnCurrentFuel(int totalBurningTicksOnCurrentFuel);

    void changeTotalBurningTicksOnCurrentFuel(int change);

    int getBurningTicksLeftOnCurrentFuel();

    void setBurningTicksLeftOnCurrentFuel(int burningTicksLeftOnCurrentFuel);

    void changeBurningTicksLeftOnCurrentFuel(int change);

    void resetBurningTicksLeftOnCurrentFuel();
}
