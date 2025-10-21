package nu.metacraft.leukocyte_plus;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import nu.metacraft.leukocyte_plus.events.ItemUseOnBlockEvent;
import nu.metacraft.leukocyte_plus.mixin.AccessorItem;
import xyz.nucleoid.stimuli.Stimuli;

public class Events {

	public static void init() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (player instanceof ServerPlayerEntity serverPlayer) {
				var pos = AccessorItem.callRaycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
				ActionResult result = ActionResult.PASS;
				if (
						pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block
				) {
					try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
						result = invokers.get(ItemUseOnBlockEvent.INCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
					}
				}
				if (result == ActionResult.PASS) {
					pos = AccessorItem.callRaycast(world, player, RaycastContext.FluidHandling.NONE);
					if (pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block) {
						try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
							result = invokers.get(ItemUseOnBlockEvent.EXCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
						}
					}
				}
				return result;
			}
			return ActionResult.PASS;
		});
	}

}
