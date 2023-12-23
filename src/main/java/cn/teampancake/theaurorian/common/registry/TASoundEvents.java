package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TASoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AurorianMod.MOD_ID);
    public static final RegistryObject<SoundEvent> WEEPING_WILLOW_BELL = register("weeping_willow_bell");
    public static final RegistryObject<SoundEvent> BACKGROUND_MUSIC = register("music");

    private static RegistryObject<SoundEvent> register(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(AurorianMod.prefix(sound)));
    }

}