package cn.teampancake.theaurorian.common.network.message;

import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NightSyncMessage {

    public int NightType;

    public NightSyncMessage(int NightType){this.NightType=NightType;}

    public static void encode(NightSyncMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.NightType);
    }

    public static NightSyncMessage decode(FriendlyByteBuf buffer)
    {
        return new NightSyncMessage(buffer.readInt());
    }

    public static void handle(NightSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientNightSync.handlePacket(message, contextSupplier)));
        }

        context.setPacketHandled(true);
    }
}

@OnlyIn(Dist.CLIENT)
class ClientNightSync{
    public static void handlePacket(NightSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier){
        TASkyRenderer.SetPhaseState(message.NightType);
        //TODO: Handle Effect here
    }
}