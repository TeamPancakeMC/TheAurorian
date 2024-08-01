package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class TAVillagerProfession {
    public static final DeferredRegister<PoiType> POINTS_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, AurorianMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, AurorianMod.MOD_ID);

    public static final RegistryObject<PoiType> POI_ASTROLOGY_TABLE = POINTS_OF_INTEREST.register(
            "astrology_table", () -> createPOI(assembleStates(TABlocks.ASTROLOGY_TABLE.get()))
    );

    public static final RegistryObject<VillagerProfession> PROF_ASTROLOGER = PROFESSIONS.register(
            "astrologer", () -> createProf("astrologer", POI_ASTROLOGY_TABLE, SoundEvents.VILLAGER_WORK_CARTOGRAPHER)
    );


    private static Collection<BlockState> assembleStates(Block block) {
        return block.getStateDefinition().getPossibleStates();
    }

    private static PoiType createPOI(Collection<BlockState> block) {
        return new PoiType(ImmutableSet.copyOf(block), 1, 1);
    }

    private static VillagerProfession createProf(String name, RegistryObject<PoiType> poi, SoundEvent sound) {
        ResourceLocation poiName = poi.getId();
        return new VillagerProfession(
                name,
                (p) -> p.is(poiName),
                (p) -> p.is(poiName),
                ImmutableSet.of(),
                ImmutableSet.of(),
                sound
        );
    }

    public static void register(IEventBus bus){
        POINTS_OF_INTEREST.register(bus);
        PROFESSIONS.register(bus);
    }
}
