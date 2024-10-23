package cn.teampancake.theaurorian.common.level.effect;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;

public class CorruptionEffectInstance extends MobEffectInstance {

    public CorruptionEffectInstance(MobEffectInstance other) {
        super(other);
    }

    @Override
    public int tickDownDuration() {
        if (this.hiddenEffect != null) {
            this.hiddenEffect.tickDownDuration();
        }

        boolean flag = RandomSource.create().nextInt(100) < 10;
        return this.duration = this.mapDuration(key -> key - (flag ? 2 : 1));
    }

}