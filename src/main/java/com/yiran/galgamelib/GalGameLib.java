package com.yiran.galgamelib;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GalGameLib.MODID)
public class GalGameLib {
    public static final String MODID = "galgamelib";

    public GalGameLib(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
    }

}
