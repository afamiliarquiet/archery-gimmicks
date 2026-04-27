package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.runIterationOnItem;

// it's a mess in here. uhh.. *teleports away* [unintelligible distant shout]
public class Spellbook {
	public static final Pattern TEPELORTS_U = Pattern.compile(".*teleports? behind (yo)?u.*");
	public static final DataComponentType<EnchantmentValueEffect> QUICKSTEP_POWER = register(
		"quickstep_power", b -> b.persistent(EnchantmentValueEffect.CODEC)
	);
	public static final ResourceKey<Enchantment> QUICKSTEP = key("quickstep");

	public static void fill() {

	}

	private static <T> DataComponentType<T> register(final String id, final UnaryOperator<DataComponentType.Builder<T>> builder) {
		return Registry.register(
			BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ArcheryGimmicks.id(id), builder.apply(DataComponentType.builder()).build()
		);
	}

	private static ResourceKey<Enchantment> key(String path) {
		return ResourceKey.create(Registries.ENCHANTMENT, ArcheryGimmicks.id(path));
	}

	public static float getQuickstepPower(Player player) {
		MutableFloat mPower = new MutableFloat(0.0F);
		runIterationOnItem(player.getMainHandItem(), (enchantment, level) ->
			enchantment.value().modifyUnfilteredValue(Spellbook.QUICKSTEP_POWER, player.getRandom(), level, mPower));
//		float generousPower = mPower.floatValue() + 1f;
		return mPower.floatValue();
	}

	// i expect this to be called on client, so i can take a little more time figuring out where to tp
	// server will just take a look based on power.
	public static Optional<Vec3> quickstep(Player target) {
//		float leap = distance.calculate(enchantmentLevel);
//		float leap = 6f;
		float leap = getQuickstepPower(target);
		Vec3 direction = new Vec3(0, 0, -1);

		// honestly since this is on client i could probably get away with doing a fan vertically instead of just look angle
		// then take the one that results on the largest leap. takes a bit of the control away though and sounds annoying
		// so not gonna do it.
		Vec3 leapVec = target.getLookAngle().addLocalCoordinates(direction).scale(leap);
		Vec3 desiredLocation = target.position().add(leapVec);

		// adjust for collision? makes you a teeny little speck from your eyes. this is natural
		// it's technically possible as a result of being a speck that you can go through walls..
		// if the walls are shaped just oddly enough to have a hole leading in with a standable space very nearby.
		// it's fine it's funny enough to let live
		// besides, client could just modify this jar and choose whatever pos they want.
		// we'll see if it needs to be tuned later.
		BlockHitResult clipResult = target.level().clip(new ClipContext(target.position().add(0, target.getEyeHeight(), 0), desiredLocation.add(0, target.getEyeHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
		desiredLocation = clipResult.getLocation()/*.add(0, -target.getEyeHeight(), 0)*/;

		// overcomplicating things for fun
		WorldBorder border = target.level().getWorldBorder();
		boolean slip = !border.isWithinBounds(desiredLocation.x, desiredLocation.z, target.getBbWidth());
		if (slip) {
			// really silly. this is completely unnecessary
			Vec3 borderLocation = border.clampVec3ToBound(desiredLocation);
			Vec3 correction = borderLocation.subtract(desiredLocation);
			Vec3 extraBbCorrection = new Vec3(target.getBbWidth() * Mth.sign(correction.x) / 2, 0, target.getBbWidth() * Mth.sign(correction.z) / 2);
			desiredLocation = borderLocation.add(extraBbCorrection);
		}

		// tiny nudge before down check because you are so infinitesimally small that you can get stuck in the side of blocks
		desiredLocation = desiredLocation.add(target.getLookAngle().scale(0.01));
		BlockHitResult gravitizer = target.level().clip(new ClipContext(desiredLocation, desiredLocation.relative(Direction.DOWN, leap + target.getEyeHeight()), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty()));
		desiredLocation = gravitizer.getLocation();

		// adjust for findFreePosition wanting a center center... hang on it might want x and z centered too. is that happening?
		// surely an entity's pos is already centered on xz.
		desiredLocation = desiredLocation
			.add(0, target.getBbHeight() / 2, 0);

		// this isn't even really necessary anymore... only because i'm shrinking you into an infinitesimal speck for quickstep
		VoxelShape tolerance = Shapes.create(AABB.ofSize(desiredLocation, 2*target.getBbWidth(), 2*target.getBbHeight(), 2*target.getBbWidth()));
		Optional<Vec3> optionalSafeLocation = target.level().findFreePosition(target, tolerance, desiredLocation, target.getBbWidth(), target.getBbHeight(), target.getBbWidth());

		optionalSafeLocation = optionalSafeLocation.map(vec3 -> vec3.add(0, -target.getBbHeight() / 2, 0));

		// now i get to throw you DOWN on the GROUND
//		optionalSafeLocation = optionalSafeLocation.map(vec3 -> {
//			BlockHitResult downResult = target.level().clip(new ClipContext(vec3, vec3.relative(Direction.DOWN, leap), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, target));
//			return downResult.getLocation();
//		});

		return optionalSafeLocation;
//		optionalSafeLocation.ifPresent(safelyBehind -> {
//			target.teleportTo(safelyBehind.x, safelyBehind.y, safelyBehind.z);
//			target.forceSetRotation(target.getYRot(), false, 0, false);
//		});

	}
}
