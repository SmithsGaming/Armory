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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArmory extends CommandBase {

    @NotNull
    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    @NotNull
    private static List<String> commands = new ArrayList<String>();

    static {
        modCommands.add(new CommandGiveHeated());

        modCommands.add(new CommandEnableTempDecay());

        for (CommandBase commandBase : modCommands) {
            commands.add(commandBase.getCommandName());
        }
    }

    @NotNull
    @Override
    public String getCommandName() {
        return References.InternalNames.Commands.BASECOMMAND;
    }

    @NotNull
    @Override
    public String getCommandUsage(ICommandSender pCommandSender) {
        return TranslationKeys.Messages.Commands.BASEUSAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(args[0]) && command.checkPermission(server, sender)) {
                    command.execute(server, sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, commands);
        } else if (args.length >= 2) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(args[0])) {
                    return command.getTabCompletionOptions(server, sender, Arrays.copyOfRange(args, 1, args.length), pos);
                }
            }
        }

        return null;
    }
}
