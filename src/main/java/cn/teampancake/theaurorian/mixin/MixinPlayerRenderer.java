package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.client.model.AurorianSteelArmorModel;
import cn.teampancake.theaurorian.client.model.CeruleanArmorModel;
import cn.teampancake.theaurorian.client.model.KnightArmorModel;
import cn.teampancake.theaurorian.client.model.SpectralArmorModel;
import cn.teampancake.theaurorian.client.renderer.layers.ModHumanoidArmorLayer;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public MixinPlayerRenderer(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        this.addLayer(new ModHumanoidArmorLayer<>(this, new AurorianSteelArmorModel<>(context.bakeLayer(ModModelLayers.AURORIAN_STEEL_ARMOR))));
        this.addLayer(new ModHumanoidArmorLayer<>(this, new CeruleanArmorModel<>(context.bakeLayer(ModModelLayers.CERULEAN_ARMOR))));
        this.addLayer(new ModHumanoidArmorLayer<>(this, new KnightArmorModel<>(context.bakeLayer(ModModelLayers.KNIGHT_ARMOR))));
        this.addLayer(new ModHumanoidArmorLayer<>(this, new SpectralArmorModel<>(context.bakeLayer(ModModelLayers.SPECTRAL_ARMOR))));
    }

}