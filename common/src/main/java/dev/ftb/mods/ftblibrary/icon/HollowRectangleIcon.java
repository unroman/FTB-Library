package dev.ftb.mods.ftblibrary.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author LatvianModder
 */
public class HollowRectangleIcon extends Icon {
	public Color4I color;
	public boolean roundEdges;

	public HollowRectangleIcon(Color4I c, boolean r) {
		color = c;
		roundEdges = r;
	}

	@Override
	public HollowRectangleIcon copy() {
		return new HollowRectangleIcon(color, roundEdges);
	}

	@Override
	public HollowRectangleIcon withColor(Color4I color) {
		return new HollowRectangleIcon(color, roundEdges);
	}

	@Override
	public HollowRectangleIcon withTint(Color4I c) {
		return withColor(color.withTint(c));
	}

	@Override
	protected void setProperties(IconProperties properties) {
		super.setProperties(properties);
		roundEdges = properties.getBoolean("round_edges", roundEdges);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void draw(PoseStack matrixStack, int x, int y, int w, int h) {
		GuiHelper.drawHollowRect(matrixStack, x, y, w, h, color, roundEdges);
	}

	@Override
	public JsonElement getJson() {
		var o = new JsonObject();
		o.addProperty("id", "hollow_rectangle");
		o.add("color", color.getJson());

		if (roundEdges) {
			o.addProperty("round_edges", true);
		}

		return o;
	}
}