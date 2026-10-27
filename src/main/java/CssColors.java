import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Catalogue of the standard CSS named colors (e.g. "red", "coral", "darkblue").
 * <p>
 * It has two jobs:
 * <ul>
 *   <li>translate a color name into a {@link Color} that can be drawn;</li>
 *   <li>give back n <b>different</b> color names, so every symbol can be told apart.</li>
 * </ul>
 * Names that are just another spelling of the same color (cyan/aqua, magenta/fuchsia,
 * grey/gray...) are accepted by {@link #toColor(String)} but are never handed out by
 * {@link #distinct(int)}, because two symbols with the same look would be confusing.
 */
public final class CssColors {

    /** Distinct CSS colors, in the order in which they are handed out (vivid ones first). */
    private static final Map<String, Integer> NAMED = new LinkedHashMap<>();

    /** Alternative spellings that point to a color already in NAMED. */
    private static final Map<String, String> ALIASES = new LinkedHashMap<>();

    static {
        add("red", 0xFF0000);
        add("blue", 0x0000FF);
        add("lime", 0x00FF00);
        add("yellow", 0xFFFF00);
        add("fuchsia", 0xFF00FF);
        add("aqua", 0x00FFFF);
        add("orange", 0xFFA500);
        add("green", 0x008000);
        add("purple", 0x800080);
        add("teal", 0x008080);
        add("navy", 0x000080);
        add("maroon", 0x800000);
        add("olive", 0x808000);
        add("black", 0x000000);
        add("gray", 0x808080);
        add("silver", 0xC0C0C0);
        add("antiquewhite", 0xFAEBD7);
        add("aquamarine", 0x7FFFD4);
        add("bisque", 0xFFE4C4);
        add("blanchedalmond", 0xFFEBCD);
        add("blueviolet", 0x8A2BE2);
        add("brown", 0xA52A2A);
        add("burlywood", 0xDEB887);
        add("cadetblue", 0x5F9EA0);
        add("chartreuse", 0x7FFF00);
        add("chocolate", 0xD2691E);
        add("coral", 0xFF7F50);
        add("cornflowerblue", 0x6495ED);
        add("crimson", 0xDC143C);
        add("darkblue", 0x00008B);
        add("darkcyan", 0x008B8B);
        add("darkgoldenrod", 0xB8860B);
        add("darkgray", 0xA9A9A9);
        add("darkgreen", 0x006400);
        add("darkkhaki", 0xBDB76B);
        add("darkmagenta", 0x8B008B);
        add("darkolivegreen", 0x556B2F);
        add("darkorange", 0xFF8C00);
        add("darkorchid", 0x9932CC);
        add("darkred", 0x8B0000);
        add("darksalmon", 0xE9967A);
        add("darkseagreen", 0x8FBC8F);
        add("darkslateblue", 0x483D8B);
        add("darkslategray", 0x2F4F4F);
        add("darkturquoise", 0x00CED1);
        add("darkviolet", 0x9400D3);
        add("deeppink", 0xFF1493);
        add("deepskyblue", 0x00BFFF);
        add("dimgray", 0x696969);
        add("dodgerblue", 0x1E90FF);
        add("firebrick", 0xB22222);
        add("forestgreen", 0x228B22);
        add("gainsboro", 0xDCDCDC);
        add("gold", 0xFFD700);
        add("goldenrod", 0xDAA520);
        add("greenyellow", 0xADFF2F);
        add("hotpink", 0xFF69B4);
        add("indianred", 0xCD5C5C);
        add("indigo", 0x4B0082);
        add("khaki", 0xF0E68C);
        add("lavender", 0xE6E6FA);
        add("lawngreen", 0x7CFC00);
        add("lemonchiffon", 0xFFFACD);
        add("lightblue", 0xADD8E6);
        add("lightcoral", 0xF08080);
        add("lightgoldenrodyellow", 0xFAFAD2);
        add("lightgray", 0xD3D3D3);
        add("lightgreen", 0x90EE90);
        add("lightpink", 0xFFB6C1);
        add("lightsalmon", 0xFFA07A);
        add("lightseagreen", 0x20B2AA);
        add("lightskyblue", 0x87CEFA);
        add("lightslategray", 0x778899);
        add("lightsteelblue", 0xB0C4DE);
        add("limegreen", 0x32CD32);
        add("mediumaquamarine", 0x66CDAA);
        add("mediumblue", 0x0000CD);
        add("mediumorchid", 0xBA55D3);
        add("mediumpurple", 0x9370DB);
        add("mediumseagreen", 0x3CB371);
        add("mediumslateblue", 0x7B68EE);
        add("mediumspringgreen", 0x00FA9A);
        add("mediumturquoise", 0x48D1CC);
        add("mediumvioletred", 0xC71585);
        add("midnightblue", 0x191970);
        add("mistyrose", 0xFFE4E1);
        add("moccasin", 0xFFE4B5);
        add("navajowhite", 0xFFDEAD);
        add("olivedrab", 0x6B8E23);
        add("orangered", 0xFF4500);
        add("orchid", 0xDA70D6);
        add("palegoldenrod", 0xEEE8AA);
        add("palegreen", 0x98FB98);
        add("paleturquoise", 0xAFEEEE);
        add("palevioletred", 0xDB7093);
        add("papayawhip", 0xFFEFD5);
        add("peachpuff", 0xFFDAB9);
        add("peru", 0xCD853F);
        add("pink", 0xFFC0CB);
        add("plum", 0xDDA0DD);
        add("powderblue", 0xB0E0E6);
        add("rebeccapurple", 0x663399);
        add("rosybrown", 0xBC8F8F);
        add("royalblue", 0x4169E1);
        add("saddlebrown", 0x8B4513);
        add("salmon", 0xFA8072);
        add("sandybrown", 0xF4A460);
        add("seagreen", 0x2E8B57);
        add("sienna", 0xA0522D);
        add("skyblue", 0x87CEEB);
        add("slateblue", 0x6A5ACD);
        add("slategray", 0x708090);
        add("springgreen", 0x00FF7F);
        add("steelblue", 0x4682B4);
        add("tan", 0xD2B48C);
        add("thistle", 0xD8BFD8);
        add("tomato", 0xFF6347);
        add("turquoise", 0x40E0D0);
        add("violet", 0xEE82EE);
        add("wheat", 0xF5DEB3);
        add("yellowgreen", 0x9ACD32);
        add("white", 0xFFFFFF);
        add("snow", 0xFFFAFA);
        add("ghostwhite", 0xF8F8FF);
        add("floralwhite", 0xFFFAF0);
        add("whitesmoke", 0xF5F5F5);
        add("seashell", 0xFFF5EE);
        add("ivory", 0xFFFFF0);
        add("mintcream", 0xF5FFFA);
        add("azure", 0xF0FFFF);
        add("aliceblue", 0xF0F8FF);
        add("honeydew", 0xF0FFF0);
        add("lavenderblush", 0xFFF0F5);
        add("oldlace", 0xFDF5E6);
        add("linen", 0xFAF0E6);
        add("cornsilk", 0xFFF8DC);
        add("beige", 0xF5F5DC);
        add("lightyellow", 0xFFFFE0);
        add("lightcyan", 0xE0FFFF);


        ALIASES.put("cyan", "aqua");
        ALIASES.put("magenta", "fuchsia");
        ALIASES.put("grey", "gray");
        ALIASES.put("darkgrey", "darkgray");
        ALIASES.put("darkslategrey", "darkslategray");
        ALIASES.put("dimgrey", "dimgray");
        ALIASES.put("lightgrey", "lightgray");
        ALIASES.put("lightslategrey", "lightslategray");
        ALIASES.put("slategrey", "slategray");
    }

    /** Utility class: it is not meant to be instantiated. */
    private CssColors() {
    }

    private static void add(String name, int rgb) {
        NAMED.put(name, rgb);
    }

    /**
     * Translates a CSS color name into a Color.
     *
     * @param color a CSS color name (in any case)
     * @return the matching Color; black if the name is not a known CSS color
     */
    public static Color toColor(String color) {
        if (color == null) {
            return Color.BLACK;
        }
        String key = color.trim().toLowerCase();
        if (ALIASES.containsKey(key)) {
            key = ALIASES.get(key);
        }
        Integer rgb = NAMED.get(key);
        return rgb == null ? Color.BLACK : new Color(rgb);
    }

    /**
     * Returns n different CSS color names (the first ones of the catalogue).
     * If n is bigger than the number of available colors, all of them are returned,
     * so the length of the result can be smaller than n.
     *
     * @param n how many colors are needed (a value below 1 gives an empty array)
     * @return an array of different color names, with length min(n, available colors)
     */
    public static String[] distinct(int n) {
        List<String> names = new ArrayList<>(NAMED.keySet());
        int amount = Math.max(0, Math.min(n, names.size()));
        return names.subList(0, amount).toArray(new String[0]);
    }
}