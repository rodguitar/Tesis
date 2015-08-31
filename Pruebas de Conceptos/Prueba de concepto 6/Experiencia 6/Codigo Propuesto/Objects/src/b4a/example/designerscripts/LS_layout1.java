package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="ButtonEncender.SetLeftAndRight(10%x,45%x)"[layout1/General script]
views.get("buttonencender").vw.setLeft((int)((10d / 100 * width)));
views.get("buttonencender").vw.setWidth((int)((45d / 100 * width) - ((10d / 100 * width))));
//BA.debugLineNum = 5;BA.debugLine="ButtonEncender.SetTopAndBottom(40%y,60%y)"[layout1/General script]
views.get("buttonencender").vw.setTop((int)((40d / 100 * height)));
views.get("buttonencender").vw.setHeight((int)((60d / 100 * height) - ((40d / 100 * height))));
//BA.debugLineNum = 6;BA.debugLine="ButtonApagar.SetLeftAndRight(55%x,90%x)"[layout1/General script]
views.get("buttonapagar").vw.setLeft((int)((55d / 100 * width)));
views.get("buttonapagar").vw.setWidth((int)((90d / 100 * width) - ((55d / 100 * width))));
//BA.debugLineNum = 7;BA.debugLine="ButtonApagar.SetTopAndBottom(40%y,60%y)"[layout1/General script]
views.get("buttonapagar").vw.setTop((int)((40d / 100 * height)));
views.get("buttonapagar").vw.setHeight((int)((60d / 100 * height) - ((40d / 100 * height))));
//BA.debugLineNum = 8;BA.debugLine="ImageViewLED.SetTopAndBottom(14%y,33%y)"[layout1/General script]
views.get("imageviewled").vw.setTop((int)((14d / 100 * height)));
views.get("imageviewled").vw.setHeight((int)((33d / 100 * height) - ((14d / 100 * height))));
//BA.debugLineNum = 9;BA.debugLine="ImageViewLED.SetLeftAndRight(41%x,59%x)"[layout1/General script]
views.get("imageviewled").vw.setLeft((int)((41d / 100 * width)));
views.get("imageviewled").vw.setWidth((int)((59d / 100 * width) - ((41d / 100 * width))));

}
}