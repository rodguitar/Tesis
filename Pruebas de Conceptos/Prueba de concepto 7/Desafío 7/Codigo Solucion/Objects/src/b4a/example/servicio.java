package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class servicio extends android.app.Service {
	public static class servicio_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, servicio.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static servicio mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return servicio.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.servicio");
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.servicio", processBA, _service);
		}
        BA.LogInfo("** Service (servicio) Create **");
        processBA.raiseEvent(null, "service_create");
        processBA.runHook("oncreate", this, null);
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (servicio) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        BA.LogInfo("** Service (servicio) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.NotificationWrapper _n = null;
public static anywheresoftware.b4a.objects.Timer _timer_fotoresistor = null;
public static int _stringtoint = 0;
public b4a.example.main _main = null;
public static String  _loopfotoresistor_tick() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub LoopFotoresistor_Tick";
 //BA.debugLineNum = 34;BA.debugLine="Main.ValorAnalogoFotoR=Main.pin39_fotoresistor.Re";
mostCurrent._main._valoranalogofotor = mostCurrent._main._pin39_fotoresistor.Read();
 //BA.debugLineNum = 35;BA.debugLine="Main.wBuffer(0)=0x00";
mostCurrent._main._wbuffer[(int) (0)] = (byte) (0x00);
 //BA.debugLineNum = 36;BA.debugLine="Main.wBuffer(1)=NumberFormat(-128*Main.ValorAnalo";
mostCurrent._main._wbuffer[(int) (1)] = (byte)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.NumberFormat(-128*mostCurrent._main._valoranalogofotor+128,(int) (1),(int) (0))));
 //BA.debugLineNum = 38;BA.debugLine="CallSub(Main, \"ActualizaLabelValor\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._main.getObject()),"ActualizaLabelValor");
 //BA.debugLineNum = 40;BA.debugLine="Main.SPI.WriteRead(0,Main.wBuffer,Main.wBuffer.Le";
mostCurrent._main._spi.WriteRead((int) (0),mostCurrent._main._wbuffer,mostCurrent._main._wbuffer.length,(int) (2),(byte[])(anywheresoftware.b4a.keywords.Common.Null),(int) (0));
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim N As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Dim Timer_Fotoresistor As Timer";
_timer_fotoresistor = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim StringToInt As Int";
_stringtoint = 0;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 14;BA.debugLine="N.Initialize";
_n.Initialize();
 //BA.debugLineNum = 15;BA.debugLine="N.Icon = \"icon\"";
_n.setIcon("icon");
 //BA.debugLineNum = 16;BA.debugLine="N.Sound = True";
_n.setSound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 17;BA.debugLine="N.Vibrate = True";
_n.setVibrate(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 18;BA.debugLine="N.Light = True";
_n.setLight(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 19;BA.debugLine="N.OnGoingEvent=True";
_n.setOnGoingEvent(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 20;BA.debugLine="N.SetInfo(\"Desafío 7\", \"Ejecutándose\", Main)";
_n.SetInfo(processBA,"Desafío 7","Ejecutándose",(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 21;BA.debugLine="N.Notify(1)";
_n.Notify((int) (1));
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 30;BA.debugLine="N.Cancel(1)";
_n.Cancel((int) (1));
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 25;BA.debugLine="Timer_Fotoresistor.Initialize(\"LoopFotoresistor\",";
_timer_fotoresistor.Initialize(processBA,"LoopFotoresistor",(long) (100));
 //BA.debugLineNum = 26;BA.debugLine="Timer_Fotoresistor.Enabled=True";
_timer_fotoresistor.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
}
