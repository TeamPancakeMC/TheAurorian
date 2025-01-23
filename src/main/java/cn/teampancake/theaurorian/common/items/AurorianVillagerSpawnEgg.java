package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

public class AurorianVillagerSpawnEgg extends DeferredSpawnEggItem {

    public AurorianVillagerSpawnEgg() {
        super(TAEntityTypes.AURORIAN_VILLAGER, 0x9e9e9e, 0x4f4f4f, new Properties());
    }

    @Override
    protected EntityType<?> getDefaultType() {
        RandomSource random = RandomSource.create();
        return random.nextFloat() <= 0.05F ? TAEntityTypes.SELENA.get() : TAEntityTypes.AURORIAN_VILLAGER.get();
    }

}