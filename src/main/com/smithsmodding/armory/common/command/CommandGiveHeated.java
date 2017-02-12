/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.command;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandGiveHeated extends CommandBase {
    @NotNull
    @Override
    public String getCommandName() {
        return References.InternalNames.Commands.GIVEHEATED;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @NotNull
    @Override
    public String getCommandUsage(ICommandSender pCommandSender) {
        return TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 3) {
            throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
        } else {
            try {
                ItemStack stack = HeatedItemFactory.getInstance().generateHeatedItem(MaterialRegistry.getInstance().getMaterial(args[1]), args[2], Float.parseFloat(args[3]));

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

    @org.jetbrains.annotations.Nullable
    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, PlayerManager.getInstance().getCommonSidedJoinedMap().values());
        }
        if (args.length == 2) {
            if (!MaterialRegistry.getInstance().getArmorMaterials().containsKey(args[1]))
                return getMaterialCompletionOptions(args[1]);

            return getTypeCompletionOptions("");
        } else if (args.length == 3) {
            if (!HeatableItemRegistry.getInstance().getAllHeatableItemTypes().contains(args[2]))
                return getTypeCompletionOptions(args[2]);

            return null;
        }

        return null;
    }

    @NotNull
    private List getMaterialCompletionOptions(@NotNull String pMaterialStart) {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            if ((pMaterialStart.equals("")) || (tMaterial.getUniqueID().contains(pMaterialStart))) {
                tTabCompletionOptions.add(tMaterial.getUniqueID());
            }
        }

        return tTabCompletionOptions;
    }

    @NotNull
    private List getTypeCompletionOptions(@NotNull String pTypeStart) {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (String tType : HeatableItemRegistry.getInstance().getAllHeatableItemTypes()) {
            if (((pTypeStart.equals("")) || (tType.contains(pTypeStart))) && !tTabCompletionOptions.contains(tType)) {
                tTabCompletionOptions.add(tType);
            }
        }

        return tTabCompletionOptions;
    }
}
