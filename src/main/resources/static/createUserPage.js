const urlNewUser = '/api/admin';
const newUserForm = document.getElementById('newUserForm');
const allRolesSelector = document.getElementById('roles');
const rolesSelected = newUserForm.roles;

async function getAllRoles() {
    try {
        const response = await fetch('/api/admin/roles').then();
        const allRoles = await response.json();
        allRolesSelector.innerHTML = '';
        allRoles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id;
            option.text = role.name;
            allRolesSelector.appendChild(option);
        });
    } catch (error) {
        alert('Произошла ошибка при получении ролей: ' + error.message);
    }
}

async function addNewUser() {
    let rolesList = [];
    for (let i = 0; i < rolesSelected.options.length; i++) {
        if (rolesSelected.options[i].selected) {
            rolesList.push({
                id: rolesSelected.options[i].value
            });
        }
    }
    let method = {
        method: 'POST', headers: {
            "Content-Type": "application/json"
        }, body: JSON.stringify({
            userName: newUserForm.username.value,
            yearOfBirth: newUserForm.yearOfBirth.value,
            email: newUserForm.email.value,
            password: newUserForm.password.value,
            roles: rolesList
        })
    };
    let response = await fetch(urlNewUser, method).then(res => {
        if (res.ok) {
            newUserForm.reset();
            getAdminPage();
            document.getElementById('user_table-tab').click();
        } else {
            showError(res);
        }
    })
}

getAllRoles();