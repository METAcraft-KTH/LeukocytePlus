package se.datasektionen.mc.leukocyte_plus;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.network.PlayerAssociatedNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import se.datasektionen.mc.leukocyte_plus.events.*;
import se.datasektionen.mc.leukocyte_plus.mixin.AccessorBucketItem;
import se.datasektionen.mc.leukocyte_plus.mixin.AccessorItem;
import se.datasektionen.mc.leukocyte_plus.mixin.AccessorServerChunkLoadingManager;
import xyz.nucleoid.leukocyte.Leukocyte;
import xyz.nucleoid.leukocyte.authority.IndexedAuthorityMap;
import xyz.nucleoid.leukocyte.rule.ProtectionRule;
import xyz.nucleoid.leukocyte.rule.ProtectionRuleMap;
import xyz.nucleoid.leukocyte.rule.enforcer.ProtectionRuleEnforcer;
import xyz.nucleoid.stimuli.EventSource;
import xyz.nucleoid.stimuli.event.EventRegistrar;
import xyz.nucleoid.stimuli.event.EventResult;
import xyz.nucleoid.stimuli.event.block.BlockUseEvent;
import xyz.nucleoid.stimuli.event.entity.EntityUseEvent;
import xyz.nucleoid.stimuli.event.projectile.ProjectileHitEvent;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LeukocytePlusEnforcer implements ProtectionRuleEnforcer {

	public static final LeukocytePlusEnforcer INSTANCE = new LeukocytePlusEnforcer();
	private LeukocytePlusEnforcer() {}

	@Override
	public void applyTo(ProtectionRuleMap ruleMap, EventRegistrar events) {
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_EXPLOSION)).applySimple(ExplosionEvents.TNT_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_EXPLOSION)).applySimple(ExplosionEvents.TNT_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_FUSE_EXPLOSION)).applySimple(ExplosionEvents.TNT_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_INSTANT_EXPLOSION)).applySimple(ExplosionEvents.TNT_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_FUSE_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_FUSE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.TNT_MINECART_INSTANT_EXPLOSION)).applySimple(ExplosionEvents.TNT_MINECART_INSTANT, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.BED_EXPLOSION)).applySimple(ExplosionEvents.BED, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.BED_FIRE)).applySimple(FireEvents.BED, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.RESPAWN_ANCHOR_EXPLOSION)).applySimple(ExplosionEvents.RESPAWN_ANCHOR, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.RESPAWN_ANCHOR_FIRE)).applySimple(FireEvents.RESPAWN_ANCHOR, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_EXPLOSION)).applySimple(ExplosionEvents.END_CRYSTAL, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.CREEPER_EXPLOSION)).applySimple(ExplosionEvents.CREEPER, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.FIREBALL_EXPLOSION)).applySimple(ExplosionEvents.FIREBALL, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.EXPLOSIVE_FIREBALL_FIRE)).applySimple(FireEvents.EXPLOSIVE_FIREBALL_FIRE, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.LIGHTNING_FIRE)).applySimple(FireEvents.LIGHTNING, rule -> {
			return (world, pos, igniter) -> {
				return rule;
			};
		});
		this.forRule(events, ruleMap.test(LeukocytePlusRules.WITHER_GRIEFING)).applySimple(WitherGriefEvent.EVENT, rule -> {
			return (wither, projectile) -> {
				return rule;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.ARMOR_STAND, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (entity instanceof ArmorStandEntity) {
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ARMOUR_STAND_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(ArmorStandEntity.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(stack -> stack.getItem() instanceof ItemFrameItem, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (entity instanceof ItemFrameEntity) {
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_EDIT).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_EDIT)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking((entity, source) -> {
				return entity instanceof ItemFrameEntity frame && !frame.getHeldItemStack().isEmpty() && source.getSource() instanceof ServerPlayerEntity;
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.ITEM_FRAME_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking((entity, source) -> {
				return entity instanceof ItemFrameEntity frame && (frame.getHeldItemStack().isEmpty() || !(source.getSource() instanceof ServerPlayerEntity));
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PAINTING_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.PAINTING, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PAINTING_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(PaintingEntity.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_PLACE)
		)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(Items.END_CRYSTAL, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.END_CRYSTAL_BREAK).orElse(
				ruleMap.test(LeukocytePlusRules.ENTITY_BLOCK_BREAK)
		)).applySimple(SpecialEntityDamageEvent.EVENT, rule -> {
			return preventBreaking(EndCrystalEntity.class, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.WATER_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PLACE)
		)).applySimple(ItemUseOnBlockEvent.EXCLUDE_FLUIDS, rule -> {
			return preventUse((player, hand, pos) -> {
				var stack = player.getStackInHand(hand);
				if (stack.getItem() instanceof AccessorBucketItem bucket && bucket.getFluid().matchesType(Fluids.WATER)) {
					if (stack.getItem() instanceof EntityBucketItem) {
						return !player.getWorld().getBlockState(pos).isOf(Blocks.WATER);
					}
					return true;
				}
				return false;
			}, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.WATER_PICKUP).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PICKUP)
		)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.WATER, Blocks.WATER_CAULDRON, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.LAVA_PICKUP).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PICKUP)
		)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.LAVA, Blocks.LAVA_CAULDRON, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.LAVA_PLACE).orElse(
				ruleMap.test(LeukocytePlusRules.FLUID_PLACE)
		)).applySimple(ItemUseOnBlockEvent.EXCLUDE_FLUIDS, rule -> {
			return preventUse(stack -> stack.getItem() instanceof AccessorBucketItem bucket && bucket.getFluid().matchesType(Fluids.LAVA), rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PICKUP_FISH)).applySimple(EntityUseEvent.EVENT, rule -> {
			return (player, entity, hand, hitResult) -> {
				if (
						entity instanceof Bucketable &&
						player.getStackInHand(hand).getItem() == Items.WATER_BUCKET &&
						entity.isAlive()
				) {
					if (rule == EventResult.DENY) {
						syncHandStack(player, hand);
						//Re-add the entity since the client will delete it even if we cancel the event.
						restoreEntityForClient(entity, player);
					}
					return rule == EventResult.ALLOW ? EventResult.PASS : rule;
				}
				return EventResult.PASS;
			};
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PLACE_FISH)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventUse(stack -> stack.getItem() instanceof EntityBucketItem, rule);
		});

		this.forRule(events, ruleMap.test(LeukocytePlusRules.PLACE_FIRE)).applySimple(BlockUseEvent.EVENT, rule -> {
			return preventPlace(stack -> stack.isOf(Items.FLINT_AND_STEEL) || stack.isOf(Items.FIRE_CHARGE), rule);
		});

		this.forRule(
				events, ruleMap.test(LeukocytePlusRules.PROJECTILES_BREAK_BLOCKS).orElse(
						ruleMap.test(ProtectionRule.BREAK)
				)
		).applySimple(ProjectileBreaksBlocks.EVENT, rule -> {
			return (projectile, world) -> rule;
		});

		//This is just for consistency, vanilla leukocyte does prevent placement of powder snow with the place rule, but not picking it up with a bucket.
		this.forRule(events, ruleMap.test(ProtectionRule.BREAK)).applySimple(ItemUseOnBlockEvent.INCLUDE_FLUIDS, rule -> {
			return preventFluidPickup(Blocks.POWDER_SNOW, Blocks.POWDER_SNOW_CAULDRON, rule);
		});


		this.forRule(events, ruleMap.test(ProtectionRule.PVP)).applySimple(TamedWolfAggroEvent.EVENT, rule -> {
			return (wolf, owner, target) -> target instanceof PlayerEntity ? rule : EventResult.PASS;
		});
		this.forRule(events, ruleMap.test(ProtectionRule.ATTACK)).applySimple(TamedWolfAggroEvent.EVENT, rule -> {
			return (wolf, owner, target) -> rule;
		});


		this.forRule(events, ruleMap.test(ProtectionRule.PVP)).applySimple(ProjectileHitEvent.ENTITY, rule -> {
			return (projectile, result) -> fixProjectile(projectile, result.getEntity() instanceof PlayerEntity ? rule : EventResult.PASS);
		});
		this.forRule(events, ruleMap.test(ProtectionRule.ATTACK)).applySimple(ProjectileHitEvent.ENTITY, rule -> {
			return (projectile, result) -> fixProjectile(projectile, rule);
		});
	}

	protected void restoreEntityForClients(Entity entity) {
		if (entity.getWorld() instanceof ServerWorld world) {
			var tracker = ((AccessorServerChunkLoadingManager) world.getChunkManager().chunkLoadingManager).getEntityTrackers().get(
					entity.getId()
			);
			restoreEntityForClients(
					entity,
					((AccessorServerChunkLoadingManager.EntityTracker) tracker).getListeners().stream().map(
							PlayerAssociatedNetworkHandler::getPlayer
					),
					tracker
			);
		}
	}

	protected void restoreEntityForClients(
			Entity entity, Stream<ServerPlayerEntity> players,
			ServerChunkLoadingManager.EntityTracker tracker
	) {
		var entry = ((AccessorServerChunkLoadingManager.EntityTracker) tracker).getEntry();
		players.forEach(player -> player.networkHandler.sendPacket(entity.createSpawnPacket(entry)));
	}

	protected void restoreEntityForClients(Entity entity, ServerWorld world, Stream<ServerPlayerEntity> players) {
		var tracker = ((AccessorServerChunkLoadingManager) world.getChunkManager().chunkLoadingManager).getEntityTrackers().get(
				entity.getId()
		);
		restoreEntityForClients(entity, players, tracker);
	}

	protected void restoreEntityForClient(Entity entity, ServerPlayerEntity player) {
		restoreEntityForClients(entity, player.getServerWorld(), Stream.of(player));
	}

	protected EventResult fixProjectile(ProjectileEntity projectile, EventResult rule) {
		if (rule == EventResult.DENY && projectile instanceof PersistentProjectileEntity) {
			restoreEntityForClients(projectile);
		}
		return rule;
	}

	protected SpecialEntityDamageEvent preventBreaking(BiPredicate<Entity, DamageSource> predicate, EventResult rule) {
		return (entity, source, amount) -> {
			if (predicate.test(entity, source)) {
				if (source.getAttacker() instanceof ServerPlayerEntity player) {
					//Allow admins to break the entity.
					var authorities = Leukocyte.get(player.getServer()).getAuthorities();
					if (authorities instanceof IndexedAuthorityMap auth) {
						for (var authority : auth.select(player.getWorld().getRegistryKey(), SpecialEntityDamageEvent.EVENT)) {
							if (authority.getEventFilter().accepts(EventSource.forEntity(player))) {
								return rule;
							}
						}
						return EventResult.PASS;
					}
				}
				return rule;
			} else {
				return EventResult.PASS;
			}
		};
	}

	protected SpecialEntityDamageEvent preventBreaking(Predicate<Entity> predicate, EventResult rule) {
		return preventBreaking((entity, source) -> predicate.test(entity), rule);
	}

	protected SpecialEntityDamageEvent preventBreaking(Class<? extends Entity> entityClass, EventResult rule) {
		return preventBreaking(entityClass::isInstance, rule);
	}

	protected ItemUseOnBlockEvent preventUse(Item item, EventResult rule) {
		return preventUse(stack -> stack.isOf(item), rule);
	}

	protected ItemUseOnBlockEvent preventFluidPickup(Block fluid, Block cauldron, EventResult rule) {
		return preventUse((player, hand, pos) -> {
			var handStack = player.getStackInHand(hand);
			if (handStack.isOf(Items.BUCKET) && rule == EventResult.DENY) {
				var result = AccessorItem.callRaycast(player.getWorld(), player, RaycastContext.FluidHandling.SOURCE_ONLY);
				if (
						result.getType() != HitResult.Type.MISS &&
						(
								player.getWorld().getBlockState(result.getBlockPos()).isOf(fluid) ||
										player.getWorld().getBlockState(result.getBlockPos()).isOf(cauldron)
						)
				) {
					if (handStack.getCount() > 1) {
						//Synchronise the slot where the bucket would be placed.
						syncInventorySlot(player, player.getInventory().getEmptySlot());
					} else {
						syncHandStack(player, hand);
					}
					return true;
				}
				return false;
			}
			return false;
		}, rule);
	}

	protected ItemUseOnBlockEvent preventUse(PlayerHandPredicate checker, EventResult rule) {
		return (player, hand, pos) -> {
			if (checker.test(player, hand, pos) && rule == EventResult.DENY) {
				syncHandStack(player, hand);
				return rule.asActionResult();
			}
			return ActionResult.PASS;
		};
	}

	public interface PlayerHandPredicate {
		boolean test(ServerPlayerEntity player, Hand hand, BlockPos pos);
	}

	protected ItemUseOnBlockEvent preventUse(Predicate<ItemStack> checker, EventResult rule) {
		return preventUse((player, hand, pos) -> checker.test(player.getStackInHand(hand)), rule);
	}

	protected BlockUseEvent preventPlace(PlayerHandPredicate checker, EventResult rule) {
		return (player, hand, hitResult) -> {
			if (checker.test(player, hand, hitResult.getBlockPos())) {
				if (rule == EventResult.DENY) {
					syncHandStack(player, hand);
				}
				return rule == EventResult.ALLOW ? ActionResult.PASS : rule.asActionResult();
			} else {
				return ActionResult.PASS;
			}
		};
	}

	protected BlockUseEvent preventPlace(Predicate<ItemStack> checker, EventResult rule) {
		return preventPlace((player, hand, pos) -> checker.test(player.getStackInHand(hand)), rule);
	}

	protected BlockUseEvent preventPlace(Item item, EventResult rule) {
		return preventPlace(stack -> stack.isOf(item), rule);
	}

	protected void syncInventorySlot(ServerPlayerEntity player, int slot) {
		player.networkHandler.sendPacket(
				player.getInventory().createSlotSetPacket(slot)
		);
	}

	protected void syncHandStack(ServerPlayerEntity player, Hand hand) {
		//Synchronise the given hand with the client, since the client will assume the event was not cancelled and use up the item.
		if (hand.equals(Hand.MAIN_HAND)) {
			syncInventorySlot(player, player.getInventory().selectedSlot);
		} else {
			syncInventorySlot(player, player.getInventory().main.size() + player.getInventory().armor.size());
		}
	}
}
