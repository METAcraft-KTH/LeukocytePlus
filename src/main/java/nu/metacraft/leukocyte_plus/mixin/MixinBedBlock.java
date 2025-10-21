package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(BedBlock.class)
public class MixinBedBlock {

	@ModifyExpressionValue(
			method = "onUse",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/World$ExplosionSourceType;BLOCK:Lnet/minecraft/world/World$ExplosionSourceType;"
			)
	)
	public World.ExplosionSourceType replaceSourceType(
			World.ExplosionSourceType original, BlockState state, World world, BlockPos pos, PlayerEntity player
	) {
		return EventHelper.getSourceType(original, ExplosionEvents.BED, world, pos, player);
	}

	@Inject(
		method = "onUse",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;Lnet/minecraft/util/math/Vec3d;FZLnet/minecraft/world/World$ExplosionSourceType;)V"
		)
	)
	public void grabVarsForFireCheck(
			BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit,
			CallbackInfoReturnable<ActionResult> cir, @Share("world") LocalRef<World> worldVar,
			@Share("pos") LocalRef<BlockPos> posVar, @Share("player") LocalRef<PlayerEntity> playerVar
	) {
		worldVar.set(world);
		posVar.set(pos);
		playerVar.set(player);
	}

	@ModifyArg(
		method = "onUse",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;Lnet/minecraft/util/math/Vec3d;FZLnet/minecraft/world/World$ExplosionSourceType;)V"
		),
		index = 5
	)
	public boolean setCreateFire(
			boolean createFire, @Share("world") LocalRef<World> world,
			@Share("pos") LocalRef<BlockPos> pos, @Share("player") LocalRef<PlayerEntity> player
	) {
		return EventHelper.shouldPlaceFire(createFire, FireEvents.BED, world.get(), pos.get(), player.get());
	}

}
