package cn.teampancake.theaurorian.common.mixin;

import net.minecraft.world.entity.npc.VillagerTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(VillagerTrades.EmeraldsForVillagerTypeItem.class)
public class MixinEmeraldsForVillagerTypeItem {

    @Redirect(method = "<init>",at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"))
    private void cancel(Optional instance, Consumer action){
        //Do nothing here to cancel
    }

}