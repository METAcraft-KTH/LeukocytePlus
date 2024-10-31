package se.datasektionen.mc.leukocyte_plus;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import se.datasektionen.mc.leukocyte_plus.events.ItemUseOnBlockEvent;
import se.datasektionen.mc.leukocyte_plus.mixin.AccessorItem;
import xyz.nucleoid.stimuli.Stimuli;

public class Events {

	public static void init() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (player instanceof ServerPlayerEntity serverPlayer) {
				var pos = AccessorItem.callRaycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
				TypedActionResult<ItemStack> result = TypedActionResult.pass(ItemStack.EMPTY);
				if (
						pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block
				) {
					try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
						result = invokers.get(ItemUseOnBlockEvent.INCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
					}
				}
				if (result.getResult() == ActionResult.PASS) {
					pos = AccessorItem.callRaycast(world, player, RaycastContext.FluidHandling.NONE);
					if (pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block) {
						try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
							result = invokers.get(ItemUseOnBlockEvent.EXCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
						}
					}
				}
				return result;
			}
			return TypedActionResult.pass(ItemStack.EMPTY);
		});
	}

}
