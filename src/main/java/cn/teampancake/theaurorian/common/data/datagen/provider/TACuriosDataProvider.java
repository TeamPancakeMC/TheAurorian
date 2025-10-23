package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TACurioValidators;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.concurrent.CompletableFuture;

public class TACuriosDataProvider extends CuriosDataProvider {

    public TACuriosDataProvider(
            PackOutput output, ExistingFileHelper fileHelper,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(TheAurorian.MOD_ID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        this.createSlot("necklace").size(4).dropRule(ICurio.DropRule.ALWAYS_DROP);
        this.createSlot("runestone").size(3).dropRule(ICurio.DropRule.ALWAYS_KEEP)
                .addValidator(TACurioValidators.IS_RUNESTONE)
                .icon(TheAurorian.prefix("misc/slot/empty_runestone_slot"));
        this.createEntities("entities").addPlayer().addSlots("necklace", "runestone");
    }

}