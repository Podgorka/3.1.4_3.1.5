const url = 'api/user'
let loggedInUser = document.querySelector('#User')

fetch(url)
    .then(res => res.json())
    .then(data => {
        loggedInUser.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.email}</td>
                                <td>${data.name}</td>
                                <td>${data.lastname}</td>
                                <td>${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
                                `
    })