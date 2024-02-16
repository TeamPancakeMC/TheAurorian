package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.RockHammerModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.RockHammer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RockHammerRenderer extends MobRenderer<RockHammer, RockHammerModel<RockHammer>> {

    public RockHammerRenderer(EntityRendererProvider.Context context) {
        super(context, new RockHammerModel<>(context.bakeLayer(TAModelLayers.ROCK_HAMMER)), 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(RockHammer entity) {
        return AurorianMod.prefix("textures/entity/rock_hammer.png");
    }

}