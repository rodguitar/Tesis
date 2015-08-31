package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[layout1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="ButtonMsgbox2.SetTopAndBottom(20%y,40%y)"[layout1/General script]
views.get("buttonmsgbox2").vw.setTop((int)((20d / 100 * height)));
views.get("buttonmsgbox2").vw.setHeight((int)((40d / 100 * height) - ((20d / 100 * height))));
//BA.debugLineNum = 4;BA.debugLine="ButtonMsgbox2.SetLeftAndRight(33%x,66%x)"[layout1/General script]
views.get("buttonmsgbox2").vw.setLeft((int)((33d / 100 * width)));
views.get("buttonmsgbox2").vw.setWidth((int)((66d / 100 * width) - ((33d / 100 * width))));
//BA.debugLineNum = 5;BA.debugLine="ButtonInputDialog.SetTopAndBottom(60%y,80%y)"[layout1/General script]
views.get("buttoninputdialog").vw.setTop((int)((60d / 100 * height)));
views.get("buttoninputdialog").vw.setHeight((int)((80d / 100 * height) - ((60d / 100 * height))));
//BA.debugLineNum = 6;BA.debugLine="ButtonInputDialog.SetLeftAndRight(33%x,66%x)"[layout1/General script]
views.get("buttoninputdialog").vw.setLeft((int)((33d / 100 * width)));
views.get("buttoninputdialog").vw.setWidth((int)((66d / 100 * width) - ((33d / 100 * width))));

}
}