package nu.metacraft.leukocyte_plus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;
import nu.metacraft.leukocyte_plus.events.WitherGriefEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

import java.util.function.Function;

public class EventHelper {

	public static World.ExplosionSourceType getSourceType(
			World.ExplosionSourceType original, StimulusEvent<? extends ExplosionEvents> event,
			Entity entity, @Nullable LivingEntity igniter
	) {
		return shouldApply(true, event, e -> e::explode, entity, igniter) ? original : World.ExplosionSourceType.NONE;
	}

	public static World.ExplosionSourceType getSourceType(
			World.ExplosionSourceType original, StimulusEvent<? extends ExplosionEvents> event,
			World world, BlockPos pos, @Nullable LivingEntity igniter
	) {
		return shouldApply(true, event, e -> e::explode, world, pos, igniter) ? original : World.ExplosionSourceType.NONE;
	}

	public static boolean shouldPlaceFire(
			boolean original, StimulusEvent<? extends FireEvents> event,
			Entity entity, @Nullable LivingEntity trigger
	) {
		return shouldApply(original, event, e -> e::createFire, entity, trigger);
	}

	public static boolean shouldPlaceFire(
			boolean original, StimulusEvent<? extends FireEvents> event,
			World world, BlockPos pos, @Nullable LivingEntity trigger
	) {
		return shouldApply(original, event, e -> e::createFire, world, pos, trigger);
	}

	public interface ApplyAble {
		EventResult apply(ServerWorld world, BlockPos pos, LivingEntity trigger);
	}

	public static <T> boolean shouldApply(
			boolean original, StimulusEvent<? extends T> event, Function<T, ApplyAble> generaliser,
			Entity entity, @Nullable LivingEntity igniter
	) {
		if (entity.getEntityWorld().isClient()) return original;
		try (var invokers = Stimuli.select().forEntity(entity)) {
			var result = generaliser.apply(invokers.get(event)).apply(
					(ServerWorld) entity.getEntityWorld(), entity.getBlockPos(), igniter
			);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

	public static <T> boolean shouldApply(
			boolean original, StimulusEvent<? extends T> event, Function<T, ApplyAble> onApply,
			World world, BlockPos pos, @Nullable LivingEntity igniter
	) {
		if (world.isClient()) return original;
		try (var invokers = Stimuli.select().at(world, pos)) {
			var result = onApply.apply(invokers.get(event)).apply(
					(ServerWorld) world, pos, igniter
			);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

	public static boolean shouldWitherGrief(
			boolean original, WitherEntity wither, @Nullable WitherSkullEntity projectile
	) {
		if (wither.getEntityWorld().isClient()) return original;
		try (var invokers = Stimuli.select().forEntity(projectile != null ? projectile : wither)) {
			var result = invokers.get(WitherGriefEvent.EVENT).grief(wither, projectile);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

}
