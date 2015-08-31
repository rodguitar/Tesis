package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Label1.SetTopAndBottom(15%y,25%y)"[layout1/General script]
views.get("label1").vw.setTop((int)((15d / 100 * height)));
views.get("label1").vw.setHeight((int)((25d / 100 * height) - ((15d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="Label2.SetTopAndBottom(25%y,35%y)"[layout1/General script]
views.get("label2").vw.setTop((int)((25d / 100 * height)));
views.get("label2").vw.setHeight((int)((35d / 100 * height) - ((25d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="LabelValorAnalogo.SetTopAndBottom(35%y,45%y)"[layout1/General script]
views.get("labelvaloranalogo").vw.setTop((int)((35d / 100 * height)));
views.get("labelvaloranalogo").vw.setHeight((int)((45d / 100 * height) - ((35d / 100 * height))));
//BA.debugLineNum = 6;BA.debugLine="Label3.SetTopAndBottom(45%Y,55%Y)"[layout1/General script]
views.get("label3").vw.setTop((int)((45d / 100 * height)));
views.get("label3").vw.setHeight((int)((55d / 100 * height) - ((45d / 100 * height))));
//BA.debugLineNum = 7;BA.debugLine="LabelValorCelcius.SetTopAndBottom(55%y,65%y)"[layout1/General script]
views.get("labelvalorcelcius").vw.setTop((int)((55d / 100 * height)));
views.get("labelvalorcelcius").vw.setHeight((int)((65d / 100 * height) - ((55d / 100 * height))));

}
}