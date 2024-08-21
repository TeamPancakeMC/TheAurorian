package cn.teampancake.theaurorian.common.data.datagen.provider;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class TASoundProvider extends SoundDefinitionsProvider {

    public TASoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TheAurorian.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.register(TASoundEvents.CRYSTALLINE_SWORD_USE);
        this.register(TASoundEvents.CRYSTALLINE_SWORD_CHARGING);
        this.register(TASoundEvents.CRYSTALLINE_SWORD_SHOOT);
        this.register(TASoundEvents.WEEPING_WILLOW_BELL);
        this.register(TASoundEvents.SNOW_TUNDRA_GIANT_CRAB_AMBIENT, 2);
        this.register(TASoundEvents.SNOW_TUNDRA_GIANT_CRAB_DEATH, 2);
        this.register(TASoundEvents.SNOW_TUNDRA_GIANT_CRAB_HURT, 3);
        this.register(TASoundEvents.SNOW_TUNDRA_GIANT_CRAB_STEP, 2);
        this.add(TASoundEvents.BACKGROUND_MUSIC, definition().with(
                sound(TheAurorian.prefix("music/aurorian_1"), SoundDefinition.SoundType.SOUND).stream(),
                sound(TheAurorian.prefix("music/aurorian_2"), SoundDefinition.SoundType.SOUND).stream(),
                sound(TheAurorian.prefix("music/aurorian_3"), SoundDefinition.SoundType.SOUND).stream(),
                sound(TheAurorian.prefix("music/aurorian_4"), SoundDefinition.SoundType.SOUND).stream(),
                sound(TheAurorian.prefix("music/aurorian_5"), SoundDefinition.SoundType.SOUND).stream()));
    }

    private void register(DeferredHolder<SoundEvent, SoundEvent> soundEvent){
        this.add(soundEvent, definition().with(sound(soundEvent.getId())));
    }

    public void register(DeferredHolder<SoundEvent, SoundEvent> soundEvent,int variantType){
        SoundDefinition definition = definition();
        for (int i = 1; i <= variantType; i++) {
            definition.with(sound(soundEvent.getId() + "_" + i));
        }

        this.add(soundEvent, definition);
    }

}