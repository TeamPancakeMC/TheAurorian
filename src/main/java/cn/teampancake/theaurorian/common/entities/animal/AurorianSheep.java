package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AurorianSheep extends Sheep {

    public AurorianSheep(EntityType<? extends AurorianSheep> type, Level level) {
        super(type, level);
    }

    @Nullable @Override
    public Sheep getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return TAEntityTypes.AURORIAN_SHEEP.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.LAVENDER.get());
    }

}