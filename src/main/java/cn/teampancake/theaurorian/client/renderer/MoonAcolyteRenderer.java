package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.client.model.entity.ModZombieModel;
import cn.teampancake.theaurorian.client.model.layer.MoonAcolyteArmorLayer;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonAcolyteRenderer extends HumanoidMobRenderer<MoonAcolyte, AbstractZombieModel<MoonAcolyte>> {

    public MoonAcolyteRenderer(EntityRendererProvider.Context context) {
        super(context, new ModZombieModel<>(context.bakeLayer(ModModelLayers.MOON_ACOLYTE)), 0.5F);
        this.addLayer(new MoonAcolyteArmorLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(MoonAcolyte entity) {
        return AurorianMod.prefix("textures/entity/moon_acolyte.png");
    }

}