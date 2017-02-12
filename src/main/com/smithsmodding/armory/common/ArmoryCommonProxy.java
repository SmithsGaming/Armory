package com.smithsmodding.armory.common;


import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.logic.initialization.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import javax.annotation.Nonnull;

/**
 * Created by Orion on 26-4-2014
 * <p>
 * com.Orion.armory.common proxy for armory
 */
public class ArmoryCommonProxy {

    public void registerInitializationComponents(IForgeRegistry<IInitializationComponent> registry) {
        registry.register(CommonConfigInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.CONFIG));
        registry.register(CommonEventHandlerInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.EVENTHANDLER));
        registry.register(CommonStructureInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.STRUCTURE));
        registry.register(CommonMedievalInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.MEDIEVAL));
        registry.register(CommonSystemInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.SYSTEM));
        registry.register(CommonCommandInitializer.getInstance().setRegistryName(References.InternalNames.InitializationComponents.Common.COMMAND));
    }

    public EntityPlayer getPlayer(@Nonnull MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }
}
