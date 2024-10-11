package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.api.ITAItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class MixinItem implements ITAItem {

    @Unique
    private Item.Properties TA$properties;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void init(Item.Properties properties, CallbackInfo ci) {
        this.TA$properties = properties;
    }

    @Unique
    public Item.Properties TA$properties() {
        return this.TA$properties;
    }

}