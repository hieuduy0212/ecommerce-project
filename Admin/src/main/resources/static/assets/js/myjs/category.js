let addCategoryForm = document.getElementById("add-category-form");
if (addCategoryForm) {
    addCategoryForm.addEventListener('submit', (event) => {
        event.preventDefault();
        let categoryName = document.getElementById("category-name").value.trim();
        if (categoryName === null || categoryName.length === 0) {
            alert("Please enter a new category");
        } else {
            let url = `http://localhost:2603/admin/category`;
            let options = {
                method: "POST", mode: "cors", headers: {
                    "Content-Type": "application/json",
                }, body: JSON.stringify({"name": categoryName})
            }
            fetch(url, options).then(response => {
                console.log(response)
                if (response.ok) return response.json();
                else throw new Error("Category already exists!");
            }).then(data => {
                let categoryTable = document.getElementById("category-table")
                let row = categoryTable.insertRow(-1)
                let c1 = row.insertCell(0)
                let c2 = row.insertCell(1)
                let c3 = row.insertCell(2)
                let c4 = row.insertCell(3)
                c1.innerHTML = `<i class="fab fa-angular fa-lg text-danger me-3"></i> <strong>${data.id}</strong>`
                c2.innerText = data.name
                c3.innerHTML = `<span class="badge bg-label-primary me-1">Active</span>`
                c4.innerHTML = `<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#editCategoryModal"
                                        onclick="openEditCategoryModal(${data.id},${data.activated})"
                                >Edit</button>
                                <button type="button"
                                        class="btn btn-danger"
                                        onclick="changeStatus(${data.id})">
                                Disable</button>`
                row.setAttribute('id', 'category-' + data.id)
                console.log("Add new successfully!")

                if(document.getElementById('show-no-category')){
                    document.getElementById('show-no-category').style.display = 'none'
                    document.getElementById('display-categories').style.display = 'block'
                }
                document.getElementById('btn-close').click()
            }).catch(error => alert(error))
        }
    })
}


function changeStatus(categoryId) {
    let confirm = window.confirm("Are you sure?")
    if (confirm) {
        let url = `http://localhost:2603/admin/category/${categoryId}`
        let options = {
            method: "delete",
            mode: "cors"
        }
        fetch(url, options).then(response => {
            console.log(response)
            let row = document.getElementById('category-' + categoryId)
            if (row.getElementsByTagName('td')[2].getElementsByTagName('span')[0].innerText == 'ACTIVE') {
                row.cells[2].getElementsByTagName('span')[0].innerText = 'Disabled'
                row.cells[3].getElementsByTagName('button')[1].innerText = 'Enable'
                row.cells[3].getElementsByTagName('button')[1].classList.remove('btn-danger')
                row.cells[3].getElementsByTagName('button')[1].classList.add('btn-outline-danger')
            } else {
                row.cells[2].getElementsByTagName('span')[0].innerText = 'Active'
                row.cells[3].getElementsByTagName('button')[1].innerText = 'Disable'
                row.cells[3].getElementsByTagName('button')[1].classList.remove('btn-outline-danger')
                row.cells[3].getElementsByTagName('button')[1].classList.add('btn-danger')
            }
        })
    }
}

let editCategoryForm = document.getElementById('edit-category-form')

function openEditCategoryModal(id, activated) {
    let row = document.getElementById('category-' + id);
    let name = row.cells[1].textContent;
    document.getElementById('category-name-edit').value = name
    document.getElementById('category-id-edit').value = id

    if (editCategoryForm) {
        editCategoryForm.addEventListener('submit', function (event) {
            event.preventDefault()
            let categoryName = document.getElementById('category-name-edit').value.trim()
            let categoryId = document.getElementById('category-id-edit').value
            let url = `http://localhost:2603/admin/category`
            let options = {
                method: 'put',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: categoryId,
                    name: categoryName,
                    activated: activated
                })
            }


            fetch(url, options).then(async response => {
                if (!response.ok) {
                    throw new Error(await response.text().then(t => t))
                }
                return response.json()
            }).then(data => {
                console.log(data)
                alert("Saved")
                row.cells[1].innerText = data.name
                document.getElementById('btn-close-edit').click()
            }).catch(error => {
                let msg = JSON.parse(error.message)
                alert(msg.errorDesc)
            })
        })
    }
}

