const popupOpen = () => document.querySelector('.popup').classList.add('isActive');
const popupClose = () => {
    document.querySelector('.popup').classList.remove('isActive');
    document.querySelectorAll('.popup__inp input').forEach(input => input.value = '');
}
document.querySelectorAll('.popup-open').forEach(item => item.addEventListener('click',popupOpen));
document.querySelectorAll('.popup-close').forEach(item => item.addEventListener('click',popupClose));

class TableStringPost {
    constructor(companyName, sharePrice, priceDate) {
        this.companyName = companyName;
        this.sharePrice = sharePrice;
        this.priceDate = priceDate;
    }
}
class TableStringPut {
    constructor(companyName, priceId, sharePrice, priceDate) {
        this.companyName = companyName;
        this.priceId = priceId;
        this.sharePrice = sharePrice;
        this.priceDate = priceDate;
    }
}



//------------------------------ ПОЛУЧИТЬ ВСЕ ДАННЫЕ ТАБЛИЦЫ
let status = function (response) {
    if (response.status !== 200 && response.status !== 201  && response.status !== 202) {
        return Promise.reject(new Error(response.statusText));
    }
    return Promise.resolve(response);
}
let json = function (response) {
    return response.json();
}

const inp = (val, name, type = 'text') => `<td><input class="table__input" type='${type}' name='${name}' value='${val}'></td>`;

//------------------------- Начальная инициализация данных из БД
getAllTableData();

function getAllTableData () {
    fetch('http://localhost:8080/shares/all', {
    })
        .then(status)
        .then(json)
        .then(function (data) {
            // вставка в таблицу строк
            let tBody = document.querySelector('.table tbody');
            tBody.innerHTML = '';
            data.forEach( item => {
                let tr = document.createElement('tr');
                tr.setAttribute('id', item.priceId);
                tr.insertAdjacentHTML('beforeend', inp(item.priceDate, 'priceDate', 'date'));
                tr.insertAdjacentHTML('beforeend', inp(item.companyName, 'companyName'));
                tr.insertAdjacentHTML('beforeend', inp(item.sharePrice, 'sharePrice', 'number'));
                tBody.append(tr);
            })
            addTableListeners();
        })
        .catch(function (error) {
        })
}

// ----------------------------- ИЗМЕНЕНИЕ СТРОКИ И ОБНОВЛЕНИЕ ТАБЛИЦЫ
function addTableListeners() {
    document.querySelectorAll('.table__input')
        .forEach( inp => inp.addEventListener('change', function() {
            changeTableString(this);
        }));
}
function changeTableString (inp) {
    let tableString = inp.parentElement.parentElement;
    let id = tableString.getAttribute('id');
    let company = tableString.querySelector('[name = companyName]');
    let date = tableString.querySelector('[name = priceDate]');
    let price = tableString.querySelector('[name = sharePrice]');

    let tableStringPut = new TableStringPut(company.value, id, price.value, date.value);

    fetch('http://localhost:8080/shares', {
        method: 'put',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(tableStringPut)
    })
        .then((res) => {
            if (res.status >= 200 && res.status < 300) {
                return res;
            } else {
                let error = new Error(res.statusText);
                error.response = res;
                throw error
            }
        })
        .then(function (data) {
        })
        .catch(function (error) {
            getAllTableData();
            alert('Некорректый ввод данных!');
        })
}


//------------------------- создание новой строки И ОБНОВЛЕНИЕ ТАБЛИЦЫ
let submitButton = document.getElementById('submit_btn');
let form = document.getElementById('popup_form');
submitButton.addEventListener('click', e => {
    e.preventDefault();
    createNewString();
    popupClose();
});

function createNewString() {
    let company = document.getElementById('popup__inp_share');
    let date = document.getElementById('popup__inp_date');
    let price = document.getElementById('popup__inp_price');

    let tableStringPost = new TableStringPost(company.value, price.value, date.value);

    fetch('http://localhost:8080/shares', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(tableStringPost)
    })
        .then((res) => {
            if (res.status >= 200 && res.status < 300) {
                return res;
            } else {
                let error = new Error(res.statusText);
                error.response = res;
                throw error
            }
        })
        .then(function (data) {
            getAllTableData();
        })
        .catch(function (error) {
            alert('Некорректый ввод данных!');
        })
}


//------------------ Chart
//let ctx = document.getElementById('myChart');

google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'Day');
    data.addColumn('number', 'Guardians of the Galaxy');
    data.addColumn('number', 'The Avengers');
    data.addColumn('number', 'Transformers: Age of Extinction');

    data.addRows([
        [1,  37.8, 80.8, 41.8],
        [2,  30.9, 69.5, 32.4],
        [3,  25.4,   57, 25.7],
        [4,  11.7, 18.8, 10.5],
        [5,  11.9, 17.6, 10.4],
        [6,   8.8, 13.6,  7.7],
        [7,   7.6, 12.3,  9.6],
        [8,  12.3, 29.2, 10.6],
        [9,  16.9, 42.9, 14.8],
        [10, 12.8, 30.9, 11.6],
        [11,  5.3,  7.9,  4.7],
        [12,  6.6,  8.4,  5.2],
        [13,  4.8,  6.3,  3.6],
        [14,  4.2,  6.2,  3.4]
    ]);

    var options = {
        chart: {
            title: 'Box Office Earnings in First Two Weeks of Opening',
            subtitle: 'in millions of dollars (USD)'
        },
        width: 900,
        height: 500
    };

    var chart = new google.charts.Line(document.getElementById('myChart'));

    chart.draw(data, google.charts.Line.convertOptions(options));
}