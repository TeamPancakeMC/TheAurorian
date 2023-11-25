package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.MoonQueenModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonQueenRenderer extends MobRenderer<MoonQueen, MoonQueenModel<MoonQueen>> {

    public MoonQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new MoonQueenModel<>(context.bakeLayer(TAModelLayers.MOON_QUEEN)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoonQueen entity) {
        return AurorianMod.prefix("textures/entity/moon_queen.png");
    }

}