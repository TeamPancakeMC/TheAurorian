package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;

public record ArrowRainSummonArrowEffect(Unit unit) implements EnchantmentEntityEffect {

    public static final MapCodec<ArrowRainSummonArrowEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(ArrowRainSummonArrowEffect::unit)).apply(instance, ArrowRainSummonArrowEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof AbstractArrow arrow) {
            AttachmentType<Boolean> type1 = TAAttachmentTypes.CAN_SUMMON_OTHER_ARROW.get();
            AttachmentType<Boolean> type2 = TAAttachmentTypes.SUMMONED_BY_SILENT_BOW.get();
            int universalLife = arrow.life;
            ItemStack weaponItem = arrow.getWeaponItem();
            boolean flag = arrow.getData(type1);
            if (flag && !arrow.getData(type2)) {
                arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                List<Vec3> vec3s = arrow.getData(TAAttachmentTypes.ARROWS_SPAWN_VEC3);
                if (!vec3s.isEmpty() && arrow.life < vec3s.size()) {
                    Vec3 vec3 = vec3s.get(arrow.life);
                    if (vec3.x > 0.0F || vec3.y > 0.0F || vec3.z > 0.0F) {
                        if (arrow.getType().create(level) instanceof AbstractArrow copyOfArrow) {
                            copyOfArrow.setUUID(Mth.createInsecureUUID());
                            copyOfArrow.setDeltaMovement(0, -3.0D, 0);
                            copyOfArrow.setPos(vec3);
                            copyOfArrow.setCritArrow(true);
                            copyOfArrow.setData(type2, true);
                            copyOfArrow.firedFromWeapon = weaponItem;
                            copyOfArrow.life = 1100;
                            level.addFreshEntity(copyOfArrow);
                            vec3s.set(arrow.life, Vec3.ZERO);
                        }
                    }
                }
            }

            if (weaponItem != null && weaponItem.getItem() instanceof ProjectileWeaponItem) {
                if (flag && universalLife > arrow.getData(TAAttachmentTypes.TIME_UNTIL_PLAYER_CAN_PICKUP)) {
                    arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                } else {
                    arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                }

                if (arrow.getData(type2)) {
                    arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}