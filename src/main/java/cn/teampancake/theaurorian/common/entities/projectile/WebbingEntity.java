package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;

import java.util.UUID;

public class WebbingEntity extends ThrowableItemProjectile {

    public static final UUID MAX_HEALTH_SUBTRACT_WEBBING = UUID.fromString("c25109f6-62d9-41d4-bb3a-3d7a0852de7b");

    public WebbingEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public WebbingEntity(LivingEntity shooter, Level level) {
        super(TAEntityTypes.WEBBING.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return TAItems.WEBBING.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Level level = this.level();
        if (!level.isClientSide) {
            if (result.getEntity() instanceof LivingEntity entity && !entity.getType().is(TAEntityTags.SPIDERLING)) {
                entity.hurt(this.damageSources().thrown(this, this.getOwner()), 2.5F);
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                this.subtractMaxHealthWebbing(entity);
                BlockPos pos = entity.blockPosition();
                if (level.getBlockState(pos).canBeReplaced()) {
                    level.setBlockAndUpdate(pos, Blocks.COBWEB.defaultBlockState());
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    private void subtractMaxHealthWebbing(LivingEntity livingEntity) {
        AttributeInstance instance = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier.Operation operation = AttributeModifier.Operation.MULTIPLY_TOTAL;
        if (instance != null && instance.getModifier(MAX_HEALTH_SUBTRACT_WEBBING) == null) {
            instance.addTransientModifier(new AttributeModifier(MAX_HEALTH_SUBTRACT_WEBBING, "Webbing", -0.25F, operation));
        }
    }

}