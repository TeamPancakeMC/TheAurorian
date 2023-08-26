package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.projectile.CeruleanArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CeruleanArrowRenderer<T extends CeruleanArrowEntity> extends ArrowRenderer<T> {

    public CeruleanArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return AurorianMod.prefix("textures/entity/cerulean_arrow.png");
    }

}