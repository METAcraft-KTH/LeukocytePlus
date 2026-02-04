package nu.metacraft.leukocyte_plus.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface ArmorDamageEvent {

	StimulusEvent<ArmorDamageEvent> EVENT = StimulusEvent.create(ArmorDamageEvent.class, ctx -> (player, source, attackDamage, itemDamage, slot, item) -> {
		try {
			for (var listener : ctx.getListeners()) {
				var result = listener.shouldDamage(player, source, attackDamage, itemDamage, slot, item);
				if (result != EventResult.PASS) {
					return result;
				}
			}
		} catch (Throwable t) {
			ctx.handleException(t);
		}
		return EventResult.PASS;
	});

	EventResult shouldDamage(LivingEntity player, DamageSource damageSource, float attackDamage, int itemDamage, EquipmentSlot slot, ItemStack stack);

}
