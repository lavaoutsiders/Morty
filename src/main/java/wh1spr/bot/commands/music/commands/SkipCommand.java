package wh1spr.bot.commands.music.commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import wh1spr.bot.commands.music.AudioScheduler;
import wh1spr.bot.commands.music.GuildMusicManager;
import wh1spr.bot.commands.music.Music;

public class SkipCommand extends AudioCommand {

	public SkipCommand(Music m, String name, String... aliases) {
		super(m, name, aliases);
	}

	@Override
	public void onCall(JDA jda, Guild guild, MessageChannel channel, User invoker, Message message, List<String> args) {
		GuildMusicManager mng = getMusic().getGuildM(guild);
		
		AudioPlayer player = mng.player;
	    AudioScheduler scheduler = mng.scheduler;
		
		if (player.getPlayingTrack() == null) {
			channel.sendMessage("I'm not playing anything.").queue();
		} else {
			channel.sendMessage("Skipped " + player.getPlayingTrack().getInfo().title).queue();
			scheduler.nextTrack();
		}
	}

}