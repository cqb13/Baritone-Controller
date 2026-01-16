package dev.cqb13.BaritoneController;

import org.meteordev.starscript.value.Value;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.pathing.calc.IPathingControlManager;
import baritone.api.process.IBaritoneProcess;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.orbit.EventHandler;

public class BaritoneControllerStarscript {
    private static IBaritone baritone;
    private static IPathingControlManager pathingControlManager;

    public static double ticksRemainingInSegment = 0;
    public static double secondsRemainingInSegment = 0;
    public static double ticksRemainingInGoal = 0;
    public static double secondsRemainingInGoal = 0;

    public static boolean baritoneProcessExists = false;

    public static Value getTicksRemainingInSegment() {
        return Value.number(ticksRemainingInSegment);
    }

    public static Value getSecondsRemainingInSegment() {
        return Value.number(secondsRemainingInSegment);
    }

    public static Value getTicksRemainingInGoal() {
        return Value.number(ticksRemainingInGoal);
    }

    public static Value getSecondsRemainingInGoal() {
        return Value.number(secondsRemainingInGoal);
    }

    public static Value isBaritoneProcessExists() {
        return Value.bool(baritoneProcessExists);
    }

    public static void init() {
        baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        pathingControlManager = baritone.getPathingControlManager();

        MeteorClient.EVENT_BUS.subscribe(BaritoneControllerStarscript.class);
    }

    @EventHandler
    private static void onTick(TickEvent.Pre event) {
        if (!Utils.canUpdate()) {
            return;
        }

        IBaritoneProcess process = pathingControlManager.mostRecentInControl().orElse(null);

        if (process == null) {
            baritoneProcessExists = false;
        }

        baritoneProcessExists = true;

        IPathingBehavior pathingBehavior = baritone.getPathingBehavior();

        ticksRemainingInSegment = pathingBehavior.ticksRemainingInSegment().orElse(Double.valueOf(0));
        ticksRemainingInGoal = pathingBehavior.estimatedTicksToGoal().orElse(Double.valueOf(0));

        secondsRemainingInSegment = ticksRemainingInSegment / 20;
        secondsRemainingInGoal = ticksRemainingInGoal / 20;
    }
}
