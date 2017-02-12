package com.smithsmodding.armory.api.common.crafting.blacksmiths.component;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.capability.IHeatableObjectCapability;
import com.smithsmodding.armory.api.common.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p>
 * Copyrighted according to Project specific license
 */
public class HeatedAnvilRecipeComponent implements IAnvilRecipeComponent {

    private final float minTemp;
    private final float maxTemp;

    private IHeatedObjectType type;
    private IHeatableObject object;
    private IMaterial material;

    public HeatedAnvilRecipeComponent(IHeatedObjectType type, IHeatableObject object, IMaterial material, float minTemp, float maxTemp) {
        this.type = type;
        this.object = object;
        this.material = material;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }


    @Nullable
    @Override
    public ItemStack getComponentTargetStack() {
        return IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, object, type, ((minTemp + maxTemp) / 2));
    }

    @Nonnull
    @Override
    public HeatedAnvilRecipeComponent setComponentTargetStack(@Nonnull ItemStack targetStack) {
        if (!targetStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null)) {
            ModLogger.getInstance().error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString(targetStack));
        }

        IHeatableObjectCapability heatableObjectCapability = targetStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null);
        material = heatableObjectCapability.getMaterial();
        object = heatableObjectCapability.getObject();
        type = heatableObjectCapability.getType();

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        return 0;
    }

    @Nonnull
    @Override
    public HeatedAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        return this;
    }

    @Override
    public boolean isValidComponentForSlot(@Nullable ItemStack pComparedItemStack) {
        if (pComparedItemStack == null)
            return false;

        if (!pComparedItemStack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null)) {
            return false;
        }

        IHeatedObjectCapability heatedObjectCapability = pComparedItemStack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        return heatedObjectCapability.getMaterial().getOreDictionaryIdentifier() == material.getOreDictionaryIdentifier() && heatedObjectCapability.getObject() == object && heatedObjectCapability.getType() == type && ((minTemp <= heatedObjectCapability.getTemperature()) && (maxTemp >= heatedObjectCapability.getTemperature()));
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     *
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     *
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     *
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     *
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new HeatedAnvilRecipeComponent(type, object, material, minTemp, maxTemp);
    }
}
