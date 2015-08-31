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
	Dim N As Notification
	Dim Timer_SensorFlex As Timer
	Dim Alarma As Beeper ' Activar Audio Lib
	Dim ValorCelciusService As Float
End Sub
Sub Service_Create
 	' Establecimiento de las Propiedades de Notificación    
    N.Initialize
    N.Icon = "icon"
    N.Sound = True
    N.Vibrate = True
    N.Light = True
    N.OnGoingEvent=True
    N.SetInfo("Desafío 9", "Presione para abrir aplicación", Main) 
    N.Notify(1)
End Sub

Sub Service_Start (StartingIntent As Intent)
	Timer_SensorFlex.Initialize("LoopSensorFlex",1000)
	Timer_SensorFlex.Enabled=True
	Alarma.Initialize(300,1000)
	
End Sub

Sub Service_Destroy
	N.Cancel(1)
End Sub

Sub LoopSensorFlex_Tick
	Try	
		Main.ValorAnalogoSensorF = Main.pin33_SensorFuerza.Read
		If Main.ValorAnalogoSensorF <0.009 Then
			Alarma.Beep
			Main.pin11_Zumbador.DutyCycle = 0.8
		Else
			Main.pin11_Zumbador.DutyCycle = 0
		End If
		
		CallSub(Main, "ActualizaLabelValor")
	Catch
		CallSub(Main, "IOIOClose")
		Log("YOYO LoopSensorT_Tick Exception: "& LastException.Message)
		StopService("")
		
	End Try

End Sub