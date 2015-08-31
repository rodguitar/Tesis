package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_principal{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[principal/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="ToggleButton1.Top=30%y"[principal/General script]
views.get("togglebutton1").vw.setTop((int)((30d / 100 * height)));
//BA.debugLineNum = 5;BA.debugLine="ToggleButton1.Left=(100%x-(25%x*3))/4"[principal/General script]
views.get("togglebutton1").vw.setLeft((int)(((100d / 100 * width)-((25d / 100 * width)*3d))/4d));
//BA.debugLineNum = 6;BA.debugLine="ToggleButton1.Height=25%x"[principal/General script]
views.get("togglebutton1").vw.setHeight((int)((25d / 100 * width)));
//BA.debugLineNum = 7;BA.debugLine="ToggleButton1.Width=25%x"[principal/General script]
views.get("togglebutton1").vw.setWidth((int)((25d / 100 * width)));
//BA.debugLineNum = 9;BA.debugLine="ToggleButton2.Top=30%y"[principal/General script]
views.get("togglebutton2").vw.setTop((int)((30d / 100 * height)));
//BA.debugLineNum = 10;BA.debugLine="ToggleButton2.Left=(100%x-(25%x*3))/4*2+25%x"[principal/General script]
views.get("togglebutton2").vw.setLeft((int)(((100d / 100 * width)-((25d / 100 * width)*3d))/4d*2d+(25d / 100 * width)));
//BA.debugLineNum = 11;BA.debugLine="ToggleButton2.Height=25%x"[principal/General script]
views.get("togglebutton2").vw.setHeight((int)((25d / 100 * width)));
//BA.debugLineNum = 12;BA.debugLine="ToggleButton2.Width=25%x"[principal/General script]
views.get("togglebutton2").vw.setWidth((int)((25d / 100 * width)));
//BA.debugLineNum = 14;BA.debugLine="ToggleButton3.Top=30%y"[principal/General script]
views.get("togglebutton3").vw.setTop((int)((30d / 100 * height)));
//BA.debugLineNum = 15;BA.debugLine="ToggleButton3.Left=(100%x-(25%x*3))/4*3+25%x*2"[principal/General script]
views.get("togglebutton3").vw.setLeft((int)(((100d / 100 * width)-((25d / 100 * width)*3d))/4d*3d+(25d / 100 * width)*2d));
//BA.debugLineNum = 16;BA.debugLine="ToggleButton3.Height=25%x"[principal/General script]
views.get("togglebutton3").vw.setHeight((int)((25d / 100 * width)));
//BA.debugLineNum = 17;BA.debugLine="ToggleButton3.Width=25%x"[principal/General script]
views.get("togglebutton3").vw.setWidth((int)((25d / 100 * width)));

}
}