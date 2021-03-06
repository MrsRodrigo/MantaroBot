/*
 * Copyright (C) 2016-2019 David Alejandro Rubio Escares / Kodehawa
 *
 * Mantaro is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Mantaro is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mantaro.  If not, see http://www.gnu.org/licenses/
 *
 */

package net.kodehawa.mantarobot.core.modules.commands;

import lombok.Getter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.kodehawa.mantarobot.core.modules.commands.base.Category;
import net.kodehawa.mantarobot.core.modules.commands.base.Command;
import net.kodehawa.mantarobot.core.modules.commands.base.CommandPermission;
import net.kodehawa.mantarobot.core.modules.commands.help.HelpContent;
import net.kodehawa.mantarobot.core.modules.commands.i18n.I18nContext;
import net.kodehawa.mantarobot.options.core.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class AliasCommand implements Command {
    private List<String> aliases;
    private final Command command;
    private final String commandName;
    private final String originalName;

    public AliasCommand(String commandName, String originalName, Command command) {
        this.commandName = commandName;
        this.command = command;
        this.originalName = originalName;
        this.aliases = command.getAliases();
    }

    public Category parentCategory() {
        return command.category();
    }

    public String parentName() {
        return originalName;
    }

    @Override
    public Category category() {
        return null; //Alias Commands are hidden
    }

    @Override
    public HelpContent help() {
        return command.help();
    }

    @Override
    public CommandPermission permission() {
        return command.permission();
    }

    @Override
    public void run(GuildMessageReceivedEvent event, I18nContext languageContext, String ignored, String content) {
        command.run(event, languageContext, commandName, content);
    }

    @Override
    public Command addOption(String call, Option option) {
        Option.addOption(call, option);
        return this;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

}
