package wh1spr.bot.command;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public abstract class Command {

	public Command(String name, String... aliases) {
		this.name = name;
		for (String alias : aliases) {
			this.aliases.add(alias);
		}
	}
	
	//Only CommandRegistry can use this, rest must use CommandInfo
	String getName() {
		return this.name;
	}
	List<String> getAliases() {
		return this.aliases;
	}
	
	protected String name = null;
	protected ArrayList<String> aliases = new ArrayList<String>();
	
	public abstract void onCall(JDA jda, Guild guild, MessageChannel channel, Member invoker, Message message, List<String> args);

}