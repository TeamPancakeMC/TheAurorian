package cn.teampancake.theaurorian.common.items.tool.moonsilver;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.AttackSpeedData;
import cn.teampancake.theaurorian.common.items.tool.GeoHandheldToolRenderer;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class MoonsilverScythe extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonsilverScythe(Properties properties) {
        super(TAToolTiers.MOONSILVER, properties.rarity(Rarity.RARE));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoHandheldToolRenderer<MoonsilverScythe>(TAItems.MOONSILVER_SCYTHE.getId()));
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        if (attacker instanceof Player player) {
            this.updateAttackSpeed(stack, player.level(), true);
            this.applyDynamicAttackSpeed(player, stack);
        }
    }

    public void updateAttackSpeed(ItemStack stack, Level level, boolean successfulHit) {
        AttackSpeedData currentData = this.getCurrentAttackData(stack);
        AttackSpeedData newData;
        long currentTime = level.getGameTime();
        if (successfulHit) {
            newData = currentData.withNewAttack(currentTime);
        } else {
            long timeSinceLastAttack = currentTime - currentData.lastAttackTime();
            newData = timeSinceLastAttack >= 60 ? currentData.withDecayedSpeed() : currentData;
        }

        if (!newData.equals(currentData)) {
            stack.set(TADataComponents.WEAPON_SPEED_DATA, newData);
        }
    }

    public void applyDynamicAttackSpeed(Player player, ItemStack weapon) {
        float currentSpeed = this.getCurrentAttackData(weapon).currentSpeed();
        AttributeInstance instance = player.getAttribute(Attributes.ATTACK_SPEED);
        player.getAttributes().removeAttributeModifiers(this.createDynamicAttributeMap());
        if (currentSpeed > 0.5f && instance != null) {
            AttributeModifier dynamicModifier = new AttributeModifier(
                    TheAurorian.prefix("ms_scythe_dynamic_speed"),
                    currentSpeed - 0.5F, AttributeModifier.Operation.ADD_VALUE);
            instance.addTransientModifier(dynamicModifier);
        }
    }

    private AttackSpeedData getCurrentAttackData(ItemStack stack) {
        return stack.getOrDefault(TADataComponents.WEAPON_SPEED_DATA, AttackSpeedData.createInitial());
    }

    private Multimap<Holder<Attribute>, AttributeModifier> createDynamicAttributeMap() {
        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                TheAurorian.prefix("ms_scythe_dynamic_speed"),
                0, AttributeModifier.Operation.ADD_VALUE));
        return map;
    }

}