package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public class TAVillagerProfession {

    public static final DeferredRegister<PoiType> POINTS_OF_INTEREST = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, AurorianMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, AurorianMod.MOD_ID);

    public static final DeferredHolder<PoiType, PoiType> POI_ASTROLOGY_TABLE = POINTS_OF_INTEREST.register(
            "astrology_table", () -> createPOI(assembleStates(TABlocks.ASTROLOGY_TABLE.get())));

    public static final DeferredHolder<VillagerProfession, VillagerProfession> PROF_ASTROLOGER = PROFESSIONS.register(
            "astrologer", () -> createProf("astrologer", POI_ASTROLOGY_TABLE, SoundEvents.VILLAGER_WORK_CARTOGRAPHER));

    private static Collection<BlockState> assembleStates(Block block) {
        return block.getStateDefinition().getPossibleStates();
    }

    private static PoiType createPOI(Collection<BlockState> block) {
        return new PoiType(ImmutableSet.copyOf(block), 1, 1);
    }

    private static VillagerProfession createProf(String name, DeferredHolder<PoiType, PoiType> poi, SoundEvent sound) {
        ResourceLocation poiName = poi.getId();
        return new VillagerProfession(name, (p) -> p.is(poiName), (p) -> p.is(poiName), ImmutableSet.of(), ImmutableSet.of(), sound);
    }

    public static void register(IEventBus bus){
        POINTS_OF_INTEREST.register(bus);
        PROFESSIONS.register(bus);
    }

}