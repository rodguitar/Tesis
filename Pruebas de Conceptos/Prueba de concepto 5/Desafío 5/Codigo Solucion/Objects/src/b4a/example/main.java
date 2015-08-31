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
        processBA.runHook("oncreate", this, null);
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
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
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
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
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
        processBA.runHook("onnewintent", this, new Object[] {intent});
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
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
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
        processBA.runHook("onresume", this, null);
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
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
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
public static anywheresoftware.b4a.gps.GPS _gps1 = null;
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
public anywheresoftware.b4a.sql.SQL.CursorWrapper _mi_cursor_google = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _mi_cursorbusca = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittextbusca = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listviewresultados = null;
public static boolean _pausa = false;
public static String _comentario_texto = "";
public static String _nombre_voz = "";
public anywheresoftware.b4a.objects.ButtonWrapper _buttonstop = null;
public anywheresoftware.b4a.agraham.dialogs.InputDialog _idtexto = null;
public static double _latitud_actual = 0;
public static double _longitud_actual = 0;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinnerreviewg = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webviewgoogle = null;
public b4a.example.charts _charts = null;
public b4a.example.dbapp _dbapp = null;
public b4a.example.googlemaps _googlemaps = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="If FirstTime=True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 71;BA.debugLine="DBApp.CreaBD";
mostCurrent._dbapp._creabd(mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="AR.Initialize()";
_ar.Initialize(processBA);
 //BA.debugLineNum = 74;BA.debugLine="VR.Initialize(\"VR\")";
_vr.Initialize("VR");
 //BA.debugLineNum = 75;BA.debugLine="TTS1.Initialize(\"TTS1\")";
_tts1.Initialize(processBA,"TTS1");
 //BA.debugLineNum = 76;BA.debugLine="GPS1.Initialize(\"GPS\")";
_gps1.Initialize("GPS");
 };
 //BA.debugLineNum = 78;BA.debugLine="VR.Prompt = \"Habla ahora!\"";
_vr.setPrompt("Habla ahora!");
 //BA.debugLineNum = 80;BA.debugLine="GD.SetOnGestureListener(Activity, \"Gesture\")";
mostCurrent._gd.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._activity.getObject()),"Gesture");
 //BA.debugLineNum = 82;BA.debugLine="TabHost1.Initialize(\"TabHost1\")";
mostCurrent._tabhost1.Initialize(mostCurrent.activityBA,"TabHost1");
 //BA.debugLineNum = 85;BA.debugLine="Activity.AddView(TabHost1, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._tabhost1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 86;BA.debugLine="TabHost1.AddTab(\"Ingreso de Reseñas\",\"Layout1\")'";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Ingreso de Reseñas","Layout1");
 //BA.debugLineNum = 87;BA.debugLine="Activity.Title=\"Desafío 1\"";
mostCurrent._activity.setTitle((Object)("Desafío 1"));
 //BA.debugLineNum = 89;BA.debugLine="LLenaSpinnerNota";
_llenaspinnernota();
 //BA.debugLineNum = 90;BA.debugLine="SpinnerTipo.Add(\"Solo Título\")";
mostCurrent._spinnertipo.Add("Solo Título");
 //BA.debugLineNum = 91;BA.debugLine="SpinnerTipo.Add(\"Texto\")";
mostCurrent._spinnertipo.Add("Texto");
 //BA.debugLineNum = 92;BA.debugLine="SpinnerTipo.Add(\"Voz\")";
mostCurrent._spinnertipo.Add("Voz");
 //BA.debugLineNum = 94;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 97;BA.debugLine="TabGraficoPie'Segunda Pestaña";
_tabgraficopie();
 //BA.debugLineNum = 98;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 //BA.debugLineNum = 99;BA.debugLine="TabBuscar' Tercera Pestaña";
_tabbuscar();
 //BA.debugLineNum = 100;BA.debugLine="TabGoogleMaps ' Cuarta Pestaña";
_tabgooglemaps();
 //BA.debugLineNum = 101;BA.debugLine="LLenaSpinnerGoogle";
_llenaspinnergoogle();
 //BA.debugLineNum = 102;BA.debugLine="SpinnerReviewG.Prompt=\"Reseñas\"";
mostCurrent._spinnerreviewg.setPrompt("Reseñas");
 //BA.debugLineNum = 104;BA.debugLine="Mi_acelerometro.StartListening(\"Sensor\")";
_mi_acelerometro.StartListening(processBA,"Sensor");
 //BA.debugLineNum = 105;BA.debugLine="RelojAcelerometro.Initialize(\"Intervalo\",100)";
_relojacelerometro.Initialize(processBA,"Intervalo",(long) (100));
 //BA.debugLineNum = 106;BA.debugLine="RelojAcelerometro.Enabled=True";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 107;BA.debugLine="MP.Initialize 'Inicializa el media player";
_mp.Initialize();
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 123;BA.debugLine="Mi_acelerometro.StopListening";
_mi_acelerometro.StopListening(processBA);
 //BA.debugLineNum = 124;BA.debugLine="RelojAcelerometro.Enabled=False";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="GPS1.Stop";
_gps1.Stop();
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 111;BA.debugLine="Mi_acelerometro.StartListening(\"Sensor\")";
_mi_acelerometro.StartListening(processBA,"Sensor");
 //BA.debugLineNum = 112;BA.debugLine="RelojAcelerometro.Enabled=True";
