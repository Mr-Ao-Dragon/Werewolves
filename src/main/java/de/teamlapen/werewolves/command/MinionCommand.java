package de.teamlapen.werewolves.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import de.teamlapen.lib.lib.util.BasicCommand;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.vampirism.entity.minion.MinionEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.vampirism.entity.minion.management.PlayerMinionController;
import de.teamlapen.vampirism.world.MinionWorldData;
import de.teamlapen.werewolves.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.WReference;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

/**
 * @see de.teamlapen.vampirism.command.test.MinionCommand
 */
public class MinionCommand extends BasicCommand {
    private static final DynamicCommandExceptionType fail = new DynamicCommandExceptionType((msg) -> new StringTextComponent("Failed: " + msg));

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("minion")
                .requires(context -> context.hasPermission(PERMISSION_LEVEL_CHEAT))
                .then(Commands.literal("spawnNew")
                        .then(Commands.literal("werewolf").executes(context -> spawnNewWerewolfMinion(context.getSource(), "Minion", 0, 0, false, WerewolfForm.BEAST))
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .executes(context -> spawnNewWerewolfMinion(context.getSource(), StringArgumentType.getString(context, "name"), 0, 0, false, WerewolfForm.BEAST))
                                        .then(Commands.argument("skinType", IntegerArgumentType.integer())
                                                .executes(context -> spawnNewWerewolfMinion(context.getSource(), StringArgumentType.getString(context, "name"), IntegerArgumentType.getInteger(context, "skinType"), 0, false, WerewolfForm.BEAST))
                                                .then(Commands.argument("eyeType", IntegerArgumentType.integer())
                                                        .executes(context -> spawnNewWerewolfMinion(context.getSource(), StringArgumentType.getString(context, "name"), IntegerArgumentType.getInteger(context, "skinType"), IntegerArgumentType.getInteger(context, "eyeType"), false, WerewolfForm.BEAST))
                                                        .then(Commands.argument("glowingEye", BoolArgumentType.bool())
                                                                .executes(context -> spawnNewWerewolfMinion(context.getSource(), StringArgumentType.getString(context, "name"), IntegerArgumentType.getInteger(context, "skinType"), IntegerArgumentType.getInteger(context, "eyeType"), BoolArgumentType.getBool(context, "glowingEye"), WerewolfForm.BEAST))
                                                                .then(Commands.argument("form", WerewolfFormArgument.nonHumanForms())
                                                                        .executes(context -> spawnNewWerewolfMinion(context.getSource(),
                                                                                StringArgumentType.getString(context, "name"),
                                                                                IntegerArgumentType.getInteger(context, "skinType"),
                                                                                IntegerArgumentType.getInteger(context, "eyeType"),
                                                                                BoolArgumentType.getBool(context, "glowingEye"),
                                                                                WerewolfFormArgument.getForm(context, "form"))))))))));
    }


    private static int spawnNewWerewolfMinion(CommandSource ctx, String name, int skinType, int eyeType, boolean glowingEyes, WerewolfForm form) throws CommandSyntaxException {
        WerewolfMinionEntity.WerewolfMinionData data = new WerewolfMinionEntity.WerewolfMinionData(name, skinType, eyeType, glowingEyes, form);
        return spawnNewMinion(ctx, WReference.WEREWOLF_FACTION, data, ModEntities.WEREWOLF_MINION.get());
    }

    private static <T extends MinionData> int spawnNewMinion(CommandSource ctx, IPlayableFaction<?> faction, T data, EntityType<? extends MinionEntity<T>> type) throws CommandSyntaxException {
        PlayerEntity p = ctx.getPlayerOrException();
        FactionPlayerHandler fph = FactionPlayerHandler.get(p);
        if (fph.getMaxMinions() > 0) {
            PlayerMinionController controller = MinionWorldData.getData(ctx.getServer()).getOrCreateController(fph);
            if (controller.hasFreeMinionSlot()) {
                if (fph.getCurrentFaction() == faction) {
                    int id = controller.createNewMinionSlot(data, type);
                    if (id < 0) {
                        throw fail.create("Failed to get new minion slot");
                    }
                    controller.createMinionEntityAtPlayer(id, p);
                } else {
                    throw fail.create("Wrong faction");
                }
            } else {
                throw fail.create("No free slot");
            }
        } else {
            throw fail.create("Can't have minions");
        }
        return 0;
    }
}
