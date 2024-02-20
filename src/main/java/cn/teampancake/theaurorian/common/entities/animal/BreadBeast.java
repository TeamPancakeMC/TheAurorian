package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BreadBeast extends PathfinderMob {

    private static final EntityDataAccessor<Integer> TAIL_STATE = SynchedEntityData.defineId(BreadBeast.class, EntityDataSerializers.INT);
    private int coolDownTime = 6000;

    public BreadBeast(EntityType<? extends BreadBeast> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0D).add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @SuppressWarnings("unused")
    public static boolean checkBreadBeastSpawnRules(EntityType<BreadBeast> breadBeast, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 6;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TAIL_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("TailState", this.getTailState());
        compound.putInt("CoolDownTime", this.coolDownTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setTailState(compound.getInt("TailState"));
        this.coolDownTime = compound.getInt("CoolDownTime");
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && this.coolDownTime > 0) {
            this.coolDownTime--;
        }
    }

    public void setTailState(int state) {
        this.entityData.set(TAIL_STATE, Math.min(3, state));
    }

    public int getTailState() {
        return this.entityData.get(TAIL_STATE);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        FoodData foodData = player.getFoodData();
        if (this.getTailState() >= 3 || !foodData.needsFood()) {
            return InteractionResult.PASS;
        } else {
            int i = this.getTailState();
            ItemStack itemInHand = player.getItemInHand(player.getUsedItemHand());
            float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
            if (itemInHand.is(TAItems.LAVENDER.get())) {
                boolean isCreative = player.getAbilities().instabuild;
                this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, f1);
                if (!this.level().isClientSide && i == 0 && (this.coolDownTime == 0 || isCreative)) {
                    this.coolDownTime = 6000;
                    this.setTailState(0);
                    if (!isCreative) {
                        itemInHand.shrink(1);
                    }
                }
            } else {
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, f1);
                foodData.eat((4), (0.1F));
                if (!this.level().isClientSide) {
                    this.setTailState(i + 1);
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
    }

}