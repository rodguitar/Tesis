package b4a.example;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
//        try {
//            if (processBA.subExists("activity_actionbarhomeclick")) {
//                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
//                    getClass().getMethod("getActionBar").invoke(this), true);
//                BA.Log("adding event");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		return true;
	}   
// @Override
// public boolean onOptionsItemSelected(android.view.MenuItem item) {
//    if (item.getItemId() == 16908332) {
//        processBA.raiseEvent(null, "activity_actionbarhomeclick");
//        return true;
//    }
//    else
//        return super.onOptionsItemSelected(item); 
//}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static float _fontsize = 0f;
public static double _maxval = 0;
public static double _minval = 0;
public static double _scalemin = 0;
public static double _scalemax = 0;
public static double _scaleinterval = 0;
public static anywheresoftware.b4a.phone.Phone.PhoneAccelerometer _mi_acelerometro = null;
public static anywheresoftware.b4a.objects.Timer _relojacelerometro = null;
public static com.rootsoft.audiorecorder.AudioRecorder _ar = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mp = null;
public static anywheresoftware.b4a.phone.Phone.VoiceRecognition _vr = null;
public static anywheresoftware.b4a.obejcts.TTS _tts1 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpie = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tabhost1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _legend = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnernota = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listviewnotas = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittexttitulo = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnertipo = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _mi_cursor = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _mi_cursorbusca = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittextbusca = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listviewresultados = null;
public static boolean _pausa = false;
public static String _comentario_texto = "";
public static String _nombre_voz = "";
public anywheresoftware.b4a.objects.ButtonWrapper _buttonstop = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog _idtexto = null;
public b4a.example.charts _charts = null;
public b4a.example.dbapp _dbapp = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 61;BA.debugLine="If FirstTime=True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 62;BA.debugLine="DBApp.CreaBD";
mostCurrent._dbapp._creabd(mostCurrent.activityBA);
 //BA.debugLineNum = 63;BA.debugLine="AR.Initialize()";
_ar.Initialize(processBA);
 //BA.debugLineNum = 65;BA.debugLine="VR.Initialize(\"VR\")";
_vr.Initialize("VR");
 //BA.debugLineNum = 66;BA.debugLine="TTS1.Initialize(\"TTS1\")";
_tts1.Initialize(processBA,"TTS1");
 };
 //BA.debugLineNum = 68;BA.debugLine="VR.Prompt = \"Habla ahora!\"";
_vr.setPrompt("Habla ahora!");
 //BA.debugLineNum = 70;BA.debugLine="GD.SetOnGestureListener(Activity, \"Gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._activity.getObject()),"Gesture");
 //BA.debugLineNum = 72;BA.debugLine="TabHost1.Initialize(\"TabHost1\")";
mostCurrent._tabhost1.Initialize(mostCurrent.activityBA,"TabHost1");
 //BA.debugLineNum = 75;BA.debugLine="Activity.AddView(TabHost1, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._tabhost1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 76;BA.debugLine="TabHost1.AddTab(\"Ingreso de Reseñas\",\"Layout1\")";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Ingreso de Reseñas","Layout1");
 //BA.debugLineNum = 77;BA.debugLine="Activity.Title=\"Desafío 1\"";
mostCurrent._activity.setTitle((Object)("Desafío 1"));
 //BA.debugLineNum = 79;BA.debugLine="LLenaSpinnerNota";
_llenaspinnernota();
 //BA.debugLineNum = 80;BA.debugLine="SpinnerTipo.Add(\"Solo Título\")";
mostCurrent._spinnertipo.Add("Solo Título");
 //BA.debugLineNum = 81;BA.debugLine="SpinnerTipo.Add(\"Texto\")";
mostCurrent._spinnertipo.Add("Texto");
 //BA.debugLineNum = 82;BA.debugLine="SpinnerTipo.Add(\"Voz\")";
mostCurrent._spinnertipo.Add("Voz");
 //BA.debugLineNum = 84;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 86;BA.debugLine="TabGraficoPie";
_tabgraficopie();
 //BA.debugLineNum = 87;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 //BA.debugLineNum = 88;BA.debugLine="TabBuscar";
_tabbuscar();
 //BA.debugLineNum = 90;BA.debugLine="Mi_acelerometro.StartListening(\"Sensor\")";
_mi_acelerometro.StartListening(processBA,"Sensor");
 //BA.debugLineNum = 91;BA.debugLine="RelojAcelerometro.Initialize(\"Intervalo\",100)";
_relojacelerometro.Initialize(processBA,"Intervalo",(long) (100));
 //BA.debugLineNum = 92;BA.debugLine="RelojAcelerometro.Enabled=True";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 93;BA.debugLine="MP.Initialize 'Inicializa el media player";
