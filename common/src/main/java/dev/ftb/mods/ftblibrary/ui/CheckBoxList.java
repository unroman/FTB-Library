package dev.ftb.mods.ftblibrary.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class CheckBoxList extends Button {
	public static class CheckBoxEntry {
		public String name;
		public int value = 0;
		private CheckBoxList checkBoxList;

		public CheckBoxEntry(String n) {
			name = n;
		}

		public void onClicked(MouseButton button, int index) {
			select((value + 1) % checkBoxList.getValueCount());
			checkBoxList.playClickSound();
		}

		public void addMouseOverText(List<String> list) {
		}

		public CheckBoxEntry select(int v) {
			if (checkBoxList.radioButtons) {
				if (v > 0) {
					for (var entry : checkBoxList.entries) {
						var old1 = entry.value > 0;
						entry.value = 0;

						if (old1) {
							entry.onValueChanged();
						}
					}
				} else {
					return this;
				}
			}

			var old = value;
			value = v;

			if (old != value) {
				onValueChanged();
			}

			return this;
		}

		public void onValueChanged() {
		}
	}

	public final boolean radioButtons;
	private final List<CheckBoxEntry> entries;

	public CheckBoxList(BaseScreen gui, boolean radiobutton) {
		super(gui);
		setSize(10, 2);
		radioButtons = radiobutton;
		entries = new ArrayList<>();
	}

	public int getValueCount() {
		return 2;
	}

	@Override
	public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
	}

	public void drawCheckboxBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
		theme.drawCheckboxBackground(matrixStack, x, y, w, h, radioButtons);
	}

	public void getCheckboxIcon(PoseStack matrixStack, Theme theme, int x, int y, int w, int h, int index, int value) {
		theme.drawCheckbox(matrixStack, x, y, w, h, WidgetType.mouseOver(isMouseOver()), value != 0, radioButtons);
	}

	public void addBox(CheckBoxEntry checkBox) {
		checkBox.checkBoxList = this;
		entries.add(checkBox);
		setWidth(Math.max(width, getGui().getTheme().getStringWidth(checkBox.name)));
		setHeight(height + 11);
	}

	public CheckBoxEntry addBox(String name) {
		var entry = new CheckBoxEntry(name);
		addBox(entry);
		return entry;
	}

	@Override
	public void onClicked(MouseButton button) {
		var y = getMouseY() - getY();

		if (y % 11 == 10) {
			return;
		}

		var i = y / 11;

		if (i >= 0 && i < entries.size()) {
			entries.get(i).onClicked(button, i);
		}
	}

	@Override
	public void addMouseOverText(TooltipList list) {
	}

	@Override
	public void draw(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
		drawBackground(matrixStack, theme, x, y, w, h);

		for (var i = 0; i < entries.size(); i++) {
			var entry = entries.get(i);
			var ey = y + i * 11 + 1;
			drawCheckboxBackground(matrixStack, theme, x, ey, 10, 10);
			getCheckboxIcon(matrixStack, theme, x + 1, ey + 1, 8, 8, i, entry.value);
			theme.drawString(matrixStack, entry.name, x + 12, ey + 1);
			RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		}
	}
}