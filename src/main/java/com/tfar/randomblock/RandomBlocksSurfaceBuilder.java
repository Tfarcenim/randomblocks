package com.tfar.randomblock;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class RandomBlocksSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
  public RandomBlocksSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51305_1_) {
    super(p_i51305_1_);
  }

  @Override
  public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
    this.buildSurface(chunkIn, x, z, startHeight);
  }

  protected void buildSurface(IChunk chunkIn, int x, int z, int startHeight) {
    BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
    int k = x & 15;
    int l = z & 15;
    for(int i1 = startHeight; i1 >= 0; --i1) {
      //setPos
      blockpos$mutable.setPos(k, i1, l);
      BlockState blockstate2 = chunkIn.getBlockState(blockpos$mutable);
      if (!blockstate2.isAir()
              && !(blockstate2.getBlock() instanceof FlowingFluidBlock)
              && blockstate2.getBlock() !=
      Blocks.BEDROCK) {
        BlockState state = getRandom(ExampleMod.whitelist);
        if (state.has(LeavesBlock.PERSISTENT))state= state.with(LeavesBlock.PERSISTENT,true);
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
