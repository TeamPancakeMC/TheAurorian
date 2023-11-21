package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.MoonAcolyteModel;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonAcolyteRenderer extends MobRenderer<MoonAcolyte, MoonAcolyteModel<MoonAcolyte>> {

    public MoonAcolyteRenderer(EntityRendererProvider.Context context) {
        super(context, new MoonAcolyteModel<>(context.bakeLayer(ModModelLayers.MOON_ACOLYTE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoonAcolyte entity) {
        return AurorianMod.prefix("textures/entity/moon_acolyte.png");
    }

}