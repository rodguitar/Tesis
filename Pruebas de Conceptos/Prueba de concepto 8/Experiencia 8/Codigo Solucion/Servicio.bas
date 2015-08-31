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
	Dim Timer_SensorT As Timer
	
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
    N.SetInfo("Desafío 8", "Presione para abrir aplicación", Main) 
    N.Notify(1)
End Sub

Sub Service_Start (StartingIntent As Intent)
	Timer_SensorT.Initialize("LoopSensorT",1000)
	Timer_SensorT.Enabled=True
End Sub

Sub Service_Destroy
	N.Cancel(1)
End Sub

Sub LoopSensorT_Tick
	Try	
		Main.ValorAnalogoSensorT=Main.pin33_SensorT.Voltage
		ValorCelciusService = NumberFormat((((Main.ValorAnalogoSensorT*1024)-500)/10)-4,1,2)
		'fahrenheit = ((celsius * (9.0 / 5.0)) + 32.0)
		If ValorCelciusService >15 Then
		
			Main.pin36_MotorDC.DutyCycle=(ValorCelciusService-12)/10
		Else
			Main.pin36_MotorDC.DutyCycle=0
		End If
			
		CallSub2(Main, "ActualizaLabelValor",ValorCelciusService)
	Catch
		CallSub(Main, "IOIOClose")
		Log("YOYO LoopSensorT_Tick Exception: "& LastException.Message)
		StopService("")
		
	End Try

End Sub