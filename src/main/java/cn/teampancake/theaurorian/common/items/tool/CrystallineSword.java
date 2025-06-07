package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 月凝晶剑 - 一种可以发射结晶光束的高级武器
 */
public class CrystallineSword extends SwordItem {

    // 常量定义
    private static final int BASE_DURABILITY = 512;
    private static final int COOLDOWN_TICKS = 600; // 30秒冷却
    private static final int MIN_CHARGE_TIME = 5;
    private static final int CHARGE_SOUND_INTERVAL = 100; // 每5秒播放一次充能音效
    private static final int DAMAGE_INTERVAL = 20; // 每1秒伤害一次
    private static final int DAMAGE_START_TIME = 120; // 6秒后开始伤害
    private static final float SELF_DAMAGE = 3.0F;
    private static final float BEAM_MIN_DAMAGE = 5.0F;
    private static final float BEAM_MAX_DAMAGE = 15.0F;
    private static final int MAX_CHARGE_TIME = 40; // 最大蓄力时长2秒(40刻)
    private static final int BEAM_DURATION = 40; // 光束持续时间2秒(40刻)
    private static final int BEAM_DISTANCE = 30; // 光束最大距离
    private static final int PARTICLES_PER_BLOCK = 15; // 每个方块的粒子数量
    private static final float BEAM_WIDTH = 0.7F; // 光束宽度

