Type=Service
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
    Dim cuenta As Int
    cuenta = 0
    Dim N As Notification
End Sub
Sub Service_Create
    ' Establecimiento de las Propiedades de Notificación    
    N.Initialize
    N.Icon = "icon"
    N.Sound = True
    N.Vibrate = True
    N.Light = True
    N.OnGoingEvent=True
    N.SetInfo("Contador", "Se inició a los "&cuenta&" Segundos", Main) 
    N.Notify(1) 
End Sub

Sub Service_Start (StartingIntent As Intent)
    cuenta = cuenta + 1
    
    StartServiceAt("", DateTime.Now + 1 * 1000, True)' Hará que cada 1 segundo se reinicie el Servicio
    CallSub(Main, "ActualizaLabelCuenta")
	
End Sub

Sub Service_Destroy
    N.Cancel(1)
End Sub
