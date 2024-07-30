package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

public class TAVillagerProfession {

    public static final ResourceLocation ASTROLOGER = AurorianMod.prefix("astrologer");

    public static final DeferredRegister<PoiType> POINTS_OF_INTEREST = DeferredRegister.create(ForgeRegistries.POI_TYPES, AurorianMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, AurorianMod.MOD_ID);

    public static final RegistryObject<PoiType> POI_ASTROLOGY_TABLE = POINTS_OF_INTEREST.register(
            "astrology_table", () -> createPOI(assembleStates(TABlocks.ASTROLOGY_TABLE.get()))
    );

    public static final RegistryObject<VillagerProfession> PROF_ASTROLOGER = PROFESSIONS.register(
            ASTROLOGER.getPath(), () -> createProf(ASTROLOGER, POI_ASTROLOGY_TABLE::getKey, SoundEvents.VILLAGER_WORK_CARTOGRAPHER )
    );


    private static Collection<BlockState> assembleStates(Block block) {
        return block.getStateDefinition().getPossibleStates();
    }

    private static PoiType createPOI(Collection<BlockState> block) {
        return new PoiType(ImmutableSet.copyOf(block), 1, 1);
    }

    private static VillagerProfession createProf(ResourceLocation name, Supplier<ResourceKey<PoiType>> poi, SoundEvent sound) {
        ResourceKey<PoiType> poiName = poi.get();
        return new VillagerProfession(
                name.toString(),
                (p) -> p.is(poiName),
                (p) -> p.is(poiName),
                ImmutableSet.of(),
                ImmutableSet.of(),
                sound
        );
    }
}
