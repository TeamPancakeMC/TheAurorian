package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.client.model.entity.RunestoneKeeperModel;
import cn.teampancake.theaurorian.client.renderer.layers.HumanoidMonsterArmorLayer;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperRenderer extends HumanoidMobRenderer<RunestoneKeeper, RunestoneKeeperModel<RunestoneKeeper>> {

    private static final ResourceLocation PATH = AurorianMod.prefix("textures/entity/runestone_keeper_layer.png");
    private static final ModelLayerLocation LAYER = ModModelLayers.RUNESTONE_KEEPER;

    public RunestoneKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new RunestoneKeeperModel<>(context.bakeLayer(LAYER)), 1.0F, 2.0F, 2.0F, 2.0F);
        this.addLayer(new HumanoidMonsterArmorLayer<>(this, new RunestoneKeeperModel<>(context.bakeLayer(LAYER)), PATH));
    }

    @Override
    public ResourceLocation getTextureLocation(RunestoneKeeper entity) {
        return AurorianMod.prefix("textures/entity/runestone_keeper.png");
    }

}