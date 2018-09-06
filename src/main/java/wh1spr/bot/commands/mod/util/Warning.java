package wh1spr.bot.commands.mod.util;

import net.dv8tion.jda.core.entities.User;
import wh1spr.bot.mongodb.MongoUser;

public class Warning extends Action {

	public Warning(String hex) {
		super(hex, "warnings");
	}
	
	public Warning(String hex, User a, User by, String reason) {
		super(hex, "warnings");
		setItem(a,by,reason);
	}
	
	@Override
	public void setItem(User a, User by, String reason) {
		super.setItem(a, by, reason);
		WarnUser ma = new WarnUser(a);
		new MongoUser(by);
		ma.addWarning(this.getHexString());
	}
}
