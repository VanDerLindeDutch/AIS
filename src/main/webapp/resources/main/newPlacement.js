const selectService = document.getElementById('selectService');
const place = document.getElementById('placeToAlerts');
const checkbox = document.getElementsByClassName('serviceCheckBox');
const button = document.getElementById('sendServices');
const ref = document.getElementById('sortHref');
const refValue = document.getElementById('sortValue');
const checkBoxText = document.getElementById('checkboxText');
const checkBoxInput = document.getElementById('inputCheckbox')


function main() {
    selectService.onchange = function () {
        let options = document.getElementsByTagName('option');

        let chosen;
        for (let option of options) {
            if (option.value == this.selectedIndex) {
                chosen = option;
            }
        }
        for (let check of checkbox) {
            if (check.value == this.selectedIndex) {
                check.checked = true;
            }
        }
        place.innerHTML += '<div class="services alert alert-success alert-dismissible">\n' +
            '  <a id="' + this.selectedIndex + '" href="#" class="close" data-dismiss="alert" aria-label="close" onclick="enableOption(this.id)" >&times;</a>\n' +
            chosen.innerText +
            '</div>'

        chosen.disabled = true
    }


    ref.onclick = async function (){
        await setCheckbox()
        refValue.innerText = "price";
        setTimeout(button.click, 500);
    }

    function setCheckbox(){
        console.log("dsasdsa")
        let array = checkBoxText.value.split(',');
        console.log(array)
        for (let check of checkbox) {
            if (array.includes(check)) {
                check.checked = false;
            }
        }
    }

}

function enableOption(value) {
    let options = document.getElementsByTagName('option');
    for (let check of checkbox) {
        if (check.value == value) {
            check.checked = false;
        }
    }
    for (let option of options) {
        if (option.value == value) {
            option.disabled = false
        }
    }
}

main()