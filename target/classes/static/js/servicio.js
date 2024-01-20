var regiones = JSON.parse(regionesConAscii.replaceAll('&#034;','"').toString());
var item;

document.getElementById("selectReg").onchange = function (){
    let selCities = document.getElementById("selectCiud")
    while (selCities.firstChild){
        selCities.removeChild(selCities.lastChild);
    }
    let initial = document.createElement('option');
    initial.textContent = "Ciudad";
    initial.value = 0;
    selCities.appendChild(initial);
    region = this.value;
    if (region != 0) {
        item = regiones.find(element => element.id == region)
        if(item != null){
            for(ciudad of item["ciudades"]){
                let opt = document.createElement('option');
                opt.textContent = ciudad.nombre;
                opt.value = ciudad.id;
                selCities.appendChild(opt);
            }
        }
    }
}

document.getElementById("formato-fecha").value = function(){
    var formatoInicial = this.value;
    var formatoFinal = formatoInicial[0].toUpperCase() + formatoInicial.substring(1);
    return formatoFinal;
}



