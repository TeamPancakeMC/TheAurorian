package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.RunestoneBookModel;
import cn.teampancake.theaurorian.client.model.RunestoneKeeperModel;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.client.renderer.layers.RunestoneKeeperBookLayer;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperRenderer extends MobRenderer<RunestoneKeeper, RunestoneKeeperModel<RunestoneKeeper>> {

    public RunestoneKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new RunestoneKeeperModel<>(context.bakeLayer(ModModelLayers.RUNESTONE_KEEPER)), 0.7F);
        this.addLayer(new RunestoneKeeperBookLayer<>(this,
                new RunestoneBookModel<>(context.bakeLayer(ModModelLayers.RUNESTONE_BOOKS))));
    }

    @Override
    protected void scale(RunestoneKeeper livingEntity, PoseStack matrixStack, float partialTickTime) {
        matrixStack.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(RunestoneKeeper entity) {
        return AurorianMod.prefix("textures/entity/runestone_keeper.png");
    }

}