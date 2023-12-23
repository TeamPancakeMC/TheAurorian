package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class TASoundProvider extends SoundDefinitionsProvider {

    public TASoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, AurorianMod.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(TASoundEvents.WEEPING_WILLOW_BELL, definition().with(
                sound(TASoundEvents.WEEPING_WILLOW_BELL.get().getLocation())));
        this.add(TASoundEvents.BACKGROUND_MUSIC, definition().with(
                sound(AurorianMod.prefix("music/aurorian_1"), SoundDefinition.SoundType.SOUND).stream(),
                sound(AurorianMod.prefix("music/aurorian_2"), SoundDefinition.SoundType.SOUND).stream(),
                sound(AurorianMod.prefix("music/aurorian_3"), SoundDefinition.SoundType.SOUND).stream(),
                sound(AurorianMod.prefix("music/aurorian_4"), SoundDefinition.SoundType.SOUND).stream(),
                sound(AurorianMod.prefix("music/aurorian_5"), SoundDefinition.SoundType.SOUND).stream()));
    }

}