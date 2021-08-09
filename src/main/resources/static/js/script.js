document.addEventListener("DOMContentLoaded", async function () {
    await updateUsersTab();
    $('#v-pills-home-tab').on('show.bs.tab', async function () {
        await updateUsersTab();
    });
    $('#new-user-tab').on('show.bs.tab', async function () {
        await newUserOpen();
    });
});

//Abstract get
const getQuery = async (url) => {
    let response = await fetch(url);
    if (!response.ok)
        throw Error(response.status.toString());
    return await response.json();
};

//Abstract POST, PUT, DELETE
const userQuery = async (method, user) => {
    let response = await fetch('http://localhost:8080/admin/users', {
        method: method,
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    if (!response.ok)
        throw Error(response.status.toString());
};

const allRoles = async () => {
    return await getQuery('http://localhost:8080/admin/roles');
};

const allUsers = async () => {
    return await getQuery('http://localhost:8080/admin/users');
};

const getUser = async (id) => {
    return await getQuery('http://localhost:8080/admin/users/' + id);
};

const saveUser = async (user) => {
    await userQuery('POST', user);
};

const updateUser = async (user) => {
    await userQuery('PUT', user);
};

const deleteUser = async (id) => {
    let response = await fetch('http://localhost:8080/admin/users/' + id, {
        method: 'DELETE'
    });
    if (!response.ok)
        throw Error(response.status.toString());
};

async function updateUsersTab() {
    let users = await allUsers();
    let tbody = document.querySelector('#user-table');
    const cols = ['id', 'name', 'surname', 'age', 'email', 'stroles'];
    tbody.innerHTML = '';

    users.forEach((user) => {
        stringRoles(user);
        let tr = getUserRow(user, cols);
        let td = document.createElement('td');
        let edit = document.createElement('button');
        edit.className = "btn btn-primary";
        edit.setAttribute("onclick", "editClick(this)");
        edit.innerHTML = "EDIT";
        edit.setAttribute("data-id", user.id);
        td.appendChild(edit);
        tr.appendChild(td);
        let td1 = document.createElement('td');
        let del = document.createElement('button');
        del.className = "btn btn-danger";
        del.setAttribute("onclick", "deleteClick(this)");
        del.innerHTML = "Delete";
        del.setAttribute("data-id", user.id);
        td1.appendChild(del);
        tr.appendChild(td1);
        tbody.appendChild(tr);
    });
}

function editClick(elem) {
    let form = document.querySelector('#edit-form');
    let id = elem.getAttribute('data-id');
    fillEditDeleteForm(id, form).catch((e) => alert(e));
    $('#edit-modal').modal('show');
}

function deleteClick(elem) {
    let form = document.querySelector('#delete-form');
    let id = elem.getAttribute('data-id');
    fillEditDeleteForm(id, form).catch((e) => alert(e));
    $('#delete-modal').modal('show');
}

async function newUserOpen(){
    let form = document.querySelector('#add-form');
    let select = form.querySelector('select');
    await setRoleOptions(select, 'USER');
}

async function fillEditDeleteForm(userId, form) {
    let user = await getUser(userId);
    let fields = ['id', 'name', 'surname', 'age', 'email', 'password'];
    let inputs = form.querySelectorAll('input');
    let select = form.querySelector('select');
    stringRoles(user);
    inputs.forEach((n, i) => n.value = user[fields[i]]);
    await setRoleOptions(select, user.stroles);
}

async function setRoleOptions(select, selRoles) {
    let roles = await allRoles();
    roles.sort((a, b) => a.name > b.name ? 1 : -1);
    select.innerHTML = '';
    roles.forEach((role) => {
        let option = document.createElement('option');
        let text = role.name.replace('ROLE_', '');
        option.innerText = text;
        option.value = role.id;
        if (selRoles.includes(text)) {
            option.setAttribute('selected', 'selected');
        }
        select.appendChild(option);
    });
}

async function editModalClickHandler() {
    let form = document.querySelector('#edit-form');
    let fields = ['id', 'name', 'surname', 'age', 'email', 'password'];
    let user = await mapUser(form, fields);
    await updateUser(user);
}

async function deleteModalClickHandler() {
    let form = document.querySelector('#delete-form');
    let id = document.querySelector('#id2').value;
    form.submit();
    await deleteUser(id);
}

async function addClickHandler() {
    let form = document.querySelector('#add-form');
    let fields = ['name', 'surname', 'age', 'email', 'password'];
    let user = await mapUser(form, fields);
    await saveUser(user);
}

async function mapUser(form, fields) {
    let roles = await allRoles();
    let selRoles = [];
    let inputs = (form.querySelectorAll('input'));
    let options = form.querySelector('select').options;
    let user = {};

    inputs.forEach((n, i) => {
        user[fields[i]] = n.value;
    });

    for (let option of options) {
        if (option.selected) {
            selRoles.push(roles.find((role) => role.id == option.value));
        }
    }
    user.roles = selRoles;
    form.submit();
    return user;
}

function getUserRow(user, cols) {
    const tr = document.createElement('tr');
    cols.forEach((coll) => {
        const td = document.createElement('td');
        td.innerHTML = user[coll];
        tr.appendChild(td);
    });
    return tr;
}

function stringRoles(user) {
    user.stroles = user.roles
        .flatMap((x) => x.name.replace('ROLE_', ''))
        .sort()
        .join(' ');
}