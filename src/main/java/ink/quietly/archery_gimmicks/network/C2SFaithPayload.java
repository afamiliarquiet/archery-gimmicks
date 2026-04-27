package ink.quietly.archery_gimmicks.network;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Spellbook;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public record C2SFaithPayload(Optional<Vec3> position, Optional<Float> yRot) implements CustomPacketPayload {
	public static final Identifier FAITH_ID = ArcheryGimmicks.id("faith");
	public static final CustomPacketPayload.Type<C2SFaithPayload> TYPE = new CustomPacketPayload.Type<>(FAITH_ID);
	public static final StreamCodec<ByteBuf, C2SFaithPayload> CODEC = StreamCodec.composite(
		ByteBufCodecs.optional(Vec3.STREAM_CODEC), C2SFaithPayload::position,
		ByteBufCodecs.optional(ByteBufCodecs.FLOAT), C2SFaithPayload::yRot,
		C2SFaithPayload::new
	);

	@Override
	public @NonNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(C2SFaithPayload payload, ServerPlayNetworking.Context context) {
		ServerPlayer player = context.player();
		ItemStack item = player.getMainHandItem();

		if (payload.position.isEmpty()) {
			return; // todo,. handle better
		}
		Vec3 pos = payload.position.get();

//		MutableFloat mPower = new MutableFloat(0.0F);
//		runIterationOnItem(item, (enchantment, level) ->
//			enchantment.value().modifyUnfilteredValue(Spellbook.QUICKSTEP_POWER, player.getRandom(), level, mPower));
		boolean belief = false;
		if (EnchantmentHelper.has(item, Spellbook.QUICKSTEP_POWER)) {
			float generousPower = Spellbook.getQuickstepPower(player) + 1f;
			if (player.onGround() && pos.distanceToSqr(player.position()) < generousPower * generousPower) {
				belief = true;
			}
		} else if (ArcheryGimmicks.CONFIG.sillyMode) {
			double generousRange = player.entityInteractionRange() + 3;
			if (pos.distanceToSqr(player.position()) < generousRange * generousRange) {
				belief = true;
			}
		}

		if (belief) {
			S2CVanishPayload vanish = new S2CVanishPayload(player.getId());
			AtomicBoolean sentToPlayer = new AtomicBoolean(false);
			PlayerLookup.tracking(player).forEach(tracker -> {
				ServerPlayNetworking.send(tracker, vanish);
				if (tracker == player) {
					sentToPlayer.set(true);
				}
			});
			if (!sentToPlayer.get()) {
				ServerPlayNetworking.send(player, vanish);
			}
			// go my packets. the race is on
			player.teleportTo(pos.x, pos.y, pos.z);
			payload.yRot.ifPresent(aFloat -> player.forceSetRotation(aFloat, false, 0, false));
		}
	}
}
