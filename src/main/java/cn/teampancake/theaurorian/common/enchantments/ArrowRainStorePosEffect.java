package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record ArrowRainStorePosEffect(Unit unit) implements EnchantmentEntityEffect {

    public static final MapCodec<ArrowRainStorePosEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(ArrowRainStorePosEffect::unit)).apply(instance, ArrowRainStorePosEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof AbstractArrow arrow) {
            ItemStack weaponItem = arrow.getWeaponItem();
            if (weaponItem != null && weaponItem.is(TAItems.SILENT_WOOD_BOW)) {
                List<Vec3> list = new ArrayList<>(20);
                RandomSource random = arrow.level().random;
                int index = 0;
                for (int i = 0; i < enchantmentLevel * 5; i++) {
                    index += random.nextInt(2) + 1;
                    double angle = random.nextDouble() * Math.PI * 2;
                    double distance = random.nextDouble() * 5.0D;
                    double x = origin.x + Math.cos(angle) * distance;
                    double z = origin.z + Math.sin(angle) * distance;
                    double y = origin.y + 10.0D;
                    while (list.size() <= index) {
                        list.add(Vec3.ZERO);
                    }

                    list.set(index, new Vec3(x, y, z));
                }

                arrow.setData(TAAttachmentTypes.TIME_UNTIL_PLAYER_CAN_PICKUP, index);
                arrow.setData(TAAttachmentTypes.CAN_SUMMON_OTHER_ARROW, true);
                arrow.setData(TAAttachmentTypes.ARROWS_SPAWN_VEC3, list);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}