package cn.teampancake.theaurorian.common.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FutureNightMessage {
    public int[] futureNight;

    public FutureNightMessage(int[] futureNight){
        this.futureNight = futureNight;
    }

    public static void encode(FutureNightMessage message, FriendlyByteBuf buffer) {
        buffer.writeVarIntArray(message.futureNight);
    }

    public static FutureNightMessage decode(FriendlyByteBuf buffer) {
        return new FutureNightMessage(buffer.readVarIntArray());
    }

    public static void handle(FutureNightMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientFutureNightSync.handlePacket(message, contextSupplier)));
        }

        context.setPacketHandled(true);
    }

}

@OnlyIn(Dist.CLIENT)
class ClientFutureNightSync {

    public static void handlePacket(FutureNightMessage message, Supplier<NetworkEvent.Context> contextSupplier) {

    }
}
