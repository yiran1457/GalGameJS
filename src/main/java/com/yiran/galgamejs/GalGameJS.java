package com.yiran.galgamejs;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GalGameJS.MODID)
public class GalGameJS {
    public static final String MODID = "galgamejs";

    public GalGameJS(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
    }

}
