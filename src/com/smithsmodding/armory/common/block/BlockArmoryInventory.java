/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockArmoryInventory extends BlockContainer {
    Random iRand = new Random();

    public BlockArmoryInventory (String pBlockName, Material pBlockMaterial) {
        super(pBlockMaterial);
        setUnlocalizedName(pBlockName);
        setHardness(5F);
        setResistance(10F);
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        /*        IInventory tContent = (IInventory) pWorld.getTileEntity(pX, pY, pZ);

        if (tContent != null) {
            for (int tStackIndex = 0; tStackIndex < tContent.getSizeInventory(); ++tStackIndex) {
                ItemStack tStack = tContent.getStackInSlot(tStackIndex);

                if (tStack != null) {
                    float f = this.iRand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.iRand.nextFloat() * 0.8F + 0.1F;
                    EntityItem tEntity;

                    for (float f2 = this.iRand.nextFloat() * 0.8F + 0.1F; tStack.stackSize > 0; pWorld.spawnEntityInWorld(tEntity)) {
                        int j1 = this.iRand.nextInt(21) + 10;

                        if (j1 > tStack.stackSize) {
                            j1 = tStack.stackSize;
                        }

                        tStack.stackSize -= j1;
                        tEntity = new EntityItem(pWorld, (double) ((float) pX + f), (double) ((float) pY + f1), (double) ((float) pZ + f2), new ItemStack(tStack.getItem(), j1, tStack.getItemDamage()));
                        float f3 = 0.05F;
                        tEntity.motionX = (double) ((float) this.iRand.nextGaussian() * f3);
                        tEntity.motionY = (double) ((float) this.iRand.nextGaussian() * f3 + 0.2F);
                        tEntity.motionZ = (double) ((float) this.iRand.nextGaussian() * f3);

                        if (tStack.hasTagCompound()) {
                            tEntity.getEntityItem().setTagCompound((NBTTagCompound) tStack.getTagCompound().copy());
                        }
                    }
                }
            }

            pWorld.func_147453_f(pX, pY, pZ, pBlock);
        }

        super.breakBlock(pWorld, pX, pY, pZ, pBlock, pMeta);*/


        super.breakBlock(worldIn, pos, state);
    }

}
