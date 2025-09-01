package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class WorldScroll extends Item {

    private static final int MAX_CHARGE_TIME = 140; // 7秒的最大蓄力时间
    private static final int PARTICLE_DENSITY = 2; // 粒子密度系数
    private static final float MAX_RADIUS = 32.0F; // 最大半径(32个方块)
    private static final int LAYER_COUNT = 5; // 魔法阵层数
    private final Random random = new Random();

    public WorldScroll() {
        super(new Item.Properties()
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                .durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!(entity instanceof Player player)) {
            return;
        }
        
        // 计算蓄力时间
        int chargeTime = getUseDuration(stack, entity) - remainingUseDuration;
        float chargeProgress = Math.min(1.0F, chargeTime / (float)MAX_CHARGE_TIME);
        
        // 服务器端设置天气为暴雨
        if (!level.isClientSide) {
            if (!level.isRaining() || !level.isThundering()) {
                // 使用正确的方法设置天气
                if (level.getServer() != null) {
                    ServerLevel serverLevel = level.getServer().getLevel(Level.OVERWORLD);
                    if (serverLevel != null) {
                        // 设置天气为暴雨
                        serverLevel.setWeatherParameters(0, 200, true, true);
                    }
                }
            }
        }

        if (level.isClientSide) {
            generateWorldMagicCircle(level, player, chargeProgress);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
    
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide && level.isRaining()) {
            // 停止暴雨和雷暴
            if (level.getServer() != null) {
                ServerLevel serverLevel = level.getServer().getLevel(Level.OVERWORLD);
                if (serverLevel != null) {
                    // 设置天气为晴天
                    serverLevel.setWeatherParameters(6000, 0, false, false);
                }
            }
        }
    }

    private void generateWorldMagicCircle(Level level, Player player, float chargeProgress) {
        Vec3 basePos = player.position();
        generateVerticalMagicCircles(level, basePos, chargeProgress);
        generateCenterGeometricPattern(level, basePos, chargeProgress);
    }

    private void generateVerticalMagicCircles(Level level, Vec3 basePos, float progress) {
        float totalHeight = 12.0F;
        int ringCount = 10;
        float baseHeight = 0.1F;
        float layerHeight = 3.0F;
        long gameTime = level.getGameTime();
        int visibleRings = Math.min(ringCount, Math.max(3, (int)(ringCount * progress * 2.0)));
        if (progress > 0.1F) {
            generateFootCircle(level, basePos, progress, gameTime);
        }

        float[] customRadii = new float[] {20.0F, 10.0F, 20.0F, 40.0F, 50.0F, 70.0F, 40.0F, 30.0F, 40.0F, 50.0F};
        for (int ring = 0; ring < visibleRings; ring++) {
            float height = baseHeight + ring * layerHeight;
            float radius = customRadii[ring % customRadii.length];
            float rotationSpeed = 0.02F * (ring % 2 == 0 ? 1 : -1);
            float rotation = gameTime * rotationSpeed;
            float ringVisibility = Math.min(1.0F, (progress * ringCount - ring * 0.5F) * 2);
            if (ringVisibility <= 0) continue;
            int particles = (int)(120 * ringVisibility);
            generateRing(level, basePos.add(0, height, 0), radius, particles, rotation);
            if (ringVisibility > 0.3F) {
                generateRing(level, basePos.add(0, height, 0), radius * 0.7F, (int)(particles * 0.7F), -rotation * 1.5F);
            }

            if (ringVisibility > 0.4F) {
                generateLayerPattern(level, basePos.add(0, height, 0), ring, radius * 0.8F, rotation, ringVisibility);
            }

            if (ring == 4 && ringVisibility > 0.2F) {
                generateSatelliteRunes(level, basePos.add(0, height, 0), radius, gameTime, Math.max(progress, 0.5F));
            }

            if ((ring == 8 || ring == 9) && ringVisibility > 0.2F) {
                float animationProgress = Math.min(1.0F, (progress * 3.0F - 0.3F));
                generateAnimatedGeometry(level, basePos.add(0, height, 0), radius, gameTime, ring, Math.max(animationProgress, 0.3F));
            }
        }
    }

    private void generateLayerPattern(Level level, Vec3 center, int layer, float size, float rotation, float visibility) {
        switch (layer % 8) {
            case 0:
                generateCrescent(level, center, size, rotation, visibility);
                break;
            case 1:
                generateMagicStar(level, center, size, 5, visibility, rotation);
                break;
            case 2:
                generateMagicStar(level, center, size, 6, visibility, rotation);
                break;
            case 3:
                generateSpiral(level, center, size, rotation, visibility);
                break;
            case 4:
                generateTriangle(level, center, size, rotation, visibility);
                break;
            case 5:
                generateMagicStar(level, center, size, 8, visibility, rotation);
                break;
            case 6:
                generateSquare(level, center, size, rotation, visibility);
                break;
            case 7:
                generateMagicStar(level, center, size, 12, visibility, rotation);
                break;
        }
    }

    private void generateCrescent(Level level, Vec3 center, float size, float rotation, float visibility) {
        int particlesOuter = (int)(60 * visibility);
        double innerRadius = size * 0.8;
        double offsetX = size * 0.4;
        int particlesInner = (int)(50 * visibility);
        for (int i = 0; i < particlesOuter; i++) {
            double angle = Math.toRadians(i * (360.0 / particlesOuter) + rotation * 10);
            double x = center.x + Math.cos(angle) * (double) size;
            double z = center.z + Math.sin(angle) * (double) size;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }

        for (int i = 0; i < particlesInner; i++) {
            double angle = Math.toRadians(i * (360.0 / particlesInner) + rotation * 10);
            double x = center.x + offsetX + Math.cos(angle) * innerRadius;
            double z = center.z + Math.sin(angle) * innerRadius;
            if (x > center.x) {
                level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
            }
        }

        if (visibility > 0.8) {
            for (int i = 0; i < 5; i++) {
                double angle = Math.toRadians(i * 30 + rotation * 5);
                double starX = center.x - size * 0.3 + Math.cos(angle) * (size * 0.15);
                double starZ = center.z + Math.sin(angle) * (size * 0.15);
                level.addParticle(ParticleTypes.END_ROD, starX, center.y, starZ, 0, 0, 0);
            }
        }
    }

    private void generateTriangle(Level level, Vec3 center, float size, float rotation, float visibility) {
        int points = 3;
        Vec3[] vertices = new Vec3[points];
        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(i * (360.0 / points) + rotation * 10);
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            vertices[i] = new Vec3(x, center.y, z);
        }

        for (int i = 0; i < points; i++) {
            drawLine(level, vertices[i], vertices[(i + 1) % points], (int)(15 * visibility));
        }

        if (visibility > 0.6) {
            Vec3[] innerVertices = new Vec3[points];
            float innerSize = size * 0.5f;
            for (int i = 0; i < points; i++) {
                double angle = Math.toRadians(i * (360.0 / points) + rotation * 10 + Math.PI);
                double x = center.x + Math.cos(angle) * innerSize;
                double z = center.z + Math.sin(angle) * innerSize;
                innerVertices[i] = new Vec3(x, center.y, z);
            }

            for (int i = 0; i < points; i++) {
                drawLine(level, innerVertices[i], innerVertices[(i + 1) % points], (int)(10 * visibility));
            }

            if (visibility > 0.8) {
                for (int i = 0; i < points; i++) {
                    drawLine(level, vertices[i], innerVertices[i], (int)(8 * visibility));
                }
            }
        }

        if (visibility > 0.7) {
            level.addParticle(ParticleTypes.END_ROD, center.x, center.y, center.z, 0, 0, 0);
        }
    }

    private void generateSquare(Level level, Vec3 center, float size, float rotation, float visibility) {
        int points = 4;
        Vec3[] vertices = new Vec3[points];
        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(i * (360.0 / points) + rotation * 10 + Math.PI/4); // 旋转45度
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            vertices[i] = new Vec3(x, center.y, z);
        }

        for (int i = 0; i < points; i++) {
            drawLine(level, vertices[i], vertices[(i + 1) % points], (int)(15 * visibility));
        }

        if (visibility > 0.7) {
            drawLine(level, vertices[0], vertices[2], (int)(10 * visibility));
            drawLine(level, vertices[1], vertices[3], (int)(10 * visibility));
        }

        if (visibility > 0.8) {
            float innerSize = size * 0.4f;
            Vec3[] innerVertices = new Vec3[points];
            for (int i = 0; i < points; i++) {
                double angle = Math.toRadians(i * (360.0 / points) + rotation * 10 + Math.PI/4);
                double x = center.x + Math.cos(angle) * innerSize;
                double z = center.z + Math.sin(angle) * innerSize;
                innerVertices[i] = new Vec3(x, center.y, z);
            }

            for (int i = 0; i < points; i++) {
                drawLine(level, innerVertices[i], innerVertices[(i + 1) % points], (int)(8 * visibility));
            }
        }
    }

    private void generateSpiral(Level level, Vec3 center, float size, float rotation, float visibility) {
        int turns = 3;
        int particles = (int)(100 * visibility);
        int outerParticles = (int)(40 * visibility);
        for (int i = 0; i < outerParticles; i++) {
            double angle = Math.toRadians(i * (360.0 / outerParticles) + rotation * 10);
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }

        for (int i = 0; i < particles; i++) {
            double progress = (double)i / particles;
            double spiralRadius = size * (1.0 - progress);
            double angle = Math.toRadians(progress * turns * 360.0 + rotation * 10);
            double x = center.x + Math.cos(angle) * spiralRadius;
            double z = center.z + Math.sin(angle) * spiralRadius;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }

        if (visibility > 0.7) {
            for (int i = 0; i < particles / 2; i++) {
                double progress = (double)i / (particles / 2.0D);
                double spiralRadius = size * (1.0 - progress);
                double angle = Math.toRadians(progress * turns * 360.0 + rotation * 10 + 180);
                double x = center.x + Math.cos(angle) * spiralRadius;
                double z = center.z + Math.sin(angle) * spiralRadius;
                level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
            }
        }

        if (visibility > 0.5) {
            level.addParticle(ParticleTypes.END_ROD, center.x, center.y, center.z, 0, 0, 0);
        }
    }

    private void generateRing(Level level, Vec3 center, float radius, int particles, float rotation) {
        int denseParticles = Math.max((int)(radius * 20), particles);
        for (int i = 0; i < denseParticles; i++) {
            double angle = Math.toRadians(i * (360.0 / denseParticles) + rotation * 10);
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
            if (i % 30 == 0) {
                for (int h = 1; h <= 3; h++) {
                    level.addParticle(ParticleTypes.END_ROD, x, center.y + h * 0.15, z, 0, 0, 0);
                }
            }
        }
    }

    private void generateCenterGeometricPattern(Level level, Vec3 basePos, float progress) {
        // 由于现在每层魔法阵都有自己的图案，这个方法不再需要生成任何内容
    }

    private void generateMagicStar(Level level, Vec3 center, float size, int points, float visibility, float rotation) {
        visibility = Math.min(1.0F, visibility);
        points = Math.max(3, points);
        size = size * visibility;
        int particlesOuter = (int)(60 * visibility);
        for (int i = 0; i < particlesOuter; i++) {
            double angle = Math.toRadians(i * (360.0 / particlesOuter) + rotation * 0.01);
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }

        double starInnerRadius = size * 0.4;
        for (int point = 0; point < points; point++) {
            double outerAngle1 = Math.toRadians(point * (360.0 / points) + rotation * 0.01);
            double outerAngle2 = Math.toRadians(((point + 1) % points) * (360.0 / points) + rotation * 0.01);
            double x1 = center.x + Math.cos(outerAngle1) * size;
            double z1 = center.z + Math.sin(outerAngle1) * size;
            double innerAngle = Math.toRadians((point + 0.5) * (360.0 / points) + rotation * 0.01);
            double x2 = center.x + Math.cos(innerAngle) * starInnerRadius;
            double z2 = center.z + Math.sin(innerAngle) * starInnerRadius;
            double x3 = center.x + Math.cos(outerAngle2) * size;
            double z3 = center.z + Math.sin(outerAngle2) * size;
            drawLine(level, new Vec3(x1, center.y, z1), new Vec3(x2, center.y, z2), 8);
            drawLine(level, new Vec3(x2, center.y, z2), new Vec3(x3, center.y, z3), 8);
            if (visibility > 0.7 && point % 2 == 0) {
                Vec3 outerPoint = new Vec3(
                        center.x + Math.cos(outerAngle1) * (size * 1.3),
                        center.y,
                        center.z + Math.sin(outerAngle1) * (size * 1.3));
                drawLine(level, new Vec3(x1, center.y, z1), outerPoint, 5);
            }
        }

        double centerRadius = size * 0.2;
        int particlesCenter = (int)(30 * visibility);
        for (int i = 0; i < particlesCenter; i++) {
            double angle = Math.toRadians(i * (360.0 / particlesCenter) + rotation * 0.02);
            double x = center.x + Math.cos(angle) * centerRadius;
            double z = center.z + Math.sin(angle) * centerRadius;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }

        if (visibility > 0.8) {
            for (int i = 0; i < points; i++) {
                double angle = Math.toRadians(i * (360.0 / points) + rotation * 0.015);
                double runeDistance = size * 0.6;
                double x = center.x + Math.cos(angle) * runeDistance;
                double z = center.z + Math.sin(angle) * runeDistance;
                generateSmallRune(level, new Vec3(x, center.y, z), 0.3F, angle);
            }
        }
    }

    private void generateSmallRune(Level level, Vec3 center, float size, double rotation) {
        int particles = 8;
        for (int i = 0; i < particles; i++) {
            double angle = Math.toRadians(i * (360.0 / particles)) + rotation;
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }
    }

    private void drawLine(Level level, Vec3 start, Vec3 end, int points) {
        int densePoints = Math.max((int)(start.distanceTo(end) * 3.5), points);
        for (int i = 0; i <= densePoints; i++) {
            double t = i / (double)densePoints;
            double x = start.x + (end.x - start.x) * t;
            double y = start.y + (end.y - start.y) * t;
            double z = start.z + (end.z - start.z) * t;
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
        }
    }

    private void generateOuterRing(Level level, Vec3 center, float progress) {
        float radius = MAX_RADIUS * progress;
        int particles = (int)(360 * PARTICLE_DENSITY * progress);
        for (int i = 0; i < particles; i++) {
            double angle = Math.toRadians(i * (360.0 / particles));
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            double heightOffset = Math.sin(angle * 4) * 2.0 * progress;
            double y = center.y + heightOffset;
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
            if (i % (10 / Math.max(1, (int)(progress * 5))) == 0) {
                for (int h = 0; h < 10 * progress; h++) {
                    double pillarHeight = y + h * 0.5;
                    level.addParticle(ParticleTypes.END_ROD, x, pillarHeight, z, 0, 0.02, 0);
                }
            }
        }

        double tiltAngle = Math.toRadians(30); // 倾斜角度
        for (int i = 0; i < particles / 2; i++) {
            double angle = Math.toRadians(i * (360.0 / (particles / 2.0D))) + level.getGameTime() * 0.01;
            double x = center.x + Math.cos(angle) * radius * Math.cos(tiltAngle);
            double y = center.y + Math.sin(tiltAngle) * Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
        }
    }

    private void generateRotatingRings(Level level, Vec3 center, float progress) {
        long gameTime = level.getGameTime();
        float maxRadius = MAX_RADIUS * 0.8f * progress;
        for (int ring = 0; ring < 5; ring++) {
            float ringRadius = maxRadius * (0.4f + ring * 0.15f);
            float rotationSpeed = 0.02f * (ring % 2 == 0 ? 1 : -1);
            float particleDensity = PARTICLE_DENSITY * (5 - ring) * progress;
            int particles = (int)(180 * particleDensity);
            for (int i = 0; i < particles; i++) {
                if (i % 2 != 0 && progress < 0.5) continue;
                double angle = Math.toRadians(i * (360.0 / particles) + gameTime * rotationSpeed);
                double x = center.x + Math.cos(angle) * ringRadius;
                double z = center.z + Math.sin(angle) * ringRadius;
                double waveOffset = Math.sin(angle * (ring + 1) * 2) * ring * progress;
                double y = center.y + waveOffset;
                level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
            }
        }

        for (int spiral = 0; spiral < 3; spiral++) {
            double spiralOffset = Math.PI * 2 / 3 * spiral;
            for (int i = 0; i < 120 * progress; i++) {
                double angle = Math.toRadians(i * 6L + gameTime * 2) + spiralOffset;
                double spiralRadius = maxRadius * 0.3 * (1 - i / (120.0 * progress));
                double x = center.x + Math.cos(angle) * spiralRadius;
                double z = center.z + Math.sin(angle) * spiralRadius;
                double y = center.y + i * 0.2 * progress;
                level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0.01, 0);
            }
        }
    }

    private void generateVerticalCircles(Level level, Vec3 center, float progress) {
        long gameTime = level.getGameTime();
        float maxRadius = MAX_RADIUS * 0.7f * progress;
        for (int plane = 0; plane < 3; plane++) {
            double rotationAngle = gameTime * 0.01 * (plane == 0 ? 1 : plane == 1 ? -1 : 0.5);
            int particles = (int)(240 * PARTICLE_DENSITY * progress);
            for (int i = 0; i < particles; i++) {
                double angle = Math.toRadians(i * (360.0 / particles) + rotationAngle * 10);
                double ringRadius = maxRadius * (0.8 + Math.sin(angle * 8) * 0.2 * progress);
                double x, y, z;
                double x1 = center.x + Math.cos(angle) * ringRadius;
                double z1 = center.z + Math.sin(angle) * ringRadius;
                switch (plane) {
                    case 0:
                        x = x1;
                        y = center.y + Math.sin(angle) * ringRadius;
                        z = center.z;
                        break;
                    case 1:
                        x = center.x;
                        y = center.y + Math.cos(angle) * ringRadius;
                        z = z1;
                        break;
                    default:
                        x = x1;
                        y = center.y;
                        z = z1;
                        break;
                }

                if (i % 15 == 0 && progress > 0.6) {
                    generateRune(level, new Vec3(x, y, z), plane, progress);
                }
                
                level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
            }
        }

        if (progress > 0.5) {
            int connectionLines = (int)(20 * progress);
            for (int i = 0; i < connectionLines; i++) {
                double angle1 = random.nextDouble() * Math.PI * 2;
                double angle2 = random.nextDouble() * Math.PI * 2;
                double radius1 = maxRadius * (0.3 + random.nextDouble() * 0.7);
                double radius2 = maxRadius * (0.3 + random.nextDouble() * 0.7);
                int plane1 = random.nextInt(3);
                int plane2 = random.nextInt(3);
                Vec3 point1 = getPointOnPlane(center, angle1, radius1, plane1);
                Vec3 point2 = getPointOnPlane(center, angle2, radius2, plane2);
                int linePoints = (int)(20 * progress);
                for (int j = 0; j < linePoints; j++) {
                    double t = j / (double)linePoints;
                    double x = point1.x + (point2.x - point1.x) * t;
                    double y = point1.y + (point2.y - point1.y) * t;
                    double z = point1.z + (point2.z - point1.z) * t;
                    level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                }
            }
        }
    }

    private Vec3 getPointOnPlane(Vec3 center, double angle, double radius, int plane) {
        double x, y, z;
        switch (plane) {
            case 0: // XY平面
                x = center.x + Math.cos(angle) * radius;
                y = center.y + Math.sin(angle) * radius;
                z = center.z;
                break;
            case 1: // YZ平面
                x = center.x;
                y = center.y + Math.cos(angle) * radius;
                z = center.z + Math.sin(angle) * radius;
                break;
            default: // XZ平面
                x = center.x + Math.cos(angle) * radius;
                y = center.y;
                z = center.z + Math.sin(angle) * radius;
                break;
        }
        
        return new Vec3(x, y, z);
    }

    private void generateRune(Level level, Vec3 position, int plane, float progress) {
        double runeSize = 1.0 + progress * 2.0;
        int runeType = random.nextInt(3);
        switch (runeType) {
            case 0:
                generateTriangleRune(level, position, runeSize, plane);
                break;
            case 1:
                generateSquareRune(level, position, runeSize, plane);
                break;
            case 2:
                generateCircleRune(level, position, runeSize, plane);
                break;
        }
    }

    private void generateTriangleRune(Level level, Vec3 center, double size, int plane) {
        // 三角形的三个顶点
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(i * 120);
            // 根据平面确定点的坐标
            double x, y, z;
            switch (plane) {
                case 0: // XY平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y + Math.sin(angle) * size;
                    z = center.z;
                    break;
                case 1: // YZ平面
                    x = center.x;
                    y = center.y + Math.cos(angle) * size;
                    z = center.z + Math.sin(angle) * size;
                    break;
                default: // XZ平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y;
                    z = center.z + Math.sin(angle) * size;
                    break;
            }
            
            // 绘制三角形边
            double nextAngle = Math.toRadians(((i + 1) % 3) * 120);
            double nextX, nextY, nextZ;
            
            switch (plane) {
                case 0: // XY平面
                    nextX = center.x + Math.cos(nextAngle) * size;
                    nextY = center.y + Math.sin(nextAngle) * size;
                    nextZ = center.z;
                    break;
                case 1: // YZ平面
                    nextX = center.x;
                    nextY = center.y + Math.cos(nextAngle) * size;
                    nextZ = center.z + Math.sin(nextAngle) * size;
                    break;
                default: // XZ平面
                    nextX = center.x + Math.cos(nextAngle) * size;
                    nextY = center.y;
                    nextZ = center.z + Math.sin(nextAngle) * size;
                    break;
            }
            
            // 绘制边
            int edgePoints = 10;
            for (int j = 0; j < edgePoints; j++) {
                double t = j / (double)edgePoints;
                double lineX = x + (nextX - x) * t;
                double lineY = y + (nextY - y) * t;
                double lineZ = z + (nextZ - z) * t;
                
                level.addParticle(ParticleTypes.END_ROD, 
                        lineX, lineY, lineZ, 
                        0, 0, 0);
            }
        }
    }

    /**
     * 生成方形符文
     */
    private void generateSquareRune(Level level, Vec3 center, double size, int plane) {
        // 正方形的四个顶点
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(i * 90);
            
            // 根据平面确定点的坐标
            double x, y, z;
            switch (plane) {
                case 0: // XY平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y + Math.sin(angle) * size;
                    z = center.z;
                    break;
                case 1: // YZ平面
                    x = center.x;
                    y = center.y + Math.cos(angle) * size;
                    z = center.z + Math.sin(angle) * size;
                    break;
                default: // XZ平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y;
                    z = center.z + Math.sin(angle) * size;
                    break;
            }
            
            // 绘制方形边
            double nextAngle = Math.toRadians(((i + 1) % 4) * 90);
            double nextX, nextY, nextZ;
            
            switch (plane) {
                case 0: // XY平面
                    nextX = center.x + Math.cos(nextAngle) * size;
                    nextY = center.y + Math.sin(nextAngle) * size;
                    nextZ = center.z;
                    break;
                case 1: // YZ平面
                    nextX = center.x;
                    nextY = center.y + Math.cos(nextAngle) * size;
                    nextZ = center.z + Math.sin(nextAngle) * size;
                    break;
                default: // XZ平面
                    nextX = center.x + Math.cos(nextAngle) * size;
                    nextY = center.y;
                    nextZ = center.z + Math.sin(nextAngle) * size;
                    break;
            }
            
            // 绘制边
            int edgePoints = 10;
            for (int j = 0; j < edgePoints; j++) {
                double t = j / (double)edgePoints;
                double lineX = x + (nextX - x) * t;
                double lineY = y + (nextY - y) * t;
                double lineZ = z + (nextZ - z) * t;
                
                level.addParticle(ParticleTypes.END_ROD, 
                        lineX, lineY, lineZ, 
                        0, 0, 0);
            }
        }
    }

    /**
     * 生成圆形符文
     */
    private void generateCircleRune(Level level, Vec3 center, double size, int plane) {
        int points = 16;
        for (int i = 0; i < points; i++) {
            double angle = Math.toRadians(i * (360.0 / points));
            
            // 根据平面确定点的坐标
            double x, y, z;
            switch (plane) {
                case 0: // XY平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y + Math.sin(angle) * size;
                    z = center.z;
                    break;
                case 1: // YZ平面
                    x = center.x;
                    y = center.y + Math.cos(angle) * size;
                    z = center.z + Math.sin(angle) * size;
                    break;
                default: // XZ平面
                    x = center.x + Math.cos(angle) * size;
                    y = center.y;
                    z = center.z + Math.sin(angle) * size;
                    break;
            }
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x, y, z, 
                    0, 0, 0);
        }
    }

    /**
     * 生成星象阵列
     */
    private void generateStarConstellation(Level level, Vec3 center, float progress) {
        if (progress < 0.4) return; // 只在较高充能时显示
        
        long gameTime = level.getGameTime();
        int starCount = (int)(100 * progress);
        float radius = MAX_RADIUS * progress;
        
        // 生成星点
        for (int i = 0; i < starCount; i++) {
            // 随机位置，但保持在魔法阵范围内
            double distance = radius * (0.4 + random.nextDouble() * 0.6);
            double azimuth = random.nextDouble() * Math.PI * 2;
            double elevation = (random.nextDouble() - 0.5) * Math.PI;
            
            double x = center.x + distance * Math.cos(elevation) * Math.cos(azimuth);
            double y = center.y + distance * Math.sin(elevation);
            double z = center.z + distance * Math.cos(elevation) * Math.sin(azimuth);
            
            // 星点位置随时间微小变化
            double offset = Math.sin(gameTime * 0.05 + i) * 0.2;
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x + offset, y + offset, z + offset, 
                    0, 0, 0);
            
            // 连接一些星点形成星座
            if (i % 10 == 0 && i > 0) {
                int prevStar = i - 10 + random.nextInt(10);
                if (prevStar >= 0) {
                    double prevDistance = radius * (0.4 + (prevStar * 0.6 / starCount));
                    double prevAzimuth = (prevStar * Math.PI * 2 / starCount) + random.nextDouble();
                    double prevElevation = ((prevStar * 1.0 / starCount) - 0.5) * Math.PI;
                    
                    double prevX = center.x + prevDistance * Math.cos(prevElevation) * Math.cos(prevAzimuth);
                    double prevY = center.y + prevDistance * Math.sin(prevElevation);
                    double prevZ = center.z + prevDistance * Math.cos(prevElevation) * Math.sin(prevAzimuth);
                    
                    // 绘制连接线
                    int linePoints = 10;
                    for (int j = 0; j < linePoints; j++) {
                        double t = j / (double)linePoints;
                        double lineX = prevX + (x - prevX) * t;
                        double lineY = prevY + (y - prevY) * t;
                        double lineZ = prevZ + (z - prevZ) * t;
                        
                        level.addParticle(ParticleTypes.END_ROD, 
                                lineX, lineY, lineZ, 
                                0, 0, 0);
                    }
                }
            }
        }
        
        // 添加星云效果
        if (progress > 0.7) {
            int nebulaPoints = (int)(300 * progress);
            for (int i = 0; i < nebulaPoints; i++) {
                // 星云在两个区域形成
                int region = i % 2;
                double regionAzimuth = region * Math.PI + random.nextDouble() * Math.PI * 0.5;
                double distance = radius * (0.5 + random.nextDouble() * 0.3);
                double elevation = (random.nextDouble() - 0.5) * Math.PI * 0.5;
                
                double x = center.x + distance * Math.cos(elevation) * Math.cos(regionAzimuth);
                double y = center.y + distance * Math.sin(elevation);
                double z = center.z + distance * Math.cos(elevation) * Math.sin(regionAzimuth);
                
                level.addParticle(ParticleTypes.END_ROD, 
                        x, y, z, 
                        0, 0, 0);
            }
        }
    }

    /**
     * 生成宇宙天体
     */
    private void generateCelestialBodies(Level level, Vec3 center, float progress) {
        if (progress < 0.6) return; // 只在高充能状态显示
        
        long gameTime = level.getGameTime();
        
        // 生成"太阳"
        double sunRadius = 5.0 * progress;
        double sunY = center.y + MAX_RADIUS * 0.7 * progress;
        
        // 太阳光芒
        int sunRays = (int)(50 * progress);
        for (int i = 0; i < sunRays; i++) {
            double angle = Math.toRadians(i * (360.0 / sunRays));
            double rayLength = sunRadius * (1.0 + Math.sin(gameTime * 0.05 + i) * 0.2);
            
            double x = center.x + Math.cos(angle) * rayLength;
            double z = center.z + Math.sin(angle) * rayLength;
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x, sunY, z, 
                    0, 0, 0);
        }
        
        // 太阳内部
        int sunInnerPoints = (int)(80 * progress);
        for (int i = 0; i < sunInnerPoints; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = random.nextDouble() * sunRadius;
            
            double x = center.x + Math.cos(angle) * distance;
            double z = center.z + Math.sin(angle) * distance;
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x, sunY, z, 
                    0, 0, 0);
        }
        
        // 生成多个"行星"
        int planetCount = 3 + (int)(progress * 2);
        for (int p = 0; p < planetCount; p++) {
            // 行星位置
            double orbitRadius = MAX_RADIUS * (0.4 + (p * 0.1));
            double orbitAngle = Math.toRadians(p * (360.0 / planetCount) + gameTime * (0.1 / (p + 1)));
            double planetRadius = 1.5 + p * 0.5 * progress;
            
            double planetX = center.x + Math.cos(orbitAngle) * orbitRadius;
            double planetZ = center.z + Math.sin(orbitAngle) * orbitRadius;
            double planetY = center.y;
            
            // 行星轮廓
            int planetPoints = (int)(20 * progress);
            for (int i = 0; i < planetPoints; i++) {
                double angle = Math.toRadians(i * (360.0 / planetPoints));
                
                double x = planetX + Math.cos(angle) * planetRadius;
                double z = planetZ + Math.sin(angle) * planetRadius;
                
                level.addParticle(ParticleTypes.END_ROD, 
                        x, planetY, z, 
                        0, 0, 0);
            }
            
            // 行星轨道
            int orbitPoints = (int)(60 * progress);
            for (int i = 0; i < orbitPoints; i++) {
                double angle = Math.toRadians(i * (360.0 / orbitPoints));
                
                double x = center.x + Math.cos(angle) * orbitRadius;
                double z = center.z + Math.sin(angle) * orbitRadius;
                
                level.addParticle(ParticleTypes.END_ROD, 
                        x, planetY, z, 
                        0, 0, 0);
            }
            
            // 为最大的行星添加"月亮"
            if (p == planetCount - 1 && progress > 0.8) {
                double moonRadius = planetRadius * 0.3;
                double moonOrbitRadius = planetRadius * 2;
                double moonAngle = gameTime * 0.2;
                
                double moonX = planetX + Math.cos(moonAngle) * moonOrbitRadius;
                double moonZ = planetZ + Math.sin(moonAngle) * moonOrbitRadius;
                
                // 月亮轮廓
                int moonPoints = (int)(12 * progress);
                for (int i = 0; i < moonPoints; i++) {
                    double angle = Math.toRadians(i * (360.0 / moonPoints));
                    
                    double x = moonX + Math.cos(angle) * moonRadius;
                    double z = moonZ + Math.sin(angle) * moonRadius;
                    
                    level.addParticle(ParticleTypes.END_ROD, 
                            x, planetY, z, 
                            0, 0, 0);
                }
            }
        }
    }

    /**
     * 生成脚底动态魔法符文
     */
    private void generateFootCircle(Level level, Vec3 basePos, float progress, long gameTime) {
        // 基本参数
        float size = 10.0F; // 固定更大的符文大小，不再随进度变化
        float rotation = gameTime * 0.008F; // 缓慢旋转
        float floatingAnim = (float) Math.sin(gameTime * 0.08) * 0.1F; // 上下浮动动画
        float footHeight = 0.15F + floatingAnim; // 基准高度
        
        // 获取动态时间数值用于动画
        float animTime = gameTime * 0.05F;
        float animCycle = (float) Math.sin(animTime);
        float animCycle2 = (float) Math.cos(animTime * 0.7F);
        
        Vec3 center = basePos.add(0, footHeight, 0);
        
        // 内部旋转圆环 - 增加圆环数量和粒子密度
        int innerCircleCount = 5; // 增加圆环数量
        for (int c = 0; c < innerCircleCount; c++) {
            float innerRadius = size * (0.15F + 0.15F * c);
            float innerRotation = rotation * (c % 2 == 0 ? 1 : -1.5F);
            
            // 粒子数随半径增加，大幅提升
            int particles = (int)(innerRadius * 40); // 加倍粒子密度
            
            // 主圆环
            float yOffset = footHeight + c * 0.06F;
            for (int i = 0; i < particles; i++) {
                double angle = Math.toRadians(i * (360.0 / particles) + innerRotation * 10);
                double x = center.x + Math.cos(angle) * innerRadius;
                double z = center.z + Math.sin(angle) * innerRadius;
                
                level.addParticle(ParticleTypes.END_ROD,
                        x, basePos.y + yOffset + Math.sin(angle * 6 + animTime) * 0.05F, z,
                        0, 0, 0);
                
                // 去掉圆环间的连接射线
            }
            
            // 添加圆环上的装饰符文
            if (c == 2 || c == 4) {
                int runeCount = c == 2 ? 4 : 8;
                for (int r = 0; r < runeCount; r++) {
                    double runeAngle = Math.toRadians(r * (360.0 / runeCount) + rotation * 5);
                    double runeX = center.x + Math.cos(runeAngle) * innerRadius;
                    double runeZ = center.z + Math.sin(runeAngle) * innerRadius;
                    
                    // 生成小型几何符文
                    generateDetailedRune(level, new Vec3(runeX, basePos.y + yOffset, runeZ), 
                            0.3F + 0.1F * c, runeAngle + gameTime * 0.001f);
                }
            }
        }
        
        // 内部复杂几何图案
        // 1. 六芒星(两个三角形) - 增加细节
        float starSize = size * 0.6F; // 调大六芒星的尺寸
        float starRotation1 = rotation * 1.2F;
        float starRotation2 = -rotation * 0.8F;
        
        // 第一个三角形
        Vec3[] triangle1 = new Vec3[3];
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(i * 120 + starRotation1 * 10);
            double x = center.x + Math.cos(angle) * starSize;
            double z = center.z + Math.sin(angle) * starSize;
            triangle1[i] = new Vec3(x, center.y, z);
        }
        
        // 第二个三角形
        Vec3[] triangle2 = new Vec3[3];
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(i * 120 + 60 + starRotation2 * 10);
            double x = center.x + Math.cos(angle) * starSize;
            double z = center.z + Math.sin(angle) * starSize;
            triangle2[i] = new Vec3(x, center.y, z);
        }
        
        // 绘制两个三角形形成六芒星 - 更高密度的线条
        for (int t = 0; t < 2; t++) {
            Vec3[] trianglePoints = t == 0 ? triangle1 : triangle2;
            for (int i = 0; i < 3; i++) {
                // 使用更高的点密度
                drawLine(level, trianglePoints[i], trianglePoints[(i + 1) % 3], 
                        (int)(trianglePoints[i].distanceTo(trianglePoints[(i + 1) % 3]) * 5.0));
            }
        }
        
        // 在六芒星的交叉点添加更精细的中心图案
        // 2. 中心符文 - 脉动圆环
        float centerPulse = 1.0F; // 固定大小，移除脉动效果
        for (int c = 0; c < 3; c++) { // 增加一层
            float runeRadius = size * 0.15F * (1 + c * 0.5F) * centerPulse;
            float runeYOffset = footHeight + 0.1F + c * 0.05F;
            
            int runeParticles = (int)(runeRadius * 40); // 增加中心符文的粒子密度
            for (int i = 0; i < runeParticles; i++) {
                double angle = Math.toRadians(i * (360.0 / runeParticles) + rotation * 15);
                double x = center.x + Math.cos(angle) * runeRadius;
                double z = center.z + Math.sin(angle) * runeRadius;
                
                level.addParticle(ParticleTypes.END_ROD,
                        x, basePos.y + runeYOffset, z,
                        0, 0, 0);
            }
        }
        
        // 3. 外环装饰光点 - 会围绕六芒星旋转
        int orbCount = 12; // 增加光点数量
        for (int i = 0; i < orbCount; i++) {
            double orbAngle = Math.toRadians(i * (360.0 / orbCount) + rotation * 20);
            double orbRadius = size * 0.7F;
            double x = center.x + Math.cos(orbAngle) * orbRadius;
            double z = center.z + Math.sin(orbAngle) * orbRadius;
            
            // 中心光点
            level.addParticle(ParticleTypes.END_ROD,
                    x, basePos.y + footHeight + 0.1F, z,
                    0, 0, 0);
            
            // 增加小光点的数量和精细度
            int miniOrbCount = 5; // 增加小光点数量
            float miniOrbRadius = 0.2F;
            for (int j = 0; j < miniOrbCount; j++) {
                double miniAngle = Math.toRadians(j * (360.0 / miniOrbCount) + gameTime * 0.2);
                double mx = x + Math.cos(miniAngle) * miniOrbRadius;
                double mz = z + Math.sin(miniAngle) * miniOrbRadius;
                
                level.addParticle(ParticleTypes.END_ROD,
                        mx, basePos.y + footHeight + 0.08F, mz,
                        0, 0, 0);
            }
            
            // 去掉光点与中心的连接线
        }
        
        // 5. 添加外部装饰环 - 全新元素
        generateDecorativeOuterRing(level, center, size, rotation, gameTime);
    }
    
    /**
     * 生成更详细的符文
     */
    private void generateDetailedRune(Level level, Vec3 center, float size, double rotation) {
        // 中心小圆
        int innerParticles = 10;
        for (int i = 0; i < innerParticles; i++) {
            double angle = Math.toRadians(i * (360.0 / innerParticles)) + rotation;
            double x = center.x + Math.cos(angle) * (size * 0.3);
            double z = center.z + Math.sin(angle) * (size * 0.3);
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x, center.y, z, 
                    0, 0, 0);
        }
        
        // 外环装饰圆点
        int outerPoints = 5;
        for (int i = 0; i < outerPoints; i++) {
            double angle = Math.toRadians(i * (360.0 / outerPoints)) + rotation;
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            
            level.addParticle(ParticleTypes.END_ROD, 
                    x, center.y, z, 
                    0, 0, 0);
            
            // 连线到中心
            Vec3 outer = new Vec3(x, center.y, z);
            drawLine(level, center, outer, 5);
        }
    }
    
    /**
     * 生成装饰性外环
     */
    private void generateDecorativeOuterRing(Level level, Vec3 center, float size, float rotation, long gameTime) {
        float outerRadius = size * 0.85F;
        float animTime = gameTime * 0.05F;
        
        // 主外环
        int particleCount = (int)(outerRadius * 50); // 非常高的粒子密度
        for (int i = 0; i < particleCount; i++) {
            double angle = Math.toRadians(i * (360.0 / particleCount) + rotation * 5);
            double x = center.x + Math.cos(angle) * outerRadius;
            double z = center.z + Math.sin(angle) * outerRadius;
            
            // 外环高度波动
            double heightVar = Math.sin(angle * 20 + gameTime * 0.1) * 0.05;
            
            level.addParticle(ParticleTypes.END_ROD,
                    x, center.y + heightVar, z,
                    0, 0, 0);
        }
        
        // 外环上的装饰图案
        int patternCount = 8;
        for (int i = 0; i < patternCount; i++) {
            double patternAngle = Math.toRadians(i * (360.0 / patternCount) + rotation * 3);
            double patternX = center.x + Math.cos(patternAngle) * outerRadius;
            double patternZ = center.z + Math.sin(patternAngle) * outerRadius;
            
            // 在外环上添加小型几何图案
            if (i % 2 == 0) {
                // 创建小三角形
                generateMiniTriangle(level, new Vec3(patternX, center.y, patternZ), 
                        size * 0.1F, patternAngle + gameTime * 0.003f);
            } else {
                // 创建小星形
                generateMiniStar(level, new Vec3(patternX, center.y, patternZ),
                        size * 0.12F, patternAngle);
            }
        }
        
        // 随机飘散的小粒子
        int floatingParticles = 40;
        for (int i = 0; i < floatingParticles; i++) {
            double angle = Math.toRadians(i * (360.0 / floatingParticles) + Math.sin(animTime + i) * 20);
            double dist = outerRadius * (0.85 + 0.15 * Math.sin(i + animTime));
            double x = center.x + Math.cos(angle) * dist;
            double z = center.z + Math.sin(angle) * dist;
            double y = center.y + Math.sin(i * 0.8 + animTime) * 0.08;
            
            level.addParticle(ParticleTypes.END_ROD,
                    x, y, z,
                    0, 0, 0);
        }
    }
    
    /**
     * 生成小型三角形
     */
    private void generateMiniTriangle(Level level, Vec3 center, float size, double rotation) {
        Vec3[] points = new Vec3[3];
        
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(i * 120 + rotation * 10);
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            points[i] = new Vec3(x, center.y, z);
        }
        
        for (int i = 0; i < 3; i++) {
            drawLine(level, points[i], points[(i + 1) % 3], 5);
        }
    }
    
    /**
     * 生成小型星形
     */
    private void generateMiniStar(Level level, Vec3 center, float size, double rotation) {
        int points = 5;
        double innerRadius = size * 0.4;
        
        for (int i = 0; i < points * 2; i++) {
            double radius = i % 2 == 0 ? size : innerRadius;
            double angle = Math.toRadians(i * (360.0 / (points * 2)) + rotation * 10);
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            
            Vec3 point = new Vec3(x, center.y, z);
            Vec3 nextPoint = new Vec3(
                center.x + Math.cos(Math.toRadians((i + 1) * (360.0 / (points * 2)) + rotation * 10)) * (i % 2 == 0 ? innerRadius : size),
                center.y,
                center.z + Math.sin(Math.toRadians((i + 1) * (360.0 / (points * 2)) + rotation * 10)) * (i % 2 == 0 ? innerRadius : size)
            );
            
            drawLine(level, point, nextPoint, 5);
        }
    }

    /**
     * 生成第5层外围的小型卫星魔法阵
     */
    private void generateSatelliteRunes(Level level, Vec3 center, float mainRadius, long gameTime, float progress) {
        int satelliteCount = 3; // 三个卫星魔法阵
        float animTime = gameTime * 0.05F;
        float fadeInProgress = Math.min(1.0F, progress * 2.0F - 0.3F); // 淡入效果
        
        for (int i = 0; i < satelliteCount; i++) {
            // 不同角度安排三个卫星魔法阵
            double angle = Math.toRadians(i * (360.0 / satelliteCount) + gameTime * 0.01);
            double distance = mainRadius * 1.5;
            double x = center.x + Math.cos(angle) * distance;
            double z = center.z + Math.sin(angle) * distance;
            
            float satelliteSize = 5.0F * fadeInProgress; // 魔法阵大小，随进度增大
            float satelliteRotation = gameTime * 0.02F * (i % 2 == 0 ? 1 : -1);
            
            // 生成动画效果
            float currentRadius = satelliteSize * Math.min(1.0F, (float)Math.sin(Math.max(0, fadeInProgress * Math.PI - Math.PI/2) + Math.PI/2));
            
            // 生成主环
            Vec3 satelliteCenter = new Vec3(x, center.y, z);
            int particles = (int)(currentRadius * 30);
            if (particles > 0) {
                for (int p = 0; p < particles; p++) {
                    double particleAngle = Math.toRadians(p * (360.0 / particles) + satelliteRotation * 10);
                    double px = x + Math.cos(particleAngle) * currentRadius;
                    double pz = z + Math.sin(particleAngle) * currentRadius;
                    
                    level.addParticle(ParticleTypes.END_ROD, px, center.y, pz, 0, 0, 0);
                }
                
                // 内环
                float innerRadius = currentRadius * 0.6F;
                int innerParticles = (int)(innerRadius * 25);
                for (int p = 0; p < innerParticles; p++) {
                    double particleAngle = Math.toRadians(p * (360.0 / innerParticles) - satelliteRotation * 15);
                    double px = x + Math.cos(particleAngle) * innerRadius;
                    double pz = z + Math.sin(particleAngle) * innerRadius;
                    
                    level.addParticle(ParticleTypes.END_ROD, px, center.y, pz, 0, 0, 0);
                }
                
                // 不同的中心图案，每个卫星魔法阵不同
                switch (i % 3) {
                    case 0: // 三角形
                        generateMiniTriangle(level, satelliteCenter, currentRadius * 0.4F, satelliteRotation * 3);
                        break;
                    case 1: // 方形
                        generateMiniSquare(level, satelliteCenter, currentRadius * 0.4F, satelliteRotation * 2);
                        break;
                    case 2: // 五角星
                        generateMiniStar(level, satelliteCenter, currentRadius * 0.4F, satelliteRotation * 2.5);
                        break;
                }
                
                // 连接到主魔法阵的光束
                if (fadeInProgress > 0.7F) {
                    Vec3 beamEnd = new Vec3(
                            center.x + Math.cos(angle) * mainRadius,
                            center.y,
                            center.z + Math.sin(angle) * mainRadius
                    );
                    
                    // 光束粒子，根据距离计算粒子数
                    drawLine(level, satelliteCenter, beamEnd, (int)(satelliteCenter.distanceTo(beamEnd) * 3));
                }
            }
        }
    }
    
    /**
     * 生成小型方块
     */
    private void generateMiniSquare(Level level, Vec3 center, float size, double rotation) {
        Vec3[] points = new Vec3[4];
        
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(i * 90 + rotation * 10);
            double x = center.x + Math.cos(angle) * size;
            double z = center.z + Math.sin(angle) * size;
            points[i] = new Vec3(x, center.y, z);
        }
        
        for (int i = 0; i < 4; i++) {
            drawLine(level, points[i], points[(i + 1) % 4], 5);
        }
    }
    
    /**
     * 为第9和第10层生成动态几何图案
     */
    private void generateAnimatedGeometry(Level level, Vec3 center, float radius, long gameTime, int layerIndex, float progress) {
        // 使用不同的旋转速度和方向
        float rotation = gameTime * (0.01F + layerIndex * 0.005F) * (layerIndex % 2 == 0 ? 1 : -1);
        
        // 生成动态效果，从中心向外扩散
        float animRadius = radius * progress;
        if (animRadius <= 0) return;
        
        // 主圆环，随动画进度扩大
        int mainParticles = (int)(animRadius * 20);
        for (int i = 0; i < mainParticles; i++) {
            double angle = Math.toRadians(i * (360.0 / mainParticles) + rotation * 10);
            double x = center.x + Math.cos(angle) * animRadius;
            double z = center.z + Math.sin(angle) * animRadius;
            
            level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
        }
        
        // 根据层数决定几何图案
        if (layerIndex == 8) { // 第9层，复杂六角形
            generateHexagonWithInnerLines(level, center, animRadius * 0.7F, rotation, progress);
        } else { // 第10层，星座图案
            generateConstellationPattern(level, center, animRadius * 0.8F, rotation, progress);
        }
        
        // 额外装饰元素
        if (progress > 0.6F) {
            int orbitCount = 3;
            for (int o = 0; o < orbitCount; o++) {
                float orbitSize = animRadius * (0.3F + o * 0.2F);
                float visProgress = Math.min(1.0F, (progress - 0.6F) * 5);
                
                int orbitParticles = (int)(orbitSize * 15 * visProgress);
                float orbitRotation = rotation * (1.5F + o * 0.5F) * (o % 2 == 0 ? 1 : -1);
                
                for (int i = 0; i < orbitParticles; i++) {
                    double angle = Math.toRadians(i * (360.0 / orbitParticles) + orbitRotation * 10);
                    double x = center.x + Math.cos(angle) * orbitSize;
                    double z = center.z + Math.sin(angle) * orbitSize;
                    
                    level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
                }
            }
        }
    }
    
    /**
     * 生成六角形及内部连线
     */
    private void generateHexagonWithInnerLines(Level level, Vec3 center, float radius, float rotation, float progress) {
        // 生成六角形顶点
        int sides = 6;
        Vec3[] vertices = new Vec3[sides];
        
        for (int i = 0; i < sides; i++) {
            double angle = Math.toRadians(i * (360.0 / sides) + rotation * 10);
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            vertices[i] = new Vec3(x, center.y, z);
        }
        
        // 绘制六角形外框
        for (int i = 0; i < sides; i++) {
            if (progress > (float)i / sides) {
                drawLine(level, vertices[i], vertices[(i + 1) % sides], (int)(vertices[i].distanceTo(vertices[(i + 1) % sides]) * 4));
            }
        }
        
        // 内部连线，从每个顶点到对面的顶点
        if (progress > 0.5F) {
            float innerProgress = Math.min(1.0F, (progress - 0.5F) * 2);
            for (int i = 0; i < sides / 2; i++) {
                if (innerProgress > (float)i / (sides / 2)) {
                    drawLine(level, vertices[i], vertices[i + sides / 2], (int)(vertices[i].distanceTo(vertices[i + sides / 2]) * 4 * innerProgress));
                }
            }
        }
    }
    
    /**
     * 生成星座图案
     */
    private void generateConstellationPattern(Level level, Vec3 center, float radius, float rotation, float progress) {
        // 星星节点坐标（相对位置）
        float[][] nodePositions = {
            {0.0F, 0.0F},   // 中心
            {0.8F, 0.0F},   // 右
            {0.5F, 0.7F},   // 右上
            {-0.3F, 0.7F},  // 左上
            {-0.8F, 0.1F},  // 左
            {-0.4F, -0.7F}, // 左下
            {0.4F, -0.6F}   // 右下
        };
        
        Vec3[] nodes = new Vec3[nodePositions.length];
        
        // 计算实际坐标并绘制星星节点
        for (int i = 0; i < nodePositions.length; i++) {
            // 旋转节点位置
            double angle = Math.atan2(nodePositions[i][1], nodePositions[i][0]) + Math.toRadians(rotation);
            double distance = Math.sqrt(nodePositions[i][0] * nodePositions[i][0] + nodePositions[i][1] * nodePositions[i][1]) * radius;
            
            double x = center.x + Math.cos(angle) * distance;
            double z = center.z + Math.sin(angle) * distance;
            nodes[i] = new Vec3(x, center.y, z);
            
            // 绘制星星节点
            float nodeSize = i == 0 ? 0.5F : 0.3F; // 中心星更大
            float nodeProgress = Math.min(1.0F, progress * 2 - 0.2F * i);
            if (nodeProgress > 0) {
                // 星星外环
                int particleCount = 8;
                for (int p = 0; p < particleCount; p++) {
                    double starAngle = Math.toRadians(p * (360.0 / particleCount));
                    double sx = x + Math.cos(starAngle) * nodeSize * nodeProgress;
                    double sz = z + Math.sin(starAngle) * nodeSize * nodeProgress;
                    level.addParticle(ParticleTypes.END_ROD, sx, center.y, sz, 0, 0, 0);
                }
                
                // 中心点
                level.addParticle(ParticleTypes.END_ROD, x, center.y, z, 0, 0, 0);
            }
        }
        
        // 连线定义（哪些点之间有连线）
        int[][] connections = {
            {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, // 中心到各点
            {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 1}  // 外围连线
        };
        
        // 绘制连线
        float lineProgress = Math.min(1.0F, progress * 1.5F - 0.2F);
        if (lineProgress > 0) {
            for (int i = 0; i < connections.length; i++) {
                if (lineProgress > (float)i / connections.length) {
                    int[] conn = connections[i];
                    Vec3 start = nodes[conn[0]];
                    Vec3 end = nodes[conn[1]];
                    
                    // 计算当前应绘制的线段长度
                    double totalDist = start.distanceTo(end);
                    double currentDist = totalDist * Math.min(1.0F, lineProgress * connections.length - i);
                    
                    if (currentDist > 0) {
                        Vec3 direction = new Vec3(
                            end.x - start.x,
                            end.y - start.y,
                            end.z - start.z
                        ).normalize();
                        
                        Vec3 currentEnd = new Vec3(
                            start.x + direction.x * currentDist,
                            start.y,
                            start.z + direction.z * currentDist
                        );
                        
                        drawLine(level, start, currentEnd, (int)(currentDist * 4));
                    }
                }
            }
        }
    }
}