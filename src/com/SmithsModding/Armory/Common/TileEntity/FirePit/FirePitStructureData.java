package com.SmithsModding.Armory.Common.TileEntity.FirePit;

import com.SmithsModding.Armory.API.Structures.IStructureComponent;
import com.SmithsModding.Armory.API.Structures.IStructureData;
import com.SmithsModding.Armory.Util.References;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 20:12
 * <p/>
 * Copyrighted according to Project specific license
 */
public class FirePitStructureData implements IStructureData {

    Float iBurningTicksLeft = 0F;

    Float iTotalBurningTicks = 0F;

    public FirePitStructureData() {
        this(0F, 0F);
    }

    public FirePitStructureData(float pBurningTicksLeft, float pTotalBurningTicks) {
        iBurningTicksLeft = pBurningTicksLeft;
        iTotalBurningTicks = pTotalBurningTicks;
    }

    @Override
    public Object getData(IStructureComponent pRequestingComponent, String pPropertyType) {
        if (pPropertyType.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            return iBurningTicksLeft;

        if (pPropertyType.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            return iTotalBurningTicks;

        return 0F;
    }

    @Override
    public void setData(IStructureComponent pSendingComponent, String pPropertyType, Object pData) {
        if (pPropertyType.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME))
            iBurningTicksLeft = (Float) pData;

        if (pPropertyType.equals(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT))
            iTotalBurningTicks = (Float) pData;
    }

    @Override
    public void writeToNBT(NBTTagCompound pTagCompound) {
        NBTTagCompound tDataCompound = new NBTTagCompound();

        tDataCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, iBurningTicksLeft);
        tDataCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, iTotalBurningTicks);

        pTagCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA).setTag(References.NBTTagCompoundData.TE.Basic.Structures.DATA, tDataCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound pTagCompound) {
        NBTTagCompound tDataCompound = pTagCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA).getCompoundTag(References.NBTTagCompoundData.TE.Basic.Structures.DATA);

        iBurningTicksLeft = tDataCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);
        iTotalBurningTicks = tDataCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);
    }

    @Override
    public void fromBytes(ByteBuf pBuf) {
        iBurningTicksLeft = pBuf.readFloat();
        iTotalBurningTicks = pBuf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf pBuf) {
        pBuf.writeFloat(iBurningTicksLeft);
        pBuf.writeFloat(iTotalBurningTicks);
    }
}
