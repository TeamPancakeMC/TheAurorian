package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.RunestoneKeeperModel;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperRenderer extends MobRenderer<RunestoneKeeper, RunestoneKeeperModel<RunestoneKeeper>> {

    public RunestoneKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new RunestoneKeeperModel<>(context.bakeLayer(ModModelLayers.RUNESTONE_KEEPER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(RunestoneKeeper entity) {
        return AurorianMod.prefix("textures/entity/runestone_keeper.png");
    }

}