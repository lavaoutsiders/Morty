package wh1spr.bot.dummy;

import net.dv8tion.jda.core.JDA;
import wh1spr.bot.command.CommandRegistry;
import wh1spr.bot.commands.economy.util.EconomyStatus;
import wh1spr.logger.Logger;
import wh1spr.logger.LoggerCache;

public abstract class Bot {
	
	protected Logger log;
	
	public static final String OWNER = "204529799912226816";
	public static final String MAELSTROM = "433693780571586580";
	
	private final String LOGIN_TOKEN;
	private final String PREFIX;
	private final String DATA_PATH;
	
	private final CommandRegistry commandRegistry;
	
	private AutoEventHandlerDummy autoEvents;
	
	protected Bot(String token, String dataPath, String prefix) {
		this.LOGIN_TOKEN = token;
		this.PREFIX = prefix;
		this.DATA_PATH = dataPath;
		
		this.commandRegistry = new CommandRegistry(this);
		
		this.autoEvents = new AutoEventHandlerDummy(this);
	}
	
	protected JDA jda = null;
	
	public abstract void registerCommands();
	public abstract JDA run();
	
	public JDA getJDA() {
		return this.jda;
	}
	
	public String getPrefix() {
		return this.PREFIX;
	}
	
	public String getDataPath() {
		return this.DATA_PATH;
	}
	
	protected String getToken() {
		return this.LOGIN_TOKEN;
	}
	
	public CommandRegistry getCommandRegistry() {
		return this.commandRegistry;
	}
	
	public AutoEventHandlerDummy getAutoEvents() {
		return this.autoEvents;
	}
	
	protected void setAutoEvents(AutoEventHandlerDummy auto) {
		this.autoEvents = auto;
	}
	
	public Logger getLog() {
		if (this.log == null) return LoggerCache.getLogger("MAIN");
		return this.log;
	}
	
	public void shutdown() {
		shutdownBot();
		shutdownLast();
	}
	
	public void shutdownLast() {
		LoggerCache.getLogger("MAIN").info("Bot shut down. Shutting down application.");
		EconomyStatus.shutdown();
		LoggerCache.shutdown();
		System.exit(0);
	}
	
	public abstract void shutdownBot();
}