_relojacelerometro.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 114;BA.debugLine="If GPS1.GPSEnabled = False Then";
if (_gps1.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 115;BA.debugLine="ToastMessageShow(\"Por favor habilita el GPS.\", T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Por favor habilita el GPS.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 116;BA.debugLine="StartActivity(GPS1.LocationSettingsIntent) 'Will";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_gps1.getLocationSettingsIntent()));
 }else {
 //BA.debugLineNum = 118;BA.debugLine="GPS1.Start(0, 0) 'Listen to GPS with no filters.";
_gps1.Start(processBA,(long) (0),(float) (0));
 };
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _addvalorgrafico(anywheresoftware.b4a.objects.collections.Map _datos) throws Exception{
b4a.example.charts._piedata _pd = null;
String _k = "";
 //BA.debugLineNum = 145;BA.debugLine="Sub addValorGrafico(datos As Map)";
 //BA.debugLineNum = 147;BA.debugLine="Dim PD As PieData";
_pd = new b4a.example.charts._piedata();
 //BA.debugLineNum = 148;BA.debugLine="PD.Initialize";
_pd.Initialize();
 //BA.debugLineNum = 149;BA.debugLine="PD.Target = pnlPie 'Set the target view";
_pd.Target = mostCurrent._pnlpie;
 //BA.debugLineNum = 151;BA.debugLine="For Each k As String In datos.Keys";
final anywheresoftware.b4a.BA.IterableList group98 = _datos.Keys();
final int groupLen98 = group98.getSize();
for (int index98 = 0;index98 < groupLen98 ;index98++){
_k = BA.ObjectToString(group98.Get(index98));
 //BA.debugLineNum = 152;BA.debugLine="Charts.AddPieItem(PD, k, datos.Get(k), 0)";
mostCurrent._charts._addpieitem(mostCurrent.activityBA,_pd,_k,(float)(BA.ObjectToNumber(_datos.Get((Object)(_k)))),(int) (0));
 }
;
 //BA.debugLineNum = 155;BA.debugLine="PD.GapDegrees = 0 'Total size of gaps between sli";
_pd.GapDegrees = (int) (0);
 //BA.debugLineNum = 156;BA.debugLine="PD.LegendBackColor = Colors.ARGB(150, 100, 100, 1";
_pd.LegendBackColor = anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (100),(int) (100),(int) (100));
 //BA.debugLineNum = 162;BA.debugLine="legend = Charts.DrawPie(PD, Colors.Gray, True)";
