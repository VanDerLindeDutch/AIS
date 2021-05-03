let i =0;
function main(){
    let modalWindow = document.getElementById('exampleModalCenter')
    if(modalWindow!=null){
        modalWindow.id = 'modalWindow'+i;
        modalWindow.previousElementSibling.setAttribute('data-target', '#modalWindow'+i)
        i++;
        main()
    }
}

main()