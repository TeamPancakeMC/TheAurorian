package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.registry.TAFluidTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;

public abstract class AbstractAurorianFish extends AbstractFish {
    public AbstractAurorianFish(EntityType<? extends AbstractFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if(type== TAFluidTypes.MOON_WATER.get())
            return false;
        return super.canDrownInFluidType(type);
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        if(type== TAFluidTypes.MOON_WATER.get())
            return true;
        return super.canDrownInFluidType(type);
    }
}
