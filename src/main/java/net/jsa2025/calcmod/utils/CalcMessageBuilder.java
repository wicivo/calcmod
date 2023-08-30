package net.jsa2025.calcmod.utils;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.Objects;

public class CalcMessageBuilder {

    public enum MessageType {
        NONE (new String[]{}, 0, 0),
        BASIC(new String[] {"input", " = ", "result"}, 1, 1),
        HELP(new String[]{}, 0, 0);
        final String[] equation;
        final int inputsLength;
        final int resultsLength;
        MessageType(String[] equation, int inputsLength, int resultsLength) {
            this.equation = equation;
            this.inputsLength = inputsLength;
            this.resultsLength = resultsLength;
        }

    }
    MessageType messageType;
    String helpMessage;

    MutableComponent messageText = Component.literal("");

    public CalcMessageBuilder() {
        this.messageType = MessageType.NONE;

    }
    public CalcMessageBuilder(MessageType type, String[] inputs, String[] results) {
        try {
            if ((type.inputsLength != inputs.length) || (type.resultsLength != results.length))
                throw new Exception("Hello");
            messageType = type;
            addFromArray(type.equation, inputs, results);
        } catch (Exception ignored) {

        }

    }

    public CalcMessageBuilder(String helpMessage) {
        this.messageType = MessageType.HELP;
        this.helpMessage = helpMessage;
    }

    public CalcMessageBuilder addString(String text) {
        messageText.append(text);
        return this;
    }
    public CalcMessageBuilder addInput(String text) {
        messageText.append("§b" + text + "§f");
        return this;
    }
    public CalcMessageBuilder addResult(String text) {
        messageText.append(Component.literal("§a" + text + "§f")
                .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text))));
        return this;
    }

    public CalcMessageBuilder addFromArray(String[] template, String[] inputs, String[] results) {
        int resultsAdded = 0;
        int inputsAdded = 0;
        for (String eqnPart : template) {
            if (Objects.equals(eqnPart, "input")) {
                addInput(inputs[inputsAdded]);
                inputsAdded++;
            } else if (Objects.equals(eqnPart, "result")) {
                addResult(results[resultsAdded]);
                resultsAdded++;
            } else {
                addString(eqnPart);
            }
        }
        return this;
    }

    public Component generateStyledText() {
        if (Objects.requireNonNull(this.messageType) == MessageType.HELP) {
            return Component.literal(helpMessage);
        }
        messageText.append(" ");
        messageText.append(Component.literal("§3[Click to Copy]§f").setStyle(net.minecraft.network.chat.Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, messageText.getString().replaceAll("§.", "").replaceAll("§b", "").replaceAll("§7", "").replaceAll("§f", "")))));
        return messageText;
    }


}