    // 存储活跃的光束信息
    private static final ConcurrentHashMap<UUID, BeamInfo> ACTIVE_BEAMS = new ConcurrentHashMap<>();

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, new Item.Properties()
                .rarity(Rarity.EPIC)
                .durability(BASE_DURABILITY)
                .attributes(createAttributes(TAToolTiers.CRYSTALLINE, (3), (-2.4F)))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS, TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.playSound(TASoundEvents.CRYSTALLINE_SWORD_USE.get());
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration < 0 || !(livingEntity instanceof Player player)) {
            return;
        }

        int ticksUsed = player.getTicksUsingItem();

        // 播放充能音效
        if (ticksUsed > 60 && player.tickCount % CHARGE_SOUND_INTERVAL == 0) {
            player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get());
        }

        // 过度充能会伤害玩家
        if (!level.isClientSide && ticksUsed > DAMAGE_START_TIME && player.tickCount % DAMAGE_INTERVAL == 0) {
            player.hurt(level.damageSources().magic(), SELF_DAMAGE);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        int chargeTime = getUseDuration(stack, player) - timeLeft;
        if (chargeTime < MIN_CHARGE_TIME) {
            return;
        }

        // 停止充能音效
        stopChargingSound(player);

        // 播放发射音效
        player.playSound(TASoundEvents.CRYSTALLINE_SWORD_SHOOT.get());

        // 发射光束
        fireBeam(stack, level, player, chargeTime);
    }

    /**
     * 停止充能音效
     */
    private void stopChargingSound(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ResourceLocation id = TASoundEvents.CRYSTALLINE_SWORD_CHARGING.getId();
            serverPlayer.connection.send(new ClientboundStopSoundPacket(id, SoundSource.PLAYERS));
        }
    }

    /**
     * 发射结晶光束
     */
    private void fireBeam(ItemStack stack, Level level, Player player, int chargeTime) {
        // 消耗耐久度
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

        if (level.isClientSide) {
            return;
        }

        // 计算实际蓄力时间(最大2秒)
        int effectiveChargeTime = Math.min(chargeTime, MAX_CHARGE_TIME);

        // 创建光束信息
        BeamInfo beamInfo = new BeamInfo(
                player.getUUID(),
                player.getEyePosition(),
                player.getLookAngle(),
                calculateBeamDamage(effectiveChargeTime),
                System.currentTimeMillis(),
                BEAM_DURATION
        );

        // 注册光束
        ACTIVE_BEAMS.put(player.getUUID(), beamInfo);

        // 应用冷却
        if (!player.getAbilities().instabuild) {
            player.getCooldowns().addCooldown(TAItems.CRYSTALLINE_SWORD.get(), COOLDOWN_TICKS);
        }

        // 在服务器端触发光束效果
        if (level instanceof ServerLevel serverLevel) {
            spawnBeamParticles(serverLevel, beamInfo);
            applyBeamDamage(serverLevel, beamInfo);
        }
    }

    /**
     * 生成光束粒子效果
     */
    private static void spawnBeamParticles(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;

        // 计算光束终点(碰撞检测)
        HitResult hitResult = rayTraceBeam(level, start, direction, BEAM_DISTANCE, beamInfo.owner);
        Vec3 end;

        if (hitResult.getType() == HitResult.Type.MISS) {
            end = start.add(direction.scale(BEAM_DISTANCE));
        } else {
            end = hitResult.getLocation();
        }

        // 计算光束长度
        double distance = start.distanceTo(end);
        int particleCount = (int)(distance * PARTICLES_PER_BLOCK);

        // 生成粒子
        for (int i = 0; i < particleCount; i++) {
            double t = i / (double)particleCount;
            Vec3 pos = start.add(direction.scale(t * distance));

            // 添加一些随机偏移使光束看起来更自然
            double offsetX = (level.getRandom().nextDouble() - 0.5) * BEAM_WIDTH;
            double offsetY = (level.getRandom().nextDouble() - 0.5) * BEAM_WIDTH;
            double offsetZ = (level.getRandom().nextDouble() - 0.5) * BEAM_WIDTH;

            // 生成粒子(发送给所有玩家)
            level.sendParticles(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    pos.x + offsetX,
                    pos.y + offsetY,
                    pos.z + offsetZ,
                    2, // 粒子数量
                    0, 0, 0, // 速度
                    0.01 // 速度因子
            );
        }
    }

    /**
     * 应用光束伤害
     */
    private static void applyBeamDamage(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;
        float damage = beamInfo.damage;

        // 获取光束路径上的实体
        List<Entity> entities = getEntitiesInBeamPath(level, start, direction, BEAM_DISTANCE, beamInfo.owner);
        Player player = level.getPlayerByUUID(beamInfo.owner);
        // 对实体造成伤害
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                // 计算伤害值，根据实体到光束中心的距离进行调整
                Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
                double distanceToBeam = distanceToLine(start, start.add(direction.scale(BEAM_DISTANCE)), entityPos);
                // 距离光束中心越近，伤害越高，最高为原始伤害的1.2倍
                float distanceMultiplier = (float)(1.2 - (distanceToBeam / (BEAM_WIDTH * 1.5)) * 0.4);
                float adjustedDamage = damage * Math.max(0.8f, distanceMultiplier);

                livingEntity.hurt(level.damageSources().indirectMagic(
                        level.getPlayerByUUID(beamInfo.owner),
                        level.getPlayerByUUID(beamInfo.owner)
                ), adjustedDamage);

                if(((LivingEntity) entity).isDeadOrDying()){
                    player.getCooldowns().removeCooldown(TAItems.CRYSTALLINE_SWORD.get());
                }else {
                    // 添加眩晕效果，距离越近眩晕时间越长
                    int stunDuration = (int)(40 * Math.max(0.8f, distanceMultiplier));
                    livingEntity.addEffect(new MobEffectInstance(TAMobEffects.STUN, stunDuration));
                }

            }
        }
    }

    /**
     * 获取光束路径上的实体
     */
    private static List<Entity> getEntitiesInBeamPath(Level level, Vec3 start, Vec3 direction, double maxDistance, UUID ownerUUID) {
        // 计算光束终点
        HitResult hitResult = rayTraceBeam(level, start, direction, maxDistance, ownerUUID);
        Vec3 end;

        if (hitResult.getType() == HitResult.Type.MISS) {
            end = start.add(direction.scale(maxDistance));
        } else {
            end = hitResult.getLocation();
        }

        // 创建包围盒
        double minX = Math.min(start.x, end.x) - BEAM_WIDTH;
        double minY = Math.min(start.y, end.y) - BEAM_WIDTH;
        double minZ = Math.min(start.z, end.z) - BEAM_WIDTH;
        double maxX = Math.max(start.x, end.x) + BEAM_WIDTH;
        double maxY = Math.max(start.y, end.y) + BEAM_WIDTH;
        double maxZ = Math.max(start.z, end.z) + BEAM_WIDTH;

        AABB beamBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);

        // 获取包围盒内的所有实体

        return level.getEntities(
                level.getPlayerByUUID(ownerUUID),
                beamBox,
                entity -> {
                    // 检查实体是否在光束路径上
                    Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
                    double distanceToLine = distanceToLine(start, end, entityPos);
                    // 使用更宽松的判定，实体中心点到光束的距离小于光束宽度的1.5倍即可命中
                    return distanceToLine < BEAM_WIDTH * 1.5;
                }
        );
    }

    /**
     * 光束射线追踪
     */
    private static HitResult rayTraceBeam(Level level, Vec3 start, Vec3 direction, double maxDistance, UUID ownerUUID) {
        Vec3 end = start.add(direction.scale(maxDistance));

        // 排除发射者自身
        Entity owner = level.getPlayerByUUID(ownerUUID);

        // 创建ClipContext
        ClipContext clipContext = new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                owner
        );

        // 执行射线检测
        BlockHitResult blockHit = level.clip(clipContext);

        // 如果没有击中方块，返回MISS类型的结果
        if (blockHit.getType() == HitResult.Type.MISS) {
            return blockHit;
        }

        // 检查是否击中实体
        double blockDistance = blockHit.getLocation().distanceTo(start);

        // 获取光束路径上的所有实体
        AABB searchBox = new AABB(
                Math.min(start.x, end.x) - BEAM_WIDTH,
                Math.min(start.y, end.y) - BEAM_WIDTH,
                Math.min(start.z, end.z) - BEAM_WIDTH,
                Math.max(start.x, end.x) + BEAM_WIDTH,
                Math.max(start.y, end.y) + BEAM_WIDTH,
                Math.max(start.z, end.z) + BEAM_WIDTH
        );

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : level.getEntities(owner, searchBox, entity -> {
            if (entity == owner) return false;

            // 检查实体是否在光束路径上
            Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
            double distanceToLine = distanceToLine(start, end, entityPos);
            // 使用更宽松的判定，实体中心点到光束的距离小于光束宽度的1.5倍即可命中
            return distanceToLine < BEAM_WIDTH * 1.5;
        })) {
            double distance = entity.position().distanceTo(start);
            if (distance < closestDistance && distance < blockDistance) {
                closestEntity = entity;
                closestDistance = distance;
            }
        }

        // 如果找到实体且比方块更近，返回实体命中结果
        if (closestEntity != null) {
            Vec3 hitPos = start.add(direction.scale(closestDistance));
            return new EntityHitResult(closestEntity, hitPos);
        }

        // 否则返回方块命中结果
        return blockHit;
    }

    /**
     * 计算点到线段的距离
     */
    private static double distanceToLine(Vec3 lineStart, Vec3 lineEnd, Vec3 point) {
        Vec3 line = lineEnd.subtract(lineStart);
        double len = line.length();
        if (len == 0.0) return point.distanceTo(lineStart);

        // 计算点在线上的投影
        double t = Math.max(0, Math.min(1, point.subtract(lineStart).dot(line) / (len * len)));
        Vec3 projection = lineStart.add(line.scale(t));
        return point.distanceTo(projection);
    }

    /**
     * 根据充能时间计算光束伤害
     */
    private float calculateBeamDamage(int chargeTime) {
        float chargeRatio = Math.min(1.0F, chargeTime / (float)MAX_CHARGE_TIME);
        return BEAM_MIN_DAMAGE + (BEAM_MAX_DAMAGE - BEAM_MIN_DAMAGE) * chargeRatio;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000; // 最大使用时间
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    /**
     * 光束信息类
     */
    private static class BeamInfo {
        final UUID owner;
        final Vec3 startPos;
        final Vec3 direction;
        final float damage;
        final long startTime;
        final int duration;

        BeamInfo(UUID owner, Vec3 startPos, Vec3 direction, float damage, long startTime, int duration) {
            this.owner = owner;
            this.startPos = startPos;
            this.direction = direction;
            this.damage = damage;
            this.startTime = startTime;
            this.duration = duration;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > startTime + (duration * 50); // 50ms per tick
        }
    }

    /**
     * 月凝晶剑使用动画实现
     */
    public static class CrystallineSwordUseAnim implements IClientItemExtensions {

        public static final EnumProxy<HumanoidModel.ArmPose> CRYSTALLINE_SWORD_SHOOT = new EnumProxy<>(
                HumanoidModel.ArmPose.class, true, ((IArmPoseTransformer) (model, entity, arm) -> {
            model.rightArm.yRot = -0.1F + model.head.yRot - 0.4F;
            model.leftArm.yRot = 0.1F + model.head.yRot + 0.4F;
            model.rightArm.xRot = (-(float)Math.PI / 2.0F) + model.head.xRot;
            model.leftArm.xRot = (-(float)Math.PI / 2.0F) + model.head.xRot;
        }));

        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (itemStack.is(TAItems.CRYSTALLINE_SWORD.get()) &&
                    entityLiving.getUsedItemHand() == hand &&
                    entityLiving.getUseItemRemainingTicks() > 0) {
                return CRYSTALLINE_SWORD_SHOOT.getValue();
            }

            return HumanoidModel.ArmPose.EMPTY;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            if (!(player.getUseItem() == itemInHand && player.isUsingItem())) {
                return false;
            }

            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            float useDuration = (float) itemInHand.getUseDuration(player);
            float remainingTicks = (float) player.getUseItemRemainingTicks();

            // 计算动画进度
            float animProgress = useDuration - (remainingTicks - partialTick + 1.0F);
            float normalizedProgress = animProgress / 20.0F;
            normalizedProgress = (normalizedProgress * normalizedProgress + normalizedProgress * 2.0F) / 3.0F;
            normalizedProgress = Math.min(normalizedProgress, 1.0F);

            // 应用变换
            float limit = (float)i * -0.641864F;
            poseStack.translate(i * 0.56F, -0.52F, -0.72F);
            poseStack.translate(Math.max(limit, limit * 3.0F * normalizedProgress), 0.05F, 0.0F);
            poseStack.mulPose(Axis.XN.rotationDegrees(Math.min(90.0F, 90.0F * 3.0F * normalizedProgress)));
            poseStack.mulPose(Axis.YN.rotation(0.0F));

            return true;
        }
    }

    /**
     * 服务器tick事件处理
     * 注意：此方法应该在ServerTickEvent中调用
     */
    public static void serverTick(ServerLevel level) {
        // 清理过期的光束
        ACTIVE_BEAMS.entrySet().removeIf(entry -> {
            BeamInfo beamInfo = entry.getValue();
            if (beamInfo.isExpired()) {
                return true;
            }

            // 继续生成粒子和应用伤害
            spawnBeamParticles(level, beamInfo);
            applyBeamDamage(level, beamInfo);  // 添加持续伤害效果
            return false;
        });
    }
}