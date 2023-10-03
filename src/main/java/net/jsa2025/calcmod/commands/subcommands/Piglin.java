package net.jsa2025.calcmod.commands.subcommands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jsa2025.calcmod.commands.CalcCommand;
import net.jsa2025.calcmod.commands.arguments.BarterSuggestionProvider;
import net.jsa2025.calcmod.commands.arguments.CBarterSuggestionProvider;
import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.text.NumberFormat;
import java.util.Locale;

public class Piglin {

    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> command) {
        command
                .then(ClientCommandManager.literal("barter")
                        .then(ClientCommandManager.literal("toitem")
                        .then(ClientCommandManager.argument("gold", StringArgumentType.string())
                                .then(ClientCommandManager.argument("item", StringArgumentType.string()).suggests(new CBarterSuggestionProvider())
                        .executes((ctx) -> {
                            String gold = StringArgumentType.getString(ctx, "gold");
                            String item = StringArgumentType.getString(ctx, "item");
                            CalcMessageBuilder message = executeToItems(ctx.getSource().getEntity(), gold, item);
                            CalcCommand.sendMessage(ctx.getSource(), message);
                            return 1;
                        }))))
                        .then(ClientCommandManager.literal("togold")
                                .then(ClientCommandManager.argument("numberofitems", StringArgumentType.string())
                                        .then(ClientCommandManager.argument("item", StringArgumentType.string()).suggests(new CBarterSuggestionProvider())
                                                .executes((ctx) -> {
                                                    String gold = StringArgumentType.getString(ctx, "numberofitems");
                                                    String item = StringArgumentType.getString(ctx, "item");
                                                    CalcMessageBuilder message = executeToGold(ctx.getSource().getEntity(), gold, item);
                                                    CalcCommand.sendMessage(ctx.getSource(), message);
                                                    return 1;
                                                }))))
                        .then(ClientCommandManager.literal("help").executes((ctx) -> {
                    CalcMessageBuilder message = Help.execute("barter");
                    CalcCommand.sendMessage(ctx.getSource(), message);
                    return 1;
                })));
        return command;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerServer(LiteralArgumentBuilder<ServerCommandSource> command) {
        command
                .then(CommandManager.literal("barter")
                        .then(CommandManager.literal("toitem")
                        .then(CommandManager.argument("gold", StringArgumentType.string())
                                .then(CommandManager.argument("item", StringArgumentType.string()).suggests(new BarterSuggestionProvider())
                                        .executes((ctx) -> {
                                            String gold = StringArgumentType.getString(ctx, "gold");
                                            String item = StringArgumentType.getString(ctx, "item");
                                            CalcMessageBuilder message = executeToItems(ctx.getSource().getEntity(), gold, item);
                                            CalcCommand.sendMessageServer(ctx.getSource(), message);
                                            return 1;
                                        }))))
                        .then(CommandManager.literal("togold")
                                .then(CommandManager.argument("numberofitems", StringArgumentType.string())
                                        .then(CommandManager.argument("item", StringArgumentType.string()).suggests(new BarterSuggestionProvider())
                                                .executes((ctx) -> {
                                                    String gold = StringArgumentType.getString(ctx, "numberofitems");
                                                    String item = StringArgumentType.getString(ctx, "item");
                                                    CalcMessageBuilder message = executeToGold(ctx.getSource().getEntity(), gold, item);
                                                    CalcCommand.sendMessageServer(ctx.getSource(), message);
                                                    return 1;
                                                }))))
                        .then(CommandManager.literal("help").executes((ctx) -> {
                            CalcMessageBuilder message = Help.execute("barter");
                            CalcCommand.sendMessageServer(ctx.getSource(), message);
                            return 1;
                        })));
        return command;
    }

    public static CalcMessageBuilder executeToItems(Entity player, String gold, String item) {

        double amount_of_items = CalcCommand.getParsedExpression(player, gold)/CBarterSuggestionProvider.barter.get(item);
        CalcMessageBuilder message = new CalcMessageBuilder().addFromArray(new String[] {"Среднее кол-во ", "input", " которое вы получите за ", "input"," золотых слитков = ","result"}, new String[] {item, gold}, new String[] {nf.format(amount_of_items)});
        return message;


    }
    public static CalcMessageBuilder executeToGold(Entity player, String numberofitems, String item) {

        double amount_of_items = CalcCommand.getParsedExpression(player, numberofitems)*CBarterSuggestionProvider.barter.get(item);
        CalcMessageBuilder message = new CalcMessageBuilder().addFromArray(new String[] {"Среднее кол-во слитков для получения ", "input"," ","input"," = ", "result"}, new String[] {numberofitems, item}, new String[] {nf.format(amount_of_items)});
        return message;


    }

    public static String helpMessage = """
            §b§LОбмен:§r§f
                Рассчитывает среднее количество золотых слитков, которое необходимо обменять для получения определенного количества желаемого предмета §7§o(togold)§r§f, или среднее количество предмета, которое будет получено при обмене определенного количества золотых слитков §7§o(toitem)§r§f.   
                §eИспользование: /calc barter togold <кол-во предмета> <предмет>§f
                §eИспользование: /calc barter toitem <кол-во золота> <предмет>§f
                    """;
}
