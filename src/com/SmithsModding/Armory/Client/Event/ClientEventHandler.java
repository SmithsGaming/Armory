package com.SmithsModding.Armory.Client.Event;
/*
 *   ClientEventHandler
 *   Created by: Orion
 *   Created on: 24-1-2015
 */

import com.SmithsModding.Armory.API.Events.Client.RegisterItemResourcesEvent;
import com.SmithsModding.Armory.API.Events.Client.RegisterMaterialResourceEvent;
import com.SmithsModding.Armory.Common.Item.ItemMetalChain;
import com.SmithsModding.Armory.Common.Item.ItemMetalRing;
import com.SmithsModding.Armory.Common.Item.ItemNugget;
import com.SmithsModding.Armory.Common.Item.ItemPlate;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureHook(TextureStitchEvent.Pre pEvent) {
        if (pEvent.map.getTextureType() == 1) {
            Textures.registerIcons(pEvent.map);
        }
    }

    @SubscribeEvent
    public void RegisterMaterialResourceHandler(RegisterMaterialResourceEvent pEvent) {
        if (pEvent.iArmor.getInternalName().equals(References.InternalNames.Armor.MEDIEVALHELMET)) {
            if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Iron.tHelmetResource);
            } else if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Obsidian.tHelmetResource);
            }
        } else if (pEvent.iArmor.getInternalName().equals(References.InternalNames.Armor.MEDIEVALCHESTPLATE)) {
            if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Iron.tChestplateResource);
            } else if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Obsidian.tChestplateResource);
            }
        } else if (pEvent.iArmor.getInternalName().equals(References.InternalNames.Armor.MEDIEVALLEGGINGS)) {
            if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Iron.tLegginsResource);
            } else if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Obsidian.tLegginsResource);
            }
        } else if (pEvent.iArmor.getInternalName().equals(References.InternalNames.Armor.MEDIEVALSHOES)) {
            if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Iron.tShoesResource);
            } else if (pEvent.iArmorMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iArmor.registerResource(Textures.MultiArmor.Materials.Obsidian.tShoesResource);
            }
        }
    }

    @SubscribeEvent
    public void RegisterItemResourcesHandler(RegisterItemResourcesEvent pEvent) {
        if (pEvent.iContainer instanceof ItemMetalRing) {
            if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemRing.IronResource);
            } else if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemRing.ObsidianResource);
            }
        } else if (pEvent.iContainer instanceof ItemMetalChain) {
            if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemChain.IronResource);
            } else if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemChain.ObsidianResource);
            }
        } else if (pEvent.iContainer instanceof ItemNugget) {
            if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemNugget.IronResource);
            } else if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemNugget.ObsidianResource);
            }
        } else if (pEvent.iContainer instanceof ItemPlate) {
            if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemPlate.IronResource);
            } else if (pEvent.iMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                pEvent.iContainer.registerResource(Textures.Items.ItemPlate.ObsidianResource);
            }
        }
    }
}
