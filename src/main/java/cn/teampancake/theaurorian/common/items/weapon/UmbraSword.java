package cn.teampancake.theaurorian.common.items.weapon;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.entities.monster.TASpider;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UmbraSword extends SwordItem {

    public UmbraSword() {
        super(TAToolTiers.UMBRA, new Item.Properties().rarity(Rarity.EPIC)
                .attributes(createAttributes(TAToolTiers.UMBRA, 7, 1.6F))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.SWORDS))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE)
                .component(TADataComponents.NO_RUN_DATA, Unit.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(this, 1200);
        for (int i = 0; i < 2; i++) {
            Entity entity = this.getRandomSpiderlings(level);
            if (!level.isClientSide && entity instanceof TASpider spider) {
                spider.setOwnerUUID(player.getUUID());
                level.addFreshEntity(entity);
            }
        }

        return InteractionResultHolder.success(itemInHand);
    }

    @Nullable
    public Entity getRandomSpiderlings(Level level) {
        List<Holder<EntityType<?>>> list = new ArrayList<>();
        BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(TAEntityTags.SPIDERLING).forEach(list::add);
        if (list.isEmpty()) return null;
        int index = level.random.nextInt(list.size());
        Entity entity = list.get(index).value().create(level);
        if (entity instanceof TASpider spider) {
            spider.summonByPlayer = true;
            spider.aliveTime = 1800;
            Holder<MobEffect> effect = new Spider.SpiderEffectsGroupData().effect;
            if (effect != null && level.random.nextFloat() <= 0.25F) {
                spider.addEffect(new MobEffectInstance(effect, -1));
            }
        }

        return entity;
    }

}