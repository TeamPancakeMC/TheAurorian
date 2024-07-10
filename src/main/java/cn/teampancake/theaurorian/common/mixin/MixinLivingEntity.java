package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.entities.animal.IOverworldAurorianAnimal;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements IOverworldAurorianAnimal {

    private static final EntityDataAccessor<Boolean> SPAWN_IN_OVERWORLD = SynchedEntityData.defineId(MixinLivingEntity.class, EntityDataSerializers.BOOLEAN);

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    public MixinLivingEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At(value = "TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(SPAWN_IN_OVERWORLD, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putBoolean("SpawnInOverworld", this.isSpawnInOverworld());
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.setSpawnInOverworld(compound.getBoolean("SpawnInOverworld"));
    }

    @Inject(method = "broadcastBreakEvent(Lnet/minecraft/world/entity/EquipmentSlot;)V", at = @At(value = "TAIL"))
    public void broadcastBreakEvent(EquipmentSlot slot, CallbackInfo ci) {
        AABB aabb = this.getBoundingBox().inflate(15.0D);
        if (this.getItemBySlot(slot).getEnchantmentLevel(TAEnchantments.AMNESIA_CURSE.get()) > 0) {
            List<LivingEntity> livingEntities = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
            livingEntities.forEach(entity -> {
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600));
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600));
            });
        }
    }

    @Override
    public boolean isSpawnInOverworld() {
        return this.entityData.get(SPAWN_IN_OVERWORLD);
    }

    @Override
    public void setSpawnInOverworld(boolean value) {
        this.entityData.set(SPAWN_IN_OVERWORLD, value);
    }

}