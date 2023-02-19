package net.gotzi.drawmachine.sim.gcode.snippet;

public class GCodeSequence implements GCodeSnippet {
    private final GCodeLine[] lines;
    private final long duration;

    public GCodeSequence(GCodeLine[] lines, String str) {
        this.lines = lines;
        this.duration = computeHighestDuration(findDurationCommandSplit(str.split(" ")));
        System.out.println("Duration: " + duration);
    }

    private long computeHighestDuration(int defaultDuration) {
        long max = defaultDuration;

        for (GCodeLine line : this.lines) {
            if ( line != null && line.getDuration() > max)
                max = line.getDuration();

        }

        return max;
    }

    private int findDurationCommandSplit(String[] commandSplit) {
        for (int i = 0; i < commandSplit.length; i++) {
            if (commandSplit[i].charAt(0) == 'D')
                return i;
        }

        return -1;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    public GCodeLine[] getLines() {
        return lines;
    }

    @Override
    public String getSnippetInfo() {
        return "";
    }
}
