package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;

public class TAAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TheAurorian.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_FROSTBITE = registerInteger("ticks_frostbite");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_THERMAL_ENHANCEMENT = registerInteger("ticks_thermal_enhancement");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TRIGGER_CORRUPTION_COOLDOWN = registerInteger("trigger_corruption_cooldown");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TIME_UNTIL_PLAYER_CAN_PICKUP = registerInteger("time_until_player_can_pickup");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT = registerInteger("uninterrupted_hurt_by_moon_queen_count");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TELEPORT_TO_AURORIAN_COUNT =
            ATTACHMENT_TYPES.register("teleport_to_aurorian_count", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> DAMAGE_ACCUMULATION = registerFloat("damage_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> EXHAUSTION_ACCUMULATION = registerFloat("exhaustion_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> ARMOR_HURT_ACCUMULATION = registerFloat("armor_hurt_accumulation");

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SHOULD_SPAWN_IN_AURORIAN = registerBoolean("should_spawn_in_aurorain");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> CAN_SUMMON_OTHER_ARROW = registerBoolean("can_summon_other_arrow");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SUMMONED_BY_SILENT_BOW = registerBoolean("summoned_by_silent_bow");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IMMUNE_TO_PRESSURE =
            ATTACHMENT_TYPES.register("immune_to_pressure", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> LAST_POS_OF_LEAVE_AURORIAN = registerBlockPos("last_pos_of_leave_aurorain");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> LAST_POS_OF_LEAVE_OVERWORLD = registerBlockPos("last_pos_of_leave_overworld");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> SPAWN_POINT_OF_AURORIAN = registerBlockPos("spawn_point_of_aurorain");

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<List<ResourceLocation>>> MAX_HEALTH_SUBTRACT_IDS =
            ATTACHMENT_TYPES.register("max_health_subtract_id", () -> AttachmentType.<List<ResourceLocation>>builder(
                    () -> new ArrayList<>()).serialize(ResourceLocation.CODEC.listOf()).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<List<Integer>>> RUNE_GAME_TIME_CONSUMING =
            ATTACHMENT_TYPES.register("rune_game_time_consuming", () -> AttachmentType.<List<Integer>>builder(
                    () -> new ArrayList<>()).serialize(Codec.INT.listOf()).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<List<UUID>>> BINDING_PLAYER_UUIDS =
            ATTACHMENT_TYPES.register("binding_player_uuid", () -> AttachmentType.<List<UUID>>builder(
                    () -> new ArrayList<>()).serialize(UUIDUtil.CODEC.listOf()).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<List<Vec3>>> ARROWS_SPAWN_VEC3 =
            ATTACHMENT_TYPES.register("arrows_spawn_vec3", () -> AttachmentType.<List<Vec3>>builder(
                    () -> new ArrayList<>()).serialize(Vec3.CODEC.listOf()).build());

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> registerInteger(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Float>> registerFloat(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> registerBoolean(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> registerBlockPos(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> BlockPos.ZERO).serialize(BlockPos.CODEC).copyOnDeath().build());
    }

}