mostCurrent._legend = mostCurrent._charts._drawpie(mostCurrent.activityBA,_pd,anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="If ImageView1.IsInitialized=False Then ImageView1";
if (mostCurrent._imageview1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._imageview1.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 165;BA.debugLine="ImageView1.RemoveView";
mostCurrent._imageview1.RemoveView();
 //BA.debugLineNum = 166;BA.debugLine="ImageView1.SetBackgroundImage(legend)";
mostCurrent._imageview1.SetBackgroundImage((android.graphics.Bitmap)(mostCurrent._legend.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="pnlPie.AddView(ImageView1, 10dip, 10dip, legend.W";
mostCurrent._pnlpie.AddView((android.view.View)(mostCurrent._imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),mostCurrent._legend.getWidth(),mostCurrent._legend.getHeight());
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _buttoningresar_click() throws Exception{
 //BA.debugLineNum = 261;BA.debugLine="Sub ButtonIngresar_Click";
 //BA.debugLineNum = 264;BA.debugLine="If EditTextTitulo.Text.Trim=\"\" Then";
if ((mostCurrent._edittexttitulo.getText().trim()).equals("")) { 
 //BA.debugLineNum = 265;BA.debugLine="Msgbox(\"Falta ingresar el Título\",\"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Falta ingresar el Título","Error",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 269;BA.debugLine="DBApp.InsertaReview(EditTextTitulo.Text,SpinnerN";
mostCurrent._dbapp._insertareview(mostCurrent.activityBA,mostCurrent._edittexttitulo.getText(),mostCurrent._spinnernota.getSelectedItem(),mostCurrent._spinnertipo.getSelectedItem(),mostCurrent._comentario_texto,mostCurrent._nombre_voz,_latitud_actual,_longitud_actual);
 //BA.debugLineNum = 271;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 273;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 //BA.debugLineNum = 274;BA.debugLine="LLenaSpinnerGoogle";
_llenaspinnergoogle();
 //BA.debugLineNum = 275;BA.debugLine="Msgbox(\"Has ingresado tu Reseña correctamente\",\"";
anywheresoftware.b4a.keywords.Common.Msgbox("Has ingresado tu Reseña correctamente","Exito",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static String  _buttonstop_click() throws Exception{
 //BA.debugLineNum = 466;BA.debugLine="Sub ButtonStop_Click";
 //BA.debugLineNum = 467;BA.debugLine="ToastMessageShow(\"Grabación detenida.\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Grabación detenida.",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 468;BA.debugLine="AR.Stop";
_ar.stop();
 //BA.debugLineNum = 469;BA.debugLine="ButtonStop.Enabled=False";
mostCurrent._buttonstop.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 470;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _calculaestadistica() throws Exception{
anywheresoftware.b4a.objects.collections.Map _mapa = null;
int _sumast = 0;
int _sumat = 0;
int _sumav = 0;
int _i = 0;
 //BA.debugLineNum = 171;BA.debugLine="Sub CalculaEstadistica As Map";
 //BA.debugLineNum = 173;BA.debugLine="Dim Mapa As Map";
_mapa = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 174;BA.debugLine="Dim sumaST As Int";
_sumast = 0;
 //BA.debugLineNum = 175;BA.debugLine="Dim sumaT As Int";
_sumat = 0;
 //BA.debugLineNum = 176;BA.debugLine="Dim sumaV As Int";
_sumav = 0;
 //BA.debugLineNum = 178;BA.debugLine="If Mi_Cursor.RowCount>0 Then";
if (mostCurrent._mi_cursor.getRowCount()>0) { 
 //BA.debugLineNum = 180;BA.debugLine="For i=0 To Mi_Cursor.RowCount-1";
{
final int step115 = 1;
final int limit115 = (int) (mostCurrent._mi_cursor.getRowCount()-1);
for (_i = (int) (0); (step115 > 0 && _i <= limit115) || (step115 < 0 && _i >= limit115); _i = ((int)(0 + _i + step115))) {
 //BA.debugLineNum = 182;BA.debugLine="Mi_Cursor.Position=i";
mostCurrent._mi_cursor.setPosition(_i);
 //BA.debugLineNum = 184;BA.debugLine="Select Mi_Cursor.GetString(\"tipo\")";
switch (BA.switchObjectToInt(mostCurrent._mi_cursor.GetString("tipo"),"Solo Título","Texto","Voz")) {
case 0:
 //BA.debugLineNum = 187;BA.debugLine="sumaST=sumaST+1";
_sumast = (int) (_sumast+1);
 break;
case 1:
 //BA.debugLineNum = 190;BA.debugLine="sumaT=sumaT+1";
_sumat = (int) (_sumat+1);
 break;
case 2:
 //BA.debugLineNum = 193;BA.debugLine="sumaV=sumaV+1";
_sumav = (int) (_sumav+1);
 break;
}
;
 }
};
 };
 //BA.debugLineNum = 201;BA.debugLine="If Mapa.IsInitialized=False Then Mapa.Initialize";
if (_mapa.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_mapa.Initialize();};
 //BA.debugLineNum = 202;BA.debugLine="Mapa.Put(\"Solo Título\",sumaST)";
_mapa.Put((Object)("Solo Título"),(Object)(_sumast));
 //BA.debugLineNum = 203;BA.debugLine="Mapa.Put(\"Texto\",sumaT)";
_mapa.Put((Object)("Texto"),(Object)(_sumat));
 //BA.debugLineNum = 204;BA.debugLine="Mapa.Put(\"Voz\",sumaV)";
_mapa.Put((Object)("Voz"),(Object)(_sumav));
 //BA.debugLineNum = 206;BA.debugLine="sumaST = 0";
_sumast = (int) (0);
 //BA.debugLineNum = 207;BA.debugLine="sumaT = 0";
_sumat = (int) (0);
 //BA.debugLineNum = 208;BA.debugLine="sumaV = 0";
_sumav = (int) (0);
 //BA.debugLineNum = 210;BA.debugLine="Return Mapa";
if (true) return _mapa;
 //BA.debugLineNum = 211;BA.debugLine="End Sub";
return null;
}
public static String  _edittextbusca_textchanged(String _old,String _new) throws Exception{
int _i = 0;
 //BA.debugLineNum = 295;BA.debugLine="Sub EditTextBusca_TextChanged (Old As String, New";
 //BA.debugLineNum = 297;BA.debugLine="Mi_CursorBusca=DBApp.Mi_BD.ExecQuery(\"SELECT id_r";
mostCurrent._mi_cursorbusca.setObject((android.database.Cursor)(mostCurrent._dbapp._mi_bd.ExecQuery("SELECT id_review, titulo, nota, tipo FROM REVIEW WHERE titulo LIKE '"+_new+"%' ")));
 //BA.debugLineNum = 299;BA.debugLine="ListViewResultados.Clear";
mostCurrent._listviewresultados.Clear();
 //BA.debugLineNum = 301;BA.debugLine="If Mi_CursorBusca.RowCount>0 Then";
if (mostCurrent._mi_cursorbusca.getRowCount()>0) { 
 //BA.debugLineNum = 303;BA.debugLine="For i=0 To Mi_CursorBusca.RowCount-1";
{
final int step191 = 1;
final int limit191 = (int) (mostCurrent._mi_cursorbusca.getRowCount()-1);
for (_i = (int) (0); (step191 > 0 && _i <= limit191) || (step191 < 0 && _i >= limit191); _i = ((int)(0 + _i + step191))) {
 //BA.debugLineNum = 305;BA.debugLine="Mi_CursorBusca.Position=i";
mostCurrent._mi_cursorbusca.setPosition(_i);
 //BA.debugLineNum = 306;BA.debugLine="ListViewResultados.AddTwoLines(Mi_CursorBusca.G";
mostCurrent._listviewresultados.AddTwoLines(mostCurrent._mi_cursorbusca.GetString("id_review"),mostCurrent._mi_cursorbusca.GetString("titulo")+" ("+mostCurrent._mi_cursorbusca.GetString("nota")+", "+mostCurrent._mi_cursorbusca.GetString("tipo")+")");
 }
};
 };
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _gesture_onfling(float _velocityx,float _velocityy,Object _motionevent1,Object _motionevent2) throws Exception{
 //BA.debugLineNum = 280;BA.debugLine="Sub Gesture_onFling(velocityX As Float, velocityY";
 //BA.debugLineNum = 281;BA.debugLine="Log(\"   onFling velocityX = \" & velocityX & \", ve";
anywheresoftware.b4a.keywords.Common.Log("   onFling velocityX = "+BA.NumberToString(_velocityx)+", velocityY = "+BA.NumberToString(_velocityy)+", ev1 = "+BA.ObjectToString(_motionevent1)+", ev2 = "+BA.ObjectToString(_motionevent2));
 //BA.debugLineNum = 282;BA.debugLine="If velocityX<0 Then";
if (_velocityx<0) { 
 //BA.debugLineNum = 283;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 }else {
 //BA.debugLineNum = 285;BA.debugLine="If TabHost1.CurrentTab=0 Then";
if (mostCurrent._tabhost1.getCurrentTab()==0) { 
 //BA.debugLineNum = 286;BA.debugLine="TabHost1.CurrentTab=TabHost1.TabCount-1";
mostCurrent._tabhost1.setCurrentTab((int) (mostCurrent._tabhost1.getTabCount()-1));
 }else {
 //BA.debugLineNum = 288;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab-1) Mod";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()-1)%mostCurrent._tabhost1.getTabCount()));
 };
 };
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
charts._process_globals();
dbapp._process_globals();
googlemaps._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _globals() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 35;BA.debugLine="Dim GD As GestureDetector";
mostCurrent._gd = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 36;BA.debugLine="Dim pnlPie As Panel";
mostCurrent._pnlpie = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim TabHost1 As TabHost";
mostCurrent._tabhost1 = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim legend As Bitmap";
mostCurrent._legend = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private SpinnerNota As Spinner";
mostCurrent._spinnernota = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private ListViewNotas As ListView";
mostCurrent._listviewnotas = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private EditTextTitulo As EditText";
mostCurrent._edittexttitulo = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private SpinnerTipo As Spinner";
mostCurrent._spinnertipo = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim Mi_Cursor As Cursor' cursor para almacenar y";
mostCurrent._mi_cursor = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim Mi_Cursor_Google As Cursor";
mostCurrent._mi_cursor_google = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim Mi_CursorBusca As Cursor";
mostCurrent._mi_cursorbusca = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private EditTextBusca As EditText";
mostCurrent._edittextbusca = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private ListViewResultados As ListView";
mostCurrent._listviewresultados = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim PAUSA As Boolean";
_pausa = false;
 //BA.debugLineNum = 56;BA.debugLine="Dim comentario_texto As String";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 57;BA.debugLine="Dim nombre_voz As String";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 58;BA.debugLine="Private ButtonStop As Button";
mostCurrent._buttonstop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim IdTexto As InputDialog";
mostCurrent._idtexto = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 61;BA.debugLine="Dim Latitud_Actual As Double";
_latitud_actual = 0;
 //BA.debugLineNum = 62;BA.debugLine="Dim Longitud_Actual As Double";
_longitud_actual = 0;
 //BA.debugLineNum = 63;BA.debugLine="Private SpinnerReviewG As Spinner";
mostCurrent._spinnerreviewg = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private WebViewGoogle As WebView";
mostCurrent._webviewgoogle = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _gps_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 513;BA.debugLine="Sub GPS_LocationChanged (Location1 As Location)";
 //BA.debugLineNum = 514;BA.debugLine="Latitud_Actual = Location1.Latitude";
_latitud_actual = _location1.getLatitude();
 //BA.debugLineNum = 515;BA.debugLine="Longitud_Actual = Location1.Longitude";
_longitud_actual = _location1.getLongitude();
 //BA.debugLineNum = 517;BA.debugLine="End Sub";
return "";
}
public static String  _iniciargrabacion() throws Exception{
 //BA.debugLineNum = 455;BA.debugLine="Sub IniciarGrabacion";
 //BA.debugLineNum = 456;BA.debugLine="AR.AudioSource = AR.AS_MIC";
_ar.setAudioSource(_ar.AS_MIC);
 //BA.debugLineNum = 457;BA.debugLine="AR.OutputFormat = AR.OF_THREE_GPP";
_ar.setOutputFormat(_ar.OF_THREE_GPP);
 //BA.debugLineNum = 458;BA.debugLine="AR.AudioEncoder = AR.AE_AMR_NB";
_ar.setAudioEncoder(_ar.AE_AMR_NB);
 //BA.debugLineNum = 459;BA.debugLine="AR.setOutputFile(File.DirDefaultExternal,nombre_v";
_ar.setOutputFile(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),mostCurrent._nombre_voz);
 //BA.debugLineNum = 460;BA.debugLine="AR.prepare()";
_ar.prepare();
 //BA.debugLineNum = 462;BA.debugLine="ToastMessageShow(\"Grabando... Pulse el boton rojo";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Grabando... Pulse el boton rojo para DETENER",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 463;BA.debugLine="AR.Start";
_ar.start();
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogtexto() throws Exception{
int _ret = 0;
 //BA.debugLineNum = 354;BA.debugLine="Sub InputDialogTexto";
 //BA.debugLineNum = 356;BA.debugLine="IdTexto.Hint = \"Escribe Aquí\"";
mostCurrent._idtexto.setHint("Escribe Aquí");
 //BA.debugLineNum = 357;BA.debugLine="IdTexto.HintColor = Colors.ARGB(196, 255, 140,";
mostCurrent._idtexto.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 359;BA.debugLine="Dim ret As Int = IdTexto.Show(\"Escribe un Come";
_ret = mostCurrent._idtexto.Show("Escribe un Comentario o ingrésalo mediante Reconocimiento de Voz","Comentario","Aceptar","Cancelar","Rec. de Voz",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 361;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 362;BA.debugLine="comentario_texto=IdTexto.Input";
mostCurrent._comentario_texto = mostCurrent._idtexto.getInput();
 //BA.debugLineNum = 363;BA.debugLine="nombre_voz=\"\"";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 364;BA.debugLine="IdTexto.Input=\"\"";
mostCurrent._idtexto.setInput("");
 };
 //BA.debugLineNum = 367;BA.debugLine="If ret =-2 Then";
if (_ret==-2) { 
 //BA.debugLineNum = 368;BA.debugLine="VR.Listen";
_vr.Listen(processBA);
 };
 //BA.debugLineNum = 372;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 //BA.debugLineNum = 373;BA.debugLine="comentario_texto=\"\"";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 374;BA.debugLine="SpinnerTipo.SelectedIndex=0";
mostCurrent._spinnertipo.setSelectedIndex((int) (0));
 };
 //BA.debugLineNum = 377;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogtitulo(int _posicion) throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 337;BA.debugLine="Sub InputDialogTitulo(posicion As Int)";
 //BA.debugLineNum = 338;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 339;BA.debugLine="Id.Hint = \"Escribe Aquí\"";
_id.setHint("Escribe Aquí");
 //BA.debugLineNum = 340;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 342;BA.debugLine="Dim ret As Int = Id.Show(\"Escribe el Título Nu";
_ret = _id.Show("Escribe el Título Nuevo","Edita Título","Cambiar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 344;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 345;BA.debugLine="DBApp.EditaReview(Id.Input,posicion)";
mostCurrent._dbapp._editareview(mostCurrent.activityBA,_id.getInput(),BA.NumberToString(_posicion));
 };
 //BA.debugLineNum = 349;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 };
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
return "";
}
public static String  _inputdialogvoz() throws Exception{
anywheresoftware.b4a.agraham.dialogs.InputDialog _id = null;
int _ret = 0;
 //BA.debugLineNum = 388;BA.debugLine="Sub InputDialogVoz";
 //BA.debugLineNum = 389;BA.debugLine="Dim Id As InputDialog";
_id = new anywheresoftware.b4a.agraham.dialogs.InputDialog();
 //BA.debugLineNum = 390;BA.debugLine="Id.Hint = \"Escribe Aquí\"";
_id.setHint("Escribe Aquí");
 //BA.debugLineNum = 391;BA.debugLine="Id.HintColor = Colors.ARGB(196, 255, 140, 0)";
_id.setHintColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (196),(int) (255),(int) (140),(int) (0)));
 //BA.debugLineNum = 393;BA.debugLine="Dim ret As Int = Id.Show(\"Ingresa un nombre y";
_ret = _id.Show("Ingresa un nombre y presiona Aceptar para comenzar a grabar","Audio Voz","Aceptar","Cancelar","",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 395;BA.debugLine="If ret = -1 Then";
if (_ret==-1) { 
 //BA.debugLineNum = 396;BA.debugLine="If Id.Input=\"\" Then";
if ((_id.getInput()).equals("")) { 
 //BA.debugLineNum = 397;BA.debugLine="InputDialogVoz";
_inputdialogvoz();
 }else {
 //BA.debugLineNum = 399;BA.debugLine="nombre_voz=Id.Input&\".wav\"";
mostCurrent._nombre_voz = _id.getInput()+".wav";
 //BA.debugLineNum = 400;BA.debugLine="comentario_texto=\"\"";
mostCurrent._comentario_texto = "";
 //BA.debugLineNum = 401;BA.debugLine="IniciarGrabacion";
_iniciargrabacion();
 //BA.debugLineNum = 402;BA.debugLine="ButtonStop.Enabled=True";
mostCurrent._buttonstop.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 406;BA.debugLine="If ret = -3 Then";
if (_ret==-3) { 
 //BA.debugLineNum = 407;BA.debugLine="nombre_voz=\"\"";
mostCurrent._nombre_voz = "";
 //BA.debugLineNum = 408;BA.debugLine="SpinnerTipo.SelectedIndex=0";
mostCurrent._spinnertipo.setSelectedIndex((int) (0));
 };
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public static String  _intervalo_tick() throws Exception{
 //BA.debugLineNum = 438;BA.debugLine="Sub Intervalo_Tick";
 //BA.debugLineNum = 439;BA.debugLine="PAUSA=True";
_pausa = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 440;BA.debugLine="RelojAcelerometro.Interval=100";
_relojacelerometro.setInterval((long) (100));
 //BA.debugLineNum = 441;BA.debugLine="End Sub";
return "";
}
public static String  _listviewnotas_itemlongclick(int _position,Object _value) throws Exception{
String _tiporeseña = "";
String _comentario = "";
String _nombrearchivo = "";
 //BA.debugLineNum = 472;BA.debugLine="Sub ListViewNotas_ItemLongClick (Position As Int,";
 //BA.debugLineNum = 473;BA.debugLine="Dim TipoReseña As String";
_tiporeseña = "";
 //BA.debugLineNum = 474;BA.debugLine="TipoReseña=DBApp.Mi_BD.ExecQuerySingleResult(\"SE";
_tiporeseña = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT tipo FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 476;BA.debugLine="If TipoReseña= \"Texto\" Then";
if ((_tiporeseña).equals("Texto")) { 
 //BA.debugLineNum = 478;BA.debugLine="Dim Comentario As String";
_comentario = "";
 //BA.debugLineNum = 479;BA.debugLine="Comentario=DBApp.Mi_BD.ExecQuerySingleResult(\"S";
_comentario = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT comentario_texto FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 480;BA.debugLine="TTS1.Speak(Comentario, True)";
_tts1.Speak(_comentario,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 481;BA.debugLine="Msgbox2(Comentario,\"Comentario de la Reseña\",\"R";
anywheresoftware.b4a.keywords.Common.Msgbox2(_comentario,"Comentario de la Reseña","Regresar","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 485;BA.debugLine="If TipoReseña= \"Voz\" Then";
if ((_tiporeseña).equals("Voz")) { 
 //BA.debugLineNum = 487;BA.debugLine="If MP.IsPlaying Then";
if (_mp.IsPlaying()) { 
 //BA.debugLineNum = 488;BA.debugLine="MP.stop";
_mp.Stop();
 //BA.debugLineNum = 489;BA.debugLine="ToastMessageShow(\"Audio de voz detenido.\",True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Audio de voz detenido.",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 491;BA.debugLine="Dim nombreArchivo As String";
_nombrearchivo = "";
 //BA.debugLineNum = 492;BA.debugLine="nombreArchivo= DBApp.Mi_BD.ExecQuerySingleResu";
_nombrearchivo = mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT nombre_voz FROM REVIEW WHERE id_review='"+BA.ObjectToString(mostCurrent._listviewnotas.GetItem(_position))+"'");
 //BA.debugLineNum = 493;BA.debugLine="MP.Load(File.DirDefaultExternal,nombreArchivo)";
_mp.Load(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),_nombrearchivo);
 //BA.debugLineNum = 494;BA.debugLine="MP.play";
_mp.Play();
 //BA.debugLineNum = 495;BA.debugLine="ToastMessageShow(\"Reproduciendo comentario de";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Reproduciendo comentario de Voz...",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return "";
}
public static String  _listviewresultados_itemlongclick(int _position,Object _value) throws Exception{
int _mensaje = 0;
 //BA.debugLineNum = 313;BA.debugLine="Sub ListViewResultados_ItemLongClick (Position As";
 //BA.debugLineNum = 315;BA.debugLine="Dim mensaje As Int";
_mensaje = 0;
 //BA.debugLineNum = 316;BA.debugLine="mensaje = Msgbox2(\"Opciones\",\"\",\"Editar\",\"Canc";
_mensaje = anywheresoftware.b4a.keywords.Common.Msgbox2("Opciones","","Editar","Cancelar","Eliminar",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 317;BA.debugLine="Select mensaje";
switch (BA.switchObjectToInt(_mensaje,anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE,anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL)) {
case 0:
 //BA.debugLineNum = 319;BA.debugLine="Log(\"Se pulsó Editar\")";
anywheresoftware.b4a.keywords.Common.Log("Se pulsó Editar");
 //BA.debugLineNum = 321;BA.debugLine="InputDialogTitulo(ListViewResultados.GetItem(Po";
_inputdialogtitulo((int)(BA.ObjectToNumber(mostCurrent._listviewresultados.GetItem(_position))));
 //BA.debugLineNum = 322;BA.debugLine="LLenaListview";
_llenalistview();
 break;
case 1:
 //BA.debugLineNum = 324;BA.debugLine="DBApp.EliminaReview(ListViewResultados.GetItem(";
mostCurrent._dbapp._eliminareview(mostCurrent.activityBA,BA.ObjectToString(mostCurrent._listviewresultados.GetItem(_position)));
 //BA.debugLineNum = 325;BA.debugLine="ListViewResultados.Clear";
mostCurrent._listviewresultados.Clear();
 //BA.debugLineNum = 326;BA.debugLine="LLenaListview";
_llenalistview();
 //BA.debugLineNum = 327;BA.debugLine="addValorGrafico(CalculaEstadistica)";
_addvalorgrafico(_calculaestadistica());
 //BA.debugLineNum = 328;BA.debugLine="LLenaSpinnerGoogle";
_llenaspinnergoogle();
 break;
case 2:
 //BA.debugLineNum = 330;BA.debugLine="Log(\"Se pulsó Cancelar\")";
anywheresoftware.b4a.keywords.Common.Log("Se pulsó Cancelar");
 break;
}
;
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static String  _llenalistview() throws Exception{
int _i = 0;
 //BA.debugLineNum = 241;BA.debugLine="Sub LLenaListview";
 //BA.debugLineNum = 243;BA.debugLine="Mi_Cursor=DBApp.Mi_BD.ExecQuery(\"SELECT id_review";
mostCurrent._mi_cursor.setObject((android.database.Cursor)(mostCurrent._dbapp._mi_bd.ExecQuery("SELECT id_review, titulo, nota, tipo FROM REVIEW")));
 //BA.debugLineNum = 245;BA.debugLine="ListViewNotas.Clear";
mostCurrent._listviewnotas.Clear();
 //BA.debugLineNum = 246;BA.debugLine="EditTextTitulo.Text=\"\"";
mostCurrent._edittexttitulo.setText((Object)(""));
 //BA.debugLineNum = 248;BA.debugLine="If Mi_Cursor.RowCount>0 Then";
if (mostCurrent._mi_cursor.getRowCount()>0) { 
 //BA.debugLineNum = 250;BA.debugLine="For i=0 To Mi_Cursor.RowCount-1";
{
final int step158 = 1;
final int limit158 = (int) (mostCurrent._mi_cursor.getRowCount()-1);
for (_i = (int) (0); (step158 > 0 && _i <= limit158) || (step158 < 0 && _i >= limit158); _i = ((int)(0 + _i + step158))) {
 //BA.debugLineNum = 252;BA.debugLine="Mi_Cursor.Position=i";
mostCurrent._mi_cursor.setPosition(_i);
 //BA.debugLineNum = 253;BA.debugLine="ListViewNotas.AddTwoLines(Mi_Cursor.GetString(\"";
mostCurrent._listviewnotas.AddTwoLines(mostCurrent._mi_cursor.GetString("id_review"),mostCurrent._mi_cursor.GetString("titulo")+" ("+mostCurrent._mi_cursor.GetString("nota")+", "+mostCurrent._mi_cursor.GetString("tipo")+")");
 }
};
 };
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _llenaspinnergoogle() throws Exception{
int _i = 0;
 //BA.debugLineNum = 221;BA.debugLine="Sub LLenaSpinnerGoogle";
 //BA.debugLineNum = 223;BA.debugLine="Mi_Cursor_Google=DBApp.Mi_BD.ExecQuery(\"SELECT id";
mostCurrent._mi_cursor_google.setObject((android.database.Cursor)(mostCurrent._dbapp._mi_bd.ExecQuery("SELECT id_review, titulo, nota, tipo FROM REVIEW")));
 //BA.debugLineNum = 225;BA.debugLine="SpinnerReviewG.Clear";
mostCurrent._spinnerreviewg.Clear();
 //BA.debugLineNum = 227;BA.debugLine="If Mi_Cursor_Google.RowCount>0 Then";
if (mostCurrent._mi_cursor_google.getRowCount()>0) { 
 //BA.debugLineNum = 229;BA.debugLine="For i=0 To Mi_Cursor_Google.RowCount-1";
{
final int step147 = 1;
final int limit147 = (int) (mostCurrent._mi_cursor_google.getRowCount()-1);
for (_i = (int) (0); (step147 > 0 && _i <= limit147) || (step147 < 0 && _i >= limit147); _i = ((int)(0 + _i + step147))) {
 //BA.debugLineNum = 231;BA.debugLine="Mi_Cursor_Google.Position=i";
mostCurrent._mi_cursor_google.setPosition(_i);
 //BA.debugLineNum = 233;BA.debugLine="SpinnerReviewG.Add(Mi_Cursor_Google.GetString(\"";
mostCurrent._spinnerreviewg.Add(mostCurrent._mi_cursor_google.GetString("id_review")+": "+mostCurrent._mi_cursor_google.GetString("titulo")+" ("+mostCurrent._mi_cursor_google.GetString("nota")+", "+mostCurrent._mi_cursor_google.GetString("tipo")+")");
 }
};
 };
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _llenaspinnernota() throws Exception{
double _i = 0;
 //BA.debugLineNum = 213;BA.debugLine="Sub LLenaSpinnerNota";
 //BA.debugLineNum = 214;BA.debugLine="Dim I As Double=7.0";
_i = 7.0;
 //BA.debugLineNum = 215;BA.debugLine="Do While I>=1.0";
while (_i>=1.0) {
 //BA.debugLineNum = 216;BA.debugLine="SpinnerNota.add(Round2(I,1))";
mostCurrent._spinnernota.Add(BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2(_i,(int) (1))));
 //BA.debugLineNum = 217;BA.debugLine="I=I-0.1";
_i = _i-0.1;
 }
;
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim FontSize = 12 As Float";
_fontsize = (float) (12);
 //BA.debugLineNum = 19;BA.debugLine="Dim MaxVal, MinVal, ScaleMin, ScaleMax, ScaleInte";
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
 //BA.debugLineNum = 26;BA.debugLine="Dim GPS1 As GPS";
_gps1 = new anywheresoftware.b4a.gps.GPS();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _sensor_accelerometerchanged(float _x,float _y,float _z) throws Exception{
 //BA.debugLineNum = 413;BA.debugLine="Sub Sensor_AccelerometerChanged (X As Float, Y As";
 //BA.debugLineNum = 415;BA.debugLine="If PAUSA= True Then";
if (_pausa==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 416;BA.debugLine="If X>12 OR X<-12 Then";
if (_x>12 || _x<-12) { 
 //BA.debugLineNum = 417;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mod";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 419;BA.debugLine="RelojAcelerometro.Interval=800";
_relojacelerometro.setInterval((long) (800));
 }else {
 //BA.debugLineNum = 421;BA.debugLine="If Y>12 OR Y<-12 Then";
if (_y>12 || _y<-12) { 
 //BA.debugLineNum = 422;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) Mo";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 424;BA.debugLine="RelojAcelerometro.Interval=800";
_relojacelerometro.setInterval((long) (800));
 }else {
 //BA.debugLineNum = 426;BA.debugLine="If Z>12 OR Z<-12 Then";
if (_z>12 || _z<-12) { 
 //BA.debugLineNum = 427;BA.debugLine="TabHost1.CurrentTab=(TabHost1.CurrentTab+1) M";
mostCurrent._tabhost1.setCurrentTab((int) ((mostCurrent._tabhost1.getCurrentTab()+1)%mostCurrent._tabhost1.getTabCount()));
 //BA.debugLineNum = 429;BA.debugLine="RelojAcelerometro.Interval=800";
_relojacelerometro.setInterval((long) (800));
 };
 };
 };
 };
 //BA.debugLineNum = 435;BA.debugLine="PAUSA=False";
_pausa = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 436;BA.debugLine="End Sub";
return "";
}
public static String  _spinnerreviewg_itemclick(int _position,Object _value) throws Exception{
float _latitud = 0f;
float _longitud = 0f;
anywheresoftware.b4a.objects.collections.List _poslat = null;
anywheresoftware.b4a.objects.collections.List _poslong = null;
String _id_reseña = "";
 //BA.debugLineNum = 518;BA.debugLine="Sub SpinnerReviewG_ItemClick (Position As Int, Val";
 //BA.debugLineNum = 519;BA.debugLine="Dim Latitud As Float";
_latitud = 0f;
 //BA.debugLineNum = 520;BA.debugLine="Dim Longitud As Float";
_longitud = 0f;
 //BA.debugLineNum = 521;BA.debugLine="Dim PosLat, PosLong As List";
_poslat = new anywheresoftware.b4a.objects.collections.List();
_poslong = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 522;BA.debugLine="Dim id_reseña As String";
_id_reseña = "";
 //BA.debugLineNum = 524;BA.debugLine="id_reseña=SpinnerReviewG.GetItem(Position).SubStr";
_id_reseña = mostCurrent._spinnerreviewg.GetItem(_position).substring((int) (0),mostCurrent._spinnerreviewg.GetItem(_position).indexOf(":"));
 //BA.debugLineNum = 526;BA.debugLine="Latitud=DBApp.Mi_BD.ExecQuerySingleResult(\"SELECT";
_latitud = (float)(Double.parseDouble(mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT latitud FROM REVIEW WHERE id_review='"+_id_reseña+"'")));
 //BA.debugLineNum = 527;BA.debugLine="Longitud=DBApp.Mi_BD.ExecQuerySingleResult(\"SELEC";
_longitud = (float)(Double.parseDouble(mostCurrent._dbapp._mi_bd.ExecQuerySingleResult("SELECT longitud FROM REVIEW WHERE id_review='"+_id_reseña+"'")));
 //BA.debugLineNum = 529;BA.debugLine="PosLat.Initialize";
_poslat.Initialize();
 //BA.debugLineNum = 530;BA.debugLine="PosLong.Initialize";
_poslong.Initialize();
 //BA.debugLineNum = 532;BA.debugLine="PosLat.Add(Latitud)";
_poslat.Add((Object)(_latitud));
 //BA.debugLineNum = 533;BA.debugLine="PosLong.Add(Longitud)";
_poslong.Add((Object)(_longitud));
 //BA.debugLineNum = 535;BA.debugLine="GoogleMaps.ShowMap(Latitud,Longitud, 15, True, Tr";
mostCurrent._googlemaps._showmap(mostCurrent.activityBA,_latitud,_longitud,(int) (15),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True,"TOP_LEFT",anywheresoftware.b4a.keywords.Common.True,_poslat,_poslong,anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False,"#ff0000",(float) (0.5),(int) (3),mostCurrent._webviewgoogle);
 //BA.debugLineNum = 536;BA.debugLine="End Sub";
return "";
}
public static String  _spinnertipo_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 443;BA.debugLine="Sub SpinnerTipo_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 445;BA.debugLine="Select SpinnerTipo.GetItem(Position)";
switch (BA.switchObjectToInt(mostCurrent._spinnertipo.GetItem(_position),"Texto","Voz")) {
case 0:
 //BA.debugLineNum = 448;BA.debugLine="InputDialogTexto";
_inputdialogtexto();
 break;
case 1:
 //BA.debugLineNum = 450;BA.debugLine="InputDialogVoz";
_inputdialogvoz();
 break;
}
;
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return "";
}
public static String  _tabbuscar() throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub TabBuscar";
 //BA.debugLineNum = 142;BA.debugLine="TabHost1.AddTab(\"Buscador\",\"layoutBusca\")";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Buscador","layoutBusca");
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _tabgooglemaps() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub TabGoogleMaps";
 //BA.debugLineNum = 129;BA.debugLine="TabHost1.AddTab(\"Google Maps\",\"layoutGoogleMaps\")";
mostCurrent._tabhost1.AddTab(mostCurrent.activityBA,"Google Maps","layoutGoogleMaps");
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _tabgraficopie() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 132;BA.debugLine="Sub TabGraficoPie";
 //BA.debugLineNum = 133;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 134;BA.debugLine="p.Initialize(\"\")";
_p.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 135;BA.debugLine="pnlPie.Initialize(\"pnlPie\")";
mostCurrent._pnlpie.Initialize(mostCurrent.activityBA,"pnlPie");
 //BA.debugLineNum = 136;BA.debugLine="p.AddView(pnlPie, 0, 0, 100%x, 100%y - 100dip)";
_p.AddView((android.view.View)(mostCurrent._pnlpie.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 137;BA.debugLine="TabHost1.AddTab2(\"Resumen\", p)";
mostCurrent._tabhost1.AddTab2("Resumen",(android.view.View)(_p.getObject()));
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _tabhost1_tabchanged() throws Exception{
 //BA.debugLineNum = 501;BA.debugLine="Sub TabHost1_TabChanged";
 //BA.debugLineNum = 502;BA.debugLine="If TabHost1.CurrentTab=0 Then";
if (mostCurrent._tabhost1.getCurrentTab()==0) { 
 //BA.debugLineNum = 503;BA.debugLine="LLenaListview";
_llenalistview();
 };
 //BA.debugLineNum = 506;BA.debugLine="If TabHost1.CurrentTab=2 Then";
if (mostCurrent._tabhost1.getCurrentTab()==2) { 
 //BA.debugLineNum = 507;BA.debugLine="EditTextBusca.Text=\"\"";
mostCurrent._edittextbusca.setText((Object)(""));
 };
 //BA.debugLineNum = 511;BA.debugLine="End Sub";
return "";
}
public static String  _vr_result(boolean _success,anywheresoftware.b4a.objects.collections.List _texts) throws Exception{
 //BA.debugLineNum = 379;BA.debugLine="Sub VR_Result (Success As Boolean, Texts As List)";
 //BA.debugLineNum = 380;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 382;BA.debugLine="IdTexto.Input=Texts.Get(0)";
mostCurrent._idtexto.setInput(BA.ObjectToString(_texts.Get((int) (0))));
 //BA.debugLineNum = 383;BA.debugLine="InputDialogTexto";
_inputdialogtexto();
 };
 //BA.debugLineNum = 386;BA.debugLine="End Sub";
return "";
}
}
