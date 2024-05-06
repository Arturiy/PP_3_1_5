const formToEdit = document.getElementById('formForEditing');
const urlDataEdit = 'api/admin/' + formToEdit.editId.value;
const allRolesSelectorToEdit = document.getElementById('editRoles');
const closeEditButton = document.getElementById("editClose")
const editModal = document.getElementById("editModal");
const bsEditModal = new bootstrap.Modal(editModal);

async function loadDataForEditModal(id) {
    let response = await fetch(urlDataEdit + id);
    if (response.ok) {
        const userToEdit = await response.json();
        formToEdit.editId.value = `${userToEdit.id}`;
        formToEdit.editUserName.value = `${userToEdit.username}`;
        formToEdit.editYearOfBirth.value = `${userToEdit.yearOfBirth}`;
        formToEdit.editEmail.value = `${userToEdit.email}`;
        formToEdit.editPassword.value = `${userToEdit.password}`;
        const roleIds = userToEdit.roles.map(role => Number(role.id));
        for (const option of allRolesSelectorToEdit.options) {
            if (roleIds.includes(Number(option.value))) {
                option.setAttribute('selected', 'selected');
            } else {
                option.removeAttribute('selected');
            }
        }
        bsEditModal.show();
    } else {
        alert(`Ошибка , ${response.status}`)
    }
}

async function showError(updateResponse) {
    errorModalBody.innerHTML = '';
    errorModalHeader.innerHTML = '';
    const errorTitle = document.createElement('h5');
    errorTitle.textContent = updateResponse.status + ' - Ошибка при добавлении пользователя';
    errorModalHeader.appendChild(errorTitle);
    let errorList = await updateResponse.json();
    for (let error of errorList) {
        const p = document.createElement('p');
        p.textContent = error;
        errorModalBody.appendChild(p);
    }
    bsErrorModal.show();
}

async function editUser() {
    let urlEdit = 'api/admin/' + formToEdit.editId.value;
    let rolesList = [];
    for (let i = 0; i < formToEdit.editRoles.options.length; i++) {
        if (formToEdit.editRoles.options[i].selected) {
            rolesList.push({"id": formToEdit.editRoles.options[i].value, "name": formToEdit.editRoles.options[i].text});
        }
    }
    let method = {
        method: 'PUT', headers: {
            "Content-Type": "application/json"
        }, body: JSON.stringify({
            userName: formToEdit.editUserName.value,
            yearOfBirth: formToEdit.editYearOfBirth.value,
            email: formToEdit.editEmail.value,
            password: formToEdit.editPassword.value,
            roles: rolesList
        })
    }
    let updateResponse = await fetch(urlEdit, method).then(res => {
        if (res.ok) {
            closeEditButton.click();
            getAdminPage();
        } else {
            showError(res);
        }
    });
}

async function getAllRoles() {
    try {
        const response = await fetch('/api/admin/roles');
        const allRoles = await response.json();
        allRolesSelectorToEdit.innerHTML = '';
        allRoles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id;
            option.text = role.name;
            allRolesSelectorToEdit.appendChild(option);
        });
    } catch (error) {
        alert('Произошла ошибка при получении ролей: ' + error.message);
    }
}

getAllRoles()