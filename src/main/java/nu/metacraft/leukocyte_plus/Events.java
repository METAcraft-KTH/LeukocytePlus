package nu.metacraft.leukocyte_plus;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import nu.metacraft.leukocyte_plus.events.ItemUseOnBlockEvent;
import nu.metacraft.leukocyte_plus.mixin.AccessorItem;
import xyz.nucleoid.stimuli.Stimuli;

public class Events {

	public static void init() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (player instanceof ServerPlayer serverPlayer) {
				var pos = AccessorItem.callGetPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
				InteractionResult result = InteractionResult.PASS;
				if (
						pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block
				) {
					try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
						result = invokers.get(ItemUseOnBlockEvent.INCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
					}
				}
				if (result == InteractionResult.PASS) {
					pos = AccessorItem.callGetPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
					if (pos.getType() != HitResult.Type.MISS && pos instanceof BlockHitResult block) {
						try (var invokers = Stimuli.select().forEntityAt(player, block.getBlockPos())) {
							result = invokers.get(ItemUseOnBlockEvent.EXCLUDE_FLUIDS).interact(serverPlayer, hand, block.getBlockPos());
						}
					}
				}
				return result;
			}
			return InteractionResult.PASS;
		});
	}

}
