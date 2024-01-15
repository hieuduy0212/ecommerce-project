let productName
let productCategorySelected
let productCategory
let productDesc
let productCurrentQuantity
let productPrice
let productImage = []

let addProductModal = document.getElementById('addProductModal')
if (addProductModal) {
    addProductModal.addEventListener('submit', (e) => {
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

        let product = {
            'name': productName,
            'description': productDesc,
            'currentQuantity': productCurrentQuantity,
            'price': productPrice,
            'category': productCategory,
            'activated': true
        }

        let formData = new FormData()
        formData.append('product', new Blob([JSON.stringify(product)], {type: 'application/json'}))
        formData.append('imageFile', null)
        for (let i = 0; i < productImage.length; i++) {
            formData.append('imageFile', productImage[i], productImage[i].name)
        }

        let url = `http://localhost:2603/admin/product`

        let options = {
            method: 'post', mode: 'cors', body: formData
        }

        fetch(url, options).then(async response => {
            console.log(response)
            if (response.ok) {
                return response.json();
            } else {
                await response.text().then(text => {
                    throw new Error(text)
                })
            }
        }).then(data => {
            console.log(data)
            document.getElementById('btnClose').click()
            let productTable = document.getElementById('product-table')
            let row = productTable.insertRow(-1)
            let c1 = row.insertCell(0)
            let c2 = row.insertCell(1)
            let c3 = row.insertCell(2)
            let c4 = row.insertCell(3)
            let c5 = row.insertCell(4)
            let c6 = row.insertCell(5)
            let c7 = row.insertCell(6)
            let c8 = row.insertCell(7)

            row.setAttribute('id', 'product-' + data.id)
            c1.innerHTML = `<i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${data.id}</strong>`
            c2.innerText = data.name
            c3.innerText = data.category.name
            c4.innerText = data.price
            c5.innerText = data.currentQuantity
            c6.innerHTML = `<button class="btn btn-link" data-bs-toggle="modal" data-bs-target="#showProductImagesModel" onclick="showProductImages(${data.id})"><i class='bx bxs-image'></i></button>`
            c7.innerHTML = `<span class="badge bg-label-primary me-1">Active</span>`
            c8.innerHTML = `<a type="button" class="btn btn-success"  href="/admin/edit-product/${data.id}">Edit</a>
                            <button type="button" class="btn btn-danger" onclick="changeProductStatus(${data.id})">
                            Disable</button>`
            if (document.getElementById('show-no-product')) {
                document.getElementById('show-no-product').style.display = 'none'
                document.getElementById('display-products').style.display = 'block'
            }
        }).catch(err => {
            console.error(err.message)
        })

    })
    addProductModal.addEventListener('hide.bs.modal', () => {
        document.getElementById('addProductForm').reset()
        document.getElementById('imagePreview').innerHTML = ``
        productImage = []
    })
}

// preview image after choose a image
let imageProduct = document.getElementById('imageProduct')
if (imageProduct) {
    imageProduct.onchange = () => {
        for (let i = 0; i < imageProduct.files.length; i++) {
            productImage.push(imageProduct.files[i])
        }
        showPreviewImage()
    }
}

function removeImageFromPreview(ind) {
    productImage.splice(ind, 1)
    showPreviewImage()
}

function showPreviewImage() {
    let imgs = ``;
    for (let i = 0; i < productImage.length; i++) {
        imgs += `<div class="col-3" style="position: relative;">
                            <span class="btn-remove-image" onclick="removeImageFromPreview(${i})">x</span>
                            <img class="img-fluid d-flex mx-auto my-4"
                                 style="aspect-ratio: 1/1;width: 95%;border-radius: 30%"
                                 src="${URL.createObjectURL(productImage[i])}"
                                 alt="no image"
                            >
                        </div>`
    }
    document.getElementById('imagePreview').innerHTML = imgs
}


function changeProductStatus(productId) {
    let confirm = window.confirm("Are you sure?")
    if (confirm) {
        let url = `http://localhost:2603/admin/product/${productId}`
        let options = {
            method: "delete", mode: "cors"
        }
        fetch(url, options).then(response => {
            console.log(response)
            let row = document.getElementById('product-' + productId)
            if (row.getElementsByTagName('td')[6].getElementsByTagName('span')[0].innerText == 'ACTIVE') {
                row.cells[6].getElementsByTagName('span')[0].innerText = 'Disabled'
                row.cells[7].getElementsByTagName('button')[0].innerText = 'Enable'
                row.cells[7].getElementsByTagName('button')[0].classList.remove('btn-danger')
                row.cells[7].getElementsByTagName('button')[0].classList.add('btn-outline-danger')
            } else {
                row.cells[6].getElementsByTagName('span')[0].innerText = 'Active'
                row.cells[7].getElementsByTagName('button')[0].innerText = 'Disable'
                row.cells[7].getElementsByTagName('button')[0].classList.remove('btn-outline-danger')
                row.cells[7].getElementsByTagName('button')[0].classList.add('btn-danger')
            }
        })
    }
}

function showProductImages(productId) {
    let url = `http://localhost:2603/admin/product/${productId}`
    let options = {
        method: 'get', mode: 'cors',
    }
    fetch(url, options).then(response => response.json())
        .then(data => {
            console.log(data)
            let content = createImage(data)
            document.getElementById('showImages').innerHTML = content
        })
}

function createImage(product) {
    let productImages = product.images
    let ret = ``
    for (let i = 0; i < productImages.length; i++) {
        let imageFileData = productImages[i]
        const imageBlob = dataURItoBlob(imageFileData.picByte, imageFileData.type)
        ret += `<div class="col-3" >
                    <img class="img-fluid d-flex mx-auto my-4"
                         style="aspect-ratio: 1/1;width: 95%;border-radius: 30%"
                         src="${URL.createObjectURL(imageBlob)}"
                         alt="no image"
                    >
                </div>`
    }
    return ret
}

function dataURItoBlob(picBytes, imageType) {
    const byteString = window.atob(picBytes)
    const arrayBuffer = new ArrayBuffer(picBytes.length)
    const int8Array = new Uint8Array(arrayBuffer)
    for (let i = 0; i < byteString.length; i++) {
        int8Array[i] = byteString.charCodeAt(i)
    }
    const blob = new Blob([int8Array], {type: imageType});
    return blob

}