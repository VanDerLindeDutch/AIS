const selectService = document.getElementById('selectService');
const place = document.getElementById('placeToAlerts');
const checkbox = document.getElementsByClassName('serviceCheckBox');

function main(){
     selectService.onchange= function() {
        let options = document.getElementsByTagName('option');

        let chosen;
        for (let option of options) {
            if(option.value==this.selectedIndex){
                chosen = option;
            }
        }
        for (let check of checkbox) {
             if(check.value==this.selectedIndex){
                 check.checked=true;
             }
        }
        place.innerHTML += '<div class="alert alert-success alert-dismissible">\n' +
            '  <a id="'+this.selectedIndex+'" href="#" class="close" data-dismiss="alert" aria-label="close" onclick="enableOption(this.id)" >&times;</a>\n' +
            chosen.innerText +
            '</div>'

        chosen.disabled=true
    }
}

function enableOption(value){
    let options = document.getElementsByTagName('option');
    for (let check of checkbox) {
        if(check.value==value){
            check.checked=false;
        }
    }
    for (let option of options) {
        if(option.value==value){
            option.disabled=false
        }
    }
}

main()