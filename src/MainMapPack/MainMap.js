const Romania = { lat: 45.81348649679973, lng: 24.916992187500004}; 
const Brasov = {lat: 45.65244828675087, lng: 25.598144531250004};
const Iasi = {lat: 47.157155337271554, lng: 27.58100509643555};
const Bucuresti = {lat: 44.43181858864192, lng: 26.09390258789063};
const Cluj = {lat: 46.77161447063216, lng: 23.595542907714847};
const Timisoara = {lat: 45.75602615586017, lng: 21.227645874023438};
const Constanta = {lat: 44.17580225275465, lng: 28.631057739257816};
const Arad = {lat: 46.18078126223906, lng: 21.319656372070316};


function CenterLeftControl(controlDiv, map) {
  // Set CSS for the control border.
  const controlUI = document.createElement("div");

  controlUI.style.backgroundColor = "#fff";
  controlUI.style.border = "2px solid #fff";
  controlUI.style.borderRadius = "3px";
  controlUI.style.boxShadow = "0 2px 6px rgba(0,0,0,.3)";
  controlUI.style.cursor = "pointer";
  controlUI.style.marginTop = "8px";
  controlUI.style.marginBottom = "22px";
  controlUI.style.textAlign = "center";
  controlUI.title = "Click to recenter the map";
  controlDiv.appendChild(controlUI);

  // Set CSS for the control interior.
  const controlText = document.createElement("div");

  controlText.style.color = "rgb(25,25,25)";
  controlText.style.fontFamily = "Roboto,Arial,sans-serif";
  controlText.style.fontSize = "16px";
  controlText.style.lineHeight = "38px";
  controlText.style.paddingLeft = "5px";
  controlText.style.paddingRight = "5px";
  controlText.innerHTML = "Recentrare";
  controlUI.appendChild(controlText);
  // Setup the click event listeners: simply set the map to Chicago.
  
}

function initMap(){

    const map = new google.maps.Map(document.getElementById("map"), {
    	center: Romania,
    	zoom: 6,
    	mapId: '27857cc3775dc8c4',
    	clickableIcons: false,
    	disableDefaultUI: true,
    	})
    	
    google.maps.event.addListener(map, 'zoom_changed', function(){
		var Zoom = map.getZoom();
		if(Zoom == 6){
			map.panTo(Romania);
			markerCluj.setVisible(true);
			markerBrasov.setVisible(true);
			markerBucuresti.setVisible(true);
			markerTimisoara.setVisible(true);
			markerIasi.setVisible(true);
			markerArad.setVisible(true);
			markerConstanta.setVisible(true);
		}
	});
   	
   	const markerBrasov = new google.maps.Marker({
		position: Brasov,
		map: map,
	});
	
	markerBrasov.addListener("click", () =>{
		map.setCenter(Brasov);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const markerIasi = new google.maps.Marker({
		position: Iasi,
		map: map,
	});
	
	markerIasi.addListener("click", () =>{
		map.setCenter(Iasi);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const markerBucuresti = new google.maps.Marker({
		position: Bucuresti,
		map: map,
	});
	
	markerBucuresti.addListener("click", () =>{
		map.setCenter(Bucuresti);
		map.setZoom(11);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const markerCluj = new google.maps.Marker({
		position: Cluj,
		map: map,
	});
	
	markerCluj.addListener("click", () =>{
		map.setCenter(Cluj);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const markerTimisoara = new google.maps.Marker({
		position: Timisoara,
		map: map,
	});
	
	markerTimisoara.addListener("click", () =>{
		map.setCenter(Timisoara);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	const markerConstanta = new google.maps.Marker({
		position: Constanta,
		map: map,
	})
	
	markerConstanta.addListener("click", () =>{
		map.setCenter(Constanta);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const markerArad = new google.maps.Marker({
		position: Arad,
		map: map,
	})
	
	markerArad.addListener("click", () =>{
		map.setCenter(Arad);
		map.setZoom(12);
		markerCluj.setVisible(false);
		markerBrasov.setVisible(false);
		markerBucuresti.setVisible(false);
		markerTimisoara.setVisible(false);
		markerIasi.setVisible(false);
		markerArad.setVisible(false);
		markerConstanta.setVisible(false);
	})
	
	const centerControlDiv = document.createElement("div");

  	CenterLeftControl(centerControlDiv, map);
  	centerControlDiv.addEventListener("click", () =>{
		markerCluj.setVisible(true);
		markerBrasov.setVisible(true);
		markerBucuresti.setVisible(true);
		markerTimisoara.setVisible(true);
		markerIasi.setVisible(true);
		markerArad.setVisible(true);
		markerConstanta.setVisible(true);
		map.setCenter(Romania);
		map.setZoom(6);
	});
	
  	map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);
}
   
window.initMap = initMap;