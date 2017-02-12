package com.smithsmodding.armory.common.command;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModHeatableObjects;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcf on 2/7/2017.
 */
public class CommandGiveHeated extends CommandBase {
    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return References.InternalNames.Commands.GIVEHEATED;
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender The ICommandSender who is requesting usage details
     */
    @Override
    public String getUsage(ICommandSender sender) {
        return TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 3) {
            throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
        } else {
            try {
                IHeatableObject object = ModHeatableObjects.ITEMSTACK;
                IHeatedObjectType type = IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry().getValue(new ResourceLocation(args[2]));
                IMaterial material = IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().getValue(new ResourceLocation(args[1])).getWrapped();
                Float temperature = Float.parseFloat(args[3]);

                if (type == null || material == null || temperature == null)
                    throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);

                ItemStack stack = IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, object, type, temperature);

                if (stack == null) {
                    throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
                }

                EntityPlayerMP player = getPlayer(server, sender, args[0]);

                EntityItem entity = player.dropItem(stack, false);
                entity.setNoPickupDelay();
                entity.setOwner(player.getDisplayNameString());
            } catch (Exception exception) {
                throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, PlayerManager.getInstance().getCommonSidedJoinedMap().values());
        }
        if (args.length == 2) {
            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().containsKey(new ResourceLocation(args[1])))
                return getMaterialCompletionOptions(args[1]);

            return getTypeCompletionOptions("");
        } else if (args.length == 3) {
            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry().containsKey(new ResourceLocation(args[2])))
                return getTypeCompletionOptions(args[2]);

            return null;
        }

        return null;
    }

    private List getMaterialCompletionOptions(String start) {
        ArrayList<String> tabCompletionOptions = new ArrayList<>();

        IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().forEach(registryMaterialWrapper ->  {
            if ((start.equals("")) || (registryMaterialWrapper.getRegistryName().toString().contains(start))) {
                tabCompletionOptions.add(registryMaterialWrapper.getRegistryName().toString());
            }
        });

        return tabCompletionOptions;
    }

    private List getTypeCompletionOptions(String start) {
        ArrayList<String> tabCompletionOptions = new ArrayList<>();

        IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry().forEach(iHeatedObjectType -> {
            if ((start.equals("")) || (iHeatedObjectType.getRegistryName().toString().contains(start))) {
                tabCompletionOptions.add(iHeatedObjectType.getRegistryName().toString());
            }
        });

        return tabCompletionOptions;
    }
}
