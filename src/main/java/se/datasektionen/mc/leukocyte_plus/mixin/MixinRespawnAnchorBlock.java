package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.datasektionen.mc.leukocyte_plus.EventHelper;
import se.datasektionen.mc.leukocyte_plus.events.ExplosionEvents;
import se.datasektionen.mc.leukocyte_plus.events.FireEvents;

@Mixin(RespawnAnchorBlock.class)
public class MixinRespawnAnchorBlock {

	@ModifyExpressionValue(
			method = "explode",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/World$ExplosionSourceType;BLOCK:Lnet/minecraft/world/World$ExplosionSourceType;"
			)
	)
	public World.ExplosionSourceType replaceSourceType(
			World.ExplosionSourceType original, BlockState state, World world, final BlockPos explodedPos
	) {
		return EventHelper.getSourceType(original, ExplosionEvents.RESPAWN_ANCHOR, world, explodedPos, null);
	}

	@Inject(
		method = "explode",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;Lnet/minecraft/util/math/Vec3d;FZLnet/minecraft/world/World$ExplosionSourceType;)V"
		)
	)
	public void grabVarsForFireCheck(
			BlockState state, World world, BlockPos explodedPos, CallbackInfo ci,
			@Share("world") LocalRef<World> worldVar, @Share("pos") LocalRef<BlockPos> posVar
	) {
		worldVar.set(world);
		posVar.set(explodedPos);
	}

	@ModifyArg(
		method = "explode",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;Lnet/minecraft/util/math/Vec3d;FZLnet/minecraft/world/World$ExplosionSourceType;)V"
		),
		index = 5
	)
	public boolean setCreateFire(
			boolean createFire, @Share("world") LocalRef<World> world,
			@Share("pos") LocalRef<BlockPos> pos
	) {
		return EventHelper.shouldPlaceFire(createFire, FireEvents.RESPAWN_ANCHOR, world.get(), pos.get(), null);
	}

}
