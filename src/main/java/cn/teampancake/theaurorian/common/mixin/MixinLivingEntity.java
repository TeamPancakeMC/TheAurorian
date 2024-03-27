package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAEnchantments;
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
public abstract class MixinLivingEntity extends Entity {

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    public MixinLivingEntity(EntityType<?> type, Level level) {
        super(type, level);
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

}