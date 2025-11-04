package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;

@Mixin(Creeper.class)
public abstract class MixinCreeperEntity extends Monster {

	protected MixinCreeperEntity(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
			method = "explodeCreeper",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(Level.ExplosionInteraction original) {
		return EventHelper.getSourceType(
				original, ExplosionEvents.CREEPER, this, this
		);
	}

}
