package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CatBell extends Item {

    private static final int BASE_DURATION = 200;
    private static final float REPEL_RADIUS = 8.0F;
    private static final double REPEL_STRENGTH = 0.5D;

    public CatBell() {
        super(new Item.Properties().durability(300).rarity(Rarity.EPIC)
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.CAT_BELL)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.DEVELOPER, Unit.INSTANCE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(this, 300);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 0.8F, 1.5F);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.CAT_AMBIENT, SoundSource.PLAYERS, 0.6F, 1.2F);
        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, BASE_DURATION, 2));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, BASE_DURATION, 1));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, BASE_DURATION, 0));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, BASE_DURATION, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, BASE_DURATION / 2, 1));
            this.repelHostileMobs(level, player);
            if (level instanceof ServerLevel serverLevel) {
                this.spawnParticleEffects(serverLevel, player);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemInHand.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        boolean hasCatBell = player.getInventory().contains(stack);
        if (hasCatBell && level.getGameTime() % 20 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 25));
            if (level.isNight() && !player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220));
            }

            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40));
        }

        boolean holdingCatBell = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
        if (level instanceof ServerLevel serverLevel && holdingCatBell && level.getGameTime() % 30 == 0) {
            Vec3 position = player.position().add(0, 1.0, 0);
            serverLevel.sendParticles(ParticleTypes.NOTE,
                    position.x + (level.random.nextDouble() - 0.5) * 0.5,
                    position.y + (level.random.nextDouble() - 0.5) * 0.5,
                    position.z + (level.random.nextDouble() - 0.5) * 0.5,
                    1, 0, 0, 0, 0.1);
        }
    }

    private void repelHostileMobs(Level level, Player player) {
        AABB areaOfEffect = player.getBoundingBox().inflate(REPEL_RADIUS);
        for (Monster monster : level.getEntitiesOfClass(Monster.class, areaOfEffect)) {
            Vec3 pushDirection = monster.position().subtract(player.position()).normalize();
            monster.setDeltaMovement(monster.getDeltaMovement().add(
                    pushDirection.x * REPEL_STRENGTH, 0.3, pushDirection.z * REPEL_STRENGTH));
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
        }
    }

    private void spawnParticleEffects(ServerLevel level, Player player) {
        RandomSource random = level.getRandom();
        Vec3 position = player.position().add(0, 1.0, 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                double angle = j * Math.PI * 2 / 20;
                double radius = 1.0 + i * 0.3;
                double offsetY = j * 0.05;
                Vec3 particlePos = position.add(Math.cos(angle) * radius, offsetY, Math.sin(angle) * radius);
                level.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                        particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0.05);
                if (random.nextInt(5) == 0) {
                    level.sendParticles(ParticleTypes.NOTE,
                            particlePos.x, particlePos.y + 0.5, particlePos.z,
                            1, 0, 0, 0, 1.0);
                }
            }
        }

        for (int i = 0; i < 36; i++) {
            double angle = i * Math.PI * 2 / 36;
            double radius = 2.0;
            Vec3 particlePos = position.add(Math.cos(angle) * radius, -0.5, Math.sin(angle) * radius);
            level.sendParticles(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z,
                    1, 0, 0, 0, 0.05);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return repairItem.is(Items.GOLD_INGOT) || super.isValidRepairItem(stack, repairItem);
    }

}