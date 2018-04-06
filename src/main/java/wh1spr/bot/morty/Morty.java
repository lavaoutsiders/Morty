package wh1spr.bot.morty;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.bot.Main;
import wh1spr.bot.command.*;
import wh1spr.bot.commands.*;
import wh1spr.bot.dummy.Bot;
import wh1spr.logger.Logger;
import wh1spr.logger.LoggerCache;

/* 
 * Basically I'm redoing a lot of stuff so I can make multiple bots from a single jar file, controlled by 
 * a .properties file. This is handy dandy if a friend would want his/her own bot instead of Morty, with
 * the same functions.
 */
public class Morty extends Bot {
	
	public Morty(String key, String dataPath, String prefix) {
		super(key, dataPath, prefix);
		log = LoggerCache.newLogger("MORTY", dataPath + "morty.log");
		log = LoggerCache.getLogger("MORTY");
		log.info("Registering commands for Morty.");
		registerCommands();
		log.info("Starting JDA instance.");
		jda = run();
	}
	
	public void registerCommands() {
		CommandRegistry commandRegistry = this.getCommandRegistry();
		// Bot Commands
		commandRegistry.registerCommand(new ChangeGameCommand("changegame", "cg"));
		commandRegistry.registerCommand(new ChangeNameCommand("changename", "cn"));
		commandRegistry.registerCommand(new ShutdownCommand("shutdown", this));
		commandRegistry.registerCommand(new CommandDisableCommand("disablecommand", this.getCommandRegistry(), "dcmd"));
		commandRegistry.registerCommand(new CommandDisableCommand("disablecommand", this.getImageRegistry(), "dcmd"));
		commandRegistry.registerCommand(new SendFromMortyCommand("send"));
		
		// Dev Commands
		commandRegistry.registerCommand(new EvalCommand("eval"));
		commandRegistry.registerCommand(new EmojiToUnicodeCommand("emote"));
		
		// Channel Commands
		commandRegistry.registerCommand(new CleanCommand("clean"));
		commandRegistry.registerCommand(new VoteCommand("vote",this));
//		commandRegistry.registerCommand(new IntroductionCommand("intro")); // I'm gonna redo this
			
		// Image Commands
		commandRegistry.registerCommand(new AddImageCommand("addimage", this.getImageRegistry()));
		commandRegistry.registerCommand(new RemoveImageCommand("removeimage", this.getImageRegistry()));
		this.getImageRegistry().registerAllCommands();
		log.info("All commands registered.");
		
	}
	
	public JDA run() {
		JDA jda = null;
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(this.getToken()).addEventListener(
			        		new CommandHandler(this.getPrefix(), this.getCommandRegistry()),
			        		new CommandHandler(this.getPrefix(), this.getImageRegistry()),
			        		new AutoEventHandler())
			        .buildBlocking();
		} catch (Exception e) {
			log.fatal(e, "JDA instance could not be initialized.");
			shutdown();
		}
		return jda;
	}

	@Override
	public void shutdownBot() {
		if (log == null) log = LoggerCache.getLogger("MORTY");
		log.info("Shutting down Morty.");
		getJDA().shutdown();
		if (jda!=null) getJDA().shutdown();
		
		log.shutdown();
		Main.removeBot("MORTY");
	}
}
