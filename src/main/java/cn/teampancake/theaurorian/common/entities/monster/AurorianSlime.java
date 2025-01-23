package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.entities.npc.Selena;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.registry.TAItems;
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

public class AurorianSlime extends Slime {

    public AurorianSlime(EntityType<? extends AurorianSlime> type, Level level) {
        super(type, level);
    }

    public static boolean checkSpawnRules(EntityType<AurorianSlime> aurorianSlime, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkMobSpawnRules(aurorianSlime, level, spawnType, pos, random);
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
        int i = random.nextInt(3);
        if (i < 2 && random.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
            i++;
        }

        if (LevelEventSubscriber.phaseCode == 0 && random.nextFloat() <= 0.15F) {
            i = 3;
        }

        int j = 1 << i;
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