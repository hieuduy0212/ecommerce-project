function addToCart(productId, quantity) {
    if(quantity == -1){
        quantity = document.getElementById("quantity").value
    }
    let url = `http://localhost:2604/customer/cart/add/${productId}/${quantity}`
    let options = {
        method: "get",
        mode: "cors"
    }
    fetch(url, options).then(r => {
        if (r.redirected) {
            window.location.href = r.url
        } else
            getSizeCartAndTotaLPriceCart()
    })
}

function checkAllItems(event){
    let cartItemsTable = document.getElementById('cartItemsTable')
    if(cartItemsTable){
        let checkBoxs = cartItemsTable.getElementsByTagName('tbody')[0].querySelectorAll("input[type='checkbox']")
        checkBoxs.forEach(e => e.checked = event.checked)

    }
}

function updateCartItem(quantityInput, cartItemId){
    let quantity = quantityInput.value

    let url = `http://localhost:2604/customer/cart/update/${cartItemId}/${quantity}`
    let options = {
        method: "put", mode:"cors"
    }
    fetch(url, options).then(response => {
        return response.json()
    }).then(data => {
        console.log(data)
        let cartItemTr = document.getElementById('cart-item-'+cartItemId)
        cartItemTr.querySelectorAll('.total-value')[0].innerText=`${data.totalPrice.toFixed(1)}`
        updateTotalCartValue()
    })
}

function updateTotalCartValue(){
    let cartItemsTable = document.getElementById('cartItemsTable')
    let cartItems = cartItemsTable.querySelector('tbody').querySelectorAll('tr')
    let totalCartValue = 0.0
    cartItems.forEach((ci) => {
        totalCartValue += parseFloat(ci.querySelector('.total-value').textContent)
    })
    document.querySelector('.total-cart-value').innerText='$' + totalCartValue.toFixed(1)
}