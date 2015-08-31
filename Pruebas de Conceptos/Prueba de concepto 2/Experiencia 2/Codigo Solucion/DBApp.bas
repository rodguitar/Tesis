Type=StaticCode
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim Mi_BD As SQL
End Sub

Sub CreaBD
	Mi_BD.Initialize(File.DirInternal,"DBApp.db",True)' Se crea el archivo en el directorio interno del Smartphone
	
	Dim sSql As String
	
	Try
		sSql = "CREATE TABLE IF NOT EXISTS REVIEW " 
		sSql = sSql & "(id_review INTEGER PRIMARY KEY AUTOINCREMENT, " 
		sSql = sSql & "titulo TEXT, " 
		sSql = sSql & "nota TEXT, " 
		sSql = sSql & "tipo TEXT, " 
		sSql = sSql & "fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)" 
		Mi_BD.ExecNonQuery(sSql)
	Catch
		Log("catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: "&LastException.Message)
	End Try
End Sub

'Inserta reseña en la base de datos.
Sub InsertaReview(titulo As String, nota As String, tipo As String)
	Dim fecha As String
	Try
		fecha=Mi_BD.ExecQuerySingleResult("SELECT datetime(CURRENT_TIMESTAMP,'localtime')")
	Catch
		Log("catch ERROR INSERT INTO REVIEW: "&LastException.Message)
	End Try
	
	Try
		Mi_BD.ExecNonQuery2("INSERT INTO REVIEW (titulo,nota,tipo,fecha_registro) VALUES (?,?,?,'"&fecha&"')", Array As Object(titulo,nota,tipo))
	Catch
		Log("catch ERROR INSERT INTO REVIEW: "&LastException.Message)
	End Try
End Sub