package manika.standard.whiteboard;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class RTextAreaHelper 
{
    static final Map<String,String> fileToSyntaxTypeMap = new HashMap<String,String>();
    
    static 
    {
        fileToSyntaxTypeMap.put("java", SyntaxConstants.SYNTAX_STYLE_JAVA);
        fileToSyntaxTypeMap.put("cpp", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
        fileToSyntaxTypeMap.put("py", SyntaxConstants.SYNTAX_STYLE_PYTHON);
        fileToSyntaxTypeMap.put("rb", SyntaxConstants.SYNTAX_STYLE_RUBY);
        fileToSyntaxTypeMap.put("c", SyntaxConstants.SYNTAX_STYLE_C);
    }

    public static List<String> getSupportedSyntaxStyles()
    {
        try
        {
            List<String> syntaxList = new ArrayList();
            Field[] fields = SyntaxConstants.class.getFields();
            for(Field f: fields)
            {
                syntaxList.add((String)f.get(f));
            }
            return syntaxList;
        }
        catch(Exception e)
        {
            System.out.println("Error getting supported syntax styles");
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
    
    public static String getSyntaxStyle(File f)
    {
        String name = f.getName();
        if (name.lastIndexOf('.') > 0)
        {
            String ext = name.substring(name.lastIndexOf('.')+1, name.length());
            if (fileToSyntaxTypeMap.containsKey(ext))
            {
                return fileToSyntaxTypeMap.get(ext);
            }
        }
        return SyntaxConstants.SYNTAX_STYLE_NONE;
    }
}
