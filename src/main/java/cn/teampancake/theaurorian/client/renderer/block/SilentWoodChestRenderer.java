package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.entity.SilentWoodChestBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SilentWoodChestRenderer extends ChestRenderer<SilentWoodChestBlockEntity> {

    private static final Material CHEST_SILENT_WOOD_LOCATION = chestMaterial("silent_wood");
    private static final Material CHEST_SILENT_WOOD_LOCATION_LEFT = chestMaterial("silent_wood_left");
    private static final Material CHEST_SILENT_WOOD_LOCATION_RIGHT = chestMaterial("silent_wood_right");

    public SilentWoodChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Material getMaterial(SilentWoodChestBlockEntity blockEntity, ChestType chestType) {
        Material material = chestType == ChestType.RIGHT ? CHEST_SILENT_WOOD_LOCATION_RIGHT : CHEST_SILENT_WOOD_LOCATION;
        return chestType == ChestType.LEFT ? CHEST_SILENT_WOOD_LOCATION_LEFT : material;
    }

    private static Material chestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, AurorianMod.prefix("entity/chest/" + chestName));
    }

}