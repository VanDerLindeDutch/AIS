const start = document.getElementById('startOfRent')
const end = document.getElementById('endOfRent')
const amount = document.getElementById('amount')
const price = document.getElementById('price')
const totalAmount = document.getElementById('totalAmount')

start.min = new Date().toISOString().split("T")[0].substr(0, 7);
end.addEventListener('input', updatePrice)
start.addEventListener('input', updatePrice)


function updatePrice(e) {
    let results;
    let endDate = new Date(e.target.value)
    let startDate = new Date(start.value);
    end.min = start.value;
    let months;
    months = (endDate.getFullYear() - startDate.getFullYear()) * 12;
    months -= startDate.getMonth();
    months += endDate.getMonth();
    results = months <= 0 ? 0 : months * Number(price.innerText);
    amount.textContent = results;
    totalAmount.value = results;

}