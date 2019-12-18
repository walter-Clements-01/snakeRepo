package snake;



import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

public class FontLoader
{
    private static ResourceLoader loader;

    public FontLoader(String fontFilePath)
    {
        loader = new ResourceLoader(fontFilePath);
    }

    public static Font getFont(int fontStyle, float fontSize) throws FontFormatException, IOException, URISyntaxException
    {
        Font font = Font.createFont(Font.TRUETYPE_FONT, loader.getResource());

        font = font.deriveFont(fontStyle, fontSize);

        return font;
    }
}
