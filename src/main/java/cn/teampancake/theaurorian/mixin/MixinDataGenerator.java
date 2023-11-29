package cn.teampancake.theaurorian.mixin;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DataGenerator.class)
public class MixinDataGenerator {

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/HashCache;purgeStaleAndWrite()V"))
    public void run(HashCache hashCache) {}

}