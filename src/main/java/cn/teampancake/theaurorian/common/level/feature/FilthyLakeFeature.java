package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

@SuppressWarnings("deprecation")
public class FilthyLakeFeature extends Feature<FilthyLakeFeature.Configuration> {

    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public FilthyLakeFeature() {
        super(Configuration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        Configuration config = context.config();
        if (origin.getY() <= level.getMinBuildHeight() + 4) {
            return false;
        } else {
            origin = origin.below(4);
            boolean[] bl = new boolean[2048];
            int i = random.nextInt(4) + 4;
            for (int j = 0; j < i; ++j) {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
                for (int l = 1; l < 15; ++l) {
                    for (int i1 = 1; i1 < 15; ++i1) {
                        for (int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D) {
                                bl[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState fluidState = config.fluid().getState(random, origin);
            for (int k1 = 0; k1 < 16; ++k1) {
                for (int k = 0; k < 16; ++k) {
                    for (int l2 = 0; l2 < 8; ++l2) {
                        boolean flag = !bl[(k1 * 16 + k) * 8 + l2] && (k1 < 15 && bl[((k1 + 1) * 16 + k) * 8 + l2] ||
                                k1 > 0 && bl[((k1 - 1) * 16 + k) * 8 + l2] || k < 15 && bl[(k1 * 16 + k + 1) * 8 + l2] ||
                                k > 0 && bl[(k1 * 16 + (k - 1)) * 8 + l2] || l2 < 7 && bl[(k1 * 16 + k) * 8 + l2 + 1] ||
                                l2 > 0 && bl[(k1 * 16 + k) * 8 + (l2 - 1)]);
                        if (flag) {
                            BlockState offsetState = level.getBlockState(origin.offset(k1, l2, k));
                            if (l2 >= 4 && offsetState.liquid()) {
                                return false;
                            }

                            if (l2 < 4 && !offsetState.isSolid() && level.getBlockState(origin.offset(k1, l2, k)) != fluidState) {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < 16; ++l1) {
                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int i3 = 0; i3 < 8; ++i3) {
                        if (bl[(l1 * 16 + i2) * 8 + i3]) {
                            BlockPos offsetPos = origin.offset(l1, i3, i2);
                            if (this.canReplaceBlock(level.getBlockState(offsetPos))) {
                                boolean flag1 = i3 >= 4;
                                level.setBlock(offsetPos, flag1 ? AIR : fluidState, Block.UPDATE_CLIENTS);
                                if (flag1) {
                                    level.scheduleTick(offsetPos, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(level, offsetPos);
                                }
                            }
                        }
                    }
                }
            }

            BlockState barrierState = config.barrier().getState(random, origin);
            BlockState barrierTopState = config.barrierTop().getState(random, origin);
            if (!barrierState.isAir() && !barrierTopState.isAir()) {
                for (int j2 = 0; j2 < 16; ++j2) {
                    for (int j3 = 0; j3 < 16; ++j3) {
                        for (int l3 = 0; l3 < 8; ++l3) {
                            boolean flag2 = !bl[(j2 * 16 + j3) * 8 + l3] && (j2 < 15 && bl[((j2 + 1) * 16 + j3) * 8 + l3] ||
                                    j2 > 0 && bl[((j2 - 1) * 16 + j3) * 8 + l3] || j3 < 15 && bl[(j2 * 16 + j3 + 1) * 8 + l3] ||
                                    j3 > 0 && bl[(j2 * 16 + (j3 - 1)) * 8 + l3] || l3 < 7 && bl[(j2 * 16 + j3) * 8 + l3 + 1] ||
                                    l3 > 0 && bl[(j2 * 16 + j3) * 8 + (l3 - 1)]);
                            if (flag2 && (l3 < 4 || random.nextInt(2) != 0)) {
                                BlockState blockState = level.getBlockState(origin.offset(j2, l3, j3));
                                if (blockState.isSolid() && !blockState.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                    BlockPos offsetPos = origin.offset(j2, l3, j3);
                                    BlockState aboveState = level.getBlockState(offsetPos.above());
                                    boolean flag = aboveState.canBeReplaced() && !aboveState.liquid();
                                    BlockState state =  flag ? barrierTopState : barrierState;
                                    level.setBlock(offsetPos, state, Block.UPDATE_CLIENTS);
                                    this.markAboveForPostProcessing(level, offsetPos);
                                }
                            }
                        }
                    }
                }
            }

            if (fluidState.getFluidState().is(FluidTags.WATER)) {
                for (int k2 = 0; k2 < 16; ++k2) {
                    for (int k3 = 0; k3 < 16; ++k3) {
                        BlockPos offsetPos = origin.offset(k2, 4, k3);
                        boolean flag = this.canReplaceBlock(level.getBlockState(offsetPos));
                        if (level.getBiome(offsetPos).value().shouldFreeze(level, offsetPos, Boolean.FALSE) && flag) {
                            level.setBlock(offsetPos, TABlocks.FILTHY_ICE.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    public record Configuration(BlockStateProvider fluid, BlockStateProvider barrier, BlockStateProvider barrierTop) implements FeatureConfiguration {
        public static final Codec<Configuration> CODEC =
                RecordCodecBuilder.create((instance) -> instance.group(
                        BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid),
                        BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier),
                        BlockStateProvider.CODEC.fieldOf("barrier_top").forGetter(Configuration::barrierTop)
                ).apply(instance, Configuration::new));
    }

}