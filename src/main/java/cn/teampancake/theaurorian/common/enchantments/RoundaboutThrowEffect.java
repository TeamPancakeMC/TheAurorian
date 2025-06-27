package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.entities.projectile.ThrownAxe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record RoundaboutThrowEffect(Unit unit) implements EnchantmentEntityEffect {

    public static final MapCodec<RoundaboutThrowEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Unit.CODEC.fieldOf("unit").forGetter(RoundaboutThrowEffect::unit)).apply(instance, RoundaboutThrowEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!level.isClientSide() && entity instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            Inventory inventory = player.getInventory();
            player.setItemInHand(hand, ItemStack.EMPTY);
            double baseDamage = player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
            double damage = 1.0F + baseDamage * 1.2F;
            int containerSize = inventory.getContainerSize();
            int slot = hand == InteractionHand.OFF_HAND ? containerSize - 1 : inventory.selected;
            ThrownAxe thrownAxe = new ThrownAxe(level, player);
            double y = player.position().y + player.getBbHeight() / 2.0F;
            thrownAxe.setPos(player.position().x, y, player.position().z);
            thrownAxe.setData((float) damage, player.getUUID(), slot);
            thrownAxe.setNoGravity(true);
            thrownAxe.setItem(item.itemStack());
            thrownAxe.shootFromRotation(
                    player, player.getXRot(), player.getYRot(),
                    0.0F, 1.5F, 0.0F);
            level.addFreshEntity(entity);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}