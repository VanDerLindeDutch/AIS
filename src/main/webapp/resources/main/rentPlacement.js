const start = document.getElementById('startOfRent')
const end = document.getElementById('endOfRent')
const amount = document.getElementById('amount')
const price = document.getElementById('price')

end.addEventListener('input',updatePrice )


function updatePrice(e){
    let endDate = new Date(e.target.value)
    let startDate = new Date(start.value);
    let months;
    months = (endDate.getFullYear() - startDate.getFullYear()) * 12;
    months -= startDate.getMonth();
    months += endDate.getMonth();
    amount.textContent = months <=0 ? 0 : months * Number(price.innerText);

}