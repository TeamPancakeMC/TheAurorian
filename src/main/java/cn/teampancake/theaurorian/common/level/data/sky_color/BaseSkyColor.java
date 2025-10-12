package cn.teampancake.theaurorian.common.level.data.sky_color;

import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public record BaseSkyColor(int color, Consumer<LivingEntity> effect) {

}