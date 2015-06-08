package com.Orion.Armory.Common.Item;

import com.Orion.Armory.API.Item.IHeatableItem;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Material.ArmorMaterial;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemPlate extends Item implements IHeatableItem
{
    private HashMap<String, CustomResource> iResources = new HashMap<String, CustomResource>();

    public ItemPlate()
    {
        this.setMaxStackSize(64);
        this.setCreativeTab(GeneralRegistry.iTabArmoryComponents);
        this.setUnlocalizedName(References.InternalNames.Items.ItemPlate);
    }

    @Override
    public void registerIcons(IIconRegister pIconRegister) {
        Iterator tResourceIterator = iResources.entrySet().iterator();
        while (tResourceIterator.hasNext()) {
            Map.Entry<String, CustomResource> tResource = (Map.Entry<String, CustomResource>) tResourceIterator.next();
            tResource.getValue().addIcon(pIconRegister.registerIcon(tResource.getValue().getPrimaryLocation()));
        }
    }

    //Function for getting the Icon from a render pass.
    @Override
    public IIcon getIcon(ItemStack pStack, int pRenderPass) {
        String tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.Material);
        return iResources.get(tMaterialID).getIcon();
    }

    //Function to get the amount of RenderPasses. Used in the vanilla rendering of the itemstack.
    @Override
    public int getRenderPasses(int pMeta) {
        return 1;
    }

    //Function that tells the renderer to use multiple layers
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    //Function that tells the renderer to use a certain color
    @Override
    public int getColorFromItemStack(ItemStack pStack, int pRenderPass) {
        String tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.Material);
        return iResources.get(tMaterialID).getColor().getColor();
    }

    ///#############################################Resource control functions##########################################
    //Function for registering a new resource the Item may need to render it
    public void registerResource(CustomResource pResource) {
        iResources.put(pResource.getInternalName(), pResource);
    }

    //Returns the resource (if registered, else null) depending on the given InternalName (which is registered as its ID)
    public CustomResource getResource(String pResourceID) {
        return iResources.get(pResourceID);
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item pRing, CreativeTabs pCreativeTab, List pItemStacks)
    {
        for(IArmorMaterial tMaterial: MaterialRegistry.getInstance().getArmorMaterials().values()){
            ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iPlate, 1);

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
            tNuggetStack.setTagCompound(tStackCompound);

            HeatedItemFactory.getInstance().addHeatableItemstack(tMaterial.getInternalMaterialName(), tNuggetStack);

            pItemStacks.add(tNuggetStack);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack pStack)
    {
        String tMaterialID = pStack.getTagCompound().getString(References.NBTTagCompoundData.Material);
        IArmorMaterial tMaterial = MaterialRegistry.getInstance().getMaterial(tMaterialID);

        return tMaterial.getVisibleNameColor() + StatCollector.translateToLocal(tMaterial.getVisibleName()) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    @Override
    public String getInternalType() {
        return References.InternalNames.HeatedItemTypes.PLATE;
    }
}
