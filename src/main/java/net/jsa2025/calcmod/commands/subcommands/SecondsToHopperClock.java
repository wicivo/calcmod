package net.jsa2025.calcmod.commands.subcommands;


import net.jsa2025.calcmod.commands.CalcCommand;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.minecraft.command.ICommandSender;

import net.minecraft.command.Commands;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.entity.Entity;


public class SecondsToHopperClock {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));


//    public static LiteralArgumentBuilder<CommandSource> registerServer(LiteralArgumentBuilder<CommandSource> command) {
//        command
//        .then(Commands.literal("secondstohopperclock").then(Commands.argument("seconds", StringArgumentType.greedyString())
//        .executes(ctx -> {
//            String[] message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "seconds"));
//            CalcCommand.sendMessageServer(ctx.getSource(), message);
//            return 0;
//        }))
//        .then(Commands.literal("help").executes(ctx -> {
//            String[] message = Help.execute("secondstohopperclock");
//            CalcCommand.sendMessageServer(ctx.getSource(), message, true);
//            return 0;
//        })));
//        return command;
//    }

    public static String[] execute(ICommandSender sender, String seconds) {
        double secondsDouble = CalcCommand.getParsedExpression(sender.getCommandSenderEntity(), seconds);
        double hopperclock = Math.ceil(secondsDouble *1.25);
        if (hopperclock > 320) {
            CalcMessageBuilder message = new CalcMessageBuilder().addFromArray(new String[] {"Items needed in hopper clock for ", "input"," seconds = ", "result", "result", " \n§cThis exceeds the maximum number of items in a hopper."}, new String[] {seconds}, new String[] {nf.format(hopperclock), " \nStacks: "+nf.format(Math.floor(hopperclock/64))+" Items: "+nf.format(hopperclock%64)});
            return message;
        } else {
            CalcMessageBuilder message = new CalcMessageBuilder().addFromArray(new String[] {"Items needed in hopper clock for ", "input"," seconds = ", "result", "result"}, new String[] {seconds}, new String[] {nf.format(hopperclock), " \nStacks: "+nf.format(Math.floor(hopperclock/64))+" Items: "+nf.format(hopperclock%64)});
        return message;
        }
    }

    public static String helpMessage = "§b§LSeconds to Hopper Clock:§r§f \nGiven a number of seconds §7§o(can be in expression form)§r§f, returns the number of items needed in a hopper clock to achieve that time. \n§eUsage: /calc secondstohopperclock <seconds>§f";

}
