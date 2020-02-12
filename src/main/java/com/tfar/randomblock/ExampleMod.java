package com.tfar.randomblock;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod {

    public static final String MODID = "randomblocks";
    public static final Tag<Block> whitelist = new BlockTags.Wrapper(new ResourceLocation(MODID,"whitelist"));

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS =
            new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS,"minecraft");


    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> RANDOM_BLOCKS =
            SURFACE_BUILDERS.register("default",
            () -> new RandomBlocksSurfaceBuilder(SurfaceBuilderConfig::deserialize));

    public ExampleMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        SURFACE_BUILDERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        ForgeRegistries.BIOMES.forEach(biome -> {
            biome.surfaceBuilder = new ConfiguredSurfaceBuilder<>(RANDOM_BLOCKS.get(),new
                    SurfaceBuilderConfig(biome.surfaceBuilder.getConfig().getTop(),
                    biome.surfaceBuilder.getConfig().getUnder(),
                    null));
        });
    }
}
