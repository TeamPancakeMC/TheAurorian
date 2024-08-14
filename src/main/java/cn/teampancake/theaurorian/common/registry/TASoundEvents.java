package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TASoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, AurorianMod.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_USE = register("crystalline_sword_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_CHARGING = register("crystalline_sword_charging");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_SHOOT = register("crystalline_sword_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> WEEPING_WILLOW_BELL = register("weeping_willow_bell");
    public static final DeferredHolder<SoundEvent, SoundEvent> BACKGROUND_MUSIC = register("music");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(AurorianMod.prefix(sound)));
    }

}