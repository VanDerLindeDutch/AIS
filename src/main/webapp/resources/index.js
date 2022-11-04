let loginRenter = document.getElementById('loginRenter')
let loginLandLord = document.getElementById('loginLandlord')


loginLandLord.addEventListener("mouseover", function () {
    this.style.left = '45%'
    loginRenter.style.right = '55%'
})

loginLandLord.addEventListener("mouseout", function () {
    loginRenter.style.right = '50%'
    loginLandLord.style.left = '50%'


})


loginRenter.addEventListener("mouseover", function () {
    this.style.right = '45%'
    loginLandLord.style.left = '55%'

})

loginRenter.addEventListener("mouseout", function () {
    this.style.right = '50%'
    loginLandLord.style.left = '50%'

})
