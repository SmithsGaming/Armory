package com.smithsmodding.armory.api.util.client;

import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/11/2017.
 */
public interface ITranslateable {

    /**
     * Method to get the translation Key.
     * @return The key to translate.
     */
    @Nonnull
    String getTranslationKey();

    /**
     * Method to get the markup.
     * @return The markup. Default is TextFormatting.Reset
     */
    @Nonnull
    default String getTextFormatting() {
        return TextFormatting.RESET.toString();
    }
}
