package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(BedBlock.class)
public class BedBlockMixin {

	@ModifyExpressionValue(
			method = "useWithoutItem",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;BLOCK:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(
			Level.ExplosionInteraction original, BlockState state, Level world, BlockPos pos, Player player
	) {
		return EventHelper.getSourceType(original, ExplosionEvents.BED, world, pos, player);
	}

	@Inject(
		method = "useWithoutItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
		)
	)
	public void grabVarsForFireCheck(
			BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit,
			CallbackInfoReturnable<InteractionResult> cir, @Share("world") LocalRef<Level> worldVar,
			@Share("pos") LocalRef<BlockPos> posVar, @Share("player") LocalRef<Player> playerVar
	) {
		worldVar.set(world);
		posVar.set(pos);
		playerVar.set(player);
	}

	@ModifyArg(
		method = "useWithoutItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
		),
		index = 5
	)
	public boolean setCreateFire(
			boolean createFire, @Share("world") LocalRef<Level> world,
			@Share("pos") LocalRef<BlockPos> pos, @Share("player") LocalRef<Player> player
	) {
		return EventHelper.shouldPlaceFire(createFire, FireEvents.BED, world.get(), pos.get(), player.get());
	}

}
