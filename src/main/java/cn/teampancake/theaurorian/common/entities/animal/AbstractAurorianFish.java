package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TAFluidTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class AbstractAurorianFish extends AbstractFish {

    public AbstractAurorianFish(EntityType<? extends AbstractAurorianFish> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != TAFluidTypes.MOON_WATER.get() || type != NeoForgeMod.WATER_TYPE;
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return type == TAFluidTypes.MOON_WATER.get() || super.canSwimInFluidType(type);
    }

}