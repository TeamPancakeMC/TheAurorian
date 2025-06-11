package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.entities.animal.AurorianWingedFish;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AurorianWingedFishModel extends GeoModel<AurorianWingedFish> {

    @Override
    public ResourceLocation getModelResource(AurorianWingedFish object) {
        return TheAurorian.prefix("geo/entity/aurorian_winged_fish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AurorianWingedFish object) {
        return TheAurorian.prefix("textures/entity/aurorian_winged_fish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AurorianWingedFish animatable) {
        return TheAurorian.prefix("animations/entity/aurorian_winged_fish.animation.json");
    }
} 