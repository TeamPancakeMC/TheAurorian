package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.ShieldSyncS2CMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class TempShield extends BaseShield {

    private IShield shieldType;

    public TempShield(int priority, float shield, float maxShield, int color) {
        super(priority, shield, maxShield,color);
    }

    @Override
    public IShield copy() {
        return new TempShield(getPriority(),getShield(), getMaxShield(),getColor());
    }

    public void setTempShield(IShield shieldType) {
        this.shieldType = shieldType;
        this.increaseShield(shieldType.getShield());
        this.increaseMaxShield(shieldType.getMaxShield());
    }

    public void clearTempShield(ServerPlayer player){
        consumeShield(getShield());
        consumeMaxShield(getMaxShield());
        this.shieldType = null;
        TAMessages.sendToPlayer(new ShieldSyncS2CMessage(serializeNBT()),player);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
        if (this.shieldType != null){
            compoundTag.put("tempShield",shieldType.serializeNBT());
        }
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        super.deserializeNBT(compoundTag);
        if (this.shieldType != null) {
            this.shieldType.deserializeNBT(compoundTag.getCompound("tempShield"));
        }
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        if (this.shieldType != null) {
            return this.shieldType.naturalRecovery(entity);
        }
        return super.naturalRecovery(entity);
    }

    @Override
    public boolean isNaturalRecovery(LivingEntity entity) {
        if (this.shieldType != null) {
            return this.shieldType.isNaturalRecovery(entity);
        }
        return super.isNaturalRecovery(entity);
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.applyDamageModifiers(entity, source, damage);
        }
        return super.applyDamageModifiers(entity, source, damage);
    }

    @Override
    public float damage(LivingEntity entity, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.damage(entity, damage);
        }
        return super.damage(entity, damage);
    }

    @Override
    public boolean isDamageNegated(LivingEntity entity, DamageSource source, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.isDamageNegated(entity, source, damage);
        }
        return super.isDamageNegated(entity, source, damage);
    }

}