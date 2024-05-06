const userPageURL = '/api/user';

async function getUserPage() {
    try {
        const response = await fetch(userPageURL);
        if (response.ok) {
            const user = await response.json();
            let roles = user.rolesToString;
            document.getElementById("navbar-username").innerHTML = user.email;
            document.getElementById("navbar-roles").innerHTML = roles;
            getInformationAboutUser(user);
        } else {
            alert(`Ошибка, ${response.status}`);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function getInformationAboutUser(user) {
    const tableBody = document.getElementById('userTable');
    let dataHtml = '';
    dataHtml = `<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.yearOfBirth}</td>
    <td>${user.email}</td>
    <td>${user.rolesToString}</td>   
</tr>`;
    tableBody.innerHTML = dataHtml;
}

getUserPage().then(result => console.log(result));