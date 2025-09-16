package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class TAJukeboxSongs {

    public static final ResourceKey<JukeboxSong> AURORIAN_FOREST = create("aurorian_forest");
    public static final ResourceKey<JukeboxSong> MOONLIT_VEIL = create("moonlit_veil");

    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, TheAurorian.prefix(name));
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, float lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), lengthInSeconds, comparatorOutput));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, AURORIAN_FOREST, TASoundEvents.AURORIAN_FOREST, 43, 12);
        register(context, MOONLIT_VEIL, TASoundEvents.MOONLIT_VEIL, 64, 15);
    }

}