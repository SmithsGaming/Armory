package com.smithsmodding.armory.common.tileentity.conduit;

import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;

import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * Author Orion (Created on: 05.09.2016)
 */
public final class TrackedStack implements INBTSerializable<NBTTagCompound> {

    private FluidStack internalStack;

    private Coordinate3D source;
    private Coordinate3D target;

    private UUID uuid;

    private LinkedHashSet<Coordinate3D> route;

    public TrackedStack(FluidStack stack) {
        internalStack = stack;
    }

    public TrackedStack(FluidStack stack, Coordinate3D source, Coordinate3D target, LinkedHashSet<Coordinate3D> route) {
        this.uuid = UUID.randomUUID();
        this.internalStack = stack;
        this.source = source;
        this.target = target;
        this.route = route;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }
}
