package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import nu.metacraft.leukocyte_plus.events.KeepInventoryEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Player.class)
public class PlayerMixin {

	@ModifyExpressionValue(
		method = {"dropEquipment", "getBaseExperienceReward"},
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
		),
		require = 2
	)
	public <T> T checkZoneKeepInventory(T original) {
		//noinspection unchecked
		return (T) KeepInventoryEvent.checkKeepInventory((Player) (Object) this, (Boolean) original);
	}

}
