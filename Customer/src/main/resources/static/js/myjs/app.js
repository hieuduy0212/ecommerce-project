function getSizeCartAndTotaLPriceCart(){
    let url = `http://localhost:2604/customer/get-size-and-price`
    fetch(url, {
        method: 'get', mode: 'cors'
    }).then(r => {
        return r.json()
    }).then(data => {
        document.getElementsByName("sizeCart").forEach(ele => ele.innerText=data.sizeCart)
        document.getElementsByName("totalPriceCart").forEach(ele => ele.innerText=`$${data.totalPrice}`)
    })
}
getSizeCartAndTotaLPriceCart()