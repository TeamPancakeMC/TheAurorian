package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.ForgottenMagicBookModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.ForgottenMagicBook;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForgottenMagicBookRenderer extends MobRenderer<ForgottenMagicBook, ForgottenMagicBookModel<ForgottenMagicBook>> {

    public ForgottenMagicBookRenderer(EntityRendererProvider.Context context) {
        super(context, new ForgottenMagicBookModel<>(context.bakeLayer(TAModelLayers.FORGOTTEN_MAGIC_BOOK)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(ForgottenMagicBook entity) {
        return TheAurorian.prefix("textures/entity/forgotten_magic_book.png");
    }

}