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
public static int _cuenta = 0;
public static anywheresoftware.b4a.objects.NotificationWrapper _n = null;
public b4a.example.main _main = null;
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim cuenta As Int";
_cuenta = 0;
 //BA.debugLineNum = 9;BA.debugLine="cuenta = 0";
_cuenta = (int) (0);
 //BA.debugLineNum = 10;BA.debugLine="Dim N As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
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
 //BA.debugLineNum = 20;BA.debugLine="N.SetInfo(\"Contador\", \"Se inició a los \"&cuent";
_n.SetInfo(processBA,"Contador","Se inició a los "+BA.NumberToString(_cuenta)+" Segundos",(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 21;BA.debugLine="N.Insistent=true";
_n.setInsistent(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 22;BA.debugLine="N.Notify(1)";
_n.Notify((int) (1));
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 33;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 34;BA.debugLine="N.Cancel(1)";
_n.Cancel((int) (1));
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 26;BA.debugLine="cuenta = cuenta + 1";
_cuenta = (int) (_cuenta+1);
 //BA.debugLineNum = 28;BA.debugLine="StartServiceAt(\"\", DateTime.Now + 1 * 1000, Tr";
anywheresoftware.b4a.keywords.Common.StartServiceAt(processBA,(Object)(""),(long) (anywheresoftware.b4a.keywords.Common.DateTime.getNow()+1*1000),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 29;BA.debugLine="CallSub(Main, \"ActualizaLabelCuenta\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._main.getObject()),"ActualizaLabelCuenta");
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
}
