$("#rutVal").inputmask({
	mask: "[99.999.999]-[9|K|k]"
});

const forceKeyPressUppercase = (e) => {
    let el = e.target;
    let charInput = e.keyCode;
    if((charInput >= 97) && (charInput <= 122)) { // range lowercase characters
        if(!e.ctrlKey && !e.metaKey && !e.altKey) {
            let newChar = charInput - 32;
            let start = el.selectionStart;
            let end = el.selectionEnd;
            el.value = el.value.substring(0, start) + String.fromCharCode(newChar) + el.value.substring(end);
            el.setSelectionRange(start+1, start+1);
            e.preventDefault();
        }
    }
};

const forceKeyPressLowercase = (e) => {
    let el = e.target;
    let charInput = e.keyCode;
    if((charInput >= 65) && (charInput <= 90)) { // range uppercase characters
        if(!e.ctrlKey && !e.metaKey && !e.altKey) {
            let newChar = charInput + 32;
            let start = el.selectionStart;
            let end = el.selectionEnd;
            el.value = el.value.substring(0, start) + String.fromCharCode(newChar) + el.value.substring(end);
            el.setSelectionRange(start+1, start+1);
            e.preventDefault();
        }
    }
};

document.getElementById("nameCap").addEventListener("keypress", function(e) {
    if(0 == this.selectionStart) {
    // uppercase first letter
    forceKeyPressUppercase(e);
    } else {
    // lowercase other letters
    forceKeyPressLowercase(e);
    }
}, false);


document.getElementById("apeCap").addEventListener("keypress", function(e) {
    if(0 == this.selectionStart) {
    // uppercase first letter
    forceKeyPressUppercase(e);
    } else {
    // lowercase other letters
    forceKeyPressLowercase(e);
    }
}, false);