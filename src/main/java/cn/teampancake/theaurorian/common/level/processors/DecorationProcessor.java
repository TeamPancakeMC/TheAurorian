package cn.teampancake.theaurorian.common.level.processors;

import cn.teampancake.theaurorian.common.blocks.TAClusterBlock;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import cn.teampancake.theaurorian.common.registry.TAStructureProcessors;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DecorationProcessor extends StructureProcessor {

    public static final DecorationProcessor INSTANCE = new DecorationProcessor();
    public static final Codec<DecorationProcessor> CODEC = Codec.unit(() -> INSTANCE);

    private DecorationProcessor() {}

    @Nullable @Override
    public StructureTemplate.StructureBlockInfo process(
            LevelReader level, BlockPos pos, BlockPos piecePos, StructureTemplate.StructureBlockInfo blockInfo,
            StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = settings.getRandom(relativeBlockInfo.pos());
        BlockState state = relativeBlockInfo.state();
        random.setSeed(random.nextLong());
        if (state.getBlock() instanceof TAClusterBlock && random.nextBoolean()) {
            List<BlockState> budList = CrystalBudDecorator.clusterBudList();
            BlockState budState = transferAllStateKeys(state, budList.get(random.nextInt(budList.size())));
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), budState, null);
        }

        if (state.getBlock() instanceof BushBlock && random.nextBoolean()) {
            BlockState bushState = transferAllStateKeys(state, bushList().get(random.nextInt(bushList().size())));
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), bushState, null);
        }

        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TAStructureProcessors.CRYSTAL_BUD_PROCESSOR.get();
    }

    private static List<BlockState> bushList() {
        IntegerProperty level = BlockStateProperties.LEVEL;
        List<BlockState> list = new ArrayList<>();
        list.add(Blocks.AIR.defaultBlockState());
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof BushBlock bushBlock) {
                BlockState state = bushBlock.defaultBlockState();
                if (state.hasProperty(level)) {
                    level.getPossibleValues().forEach(lightLevel -> list.add(state.setValue(level, lightLevel)));
                } else {
                    list.add(state);
                }
            }
        }

        return list;
    }

    private static BlockState transferAllStateKeys(BlockState stateIn, BlockState stateOut) {
        for (Property<?> property : stateOut.getProperties()) {
            stateOut = transferStateKey(stateIn, stateOut, property);
        }
        return stateOut;
    }

    private static <T extends Comparable<T>> BlockState transferStateKey(BlockState stateIn, BlockState stateOut, Property<T> property) {
        boolean flag = !stateIn.hasProperty(property) || !stateOut.hasProperty(property);
        return flag ? stateOut : stateOut.setValue(property, stateIn.getValue(property));
    }

}