package net.kodehawa.mantarobot.data;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.kodehawa.mantarobot.data.data.GlobalUserData;
import net.kodehawa.mantarobot.data.data.GuildData;
import net.kodehawa.mantarobot.commands.currency.entity.player.EntityPlayer;

import java.util.*;

public class Data {
	public List<String> blacklistedGuilds = new ArrayList<>();
	public List<String> blacklistedUsers = new ArrayList<>();
	public String defaultPrefix = "~>";
	public Map<String, GuildData> guilds = new HashMap<>();
	public Map<String, GlobalUserData> users = new HashMap<>();

	public GuildData getGuild(Guild guild, boolean isRewritable) {
		if (isRewritable) return guilds.computeIfAbsent(guild.getId(), s -> new GuildData());
		return guilds.getOrDefault(guild.getId(), new GuildData());
	}

	public String getPrefix(Guild guild) {
		return Optional.ofNullable(getGuild(guild, false).prefix).orElse(defaultPrefix);
	}

	public GlobalUserData getUser(User user, boolean isRewritable) {
		if (isRewritable) return users.computeIfAbsent(user.getId(), s -> new GlobalUserData());
		return users.getOrDefault(user.getId(), new GlobalUserData());
	}

	public EntityPlayer getUser(GuildMessageReceivedEvent event, boolean isRewritable) {
		return getUser(event.getGuild(), event.getAuthor(), isRewritable);
	}

	public EntityPlayer getUser(Guild guild, User user, boolean isRewritable) {
		GuildData guildData = getGuild(guild, isRewritable);

		if (guildData.localMode) {
			if (isRewritable) return guildData.users.computeIfAbsent(user.getId(), s -> new EntityPlayer());
			return guildData.users.getOrDefault(user.getId(), new EntityPlayer());
		}

		return getUser(user, isRewritable);
	}

	public GlobalUserData getUser(Member member, boolean isRewritable) {
		return getUser(member.getUser(), isRewritable);
	}
}
