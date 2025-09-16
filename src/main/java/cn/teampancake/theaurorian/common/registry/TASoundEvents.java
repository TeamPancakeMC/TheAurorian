package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TASoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, TheAurorian.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_USE = register("crystalline_sword_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_CHARGING = register("crystalline_sword_charging");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTALLINE_SWORD_SHOOT = register("crystalline_sword_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> WEEPING_WILLOW_BELL = register("weeping_willow_bell");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOW_TUNDRA_GIANT_CRAB_AMBIENT = register("snow_tundra_giant_crab/ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOW_TUNDRA_GIANT_CRAB_STEP = register("snow_tundra_giant_crab/step");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOW_TUNDRA_GIANT_CRAB_HURT = register("snow_tundra_giant_crab/hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOW_TUNDRA_GIANT_CRAB_DEATH = register("snow_tundra_giant_crab/death");
    public static final DeferredHolder<SoundEvent, SoundEvent> FOREST_BACKGROUND_MUSIC = register("forest_music");
    public static final DeferredHolder<SoundEvent, SoundEvent> UNIVERSAL_BACKGROUND_MUSIC = register("universal_music");
    public static final DeferredHolder<SoundEvent, SoundEvent> AURORIAN_FOREST = register("aurorian_forest");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOONLIT_VEIL = register("moonlit_veil");
    public static final DeferredHolder<SoundEvent, SoundEvent> EMPTY = register("empty");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(TheAurorian.prefix(sound)));
    }

}