package KCSoft.GoogleMapsPlus;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class googlemaps {
private static googlemaps mostCurrent = new googlemaps();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public KCSoft.GoogleMapsPlus.main _main = null;
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _showmap(anywheresoftware.b4a.BA _ba,float _centerlat,float _centerlong,int _zoom,boolean _maptypecontrol,boolean _dispzoomcontrol,boolean _dispscalecontrol,String _scalecontrolposition,boolean _dispmarkercenter,anywheresoftware.b4a.objects.collections.List _markerlat,anywheresoftware.b4a.objects.collections.List _markerlong,boolean _dispmarkers,boolean _disppolyline,String _polylinecolor,float _polylineopacity,int _polylinewidth,anywheresoftware.b4a.objects.WebViewWrapper _mapwebview) throws Exception{
String _htmlcode = "";
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 9;BA.debugLine="Sub ShowMap(CenterLat As Float, CenterLong As Floa";
 //BA.debugLineNum = 26;BA.debugLine="Dim HtmlCode As String";
_htmlcode = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim i, j As Int";
_i = 0;
_j = 0;
 //BA.debugLineNum = 29;BA.debugLine="HtmlCode = \"<!DOCTYPE html><html><head><meta name";
_htmlcode = "<!DOCTYPE html><html><head><meta name='viewport' content='initial-scale=1.0, user-scalable=no' /><style type='text/css'>  html { height: 100% }  body { height: 100%; margin: 0px; padding: 0px }#map_canvas { height: 100% }</style><script type='text/javascript' src='http://maps.google.com/maps/api/js?sensor=true'></script><script type='text/javascript'> function initialize() {var latlng = new google.maps.LatLng("+BA.NumberToString(_centerlat)+","+BA.NumberToString(_centerlong)+"); var myOptions = { zoom: "+BA.NumberToString(_zoom)+", center: latlng, disableDefaultUI: true, zoomControl: "+BA.ObjectToString(_dispzoomcontrol)+", scaleControl: "+BA.ObjectToString(_dispscalecontrol)+", scaleControlOptions: {position: google.maps.ControlPosition."+_scalecontrolposition+"}, mapTypeControl: "+BA.ObjectToString(_maptypecontrol)+", mapTypeId: google.maps.MapTypeId.ROADMAP }; var map = new google.maps.Map(document.getElementById('map_canvas'),  myOptions)";
 //BA.debugLineNum = 32;BA.debugLine="If DispMarkerCenter = True Then";
if (_dispmarkercenter==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 33;BA.debugLine="HtmlCode = HtmlCode & \"; var markerc = new googl";
_htmlcode = _htmlcode+"; var markerc = new google.maps.Marker({	position: new google.maps.LatLng("+BA.NumberToString(_centerlat)+","+BA.NumberToString(_centerlong)+"),map: map, title: '',clickable: false,icon: 'http://www.google.com/mapfiles/arrow.png' })";
 };
 //BA.debugLineNum = 37;BA.debugLine="If MarkerLat.Size>0 AND DispMarkers = True Then";
if (_markerlat.getSize()>0 && _dispmarkers==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 38;BA.debugLine="j = MarkerLat.Size - 1";
_j = (int) (_markerlat.getSize()-1);
 //BA.debugLineNum = 39;BA.debugLine="If j = 0 Then";
if (_j==0) { 
 //BA.debugLineNum = 40;BA.debugLine="HtmlCode = HtmlCode & \"; var marker\" & i & \" =";
_htmlcode = _htmlcode+"; var marker"+BA.NumberToString(_i)+" = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+"),map: map, title: 'Test"+BA.NumberToString(_i)+"',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_red.png' })";
 }else if(_j==1) { 
 //BA.debugLineNum = 43;BA.debugLine="HtmlCode = HtmlCode & \"; var marker\" & i & \" =";
_htmlcode = _htmlcode+"; var marker"+BA.NumberToString(_i)+" = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+"),map: map, title: 'Test"+BA.NumberToString(_i)+"',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_green.png' })";
 //BA.debugLineNum = 45;BA.debugLine="HtmlCode = HtmlCode & \"; var marker\" & i & \" =";
_htmlcode = _htmlcode+"; var marker"+BA.NumberToString(_i)+" = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+"),map: map, title: 'Test"+BA.NumberToString(_i)+"',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker.png' })";
 }else {
 //BA.debugLineNum = 48;BA.debugLine="HtmlCode = HtmlCode & \"; var marker0 = new goog";
_htmlcode = _htmlcode+"; var marker0 = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+"),map: map, title: 'Test0',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_greenA.png' })";
 //BA.debugLineNum = 50;BA.debugLine="For i = 1 To j - 1 ' diplays the markers";
{
final int step18 = 1;
final int limit18 = (int) (_j-1);
for (_i = (int) (1); (step18 > 0 && _i <= limit18) || (step18 < 0 && _i >= limit18); _i = ((int)(0 + _i + step18))) {
 //BA.debugLineNum = 51;BA.debugLine="HtmlCode = HtmlCode & \"; var marker\" & i & \" =";
_htmlcode = _htmlcode+"; var marker"+BA.NumberToString(_i)+" = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+"),map: map, title: 'Test"+BA.NumberToString(_i)+"',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker_orange"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (_i+65)))+".png' })";
 }
};
 //BA.debugLineNum = 54;BA.debugLine="HtmlCode = HtmlCode & \"; var marker\" & (j) & \"";
