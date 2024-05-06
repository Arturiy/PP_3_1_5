const adminPageURL = '/api/admin';
const currentUserURL = '/api/user';
const currentUser = fetch(currentUserURL).then(response => response.json())
const errorModal = document.getElementById('errorModal');
const errorModalBody = document.getElementById('errorBody');
const errorModalHeader = document.getElementById('errorHeader');
const bsErrorModal = new bootstrap.Modal(errorModal);

async function getAdminPage() {
    let page = await fetch(adminPageURL);
    if (page.ok) {
        currentUser.then(user => {
            document.getElementById("navbar-username").innerHTML = user.username
            document.getElementById("navbar-roles").innerHTML = user.rolesToString
        })
        let allUser = await page.json();
        loadTableData(allUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}

function loadTableData(userList) {
    const tableBody = document.getElementById('adminTable');
    let dataHtml = '';
    for (let user of userList) {
        dataHtml += `<tr>
    <td>${user.id}</td>
    <td>${user.userName}</td>
    <td>${user.yearOfBirth}</td>
    <td>${user.email}</td>
    <td>${user.rolesToString}</td>
    <td>
        <button type="button" class="btn btn-primary" data-bs-toogle="modal"
        data-bs-target="#editModal" 
        onclick="loadDataForEditModal(${user.id})">Edit</button>
    </td>
    <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal" 
        onclick="loadDataForDeleteModal(${user.id})">Delete</button>
    </td>
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}

async function getUserPage() {
    let roles = currentUser.rolesToString;
    document.getElementById("navbar-username").innerHTML = currentUser.email;
    document.getElementById("navbar-roles").innerHTML = roles;
    getInformationAboutUser(currentUser);
}

function getInformationAboutUser(user) {
    const tableBody = document.getElementById('userTable');
    let dataHtml = '';
    dataHtml = `<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.yearOfBirth}</td>
    <td>${user.email}</td>
    <td>${user.password}</td>
    <td>${user.rolesToString}</td>   
</tr>`;
    tableBody.innerHTML = dataHtml;
}

getAdminPage();
getUserPage();
