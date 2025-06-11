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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
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
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;
import net.minecraft.network.chat.Component;

public class CrystallineSword extends SwordItem {

    // 常量定义
    private static final int COOLDOWN_TICKS = 600; // 30秒冷却
    private static final int MIN_CHARGE_TIME = 5;
    private static final int CHARGE_SOUND_INTERVAL = 100; // 每5秒播放一次充能音效
    private static final int DAMAGE_INTERVAL = 20; // 每1秒伤害一次
    private static final int DAMAGE_START_TIME = 120; // 6秒后开始伤害
    private static final float SELF_DAMAGE = 1.0F;
    private static final float BEAM_MIN_DAMAGE = 5.0F;
    private static final float BEAM_MAX_DAMAGE = 20.0F; // 最大伤害提升至20
    private static final int MAX_CHARGE_TIME = 60; // 最大蓄力时长3秒(60刻)
    private static final int BEAM_DURATION = 40; // 光束持续时间2秒(40刻)
    private static final int BEAM_DISTANCE = 70; // 光束最大距离，从50增加到70
    private static final int PARTICLES_PER_BLOCK = 25; // 每个方块的粒子数量
    private static final float BEAM_WIDTH = 1.2F; // 光束宽度
    
    // 超级蓄力相关常量
    private static final int SUPER_CHARGE_TIME = 120; // 超级蓄力时间6秒(120刻)
    private static final float SUPER_BEAM_DAMAGE = 50.0F; // 超级蓄力伤害
    private static final float SUPER_BEAM_WIDTH = 2.5F; // 超级光束宽度
    private static final int SUPER_PARTICLES_PER_BLOCK = 40; // 超级光束每方块粒子数量
    private static final float EXPLOSION_POWER = 3.0F; // 爆炸威力

    // 存储活跃的光束信息
    private static final ConcurrentHashMap<UUID, BeamInfo> ACTIVE_BEAMS = new ConcurrentHashMap<>();

    public CrystallineSword() {
        super(TAToolTiers.CRYSTALLINE, new Item.Properties().rarity(Rarity.EPIC).durability(512)
                .attributes(createAttributes(TAToolTiers.CRYSTALLINE, 3, -2.4F))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS, TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemInHand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration < 0 || !(livingEntity instanceof Player player)) {
            return;
        }

        int ticksUsed = player.getTicksUsingItem();
        
        // 检查是否达到超级充能阈值且武器耐久不足
        if (ticksUsed >= SUPER_CHARGE_TIME) {
            int remainingDurability = stack.getMaxDamage() - stack.getDamageValue();
            if (remainingDurability <= 15) {
                // 每10刻显示一次提示
                if (ticksUsed % 10 == 0 && level.isClientSide) {
                    player.displayClientMessage(Component.translatable("message.theaurorian.crystalline_sword.charging_low_durability"), true);
                    
                    // 添加红色警告粒子
                    Vec3 pos = player.getEyePosition();
                    Vec3 look = player.getLookAngle();
                    Vec3 particlePos = pos.add(look.scale(2.0));

                    for (int i = 0; i < 10; i++) {
                        double offsetX = level.getRandom().nextDouble() - 0.5;
                        double offsetY = level.getRandom().nextDouble() - 0.5;
                        double offsetZ = level.getRandom().nextDouble() - 0.5;

                        level.addParticle(
                            new DustParticleOptions(new Vector3f(1.0F, 0.2F, 0.2F), 1.0F), // 红色粒子
                            particlePos.x + offsetX, particlePos.y + offsetY, particlePos.z + offsetZ,
                            0, 0, 0);
                    }
                }
            }
        }

        // 整个蓄力过程都使用充能音效，但调整不同阶段的播放频率
        if (ticksUsed <= MAX_CHARGE_TIME) {
            // 前三秒(MAX_CHARGE_TIME=60刻)内，每40刻播放一次充能音效
            if (ticksUsed % 40 == 0) {
                player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get(), 0.7F, 0.8F);
            }
        } else {
            // 三秒后，每100刻播放一次充能音效，音量和音调更高
            if ((ticksUsed - MAX_CHARGE_TIME) % 100 == 0) {
                player.playSound(TASoundEvents.CRYSTALLINE_SWORD_CHARGING.get(), 1.0F, 1.0F);
            }
        }

        // 显示魔法阵粒子效果
        if (level.isClientSide) {
            spawnMagicCircleParticles(level, player, ticksUsed);
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

        // 停止所有之前的音效
        stopChargingSound(player);

        // 播放发射音效
        player.playSound(TASoundEvents.CRYSTALLINE_SWORD_SHOOT.get(), 1.0F, 1.0F);

        // 发射光束
        fireBeam(stack, level, player, chargeTime);
    }

