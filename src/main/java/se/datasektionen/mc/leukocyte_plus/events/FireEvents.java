package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface FireEvents {

	StimulusEvent<FireEvents> BED = StimulusEvent.create(FireEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.createFire(world, pos, igniter);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	StimulusEvent<FireEvents> RESPAWN_ANCHOR = StimulusEvent.create(FireEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.createFire(world, pos, igniter);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	StimulusEvent<FireEvents> EXPLOSIVE_FIREBALL_FIRE = StimulusEvent.create(FireEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.createFire(world, pos, igniter);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	StimulusEvent<FireEvents> LIGHTNING = StimulusEvent.create(FireEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.createFire(world, pos, igniter);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult createFire(ServerWorld world, BlockPos pos, @Nullable LivingEntity igniter);

}
