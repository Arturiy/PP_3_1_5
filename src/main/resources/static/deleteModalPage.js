const urlForDel = 'api/admin/';
const formToDelete = document.getElementById('formForDeleting');
const deleteModal = document.getElementById("deleteModal");
const closeDeleteButton = document.getElementById("closeDelete")
const bsDeleteModal = new bootstrap.Modal(deleteModal);

async function loadDataForDeleteModal(id) {
    let response = await fetch(urlForDel + id);
    if (response.ok) {
        const user = await response.json();
        formToDelete.deleteId.value = `${user.id}`;
        formToDelete.deleteUserName.value = `${user.userName}`;
        formToDelete.deleteYearOfBirth.value = `${user.yearOfBirth}`;
        formToDelete.deleteEmail.value = `${user.email}`;
        formToDelete.deletePassword.value = `${user.password}`;
        formToDelete.deleteRoles.value = `${user.rolesToString}`
        bsDeleteModal.show();
    } else {
        alert(`Error, ${response.status}`)
    }
}

async function deleteUser() {
    let method = {
        method: 'DELETE', headers: {
            "Content-Type": "application/json"
        }
    }
    fetch(urlForDel + formToDelete.deleteId.value, method).then(() => {
        closeDeleteButton.click();
        getAdminPage();
    })
}