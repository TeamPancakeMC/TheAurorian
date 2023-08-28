package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.client.model.entity.ModZombieModel;
import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisturbedHollowRenderer extends HumanoidMobRenderer<DisturbedHollow, AbstractZombieModel<DisturbedHollow>> {

    public DisturbedHollowRenderer(EntityRendererProvider.Context context) {
        super(context, new ModZombieModel<>(context.bakeLayer(ModModelLayers.DISTURBED_HOLLOW)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(DisturbedHollow entity) {
        return AurorianMod.prefix("textures/entity/disturbed_hollow.png");
    }

}