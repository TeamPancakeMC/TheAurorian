package cn.teampancake.theaurorian.common.items.tool.crystalline;

import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Vector3f;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class CrystallineSwordBeamUtils {

    private static final int DAMAGE_START_TIME = 120;
    private static final float BEAM_MIN_DAMAGE = 5.0F;
    private static final float BEAM_MAX_DAMAGE = 20.0F;
    private static final int MAX_CHARGE_TIME = 60;
    private static final int BEAM_DURATION = 40;
    private static final int BEAM_DISTANCE = 70;
    private static final int PARTICLES_PER_BLOCK = 25;
    private static final float BEAM_WIDTH = 1.2F;
    private static final int SUPER_CHARGE_TIME = 120;
    private static final float SUPER_BEAM_DAMAGE = 50.0F;
    private static final float SUPER_BEAM_WIDTH = 2.5F;
    private static final int SUPER_PARTICLES_PER_BLOCK = 40;
    private static final ConcurrentHashMap<UUID, BeamInfo> ACTIVE_BEAMS = new ConcurrentHashMap<>();
    
    public static void spawnBeamParticles(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;
        boolean isSuperBeam = beamInfo.isSuperBeam;
        float beamWidth = isSuperBeam ? SUPER_BEAM_WIDTH : BEAM_WIDTH;
        HitResult hitResult = rayTraceBeam(level, start, direction, BEAM_DISTANCE, beamInfo.owner);
        Vec3 end;
        if (hitResult.getType() == HitResult.Type.MISS) {
            end = start.add(direction.scale(BEAM_DISTANCE));
        } else {
            end = hitResult.getLocation();
        }
        
        double distance = start.distanceTo(end);
        int particleCount = (int)(distance * (isSuperBeam ? SUPER_PARTICLES_PER_BLOCK : PARTICLES_PER_BLOCK));
        RandomSource random = level.getRandom();
        for (int i = 0; i < particleCount; i++) {
            double t = i / (double)particleCount;
            Vec3 pos = start.add(direction.scale(t * distance));
            double offsetX = (random.nextDouble() - 0.5) * beamWidth;
            double offsetY = (random.nextDouble() - 0.5) * beamWidth;
            double offsetZ = (random.nextDouble() - 0.5) * beamWidth;
            double distFromCenter = Math.sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ);
            if (distFromCenter > beamWidth * 0.5 && random.nextDouble() > 0.7) continue;
            double speedFactor = isSuperBeam ? 0.04 : 0.02;
            double vx = offsetX * speedFactor;
            double vy = offsetY * speedFactor;
            double vz = offsetZ * speedFactor;
            if (random.nextDouble() < 0.8) {
                level.sendParticles(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        pos.x + offsetX,
                        pos.y + offsetY,
                        pos.z + offsetZ,
                        1,
                        vx, vy, vz,
                        isSuperBeam ? 0.04 : 0.02);
            } else {
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
        
        addBeamSpiralEffect(level, start, direction, distance, beamInfo);
        addBeamOriginEffect(level, start, direction);
        if (hitResult.getType() != HitResult.Type.MISS) {
            addBeamImpactEffect(level, end, direction);
            if (isSuperBeam) {
                level.explode(null, end.x, end.y, end.z, 3.0F, Level.ExplosionInteraction.BLOCK);
            }
        }
    }

    public static void addBeamSpiralEffect(ServerLevel level, Vec3 start, Vec3 direction, double distance, BeamInfo beamInfo) {
        boolean isSuperBeam = beamInfo.isSuperBeam;
        RandomSource random = level.getRandom();
        float spiralRadius = isSuperBeam ? 3.0F : 0.8F;
        int spiralSegments = isSuperBeam ? 60 : 20;
        int spiralCount = isSuperBeam ? 6 : 2;
        Vec3 perpendicular1;
        if (Math.abs(direction.y) < 0.9) {
            perpendicular1 = new Vec3(direction.z, 0, -direction.x).normalize();
        } else {
            perpendicular1 = new Vec3(1, 0, 0).normalize();
        }
        
        Vec3 perpendicular2 = direction.cross(perpendicular1).normalize();
        for (int spiral = 0; spiral < spiralCount; spiral++) {
            double spiralOffset = (double)spiral / spiralCount * Math.PI * 2.0;
            for (int i = 0; i < spiralSegments; i++) {
                double t = (double)i / spiralSegments;
                double angle = t * Math.PI * (isSuperBeam ? 15.0 : 10.0) + spiralOffset;
                double x = Math.cos(angle) * spiralRadius;
                double y = Math.sin(angle) * spiralRadius;
                Vec3 pos = start.add(direction.scale(t * distance))
                        .add(perpendicular1.scale(x))
                        .add(perpendicular2.scale(y));
                double vx = perpendicular1.x * x * 0.01 + perpendicular2.x * y * 0.01;
                double vy = perpendicular1.y * x * 0.01 + perpendicular2.y * y * 0.01;
                double vz = perpendicular1.z * x * 0.01 + perpendicular2.z * y * 0.01;
                if (isSuperBeam) {
                    if (random.nextDouble() < 0.7) {
                        level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                                pos.x, pos.y, pos.z, 1, vx, vy, vz, 0.02);
                        if (random.nextDouble() < 0.6) {
                            double offsetScale = 0.5 + random.nextDouble() * 0.3; 
                            Vec3 offsetPos = pos.add(
                                    perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                                            .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));
                            level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                                    1, vx * 0.8, vy * 0.8, vz * 0.8, 0.015);
                        }
                    } else if (random.nextDouble() < 0.5) {
                        level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                                1, vx * 2, vy * 2, vz * 2, 0.05);
                    } else {
                        level.sendParticles(
                                ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                                1, 0, 0, 0, 0);
                    }
                } else {
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
        
        if (isSuperBeam) {
            float doubleHelixRadius = 3.5F; 
            int doubleHelixSegments = 60;
            for (int helix = 0; helix < 2; helix++) {
                double helixOffset = helix * Math.PI;
                for (int i = 0; i < doubleHelixSegments; i++) {
                    double t = (double)i / doubleHelixSegments;
                    double angle = t * Math.PI * 20.0 + helixOffset;
                    double x = Math.cos(angle) * doubleHelixRadius;
                    double y = Math.sin(angle) * doubleHelixRadius;
                    Vec3 pos = start.add(direction.scale(t * distance))
                            .add(perpendicular1.scale(x))
                            .add(perpendicular2.scale(y));
                    double vx = perpendicular1.x * x * 0.02;
                    double vy = perpendicular1.y * x * 0.02;
                    double vz = perpendicular1.z * x * 0.02;
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                            pos.x, pos.y, pos.z, 1, vx, vy, vz, 0.03);
                    if (helix == 0) {
                        if (random.nextDouble() < 0.5) {
                            double offsetScale = 0.6;
                            Vec3 offsetPos = pos.add(
                                    perpendicular1.scale((random.nextDouble() - 0.5) * offsetScale)
                                            .add(perpendicular2.scale((random.nextDouble() - 0.5) * offsetScale)));

                            level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), offsetPos.x, offsetPos.y, offsetPos.z,
                                    1, vx * 0.7, vy * 0.7, vz * 0.7, 0.02);
                        }
                    } else {
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
            
            float centerSpiralRadius = 1.8F;
            int centerSpiralSegments = 40;
            for (int i = 0; i < centerSpiralSegments; i++) {
                double t = (double)i / centerSpiralSegments;
                double angle = t * Math.PI * 12.0;
                double x = Math.cos(angle) * centerSpiralRadius;
                double y = Math.sin(angle) * centerSpiralRadius;
                Vec3 pos = start.add(direction.scale(t * distance))
                        .add(perpendicular1.scale(x))
                        .add(perpendicular2.scale(y));
                double vx = perpendicular1.x * x * 0.015;
                double vy = perpendicular1.y * x * 0.015;
                double vz = perpendicular1.z * x * 0.015;
                level.sendParticles(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        pos.x, pos.y, pos.z,
                        1, vx, vy, vz, 0.025);
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

    public static void addBeamImpactEffect(ServerLevel level, Vec3 impactPos, Vec3 direction) {
        RandomSource random = level.getRandom();
        BeamInfo beamInfo = null;
        for (BeamInfo info : ACTIVE_BEAMS.values()) {
            if (info.direction.equals(direction)) {
                beamInfo = info;
                break;
            }
        }

        
        boolean isSuperBeam = beamInfo != null && beamInfo.isSuperBeam;
        int particleCount = isSuperBeam ? 150 : 60;
        float impactRadius = isSuperBeam ? 2.5F : 1.2F;
        for (int i = 0; i < particleCount; i++) {
            double theta = random.nextDouble() * Math.PI * 2;
            double phi = random.nextDouble() * Math.PI;
            double x = Math.sin(phi) * Math.cos(theta);
            double y = Math.sin(phi) * Math.sin(theta);
            double z = Math.cos(phi);
            Vec3 particleDir = new Vec3(x, y, z);
            double dotProduct = particleDir.dot(direction);
            if (dotProduct > 0 && random.nextDouble() > 0.3) continue;
            double distance = random.nextDouble() * impactRadius;
            Vec3 pos = impactPos.add(x * distance, y * distance, z * distance);
            double speed = isSuperBeam ? 0.2 : 0.1;
            double vx = x * speed * (1.0 - random.nextDouble() * 0.3);
            double vy = y * speed * (1.0 - random.nextDouble() * 0.3);
            double vz = z * speed * (1.0 - random.nextDouble() * 0.3);
            if (isSuperBeam) {
                if (random.nextDouble() < 0.4) {
                    level.sendParticles(
                            TAParticleTypes.MAGIC_PURPLE.get(),
                            pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.05);
                } else if (random.nextDouble() < 0.3) {
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                            1, vx * 1.5, vy * 1.5, vz * 1.5, 0.1);
                } else if (random.nextDouble() < 0.1) {
                    level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                            1, 0, 0, 0, 0);
                } else {
                    level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z,
                            1, vx * 0.2, vy * 0.2, vz * 0.2, 0.02);
                }
            } else {
                if (random.nextDouble() < 0.7) {
                    level.sendParticles(
                            TAParticleTypes.MAGIC_PURPLE.get(),
                            pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.02);
                } else {
                    level.sendParticles(
                            ParticleTypes.END_ROD, pos.x, pos.y, pos.z,
                            1, vx, vy, vz, 0.02);
                }
            }
        }
        
        if (isSuperBeam) {
            for (int i = 0; i < 2; i++) { 
                double radius = i == 0 ? 0.5 : 1.0;
                double speed = i == 0 ? 0.15 : 0.1;
                int ringParticles = i == 0 ? 20 : 30;
                Vec3 up = new Vec3(0, 1, 0);
                if (Math.abs(direction.dot(up)) > 0.9) {
                    up = new Vec3(1, 0, 0);
                }
                
                Vec3 right = direction.cross(up).normalize();
                Vec3 planeNormal = right.cross(direction).normalize();
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

    public static void addBeamOriginEffect(ServerLevel level, Vec3 origin, Vec3 direction) {
        RandomSource random = level.getRandom();
        int originParticles = 20;
        double originRadius = 0.8;
        for (int i = 0; i < originParticles; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
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
            double speed = 0.02;
            Vec3 outDir = ringPos.subtract(origin).normalize();
            level.sendParticles(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    ringPos.x, ringPos.y, ringPos.z,
                    1,
                    outDir.x * speed,
                    outDir.y * speed,
                    outDir.z * speed,
                    0.01);
        }
        
        int forwardParticles = 15;
        for (int i = 0; i < forwardParticles; i++) {
            double dist = random.nextDouble() * 2;
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = (random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;
            Vec3 pos = origin.add(direction.scale(dist))
                    .add(offsetX, offsetY, offsetZ);
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

    public static void applyBeamDamage(ServerLevel level, BeamInfo beamInfo) {
        Vec3 start = beamInfo.startPos;
        Vec3 direction = beamInfo.direction;
        float damage = beamInfo.damage;
        boolean isSuperBeam = beamInfo.isSuperBeam;
        List<Entity> entities = getEntitiesInBeamPath(level, start, direction, BEAM_DISTANCE, beamInfo.owner);
        Player player = level.getPlayerByUUID(beamInfo.owner);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
                double distanceToBeam = distanceToLine(start, start.add(direction.scale(BEAM_DISTANCE)), entityPos);
                float beamWidth = isSuperBeam ? SUPER_BEAM_WIDTH : BEAM_WIDTH;
                float distanceMultiplier = (float)(1.2 - (distanceToBeam / (beamWidth * 1.5)) * 0.4);
                float adjustedDamage = damage * Math.max(0.8f, distanceMultiplier);
                livingEntity.hurt(level.damageSources().indirectMagic(
                        level.getPlayerByUUID(beamInfo.owner),
                        level.getPlayerByUUID(beamInfo.owner)
                ), adjustedDamage);
                if (livingEntity.isDeadOrDying() && player != null){
                    player.getCooldowns().removeCooldown(TAItems.CRYSTALLINE_SWORD.get());
                } else {
                    int stunDuration = (int)(40 * Math.max(0.8f, distanceMultiplier));
                    if (isSuperBeam) {
                        stunDuration *= 2;
                    }
                    
                    applyStunEffect(livingEntity, stunDuration, isSuperBeam);
                }
                
                if (isSuperBeam && level.getGameTime() % 10 == 0) {
                    level.explode(null, entityPos.x, entityPos.y, entityPos.z, 1.0F, Level.ExplosionInteraction.NONE);
                }
            }
        }
    }
    
    public static void applyStunEffect(LivingEntity target, int baseDuration, boolean isSuperBeam) {
        if (target instanceof EnderDragon || target instanceof WitherBoss ||
                (target instanceof Mob mob && mob.getMaxHealth() >= 100)) {
            baseDuration = Math.max(10, baseDuration / 2);
        }

        if (target instanceof Player) {
            baseDuration = Math.max(5, (int)(baseDuration * 0.75f));
            if (isSuperBeam) {
                target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, baseDuration / 2, 1));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, baseDuration, 1));
            }
        } else if (target.getMaxHealth() > 50 && isSuperBeam) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, baseDuration * 2, 0));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, baseDuration / 2, 0));
        }

        target.addEffect(new MobEffectInstance(TAMobEffects.STUN, baseDuration));
    }

    public static List<Entity> getEntitiesInBeamPath(Level level, Vec3 start, Vec3 direction, double maxDistance, UUID ownerUUID) {
        HitResult hitResult = rayTraceBeam(level, start, direction, maxDistance, ownerUUID);
        Vec3 end;
        if (hitResult.getType() == HitResult.Type.MISS) {
            end = start.add(direction.scale(maxDistance));
        } else {
            end = hitResult.getLocation();
        }

        double minX = Math.min(start.x, end.x) - BEAM_WIDTH;
        double minY = Math.min(start.y, end.y) - BEAM_WIDTH;
        double minZ = Math.min(start.z, end.z) - BEAM_WIDTH;
        double maxX = Math.max(start.x, end.x) + BEAM_WIDTH;
        double maxY = Math.max(start.y, end.y) + BEAM_WIDTH;
        double maxZ = Math.max(start.z, end.z) + BEAM_WIDTH;
        AABB beamBox = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        return level.getEntities(level.getPlayerByUUID(ownerUUID), beamBox, entity -> {
            Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
            double distanceToLine = distanceToLine(start, end, entityPos);
            return distanceToLine < BEAM_WIDTH * 1.5;
        });
    }

    public static HitResult rayTraceBeam(Level level, Vec3 start, Vec3 direction, double maxDistance, UUID ownerUUID) {
        Vec3 end = start.add(direction.scale(maxDistance));
        Entity owner = level.getPlayerByUUID(ownerUUID);
        ClipContext clipContext = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner);
        BlockHitResult blockHit = level.clip(clipContext);
        if (blockHit.getType() == HitResult.Type.MISS) return blockHit;
        double blockDistance = blockHit.getLocation().distanceTo(start);
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
            Vec3 entityPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
            double distanceToLine = distanceToLine(start, end, entityPos);
            return distanceToLine < BEAM_WIDTH * 1.5;
        })) {
            double distance = entity.position().distanceTo(start);
            if (distance < closestDistance && distance < blockDistance) {
                closestEntity = entity;
                closestDistance = distance;
            }
        }

        if (closestEntity != null) {
            Vec3 hitPos = start.add(direction.scale(closestDistance));
            return new EntityHitResult(closestEntity, hitPos);
        }

        return blockHit;
    }

    public static double distanceToLine(Vec3 lineStart, Vec3 lineEnd, Vec3 point) {
        Vec3 line = lineEnd.subtract(lineStart);
        double len = line.length();
        if (len == 0.0) return point.distanceTo(lineStart);
        double t = Math.max(0, Math.min(1, point.subtract(lineStart).dot(line) / (len * len)));
        Vec3 projection = lineStart.add(line.scale(t));
        return point.distanceTo(projection);
    }

    static void fireBeam(ItemStack stack, Level level, Player player, int chargeTime) {
        boolean isSuperBeam = chargeTime >= SUPER_CHARGE_TIME;
        int remainingDurability = stack.getMaxDamage() - stack.getDamageValue();
        if (isSuperBeam && remainingDurability <= 15) isSuperBeam = false;
        stack.consume(1, player);
        if (isSuperBeam && !player.getAbilities().instabuild) {
            for (int i = 0; i < 15; i++) {
                if (stack.getDamageValue() >= stack.getMaxDamage() - 1) break;
                stack.setDamageValue(stack.getDamageValue() + 1);
            }
        }

        if (level.isClientSide) return;
        int effectiveChargeTime = isSuperBeam ? SUPER_CHARGE_TIME : Math.min(chargeTime, MAX_CHARGE_TIME);
        BeamInfo beamInfo = new BeamInfo(
                player.getUUID(),
                player.getEyePosition(),
                player.getLookAngle(),
                calculateBeamDamage(effectiveChargeTime),
                System.currentTimeMillis(),
                BEAM_DURATION,
                isSuperBeam);
        ACTIVE_BEAMS.put(player.getUUID(), beamInfo);
        if (!player.getAbilities().instabuild) {
            player.getCooldowns().addCooldown(TAItems.CRYSTALLINE_SWORD.get(), 600);
        }

        if (level instanceof ServerLevel serverLevel) {
            spawnBeamParticles(serverLevel, beamInfo);
            applyBeamDamage(serverLevel, beamInfo); 
        }
    }

    private static float calculateBeamDamage(int chargeTime) {
        if (chargeTime >= SUPER_CHARGE_TIME) {
            return SUPER_BEAM_DAMAGE;
        }

        float chargeRatio = Math.min(1.0F, chargeTime / (float) MAX_CHARGE_TIME);
        return BEAM_MIN_DAMAGE + (BEAM_MAX_DAMAGE - BEAM_MIN_DAMAGE) * chargeRatio;
    }

    public static void spawnMagicCircleParticles(Level level, Player player, int ticksUsed) {
        float progress = Math.min(1.0F, ticksUsed / (float) MAX_CHARGE_TIME);
        boolean isSuperCharge = ticksUsed >= SUPER_CHARGE_TIME;
        float superProgress = 0;
        if (isSuperCharge) {
            superProgress = Math.min(1.0F, (ticksUsed - SUPER_CHARGE_TIME) / (float)(SUPER_CHARGE_TIME - MAX_CHARGE_TIME));
        }

        Vec3 playerLook = player.getLookAngle();
        Vec3 circleCenter = player.getEyePosition().add(playerLook.scale(3.5));
        float size = 2.5F + progress;
        if (isSuperCharge) {
            size = 3.5F + superProgress * 2.0F;
        }

        double baseRotation = (level.getGameTime() % 360) * 2;
        double rotation = baseRotation;
        double innerRotation = baseRotation * -1.5;
        double outerRotation = baseRotation * 0.8;
        if (isSuperCharge) {
            rotation *= 1.5;
            innerRotation *= 2.0;
            outerRotation *= 1.8;
        }

        float pulseEffect = (float)Math.sin(level.getGameTime() * 0.1) * 0.1F + 1.0F;
        size *= pulseEffect;
        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = playerLook.cross(up).normalize();
        if (right.lengthSqr() < 0.001) {
            right = new Vec3(1, 0, 0);
        }

        Vec3 planeNormal = right.cross(playerLook).normalize();
        float outerRingSize = isSuperCharge ? size * 1.3F : size * 1.2F;
        generateOuterRing(level, circleCenter, outerRingSize, outerRotation, progress, playerLook, right, planeNormal);
        float auraSize = isSuperCharge ? size * 1.4F : size * 1.3F;
        generateAura(level, circleCenter, auraSize, rotation * 0.5, progress, playerLook, right, planeNormal);
        generateHexagram(level, circleCenter, size, rotation, progress, playerLook, right, planeNormal);
        generateRunicCircle(level, circleCenter, size * 0.7F, innerRotation, progress, playerLook, right, planeNormal);
        if (level.getRandom().nextInt(3) == 0) {
            generateEnergyRays(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }

        generateMagicRunes(level, circleCenter, size * 0.9F, rotation, progress, playerLook, right, planeNormal);
        generateEnchantParticles(level, player, circleCenter, size, progress, playerLook, right, planeNormal);
        if (progress > 0.8F) {
            generateHighChargeEffects(level, circleCenter, size, progress);
        }

        if (progress > 0.5F && level.getGameTime() % 20 == 0) {
            generateEnergyRipple(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }

        if (progress > 0.6F && level.getRandom().nextInt(5) == 0) {
            generateLightningEffects(level, circleCenter, size, progress, playerLook, right, planeNormal);
        }

        if (isSuperCharge) {
            generateStarburstEffects(level, circleCenter, size, superProgress, playerLook, right, planeNormal);
            generateRunicTrails(level, circleCenter, size, rotation * 2, superProgress, playerLook, right, planeNormal);
            generateEnergyVortex(level, circleCenter, size * 1.5F, superProgress, playerLook, right, planeNormal);
            if (level.getRandom().nextInt(3) == 0) {
                generateLightningEffects(level, circleCenter, size * 1.2F, superProgress, playerLook, right, planeNormal);
            }

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

    public static void generateOuterRing(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.5F) return;
        int pointCount = 72;
        boolean isSuperCharge = progress >= 1.0F;
        int skipFactor = isSuperCharge ? 2 : 3;
        for (int i = 0; i < pointCount; i++) {
            if (i % skipFactor != 0) continue;
            double angle = Math.toRadians(360.0 / pointCount * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            double speedFactor = 0.002;
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    point.x, point.y, point.z,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor,
                    (level.getRandom().nextDouble() - 0.5) * speedFactor);
            if (isSuperCharge) {
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

    public static void generateAura(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.6F) return;
        RandomSource random = level.getRandom();
        boolean isSuperCharge = progress >= 1.0F;
        int particleCount = isSuperCharge ? (int)(24 * progress) : (int)(12 * progress);
        for (int i = 0; i < particleCount; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = (0.95 + random.nextDouble() * 0.1) * size;
            if (isSuperCharge) {
                dist = (0.9 + random.nextDouble() * 0.2) * size;
            }

            Vec3 offset = right.scale(Math.sin(angle) * dist)
                    .add(up.scale(Math.cos(angle) * dist));
            Vec3 pos = center.add(offset);
            double speedFactor = isSuperCharge ? 0.003 : 0.002;
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    pos.x, pos.y, pos.z,
                    (random.nextDouble() - 0.5) * speedFactor,
                    (random.nextDouble() - 0.5) * speedFactor,
                    (random.nextDouble() - 0.5) * speedFactor);
        }
    }

    public static void generateMagicRunes(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.4F) return;
        RandomSource random = level.getRandom();
        int runeCount = (int)(2 * progress);
        long gameTime = level.getGameTime();
        for (int i = 0; i < runeCount; i++) {
            double angle = (i / (double)runeCount * Math.PI * 2) + (gameTime * 0.01);
            double dist = (0.5 + random.nextDouble() * 0.4) * size;
            Vec3 runePos = center.add(
                    right.scale(Math.sin(angle) * dist).add(
                            up.scale(Math.cos(angle) * dist)));
            double runeRotation = rotation + i * 30;
            int runeType = i % 3;
            switch (runeType) {
                case 0: generateTriangleRune(level, runePos, 0.2F, runeRotation, forward, right, up);break;
                case 1: generateSquareRune(level, runePos, 0.15F, runeRotation, forward, right, up);break;
                case 2: generateCircleRune(level, runePos, 0.18F, runeRotation, forward, right, up);break;
            }
        }
    }

    public static void generateTriangleRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(120 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point1 = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            double nextAngle = Math.toRadians(120 * ((i + 1) % 3) + rotation);
            double nextSin = Math.sin(nextAngle);
            double nextCos = Math.cos(nextAngle);
            Vec3 point2 = center.add(
                    right.scale(nextSin * size).add(
                            up.scale(nextCos * size)));
            int points = 5;
            for (int j = 0; j <= points; j++) {
                double t = j / (double)points;
                Vec3 linePos = point1.add(point2.subtract(point1).scale(t));
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x, linePos.y, linePos.z,
                        0, 0.005, 0);
            }
        }
    }

    public static void generateSquareRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(90 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point1 = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            double nextAngle = Math.toRadians(90 * ((i + 1) % 4) + rotation);
            double nextSin = Math.sin(nextAngle);
            double nextCos = Math.cos(nextAngle);
            Vec3 point2 = center.add(
                    right.scale(nextSin * size).add(
                            up.scale(nextCos * size)));
            int points = 4;
            for (int j = 0; j <= points; j++) {
                double t = j / (double)points;
                Vec3 linePos = point1.add(point2.subtract(point1).scale(t));
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x, linePos.y, linePos.z,
                        0, 0.005, 0);
            }
        }
    }

    public static void generateCircleRune(Level level, Vec3 center, float size, double rotation, Vec3 forward, Vec3 right, Vec3 up) {
        int points = 8;
        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(360.0 / points * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            level.addParticle(
                    TAParticleTypes.MAGIC_PURPLE.get(),
                    point.x, point.y, point.z,
                    0, 0.005, 0);
        }
    }

    public static void generateEnergyRipple(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        float minSize = 0.2F * size;
        float maxSize = 1.2F * size;
        int steps = 8;
        for (int step = 0; step < steps; step++) {
            float rippleSize = minSize + (maxSize - minSize) * (step / (float)steps);
            int pointCount = (int)(12 * (1 + step / (float)steps));
            float alpha = 0.8F * (1 - step / (float)steps) * progress;
            for (int i = 0; i < pointCount; i++) {
                double angle = Math.toRadians(360.0 / pointCount * i);
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);
                Vec3 point = center.add(
                        right.scale(sin * rippleSize).add(
                                up.scale(cos * rippleSize)));
                double speedFactor = 0.001 * (1 - step / (float)steps);
                level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), point.x, point.y, point.z,
                        sin * speedFactor, 0, cos * speedFactor);
            }
        }
    }

    public static void generateEnchantParticles(Level level, Player player, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.3F) return;
        RandomSource random = level.getRandom();
        int particleCount = (int)(3 * progress);
        for (int i = 0; i < particleCount; i++) {
            if (random.nextBoolean()) {
                double angle = random.nextDouble() * Math.PI * 2;
                double dist = (0.8 + random.nextDouble() * 0.4) * size;
                Vec3 offset = right.scale(Math.sin(angle) * dist)
                        .add(up.scale(Math.cos(angle) * dist));
                Vec3 pos = center.add(offset);
                level.addParticle(ParticleTypes.ENCHANT, pos.x, pos.y, pos.z,
                        0, 0.1 + random.nextDouble() * 0.2, 0);
            } else {
                double offsetX = (random.nextDouble() - 0.5);
                double offsetY = random.nextDouble() * 2.0;
                double offsetZ = (random.nextDouble() - 0.5);
                Vec3 playerPos = player.position();
                level.addParticle(ParticleTypes.ENCHANT,
                        playerPos.x + offsetX, playerPos.y + offsetY, playerPos.z + offsetZ,
                        0, 0.05 + random.nextDouble() * 0.05, 0);
            }
        }
    }

    public static void generateHighChargeEffects(Level level, Vec3 center, float size, float progress) {
        RandomSource random = level.getRandom();
        if (random.nextInt(3) == 0) {
            double offsetX = (random.nextDouble() - 0.5) * size * 0.8;
            double offsetY = (random.nextDouble() - 0.5) * size * 0.8;
            double offsetZ = (random.nextDouble() - 0.5) * size * 0.8;
            level.addParticle(ParticleTypes.PORTAL,
                    center.x + offsetX, center.y + offsetY, center.z + offsetZ,
                    (random.nextDouble() - 0.5) * 2,
                    (random.nextDouble() - 0.5) * 2,
                    (random.nextDouble() - 0.5) * 2);
        }
    }

    public static void generateHexagram(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.2F) return;
        boolean isSuperCharge = progress >= 1.0F;
        int skipFactor = isSuperCharge ? 1 : 2;
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            double oppositeAngle = Math.toRadians(60 * ((i + 3) % 6) + rotation);
            double oppositeSin = Math.sin(oppositeAngle);
            double oppositeCos = Math.cos(oppositeAngle);
            Vec3 oppositePoint = center.add(
                    right.scale(oppositeSin * size).add(
                            up.scale(oppositeCos * size)));
            int points = isSuperCharge ? 15 : 10;
            for (int j = 0; j <= points; j++) {
                if (j % skipFactor == 0 && j > 0 && j < points) continue;
                double t = j / (double)points;
                Vec3 linePos = point.add(oppositePoint.subtract(point).scale(t));
                double offsetFactor = isSuperCharge ? 0.12 : 0.08;
                Vec3 offset = right.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor)
                        .add(up.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor));
                level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                        linePos.x + offset.x, linePos.y + offset.y, linePos.z + offset.z,
                        0, 0.005 + level.getRandom().nextDouble() * (isSuperCharge ? 0.02 : 0.01), 0);
                if (isSuperCharge && level.getRandom().nextBoolean()) {
                    Vec3 extraOffset = right.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor * 1.5)
                            .add(up.scale((level.getRandom().nextDouble() - 0.5) * offsetFactor * 1.5));
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                            linePos.x + extraOffset.x, linePos.y + extraOffset.y, linePos.z + extraOffset.z,
                            0, 0.005 + level.getRandom().nextDouble() * 0.02, 0);
                }
            }
        }
    }

    public static void generateRunicCircle(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.4F) return;
        int runeCount = 10;
        for (int i = 0; i < runeCount; i++) {
            double angle = Math.toRadians(360.0 / runeCount * i + rotation);
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            Vec3 point = center.add(
                    right.scale(sin * size).add(
                            up.scale(cos * size)));
            double runeHeight = 0.2;
            int runePoints = 1;
            for (int j = 0; j < runePoints; j++) {
                double t = j - 0.5;
                Vec3 runePos = point.add(forward.scale(t * runeHeight));
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

    public static void generateEnergyRays(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.7F) return;
        int rayCount = (int)(1 + progress * 2);
        for (int i = 0; i < rayCount; i++) {
            double angle = level.getRandom().nextDouble() * Math.PI * 2;
            Vec3 rayDir = right.scale(Math.sin(angle))
                    .add(up.scale(Math.cos(angle)))
                    .add(forward.scale((level.getRandom().nextDouble() - 0.5) * 0.1));
            rayDir = rayDir.normalize();
            double rayLength = size * (0.4 + level.getRandom().nextDouble() * 0.4);
            int points = 2;
            for (int j = 0; j < points; j++) {
                double t = j / (double)points;
                Vec3 rayPos = center.add(rayDir.scale(t * rayLength));
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

    public static void generateLightningEffects(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        int lightningCount = 1 + random.nextInt(2);
        for (int i = 0; i < lightningCount; i++) {
            double startAngle = random.nextDouble() * Math.PI * 2;
            double startDist = size * 0.8;
            Vec3 startPos = center.add(
                    right.scale(Math.sin(startAngle) * startDist).add(
                            up.scale(Math.cos(startAngle) * startDist)));
            double endAngle = startAngle + Math.PI + (random.nextDouble() - 0.5) * Math.PI;
            double endDist = size * (0.7 + random.nextDouble() * 0.3);
            Vec3 endPos = center.add(
                    right.scale(Math.sin(endAngle) * endDist).add(
                            up.scale(Math.cos(endAngle) * endDist)));
            int segments = 4 + random.nextInt(3);
            Vec3 lastPos = startPos;
            for (int j = 1; j <= segments; j++) {
                double t = j / (double)segments;
                Vec3 targetPos = startPos.add(endPos.subtract(startPos).scale(t));
                double offsetMagnitude = size * 0.15 * (1 - t);
                Vec3 offset = right.scale((random.nextDouble() - 0.5) * offsetMagnitude)
                        .add(up.scale((random.nextDouble() - 0.5) * offsetMagnitude));
                Vec3 toCenter = center.subtract(targetPos.add(offset));
                double distToCenter = toCenter.length();
                if (distToCenter < size * 0.4) {
                    offset = offset.scale(-1);
                }

                targetPos = targetPos.add(offset);
                int points = 5;
                for (int k = 0; k < points; k++) {
                    double s = k / (double)points;
                    Vec3 pos = lastPos.add(targetPos.subtract(lastPos).scale(s));
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z, 0, 0, 0);
                }

                lastPos = targetPos;
            }
        }
    }

    public static void generateStarburstEffects(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        if (progress < 0.3F || level.getRandom().nextInt(4) != 0) return;
        RandomSource random = level.getRandom();
        int starCount = 1 + (int)(progress * 2);
        for (int i = 0; i < starCount; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = size * (0.5 + random.nextDouble() * 0.5);
            Vec3 starPos = center.add(
                    right.scale(Math.sin(angle) * dist).add(
                            up.scale(Math.cos(angle) * dist)));
            int rays = 4 + random.nextInt(4);
            float rayLength = 0.2F + random.nextFloat() * 0.3F;
            for (int j = 0; j < rays; j++) {
                double rayAngle = j * (Math.PI * 2 / rays);
                Vec3 rayDir = right.scale(Math.sin(rayAngle)).add(up.scale(Math.cos(rayAngle)));
                Vec3 rayEnd = starPos.add(rayDir.scale(rayLength));
                level.addParticle(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        starPos.x, starPos.y, starPos.z,
                        rayDir.x * 0.02,
                        rayDir.y * 0.02,
                        rayDir.z * 0.02);
            }
        }
    }

    public static void generateRunicTrails(Level level, Vec3 center, float size, double rotation, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        int trailCount = 1 + (int)(progress * 2);
        if (random.nextInt(3) != 0) return;
        for (int i = 0; i < trailCount; i++) {
            double startAngle = random.nextDouble() * Math.PI * 2;
            double startDist = size * (0.5 + random.nextDouble() * 0.5);
            Vec3 startPos = center.add(
                    right.scale(Math.sin(startAngle) * startDist).add(
                            up.scale(Math.cos(startAngle) * startDist)));
            double moveAngle = startAngle + Math.PI/2 + (random.nextDouble() - 0.5) * Math.PI/4;
            Vec3 moveDir = right.scale(Math.sin(moveAngle)).add(up.scale(Math.cos(moveAngle)));
            double trailLength = size * (0.3 + random.nextDouble() * 0.3);
            int points = 5 + random.nextInt(5);
            for (int j = 0; j < points; j++) {
                double t = j / (double)points;
                Vec3 pos = startPos.add(moveDir.scale(t * trailLength));
                float hue = (float)(t * 0.2 + random.nextDouble() * 0.1);
                Vector3f color = new Vector3f(0.7F, 0.3F + hue, 0.9F);
                level.addParticle(new DustParticleOptions(color, 1.0F), pos.x, pos.y, pos.z, 0, 0, 0);
            }
        }
    }

    public static void generateEnergyVortex(Level level, Vec3 center, float size, float progress, Vec3 forward, Vec3 right, Vec3 up) {
        RandomSource random = level.getRandom();
        if (random.nextInt(4) != 0) return;
        double vortexAngle = random.nextDouble() * Math.PI * 2;
        double vortexDist = size * 0.8;
        Vec3 vortexCenter = center.add(
                right.scale(Math.sin(vortexAngle) * vortexDist).add(
                        up.scale(Math.cos(vortexAngle) * vortexDist)));
        float vortexSize = size * 0.25F;
        boolean clockwise = random.nextBoolean();
        int particleCount = 10 + (int)(progress * 10);
        for (int i = 0; i < particleCount; i++) {
            double t = i / (double)particleCount;
            double spiralRadius = vortexSize * t;
            double spiralAngle = t * Math.PI * 6 + level.getGameTime() * 0.1 * (clockwise ? 1 : -1);
            Vec3 offset = right.scale(Math.sin(spiralAngle) * spiralRadius)
                    .add(up.scale(Math.cos(spiralAngle) * spiralRadius));
            Vec3 pos = vortexCenter.add(offset);
            double speedFactor = 0.02 * (1 - t);
            Vec3 velocity = right.scale(Math.cos(spiralAngle) * speedFactor * (clockwise ? -1 : 1))
                    .add(up.scale(-Math.sin(spiralAngle) * speedFactor * (clockwise ? -1 : 1)));
            level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(), pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
        }
    }

    public static class BeamInfo {

        final UUID owner;
        final Vec3 startPos;
        final Vec3 direction;
        final float damage;
        final long startTime;
        final int duration;
        final boolean isSuperBeam;

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
    
}