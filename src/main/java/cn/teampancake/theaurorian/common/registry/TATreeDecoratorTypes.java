package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TATreeDecoratorTypes {

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, AurorianMod.MOD_ID);
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<CrystalBudDecorator>> CRYSTAL_BUD = register("crystal_bud", CrystalBudDecorator.CODEC);

    private static <P extends TreeDecorator> DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<P>> register(String key, MapCodec<P> codec) {
        return TREE_DECORATOR_TYPES.register(key, () -> new TreeDecoratorType<>(codec));
    }

}