package cn.teampancake.theaurorian.common.components;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public record RunestoneNature(float minChopBoost, float maxChopBoost, float minXpBoost, float maxXpBoost) {

    public static final Codec<RunestoneNature> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chop_boost").forGetter(RunestoneNature::minChopBoost),
            Codec.FLOAT.fieldOf("max_chop_boost").forGetter(RunestoneNature::maxChopBoost),
            Codec.FLOAT.fieldOf("min_xp_boost").forGetter(RunestoneNature::minXpBoost),
            Codec.FLOAT.fieldOf("max_xp_boost").forGetter(RunestoneNature::maxXpBoost)).apply(instance, RunestoneNature::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneNature> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneNature::minChopBoost, ByteBufCodecs.FLOAT, RunestoneNature::maxChopBoost,
            ByteBufCodecs.FLOAT, RunestoneNature::minXpBoost, ByteBufCodecs.FLOAT, RunestoneNature::maxXpBoost, RunestoneNature::new);

    @Nullable
    public static RunestoneNature findRunestoneNature(@Nullable Player player) {
        if (player == null || player instanceof LocalPlayer || player.level().isClientSide()) return null;
        Optional<ICuriosItemHandler> itemHandlerOptional = CuriosApi.getCuriosInventory(player);
        if (itemHandlerOptional.isEmpty()) return null;
        ICuriosItemHandler itemHandler = itemHandlerOptional.get();
        DataComponentType<RunestoneNature> component = TADataComponents.RUNESTONE_NATURE.get();
        Optional<SlotResult> slotResultOptional = itemHandler.findFirstCurio(stack -> stack.has(component));
        return slotResultOptional.map(slotResult -> slotResult.stack().get(component)).orElse(null);
    }

}