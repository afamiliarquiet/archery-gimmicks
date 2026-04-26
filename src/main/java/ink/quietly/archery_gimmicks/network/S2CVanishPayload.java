package ink.quietly.archery_gimmicks.network;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record S2CVanishPayload(int playerId) implements CustomPacketPayload {
	public static final Identifier VANISH_ID = ArcheryGimmicks.id("vanish");
	public static final CustomPacketPayload.Type<S2CVanishPayload> TYPE = new CustomPacketPayload.Type<>(VANISH_ID);
	public static final StreamCodec<ByteBuf, S2CVanishPayload> CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, S2CVanishPayload::playerId,
		S2CVanishPayload::new
	);

	@Override
	public @NonNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
