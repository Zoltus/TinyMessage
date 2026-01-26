package fi.sulku.hytale;

import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TinyMsg extends JavaPlugin {

    public TinyMsg(JavaPluginInit init) {
        super(init);
    }

    // Matches <tag>, <tag:arg>, </tag>
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/?)([a-zA-Z0-9_]+)(?::([^>]+))?>");

    private static final Map<String, Color> NAMED_COLORS = new HashMap<>();

    static {
        NAMED_COLORS.put("black", new Color(0, 0, 0));
        NAMED_COLORS.put("dark_blue", new Color(0, 0, 170));
        NAMED_COLORS.put("dark_green", new Color(0, 170, 0));
        NAMED_COLORS.put("dark_aqua", new Color(0, 170, 170));
        NAMED_COLORS.put("dark_red", new Color(170, 0, 0));
        NAMED_COLORS.put("dark_purple", new Color(170, 0, 170));
        NAMED_COLORS.put("gold", new Color(255, 170, 0));
        NAMED_COLORS.put("gray", new Color(170, 170, 170));
        NAMED_COLORS.put("dark_gray", new Color(85, 85, 85));
        NAMED_COLORS.put("blue", new Color(85, 85, 255));
        NAMED_COLORS.put("green", new Color(85, 255, 85));
        NAMED_COLORS.put("aqua", new Color(85, 255, 255));
        NAMED_COLORS.put("red", new Color(255, 85, 85));
        NAMED_COLORS.put("light_purple", new Color(255, 85, 255));
        NAMED_COLORS.put("yellow", new Color(255, 255, 85));
        NAMED_COLORS.put("white", new Color(255, 255, 255));
    }


    private record StyleState(
            Color color,
            List<Color> gradient,
            boolean bold,
            boolean italic,
            boolean underlined,
            boolean monospace, String link) {

        StyleState() {
            this(null, null, false, false, false, false, null);
        }

        StyleState copy() {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withColor(Color color) {
            return new StyleState(color, null, bold, italic, underlined, monospace, link);
        }

        StyleState withGradient(List<Color> gradient) {
            return new StyleState(null, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withBold(boolean bold) {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withItalic(boolean italic) {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withUnderlined(boolean underlined) {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withMonospace(boolean monospace) {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }

        StyleState withLink(String link) {
            return new StyleState(color, gradient, bold, italic, underlined, monospace, link);
        }
    }

    /**
     * Parses a string containing TinyMsg formatting tags and converts it into a Hytale Message.
     * <p>
     * This method processes all supported tags including colors, gradients, styles, and links.
     * Tags can be nested indefinitely for complex formatting.
     * </p>
     *
     * @param text The string to parse, containing TinyMsg formatting tags
     * @return A formatted {@link Message} object ready to be sent to players
     * @throws NullPointerException if text is null
     * @see Message
     */
    public static Message parse(String text) {
        if (!text.contains("<")) {
            return Message.raw(text);
        }

        Message root = Message.empty();

        // Stack keeps track of nested styles.
        // Example: Stack = [Base, Bold, Bold+Red]
        Deque<StyleState> stateStack = new ArrayDeque<>();
        stateStack.push(new StyleState()); // Start with default empty state

        Matcher matcher = TAG_PATTERN.matcher(text);
        int lastIndex = 0;

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            // Handle text BEFORE this tag (using the state at the top of the stack)
            if (start > lastIndex) {
                String content = text.substring(lastIndex, start);
                Message segmentMsg = createStyledMessage(content, stateStack.peek());
                root.insert(segmentMsg);
            }

            // Process the tag to update the Stack
            boolean isClosing = "/".equals(matcher.group(1));
            String tagName = matcher.group(2).toLowerCase();
            String tagArg = matcher.group(3);

            if (isClosing) {
                if (stateStack.size() > 1) {
                    stateStack.pop();
                }
            } else {
                // Start with the current state, and modify it
                StyleState currentState = stateStack.peek();
                StyleState newState = currentState.copy();

                // If checking named colors directly
                if (NAMED_COLORS.containsKey(tagName)) {
                    newState = newState.withColor(NAMED_COLORS.get(tagName));
                } else {
                    switch (tagName) {
                        case "color":
                        case "c":
                        case "colour":
                            Color c = parseColorArg(tagArg);
                            if (c != null) newState = newState.withColor(c);
                            break;

                        case "grnt":
                        case "gradient":
                            if (tagArg != null) {
                                List<Color> colors = parseGradientColors(tagArg);
                                if (!colors.isEmpty()) {
                                    newState = newState.withGradient(colors);
                                }
                            }
                            break;

                        case "bold":
                        case "b":
                            newState = newState.withBold(true);
                            break;

                        case "italic":
                        case "i":
                        case "em":
                            newState = newState.withItalic(true);
                            break;

                        case "underline":
                        case "u":
                            newState = newState.withUnderlined(true);
                            break;

                        case "monospace":
                        case "mono":
                            newState = newState.withMonospace(true);
                            break;

                        case "link":
                        case "url":
                            if (tagArg != null) newState = newState.withLink(tagArg);
                            break;

                        case "reset":
                        case "r":
                            stateStack.clear();
                            newState = new StyleState();
                            break;
                    }
                }
                stateStack.push(newState);
            }

            lastIndex = end;
        }

        if (lastIndex < text.length()) {
            String content = text.substring(lastIndex);
            Message segmentMsg = createStyledMessage(content, stateStack.peek());
            root.insert(segmentMsg);
        }

        return root;
    }

    private static Message createStyledMessage(String content, StyleState state) {
        // If we have a gradient, we must return a container with char-by-char coloring
        if (state.gradient != null && !state.gradient.isEmpty()) {
            return applyGradient(content, state);
        }

        Message msg = Message.raw(content);

        if (state.color != null) msg.color(state.color);
        if (state.bold) msg.bold(true);
        if (state.italic) msg.italic(true);
        if (state.monospace) msg.monospace(true);
        if (state.underlined) msg.getFormattedMessage().underlined = MaybeBool.True;
        if (state.link != null) msg.link(state.link);

        return msg;
    }

    private static Message applyGradient(String text, StyleState state) {
        Message container = Message.empty();
        List<Color> colors = state.gradient;
        int length = text.length();

        for (int index = 0; index < length; index++) {
            char ch = text.charAt(index);
            float progress = index / (float) Math.max(length - 1, 1);
            Color color = interpolateColor(colors, progress);

            Message charMsg = Message.raw(String.valueOf(ch)).color(color);

            if (state.bold) charMsg.bold(true);
            if (state.italic) charMsg.italic(true);
            if (state.monospace) charMsg.monospace(true);
            if (state.underlined) charMsg.getFormattedMessage().underlined = MaybeBool.True;
            if (state.link != null) charMsg.link(state.link);

            container.insert(charMsg);
        }
        return container;
    }

    private static Color parseColorArg(String arg) {
        if (arg == null) return null;
        return NAMED_COLORS.containsKey(arg) ? NAMED_COLORS.get(arg) : parseHexColor(arg);
    }

    private static List<Color> parseGradientColors(String arg) {
        List<Color> colors = new ArrayList<>();
        for (String part : arg.split(":")) {
            Color c = parseColorArg(part);
            if (c != null) colors.add(c);
        }
        return colors;
    }

    private static Color parseHexColor(String hex) {
        try {
            String clean = hex.replace("#", "");
            if (clean.length() == 6) {
                int r = Integer.parseInt(clean.substring(0, 2), 16);
                int g = Integer.parseInt(clean.substring(2, 4), 16);
                int b = Integer.parseInt(clean.substring(4, 6), 16);
                return new Color(r, g, b);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static Color interpolateColor(List<Color> colors, float progress) {
        float clampedProgress = Math.max(0f, Math.min(1f, progress));
        float scaledProgress = clampedProgress * (colors.size() - 1);
        int index = Math.min((int) scaledProgress, colors.size() - 2);
        float localProgress = scaledProgress - index;

        Color c1 = colors.get(index);
        Color c2 = colors.get(index + 1);

        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * localProgress);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * localProgress);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * localProgress);

        return new Color(r, g, b);
    }
}