    /**
     * 停止充能音效
     */
    private void stopChargingSound(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            // 只需要停止CHARGING音效，因为现在只使用这一种音效
            ResourceLocation chargingId = TASoundEvents.CRYSTALLINE_SWORD_CHARGING.getId();
            serverPlayer.connection.send(new ClientboundStopSoundPacket(chargingId, SoundSource.PLAYERS));
        }
    }

    /**
     * 发射结晶光束
     */
    private void fireBeam(ItemStack stack, Level level, Player player, int chargeTime) {
        // 判断是否达到超级蓄力时间
        boolean isSuperBeam = chargeTime >= SUPER_CHARGE_TIME;
        
        // 检查耐久度是否足够使用超级光束
        int remainingDurability = stack.getMaxDamage() - stack.getDamageValue();
        if (isSuperBeam && remainingDurability <= 15) {
            // 耐久不足，降级为普通光束
            isSuperBeam = false;
            
            // 通知玩家
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.theaurorian.crystalline_sword.low_durability"), true);
            }
        }
        
        // 消耗耐久度
        stack.consume(1, player);
        
        // 超级光束额外消耗15点耐久
        if (isSuperBeam) {
            // 使用循环单独扣除15点耐久，避免触发多次onBroken回调
            for (int i = 0; i < 15; i++) {
                if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
                    break; // 防止过度损坏
                }
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
        }

        if (level.isClientSide) {
            return;
        }

        // 计算实际蓄力时间
        int effectiveChargeTime = isSuperBeam ? SUPER_CHARGE_TIME : Math.min(chargeTime, MAX_CHARGE_TIME);

        // 创建光束信息
        BeamInfo beamInfo = new BeamInfo(
                player.getUUID(),
                player.getEyePosition(),
                player.getLookAngle(),
                calculateBeamDamage(effectiveChargeTime),
                System.currentTimeMillis(),
                BEAM_DURATION,
                isSuperBeam);

        // 注册光束
        ACTIVE_BEAMS.put(player.getUUID(), beamInfo);

        // 应用冷却
        if (!player.getAbilities().instabuild) {
            player.getCooldowns().addCooldown(TAItems.CRYSTALLINE_SWORD.get(), COOLDOWN_TICKS);
        }

        // 在服务器端触发光束效果
        if (level instanceof ServerLevel serverLevel) {
            spawnBeamParticles(serverLevel, beamInfo);
            applyBeamDamage(serverLevel, beamInfo);  // 添加持续伤害效果
        }
    }

    /**
     * 生成光束粒子效果
     */
    private static void spawnBeamParticles(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;
        boolean isSuperBeam = beamInfo.isSuperBeam;
        
        // 根据是否为超级光束选择光束宽度
        float beamWidth = isSuperBeam ? SUPER_BEAM_WIDTH : BEAM_WIDTH;

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
        // 根据是否为超级光束选择粒子数量
        int particleCount = (int)(distance * (isSuperBeam ? SUPER_PARTICLES_PER_BLOCK : PARTICLES_PER_BLOCK));

        // 获取随机源
        RandomSource random = level.getRandom();
        
        // 生成主光束粒子
        for (int i = 0; i < particleCount; i++) {
            double t = i / (double)particleCount;
            Vec3 pos = start.add(direction.scale(t * distance));

            // 添加一些随机偏移使光束看起来更自然
            double offsetX = (random.nextDouble() - 0.5) * beamWidth;
            double offsetY = (random.nextDouble() - 0.5) * beamWidth;
            double offsetZ = (random.nextDouble() - 0.5) * beamWidth;

            // 光束中心更密集
            double distFromCenter = Math.sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ);
            if (distFromCenter > beamWidth * 0.5 && random.nextDouble() > 0.7) {
                continue; // 跳过一些外围粒子，使中心更密集
            }

            // 计算粒子速度 - 从中心向外扩散
            double speedFactor = isSuperBeam ? 0.04 : 0.02;
            double vx = offsetX * speedFactor;
            double vy = offsetY * speedFactor;
            double vz = offsetZ * speedFactor;

            // 生成粒子(发送给所有玩家)
            // 使用不同的粒子类型和大小
            if (random.nextDouble() < 0.8) {
                // 主光束粒子 - 超级光束使用更亮的粒子
                level.sendParticles(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        pos.x + offsetX,
                        pos.y + offsetY,
                        pos.z + offsetZ,
                        1,
                        vx, vy, vz,
                        isSuperBeam ? 0.04 : 0.02);
            } else {
                // 添加一些闪光粒子
                level.sendParticles(
                        ParticleTypes.END_ROD,
                        pos.x + offsetX * 0.5,
                        pos.y + offsetY * 0.5,
                        pos.z + offsetZ * 0.5,
                        1,
                        vx * 0.5, vy * 0.5, vz * 0.5,
                        isSuperBeam ? 0.02 : 0.01);
            }
        }
        
        // 添加螺旋效果
        addBeamSpiralEffect(level, start, direction, distance, beamInfo);
        
        // 添加光束起点效果
        addBeamOriginEffect(level, start, direction);
        
        // 添加光束撞击效果
        if (hitResult.getType() != HitResult.Type.MISS) {
            addBeamImpactEffect(level, end, direction);
            
            // 如果是超级光束且击中了方块或实体，产生爆炸
            if (isSuperBeam) {
                level.explode(null, end.x, end.y, end.z, EXPLOSION_POWER, Level.ExplosionInteraction.BLOCK);
            }
        }
    }
    
    /**
     * 添加光束螺旋效果
     */
    private static void addBeamSpiralEffect(ServerLevel level, Vec3 start, Vec3 direction, double distance, BeamInfo beamInfo) {
        boolean isSuperBeam = beamInfo.isSuperBeam;
        RandomSource random = level.getRandom();
        
        // 螺旋半径 - 超级光束螺旋更粗
        float spiralRadius = isSuperBeam ? 3.0F : 0.8F; // 增加超级光束螺旋半径从2.5F到3.0F
        // 螺旋密度
        int spiralSegments = isSuperBeam ? 60 : 20; // 增加超级光束螺旋密度从40到60
        // 螺旋数量
        int spiralCount = isSuperBeam ? 6 : 2; // 增加超级光束螺旋数量从5到6
        
        // 计算垂直于光束方向的两个向量
        Vec3 perpendicular1;
        if (Math.abs(direction.y) < 0.9) {
            perpendicular1 = new Vec3(direction.z, 0, -direction.x).normalize();
        } else {
            perpendicular1 = new Vec3(1, 0, 0).normalize();
        }
        Vec3 perpendicular2 = direction.cross(perpendicular1).normalize();
        
        // 生成螺旋效果
        for (int spiral = 0; spiral < spiralCount; spiral++) {
            double spiralOffset = (double)spiral / spiralCount * Math.PI * 2.0;
            
            for (int i = 0; i < spiralSegments; i++) {
                double t = (double)i / spiralSegments;
                double angle = t * Math.PI * (isSuperBeam ? 15.0 : 10.0) + spiralOffset;
                
                // 计算螺旋位置
                double x = Math.cos(angle) * spiralRadius;
                double y = Math.sin(angle) * spiralRadius;
                
                // 计算在光束上的位置
                Vec3 pos = start.add(direction.scale(t * distance))
                        .add(perpendicular1.scale(x))
                        .add(perpendicular2.scale(y));
                
                // 计算粒子速度
                double vx = perpendicular1.x * x * 0.01 + perpendicular2.x * y * 0.01;
                double vy = perpendicular1.y * x * 0.01 + perpendicular2.y * y * 0.01;
                double vz = perpendicular1.z * x * 0.01 + perpendicular2.z * y * 0.01;
                
                // 根据是否为超级光束选择不同的粒子
                if (isSuperBeam) {
                    // 超级光束使用更炫酷的粒子
                    if (random.nextDouble() < 0.7) {
                        // 主螺旋粒子
                        level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                                pos.x, pos.y, pos.z, 1, vx, vy, vz, 0.02);
                        
                        // 超级光束增加额外粒子使螺旋更粗
                        if (random.nextDouble() < 0.6) {
                            // 添加周围的粒子，使螺旋看起来更粗
                            double offsetScale = 0.5 + random.nextDouble() * 0.3; // 0.5-0.8的随机偏移
                            Vec3 offsetPos = pos.add(
                                perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                                .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));
                            
                            level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                                    1, vx * 0.8, vy * 0.8, vz * 0.8, 0.015);
                        }
                    } else if (random.nextDouble() < 0.5) {
                        // 闪电粒子
                        level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                                1, vx * 2, vy * 2, vz * 2, 0.05);
                    } else {
                        // 闪光粒子
                        level.sendParticles(
                            ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                            1, 0, 0, 0, 0);
                    }
                } else {
                    // 普通光束粒子
                    if (random.nextDouble() < 0.7) {
                        level.sendParticles(
                                TAParticleTypes.MAGIC_PURPLE.get(),
                                pos.x, pos.y, pos.z,
                                1, vx, vy, vz, 0.01);
                    } else {
                        level.sendParticles(
                                ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                                1, vx, vy, vz, 0.01);
                    }
                }
            }
        }
        
        // 超级光束额外添加双螺旋效果
        if (isSuperBeam) {
            // 双螺旋参数 - 增加半径使其更粗
            float doubleHelixRadius = 3.5F; // 从3.0F增加到3.5F
            int doubleHelixSegments = 60; // 从50增加到60
            
            // 生成双螺旋
            for (int helix = 0; helix < 2; helix++) {
                double helixOffset = helix * Math.PI; // 两条螺旋相差180度
                
                for (int i = 0; i < doubleHelixSegments; i++) {
                    double t = (double)i / doubleHelixSegments;
                    double angle = t * Math.PI * 20.0 + helixOffset;
                    
                    // 计算螺旋位置
                    double x = Math.cos(angle) * doubleHelixRadius;
                    double y = Math.sin(angle) * doubleHelixRadius;
                    
                    // 计算在光束上的位置
                    Vec3 pos = start.add(direction.scale(t * distance))
                            .add(perpendicular1.scale(x))
                            .add(perpendicular2.scale(y));
                    
                    // 计算粒子速度
                    double vx = perpendicular1.x * x * 0.02;
                    double vy = perpendicular1.y * x * 0.02;
                    double vz = perpendicular1.z * x * 0.02;
                    
                    // 使用不同的粒子
                    if (helix == 0) {
                        level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                                pos.x, pos.y, pos.z, 1, vx, vy, vz, 0.03);
                        
                        // 添加额外粒子使双螺旋更粗
                        if (random.nextDouble() < 0.5) {
                            double offsetScale = 0.6;
                            Vec3 offsetPos = pos.add(
                                perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                                .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));
                            
                            level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                                    1, vx * 0.7, vy * 0.7, vz * 0.7, 0.02);
                        }
                    } else {
                        level.sendParticles(
                                TAParticleTypes.MAGIC_PURPLE.get(),
                                pos.x, pos.y, pos.z,
                                1, vx, vy, vz, 0.03);
                        
                        // 添加额外粒子使双螺旋更粗
                        if (random.nextDouble() < 0.5) {
                            double offsetScale = 0.6;
                            Vec3 offsetPos = pos.add(
                                perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                                .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));
                            
                            level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                                    1, vx * 0.7, vy * 0.7, vz * 0.7, 0.02);
                        }
                    }
                }
            }
            
            // 添加第三条更粗的中心螺旋
            float centerSpiralRadius = 1.8F;
            int centerSpiralSegments = 40;
            
            for (int i = 0; i < centerSpiralSegments; i++) {
                double t = (double)i / centerSpiralSegments;
                double angle = t * Math.PI * 12.0;
                
                // 计算螺旋位置
                double x = Math.cos(angle) * centerSpiralRadius;
                double y = Math.sin(angle) * centerSpiralRadius;
                
                // 计算在光束上的位置
                Vec3 pos = start.add(direction.scale(t * distance))
                        .add(perpendicular1.scale(x))
                        .add(perpendicular2.scale(y));
                
                // 计算粒子速度
                double vx = perpendicular1.x * x * 0.015;
                double vy = perpendicular1.y * x * 0.015;
                double vz = perpendicular1.z * x * 0.015;
                
                // 生成中心螺旋粒子
                level.sendParticles(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        pos.x, pos.y, pos.z,
                        1, vx, vy, vz, 0.025);
                
                // 添加额外粒子使中心螺旋更粗
                if (random.nextDouble() < 0.7) {
                    double offsetScale = 0.8;
                    Vec3 offsetPos = pos.add(
                        perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                        .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));
                    
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                            1, vx * 0.6, vy * 0.6, vz * 0.6, 0.02);
                }
            }
        }
    }
    
    /**
     * 添加光束撞击效果
     */
    private static void addBeamImpactEffect(ServerLevel level, Vec3 impactPos, Vec3 direction) {
        RandomSource random = level.getRandom();
        
        // 获取光束信息
        BeamInfo beamInfo = null;
        for (BeamInfo info : ACTIVE_BEAMS.values()) {
            if (info.direction.equals(direction)) {
                beamInfo = info;
                break;
            }
        }
        
        // 判断是否为超级光束
        boolean isSuperBeam = beamInfo != null && beamInfo.isSuperBeam;
        
        // 撞击效果的粒子数量
        int particleCount = isSuperBeam ? 150 : 60;
        
        // 撞击范围
        float impactRadius = isSuperBeam ? 2.5F : 1.2F;
        
        // 生成撞击粒子
        for (int i = 0; i < particleCount; i++) {
            // 计算随机方向
            double theta = random.nextDouble() * Math.PI * 2;
            double phi = random.nextDouble() * Math.PI;
            
            double x = Math.sin(phi) * Math.cos(theta);
            double y = Math.sin(phi) * Math.sin(theta);
            double z = Math.cos(phi);
            
            // 反向方向更多粒子
            Vec3 particleDir = new Vec3(x, y, z);
            double dotProduct = particleDir.dot(direction);
            if (dotProduct > 0 && random.nextDouble() > 0.3) {
                continue; // 70%的概率跳过朝向光束方向的粒子
            }
            
            // 计算粒子位置
            double distance = random.nextDouble() * impactRadius;
            Vec3 pos = impactPos.add(x * distance, y * distance, z * distance);
            
            // 计算粒子速度
            double speed = isSuperBeam ? 0.2 : 0.1;
            double vx = x * speed * (1.0 - random.nextDouble() * 0.3);
            double vy = y * speed * (1.0 - random.nextDouble() * 0.3);
            double vz = z * speed * (1.0 - random.nextDouble() * 0.3);
            
            // 根据是否为超级光束选择不同的粒子
            if (isSuperBeam) {
                // 超级光束撞击效果
                if (random.nextDouble() < 0.4) {
                    // 龙息粒子
                    level.sendParticles(
                            TAParticleTypes.MAGIC_PURPLE.get(),
                            pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.05);
                } else if (random.nextDouble() < 0.3) {
                    // 闪电
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                            1, vx * 1.5, vy * 1.5, vz * 1.5, 0.1);
                } else if (random.nextDouble() < 0.1) {
                    // 闪光
                    level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                            1, 0, 0, 0, 0);
                } else {
                    // 爆炸
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                            1, vx * 0.2, vy * 0.2, vz * 0.2, 0.02);
                }
            } else {
                // 普通光束撞击效果
                if (random.nextDouble() < 0.7) {
                    // 紫色魔法粒子
                    level.sendParticles(
                            TAParticleTypes.MAGIC_PURPLE.get(),
                            pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.02);
                } else {
                    // 末地烛粒子
                    level.sendParticles(
                            ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.02);
                }
            }
        }
        
        // 超级光束的额外效果 - 冲击波
        if (isSuperBeam) {
            // 生成圆形冲击波
            for (int i = 0; i < 2; i++) { // 生成两个冲击波，不同大小和速度
                double radius = i == 0 ? 0.5 : 1.0;
                double speed = i == 0 ? 0.15 : 0.1;
                int ringParticles = i == 0 ? 20 : 30;
                
                // 计算冲击波平面
                Vec3 up = new Vec3(0, 1, 0);
                if (Math.abs(direction.dot(up)) > 0.9) {
                    up = new Vec3(1, 0, 0);
                }
                Vec3 right = direction.cross(up).normalize();
                Vec3 planeNormal = right.cross(direction).normalize();
                
                // 生成圆形冲击波
                for (int j = 0; j < ringParticles; j++) {
                    double angle = j * Math.PI * 2 / ringParticles;
                    Vec3 ringPos = impactPos.add(
                            right.scale(Math.cos(angle) * radius).add(
                            planeNormal.scale(Math.sin(angle) * radius)));
                    
                    Vec3 ringVelocity = right.scale(Math.cos(angle) * speed).add(
                            planeNormal.scale(Math.sin(angle) * speed));
                    
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), ringPos.x, ringPos.y, ringPos.z,
                            1, ringVelocity.x, ringVelocity.y, ringVelocity.z, 0.02);
                }
            }
        }
    }
    
    /**
     * 添加光束起点效果
     */
    private static void addBeamOriginEffect(ServerLevel level, Vec3 origin, Vec3 direction) {
        RandomSource random = level.getRandom();
        
        // 起点光环效果
        int originParticles = 20;
        double originRadius = 0.8;
        
        for (int i = 0; i < originParticles; i++) {
            // 随机角度
            double angle = random.nextDouble() * Math.PI * 2;
            
            // 计算环上的点
            Vec3 up = new Vec3(0, 1, 0);
            Vec3 right = direction.cross(up).normalize();
            if (right.lengthSqr() < 0.001) {
                right = new Vec3(1, 0, 0);
            }
            Vec3 newUp = right.cross(direction).normalize();
            
            double r = originRadius * (0.8 + random.nextDouble() * 0.4);
            Vec3 ringPos = origin.add(
                right.scale(Math.cos(angle) * r).add(
                newUp.scale(Math.sin(angle) * r)));
            
            // 向外的速度
            double speed = 0.02;
            Vec3 outDir = ringPos.subtract(origin).normalize();
            
            // 生成起点光环粒子
            level.sendParticles(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    ringPos.x, ringPos.y, ringPos.z,
                    1,
                    outDir.x * speed,
                    outDir.y * speed,
                    outDir.z * speed,
                    0.01);
        }
        
        // 添加一些向前的粒子
        int forwardParticles = 15;
        for (int i = 0; i < forwardParticles; i++) {
            double dist = random.nextDouble() * 2;
            
            // 随机偏移
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = (random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;
            
            Vec3 pos = origin.add(direction.scale(dist))
                     .add(offsetX, offsetY, offsetZ);
            
            // 生成向前的粒子
            level.sendParticles(
                    ParticleTypes.END_ROD,
                    pos.x, pos.y, pos.z,
                    1,
                    direction.x * 0.1,
                    direction.y * 0.1,
                    direction.z * 0.1,
                    0.01);
        }
    }

    /**
     * 应用光束伤害
     */
    private static void applyBeamDamage(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;
        float damage = beamInfo.damage;
        boolean isSuperBeam = beamInfo.isSuperBeam;

        // 获取光束路径上的实体
        List<Entity> entities = getEntitiesInBeamPath(level, start, direction, BEAM_DISTANCE, beamInfo.owner);
        Player player = level.getPlayerByUUID(beamInfo.owner);
        
        // 对实体造成伤害
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                // 计算伤害值，根据实体到光束中心的距离进行调整
                Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
                double distanceToBeam = distanceToLine(start, start.add(direction.scale(BEAM_DISTANCE)), entityPos);
                
                // 使用适当的光束宽度
                float beamWidth = isSuperBeam ? SUPER_BEAM_WIDTH : BEAM_WIDTH;
                
                // 距离光束中心越近，伤害越高，最高为原始伤害的1.2倍
                float distanceMultiplier = (float)(1.2 - (distanceToBeam / (beamWidth * 1.5)) * 0.4);
                float adjustedDamage = damage * Math.max(0.8f, distanceMultiplier);

                livingEntity.hurt(level.damageSources().indirectMagic(
                        level.getPlayerByUUID(beamInfo.owner),
                        level.getPlayerByUUID(beamInfo.owner)
                ), adjustedDamage);

                if (livingEntity.isDeadOrDying() && player != null){
                    player.getCooldowns().removeCooldown(TAItems.CRYSTALLINE_SWORD.get());
                } else {
                    // 添加眩晕效果，距离越近眩晕时间越长
                    int stunDuration = (int)(40 * Math.max(0.8f, distanceMultiplier));
                    
                    // 超级光束增加眩晕时间
                    if (isSuperBeam) {
                        stunDuration *= 2;
                    }
                    
                    // 智能眩晕效果系统
                    applyStunEffect(livingEntity, stunDuration, isSuperBeam);
                }
                
                // 如果是超级光束，在实体位置产生小爆炸
                if (isSuperBeam && level.getGameTime() % 10 == 0) {
                    // 创建视觉爆炸效果，但不破坏方块
                    level.explode(null, entityPos.x, entityPos.y, entityPos.z, 1.0F, Level.ExplosionInteraction.NONE);
                }
            }
        }
    }

    /**
     * 智能应用眩晕效果
     * 根据目标类型和强度调整眩晕效果
     */
    private static void applyStunEffect(LivingEntity target, int baseDuration, boolean isSuperBeam) {
        // BOSS级实体眩晕时间减半
        if (target instanceof EnderDragon || target instanceof WitherBoss || 
            (target instanceof Mob mob && mob.getMaxHealth() >= 100)) {
            baseDuration = Math.max(10, baseDuration / 2);
        }
        
        // 对于玩家，时间再减少25%，并且添加额外负面效果
        if (target instanceof Player) {
            baseDuration = Math.max(5, (int)(baseDuration * 0.75f));
            
            // 超级光束对玩家施加额外的短暂负面效果
            if (isSuperBeam) {
                // 短暂的挖掘疲劳效果
                target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, baseDuration / 2, 1));
                // 短暂的缓慢效果
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, baseDuration, 1));
            }
        } 
        // 对于强大的怪物，增加额外的眩光效果
        else if (target.getMaxHealth() > 50 && isSuperBeam) {
            // 短暂的发光效果，使其更容易被看见
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, baseDuration * 2, 0));
            // 短暂的虚弱效果
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, baseDuration / 2, 0));
        }
        
        // 应用主要的眩晕效果
        target.addEffect(new MobEffectInstance(TAMobEffects.STUN, baseDuration));
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
                });
    }

    /**
     * 光束射线追踪
     */
    private static HitResult rayTraceBeam(Level level, Vec3 start, Vec3 direction, double maxDistance, UUID ownerUUID) {
        Vec3 end = start.add(direction.scale(maxDistance));

        // 排除发射者自身
        Entity owner = level.getPlayerByUUID(ownerUUID);

        // 创建ClipContext
        ClipContext clipContext = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner);

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
                Math.max(start.z, end.z) + BEAM_WIDTH);

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
        // 超级蓄力状态
        if (chargeTime >= SUPER_CHARGE_TIME) {
            return SUPER_BEAM_DAMAGE;
        }
        // 普通蓄力状态
        float chargeRatio = Math.min(1.0F, chargeTime / (float)MAX_CHARGE_TIME);
        return BEAM_MIN_DAMAGE + (BEAM_MAX_DAMAGE - BEAM_MIN_DAMAGE) * chargeRatio;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (target.isDeadOrDying() && attacker instanceof Player player) {
            player.getCooldowns().removeCooldown(TAItems.CRYSTALLINE_SWORD.get());
        }
        
        return result;
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
        final boolean isSuperBeam; // 是否为超级光束

        BeamInfo(UUID owner, Vec3 startPos, Vec3 direction, float damage, long startTime, int duration) {
            this(owner, startPos, direction, damage, startTime, duration, false);
        }
        
        BeamInfo(UUID owner, Vec3 startPos, Vec3 direction, float damage, long startTime, int duration, boolean isSuperBeam) {
            this.owner = owner;
            this.startPos = startPos;
            this.direction = direction;
            this.damage = damage;
            this.startTime = startTime;
            this.duration = duration;
            this.isSuperBeam = isSuperBeam;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > startTime + (duration * 50L);
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

    /**
     * 生成六芒星魔法阵粒子效果
     */
    private void spawnMagicCircleParticles(Level level, Player player, int ticksUsed) {
        // 计算魔法阵完成度 (0.0 - 1.0)
        float progress = Math.min(1.0F, ticksUsed / (float)MAX_CHARGE_TIME);
        
        // 判断是否为超级蓄力状态
        boolean isSuperCharge = ticksUsed >= SUPER_CHARGE_TIME;
        float superProgress = 0;
        if (isSuperCharge) {
            // 计算超级魔法阵完成度
            superProgress = Math.min(1.0F, (ticksUsed - SUPER_CHARGE_TIME) / (float)(SUPER_CHARGE_TIME - MAX_CHARGE_TIME));
        }
        
        // 确定魔法阵位置 (在玩家视线前方3.5格，更远以避免遮挡)
        Vec3 playerLook = player.getLookAngle();
        Vec3 circleCenter = player.getEyePosition().add(playerLook.scale(3.5));
        
        // 魔法阵大小随充能增长 (更大的基础尺寸)
        float size = 2.5F + progress;
        
        // 超级蓄力状态下，魔法阵更大
        if (isSuperCharge) {
            size = 3.5F + superProgress * 2.0F;
        }
        
        // 魔法阵旋转角度 (随时间变化)
        double baseRotation = (level.getGameTime() % 360) * 2;
        double rotation = baseRotation;
        double innerRotation = baseRotation * -1.5;
        double outerRotation = baseRotation * 0.8;
        
        // 超级蓄力状态下，旋转更快
        if (isSuperCharge) {
            rotation *= 1.5;
            innerRotation *= 2.0;
            outerRotation *= 1.8;
        }
        
        // 脉冲效果 (随时间波动的大小变化)
        float pulseEffect = (float)Math.sin(level.getGameTime() * 0.1) * 0.1F + 1.0F;
        size *= pulseEffect;
        
        // 计算魔法阵的朝向 (与玩家视线方向垂直)
        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = playerLook.cross(up).normalize();
        if (right.lengthSqr() < 0.001) {
            // 如果玩家正好垂直向上或向下看，使用另一个方向作为参考
            right = new Vec3(1, 0, 0);
        }
        Vec3 planeNormal = right.cross(playerLook).normalize();
        
        // 生成外部装饰环 - 超级蓄力时环更大
        float outerRingSize = isSuperCharge ? size * 1.3F : size * 1.2F;
        generateOuterRing(level, circleCenter, outerRingSize, outerRotation, progress, playerLook, right, planeNormal);
        
        // 生成外部光环效果 - 超级蓄力时光环更大
        float auraSize = isSuperCharge ? size * 1.4F : size * 1.3F;
        generateAura(level, circleCenter, auraSize, rotation * 0.5, progress, playerLook, right, planeNormal);
        
        // 生成六芒星外圈
        generateHexagram(level, circleCenter, size, rotation, progress, playerLook, right, planeNormal);
        
        // 生成内部魔法符文圈 (旋转方向相反)
        generateRunicCircle(level, circleCenter, size * 0.7F, innerRotation, progress, playerLook, right, planeNormal);
        
        // 生成能量射线 (从中心向外，减少数量)
        if (level.getRandom().nextInt(3) == 0) { // 只有1/3的几率生成射线
            generateEnergyRays(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }
        
        // 生成魔法符文
        generateMagicRunes(level, circleCenter, size * 0.9F, rotation, progress, playerLook, right, planeNormal);
        
        // 添加附魔粒子效果 (分布在魔法阵和玩家周围)
        generateEnchantParticles(level, player, circleCenter, size, progress, playerLook, right, planeNormal);
        
        // 在高充能阶段添加额外的粒子效果
        if (progress > 0.8F) {
            generateHighChargeEffects(level, circleCenter, size, progress);
        }
        
        // 生成能量波纹
        if (progress > 0.5F && level.getGameTime() % 20 == 0) {
            generateEnergyRipple(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }
        
        // 生成闪电效果
        if (progress > 0.6F && level.getRandom().nextInt(5) == 0) {
            generateLightningEffects(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }
        
        // 超级蓄力状态下的特殊效果
        if (isSuperCharge) {
            // 生成星爆效果
            generateStarburstEffects(level, circleCenter, size, superProgress, playerLook, right, planeNormal);
            
            // 生成符文轨迹
            generateRunicTrails(level, circleCenter, size, rotation * 2, superProgress, playerLook, right, planeNormal);
            
            // 生成能量漩涡
            generateEnergyVortex(level, circleCenter, size * 1.5F, superProgress, playerLook, right, planeNormal);
            
            // 增加更多闪电效果
            if (level.getRandom().nextInt(3) == 0) {
                generateLightningEffects(level, circleCenter, size * 1.2F, superProgress, playerLook, right, planeNormal);
            }
            
            // 在玩家周围添加光环效果
            if (level.getGameTime() % 10 == 0) {
                for (int i = 0; i < 20; i++) {
                    double angle = i * Math.PI * 2 / 20;
                    double radius = 1.5 + Math.sin(level.getGameTime() * 0.05) * 0.5;
                    Vec3 offset = right.scale(Math.cos(angle) * radius).add(planeNormal.scale(Math.sin(angle) * radius));
                    Vec3 pos = player.position().add(0, 1.0, 0).add(offset);
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z, offset.x * 0.02, 0.05, offset.z * 0.02);
                }
            }
        }
    }
    
    /**
     * 生成外部装饰环
     */
    private void generateOuterRing(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.5F) return;
        
        // 计算外环上的点
        int pointCount = 72; // 增加点数，使环更密集
        boolean isSuperCharge = progress >= 1.0F; // 判断是否为超级蓄力状态
        
        // 超级蓄力时环更粗
        int skipFactor = isSuperCharge ? 2 : 3; // 超级蓄力时每2个点生成一个，否则每3个点生成一个
        
        for (int i = 0; i < pointCount; i++) {
            // 只绘制部分点，形成断开的环
            if (i % skipFactor != 0) continue;
            
            double angle = Math.toRadians(360.0 / pointCount * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            // 在垂直于玩家视线的平面上计算点的位置
            Vec3 point = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 使用紫色魔法粒子，添加一些随机速度使其更动态
            double speedFactor = 0.002;
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    point.x, point.y, point.z,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor);
            
            // 超级蓄力状态下，添加额外的粒子使环更粗
            if (isSuperCharge) {
                // 向内侧添加粒子
                double innerFactor = 0.9;
                Vec3 innerPoint = center.add(
                    right.scale(sin * size * innerFactor).add(
                    up.scale(cos * size * innerFactor)));
                
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        innerPoint.x, innerPoint.y, innerPoint.z,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor);
                
                // 向外侧添加粒子
                double outerFactor = 1.1;
                Vec3 outerPoint = center.add(
                    right.scale(sin * size * outerFactor).add(
                    up.scale(cos * size * outerFactor)));
                
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        outerPoint.x, outerPoint.y, outerPoint.z,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor);
            }
        }
    }
    
    /**
     * 生成外部光环效果
     */
    private void generateAura(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.6F) return;
        
        // 使用紫色魔法粒子创建光环
        RandomSource random = level.getRandom();
        boolean isSuperCharge = progress >= 1.0F; // 判断是否为超级蓄力状态
        
        // 超级蓄力时粒子更多
        int particleCount = isSuperCharge ? (int)(24 * progress) : (int)(12 * progress);
        
        for (int i = 0; i < particleCount; i++) {
            // 随机角度
            double angle = random.nextDouble() * Math.PI * 2;
            // 随机距离 (在外围)
            double dist = (0.95 + random.nextDouble() * 0.1) * size;
            
            // 超级蓄力状态下，增加粒子散布范围
            if (isSuperCharge) {
                dist = (0.9 + random.nextDouble() * 0.2) * size;
            }
            
            // 计算位置
            Vec3 offset = right.scale(Math.sin(angle) * dist)
                         .add(up.scale(Math.cos(angle) * dist));
            
            Vec3 pos = center.add(offset);
            
            // 添加紫色魔法粒子
            double speedFactor = isSuperCharge ? 0.003 : 0.002;
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    pos.x, pos.y, pos.z,
                    (random.nextDouble() - 0.5) * speedFactor,
                    (random.nextDouble() - 0.5) * speedFactor,
                    (random.nextDouble() - 0.5) * speedFactor);
        }
    }
    
    /**
     * 生成魔法符文
     */
    private void generateMagicRunes(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.4F) return;
        
        RandomSource random = level.getRandom();
        
        // 符文数量随进度增加
        int runeCount = (int)(2 * progress);
        
        // 符文位置随时间变化
        long gameTime = level.getGameTime();
        
        for (int i = 0; i < runeCount; i++) {
            // 符文在魔法阵上的随机位置
            double angle = (i / (double)runeCount * Math.PI * 2) + (gameTime * 0.01);
            double dist = (0.5 + random.nextDouble() * 0.4) * size;
            
            Vec3 runePos = center.add(
                right.scale(Math.sin(angle) * dist).add(
                up.scale(Math.cos(angle) * dist)));
            
            // 符文旋转角度
            double runeRotation = rotation + i * 30;
            
            // 生成符文形状 (简单的几何图形)
            int runeType = i % 3; // 三种不同的符文类型
            
            switch (runeType) {
                case 0: // 三角形符文
                    generateTriangleRune(level, runePos, 0.2F, runeRotation, forward, right, up);
                    break;
                case 1: // 方形符文
                    generateSquareRune(level, runePos, 0.15F, runeRotation, forward, right, up);
                    break;
                case 2: // 圆形符文
                    generateCircleRune(level, runePos, 0.18F, runeRotation, forward, right, up);
                    break;
            }
        }
    }
    
    /**
     * 生成三角形符文
     */
    private void generateTriangleRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        // 三角形的三个顶点
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(120 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            Vec3 point1 = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 下一个点
            double nextAngle = Math.toRadians(120 * ((i + 1) % 3) + rotation);
            double nextSin = Math.sin(nextAngle);
            double nextCos = Math.cos(nextAngle);
            
            Vec3 point2 = center.add(
                right.scale(nextSin * size).add(
                up.scale(nextCos * size)));
            
            // 在两点之间生成粒子线
            int points = 5;
            for (int j = 0; j <= points; j++) {
                double t = j / (double)points;
                Vec3 linePos = point1.add(point2.subtract(point1).scale(t));
                
                // 使用紫色魔法粒子
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x, linePos.y, linePos.z,
                        0, 0.005, 0);
            }
        }
    }
    
    /**
     * 生成方形符文
     */
    private void generateSquareRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        // 方形的四个顶点
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(90 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            Vec3 point1 = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 下一个点
            double nextAngle = Math.toRadians(90 * ((i + 1) % 4) + rotation);
            double nextSin = Math.sin(nextAngle);
            double nextCos = Math.cos(nextAngle);
            
            Vec3 point2 = center.add(
                right.scale(nextSin * size).add(
                up.scale(nextCos * size)));
            
            // 在两点之间生成粒子线
            int points = 4;
            for (int j = 0; j <= points; j++) {
                double t = j / (double)points;
                Vec3 linePos = point1.add(point2.subtract(point1).scale(t));
                
                // 使用紫色魔法粒子
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x, linePos.y, linePos.z,
                        0, 0.005, 0);
            }
        }
    }
    
    /**
     * 生成圆形符文
     */
    private void generateCircleRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        // 圆形的点
        int points = 8;
        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(360.0 / points * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            Vec3 point = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 使用紫色魔法粒子
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    point.x, point.y, point.z,
                    0, 0.005, 0);
        }
    }
    
    /**
     * 生成能量波纹
     */
    private void generateEnergyRipple(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 波纹从中心向外扩散
        RandomSource random = level.getRandom();
        
        // 波纹的初始大小和最大大小
        float minSize = 0.2F * size;
        float maxSize = 1.2F * size;
        
        // 波纹扩散的步数
        int steps = 8;
        
        for (int step = 0; step < steps; step++) {
            // 波纹当前大小
            float rippleSize = minSize + (maxSize - minSize) * (step / (float)steps);
            
            // 波纹上的点数量
            int pointCount = (int)(12 * (1 + step / (float)steps));
            
            // 波纹透明度 (随距离减小)
            float alpha = 0.8F * (1 - step / (float)steps) * progress;
            
            for (int i = 0; i < pointCount; i++) {
                double angle = Math.toRadians(360.0 / pointCount * i);
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);
                
                Vec3 point = center.add(
                    right.scale(sin * rippleSize).add(
                    up.scale(cos * rippleSize)));
                
                // 使用紫色魔法粒子
                double speedFactor = 0.001 * (1 - step / (float)steps); // 速度随距离减小
                level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), point.x, point.y, point.z,
                        sin * speedFactor, 0, cos * speedFactor);
            }
        }
    }

    /**
     * 生成附魔粒子效果
     */
    private void generateEnchantParticles(Level level, Player player, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.3F) return;
        
        RandomSource random = level.getRandom();
        
        // 附魔粒子数量随进度增加 (减少总数)
        int particleCount = (int)(3 * progress);
        
        // 在魔法阵周围生成附魔粒子 (减少数量)
        for (int i = 0; i < particleCount; i++) {
            // 只有50%的几率在魔法阵处生成粒子
            if (random.nextBoolean()) {
                // 随机位置在魔法阵外围
                double angle = random.nextDouble() * Math.PI * 2;
                double dist = (0.8 + random.nextDouble() * 0.4) * size; // 主要在外围
                
                Vec3 offset = right.scale(Math.sin(angle) * dist)
                             .add(up.scale(Math.cos(angle) * dist));
                
                Vec3 pos = center.add(offset);
                
                // 向上漂浮的附魔粒子
                level.addParticle(ParticleTypes.ENCHANT, pos.x, pos.y, pos.z,
                        0, 0.1 + random.nextDouble() * 0.2, 0);
            } 
            // 在玩家周围生成附魔粒子
            else {
                // 玩家周围的随机位置
                double offsetX = (random.nextDouble() - 0.5);
                double offsetY = random.nextDouble() * 2.0; // 主要在上方
                double offsetZ = (random.nextDouble() - 0.5);
                
                Vec3 playerPos = player.position();
                
                level.addParticle(
                        ParticleTypes.ENCHANT,
                        playerPos.x + offsetX,
                        playerPos.y + offsetY,
                        playerPos.z + offsetZ,
                        0, 0.05 + random.nextDouble() * 0.05, 0);
            }
        }
    }
    
    /**
     * 生成高充能阶段特效
     */
    private void generateHighChargeEffects(Level level, Vec3 center, float size, float progress) {
        RandomSource random = level.getRandom();
        
        // 添加末影粒子 (增加出现概率)
        if (random.nextInt(3) == 0) {
            double offsetX = (random.nextDouble() - 0.5) * size * 0.8;
            double offsetY = (random.nextDouble() - 0.5) * size * 0.8;
            double offsetZ = (random.nextDouble() - 0.5) * size * 0.8;
            
            level.addParticle(
                    ParticleTypes.PORTAL,
                    center.x + offsetX,
                    center.y + offsetY,
                    center.z + offsetZ,
                    (random.nextDouble() - 0.5) * 2,
                    (random.nextDouble() - 0.5) * 2,
                    (random.nextDouble() - 0.5) * 2);
        }
    }

    /**
     * 生成六芒星图案
     */
    private void generateHexagram(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.2F) return;
        
        // 判断是否为超级蓄力状态
        boolean isSuperCharge = progress >= 1.0F;
        
        // 超级蓄力时线条更粗更亮
        int skipFactor = isSuperCharge ? 1 : 2; // 超级蓄力时不跳过点，使线条更密集
        
        // 计算六芒星的两个三角形顶点
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            // 在垂直于玩家视线的平面上计算点的位置
            Vec3 point = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 连接到对面的点形成六芒星
            double oppositeAngle = Math.toRadians(60 * ((i + 3) % 6) + rotation);
            double oppositeSin = Math.sin(oppositeAngle);
            double oppositeCos = Math.cos(oppositeAngle);
            
            Vec3 oppositePoint = center.add(
                right.scale(oppositeSin * size).add(
                up.scale(oppositeCos * size)));
            
            // 在两点之间生成粒子线
            int points = isSuperCharge ? 15 : 10; // 超级蓄力时点数更多
            for (int j = 0; j <= points; j++) {
                // 跳过一些点，使线条更稀疏，但超级蓄力时线条更密集
                if (j % skipFactor == 0 && j > 0 && j < points) continue;
                
                double t = j / (double)points;
                Vec3 linePos = point.add(oppositePoint.subtract(point).scale(t));
                
                // 添加一些随机偏移使线条看起来更魔法
                double offsetFactor = isSuperCharge ? 0.12 : 0.08; // 超级蓄力时偏移更大，线条更粗
                Vec3 offset = right.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor)
                             .add(up.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor));
                
                // 使用紫色魔法粒子，并添加一些微小的速度使其闪烁
                level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x + offset.x, linePos.y + offset.y, linePos.z + offset.z,
                        0, 0.005 + level.getRandom().nextDouble() * (isSuperCharge ? 0.02 : 0.01), 0);
                
                // 超级蓄力时添加额外的粒子使线条更粗
                if (isSuperCharge && level.getRandom().nextBoolean()) {
                    // 添加额外偏移的粒子
                    Vec3 extraOffset = right.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor * 1.5)
                                     .add(up.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor * 1.5));
                    
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                            linePos.x + extraOffset.x, linePos.y + extraOffset.y, linePos.z + extraOffset.z,
                            0, 0.005 + level.getRandom().nextDouble() * 0.02, 0);
                }
            }
        }
    }

    /**
     * 生成符文圈
     */
    private void generateRunicCircle(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够时才显示
        if (progress < 0.4F) return;
        
        // 计算符文圈上的点 (减少符文数量，使其更稀疏)
        int runeCount = 10;
        for (int i = 0; i < runeCount; i++) {
            double angle = Math.toRadians(360.0 / runeCount * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            
            // 在垂直于玩家视线的平面上计算点的位置
            Vec3 point = center.add(
                right.scale(sin * size).add(
                up.scale(cos * size)));
            
            // 符文效果 (更大更明显的符文)
            double runeHeight = 0.2;
            int runePoints = 1;
            for (int j = 0; j < runePoints; j++) {
                double t = j / (double)(Math.max(1, runePoints - 1)) - 0.5;
                Vec3 runePos = point.add(forward.scale(t * runeHeight));
                
                // 使用紫色魔法粒子，添加一些速度使其更动态
                double speedFactor = 0.002;
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        runePos.x, runePos.y, runePos.z,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor,
                        (level.getRandom().nextDouble() - 0.5) * speedFactor);
            }
        }
    }
    
    /**
     * 生成能量射线
     */
    private void generateEnergyRays(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只有当进度足够高时才显示
        if (progress < 0.7F) return;
        
        // 射线数量随进度增加 (减少数量)
        int rayCount = (int)(1 + progress * 2);
        
        for (int i = 0; i < rayCount; i++) {
            // 随机方向，但主要在魔法阵平面上
            double angle = level.getRandom().nextDouble() * Math.PI * 2;
            
            // 计算射线方向 (主要在魔法阵平面上，减少向玩家方向的射线)
            Vec3 rayDir = right.scale(Math.sin(angle))
                         .add(up.scale(Math.cos(angle)))
                         .add(forward.scale((level.getRandom().nextDouble() - 0.5) * 0.1));
            
            rayDir = rayDir.normalize();
            
            // 射线长度
            double rayLength = size * (0.4 + level.getRandom().nextDouble() * 0.4);
            
            // 生成射线粒子 (减少粒子数量)
            int points = 2;
            for (int j = 0; j < points; j++) {
                double t = j / (double)points;
                Vec3 rayPos = center.add(rayDir.scale(t * rayLength));
                
                // 使用紫色魔法粒子，速度向外
                double speedFactor = 0.01;
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        rayPos.x, rayPos.y, rayPos.z,
                        rayDir.x * speedFactor,
                        rayDir.y * speedFactor,
                        rayDir.z * speedFactor);
            }
        }
    }
    
    /**
     * 新增：生成闪电效果
     */
    private void generateLightningEffects(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        
        // 闪电起点数量
        int lightningCount = 1 + random.nextInt(2);
        
        for (int i = 0; i < lightningCount; i++) {
            // 在魔法阵外围随机选择起点
            double startAngle = random.nextDouble() * Math.PI * 2;
            double startDist = size * 0.8;
            
            Vec3 startPos = center.add(
                right.scale(Math.sin(startAngle) * startDist).add(
                up.scale(Math.cos(startAngle) * startDist)));
            
            // 随机选择终点 (可能是另一边的外围点或者其他位置)
            double endAngle = startAngle + Math.PI + (random.nextDouble() - 0.5) * Math.PI;
            double endDist = size * (0.7 + random.nextDouble() * 0.3);
            
            Vec3 endPos = center.add(
                right.scale(Math.sin(endAngle) * endDist).add(
                up.scale(Math.cos(endAngle) * endDist)));
            
            // 生成闪电路径 (之字形)
            int segments = 4 + random.nextInt(3);
            Vec3 lastPos = startPos;
            
            for (int j = 1; j <= segments; j++) {
                // 计算当前段的目标位置
                double t = j / (double)segments;
                Vec3 targetPos = startPos.add(endPos.subtract(startPos).scale(t));
                
                // 添加随机偏移 (但不要偏移到中心)
                double offsetMagnitude = size * 0.15 * (1 - t); // 越靠近终点偏移越小
                Vec3 offset = right.scale((random.nextDouble() - 0.5) * offsetMagnitude)
                             .add(up.scale((random.nextDouble() - 0.5) * offsetMagnitude));
                
                // 确保偏移不会导致闪电指向中心
                Vec3 toCenter = center.subtract(targetPos.add(offset));
                double distToCenter = toCenter.length();
                if (distToCenter < size * 0.4) {
                    // 如果太靠近中心，调整偏移方向
                    offset = offset.scale(-1);
                }
                
                targetPos = targetPos.add(offset);
                
                // 在两点之间生成闪电粒子
                int points = 5;
                for (int k = 0; k < points; k++) {
                    double s = k / (double)points;
                    Vec3 pos = lastPos.add(targetPos.subtract(lastPos).scale(s));
                    
                    // 使用亮紫色粒子
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z, 0, 0, 0);
                }
                
                lastPos = targetPos;
            }
        }
    }
    
    /**
     * 新增：生成星辰爆发效果
     */
    private void generateStarburstEffects(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        // 只在特定充能阶段生成
        if (progress < 0.3F || level.getRandom().nextInt(4) != 0) return;

        RandomSource random = level.getRandom();

        // 星辰数量随充能增加
        int starCount = 1 + (int)(progress * 2);

        for (int i = 0; i < starCount; i++) {
            // 随机位置 (避开中心区域)
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = size * (0.5 + random.nextDouble() * 0.5); // 主要在中间到外围区域

            Vec3 starPos = center.add(
                right.scale(Math.sin(angle) * dist).add(
                up.scale(Math.cos(angle) * dist)));
            
            // 星辰爆发效果
            int rays = 4 + random.nextInt(4);
            float rayLength = 0.2F + random.nextFloat() * 0.3F;
            
            for (int j = 0; j < rays; j++) {
                double rayAngle = j * (Math.PI * 2 / rays);
                Vec3 rayDir = right.scale(Math.sin(rayAngle)).add(up.scale(Math.cos(rayAngle)));
                
                // 射线终点
                Vec3 rayEnd = starPos.add(rayDir.scale(rayLength));
                
                // 生成射线粒子
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        starPos.x, starPos.y, starPos.z,
                        rayDir.x * 0.02,
                        rayDir.y * 0.02,
                        rayDir.z * 0.02);
            }
        }
    }
    
    /**
     * 新增：生成符文轨迹
     */
    private void generateRunicTrails(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        
        // 符文轨迹数量
        int trailCount = 1 + (int)(progress * 2);
        
        // 只有一定几率生成
        if (random.nextInt(3) != 0) return;
        
        for (int i = 0; i < trailCount; i++) {
            // 轨迹起点 (在魔法阵上随机位置)
            double startAngle = random.nextDouble() * Math.PI * 2;
            double startDist = size * (0.5 + random.nextDouble() * 0.5);
            
            Vec3 startPos = center.add(
                right.scale(Math.sin(startAngle) * startDist).add(
                up.scale(Math.cos(startAngle) * startDist)));
            
            // 轨迹方向 (沿着魔法阵平面)
            double moveAngle = startAngle + Math.PI/2 + (random.nextDouble() - 0.5) * Math.PI/4;
            Vec3 moveDir = right.scale(Math.sin(moveAngle)).add(up.scale(Math.cos(moveAngle)));
            
            // 轨迹长度
            double trailLength = size * (0.3 + random.nextDouble() * 0.3);
            
            // 生成轨迹粒子
            int points = 5 + random.nextInt(5);
            for (int j = 0; j < points; j++) {
                double t = j / (double)points;
                Vec3 pos = startPos.add(moveDir.scale(t * trailLength));
                
                // 轨迹颜色随距离变化
                float hue = (float)(t * 0.2 + random.nextDouble() * 0.1);
                Vector3f color = new Vector3f(0.7F, 0.3F + hue, 0.9F); // 从紫色到略带蓝色
                
                // 使用尘埃粒子实现颜色变化
                level.addParticle(new DustParticleOptions(color, 1.0F), pos.x, pos.y, pos.z, 0, 0, 0);
            }
        }
    }
    
    /**
     * 新增：生成能量漩涡
     */
    private void generateEnergyVortex(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        
        // 只有一定几率生成
        if (random.nextInt(4) != 0) return;
        
        // 漩涡中心点 (在魔法阵外围)
        double vortexAngle = random.nextDouble() * Math.PI * 2;
        double vortexDist = size * 0.8;
        
        Vec3 vortexCenter = center.add(
            right.scale(Math.sin(vortexAngle) * vortexDist).add(
            up.scale(Math.cos(vortexAngle) * vortexDist))
        );
        
        // 漩涡大小
        float vortexSize = size * 0.25F;
        
        // 漩涡旋转方向
        boolean clockwise = random.nextBoolean();
        
        // 漩涡粒子数量
        int particleCount = 10 + (int)(progress * 10);
        
        // 生成漩涡粒子
        for (int i = 0; i < particleCount; i++) {
            // 螺旋参数
            double t = i / (double)particleCount;
            double spiralRadius = vortexSize * t;
            double spiralAngle = t * Math.PI * 6 + level.getGameTime() * 0.1 * (clockwise ? 1 : -1);
            
            // 计算粒子位置
            Vec3 offset = right.scale(Math.sin(spiralAngle) * spiralRadius)
                         .add(up.scale(Math.cos(spiralAngle) * spiralRadius));
            
            Vec3 pos = vortexCenter.add(offset);
            
            // 粒子速度 (向中心旋转)
            double speedFactor = 0.02 * (1 - t);
            Vec3 velocity = right.scale(Math.cos(spiralAngle) * speedFactor * (clockwise ? -1 : 1))
                           .add(up.scale(-Math.sin(spiralAngle) * speedFactor * (clockwise ? -1 : 1)));
            
            // 使用紫色魔法粒子
            level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
        }
    }
}