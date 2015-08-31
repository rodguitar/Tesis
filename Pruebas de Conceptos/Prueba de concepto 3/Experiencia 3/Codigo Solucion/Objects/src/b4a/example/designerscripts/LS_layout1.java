package b4a.example.designerscripts;
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
//BA.debugLineNum = 5;BA.debugLine="LabelIngresaTitulo.Height=Panel1.Height/100*15"[layout1/General script]
views.get("labelingresatitulo").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 6;BA.debugLine="LabelIngresaTitulo.Top=(Panel1.Height/100)*2*1"[layout1/General script]
views.get("labelingresatitulo").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*1d));
//BA.debugLineNum = 8;BA.debugLine="EditTextTitulo.Height=Panel1.Height/100*15"[layout1/General script]
views.get("edittexttitulo").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 9;BA.debugLine="EditTextTitulo.Top=(Panel1.Height/100)*2*2+LabelIngresaTitulo.Height"[layout1/General script]
views.get("edittexttitulo").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*2d+(views.get("labelingresatitulo").vw.getHeight())));
//BA.debugLineNum = 11;BA.debugLine="LabelIngresaNT.Height=Panel1.Height/100*15"[layout1/General script]
views.get("labelingresant").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 12;BA.debugLine="LabelIngresaNT.Top=(Panel1.Height/100)*2*3+EditTextTitulo.Height*2"[layout1/General script]
views.get("labelingresant").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*3d+(views.get("edittexttitulo").vw.getHeight())*2d));
//BA.debugLineNum = 14;BA.debugLine="SpinnerNota.Height=Panel1.Height/100*15"[layout1/General script]
views.get("spinnernota").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 15;BA.debugLine="SpinnerNota.Width=Panel1.Width/100*30"[layout1/General script]
views.get("spinnernota").vw.setWidth((int)((views.get("panel1").vw.getWidth())/100d*30d));
//BA.debugLineNum = 16;BA.debugLine="SpinnerNota.Top=(Panel1.Height/100)*2*4+LabelIngresaNT.Height*3"[layout1/General script]
views.get("spinnernota").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*4d+(views.get("labelingresant").vw.getHeight())*3d));
//BA.debugLineNum = 17;BA.debugLine="SpinnerNota.Left=Panel1.Width/100*5"[layout1/General script]
views.get("spinnernota").vw.setLeft((int)((views.get("panel1").vw.getWidth())/100d*5d));
//BA.debugLineNum = 19;BA.debugLine="SpinnerTipo.Height=Panel1.Height/100*15"[layout1/General script]
views.get("spinnertipo").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 20;BA.debugLine="SpinnerTipo.Width=Panel1.Width/100*45"[layout1/General script]
views.get("spinnertipo").vw.setWidth((int)((views.get("panel1").vw.getWidth())/100d*45d));
//BA.debugLineNum = 21;BA.debugLine="SpinnerTipo.Top=(Panel1.Height/100)*2*4+LabelIngresaNT.Height*3"[layout1/General script]
views.get("spinnertipo").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*4d+(views.get("labelingresant").vw.getHeight())*3d));
//BA.debugLineNum = 22;BA.debugLine="SpinnerTipo.Left=(100%x-Panel1.Left*2)/100*50"[layout1/General script]
views.get("spinnertipo").vw.setLeft((int)(((100d / 100 * width)-(views.get("panel1").vw.getLeft())*2d)/100d*50d));
//BA.debugLineNum = 24;BA.debugLine="ButtonIngresar.Height=Panel1.Height/100*15"[layout1/General script]
views.get("buttoningresar").vw.setHeight((int)((views.get("panel1").vw.getHeight())/100d*15d));
//BA.debugLineNum = 25;BA.debugLine="ButtonIngresar.Width=Panel1.Width/100*50"[layout1/General script]
views.get("buttoningresar").vw.setWidth((int)((views.get("panel1").vw.getWidth())/100d*50d));
//BA.debugLineNum = 26;BA.debugLine="ButtonIngresar.Top=(Panel1.Height/100)*2*8+LabelIngresaNT.Height*4"[layout1/General script]
views.get("buttoningresar").vw.setTop((int)(((views.get("panel1").vw.getHeight())/100d)*2d*8d+(views.get("labelingresant").vw.getHeight())*4d));
//BA.debugLineNum = 27;BA.debugLine="ButtonIngresar.HorizontalCenter=Panel1.Width/100*50"[layout1/General script]
views.get("buttoningresar").vw.setLeft((int)((views.get("panel1").vw.getWidth())/100d*50d - (views.get("buttoningresar").vw.getWidth() / 2)));
//BA.debugLineNum = 29;BA.debugLine="ListViewNotas.Top=Panel1.Top*2+Panel1.Height"[layout1/General script]
views.get("listviewnotas").vw.setTop((int)((views.get("panel1").vw.getTop())*2d+(views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 30;BA.debugLine="ListViewNotas.Height=100%y-Panel1.Top*3-Panel1.Height"[layout1/General script]
views.get("listviewnotas").vw.setHeight((int)((100d / 100 * height)-(views.get("panel1").vw.getTop())*3d-(views.get("panel1").vw.getHeight())));
//BA.debugLineNum = 31;BA.debugLine="LabelAcelerometro.SetTopAndBottom(ButtonIngresar.Bottom-10,Panel1.Bottom-10)"[layout1/General script]
views.get("labelacelerometro").vw.setTop((int)((views.get("buttoningresar").vw.getTop() + views.get("buttoningresar").vw.getHeight())-10d));
views.get("labelacelerometro").vw.setHeight((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight())-10d - ((views.get("buttoningresar").vw.getTop() + views.get("buttoningresar").vw.getHeight())-10d)));

}
}