package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by marcf on 1/31/2017.
 */
public class TileEntityHeatableBlock extends TileEntity {

    //TODO: Next update implement heating mechanics on Block!

    private IMaterial material;

    public IMaterial getMaterial() {
        return material;
    }

    public void setMaterial(IMaterial material) {
        this.material = material;
    }

    public TileEntityHeatableBlock() {
    }

    public TileEntityHeatableBlock(IMaterial material) {
        this.material = material;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        material = IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.CoreMaterial))).getWrapped();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString(References.NBTTagCompoundData.CoreMaterial, material.getRegistryName().toString());
        return compound;
    }


}
