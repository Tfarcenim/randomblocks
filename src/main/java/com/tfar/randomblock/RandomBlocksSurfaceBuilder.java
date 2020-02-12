package com.tfar.randomblock;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Random;
import java.util.function.Function;

public class RandomBlocksSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
  public RandomBlocksSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51305_1_) {
    super(p_i51305_1_);
  }

  @Override
  public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
    this.buildSurface(random, chunkIn, x, z, startHeight);
  }

  protected void buildSurface(Random random, IChunk chunkIn, int x, int z, int startHeight) {
    BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
    int k = x & 15;
    int l = z & 15;
    for(int i1 = startHeight; i1 >= 0; --i1) {
      //setPos
      blockpos$mutable.func_181079_c(k, i1, l);
      BlockState blockstate2 = chunkIn.getBlockState(blockpos$mutable);
      if (!blockstate2.isAir()
              && !(blockstate2.getBlock() instanceof FlowingFluidBlock)
              && blockstate2.getBlock() !=
      Blocks.BEDROCK) {
        BlockState state = getRandom(ExampleMod.whitelist);
          if (state.has(BlockStateProperties.WATERLOGGED)) state = state.with(BlockStateProperties.WATERLOGGED, false);
          chunkIn.setBlockState(blockpos$mutable, state, false);
      }
    }
  }

  public static BlockState getRandom(Tag<Block> tag){
    return tag.getAllElements().stream().skip((int)(tag.getAllElements().size() * Math.random()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("tag cannot be empty"))
            .getDefaultState();
  }
}
