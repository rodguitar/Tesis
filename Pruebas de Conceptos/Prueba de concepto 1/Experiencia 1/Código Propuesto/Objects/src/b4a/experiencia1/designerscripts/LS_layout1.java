package b4a.experiencia1.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="Panel1.Height=51%y"[layout1/General script]
views.get("panel1").vw.setHeight((int)((51d / 100 * height)));
//BA.debugLineNum = 5;BA.debugLine="ListViewNotas.Top=Panel1.Top*2+Panel1.Height"[layout1/General script]
views.get("listviewnotas").vw.setTop((int)((views.get("panel1").vw.getTop())*2d+(views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 6;BA.debugLine="ListViewNotas.Height=100%y-Panel1.Top*3-Panel1.Height"[layout1/General script]
views.get("listviewnotas").vw.setHeight((int)((100d / 100 * height)-(views.get("panel1").vw.getTop())*3d-(views.get("panel1").vw.getHeight())));

}
}