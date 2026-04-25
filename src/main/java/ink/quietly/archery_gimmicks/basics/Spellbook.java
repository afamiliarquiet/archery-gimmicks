package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Optional;
import java.util.function.UnaryOperator;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.runIterationOnItem;

public class Spellbook {
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
	// todo - try to prevent going through walls.
	public static Optional<Vec3> quickstep(Player target) {
//		float leap = distance.calculate(enchantmentLevel);
//		float leap = 6f;
		float leap = getQuickstepPower(target);
		Vec3 direction = new Vec3(0, 0, -1);

		Vec3 startLocation = target.position()
			.add(0, target.getBbHeight() / 2, 0);
		Vec3 desiredLocation = startLocation
			.add(target.getLookAngle().addLocalCoordinates(direction).scale(leap));
		VoxelShape tolerance = Shapes.create(AABB.ofSize(desiredLocation, leap / 3, leap / 3, leap / 3));
		Optional<Vec3> optionalSafeLocation = target.level().findFreePosition(target, tolerance, desiredLocation, target.getBbWidth(), target.getBbHeight(), target.getBbWidth());

		return optionalSafeLocation;
//		optionalSafeLocation.ifPresent(safelyBehind -> {
//			target.teleportTo(safelyBehind.x, safelyBehind.y, safelyBehind.z);
//			target.forceSetRotation(target.getYRot(), false, 0, false);
//		});

	}
}
