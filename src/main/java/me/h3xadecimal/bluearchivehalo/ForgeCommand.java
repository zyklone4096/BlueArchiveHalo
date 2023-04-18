package me.h3xadecimal.bluearchivehalo;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ForgeCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "halo";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/halo <key> <value>";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<String>();
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length < 2) {
            iCommandSender.addChatMessage(new ChatComponentText(getCommandUsage(iCommandSender)));
            return;
        }
        if (strings[0].equalsIgnoreCase("type")) {
            try {
                Configuration.type = HaloType.valueOf(strings[1]);
            }catch (Throwable t) {
                iCommandSender.addChatMessage(new ChatComponentText("Unable to set halo type to " + strings[1]));
            }
        } else if (strings[0].equalsIgnoreCase("yOffset")) {
            try {
                float yOffset = new BigDecimal(strings[1]).floatValue();
                if (yOffset < 0.1 || yOffset > 1) {
                    iCommandSender.addChatMessage(new ChatComponentText("yOffset range is 0.1-1.0, target value is " + yOffset));
                    return;
                }
                Configuration.yOffset = new BigDecimal(yOffset*10).intValue();
            }catch (Throwable t) {
                iCommandSender.addChatMessage(new ChatComponentText("Unable to set yOffset to " + strings[1]));
            }
        } else if (strings[0].equalsIgnoreCase("angle")) {
            try {
                int angle = new BigDecimal(strings[1]).intValue();
                if (angle < 20 || angle > 90) {
                    iCommandSender.addChatMessage(new ChatComponentText("yOffset range is 20-90, target value is " + angle));
                    return;
                }
                Configuration.angle = angle;
            }catch (Throwable t) {
                iCommandSender.addChatMessage(new ChatComponentText("Unable to set angle to " + strings[1]));
            }
        }

        try {
            Forge.INSTANCE.saveConfig();
        }catch (Throwable t) {
            iCommandSender.addChatMessage(new ChatComponentText("[WARNING] Unable to save halo configuration: " + t.getMessage()));
            t.printStackTrace();
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        List<String> tabs = new ArrayList<String>();
        if (strings.length == 0) {
            tabs.add("type");
            tabs.add("yOffset");
            tabs.add("angle");
            return tabs;
        }
        if (strings[0].equalsIgnoreCase("type")) {
            if (strings.length == 1) {
                for (HaloType t: HaloType.values()) tabs.add(t.name());
                return tabs;
            }
            for (HaloType t: HaloType.values()) {
                if (t.name().startsWith(strings[0])) tabs.add(t.name());
            }
        }
        return tabs;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
