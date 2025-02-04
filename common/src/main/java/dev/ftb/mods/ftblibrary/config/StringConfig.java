package dev.ftb.mods.ftblibrary.config;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author LatvianModder
 */
public class StringConfig extends ConfigFromString<String> {
	public static final Color4I COLOR = Color4I.rgb(0xFFAA49);

	public final Pattern pattern;

	public StringConfig(@Nullable Pattern p) {
		pattern = p;
		defaultValue = "";
		value = "";
	}

	public StringConfig() {
		this(null);
	}

	@Override
	public Color4I getColor(@Nullable String v) {
		return COLOR;
	}

	@Override
	public boolean parse(@Nullable Consumer<String> callback, String string) {
		if (pattern == null || pattern.matcher(string).matches()) {
			if (callback != null) {
				callback.accept(string);
			}

			return true;
		}

		return false;
	}

	@Override
	public Component getStringForGUI(@Nullable String v) {
		return v == null ? NULL_TEXT : new TextComponent('"' + v + '"');
	}

	@Override
	public void addInfo(TooltipList list) {
		if (value != null && !value.equals(defaultValue)) {
			list.add(new TranslatableComponent("config.group.value").append(": ").withStyle(ChatFormatting.AQUA)
					.append(new TextComponent("\"" + value + "\"").withStyle(ChatFormatting.WHITE)));
		}

		super.addInfo(list);

		if (pattern != null) {
			list.add(info("Regex", pattern.pattern()));
		}
	}
}