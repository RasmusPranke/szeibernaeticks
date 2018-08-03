package main.de.grzb.szeibernaeticks.control;

// TODO: Maybe reintroduce this command?
//
// import net.minecraft.command.CommandException;
// import net.minecraft.command.ICommand;
// import net.minecraft.command.ICommandSender;
// import net.minecraft.server.MinecraftServer;
// import net.minecraft.util.math.BlockPos;
//
// import java.util.ArrayList;
// import java.util.List;
// public class CommandDebug implements ICommand {
//
// @Override
// public int compareTo(ICommand arg0) {
// return 0;
// }
//
// @Override
// public String getName() {
// return "debug";
// }
//
// @Override
// public String getUsage(ICommandSender sender) {
// return "debug <enable/disable/toggle> <DebugTypes...>";
// }
//
// @Override
// public List<String> getAliases() {
// return new ArrayList<String>();
// }
//
// @Override
// public void execute(MinecraftServer server, ICommandSender sender, String[]
// args) throws CommandException {
// if(args.length < 2) {
// return;
// }
//
// boolean value = (args[0].toUpperCase().charAt(0) == 'E');
// boolean toggle = (args[0].toUpperCase().charAt(0) == 'T');
//
// for(String s : args) {
// String actual = s.toUpperCase();
// LogType type = null;
// try {
// type = LogType.valueOf(actual);
// }
// catch(IllegalArgumentException e) {
// }
//
// if(type != null) {
// if(value || (toggle && !Log.getLogger().isEnabled(type))) {
// Log.getLogger().enable(type);
// }
// else {
// Log.getLogger().disable(type);
// }
// }
// }
// }
//
// @Override
// public boolean checkPermission(MinecraftServer server, ICommandSender sender)
// {
// return sender.canUseCommand(2, "");
// }
//
// @Override
// public List<String> getTabCompletions(MinecraftServer server, ICommandSender
// sender, String[] args, BlockPos targetPos) {
// Log.log("Getting Tab Completions", LogType.DEBUG, LogType.COMMAND);
// Log.log("Currently " + args.length + " args!", LogType.DEBUG,
// LogType.COMMAND);
// Log.log("They are:", LogType.DEBUG, LogType.COMMAND);
// for(String s : args) {
// Log.log(s, LogType.DEBUG, LogType.COMMAND);
// }
// ArrayList<String> completions = new ArrayList<String>();
//
// if(args.length == 1) {
// completions.add("enable");
// completions.add("disable");
// completions.add("toggle");
// }
//
// if(args.length > 1) {
// String started = args[args.length - 1].toUpperCase();
// for(LogType logType : LogType.values()) {
// String type = logType.toString().toUpperCase();
// exit:
// if(started.length() < type.length()) {
// for(int i = 0; i < started.length(); i++) {
// if(type.charAt(i) != started.charAt(i)) {
// break exit;
// }
// }
// completions.add(type);
// }
// }
// }
//
// return completions;
// }
//
// @Override
// public boolean isUsernameIndex(String[] args, int index) {
// return false;
// }
//
// }
