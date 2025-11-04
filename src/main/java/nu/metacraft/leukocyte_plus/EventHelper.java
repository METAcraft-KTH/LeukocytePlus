package nu.metacraft.leukocyte_plus;

import org.jetbrains.annotations.Nullable;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;
import nu.metacraft.leukocyte_plus.events.WitherGriefEvent;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;

public class EventHelper {

	public static Level.ExplosionInteraction getSourceType(
			Level.ExplosionInteraction original, StimulusEvent<? extends ExplosionEvents> event,
			Entity entity, @Nullable LivingEntity igniter
	) {
		return shouldApply(true, event, e -> e::explode, entity, igniter) ? original : Level.ExplosionInteraction.NONE;
	}

	public static Level.ExplosionInteraction getSourceType(
			Level.ExplosionInteraction original, StimulusEvent<? extends ExplosionEvents> event,
			Level world, BlockPos pos, @Nullable LivingEntity igniter
	) {
		return shouldApply(true, event, e -> e::explode, world, pos, igniter) ? original : Level.ExplosionInteraction.NONE;
	}

	public static boolean shouldPlaceFire(
			boolean original, StimulusEvent<? extends FireEvents> event,
			Entity entity, @Nullable LivingEntity trigger
	) {
		return shouldApply(original, event, e -> e::createFire, entity, trigger);
	}

	public static boolean shouldPlaceFire(
			boolean original, StimulusEvent<? extends FireEvents> event,
			Level world, BlockPos pos, @Nullable LivingEntity trigger
	) {
		return shouldApply(original, event, e -> e::createFire, world, pos, trigger);
	}

	public interface ApplyAble {
		EventResult apply(ServerLevel world, BlockPos pos, LivingEntity trigger);
	}

	public static <T> boolean shouldApply(
			boolean original, StimulusEvent<? extends T> event, Function<T, ApplyAble> generaliser,
			Entity entity, @Nullable LivingEntity igniter
	) {
		if (entity.level().isClientSide()) return original;
		try (var invokers = Stimuli.select().forEntity(entity)) {
			var result = generaliser.apply(invokers.get(event)).apply(
					(ServerLevel) entity.level(), entity.blockPosition(), igniter
			);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

	public static <T> boolean shouldApply(
			boolean original, StimulusEvent<? extends T> event, Function<T, ApplyAble> onApply,
			Level world, BlockPos pos, @Nullable LivingEntity igniter
	) {
		if (world.isClientSide()) return original;
		try (var invokers = Stimuli.select().at(world, pos)) {
			var result = onApply.apply(invokers.get(event)).apply(
					(ServerLevel) world, pos, igniter
			);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

	public static boolean shouldWitherGrief(
			boolean original, WitherBoss wither, @Nullable WitherSkull projectile
	) {
		if (wither.level().isClientSide()) return original;
		try (var invokers = Stimuli.select().forEntity(projectile != null ? projectile : wither)) {
			var result = invokers.get(WitherGriefEvent.EVENT).grief(wither, projectile);
			if (result != EventResult.PASS) {
				return result == EventResult.ALLOW;
			}
		}
		return original;
	}

}
