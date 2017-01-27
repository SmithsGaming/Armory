package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.common.material.client.MaterialRenderControllers;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientMedievalInitializer extends IInitializationComponent.Impl {

    private static final ClientMedievalInitializer INSTANCE = new ClientMedievalInitializer();

    public static ClientMedievalInitializer getInstance() {
        return INSTANCE;
    }

    private ClientMedievalInitializer () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        ModMaterials.Armor.Core.IRON.setRenderInfo(new MaterialRenderControllers.Metal(0xcacaca, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(MinecraftColor.RED);
            }
        });
        ModMaterials.Armor.Addon.IRON.setRenderInfo(ModMaterials.Armor.Core.IRON.getRenderInfo());

        ModMaterials.Armor.Core.OBSIDIAN.setRenderInfo(new MaterialRenderControllers.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
        ModMaterials.Armor.Addon.OBSIDIAN.setRenderInfo(ModMaterials.Armor.Core.OBSIDIAN.getRenderInfo());

        ModMaterials.Armor.Core.GOLD.setRenderInfo(new MaterialRenderControllers.Metal(0xffd700, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(MinecraftColor.YELLOW);
            }
        });
        ModMaterials.Armor.Addon.GOLD.setRenderInfo(ModMaterials.Armor.Core.GOLD.getRenderInfo());

        ModMaterials.Armor.Core.STEEL.setRenderInfo(new MaterialRenderControllers.Metal(0x6699CC, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(102, 153, 204, 255);
            }
        });
        ModMaterials.Armor.Addon.STEEL.setRenderInfo(ModMaterials.Armor.Core.STEEL.getRenderInfo());

        ModMaterials.Anvil.STONE.setRenderInfo(new MaterialRenderControllers.BlockTexture("minecraft:blocks/stone"));
        ModMaterials.Anvil.IRON.setRenderInfo(ModMaterials.Armor.Core.IRON.getRenderInfo());
        ModMaterials.Anvil.OBSIDIAN.setRenderInfo(ModMaterials.Armor.Core.OBSIDIAN.getRenderInfo());
    }
}
