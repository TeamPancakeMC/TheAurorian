package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.tags.DamageTypeTags.*;

@SuppressWarnings("unchecked")
public class CommonShield extends BaseShield{

    protected static final TagKey<DamageType>[] DAMAGE_TYPE_TAGS = new TagKey[]{
            IS_FIRE, IS_PROJECTILE, IS_EXPLOSION, IS_FALL, WITCH_RESISTANT_TO};

    public CommonShield(int priority,float shield, float maxShield,int color) {
        super(priority, shield, maxShield,color);
        this.rate = 0.25f;
    }

    @Override
    public IShield copy() {
        return new CommonShield(getPriority(),getShield(), getMaxShield(),getColor());
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 1.0f;
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        if (source.is(DamageTypes.MOB_ATTACK)) {
            return 0.0f;
        }
        for (TagKey<DamageType> damageTypeTag : DAMAGE_TYPE_TAGS) {
            if (source.is(damageTypeTag)) {
                return damage * (1 - this.rate);
            }
        }
        return damage;
    }

}