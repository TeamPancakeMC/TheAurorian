package cn.teampancake.theaurorian.common.entities.ai;

import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CatFollowCatBellGoal extends TemptGoal {

    private final Cat cat;

    public CatFollowCatBellGoal(Cat cat) {
        super(cat, (0.6D), Ingredient.of(TAItems.CAT_BELL.get()), true);
        this.cat = cat;
    }

    @Override
    public boolean shouldFollow(@NotNull LivingEntity livingEntity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (this.items.test(livingEntity.getItemBySlot(slot))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.cat.isOrderedToSit();
    }

}