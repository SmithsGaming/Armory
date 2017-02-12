package com.smithsmodding.armory.common.item.armor;

import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/25/2017.
 */
public class ItemMultiComponentArmor extends Item implements ISpecialArmor {


    public ItemMultiComponentArmor(ResourceLocation internalName, String translationKey) {
        this.setRegistryName(internalName);
        this.setUnlocalizedName(translationKey);
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.armorTab);
    }

    /**
     * Retrieves the modifiers to be used when calculating armor damage.
     * <p>
     * Armor will higher priority will have damage applied to them before
     * lower priority ones. If there are multiple pieces of armor with the
     * same priority, damage will be distributed between them based on there
     * absorption ratio.
     *
     * @param player The entity wearing the armor.
     * @param armor  The ItemStack of the armor item itself.
     * @param source The source of the damage, which can be used to alter armor
     *               properties based on the type or source of damage.
     * @param damage The total damage being applied to the entity
     * @param slot   The armor slot the item is in.
     * @return A ArmorProperties instance holding information about how the armor effects damage.
     */
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    /**
     * Get the displayed effective armor.
     *
     * @param player The player wearing the armor.
     * @param armor  The ItemStack of the armor item itself.
     * @param slot   The armor slot the item is in.
     * @return The number of armor points for display, 2 per shield.
     */
    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        return 0;
    }

    /**
     * Applies damage to the ItemStack. The mod is responsible for reducing the
     * item durability and stack size. If the stack is depleted it will be cleaned
     * up automatically.
     *
     * @param entity The entity wearing the armor
     * @param stack  The ItemStack of the armor item itself.
     * @param source The source of the damage, which can be used to alter armor
     *               properties based on the type or source of damage.
     * @param damage The amount of damage being applied to the armor
     * @param slot   The armor slot the item is in.
     */
    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {

    }

    /**
     * Override this method to have an item handle its own armor rendering.
     *
     * @param entityLiving The entity wearing the armor
     * @param itemStack    The itemStack to render the model of
     * @param armorSlot    The slot the armor is in
     * @param _default     Original armor model. Will have attributes set.
     * @return A ModelBiped to render instead of the default
     */
    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        IBakedModel bakedModel= Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(itemStack,entityLiving.getEntityWorld(),entityLiving);
        if(bakedModel instanceof ModelBiped) {
            return (ModelBiped) bakedModel;
        }
        return _default;
    }
}
