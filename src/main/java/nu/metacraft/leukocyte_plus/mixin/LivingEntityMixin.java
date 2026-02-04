package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import nu.metacraft.leukocyte_plus.events.ArmorDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.nucleoid.stimuli.Stimuli;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@WrapWithCondition(
			method = "doHurtEquipment",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V"
			)
	)
	public boolean shouldDamage(
			ItemStack itemStack, int itemDamage, LivingEntity livingEntity, EquipmentSlot equipmentSlot,
			@Local(argsOnly = true) DamageSource damageSource, @Local(argsOnly = true) float attackDamage
	) {
		try (var invokers = Stimuli.select().forEntity(livingEntity)) {
			var result = invokers.get(ArmorDamageEvent.EVENT).shouldDamage(
					livingEntity, damageSource, attackDamage, itemDamage, equipmentSlot, itemStack
			);
			return switch (result) {
				case DENY -> false;
				case ALLOW, PASS -> true;
			};
		}
	}

}
