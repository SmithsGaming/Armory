package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.client.model.item.baked.components.BakedTemperatureBarModel;
import com.smithsmodding.armory.client.model.item.baked.heateditem.BakedHeatedItemModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.TemperatureBarComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector3f;
import java.util.Collection;

/**
 * Created by Marc on 08.12.2015.
 */
public class HeatedItemItemModel extends ItemLayerModel {

    TemperatureBarComponentModel gaugeDisplay;

    public HeatedItemItemModel(ImmutableList<ResourceLocation> defaultTextures) {
        super(defaultTextures);

        this.gaugeDisplay = new TemperatureBarComponentModel(defaultTextures);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);
        float additionalScale = 1f / 0.625f;

        BakedTemperatureBarModel unrotatedModel = gaugeDisplay.generateBackedComponentModel(state, format, bakedTextureGetter);

        IBakedModel bakedBlockModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(Item.getItemFromBlock(Blocks.STONE)));
        TRSRTransformation invertedBlockTRSR = new TRSRTransformation(bakedBlockModel.getItemCameraTransforms().gui).inverse();
        invertedBlockTRSR = new TRSRTransformation(new Vector3f(2.14f, 0.44f, 0), invertedBlockTRSR.getLeftRot(), invertedBlockTRSR.getScale(), invertedBlockTRSR.getRightRot());

        BakedTemperatureBarModel rotatedModel = gaugeDisplay.generateBackedComponentModel(state.apply(Optional.absent()).get().compose(invertedBlockTRSR), format, bakedTextureGetter);

        //Bake the model.
        return new BakedHeatedItemModel(base, unrotatedModel, rotatedModel);
    }
}
