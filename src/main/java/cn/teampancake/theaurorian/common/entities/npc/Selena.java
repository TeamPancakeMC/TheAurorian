package cn.teampancake.theaurorian.common.entities.npc;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import cn.teampancake.theaurorian.common.network.InteractWithSelenaS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

/** @noinspection deprecation*/
public class Selena extends PathfinderMob implements GeoEntity {

    private static final RawAnimation ATTACK_BUFF = RawAnimation.begin().thenPlay("attack.buff");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Selena(EntityType<? extends Selena> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, Boolean.TRUE));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.5D, 32.0F));
        this.goalSelector.addGoal(3, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, Boolean.FALSE, entity -> entity.getLastHurtMob() instanceof Player));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, Boolean.FALSE, entity -> entity.getLastAttacker() instanceof Player));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Mob.class, Boolean.FALSE, entity -> entity instanceof Enemy));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(1));
        controllers.add(new AnimationController<>(this, "buff_controller", state -> PlayState.STOP)
                .triggerableAnim("buff_animation", ATTACK_BUFF).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.setCanPickUpLoot(true);
        return spawnGroupData;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            if (player instanceof ServerPlayer serverPlayer) {
                String name = this.getName().getString();
                PacketDistributor.sendToPlayer(serverPlayer, new InteractWithSelenaS2CPacket(name, this.getId()));
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            int safeTime = this.tickCount - this.lastHurtByMobTimestamp;
            if (safeTime > 140 && this.tickCount % 40 == 0) {
                this.heal(1.0F);
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        Level level = this.level();
        if (!level.isClientSide && this.isDeadOrDying()) {
            Spirit spirit = new Spirit(TAEntityTypes.SPIRIT.get(), level);
            spirit.setPos(this.position());
            spirit.setAngry(true);
            level.addFreshEntity(spirit);
        }

        super.die(damageSource);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return !(source.getEntity() instanceof Player) && !(source.getDirectEntity() instanceof Player) && super.hurt(source, amount);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        ItemStack stack = new ItemStack(TAItems.AURORIAN_STONE_SWORD);
        stack.enchant(TAEnchantments.get(this.level(), Enchantments.SMITE), 10);
        this.setItemSlot(EquipmentSlot.MAINHAND, stack);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0.0F);
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

}