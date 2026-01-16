package dev.cqb13.BaritoneController.hud;

import dev.cqb13.BaritoneController.BaritoneController;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.elements.TextHud;

public class TextPresets {
    public static final HudElementInfo<TextHud> INFO = new HudElementInfo<>(BaritoneController.BaritoneControllerHUD,
            "baritone-controller-text",
            "Displays information about baritone.", TextPresets::create);

    static {
        addPreset("Ticks Remaining in Segment",
                "Ticks Remaining in Segment: {round(baritoneController.ticksInSegment)}", 0);
        addPreset("Ticks Remaining in Goal", "Ticks Remaining in Goal: {round(baritoneController.ticksInGoal)}", 0);
        addPreset("Seconds Remaining in Segment",
                "Seconds Remaining in Segment: {round(baritoneController.secsInSegment)}",
                0);
        addPreset("Seconds Remaining in Goal", "Seconds Remaining in Goal: {round(baritoneController.secsInGoal)}", 0);

    }

    private static TextHud create() {
        return new TextHud(INFO);
    }

    private static HudElementInfo<TextHud>.Preset addPreset(String title, String text, int updateDelay) {
        return INFO.addPreset(title, textHud -> {
            if (text != null)
                textHud.text.set(text);
            if (updateDelay != -1)
                textHud.updateDelay.set(updateDelay);
        });
    }
}
