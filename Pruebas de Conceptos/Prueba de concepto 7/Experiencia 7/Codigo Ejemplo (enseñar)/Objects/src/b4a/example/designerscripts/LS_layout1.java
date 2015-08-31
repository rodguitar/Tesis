package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Label1.SetTopAndBottom(5%y,14%y)"[layout1/General script]
views.get("label1").vw.setTop((int)((5d / 100 * height)));
views.get("label1").vw.setHeight((int)((14d / 100 * height) - ((5d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="Label2.SetTopAndBottom(20%y,29%y)"[layout1/General script]
views.get("label2").vw.setTop((int)((20d / 100 * height)));
views.get("label2").vw.setHeight((int)((29d / 100 * height) - ((20d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="LabelValorAnalogo.SetTopAndBottom(29%y,38%y)"[layout1/General script]
views.get("labelvaloranalogo").vw.setTop((int)((29d / 100 * height)));
views.get("labelvaloranalogo").vw.setHeight((int)((38d / 100 * height) - ((29d / 100 * height))));

}
}