package com.SmithsModding.Armory.Util.Client;

import com.SmithsModding.SmithsCore.Util.Client.Color.MinecraftColor;

/**
 * Created by Marc on 06.12.2015.
 */
public class Colors {
    public static MinecraftColor DEFAULT = (MinecraftColor) MinecraftColor.WHITE;

    public static class Metals {
        public static MinecraftColor IRON = new MinecraftColor(255, 255, 255);
        public static MinecraftColor OBSIDIAN = new MinecraftColor(86, 63, 124);
    }

    public static class Ledgers {
        public static MinecraftColor DEFAULT = new MinecraftColor(255, 255, 255);
        public static MinecraftColor RED = new MinecraftColor(255, 0, 0);
        public static MinecraftColor YELLOW = new MinecraftColor(0, 255, 255);
        public static MinecraftColor BLACK = new MinecraftColor(255, 255, 255);
        public static MinecraftColor GREY = new MinecraftColor(139, 139, 139);
    }

}
