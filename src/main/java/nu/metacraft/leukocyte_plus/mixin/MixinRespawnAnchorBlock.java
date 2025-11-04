package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(RespawnAnchorBlock.class)
public class MixinRespawnAnchorBlock {

	@ModifyExpressionValue(
			method = "explode",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;BLOCK:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(
			Level.ExplosionInteraction original, BlockState state, Level world, final BlockPos explodedPos
	) {
		return EventHelper.getSourceType(original, ExplosionEvents.RESPAWN_ANCHOR, world, explodedPos, null);
	}

	@Inject(
		method = "explode",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
		)
	)
	public void grabVarsForFireCheck(
			BlockState state, Level world, BlockPos explodedPos, CallbackInfo ci,
			@Share("world") LocalRef<Level> worldVar, @Share("pos") LocalRef<BlockPos> posVar
	) {
		worldVar.set(world);
		posVar.set(explodedPos);
	}

	@ModifyArg(
		method = "explode",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
		),
		index = 5
	)
	public boolean setCreateFire(
			boolean createFire, @Share("world") LocalRef<Level> world,
			@Share("pos") LocalRef<BlockPos> pos
	) {
		return EventHelper.shouldPlaceFire(createFire, FireEvents.RESPAWN_ANCHOR, world.get(), pos.get(), null);
	}

}
