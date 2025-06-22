package cn.teampancake.theaurorian.common.event;

import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.*;

public class RegisterConflictingEffectEvent extends Event implements IModBusEvent {

    private final Multimap<Holder<MobEffect>, Holder<MobEffect>> conflictMap;

    public RegisterConflictingEffectEvent(Multimap<Holder<MobEffect>, Holder<MobEffect>> conflictMap) {
        this.conflictMap = conflictMap;
    }

    public void registerConflict(Holder<MobEffect> effect1, Holder<MobEffect> effect2) {
        if (effect1.equals(effect2)) {
            throw new IllegalArgumentException("Cannot register conflict between same effects");
        }

        this.conflictMap.put(effect1, effect2);
        this.conflictMap.put(effect2, effect1);
    }

    public void registerConflicts(Collection<Pair<Holder<MobEffect>, Holder<MobEffect>>> conflicts) {
        for (Pair<Holder<MobEffect>, Holder<MobEffect>> pair : conflicts) {
            this.registerConflict(pair.getFirst(), pair.getSecond());
        }
    }

}