_htmlcode = _htmlcode+"; var marker"+BA.NumberToString((_j))+" = new google.maps.Marker({	position: new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_j))+","+BA.ObjectToString(_markerlong.Get(_j))+"),map: map, title: 'Test"+BA.NumberToString(_j)+"',clickable: true, draggable: true, icon: 'http://www.google.com/mapfiles/marker"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (_j+65)))+".png' })";
 };
 //BA.debugLineNum = 59;BA.debugLine="If DispPolyline = True AND j > 0 Then";
if (_disppolyline==anywheresoftware.b4a.keywords.Common.True && _j>0) { 
 //BA.debugLineNum = 60;BA.debugLine="HtmlCode = HtmlCode & \"; var points = [\"";
_htmlcode = _htmlcode+"; var points = [";
 //BA.debugLineNum = 61;BA.debugLine="HtmlCode = HtmlCode & \" new google.maps.LatLng(";
_htmlcode = _htmlcode+" new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get((int) (0)))+","+BA.ObjectToString(_markerlong.Get((int) (0)))+")";
 //BA.debugLineNum = 62;BA.debugLine="For i=1 To j";
{
final int step26 = 1;
final int limit26 = _j;
for (_i = (int) (1); (step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26); _i = ((int)(0 + _i + step26))) {
 //BA.debugLineNum = 63;BA.debugLine="HtmlCode = HtmlCode & \", new google.maps.LatLn";
_htmlcode = _htmlcode+", new google.maps.LatLng("+BA.ObjectToString(_markerlat.Get(_i))+","+BA.ObjectToString(_markerlong.Get(_i))+")";
 }
};
 //BA.debugLineNum = 65;BA.debugLine="HtmlCode = HtmlCode & \"] \"";
_htmlcode = _htmlcode+"] ";
 //BA.debugLineNum = 66;BA.debugLine="HtmlCode = HtmlCode & \"; var polyline = new goo";
_htmlcode = _htmlcode+"; var polyline = new google.maps.Polyline({path: points, strokeColor: '"+_polylinecolor+"', strokeOpacity: "+BA.NumberToString(_polylineopacity)+", strokeWeight: "+BA.NumberToString(_polylinewidth)+"})";
 //BA.debugLineNum = 67;BA.debugLine="HtmlCode = HtmlCode & \"; polyline.setMap(map)\"";
_htmlcode = _htmlcode+"; polyline.setMap(map)";
 };
 };
 //BA.debugLineNum = 70;BA.debugLine="HtmlCode = HtmlCode & \"; }</script></head><body o";
_htmlcode = _htmlcode+"; }</script></head><body onload='initialize()'>  <div id='map_canvas' style='width:100%; height:100%'></div></body></html>";
 //BA.debugLineNum = 72;BA.debugLine="MapWebView.LoadHtml(HtmlCode)";
_mapwebview.LoadHtml(_htmlcode);
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
}
