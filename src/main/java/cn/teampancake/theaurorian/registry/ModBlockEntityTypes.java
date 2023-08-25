package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.entity.AurorianFurnaceBlockEntity;
import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"ConstantConditions", "SpellCheckingInspection"})
public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AurorianMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<AurorianFurnaceBlockEntity>> AURORIAN_FURNACE = BLOCK_ENTITY_TYPES.register("aurorian_furnace",
            () -> BlockEntityType.Builder.of(AurorianFurnaceBlockEntity::new, ModBlocks.AURORIAN_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MoonlightForgeBlockEntity>> MOONLIGHT_FORGE = BLOCK_ENTITY_TYPES.register("moonlight_forge",
            () -> BlockEntityType.Builder.of(MoonlightForgeBlockEntity::new, ModBlocks.MOONLIGHT_FORGE.get()).build(null));

}