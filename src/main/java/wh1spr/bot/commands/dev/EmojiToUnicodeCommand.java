package wh1spr.bot.commands.dev;

import java.util.List;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import wh1spr.bot.command.Command;
import wh1spr.bot.dummy.Perm;

public class EmojiToUnicodeCommand extends Command {

	public EmojiToUnicodeCommand(String name, String... aliases) {
		super(name, aliases);
		this.setMaelstromOnly(false);
	}

	@Override
	public void onCall(JDA jda, Guild guild, MessageChannel channel, User invoker, Message message, List<String> args) {
		if (!Perm.has(Perm.PROGRAMMER, invoker)) {
			return;
		}
		
		if (args.size() > 0) {
			for (int i = 0; i < args.size(); i++) {
				Emoji emoji = EmojiManager.getByUnicode(args.get(i));
				
				if (emoji == null) {
					channel.sendMessage(":x: I could not recognize this emoticon: " + args.get(i)).queue();
				} else {
					channel.sendMessage(":white_check_mark: Unicode for " + args.get(i) + " is `" + emoji.getUnicode() + "`").queue();
				}
				
				
			}
		}
		success(message);
	}
}
