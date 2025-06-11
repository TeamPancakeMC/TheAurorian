package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class CatBell extends Item {

    private static final UUID CAT_BELL_LUCK_MODIFIER = UUID.fromString("107E580A-7C1F-4D25-9A84-8C037169F96B");
    private static final int BASE_DURATION = 200; // 10秒
    private static final int COOLDOWN = 300; // 15秒冷却
    private static final float REPEL_RADIUS = 8.0F; // 驱散敌对生物半径
    private static final double REPEL_STRENGTH = 0.5D; // 驱散力度

    public CatBell() {
        super(new Item.Properties()
                .durability(300) // 增加耐久到300
                .rarity(Rarity.EPIC) // 提升稀有度
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.HAS_CUSTOM_TOOLTIPS, TAItemTags.IS_MYTHICAL))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.DEVELOPER, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        
        // 添加冷却
        player.getCooldowns().addCooldown(this, COOLDOWN);
        
        // 播放猫铃铛音效
        level.playSound(player, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 0.8F, 1.5F);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.CAT_AMBIENT, SoundSource.PLAYERS, 0.6F, 1.2F);
        
        if (!level.isClientSide) {
            // 主动效果：速度和跳跃提升
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, BASE_DURATION, 2));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, BASE_DURATION, 1));
            
            // 添加夜视和轻功效果
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, BASE_DURATION, 0));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, BASE_DURATION, 0));
            
            // 短暂的力量提升
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, BASE_DURATION / 2, 1));
            
            // 驱散周围的敌对生物
            repelHostileMobs(level, player);
            
            // 在服务器端生成粒子效果
            spawnParticleEffects((ServerLevel) level, player);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        
        // 仅在非创造模式下消耗耐久
        if (!player.getAbilities().instabuild) {
            itemInHand.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }
    
    /**
     * 每个tick检查物品效果
     */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }
        
        // 检查猫铃铛是否在玩家物品栏中
        boolean hasCatBell = player.getInventory().contains(stack);
        
        // 被动效果：每20tick应用幸运效果
        if (hasCatBell && level.getGameTime() % 20 == 0) {
            // 添加幸运效果
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 25, 0, true, false));
            
            // 在夜晚提供夜视效果
            if (level.isNight() && !player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, false));
            }
            
            // 用直接的游戏效果代替属性修饰符
            // 这样可以避免使用可能不兼容的属性API
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, false));
        }
        
        // 当玩家主手或副手持有猫铃铛时，每30tick生成粒子效果
        boolean holdingCatBell = player.getItemInHand(InteractionHand.MAIN_HAND) == stack || 
                                 player.getItemInHand(InteractionHand.OFF_HAND) == stack;
        
        if (holdingCatBell && level.getGameTime() % 30 == 0) {
            // 在玩家周围生成小粒子效果
            Vec3 position = player.position().add(0, 1.0, 0);
            ((ServerLevel) level).sendParticles(
                    ParticleTypes.NOTE,
                    position.x + (level.random.nextDouble() - 0.5) * 0.5,
                    position.y + (level.random.nextDouble() - 0.5) * 0.5,
                    position.z + (level.random.nextDouble() - 0.5) * 0.5,
                    1, 0, 0, 0, 0.1
            );
        }
    }
    
    /**
     * 驱散周围的敌对生物
     */
    private void repelHostileMobs(Level level, Player player) {
        AABB areaOfEffect = player.getBoundingBox().inflate(REPEL_RADIUS);
        List<Monster> nearbyMonsters = level.getEntitiesOfClass(Monster.class, areaOfEffect);
        
        for (Monster monster : nearbyMonsters) {
            // 计算推力方向 - 从玩家指向怪物
            Vec3 pushDirection = monster.position().subtract(player.position()).normalize();
            
            // 应用推力
            monster.setDeltaMovement(monster.getDeltaMovement().add(
                    pushDirection.x * REPEL_STRENGTH,
                    0.3, // 轻微向上推力
                    pushDirection.z * REPEL_STRENGTH
            ));
            
            // 让怪物短暂眩晕（减速效果）
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            
            // 发光效果，使其更容易被看见
            monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
        }
    }
    
    /**
     * 生成粒子效果
     */
    private void spawnParticleEffects(ServerLevel level, Player player) {
        RandomSource random = level.getRandom();
        Vec3 position = player.position().add(0, 1.0, 0);
        
        // 在玩家周围生成螺旋粒子效果
        for (int i = 0; i < 3; i++) { // 3圈螺旋
            for (int j = 0; j < 20; j++) { // 每圈20个粒子
                double angle = j * Math.PI * 2 / 20;
                double radius = 1.0 + i * 0.3; // 逐渐增大的半径
                double offsetY = j * 0.05; // 螺旋上升
                
                Vec3 particlePos = position.add(
                        Math.cos(angle) * radius,
                        offsetY,
                        Math.sin(angle) * radius
                );
                
                // 紫色魔法粒子
                level.sendParticles(
                        TAParticleTypes.MAGIC_PURPLE.get(),
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0, 0, 0, 0.05
                );
                
                // 偶尔添加一些音符粒子
                if (random.nextInt(5) == 0) {
                    level.sendParticles(
                            ParticleTypes.NOTE,
                            particlePos.x, particlePos.y + 0.5, particlePos.z,
                            1, 0, 0, 0, 1.0
                    );
                }
            }
        }
        
        // 在玩家脚下生成圆形波纹
        for (int i = 0; i < 36; i++) {
            double angle = i * Math.PI * 2 / 36;
            double radius = 2.0;
            
            Vec3 particlePos = position.add(
                    Math.cos(angle) * radius,
                    -0.5, // 脚下一点
                    Math.sin(angle) * radius
            );
            
            // 末地烛粒子
            level.sendParticles(
                    ParticleTypes.END_ROD,
                    particlePos.x, particlePos.y, particlePos.z,
                    1, 0, 0, 0, 0.05
            );
        }
    }
    
    /**
     * 获得更好的修复
     */
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return repairItem.is(net.minecraft.world.item.Items.GOLD_INGOT) || super.isValidRepairItem(stack, repairItem);
    }
}