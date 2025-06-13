package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.entities.npc.Selena;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.player.Player;

public class AurorianSlime extends Slime {

    public AurorianSlime(EntityType<? extends AurorianSlime> type, Level level) {
        super(type, level);
    }

    public static boolean checkSpawnRules(EntityType<AurorianSlime> aurorianSlime, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) != 0) {
            return false;
        }
        
        boolean isAurorianPlains = level.getBiome(pos).is(TABiomes.AURORIAN_PLAINS);
        boolean isAurorianForest = level.getBiome(pos).is(TABiomes.AURORIAN_FOREST) || level.getBiome(pos).is(TABiomes.AURORIAN_FOREST_HILL);
        
        if (isAurorianForest && random.nextInt(3) != 0) {
            return false;
        }
        
        if (isAurorianPlains && random.nextInt(4) != 0) {
            return false;
        }
        
        double inflateDistance = 20.0D;
        if (!level.getEntitiesOfClass(Player.class, 
                new net.minecraft.world.phys.AABB(
                    pos.getX() - inflateDistance, pos.getY() - inflateDistance, pos.getZ() - inflateDistance, 
                    pos.getX() + inflateDistance, pos.getY() + inflateDistance, pos.getZ() + inflateDistance
                )).isEmpty()) {
            if (random.nextInt(5) != 0) {
                return false;
            }
        }
        
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkMobSpawnRules(aurorianSlime, level, spawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AurorianVillager.class,
                Boolean.TRUE, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Selena.class,
                Boolean.TRUE, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0F));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource random = level.getRandom();
        
        // 获取当前生物群系
        boolean isAurorianPlains = level.getBiome(this.blockPosition()).is(TABiomes.AURORIAN_PLAINS);
        
        // 默认大小为size4 (2^2)
        int sizeCategory = 2;
        
        // 20%的概率生成size8 (2^3)
        if (random.nextFloat() < 0.2F) {
            sizeCategory = 3;
        }
        
        // 如果是在极光平原中，有1%的极低概率生成size32 (2^5)
        if (isAurorianPlains && random.nextFloat() < 0.01F) {
            sizeCategory = 5;
        }
        
        int j = 1 << sizeCategory;
        this.setSize(j, true);
        return spawnGroupData;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && source.getEntity() instanceof Mob mob && this.random.nextFloat() <= 0.25F) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30));
        }

        return flag;
    }

    @Override
    protected ParticleOptions getParticleType() {
        return new ItemParticleOption(ParticleTypes.ITEM, TAItems.AURORIAN_SLIMEBALL.get().getDefaultInstance());
    }

}