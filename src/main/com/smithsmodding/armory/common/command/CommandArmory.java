/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.command;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArmory extends CommandBase {

    @Nonnull
    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    @Nonnull
    private static List<String> commands = new ArrayList<String>();

    static {
        modCommands.add(new CommandGiveHeated());
        modCommands.add(new CommandEnableTempDecay());

        for (CommandBase commandBase : modCommands) {
            commands.add(commandBase.getName());
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return References.InternalNames.Commands.BASECOMMAND;
    }

    @Nonnull
    @Override
    public String getUsage(ICommandSender pCommandSender) {
        return TranslationKeys.Messages.Commands.TK_BASEUSAGE;
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            for (CommandBase command : modCommands) {
                if (command.getName().equalsIgnoreCase(args[0]) && command.checkPermission(server, sender)) {
                    command.execute(server, sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, commands);
        } else if (args.length >= 2) {
            for (CommandBase command : modCommands) {
                if (command.getName().equalsIgnoreCase(args[0])) {
                    return command.getTabCompletions(server, sender, Arrays.copyOfRange(args, 1, args.length), targetPos);
                }
            }
        }

        return new ArrayList<>();
    }
}
