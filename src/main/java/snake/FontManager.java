package snake;



import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FontManager 
{
	public static Font getFont(String key, int size) throws IOException, FontFormatException 
	{
		InputStream font;

		//ResourceLoader fontLoader = new ResourceLoader("small_font.ttf");
		//font=fontLoader.getResource();

		URL url = FontManager.class.getResource("/small_font.ttf");
		//url.toString();
		font=FontManager.class.getResourceAsStream("/small_font.ttf");

		/*try
		{
			font = new FileInputStream("small_font.ttf");
			//font=getClass().getResourceAsStream("/small_font.ttf");
		}
		catch(Exception e)
		{
			font= new FileInputStream(new File("src\\main\\java\\resources\\small_font.ttf"));
		}*/
		//Font smallFont; = Font.createFont(Font.TRUETYPE_FONT, new ResourceLoader("small_font.ttf"));
		Font smallFont = Font.createFont(Font.TRUETYPE_FONT, font);
		if(key.equals("BOLD"))
		smallFont=smallFont.deriveFont(Font.BOLD, size);
		else
		smallFont=smallFont.deriveFont(Font.PLAIN, size);
		return smallFont;
	}
}