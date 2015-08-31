package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_main{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[main/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="LabelTitulo.SetTopAndBottom(15%y,25%y)"[main/General script]
views.get("labeltitulo").vw.setTop((int)((15d / 100 * height)));
views.get("labeltitulo").vw.setHeight((int)((25d / 100 * height) - ((15d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="LabelAnalogo.SetTopAndBottom(40%y,50%y)"[main/General script]
views.get("labelanalogo").vw.setTop((int)((40d / 100 * height)));
views.get("labelanalogo").vw.setHeight((int)((50d / 100 * height) - ((40d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="LabelValorAnalogo.SetTopAndBottom(50%y,60%y)"[main/General script]
views.get("labelvaloranalogo").vw.setTop((int)((50d / 100 * height)));
views.get("labelvaloranalogo").vw.setHeight((int)((60d / 100 * height) - ((50d / 100 * height))));

}
}