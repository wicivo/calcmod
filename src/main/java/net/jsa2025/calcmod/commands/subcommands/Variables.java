package net.jsa2025.calcmod.commands.subcommands;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jsa2025.calcmod.commands.CalcCommand;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class Variables {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
    
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> command) {
        command
        .then(ClientCommandManager.literal("variables")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerServer(LiteralArgumentBuilder<ServerCommandSource> command) {
        command
        .then(CommandManager.literal("variables")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static CalcMessageBuilder execute() {
        String message = """
            §bПеременные§f могут использоваться внутри уравнений в любом числовом поле. Они служат для сокращения времени, вместо того чтобы запоминать, что двойной сундук, полный 16 стакабельными предметами, равен 864.
            
                dub: 3456 (default)
                dub64: 3456
                dub16: 864
                dub1: 54
                sb: 1728 (default)
                sb64: 1728
                sb16: 432
                sb1: 27
                stack: 64 (default)
                stack64: 64
                stack16: 16
                stack1: 1
                min: 60
                hour: 3600
                x: player x coord
                y: player y coord
                z: player z coord
                health: player health
                """;
        return new CalcMessageBuilder(message);
    }
}
