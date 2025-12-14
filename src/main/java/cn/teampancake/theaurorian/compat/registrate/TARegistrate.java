package cn.teampancake.theaurorian.compat.registrate;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TARegistrate extends AbstractRegistrate<TARegistrate> {

    private static final Logger log = LogManager.getLogger(TARegistrate.class);

    protected TARegistrate(String modid) {
        super(modid);
    }

    public static TARegistrate create(String modid) {
        var ret = new TARegistrate(modid);
        Optional<IEventBus> modEventBus = ModList.get().getModContainerById(modid).map(ModContainer::getEventBus);
        modEventBus.ifPresentOrElse(ret::registerEventListeners, () -> {
            String message = "# [Registrate] Failed to register eventListeners for mod " + modid + ", This should be reported to this mod's dev #";
            StringBuilder hashtags = new StringBuilder().append("#".repeat(message.length()));
            log.fatal(hashtags.toString());
            log.fatal(message);
            log.fatal(hashtags.toString());
        });
        return ret;
    }

    public <T extends Item, P> ItemBuilder<T, P> item(P parent, String name, NonNullFunction<Item.Properties, T> factory) {
        return entry(name, callback -> TAItemBuilder.create(this, parent, name, callback, factory).transform(builder -> builder));
    }

    public <T extends Block, P> BlockBuilder<T, P> block(P parent, String name, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        return this.entry(name, callback -> TABlockBuilder.create(this, parent, name, callback, factory));
    }

}