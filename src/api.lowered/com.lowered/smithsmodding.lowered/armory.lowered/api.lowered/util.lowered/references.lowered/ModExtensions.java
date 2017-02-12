package com.smithsmodding.armory.api.util.references;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/21/2017.
 */
public class ModExtensions {

    public static final class Medieval {
        public static final class Helmet {

            @Nonnull
            public static IMultiComponentArmorExtension TOP;

            @Nonnull
            public static IMultiComponentArmorExtension RIGHT;

            @Nonnull
            public static IMultiComponentArmorExtension LEFT;
        }

        public static final class ChestPlate {

            @Nonnull
            public static IMultiComponentArmorExtension SHOULDERLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension SHOULDERRIGHT;

            @Nonnull
            public static IMultiComponentArmorExtension STOMACHLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension STOMACHRIGHT;

            @Nonnull
            public static IMultiComponentArmorExtension BACKLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension BACKRIGHT;
        }

        public static final class Leggings {

            @Nonnull
            public static IMultiComponentArmorExtension SHINLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension SHINRIGHT;

            @Nonnull
            public static IMultiComponentArmorExtension CALFLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension CALFRIGHT;
        }

        public static final class Shoes {

            @Nonnull
            public static IMultiComponentArmorExtension LACESLEFT;

            @Nonnull
            public static IMultiComponentArmorExtension LACESRIGHT;
        }
    }
}
