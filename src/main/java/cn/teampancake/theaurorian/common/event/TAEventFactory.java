package cn.teampancake.theaurorian.common.event;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.NeoForge;

public class TAEventFactory {

    public static void onRegisterConflictingEffect(Multimap<Holder<MobEffect>, Holder<MobEffect>> conflictMap) {
        NeoForge.EVENT_BUS.post(new RegisterConflictingEffectEvent(conflictMap));
    }

}