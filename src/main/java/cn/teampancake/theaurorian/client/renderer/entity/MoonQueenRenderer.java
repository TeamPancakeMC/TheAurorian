package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class MoonQueenRenderer<T extends MoonQueen> extends DeathWithoutRotationRenderer<T> {

    public MoonQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(TAEntityTypes.MOON_QUEEN.getId()));
        this.shadowRadius = 0.4F;
    }

}