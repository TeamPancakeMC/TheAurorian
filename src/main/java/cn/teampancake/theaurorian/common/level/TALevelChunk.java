package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TALevelChunk {

    @Nullable
    private static MethodHandle METHOD_HANDLE;

    @SuppressWarnings("unchecked")
    public static void tick(BlockEntity blockEntity, Level level) {
        if (blockEntity instanceof BaseContainerBlockEntity container && METHOD_HANDLE != null) {
            try {
                NonNullList<ItemStack> stacks = (NonNullList<ItemStack>) METHOD_HANDLE.invokeExact(container);
                TAInventoryUtils.applyPotionDecay(stacks, null, level);
            } catch (Throwable ignored) {}
        }
    }

    static {
        try {
            Class<?> clazz = BaseContainerBlockEntity.class;
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(NonNullList.class);
            METHOD_HANDLE = lookup.findVirtual(clazz, "getItems", methodType);
        } catch (Throwable ignored) {}
    }

}