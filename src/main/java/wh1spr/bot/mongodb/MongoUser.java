package wh1spr.bot.mongodb;

import org.bson.Document;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class MongoUser extends BasicMongoItem {
	
	MongoUser(User user) {
		this(user.getId());
	}
	private MongoUser(String userId) {
		super("users");
		this.setId(userId);
		
		if (!MongoDB.exists(jda.getUserById(userId))) throw new IllegalArgumentException("User doesn't exist in db.");
		
		if (MongoDB.isUpdated(getUser())) 
			if (!update())
				throw new Error("Could not update User " + userId + " in MongoDB.");
	}

	public User getUser() {
		return jda.getUserById(getId());
	}

	//TODO Warnings
	//TODO Bans
	
	/*************
	 *  GETTERS  * Getters assume that what you're doing is correct
	 *  		 * Getters use newest doc version, and only get stuff that can't be easily obtained with guild.
	 *************/
	public String getUserMention() {
		return getDoc().getString("mention");
	}
	
	public boolean hasIntro(Guild guild) {
		if (!MongoDB.getMongoGuild(guild).hasIntro()) return false;
		else if (!getDoc().get("guilds", Document.class)
				.get(guild.getId(), Document.class).containsKey("introID")){
			return false;
		} else {
			return true;
		}
	}
	
	public String getIntroId(Guild guild) {
		if (hasIntro(guild)) {
			return getDoc().get("guilds", Document.class)
					.get(guild.getId(), Document.class).getString("introID");
		} else {
			return null;
		}
	}
	
	/*************
	 *  SETTERS  * Setters assume what you're doing is correct.
	 *  		 * Setters update straight to DB. Getters use newest doc version.
	 *************/
	public void setMention() {
		this.setKey("mention", String.format("%s#%s", getUser().getName(), getUser().getDiscriminator()));			
	}
	
	@Override
	protected boolean update() {
		try {
			setMention();
			MongoDB.addUpdated("u" + getId());
			return true;
		} catch (Exception e) {
			log.error(e, "Couldn't update MongoGuild with ID " + getId());
			return false;
		}
	}
}