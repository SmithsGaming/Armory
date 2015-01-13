package com.Orion.Armory.Common.TileEntity;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Registry.IngotRegistry;
import com.Orion.Armory.Util.HeatedIngots.NBTHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import java.util.ArrayList;

public class TileEntityFirePit extends TileEntityArmory {
    protected ArrayList<ItemStack> iIngotsInFire = new ArrayList<ItemStack>(5);
    protected ItemStack iCurrentFuelStack = new ItemStack(Items.coal, 64);
    protected ArrayList<ItemStack> iFuelReserve = new ArrayList<ItemStack>(4);
    protected float iCurrentTemperature = 20;
    protected float iLastAddedHeat = 0;
    protected boolean iIsBurning = false;

    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID < 4) {
            try {
                return iIngotsInFire.get(pSlotID);
            } catch (Exception exception) {
                return null;
            }
        } else if (pSlotID == 4) {
            return iCurrentFuelStack;
        } else if (pSlotID > 4 && pSlotID < 9) {
            try {
                return iFuelReserve.get(pSlotID);
            } catch (Exception exception) {
                return null;
            }
        }

        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound pCompound) {
        super.readFromNBT(pCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound)
    {
        super.writeToNBT(pCompound);

    }

    public void updateEntity()
    {
        int tTotalIngots = iIngotsInFire.size();
        float tAddedHeat = this.iLastAddedHeat / (float) (tTotalIngots + 1);
        for (ItemStack tIngot : iIngotsInFire)
        {
            int tIndex = iIngotsInFire.indexOf(tIngot);

            if (IngotRegistry.geInstance().isHeatable(tIngot))
            {
                tIngot = HeatedIngotFactory.getInstance().convertToHeatedIngot(tIngot);
            }

            tIngot = NBTHelper.heatHeatedItem(tIngot, (int) tAddedHeat);

            if (NBTHelper.getTemperatureOfIngot(tIngot) <= 20)
            {
                tIngot = HeatedIngotFactory.getInstance().convertToCooleadIngot(tIngot);
            }

            iIngotsInFire.add(tIndex, tIngot);
            this.markDirty();
        }
    }

    public float heatFurnace(float pFuelAmount)
    {
        this.iCurrentTemperature += pFuelAmount / 20F;
        this.iLastAddedHeat = pFuelAmount / 20F;
        return this.iCurrentTemperature;
    }

    public int getFuelReserveAmount()
    {
        return 5;

        /*
        if (iCurrentFuelStack != null) {
            return iFuelReserve.toArray().length + 1;
        }
        else
        {
            return iFuelReserve.toArray().length;
        }
        */

    }

    public boolean isBurning() {return iIsBurning; }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.func_148857_g());
    }
}
