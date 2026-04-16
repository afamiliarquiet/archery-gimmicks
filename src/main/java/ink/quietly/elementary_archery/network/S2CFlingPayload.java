package ink.quietly.elementary_archery.network;

import ink.quietly.elementary_archery.ElementaryArchery;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record S2CFlingPayload(Vec3 fling) implements CustomPacketPayload {
	public static final Identifier FLING_ID = ElementaryArchery.id("fling");
	public static final CustomPacketPayload.Type<S2CFlingPayload> TYPE = new CustomPacketPayload.Type<>(FLING_ID);
	public static final StreamCodec<ByteBuf, S2CFlingPayload> CODEC = StreamCodec.composite(
		Vec3.STREAM_CODEC, S2CFlingPayload::fling,
		S2CFlingPayload::new
	);

	@Override
	public @NonNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
