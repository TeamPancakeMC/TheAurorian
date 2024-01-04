package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.AurorianWingedFishModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianWingedFish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianWingedFishRenderer extends MobRenderer<AurorianWingedFish, AurorianWingedFishModel<AurorianWingedFish>> {

    public AurorianWingedFishRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianWingedFishModel<>(context.bakeLayer(TAModelLayers.AURORIAN_WINGED_FISH)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianWingedFish pEntity) {
        return AurorianMod.prefix("textures/entity/aurorian_winged_fish.png");
    }

}