package com.smithsmodding.armory.api.util.references;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/24/2017.
 */
public class ModData {

    public static class Materials {

        public static class Iron {
            @Nonnull
            public static final Float IRON_MELTINGPOINT = 1538F;
            @Nonnull
            public static final Integer IRON_MELTINGTIME = 500;
            @Nonnull
            public static final Float IRON_VAPORIZINGPOINT = 2862F;
            @Nonnull
            public static final Integer IRON_VAPORIZINGTIME = (int) (IRON_MELTINGTIME * (float) Iron.IRON_VAPORIZINGPOINT / (float) Iron.IRON_MELTINGPOINT);
            @Nonnull
            public static final Float IRON_HEATCOEFFICIENT = 0.225F;
        }

        public static class Gold {
            @Nonnull
            public static final Float GOLD_MELTINGPOINT = 1064F;
            @Nonnull
            public static final Integer GOLD_MELTINGTIME = (int) (Iron.IRON_MELTINGTIME * (GOLD_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float GOLD_VAPORIZINGPOINT = 2856F;
            @Nonnull
            public static final Integer GOLD_VAPORIZINGTIME = (int) (GOLD_MELTINGTIME * GOLD_VAPORIZINGPOINT / GOLD_MELTINGPOINT);
            @Nonnull
            public static final Float GOLD_HEATCOEFFICIENT = 0.478F;
        }

        public static class Obsidian {
            @Nonnull
            public static final Float OBSIDIAN_MELTINGPOINT = 798F;
            @Nonnull
            public static final Integer OBSIDIAN_MELTINGTIME = (int) (Iron.IRON_MELTINGTIME * (OBSIDIAN_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float OBSIDIAN_VAPORIZINGPOINT = 2950F;
            @Nonnull
            public static final Integer OBSIDIAN_VAPORIZINGTIME = (int) (OBSIDIAN_MELTINGTIME * Obsidian.OBSIDIAN_VAPORIZINGPOINT / Obsidian.OBSIDIAN_MELTINGPOINT);
            @Nonnull
            public static final Float OBSIDIAN_HEATCOEFFICIENT = 0.345F;

        }

        public static class Steel {
            @Nonnull
            public static final Float STEEL_MELTINGPOINT = 1373F;
            @Nonnull
            public static final Integer STEEL_MELTINGTIME = (int) (Iron.IRON_MELTINGTIME * (STEEL_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float STEEL_VAPORIZINGPOINT = 3165F;
            @Nonnull
            public static final Integer STEEL_VAPORIZINGTIME = (int) (STEEL_MELTINGTIME * Steel.STEEL_VAPORIZINGPOINT / Steel.STEEL_MELTINGPOINT);
            @Nonnull
            public static final Float STEEL_HEATCOEFFICIENT = 0.2F;
        }
        
        public static class Hardened_Iron {
            @Nonnull
            public static final Float HARDENED_IRON_MELTINGPOINT = 1785F;
            @Nonnull
            public static final Integer HARDENED_IRON_MELTINGTIME = (int) (Iron.IRON_MELTINGTIME * (HARDENED_IRON_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float HARDENED_IRON_VAPORIZINGPOINT = 2963F;
            @Nonnull
            public static final Integer HARDENED_IRON_VAPORIZINGTIME = (int) (HARDENED_IRON_MELTINGTIME * Hardened_Iron.HARDENED_IRON_VAPORIZINGPOINT / Hardened_Iron.HARDENED_IRON_MELTINGPOINT);
            @Nonnull
            public static final Float HARDENED_IRON_HEATCOEFFICIENT = 0.176F;
        }
    }

    public static class Durability {

        public static class Armor {
            @Nonnull
            public static final Integer DAR_HELMET = 345;
            @Nonnull
            public static final Integer DAR_CHESTPLATE = 475;
            @Nonnull
            public static final Integer DAR_LEGGINGS = 380;
            @Nonnull
            public static final Integer DAR_SHOES = 310;
        }

        public static class Anvil {
            @Nonnull
            public static final Integer DAN_IRON = 1575;
            @Nonnull
            public static final Integer DAN_OBSIDIAN = 2100;
            @Nonnull
            public static final Integer DAN_STONE = 575;
        }
    }
}
