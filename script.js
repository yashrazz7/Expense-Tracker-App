function submitExpense() {
    const data = `${document.getElementById('name').value},${document.getElementById('amt').value},${document.getElementById('cat').value}`;
    fetch('http://localhost:8080/addExpense', { method: 'POST', body: data })
    .then(() => { loadExpenses(); });
}

function deleteExpense(index) {
    fetch('http://localhost:8080/deleteExpense', { method: 'POST', body: index })
    .then(() => loadExpenses());
}

function loadExpenses() {
    fetch('http://localhost:8080/getExpenses')
    .then(res => res.text())
    .then(data => {
        const tbody = document.getElementById('tableBody');
        tbody.innerHTML = "";
        data.split(" | ").forEach((item, index) => {
            if(!item) return;
            const [n, a, c] = item.split(",");
            tbody.innerHTML += `<tr><td>${n}</td><td>${a}</td><td>${c}</td>
                                <td class="delete-btn" onclick="deleteExpense(${index})">Delete</td></tr>`;
        });
    });
}
loadExpenses();