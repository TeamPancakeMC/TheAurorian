package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.MoonFishModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.MoonFish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonFishRenderer extends MobRenderer<MoonFish, MoonFishModel<MoonFish>> {

    public MoonFishRenderer(EntityRendererProvider.Context context) {
        super(context, new MoonFishModel<>(context.bakeLayer(TAModelLayers.MOON_FISH)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoonFish pEntity) {
        return TheAurorian.prefix("textures/entity/moon_fish.png");
    }

}