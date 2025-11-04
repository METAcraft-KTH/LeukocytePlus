package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import nu.metacraft.leukocyte_plus.EventHelper;

@Mixin(WitherBoss.class)
public abstract class MixinWitherEntity extends Monster {

	protected MixinWitherEntity(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(
		method = "customServerAiStep",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"
		)
	)
	public boolean mobTick(boolean original) {
		return EventHelper.shouldWitherGrief(original, (WitherBoss) (Object) this, null);
	}

	@ModifyExpressionValue(
		method = "customServerAiStep",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;MOB:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
		)
	)
	public Level.ExplosionInteraction mobTick(Level.ExplosionInteraction original) {
		return EventHelper.shouldWitherGrief(
				true, (WitherBoss) (Object) this, null
		) ? original : Level.ExplosionInteraction.NONE;
	}

}
