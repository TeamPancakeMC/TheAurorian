package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class AurorianRabbit extends Rabbit {

    private static final ResourceLocation BLOOD_MOON_ATTACK_POWER_MODIFIER = TheAurorian.prefix("blood_moon_rabbit");

    public AurorianRabbit(EntityType<? extends AurorianRabbit> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Rabbit.createAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.SILK_BERRY.get());
    }

    @Nullable
    @Override
    public AurorianRabbit getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return TAEntityTypes.AURORIAN_RABBIT.get().create(level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(1, new RabbitPanicGoal(this, 2.2));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0,
                Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.4F, Boolean.FALSE));
        this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal<>(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal<>(this, Wolf.class, 10.0F));
        this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal<>(this, BlueTailWolf.class, 10.0F));
        this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal<>(this, Monster.class, 4.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        AttributeInstance instance = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (TAWorldEvents.BLOOD_MOON.get().isActive(level.getLevel()) && instance != null) {
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_VALUE;
            instance.addOrUpdateTransientModifier(new AttributeModifier(BLOOD_MOON_ATTACK_POWER_MODIFIER, 1.0, operation));
        }

        return spawnGroupData;
    }

    static class RabbitPanicGoal extends PanicGoal {

        private final Rabbit rabbit;

        public RabbitPanicGoal(Rabbit rabbit, double speedModifier) {
            super(rabbit, speedModifier);
            this.rabbit = rabbit;
        }

        public void tick() {
            super.tick();
            this.rabbit.setSpeedModifier(this.speedModifier);
        }

    }

    static class RabbitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

        private final Rabbit rabbit;

        public RabbitAvoidEntityGoal(Rabbit rabbit, Class<T> entityClassToAvoid, float maxDist) {
            super(rabbit, entityClassToAvoid, maxDist, 2.2D, 2.2D);
            this.rabbit = rabbit;
        }

        public boolean canUse() {
            return this.rabbit.getVariant() != Variant.EVIL && super.canUse();
        }

    }

}