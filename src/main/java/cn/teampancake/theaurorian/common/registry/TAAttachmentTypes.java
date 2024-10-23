package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class TAAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TheAurorian.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_FROSTBITE = registerInteger("ticks_frostbite");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_THERMAL_ENHANCEMENT = registerInteger("ticks_thermal_enhancement");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT = registerInteger("uninterrupted_hurt_by_moon_queen_count");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> DAMAGE_ACCUMULATION = registerFloat("damage_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> EXHAUSTION_ACCUMULATION = registerFloat("exhaustion_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> ARMOR_HURT_ACCUMULATION = registerFloat("armor_hurt_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IMMUNE_TO_PRESSURE = registerBoolean("immune_to_pressure");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<List<ResourceLocation>>> MAX_HEALTH_SUBTRACT_IDS =
            ATTACHMENT_TYPES.register("max_health_subtract_id", () -> AttachmentType.<List<ResourceLocation>>builder(() -> new ArrayList<>()).build());

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> registerInteger(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Float>> registerFloat(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> registerBoolean(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());
    }

}