_mp.Initialize();
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="Mi_acelerometro.StopListening";
_mi_acelerometro.StopListening(processBA);
 //BA.debugLineNum = 103;BA.debugLine="RelojAcelerometro.Enabled=False";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 97;BA.debugLine="Mi_acelerometro.StartListening(\"Sensor\")";
_mi_acelerometro.StartListening(processBA,"Sensor");
 //BA.debugLineNum = 98;BA.debugLine="RelojAcelerometro.Enabled=True";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _addvalorgrafico(anywheresoftware.b4a.objects.collections.Map _datos) throws Exception{
b4a.example.charts._piedata _pd = null;
String _k = "";
 //BA.debugLineNum = 120;BA.debugLine="Sub addValorGrafico(datos As Map)";
 //BA.debugLineNum = 122;BA.debugLine="Dim PD As PieData";
_pd = new b4a.example.charts._piedata();
 //BA.debugLineNum = 123;BA.debugLine="PD.Initialize";
_pd.Initialize();
 //BA.debugLineNum = 124;BA.debugLine="PD.Target = pnlPie 'Set the target view";
_pd.Target = mostCurrent._pnlpie;
 //BA.debugLineNum = 126;BA.debugLine="For Each k As String In datos.Keys";
final anywheresoftware.b4a.BA.IterableList group78 = _datos.Keys();
final int groupLen78 = group78.getSize();
for (int index78 = 0;index78 < groupLen78 ;index78++){
_k = BA.ObjectToString(group78.Get(index78));
 //BA.debugLineNum = 127;BA.debugLine="Charts.AddPieItem(PD, k, datos.Get(k), 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,_k,(float)(BA.ObjectToNumber(_datos.Get((Object)(_k)))),(int) (0));
 }
;
 //BA.debugLineNum = 130;BA.debugLine="PD.GapDegrees = 0 'Total size of gaps between slices. Set to 0 for no gaps.";
_pd.GapDegrees = (int) (0);
 //BA.debugLineNum = 131;BA.debugLine="PD.LegendBackColor = Colors.ARGB(150, 100, 100, 100) 'The background color of the legend bitmap.";
_pd.LegendBackColor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (100),(int) (100),(int) (100));
 //BA.debugLineNum = 137;BA.debugLine="legend = Charts.DrawPie(PD, Colors.Gray, True)";
