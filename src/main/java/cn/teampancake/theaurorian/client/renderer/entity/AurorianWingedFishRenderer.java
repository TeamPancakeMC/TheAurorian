package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.client.model.entity.AurorianWingedFishModel;
import cn.teampancake.theaurorian.common.entities.animal.AurorianWingedFish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AurorianWingedFishRenderer extends GeoEntityRenderer<AurorianWingedFish> {

    public AurorianWingedFishRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianWingedFishModel());
    }
    
} 