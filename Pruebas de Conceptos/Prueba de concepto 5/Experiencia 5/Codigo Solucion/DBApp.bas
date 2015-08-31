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
		sSql = sSql & "comentario_texto TEXT, " 
		sSql = sSql & "nombre_voz TEXT, "
		sSql = sSql & "latitud TEXT, "
		sSql = sSql & "longitud TEXT, " 
		sSql = sSql & "fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)" 
		Mi_BD.ExecNonQuery(sSql)
	Catch
		Log("catch ERROR CREATE TABLE IF NOT EXISTS REVIEW: "&LastException.Message)
	End Try
End Sub

'Inserta reseña en la base de datos.
Sub InsertaReview(titulo As String, nota As String, tipo As String, comentario_texto As String,nombre_voz As String, latitud As Double, longitud As Double)
	Dim fecha As String
	Try
		fecha=Mi_BD.ExecQuerySingleResult("SELECT datetime(CURRENT_TIMESTAMP,'localtime')")
	Catch
		Log("catch ERROR INSERT INTO REVIEW: "&LastException.Message)
	End Try
	
	Try
		Mi_BD.ExecNonQuery2("INSERT INTO REVIEW (titulo,nota,tipo,comentario_texto,nombre_voz,latitud,longitud,fecha_registro) VALUES (?,?,?,?,?,?,?,'"&fecha&"')", Array As Object(titulo,nota,tipo,comentario_texto,nombre_voz,latitud,longitud))
	Catch
		Log("catch ERROR INSERT INTO REVIEW: "&LastException.Message)
	End Try
End Sub

Sub EditaReview(titulo As String, id_review As String)
	Try
		Mi_BD.ExecNonQuery("UPDATE REVIEW SET titulo='"& titulo &"' WHERE id_review='"& id_review &"'")
		Log("OK UPDATE REVIEW Realizado.")
		
	Catch
		Log("catch UPDATE REVIEW DBApp   " & LastException.Message)
		
	End Try
End Sub

Sub EliminaReview(id_review As String)

	Try
		Mi_BD.ExecNonQuery("DELETE from REVIEW where id_review= '"& id_review &"' ")
		Log("OK DELETE from REVIEW Realizado.")
		
	Catch
		Log("catch DELETE from REVIEW DBApp   " & LastException.Message)
		
	End Try
End Sub
