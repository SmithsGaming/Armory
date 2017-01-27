package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.client.render.armor.BodyArmorPartRenderer;
import com.smithsmodding.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.util.armor.ArmorNBTHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by marcf1 on 8/16/2016.
 */
public class BakedBipedPerspectiveAwareModel extends ModelBiped {

    private final BakedMultiLayeredArmorItemModel model;
    private final ItemStack itemStack;

    public BakedBipedPerspectiveAwareModel(BakedMultiLayeredArmorItemModel model, ItemStack itemStack) {
        super(0);
        this.model = model;
        this.itemStack = itemStack;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!(entityIn instanceof EntityLivingBase))
            return;

        EntityLivingBase entitylivingbaseIn = (EntityLivingBase) entityIn;

        IMultiComponentArmorCapability capability = ArmorNBTHelper.getArmorDataFromStack(itemStack);
        if (capability == null)
            return;

        ICoreArmorMaterial coreArmorMaterial = capability.getMaterial();

        ArrayList<IMultiComponentArmorExtensionInformation> installedExtensions = new ArrayList<>();

        //Sort the list based on priority.
        Collections.sort(installedExtensions, new Comparator<IMultiComponentArmorExtensionInformation>() {
            @Override
            public int compare(@Nonnull IMultiComponentArmorExtensionInformation o1, @Nonnull IMultiComponentArmorExtensionInformation o2) {
                return Integer.compare(o1.getPosition().getArmorLayer(), o2.getPosition().getArmorLayer());
            }
        });

        boolean broken = ArmorNBTHelper.checkIfStackIsBroken(itemStack);

        BodyArmorPartRenderer.render(entitylivingbaseIn,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                scale,
                capability.getArmorType(),
                model.baseLayer.getModelByIdentifier(coreArmorMaterial.getRegistryName()),
                itemStack);

        for (IMultiComponentArmorExtensionInformation extensionInformation : installedExtensions) {
            IMultiComponentArmorExtension extension = extensionInformation.getExtension();

            IBakedModel partModel;
            ResourceLocation addonArmorMaterialName = null;
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
                addonArmorMaterialName = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterial().getRegistryName();

            if (broken && model.brokenParts.containsKey(extension) && model.brokenParts.get(extension) != null) {
                partModel = model.brokenParts.get(extension).getModelByIdentifier(addonArmorMaterialName);
            } else if (model.parts.containsKey(extension) && model.parts.get(extension) != null) {
                partModel = model.parts.get(extension).getModelByIdentifier(addonArmorMaterialName);
            } else {
                continue;
            }

            BodyArmorPartRenderer.render(entitylivingbaseIn,
                    limbSwing,
                    limbSwingAmount,
                    ageInTicks,
                    netHeadYaw,
                    headPitch,
                    scale,
                    extension,
                    partModel,
                    itemStack);
        }

    }
}
