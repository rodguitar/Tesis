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

End Sub

Sub ShowMap(CenterLat As Float, CenterLong As Float, Zoom As Int, MapTypeControl As Boolean, DispZoomControl As Boolean, DispScaleControl As Boolean, ScaleControlPosition As String, DispMarkerCenter As Boolean, MarkerLat As List, MarkerLong As List, DispMarkers As Boolean, DispPolyline As Boolean, PolyLineColor As String, PolyLineOpacity As Float, PolyLineWidth As Int, MapWebView As WebView)
	' CenterLat        = latitude of map center in degrees
	' CenterLong       = longitude of map center in degrees
	' Zoom             = zomm index   0 - 21
	' MapTypeControl   = true displays the map type control
	' DispZoomControl  = true displays the zoom control otherwise false
	' ScaleControl     = true displays the zoom control otherwise false
	' ScaleControlPosition  = position of the scale control TOP_LEFT, TOP_CENTER, TOP_RIGHT, LEFT_CENTER, RIGHT_CENTER, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
	' DispMarkerCenter = true sets a marker on the center of the map
	' MarkerLat 	  	 = List of lat  positions of the markers 
	' MarkerLong 		   = List of long positions of the markers
	' DipsMarkers  		 = true displays the markers
	' DispPolyline 	   = true displays a polyline with the markers as vertices
	' PolyLineColor    = polyline color hexadecimal  #ff0000 = red  #00ff00 = green   #0000ff = blue
	' PolyLineOpacity  = polyline opacity  0.0  transparent   1.0 full opaque
	' PolyLineWidth    = polyline width in pixels
	
 	Dim HtmlCode As String
	Dim i, j As Int
	
	HtmlCode = "<!DOCTYPE html><html><head><meta name='viewport' content='initial-scale=1.0, user-scalable=no' /><style type='text/css'>  html { height: 100% }  body { height: 100%; margin: 0px; padding: 0px }#map_canvas { height: 100% }</style><script type='text/javascript' src='http://maps.google.com/maps/api/js?sensor=true'></script><script type='text/javascript'> function initialize() {var latlng = new google.maps.LatLng(" & CenterLat & "," & CenterLong & "); var myOptions = { zoom: "&Zoom&", center: latlng, disableDefaultUI: true, zoomControl: "& DispZoomControl & ", scaleControl: "& DispScaleControl & ", scaleControlOptions: {position: google.maps.ControlPosition." & ScaleControlPosition & "}, mapTypeControl: "& MapTypeControl& ", mapTypeId: google.maps.MapTypeId.ROADMAP }; var map = new google.maps.Map(document.getElementById('map_canvas'),  myOptions)" 

	' displays a marker on the map center
	If DispMarkerCenter = True Then	
		HtmlCode = HtmlCode & "; var markerc = new google.maps.Marker({	position: new google.maps.LatLng(" & CenterLat & "," & CenterLong & "),map: map, title: '',clickable: false,icon: 'http://www.google.com/mapfiles/arrow.png' })"
	End If
	
	' displays markers on the map
	If MarkerLat.Size>0 AND DispMarkers = True Then
		j = MarkerLat.Size - 1
		If j = 0 Then
			HtmlCode = HtmlCode & "; var marker" & i & " = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & "),map: map, title: 'Test" & i & "',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_red.png' })"
'			HtmlCode = HtmlCode & "; google.maps.event.addListener(marker" & i & ", 'click', function() {alert('Marker1')} )"
		Else If j = 1 Then
			HtmlCode = HtmlCode & "; var marker" & i & " = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & "),map: map, title: 'Test" & i & "',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_green.png' })"
'			HtmlCode = HtmlCode & "; google.maps.event.addListener(marker" & i & ", 'click', function() {map.set_center(marker" & i & ")} )"
			HtmlCode = HtmlCode & "; var marker" & i & " = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & "),map: map, title: 'Test" & i & "',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker.png' })"
'			HtmlCode = HtmlCode & "; google.maps.event.addListener(marker" & i & ", 'click', function() {map.set_center(marker" & i & ")} )"
		Else
			HtmlCode = HtmlCode & "; var marker0 = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & "),map: map, title: 'Test0',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_greenA.png' })"
'			HtmlCode = HtmlCode & "; google.maps.event.addListener(marker0, 'click', function() {alert('Marker0')} )"
			For i = 1 To j - 1 ' diplays the markers
				HtmlCode = HtmlCode & "; var marker" & i & " = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & "),map: map, title: 'Test" & i & "',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_orange" & Chr(i + 65) & ".png' })"
'				HtmlCode = HtmlCode & "; google.maps.event.addListener(marker" & i & ", 'click', function() {map.set_center(marker" & i & ")} )"
			Next
			HtmlCode = HtmlCode & "; var marker" & (j) & " = new google.maps.Marker({	position: new google.maps.LatLng(" & MarkerLat.Get(j) & "," & MarkerLong.Get(j) & "),map: map, title: 'Test" & j & "',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker" & Chr(j + 65) & ".png' })"
'			HtmlCode = HtmlCode & "; google.maps.event.addListener(marker" & i & ", 'click', function() {map.set_center(marker" & i & ")} )"
		End If
		
	' displays a polyline between the markers
		If DispPolyline = True AND j > 0 Then
			HtmlCode = HtmlCode & "; var points = ["
			HtmlCode = HtmlCode & " new google.maps.LatLng(" & MarkerLat.Get(0) & "," & MarkerLong.Get(0) & ")"
			For i=1 To j
				HtmlCode = HtmlCode & ", new google.maps.LatLng(" & MarkerLat.Get(i) & "," & MarkerLong.Get(i) & ")"
			Next
			HtmlCode = HtmlCode & "] "
			HtmlCode = HtmlCode & "; var polyline = new google.maps.Polyline({path: points, strokeColor: '" & PolyLineColor & "', strokeOpacity: " & PolyLineOpacity & ", strokeWeight: " & PolyLineWidth & "})"
			HtmlCode = HtmlCode & "; polyline.setMap(map)"
		End If
	End If
	HtmlCode = HtmlCode & "; }</script></head><body onload='initialize()'>  <div id='map_canvas' style='width:100%; height:100%'></div></body></html>"

	MapWebView.LoadHtml(HtmlCode)
End Sub