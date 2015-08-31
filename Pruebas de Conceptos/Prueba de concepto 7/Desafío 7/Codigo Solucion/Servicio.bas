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
	Dim Timer_Fotoresistor As Timer
End Sub
Sub Service_Create
 	' Establecimiento de las Propiedades de Notificación    
    N.Initialize
    N.Icon = "icon"
    N.Sound = True
    N.Vibrate = True
    N.Light = True
    N.OnGoingEvent=True
    N.SetInfo("Desafío 7", "Ejecutándose", Main) 
    N.Notify(1)
End Sub

Sub Service_Start (StartingIntent As Intent)
	Timer_Fotoresistor.Initialize("LoopFotoresistor",100)
	Timer_Fotoresistor.Enabled=True
End Sub

Sub Service_Destroy
	N.Cancel(1)
End Sub

Sub LoopFotoresistor_Tick
	Main.ValorAnalogoFotoR=Main.pin39_fotoresistor.Read
	Main.wBuffer(0)=0x00 
	Main.wBuffer(1)=NumberFormat(-128*Main.ValorAnalogoFotoR+128,1,0)
	
	CallSub(Main, "ActualizaLabelValor")
	
	Main.SPI.WriteRead(0,Main.wBuffer,Main.wBuffer.Length,2,Null,0)

End Sub