mostCurrent._legend = mostCurrent._charts._drawpie(mostCurrent.activityBA,_pd,anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 139;BA.debugLine="If ImageView1.IsInitialized=False Then ImageView1.Initialize(\"\")";
if (mostCurrent._imageview1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._imageview1.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 140;BA.debugLine="ImageView1.RemoveView";
mostCurrent._imageview1.RemoveView();
 //BA.debugLineNum = 141;BA.debugLine="ImageView1.SetBackgroundImage(legend)";
mostCurrent._imageview1.SetBackgroundImage((android.graphics.Bitmap)(mostCurrent._legend.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="pnlPie.AddView(ImageView1, 10dip, 10dip, legend.Width, legend.Height)";
mostCurrent._pnlpie.AddView((android.view.View)(mostCurrent._imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),mostCurrent._legend.getWidth(),mostCurrent._legend.getHeight());
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _buttoningresar_click() throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Sub ButtonIngresar_Click";
 //BA.debugLineNum = 219;BA.debugLine="If EditTextTitulo.Text.Trim=\"\" Then";
if ((mostCurrent._edittexttitulo.getText().trim()).equals("")) { 
 //BA.debugLineNum = 220;BA.debugLine="Msgbox(\"Falta ingresar el Título\",\"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Falta ingresar el Título","Error",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 224;BA.debugLine="DBApp.InsertaReview(EditTextTitulo.Text,SpinnerNota.SelectedItem,SpinnerTipo.SelectedItem,comentario_texto,nombre_voz)";
mostCurrent._dbapp._insertareview(mostCurrent.activityBA,mostCurrent._edittexttitulo.getText(),mostCurrent._spinnernota.getSelectedItem(),mostCurrent._spinnertipo.getSelectedItem(),mostCurrent._comentario_texto,mostCurrent._nombre_voz);
 //BA.debugLineNum = 226;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 228;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 //BA.debugLineNum = 230;BA.debugLine="Msgbox(\"Has ingresado tu Reseña correctamente\",\"Exito\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Has ingresado tu Reseña correctamente","Exito",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _buttonstop_click() throws Exception{
 //BA.debugLineNum = 417;BA.debugLine="Sub ButtonStop_Click";
 //BA.debugLineNum = 418;BA.debugLine="ToastMessageShow(\"Grabación detenida.\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Grabación detenida.",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 419;BA.debugLine="AR.Stop";
_ar.stop();
 //BA.debugLineNum = 420;BA.debugLine="ButtonStop.Enabled=False";
mostCurrent._buttonstop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _calculaestadistica() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _sumast = 0;
int _sumat = 0;
int _sumav = 0;
int _i = 0;
 //BA.debugLineNum = 146;BA.debugLine="Sub CalculaEstadistica As Map";
 //BA.debugLineNum = 148;BA.debugLine="Dim Mapa As Map";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 149;BA.debugLine="Dim sumaST As Int";
_sumast = 0;
 //BA.debugLineNum = 150;BA.debugLine="Dim sumaT As Int";
_sumat = 0;
 //BA.debugLineNum = 151;BA.debugLine="Dim sumaV As Int";
_sumav = 0;
 //BA.debugLineNum = 153;BA.debugLine="If Mi_Cursor.RowCount>0 Then";
if (mostCurrent._mi_cursor.getRowCount()>0) { 
 //BA.debugLineNum = 155;BA.debugLine="For i=0 To Mi_Cursor.RowCount-1";
{
final int step95 = 1;
final int limit95 = (int) (mostCurrent._mi_cursor.getRowCount()-1);
for (_i = (int) (0); (step95 > 0 && _i <= limit95) || (step95 < 0 && _i >= limit95); _i = ((int)(0 + _i + step95))) {
 //BA.debugLineNum = 157;BA.debugLine="Mi_Cursor.Position=i";
mostCurrent._mi_cursor.setPosition(_i);
 //BA.debugLineNum = 159;BA.debugLine="Select Mi_Cursor.GetString(\"tipo\")";
switch (BA.switchObjectToInt(mostCurrent._mi_cursor.GetString("tipo"),"Solo Título","Texto","Voz")) {
case 0:
 //BA.debugLineNum = 162;BA.debugLine="sumaST=sumaST+1";
_sumast = (int) (_sumast+1);
 break;
case 1:
 //BA.debugLineNum = 165;BA.debugLine="sumaT=sumaT+1";
_sumat = (int) (_sumat+1);
 break;
case 2:
 //BA.debugLineNum = 168;BA.debugLine="sumaV=sumaV+1";
_sumav = (int) (_sumav+1);
 break;
}
;
 }
};
 };
 //BA.debugLineNum = 176;BA.debugLine="If Mapa.IsInitialized=False Then Mapa.Initialize";
if (_mapa.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_mapa.Initialize();};
 //BA.debugLineNum = 177;BA.debugLine="Mapa.Put(\"Solo Título\",sumaST)";
_mapa.Put((Object)("Solo Título"),(Object)(_sumast));
 //BA.debugLineNum = 178;BA.debugLine="Mapa.Put(\"Texto\",sumaT)";
_mapa.Put((Object)("Texto"),(Object)(_sumat));
 //BA.debugLineNum = 179;BA.debugLine="Mapa.Put(\"Voz\",sumaV)";
_mapa.Put((Object)("Voz"),(Object)(_sumav));
 //BA.debugLineNum = 181;BA.debugLine="sumaST = 0";
_sumast = (int) (0);
 //BA.debugLineNum = 182;BA.debugLine="sumaT = 0";
_sumat = (int) (0);
 //BA.debugLineNum = 183;BA.debugLine="sumaV = 0";
_sumav = (int) (0);
 //BA.debugLineNum = 185;BA.debugLine="Return Mapa";
if (true) return _mapa;
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return null;
}
public static String  _edittextbusca_textchanged(String _old,String _new) throws Exception{
int _i = 0;
 //BA.debugLineNum = 250;BA.debugLine="Sub EditTextBusca_TextChanged (Old As String, New As String)";
 //BA.debugLineNum = 252;BA.debugLine="Mi_CursorBusca=DBApp.Mi_BD.ExecQuery(\"SELECT id_review, titulo, nota, tipo FROM REVIEW WHERE titulo LIKE '\"& New &\"%' \")";
mostCurrent._mi_cursorbusca.setObject((android.database.Cursor)(mostCurrent._dbapp._mi_bd.ExecQuery("SELECT id_review, titulo, nota, tipo FROM REVIEW WHERE titulo LIKE '"+_new+"%' ")));
 //BA.debugLineNum = 254;BA.debugLine="ListViewResultados.Clear";
mostCurrent._listviewresultados.Clear();
 //BA.debugLineNum = 256;BA.debugLine="If Mi_CursorBusca.RowCount>0 Then";
if (mostCurrent._mi_cursorbusca.getRowCount()>0) { 
 //BA.debugLineNum = 258;BA.debugLine="For i=0 To Mi_CursorBusca.RowCount-1";
{
final int step160 = 1;
final int limit160 = (int) (mostCurrent._mi_cursorbusca.getRowCount()-1);
for (_i = (int) (0); (step160 > 0 && _i <= limit160) || (step160 < 0 && _i >= limit160); _i = ((int)(0 + _i + step160))) {
 //BA.debugLineNum = 260;BA.debugLine="Mi_CursorBusca.Position=i";
mostCurrent._mi_cursorbusca.setPosition(_i);
 //BA.debugLineNum = 261;BA.debugLine="ListViewResultados.AddTwoLines(Mi_CursorBusca.GetString(\"id_review\"),Mi_CursorBusca.GetString(\"titulo\") & \" (\"&Mi_CursorBusca.GetString(\"nota\")&\", \"& Mi_CursorBusca.GetString(\"tipo\") &\")\")";
mostCurrent._listviewresultados.AddTwoLines(mostCurrent._mi_cursorbusca.GetString("id_review"),mostCurrent._mi_cursorbusca.GetString("titulo")+" ("+mostCurrent._mi_cursorbusca.GetString("nota")+", "+mostCurrent._mi_cursorbusca.GetString("tipo")+")");
 }
};
 };
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _gesture_onfling(float _velocityx,float _velocityy,Object _motionevent1,Object _motionevent2) throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Sub Gesture_onFling(velocityX As Float, velocityY As Float, MotionEvent1 As Object, MotionEvent2 As Object)";
 //BA.debugLineNum = 236;BA.debugLine="Log(\"   onFling velocityX = \" & velocityX & \", velocityY = \" & velocityY & \", ev1 = \" & MotionEvent1 & \", ev2 = \" & MotionEvent2)";
anywheresoftware.b4a.keywords.Common.Log("   onFling velocityX = "+BA.NumberToString(_velocityx)+", velocityY = "+BA.NumberToString(_velocityy)+", ev1 = "+BA.ObjectToString(_motionevent1)+", ev2 = "+BA.ObjectToString(_motionevent2));
 //BA.debugLineNum = 237;BA.debugLine="If velocityX<0 Then";
if (_velocityx<0) { 
 //BA.debugLineNum = 238;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod TabHost1.TabCount";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 }else {
 //BA.debugLineNum = 240;BA.debugLine="If TabHost1.CurrentTab=0 Then";
if (mostCurrent._tabhost1.getCurrentTab()==0) { 
 //BA.debugLineNum = 241;BA.debugLine="TabHost1.CurrentTab=TabHost1.TabCount-1";
mostCurrent._tabhost1.setCurrentTab((int) (mostCurrent._tabhost1.getTabCount()-1));
 }else {
 //BA.debugLineNum = 243;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab-1) Mod TabHost1.TabCount";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()-1)%mostCurrent._tabhost1.getTabCount()));
 };
 };
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
charts._process_globals();
dbapp._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 33;BA.debugLine="Dim GD As GestureDetector";
mostCurrent._gd = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 34;BA.debugLine="Dim pnlPie As Panel";
mostCurrent._pnlpie = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim TabHost1 As TabHost";
mostCurrent._tabhost1 = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim legend As Bitmap";
mostCurrent._legend = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private SpinnerNota As Spinner";
mostCurrent._spinnernota = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private ListViewNotas As ListView";
mostCurrent._listviewnotas = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private EditTextTitulo As EditText";
mostCurrent._edittexttitulo = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private SpinnerTipo As Spinner";
mostCurrent._spinnertipo = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim Mi_Cursor As Cursor' cursor para almacenar y recorrer una respuesta de un SELECT";
mostCurrent._mi_cursor = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim Mi_CursorBusca As Cursor";
mostCurrent._mi_cursorbusca = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private EditTextBusca As EditText";
mostCurrent._edittextbusca = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private ListViewResultados As ListView";
mostCurrent._listviewresultados = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim PAUSA As Boolean";
_pausa = false;
 //BA.debugLineNum = 53;BA.debugLine="Dim comentario_texto As String";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim nombre_voz As String";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 55;BA.debugLine="Private ButtonStop As Button";
mostCurrent._buttonstop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim IdTexto As InputDialog";
mostCurrent._idtexto = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _iniciargrabacion() throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Sub IniciarGrabacion";
 //BA.debugLineNum = 407;BA.debugLine="AR.AudioSource = AR.AS_MIC";
_ar.setAudioSource(_ar.AS_MIC);
 //BA.debugLineNum = 408;BA.debugLine="AR.OutputFormat = AR.OF_THREE_GPP";
_ar.setOutputFormat(_ar.OF_THREE_GPP);
 //BA.debugLineNum = 409;BA.debugLine="AR.AudioEncoder = AR.AE_AMR_NB";
_ar.setAudioEncoder(_ar.AE_AMR_NB);
 //BA.debugLineNum = 410;BA.debugLine="AR.setOutputFile(File.DirDefaultExternal,nombre_voz)";
_ar.setOutputFile(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),mostCurrent._nombre_voz);
 //BA.debugLineNum = 411;BA.debugLine="AR.prepare()";
_ar.prepare();
 //BA.debugLineNum = 413;BA.debugLine="ToastMessageShow(\"Grabando... Pulse el boton rojo para DETENER\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Grabando... Pulse el boton rojo para DETENER",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 414;BA.debugLine="AR.Start";
_ar.start();
 //BA.debugLineNum = 415;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogtexto() throws Exception{
int _ret = 0;
 //BA.debugLineNum = 308;BA.debugLine="Sub InputDialogTexto";
 //BA.debugLineNum = 310;BA.debugLine="IdTexto.Hint = \"Escribe Aquí\"";
mostCurrent._idtexto.setHint("Escribe Aquí");
 //BA.debugLineNum = 311;BA.debugLine="IdTexto.HintColor = Colors.ARGB(196, 255, 140, 0)";
mostCurrent._idtexto.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 313;BA.debugLine="Dim ret As Int = IdTexto.Show(\"Escribe un Comentario o ingrésalo mediante Reconocimiento de Voz\", \"Comentario\", \"Aceptar\", \"Cancelar\",\"Rec. de Voz\", Null)";
_ret = mostCurrent._idtexto.Show("Escribe un Comentario o ingrésalo mediante Reconocimiento de Voz","Comentario","Aceptar","Cancelar","Rec. de Voz",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 315;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 316;BA.debugLine="comentario_texto=IdTexto.Input";
mostCurrent._comentario_texto = mostCurrent._idtexto.getInput();
 //BA.debugLineNum = 317;BA.debugLine="nombre_voz=\"\"";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 318;BA.debugLine="IdTexto.Input=\"\"";
mostCurrent._idtexto.setInput("");
 };
 //BA.debugLineNum = 321;BA.debugLine="If ret =-2 Then";
if (_ret==-2) { 
 //BA.debugLineNum = 322;BA.debugLine="VR.Listen";
_vr.Listen(processBA);
 };
 //BA.debugLineNum = 326;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 //BA.debugLineNum = 327;BA.debugLine="comentario_texto=\"\"";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 328;BA.debugLine="SpinnerTipo.SelectedIndex=0";
mostCurrent._spinnertipo.setSelectedIndex((int) (0));
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogtitulo(int _posicion) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 291;BA.debugLine="Sub InputDialogTitulo(posicion As Int)";
 //BA.debugLineNum = 292;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 293;BA.debugLine="Id.Hint = \"Escribe Aquí\"";
_id.setHint("Escribe Aquí");
 //BA.debugLineNum = 294;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 296;BA.debugLine="Dim ret As Int = Id.Show(\"Escribe el Título Nuevo\", \"Edita Título\", \"Cambiar\", \"Cancelar\",\"\", Null)";
_ret = _id.Show("Escribe el Título Nuevo","Edita Título","Cambiar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 298;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 299;BA.debugLine="DBApp.EditaReview(Id.Input,posicion)";
mostCurrent._dbapp._editareview(mostCurrent.activityBA,_id.getInput(),BA.NumberToString(_posicion));
 };
 //BA.debugLineNum = 303;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 };
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogvoz() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 342;BA.debugLine="Sub InputDialogVoz";
 //BA.debugLineNum = 343;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 344;BA.debugLine="Id.Hint = \"Escribe Aquí\"";
_id.setHint("Escribe Aquí");
 //BA.debugLineNum = 345;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 347;BA.debugLine="Dim ret As Int = Id.Show(\"Ingresa un nombre y presiona Aceptar para comenzar a grabar\", \"Audio Voz\", \"Aceptar\", \"Cancelar\",\"\", Null)";
_ret = _id.Show("Ingresa un nombre y presiona Aceptar para comenzar a grabar","Audio Voz","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 349;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 350;BA.debugLine="If Id.Input=\"\" Then";
if ((_id.getInput()).equals("")) { 
 //BA.debugLineNum = 351;BA.debugLine="InputDialogVoz";
_inputdialogvoz();
 }else {
 //BA.debugLineNum = 353;BA.debugLine="nombre_voz=Id.Input&\".wav\"";
mostCurrent._nombre_voz = _id.getInput()+".wav";
 //BA.debugLineNum = 354;BA.debugLine="comentario_texto=\"\"";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 355;BA.debugLine="IniciarGrabacion";
_iniciargrabacion();
 //BA.debugLineNum = 356;BA.debugLine="ButtonStop.Enabled=True";
mostCurrent._buttonstop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 360;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 //BA.debugLineNum = 361;BA.debugLine="nombre_voz=\"\"";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 362;BA.debugLine="SpinnerTipo.SelectedIndex=0";
mostCurrent._spinnertipo.setSelectedIndex((int) (0));
 };
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return "";
}
public static String  _intervalo_tick() throws Exception{
 //BA.debugLineNum = 389;BA.debugLine="Sub Intervalo_Tick";
 //BA.debugLineNum = 390;BA.debugLine="PAUSA=True";
_pausa = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 391;BA.debugLine="RelojAcelerometro.Interval=100";
_relojacelerometro.setInterval((long) (100));
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return "";
}
public static String  _listviewnotas_itemlongclick(int _position,Object _value) throws Exception{
String _tiporeseña = "";
String _comentario = "";
String _nombrearchivo = "";
 //BA.debugLineNum = 423;BA.debugLine="Sub ListViewNotas_ItemLongClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 424;BA.debugLine="Dim TipoReseña As String";
_tiporeseña = "";
 //BA.debugLineNum = 425;BA.debugLine="TipoReseña=DBApp.Mi_BD.ExecQuerySingleResult(\"SELECT tipo FROM REVIEW WHERE id_review='\"&ListViewNotas.GetItem(Position)&\"'\")";
_tiporeseña = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT tipo FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 427;BA.debugLine="If TipoReseña= \"Texto\" Then";
if ((_tiporeseña).equals("Texto")) { 
 //BA.debugLineNum = 429;BA.debugLine="Dim Comentario As String";
_comentario = "";
 //BA.debugLineNum = 430;BA.debugLine="Comentario=DBApp.Mi_BD.ExecQuerySingleResult(\"SELECT comentario_texto FROM REVIEW WHERE id_review='\"&ListViewNotas.GetItem(Position)&\"'\")";
_comentario = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT comentario_texto FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 431;BA.debugLine="TTS1.Speak(Comentario, True)";
_tts1.Speak(_comentario,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 432;BA.debugLine="Msgbox2(Comentario,\"Comentario de la Reseña\",\"Regresar\",\"\",\"\",Null)";
anywheresoftware.b4a.keywords.Common.Msgbox2(_comentario,"Comentario de la Reseña","Regresar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 436;BA.debugLine="If TipoReseña= \"Voz\" Then";
if ((_tiporeseña).equals("Voz")) { 
 //BA.debugLineNum = 438;BA.debugLine="If MP.IsPlaying Then";
if (_mp.IsPlaying()) { 
 //BA.debugLineNum = 439;BA.debugLine="MP.stop";
_mp.Stop();
 //BA.debugLineNum = 440;BA.debugLine="ToastMessageShow(\"Audio de voz detenido.\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Audio de voz detenido.",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 442;BA.debugLine="Dim nombreArchivo As String";
_nombrearchivo = "";
 //BA.debugLineNum = 443;BA.debugLine="nombreArchivo= DBApp.Mi_BD.ExecQuerySingleResult(\"SELECT nombre_voz FROM REVIEW WHERE id_review='\"&ListViewNotas.GetItem(Position)&\"'\")";
_nombrearchivo = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT nombre_voz FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 444;BA.debugLine="MP.Load(File.DirDefaultExternal,nombreArchivo)";
_mp.Load(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),_nombrearchivo);
 //BA.debugLineNum = 445;BA.debugLine="MP.play";
_mp.Play();
 //BA.debugLineNum = 446;BA.debugLine="ToastMessageShow(\"Reproduciendo comentario de Voz...\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Reproduciendo comentario de Voz...",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 449;BA.debugLine="End Sub";
return "";
}
public static String  _listviewresultados_itemlongclick(int _position,Object _value) throws Exception{
int _mensaje = 0;
 //BA.debugLineNum = 268;BA.debugLine="Sub ListViewResultados_ItemLongClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 270;BA.debugLine="Dim mensaje As Int";
_mensaje = 0;
 //BA.debugLineNum = 271;BA.debugLine="mensaje = Msgbox2(\"Opciones\",\"\",\"Editar\",\"Cancelar\",\"Eliminar\",Null)";
_mensaje = anywheresoftware.b4a.keywords.Common.Msgbox2("Opciones","","Editar","Cancelar","Eliminar",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="Select mensaje";
switch (BA.switchObjectToInt(_mensaje,anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL)) {
case 0:
 //BA.debugLineNum = 274;BA.debugLine="Log(\"Se pulsó Editar\")";
anywheresoftware.b4a.keywords.Common.Log("Se pulsó Editar");
 //BA.debugLineNum = 276;BA.debugLine="InputDialogTitulo(ListViewResultados.GetItem(Position))";
_inputdialogtitulo((int)(BA.ObjectToNumber(mostCurrent._listviewresultados.GetItem(_position))));
 //BA.debugLineNum = 277;BA.debugLine="LLenaListview";
_llenalistview();
 break;
case 1:
 //BA.debugLineNum = 279;BA.debugLine="DBApp.EliminaReview(ListViewResultados.GetItem(Position))";
mostCurrent._dbapp._eliminareview(mostCurrent.activityBA,BA.ObjectToString(mostCurrent._listviewresultados.GetItem(_position)));
 //BA.debugLineNum = 280;BA.debugLine="ListViewResultados.Clear";
mostCurrent._listviewresultados.Clear();
 //BA.debugLineNum = 281;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 282;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 break;
case 2:
 //BA.debugLineNum = 284;BA.debugLine="Log(\"Se pulsó Cancelar\")";
anywheresoftware.b4a.keywords.Common.Log("Se pulsó Cancelar");
 break;
}
;
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _llenalistview() throws Exception{
int _i = 0;
 //BA.debugLineNum = 196;BA.debugLine="Sub LLenaListview";
 //BA.debugLineNum = 198;BA.debugLine="Mi_Cursor=DBApp.Mi_BD.ExecQuery(\"SELECT id_review, titulo, nota, tipo FROM REVIEW\")";
mostCurrent._mi_cursor.setObject((android.database.Cursor)(mostCurrent._dbapp._mi_bd.ExecQuery("SELECT id_review, titulo, nota, tipo FROM REVIEW")));
 //BA.debugLineNum = 200;BA.debugLine="ListViewNotas.Clear";
mostCurrent._listviewnotas.Clear();
 //BA.debugLineNum = 201;BA.debugLine="EditTextTitulo.Text=\"\"";
mostCurrent._edittexttitulo.setText((Object)(""));
 //BA.debugLineNum = 203;BA.debugLine="If Mi_Cursor.RowCount>0 Then";
if (mostCurrent._mi_cursor.getRowCount()>0) { 
 //BA.debugLineNum = 205;BA.debugLine="For i=0 To Mi_Cursor.RowCount-1";
{
final int step128 = 1;
final int limit128 = (int) (mostCurrent._mi_cursor.getRowCount()-1);
for (_i = (int) (0); (step128 > 0 && _i <= limit128) || (step128 < 0 && _i >= limit128); _i = ((int)(0 + _i + step128))) {
 //BA.debugLineNum = 207;BA.debugLine="Mi_Cursor.Position=i";
mostCurrent._mi_cursor.setPosition(_i);
 //BA.debugLineNum = 208;BA.debugLine="ListViewNotas.AddTwoLines(Mi_Cursor.GetString(\"id_review\"),Mi_Cursor.GetString(\"titulo\") & \" (\"&Mi_Cursor.GetString(\"nota\")&\", \"& Mi_Cursor.GetString(\"tipo\") &\")\")";
mostCurrent._listviewnotas.AddTwoLines(mostCurrent._mi_cursor.GetString("id_review"),mostCurrent._mi_cursor.GetString("titulo")+" ("+mostCurrent._mi_cursor.GetString("nota")+", "+mostCurrent._mi_cursor.GetString("tipo")+")");
 }
};
 };
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _llenaspinnernota() throws Exception{
double _i = 0;
 //BA.debugLineNum = 188;BA.debugLine="Sub LLenaSpinnerNota";
 //BA.debugLineNum = 189;BA.debugLine="Dim I As Double=7.0";
_i = 7.0;
 //BA.debugLineNum = 190;BA.debugLine="Do While I>=1.0";
while (_i>=1.0) {
 //BA.debugLineNum = 191;BA.debugLine="SpinnerNota.add(Round2(I,1))";
mostCurrent._spinnernota.Add(BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2(_i,(int) (1))));
 //BA.debugLineNum = 192;BA.debugLine="I=I-0.1";
_i = _i-0.1;
 }
;
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim FontSize = 12 As Float";
_fontsize = (float) (12);
 //BA.debugLineNum = 19;BA.debugLine="Dim MaxVal, MinVal, ScaleMin, ScaleMax, ScaleInterval As Double";
_maxval = 0;
_minval = 0;
_scalemin = 0;
_scalemax = 0;
_scaleinterval = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim Mi_acelerometro As PhoneAccelerometer";
_mi_acelerometro = new anywheresoftware.b4a.phone.Phone.PhoneAccelerometer();
 //BA.debugLineNum = 21;BA.debugLine="Dim RelojAcelerometro As Timer";
_relojacelerometro = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 22;BA.debugLine="Dim AR As AudioRecorder";
_ar = new com.rootsoft.audiorecorder.AudioRecorder();
 //BA.debugLineNum = 23;BA.debugLine="Dim MP As MediaPlayer";
_mp = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim VR As VoiceRecognition";
_vr = new anywheresoftware.b4a.phone.Phone.VoiceRecognition();
 //BA.debugLineNum = 25;BA.debugLine="Dim TTS1 As TTS";
_tts1 = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _sensor_accelerometerchanged(float _x,float _y,float _z) throws Exception{
 //BA.debugLineNum = 367;BA.debugLine="Sub Sensor_AccelerometerChanged (X As Float, Y As Float, Z As Float)";
 //BA.debugLineNum = 369;BA.debugLine="If PAUSA= True Then";
if (_pausa==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 370;BA.debugLine="If X>10.8 Then";
if (_x>10.8) { 
 //BA.debugLineNum = 371;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod TabHost1.TabCount";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 372;BA.debugLine="RelojAcelerometro.Interval=1000";
_relojacelerometro.setInterval((long) (1000));
 }else {
 //BA.debugLineNum = 374;BA.debugLine="If Y>10.8 Then";
if (_y>10.8) { 
 //BA.debugLineNum = 375;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod TabHost1.TabCount";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 376;BA.debugLine="RelojAcelerometro.Interval=1000";
_relojacelerometro.setInterval((long) (1000));
 }else {
 //BA.debugLineNum = 378;BA.debugLine="If Z>10.8 Then";
if (_z>10.8) { 
 //BA.debugLineNum = 379;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod TabHost1.TabCount";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 380;BA.debugLine="RelojAcelerometro.Interval=1000";
_relojacelerometro.setInterval((long) (1000));
 };
 };
 };
 };
 //BA.debugLineNum = 386;BA.debugLine="PAUSA=False";
_pausa = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _spinnertipo_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 394;BA.debugLine="Sub SpinnerTipo_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 396;BA.debugLine="Select SpinnerTipo.GetItem(Position)";
switch (BA.switchObjectToInt(mostCurrent._spinnertipo.GetItem(_position),"Texto","Voz")) {
case 0:
 //BA.debugLineNum = 399;BA.debugLine="InputDialogTexto";
_inputdialogtexto();
 break;
case 1:
 //BA.debugLineNum = 401;BA.debugLine="InputDialogVoz";
_inputdialogvoz();
 break;
}
;
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _tabbuscar() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub TabBuscar";
 //BA.debugLineNum = 117;BA.debugLine="TabHost1.AddTab(\"Buscador\",\"layoutBusca\")";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Buscador","layoutBusca");
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _tabgraficopie() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 107;BA.debugLine="Sub TabGraficoPie";
 //BA.debugLineNum = 108;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 109;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 110;BA.debugLine="pnlPie.Initialize(\"pnlPie\")";
mostCurrent._pnlpie.Initialize(mostCurrent.activityBA,"pnlPie");
 //BA.debugLineNum = 111;BA.debugLine="p.AddView(pnlPie, 0, 0, 100%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlpie.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 112;BA.debugLine="TabHost1.AddTab2(\"Resumen\", p)";
mostCurrent._tabhost1.AddTab2("Resumen",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _tabhost1_tabchanged() throws Exception{
 //BA.debugLineNum = 452;BA.debugLine="Sub TabHost1_TabChanged";
 //BA.debugLineNum = 453;BA.debugLine="If TabHost1.CurrentTab=0 Then";
if (mostCurrent._tabhost1.getCurrentTab()==0) { 
 //BA.debugLineNum = 454;BA.debugLine="LLenaListview";
_llenalistview();
 };
 //BA.debugLineNum = 457;BA.debugLine="If TabHost1.CurrentTab=2 Then";
if (mostCurrent._tabhost1.getCurrentTab()==2) { 
 //BA.debugLineNum = 458;BA.debugLine="EditTextBusca.Text=\"\"";
mostCurrent._edittextbusca.setText((Object)(""));
 };
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return "";
}
public static String  _vr_result(boolean _success,anywheresoftware.b4a.objects.collections.List _texts) throws Exception{
 //BA.debugLineNum = 333;BA.debugLine="Sub VR_Result (Success As Boolean, Texts As List)";
 //BA.debugLineNum = 334;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 336;BA.debugLine="IdTexto.Input=Texts.Get(0)";
mostCurrent._idtexto.setInput(BA.ObjectToString(_texts.Get((int) (0))));
 //BA.debugLineNum = 337;BA.debugLine="InputDialogTexto";
_inputdialogtexto();
 };
 //BA.debugLineNum = 340;BA.debugLine="End Sub";
return "";
}
}
