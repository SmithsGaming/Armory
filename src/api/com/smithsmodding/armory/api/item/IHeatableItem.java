package com.smithsmodding.armory.api.item;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Orion Created on 02.05.2015 15:04
 *
 * Copyrighted according to Project specific license
 */
public interface IHeatableItem {
    @NotNull String getInternalType();

    int getMoltenMilibucket ();
}