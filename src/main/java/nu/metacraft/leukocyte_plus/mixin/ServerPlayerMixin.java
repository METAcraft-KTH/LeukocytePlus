package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import nu.metacraft.leukocyte_plus.events.KeepInventoryEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@ModifyExpressionValue(
		method = "restoreFrom",
		slice = @Slice(
				from = @At(
						value = "FIELD",
						target = "Lnet/minecraft/world/level/gamerules/GameRules;KEEP_INVENTORY:Lnet/minecraft/world/level/gamerules/GameRule;",
						opcode = Opcodes.GETSTATIC
				)
		),
		at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"
		)
	)
	public <T> T checkZoneKeepInventory(T original, @Local(argsOnly = true) ServerPlayer deadPlayer) {
		//noinspection unchecked
		return (T) KeepInventoryEvent.checkKeepInventory(deadPlayer, (Boolean) original);
	}

}
