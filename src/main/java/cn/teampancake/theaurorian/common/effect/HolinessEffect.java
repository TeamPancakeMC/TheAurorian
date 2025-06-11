package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class HolinessEffect extends MobEffect {

    private static final double ATTACK_BOOST_AMOUNT = 0.1D; // 10%攻击力提升
    private static final double SPEED_BOOST_AMOUNT = 0.05D; // 5%速度提升
    
    // 粒子效果配置
    private static final int PARTICLE_UPDATE_INTERVAL = 3; // 每3tick更新一次粒子效果（更频繁）
    private static final float VERTICAL_RING_RADIUS = 0.6F; // 垂直环半径
    private static final int BASE_PARTICLE_COUNT = 12; // 基础粒子数量
    private static final float RING_HEIGHT_OFFSET = 1.0F; // 环绕高度偏移量
    
    // 魔法阵配置
    private static final float MAGIC_CIRCLE_RADIUS = 1.8F; // 魔法阵半径（更大）
    private static final float MAGIC_CIRCLE_HEIGHT = 0.05F; // 魔法阵高度（更贴地）
    private static final int MAGIC_CIRCLE_OUTER_POINTS = 12; // 魔法阵外圈点数（更多）
    private static final int MAGIC_CIRCLE_MIDDLE_POINTS = 8; // 魔法阵中圈点数
    private static final int MAGIC_CIRCLE_INNER_POINTS = 5; // 魔法阵内部星形点数
    private static final float ROTATION_SPEED_OUTER = 0.7F; // 外圈旋转速度
    private static final float ROTATION_SPEED_MIDDLE = -0.5F; // 中圈旋转速度（反向）
    private static final float ROTATION_SPEED_INNER = 1.2F; // 内圈旋转速度
    
    // 粒子颜色
    private static final Vector3f GOLD_COLOR = new Vector3f(1.0F, 0.9F, 0.5F); // 金色
    private static final Vector3f GOLD_BRIGHT = new Vector3f(1.0F, 0.95F, 0.7F); // 亮金色
    private static final Vector3f WHITE_COLOR = new Vector3f(1.0F, 1.0F, 1.0F); // 白色
    private static final Vector3f WHITE_BLUE = new Vector3f(0.9F, 0.95F, 1.0F); // 带蓝的白色

    public HolinessEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffffeb);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
            TheAurorian.prefix("holiness_attack_boost"), ATTACK_BOOST_AMOUNT,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
            TheAurorian.prefix("holiness_speed_boost"), SPEED_BOOST_AMOUNT,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        // 清除所有负面效果
        livingEntity.getActiveEffectsMap().values().stream()
                .map(MobEffectInstance::getEffect)
                .filter(holder -> !holder.value().isBeneficial())
                .forEach(livingEntity::removeEffect);

        // 产生初始圣洁光环粒子效果（爆发效果）
        this.spawnInitialHolinessParticles(livingEntity);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.tickCount % PARTICLE_UPDATE_INTERVAL == 0) {
            spawnHolinessParticles(livingEntity, amplifier);
        }

        return true;
    }
    
    /**
     * 产生初始圣洁光环粒子效果（爆发效果）
     */
    private void spawnInitialHolinessParticles(LivingEntity entity) {
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }
        
        ServerLevel serverLevel = (ServerLevel) level;
        double x = entity.getX();
        double y = entity.getY() + entity.getBbHeight() * 0.5 + RING_HEIGHT_OFFSET;
        double z = entity.getZ();
        
        // 爆发效果 - 大量粒子向外扩散
        for (int i = 0; i < 60; i++) {
            double offsetX = (entity.getRandom().nextDouble() - 0.5) * 2.0;
            double offsetY = (entity.getRandom().nextDouble() - 0.5) * 2.0;
            double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 2.0;
            double speed = 0.15 + entity.getRandom().nextDouble() * 0.15;
            
            // 末地烛光粒子 - 白色光芒
            serverLevel.sendParticles(ParticleTypes.END_ROD, x, y, z,
                1, offsetX, offsetY, offsetZ, speed);
        }
        
        // 金色尘埃粒子 - 形成爆发中心
        for (int i = 0; i < 40; i++) {
            double offsetX = (entity.getRandom().nextDouble() - 0.5);
            double offsetY = (entity.getRandom().nextDouble() - 0.5);
            double offsetZ = (entity.getRandom().nextDouble() - 0.5);
            serverLevel.sendParticles(new DustParticleOptions(GOLD_COLOR, 1.0F),
                    x, y, z, 1, offsetX, offsetY, offsetZ, 0.1);
        }
        
        // 初始魔法阵 - 快速展开效果
        double baseY = entity.getY() + MAGIC_CIRCLE_HEIGHT;
        for (int r = 0; r < 10; r++) {
            float radius = r * MAGIC_CIRCLE_RADIUS / 10.0F;
            int points = 12;
            for (int i = 0; i < points; i++) {
                double angle = 2.0 * Math.PI * i / points;
                double particleX = x + radius * Math.cos(angle);
                double particleZ = z + radius * Math.sin(angle);
                
                // 交替使用金色和白色
                Vector3f color = (i % 2 == 0) ? GOLD_COLOR : WHITE_COLOR;
                float size = 0.7F + (r / 10.0F) * 0.3F;
                serverLevel.sendParticles(new DustParticleOptions(color, size),
                        particleX, baseY, particleZ, 1,
                        0.0, 0.0, 0.0, 0.0);
            }
        }
    }
    
    /**
     * 产生持续的圣洁光环粒子效果
     */
    private void spawnHolinessParticles(LivingEntity entity, int amplifier) {
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }
        
        ServerLevel serverLevel = (ServerLevel) level;
        
        // 生成魔法阵
        spawnEnhancedMagicCircle(serverLevel, entity, entity.tickCount);
        
        // 增加效果等级会增加粒子数量和复杂度
        int particleMultiplier = amplifier + 1;
        int particleCount = BASE_PARTICLE_COUNT * particleMultiplier;
        
        // 垂直光环 - 旋转效果
        double rotationAngle = (entity.tickCount % 360) * Math.PI / 180.0;
        spawnVerticalRingParticles(serverLevel, entity, VERTICAL_RING_RADIUS, rotationAngle, particleCount / 2);
        
        // 随机闪烁粒子（稍微降低频率，不在头部区域生成）
        if (entity.getRandom().nextInt(4) == 0) {
            double randomX = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 1.5;
            // 在肩部以下生成随机粒子，避免遮挡视野
            double maxHeight = entity.getY() + entity.getBbHeight() * 0.6 + RING_HEIGHT_OFFSET;
            double minHeight = entity.getY() + RING_HEIGHT_OFFSET * 0.5;
            double randomY = minHeight + entity.getRandom().nextDouble() * (maxHeight - minHeight);
            double randomZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 1.5;
            serverLevel.sendParticles(new DustParticleOptions(WHITE_COLOR, 0.7F),
                randomX, randomY, randomZ, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
    
    /**
     * 生成增强版魔法阵
     */
    private void spawnEnhancedMagicCircle(ServerLevel level, LivingEntity entity, int tickCount) {
        double entityX = entity.getX();
        double entityY = entity.getY() + MAGIC_CIRCLE_HEIGHT;
        double entityZ = entity.getZ();
        
        // 计算旋转角度 - 三层不同速度旋转
        double outerRotation = (tickCount * ROTATION_SPEED_OUTER) % 360 * Math.PI / 180.0;
        double middleRotation = (tickCount * ROTATION_SPEED_MIDDLE) % 360 * Math.PI / 180.0;
        double innerRotation = (tickCount * ROTATION_SPEED_INNER) % 360 * Math.PI / 180.0;
        
        // 生成魔法阵外圈
        List<Vector3f> outerPoints = generateCirclePoints(MAGIC_CIRCLE_OUTER_POINTS, MAGIC_CIRCLE_RADIUS, outerRotation);
        for (int i = 0; i < outerPoints.size(); i++) {
            Vector3f point = outerPoints.get(i);
            Vector3f nextPoint = outerPoints.get((i + 1) % outerPoints.size());
            
            // 生成外圈连接线
            spawnLineBetweenPoints(level,
                    entityX + point.x(), entityY, entityZ + point.z(),
                    entityX + nextPoint.x(), entityY, entityZ + nextPoint.z(),
                    6, WHITE_COLOR, 0.8f);
            
            // 在每个点上添加金色亮点
            level.sendParticles(new DustParticleOptions(GOLD_BRIGHT, 1.0f),
                entityX + point.x(), entityY, entityZ + point.z(),
                1, 0.0, 0.0, 0.0, 0.0);
        }
        
        // 生成魔法阵中圈
        List<Vector3f> middlePoints = generateCirclePoints(MAGIC_CIRCLE_MIDDLE_POINTS, MAGIC_CIRCLE_RADIUS * 0.7f, middleRotation);
        for (int i = 0; i < middlePoints.size(); i++) {
            Vector3f point = middlePoints.get(i);
            Vector3f nextPoint = middlePoints.get((i + 1) % middlePoints.size());
            
            // 生成中圈连接线
            spawnLineBetweenPoints(level, 
                entityX + point.x(), entityY, entityZ + point.z(),
                entityX + nextPoint.x(), entityY, entityZ + nextPoint.z(),
                5, GOLD_COLOR, 0.7f);
        }
        
        // 生成魔法阵内圈（五角星）
        List<Vector3f> innerPoints = generateStarPoints(MAGIC_CIRCLE_INNER_POINTS, MAGIC_CIRCLE_RADIUS * 0.4f, innerRotation);
        for (int i = 0; i < innerPoints.size(); i++) {
            Vector3f point = innerPoints.get(i);
            // 五角星连接方式：0-2-4-1-3-0
            int nextIdx = (i * 2) % innerPoints.size();
            Vector3f nextPoint = innerPoints.get(nextIdx);
            
            // 生成内圈连接线
            spawnLineBetweenPoints(level, 
                entityX + point.x(), entityY, entityZ + point.z(),
                entityX + nextPoint.x(), entityY, entityZ + nextPoint.z(),
                4, WHITE_BLUE, 0.7f);
            
            // 在每个星角添加金色亮点
            level.sendParticles(new DustParticleOptions(GOLD_BRIGHT, 1.0f),
                entityX + point.x(), entityY, entityZ + point.z(),
                1, 0.0, 0.0, 0.0, 0.0);
        }
        
        // 生成中心点
        level.sendParticles(new DustParticleOptions(GOLD_BRIGHT, 1.2f),
            entityX, entityY, entityZ, 1, 0.0, 0.0, 0.0, 0.0);
        
        // 生成魔法阵中心符文（小圆环）
        List<Vector3f> centerPoints = generateCirclePoints(8, MAGIC_CIRCLE_RADIUS * 0.2f, outerRotation * 2);
        for (Vector3f point : centerPoints) {
            level.sendParticles(new DustParticleOptions(WHITE_COLOR, 0.5f),
                entityX + point.x(), entityY, entityZ + point.z(),
                1, 0.0, 0.0, 0.0, 0.0);
        }
        
        // 添加旋转光束效果
        int beamCount = 2;
        double beamRotation = (tickCount * 2.0) % 360 * Math.PI / 180.0;
        for (int i = 0; i < beamCount; i++) {
            double beamAngle = beamRotation + (2.0 * Math.PI * i / beamCount);
            double beamX = entityX + MAGIC_CIRCLE_RADIUS * 0.5 * Math.cos(beamAngle);
            double beamZ = entityZ + MAGIC_CIRCLE_RADIUS * 0.5 * Math.sin(beamAngle);
            
            // 向上的光束
            for (int h = 0; h < 5; h++) {
                double beamHeight = entityY + 0.1 + (h * 0.2);
                level.sendParticles(ParticleTypes.END_ROD, beamX, beamHeight, beamZ,
                    1, 0.0, 0.05, 0.0, 0.01);
            }
        }
        
        // 添加外圈光环效果
        if (tickCount % 20 < 10) {
            double pulseScale = 1.0 + 0.05 * Math.sin(tickCount * 0.1);
            List<Vector3f> pulsePoints = generateCirclePoints(24, (float)(MAGIC_CIRCLE_RADIUS * pulseScale), 0);
            for (Vector3f point : pulsePoints) {
                if (entity.getRandom().nextInt(3) == 0) { // 随机跳过一些点，创造不均匀效果
                    level.sendParticles(
                        new DustParticleOptions(GOLD_COLOR, 0.4f),
                        entityX + point.x(), entityY, entityZ + point.z(),
                        1, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
    }
    
    /**
     * 生成两点之间的粒子线
     */
    private void spawnLineBetweenPoints(ServerLevel level, double x1, double y1, double z1, 
                                      double x2, double y2, double z2, 
                                      int particleCount, Vector3f color, float size) {
        for (int i = 0; i <= particleCount; i++) {
            double ratio = (double) i / particleCount;
            double x = x1 + (x2 - x1) * ratio;
            double y = y1 + (y2 - y1) * ratio;
            double z = z1 + (z2 - z1) * ratio;
            level.sendParticles(
                new DustParticleOptions(color, size), x, y, z,
                    1, 0.0, 0.0, 0.0, 0.0);
        }
    }
    
    /**
     * 生成圆形上的点
     */
    private List<Vector3f> generateCirclePoints(int pointCount, float radius, double rotation) {
        List<Vector3f> points = new ArrayList<>();
        for (int i = 0; i < pointCount; i++) {
            double angle = 2.0 * Math.PI * i / pointCount + rotation;
            float x = (float)(radius * Math.cos(angle));
            float z = (float)(radius * Math.sin(angle));
            points.add(new Vector3f(x, 0, z));
        }
        return points;
    }
    
    /**
     * 生成星形上的点
     */
    private List<Vector3f> generateStarPoints(int pointCount, float radius, double rotation) {
        List<Vector3f> points = new ArrayList<>();
        for (int i = 0; i < pointCount; i++) {
            double angle = 2.0 * Math.PI * i / pointCount + rotation;
            float x = (float)(radius * Math.cos(angle));
            float z = (float)(radius * Math.sin(angle));
            points.add(new Vector3f(x, 0, z));
        }
        return points;
    }
    
    /**
     * 生成垂直环形粒子（绕X或Z轴旋转）
     */
    private void spawnVerticalRingParticles(ServerLevel level, LivingEntity entity, float radius, double rotationAngle, int count) {
        double entityX = entity.getX();
        // 提高垂直环的位置，使其位于实体上方
        double entityY = entity.getY() + entity.getBbHeight() * 0.5 + RING_HEIGHT_OFFSET;
        double entityZ = entity.getZ();
        
        for (int i = 0; i < count; i++) {
            double angle = 2.0 * Math.PI * i / count;
            
            // 计算在垂直环上的位置（先假设绕Z轴）
            double offsetX = radius * Math.cos(angle);
            double offsetY = radius * Math.sin(angle);
            
            // 应用旋转（绕Y轴）
            double finalX = offsetX * Math.cos(rotationAngle) - 0 * Math.sin(rotationAngle);
            double finalZ = offsetX * Math.sin(rotationAngle) + 0 * Math.cos(rotationAngle);
            
            double particleX = entityX + finalX;
            double particleY = entityY + offsetY;
            double particleZ = entityZ + finalZ;
            
            // 交替使用两种粒子
            if (i % 2 == 0) {
                level.sendParticles(ParticleTypes.END_ROD, particleX, particleY, particleZ,
                    1, 0.01, 0.01, 0.01, 0.01);
            } else {
                level.sendParticles(new DustParticleOptions(GOLD_COLOR, 0.8F),
                    particleX, particleY, particleZ,
                    1, 0.01, 0.01, 0.01, 0.0);
            }
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}