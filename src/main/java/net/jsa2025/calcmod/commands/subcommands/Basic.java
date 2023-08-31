package net.jsa2025.calcmod.commands.subcommands;



import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.jsa2025.calcmod.CalcMod;
import net.jsa2025.calcmod.commands.CalcCommand;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.Commands;import net.minecraft.command.CommandSource;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;

import net.minecraft.entity.Entity;


public class Basic {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US")); 


//    public static LiteralArgumentBuilder<CommandSource> registerServer(LiteralArgumentBuilder<CommandSource> command) {
//        command
//        .then(Commands.argument("expression", StringArgumentType.greedyString()).executes((ctx) -> {
//            String[] message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "expression"));
//            CalcCommand.sendMessageServer(ctx.getSource(), message);
//            return 0;
//        }));
//        return command;
//    }
//
    public static String[] execute(ICommandSender sender, String expression) {
        double result = CalcCommand.getParsedExpression(sender.getPosition(), expression);
        String[] message = {expression+" = ", nf.format(result)};
        return message;
    }


}
