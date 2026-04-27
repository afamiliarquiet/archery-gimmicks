package ink.quietly.archery_gimmicks.client;

import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.client.basics.MessengersBody;
import ink.quietly.archery_gimmicks.client.basics.MotesInABottle;
import ink.quietly.archery_gimmicks.network.C2SFaithPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.Optional;

import static ink.quietly.archery_gimmicks.basics.Spellbook.TEPELORTS_U;

public class ArcheryGimmicksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BestiarySketches.fill();
		MessengersBody.bury();
		MotesInABottle.shake();

		ClientSendMessageEvents.CHAT.register((message) -> {
			Player sender = Minecraft.getInstance().player;
			// server can check config later it's fine to waste time on this if it's disabled
			if (/*ArcheryGimmicks.CONFIG.sillyMode && */sender != null && TEPELORTS_U.matcher(message).matches()) {
				double dist = Math.max(sender.entityInteractionRange() + 2, 16);
				Vec3 cast = sender.getLookAngle().scale(dist);
				AABB box = sender.getBoundingBox().expandTowards(cast).inflate(1.0, 1.0, 1.0);
				EntityHitResult hit = ProjectileUtil.getEntityHitResult(sender, sender.getEyePosition(), sender.getEyePosition().add(cast), box, EntitySelector.CAN_BE_PICKED, dist * dist);
				if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
					Entity target = hit.getEntity();

					// basically need to copy spellbook.quickstep here.
					// fortunately, this is a joke feature so i can care a lot less about it and skip like, 80% of it
					Vec3 centerBehindYou = target.position()
						.add(0, sender.getBbHeight() / 2, 0)
						.add(target.getLookAngle().horizontal().normalize().reverse());
					Optional<Vec3> optionalSafeLocation = sender.level().findFreePosition(sender, Shapes.create(AABB.ofSize(centerBehindYou, 1.5, 3, 1.5)), centerBehindYou, sender.getBbWidth(), sender.getBbHeight(), sender.getBbWidth());
					optionalSafeLocation = optionalSafeLocation.map(vec3 -> vec3.add(0, -sender.getBbHeight() / 2, 0));

					ClientPlayNetworking.send(new C2SFaithPayload(optionalSafeLocation, Optional.of(target.getYRot())));
				}
			}
		});
	}
}
