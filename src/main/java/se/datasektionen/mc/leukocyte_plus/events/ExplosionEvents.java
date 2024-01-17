package se.datasektionen.mc.leukocyte_plus.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ExplosionEvents {

	StimulusEvent<ExplosionEvents> TNT_FUSE = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> TNT_INSTANT = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> TNT_MINECART_FUSE = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> TNT_MINECART_INSTANT = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> BED = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> RESPAWN_ANCHOR = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> END_CRYSTAL = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> CREEPER = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	StimulusEvent<ExplosionEvents> FIREBALL = StimulusEvent.create(ExplosionEvents.class, ctx -> (world, pos, igniter) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.explode(world, pos, igniter);
				if (result != ActionResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return ActionResult.PASS;
	});

	ActionResult explode(ServerWorld world, BlockPos pos, @Nullable LivingEntity igniter);

}
