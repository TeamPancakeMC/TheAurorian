package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.RunestoneBookModel;
import cn.teampancake.theaurorian.client.model.entity.RunestoneKeeperModel;
import cn.teampancake.theaurorian.client.renderer.layers.RunestoneKeeperBookLayer;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperRenderer extends DeathWithoutRotationRenderer<RunestoneKeeper, RunestoneKeeperModel<RunestoneKeeper>> {

    public RunestoneKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new RunestoneKeeperModel<>(context.bakeLayer(TAModelLayers.RUNESTONE_KEEPER)), 0.7F);
        this.addLayer(new RunestoneKeeperBookLayer<>(this,
                new RunestoneBookModel<>(context.bakeLayer(TAModelLayers.RUNESTONE_BOOKS))));
    }

    @Override
    protected void scale(RunestoneKeeper livingEntity, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(1.5F, 1.5F, 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(RunestoneKeeper entity) {
        return AurorianMod.prefix("textures/entity/runestone_keeper.png");
    }

}