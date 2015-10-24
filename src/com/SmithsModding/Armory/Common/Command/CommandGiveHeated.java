/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Command;

import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class CommandGiveHeated extends CommandBase {
    @Override
    public String getCommandName() {
        return References.InternalNames.Commands.GIVEHEATED;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender pCommandSender) {
        return TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE;
    }

    @Override
    public void processCommand(ICommandSender pCommandSender, String[] pArguments) {
        if (pArguments.length <= 4) {
            throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
        } else {
            try {
                ItemStack tResultStack = HeatedItemFactory.getInstance().generateHeatedItem(pArguments[2], pArguments[3], Float.parseFloat(pArguments[4]));

                if (tResultStack == null) {
                    throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
                }

                EntityPlayerMP tPlayer = getPlayer(pCommandSender, pArguments[1]);

                EntityItem tEntity = tPlayer.dropPlayerItemWithRandomChoice(tResultStack, false);
                tEntity.delayBeforeCanPickup = 0;
                tEntity.func_145797_a(tPlayer.getCommandSenderName());
            } catch (Exception exception) {
                throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender pCommandSender, String[] pArguments) {
        if (pArguments.length == 1) {
            return getListOfStringsMatchingLastWord(pArguments, this.getPlayers());
        }
        if (pArguments.length == 2) {
            if (!MaterialRegistry.getInstance().getArmorMaterials().containsKey(pArguments[1]))
                return getMaterialCompletionOptions(pArguments[1]);

            return getTypeCompletionOptions("");
        } else if (pArguments.length == 3) {
            if (!HeatedItemFactory.getInstance().getAllMappedTypes().contains(pArguments[2]))
                return getTypeCompletionOptions(pArguments[2]);

            return null;
        }

        return null;
    }

    private List getMaterialCompletionOptions(String pMaterialStart) {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
            if ((pMaterialStart.equals("")) || (tMaterial.getInternalMaterialName().contains(pMaterialStart))) {
                tTabCompletionOptions.add(tMaterial.getInternalMaterialName());
            }
        }

        return tTabCompletionOptions;
    }

    private List getTypeCompletionOptions(String pTypeStart) {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (String tType : HeatedItemFactory.getInstance().getAllMappedTypes()) {
            if (((pTypeStart.equals("")) || (tType.contains(pTypeStart))) && !tTabCompletionOptions.contains(tType)) {
                tTabCompletionOptions.add(tType);
            }
        }

        return tTabCompletionOptions;
    }

    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
