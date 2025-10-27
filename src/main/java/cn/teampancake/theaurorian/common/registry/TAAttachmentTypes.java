package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.world_event.WorldEventData;
import cn.teampancake.theaurorian.common.shields.BaseShield;
import cn.teampancake.theaurorian.common.shields.ShieldInstance;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class TAAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TheAurorian.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_IN_FOREST = registerInteger("ticks_in_forest");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_STAND_STILL = registerInteger("ticks_stand_still");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ACTIVATION_TICKS = registerInteger("activation_ticks");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> MAX_ACTIVATION_TICKS = registerInteger("max_activation_ticks");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_FROSTBITE = registerInteger("ticks_frostbite");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TICKS_THERMAL_ENHANCEMENT = registerInteger("ticks_thermal_enhancement");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TRIGGER_CORRUPTION_COOLDOWN = registerInteger("trigger_corruption_cooldown");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TIME_UNTIL_PLAYER_CAN_PICKUP = registerInteger("time_until_player_can_pickup");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT = registerInteger("uninterrupted_hurt_by_moon_queen_count");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> NIGHT_SKY_COLOR =
            ATTACHMENT_TYPES.register("night_sky_color", () -> AttachmentType.builder(() -> 0x010e34)
                    .serialize(Codec.INT).sync(ByteBufCodecs.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TELEPORT_TO_AURORIAN_COUNT =
            ATTACHMENT_TYPES.register("teleport_to_aurorian_count", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> KILL_COUNT_IN_BLOOD_MOON =
            ATTACHMENT_TYPES.register("kill_count_in_blood_moon", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> DAMAGE_ACCUMULATION = registerFloat("damage_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> EXHAUSTION_ACCUMULATION = registerFloat("exhaustion_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> ARMOR_HURT_ACCUMULATION = registerFloat("armor_hurt_accumulation");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> SYLVANIS_PROGRESS = registerFloat("sylvanis_progress");

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SHOULD_SPAWN_IN_AURORIAN = registerBoolean("should_spawn_in_aurorain");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> CAN_SUMMON_OTHER_ARROW = registerBoolean("can_summon_other_arrow");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SUMMONED_BY_SILENT_BOW = registerBoolean("summoned_by_silent_bow");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> LOST_IN_FOREST = registerBoolean("lost_in_forest");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> SOUND_PLAYED_FLAG = registerBoolean("sound_played_flag");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> TRIGGER_CRITICAL_HIT = registerBoolean("trigger_critical_hit");
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IMMUNE_PRESSURE_BY_KILL_MOON_QUEEN =
            ATTACHMENT_TYPES.register("immune_pressure_by_kill_moon_queen", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IMMUNE_PRESSURE_UNTIL_NEXT_BLOOD_MOON =
            ATTACHMENT_TYPES.register("immune_pressure_until_next_blood_moon", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> REMOVE_BLESS_UNTIL_NEXT_BLOOD_MOON =
            ATTACHMENT_TYPES.register("remove_bless_until_next_blood_moon", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> FIRST_ENTER_AURORIAN =
            ATTACHMENT_TYPES.register("first_enter_aurorian", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());

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

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> ANIMATION_TEXTURE =
            ATTACHMENT_TYPES.register("animation_texture", () -> AttachmentType.builder(
                    () -> ResourceLocation.tryParse(StringUtils.EMPTY)).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ShieldStack>> CURRENT_SHIELD =
            ATTACHMENT_TYPES.register("current_shield", () -> AttachmentType.builder(
                    () -> ShieldStack.EMPTY).serialize(ShieldStack.CODEC).sync(ShieldStack.STREAM_CODEC).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<WorldEventData>> WORLD_EVENT_DATA =
            ATTACHMENT_TYPES.register("world_event_data", () -> AttachmentType.builder(
                    () -> new WorldEventData(new HashMap<>(), new HashMap<>(),
                            new HashMap<>(), new HashMap<>(), new HashMap<>())
            ).serialize(WorldEventData.CODEC).sync(WorldEventData.STREAM_CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Map<Holder<BaseShield>, ShieldInstance>>> SHIELDS =
            ATTACHMENT_TYPES.register("shields", () -> AttachmentType.<Map<Holder<BaseShield>, ShieldInstance>>builder(
                    () -> new HashMap<>()).serialize(Codec.unboundedMap(BaseShield.CODEC, ShieldInstance.CODEC))
                    .sync(ByteBufCodecs.map(HashMap::new, BaseShield.STREAM_CODEC, ShieldInstance.STREAM_CODEC)).build());

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> registerInteger(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Float>> registerFloat(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).sync(ByteBufCodecs.FLOAT).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> registerBoolean(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());
    }

    private static DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> registerBlockPos(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> BlockPos.ZERO).serialize(BlockPos.CODEC).copyOnDeath().build());
    }

}