let productName
let productCategorySelected
let productCategory
let productDesc
let productCurrentQuantity
let productPrice
let productSale
let productActivated
let productId
let newProductImage = []

let editProductForm = document.getElementById('editProductForm')
if (editProductForm) {
    editProductForm.addEventListener('submit', (e) => {
        e.preventDefault()

        productName = document.getElementById('productName').value
        productCategorySelected = document.getElementById('productCategory')
        productCategory = {
            id: productCategorySelected.value,
            name: productCategorySelected.options[productCategorySelected.selectedIndex].text
        }
        productDesc = document.getElementById('productDesc').value
        productCurrentQuantity = document.getElementById('productCurrentQuantity').value
        productPrice = document.getElementById('productPrice').value
        productSale = document.getElementById('productSale').value
        productActivated = document.getElementById('productActivated').value
        productId = document.getElementById('productId').value

        let product = {
            'id': productId,
            'name': productName,
            'description': productDesc,
            'currentQuantity': productCurrentQuantity,
            'price': productPrice,
            'category': productCategory,
            'sale': productSale,
            'activated': productActivated
        }

        let formData = new FormData()
        formData.append('product', new Blob([JSON.stringify(product)], {type: 'application/json'}))
        formData.append('imageFile', null)

        for (let i = 0; i < newProductImage.length; i++) {
            formData.append('imageFile', newProductImage[i], newProductImage[i].name)
        }

        let url = `http://localhost:2603/admin/product`
        let options = {
            method: 'put',
            mode: 'cors',
            body: formData
        }

        fetch(url, options).then(response => {
            console.log(response)
            if (response.ok) {
                return response.json()
            }
        }).then(data => {
            console.log(data)
            alert("Update product successfully")
            window.location.reload()
        })


    })
}

function deleteImage(imgId, productId) {
    if (confirm("Are you sure?")) {
        let url = `http://localhost:2603/admin/product/${imgId}/${productId}`
        let options = {
            method: 'delete',
            mode: 'cors'
        }
        fetch(url, options).then(resp => {
            console.log(resp);
            window.location.reload()
        })
    }
}

let newProductImageInput = document.getElementById('newProductImageInput')
if (newProductImageInput) {
    newProductImageInput.addEventListener('change', () => {
        for (let i = 0; i < newProductImageInput.files.length; i++) {
            newProductImage.push(newProductImageInput.files[i])
        }
        showNewProductImage()
    })
}

function showNewProductImage() {
    let content = ``
    for (let i = 0; i < newProductImage.length; i++) {
        content += `<div class="col-3" style="position: relative;">
                        <span class="btn-remove-image" onclick="removeNewImageFromPreview(${i})">x</span>
                        <img class="img-fluid d-flex mx-auto my-4"
                             style="aspect-ratio: 1/1;width: 95%;border-radius: 30%"
                             src="${URL.createObjectURL(newProductImage[i])}"
                             alt="no image"
                        >
                    </div>`
    }
    document.getElementById('showPreviewNewImage').innerHTML = content
}

function removeNewImageFromPreview(ind) {
    newProductImage.splice(ind, 1)
    showNewProductImage()
}