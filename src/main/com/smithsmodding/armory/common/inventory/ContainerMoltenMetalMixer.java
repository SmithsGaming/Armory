package com.smithsmodding.armory.common.inventory;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalMixer;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 3/9/2017.
 */
public class ContainerMoltenMetalMixer extends ContainerSmithsCore {

    public ContainerMoltenMetalMixer(@Nonnull EntityPlayer playerMP, @Nonnull TileEntityMoltenMetalMixer moltenMetalMixer) {
        super(References.InternalNames.TileEntities.MoltenMetalMixer, moltenMetalMixer, moltenMetalMixer, playerMP);
    }
}
