package eu.fuss4j.util;

import eu.fuss4j.Location;

import java.util.Collection;
import java.util.function.BiFunction;

/**
 * @author Octavian Theodor NITA (https://github.com/octavian-nita/)
 * @version 1.0, Aug 9, 2017
 */
public final class Highlight implements BiFunction<CharSequence, Collection<Location>, String> {

    private final String prefix;

    private final String suffix;

    public Highlight(String prefix, String suffix) {
        this.prefix = prefix == null ? "" : prefix;
        this.suffix = suffix == null ? "" : suffix;
    }

    public Highlight(char prefix, char suffix) {
        this.prefix = Character.toString(prefix);
        this.suffix = Character.toString(suffix);
    }

    public Highlight(String tag) {
        if (tag == null) {
            this.prefix = this.suffix = null;
        } else {
            this.prefix = "<" + tag + ">";
            this.suffix = "</" + tag + ">";
        }
    }

    public Highlight() { this('[', ']'); }

    @Override
    public String apply(CharSequence seq, Collection<Location> locations) {
        if (locations == null || locations.isEmpty()) {
            return seq.toString();
        }

        final StringBuilder buf = new StringBuilder();

        int idx = 0;
        for (Location loc : locations) {

            if (idx < loc.start) {
                buf.append(seq.subSequence(idx, loc.start));
            }

            buf.append(prefix).append(seq.subSequence(loc.start, loc.end)).append(suffix);
            idx = loc.end;

        }
        if (idx < seq.length()) {
            buf.append(seq.subSequence(idx, seq.length()));
        }

        return buf.toString();
    }
}