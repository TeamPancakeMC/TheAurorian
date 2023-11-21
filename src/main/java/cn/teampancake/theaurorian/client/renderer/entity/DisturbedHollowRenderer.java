package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.DisturbedHollowModel;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisturbedHollowRenderer extends MobRenderer<DisturbedHollow, DisturbedHollowModel<DisturbedHollow>> {

    public DisturbedHollowRenderer(EntityRendererProvider.Context context) {
        super(context, new DisturbedHollowModel<>(context.bakeLayer(ModModelLayers.DISTURBED_HOLLOW).getChild("all")), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DisturbedHollow entity) {
        return AurorianMod.prefix("textures/entity/disturbed_hollow.png");
    }

}