package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 极光钢剑 - 具有圣洁效果的特殊武器
 * 右键激活圣洁效果，对敌人造成额外伤害，击杀敌人可延长效果持续时间
 */
public class AurorianSteelSword extends SwordItem {

    // 常量定义，便于调整参数
    private static final int COOLDOWN_TICKS = 400; // 技能冷却时间（20秒）
    private static final int ABSORPTION_DURATION = 600; // 伤害吸收效果持续时间（30秒）
    private static final int ABSORPTION_AMPLIFIER = 1; // 伤害吸收效果等级
    private static final int HOLINESS_DURATION = 200; // 圣洁效果基础持续时间（10秒）
    private static final int HOLINESS_EXTEND_NORMAL = 30; // 击杀普通生物延长圣洁效果时间（1.5秒）
    private static final int HOLINESS_EXTEND_UNDEAD = 60; // 击杀亡灵生物延长圣洁效果时间（3秒）
    private static final float DAMAGE_BONUS_NORMAL = 1.0F; // 对普通生物额外伤害
    private static final float DAMAGE_BONUS_UNDEAD = 2.0F; // 对亡灵生物额外伤害
    private static final String EASTER_EGG_PLAYER = "sxuuz"; // 彩蛋玩家ID
    private static final float EASTER_EGG_DAMAGE = 1202.0F; // 彩蛋伤害值

    public AurorianSteelSword() {
        super(TAToolTiers.AURORIAN_STEEL, new Item.Properties()
                .attributes(createAttributes(TAToolTiers.AURORIAN_STEEL, (3), (-2.4F)))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS, TAItemTags.IS_EPIC))
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    /**
     * 攻击敌人时的特殊效果处理
     * 当玩家拥有圣洁效果时，会造成额外伤害并可能延长效果持续时间
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 检查攻击者是否有圣洁效果
        if (attacker instanceof Player player) {
            // 彩蛋效果：检查玩家ID是否为特定值
            if (isEasterEggPlayer(player)) {
                // 对目标造成超高伤害
                target.hurt(target.damageSources().playerAttack(player), EASTER_EGG_DAMAGE);
                // 显示特殊消息
                if (!player.level().isClientSide()) {
                    player.displayClientMessage(Component.literal("§duuz大人把你吃掉啦"), true);
                }
            } else if (hasHolinessEffect(player)) {
                // 应用额外伤害
                applyExtraDamage(target, player);
                
                // 只有当目标生物被击杀时才延长圣洁效果
                if (target.isDeadOrDying()) {
                    extendHolinessEffect(player, isUndead(target));
                }
            }
        }

        // 武器耐久损耗
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    /**
     * 检查玩家是否为彩蛋玩家
     */
    private boolean isEasterEggPlayer(Player player) {
        return player.getName().getString().equalsIgnoreCase(EASTER_EGG_PLAYER);
    }

    /**
     * 对目标造成额外伤害
     * 亡灵生物受到更多伤害
     */
    private void applyExtraDamage(LivingEntity target, Player player) {
        // 对亡灵生物额外+2伤害，其他生物+1伤害
        float extraDamage = isUndead(target) ? DAMAGE_BONUS_UNDEAD : DAMAGE_BONUS_NORMAL;
        target.hurt(target.damageSources().playerAttack(player), extraDamage);
    }
    
    /**
     * 延长圣洁效果持续时间
     * 击杀亡灵生物延长更多时间
     */
    private void extendHolinessEffect(Player player, boolean isUndead) {
        // 获取当前圣洁效果并延长持续时间
        MobEffectInstance currentEffect = player.getEffect(TAMobEffects.HOLINESS);
        if (currentEffect != null) {
            // 击杀亡灵生物延长更多时间
            int extraDuration = isUndead ? HOLINESS_EXTEND_UNDEAD : HOLINESS_EXTEND_NORMAL;
            player.addEffect(new MobEffectInstance(
                TAMobEffects.HOLINESS, 
                currentEffect.getDuration() + extraDuration, 
                currentEffect.getAmplifier()
            ));
        }
    }

    /**
     * 检查玩家是否拥有圣洁效果
     */
    private boolean hasHolinessEffect(Player player) {
        return player.hasEffect(TAMobEffects.HOLINESS);
    }

    /**
     * 判断实体是否为亡灵生物
     */
    private boolean isUndead(LivingEntity entity) {
        return entity.getType().is(EntityTypeTags.UNDEAD);
    }

    /**
     * 右键使用武器激活圣洁效果
     * 获得伤害吸收和圣洁效果，并进入冷却
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        
        // 彩蛋效果：检查玩家ID
        if (isEasterEggPlayer(player) && !level.isClientSide()) {
            player.displayClientMessage(Component.literal("§duuz大人又开始幻想了"), false);
            // 彩蛋玩家不受冷却限制
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ABSORPTION_DURATION * 2, ABSORPTION_AMPLIFIER * 2));
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, HOLINESS_DURATION * 2));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        }
        
        // 添加技能冷却
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        
        if (!level.isClientSide) {
            // 添加效果
            // 使用原版效果 - 伤害吸收
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ABSORPTION_DURATION, ABSORPTION_AMPLIFIER));
            // 使用模组效果 - 圣洁效果
            player.addEffect(new MobEffectInstance(TAMobEffects.HOLINESS, HOLINESS_DURATION));
            
            // 向玩家发送激活消息
            if (player instanceof ServerPlayer serverPlayer) {
                String messageKey = "messages." + this.getDescriptionId() + ".holiness";
                serverPlayer.sendSystemMessage(Component.translatable(messageKey));
            }
        }

        // 记录物品使用统计
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }
}