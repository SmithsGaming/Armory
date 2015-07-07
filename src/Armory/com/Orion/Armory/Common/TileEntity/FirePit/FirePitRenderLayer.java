package com.Orion.Armory.Common.TileEntity.FirePit;

import com.Orion.Armory.API.Structures.IStructureRenderLayer;
import com.Orion.Armory.Util.References;

/**
 * Created by Orion
 * Created on 07.07.2015
 * 10:10
 * <p/>
 * Copyrighted according to Project specific license
 */
public enum FirePitRenderLayer implements IStructureRenderLayer {
    SOLID,
    SEETHROUGH
    ;

    @Override
    public String getStructureType() {
        return References.InternalNames.TileEntities.Structures.FirePit;
    }
}
