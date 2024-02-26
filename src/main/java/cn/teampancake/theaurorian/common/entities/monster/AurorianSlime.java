package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AurorianSlime extends Slime {

    public AurorianSlime(EntityType<? extends AurorianSlime> type, Level level) {
        super(type, level);
    }

    public static boolean checkSpawnRules(EntityType<AurorianSlime> aurorianSlime, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkMobSpawnRules(aurorianSlime, level, spawnType, pos, random);
    }

}