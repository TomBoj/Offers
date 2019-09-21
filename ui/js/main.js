
document.addEventListener( "DOMContentLoaded", get_data, false );

function get_data() {
	get_offer_data();
	get_product_data();
}

function get_offer_data(){
    let json_url = 'http://localhost:8081/offers';
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() { 
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            append_offer_data(data);
        }
    }
    xmlhttp.open("GET", json_url, true);
    xmlhttp.send();
}

function append_offer_data(data){
    let table = document.getElementById('offers-body');
    data.forEach(function(object) {
        let tr = document.createElement('tr');
        let idCell = document.createElement('td');
        idCell.innerHTML = '<td>' + object.offerId + '</td>';
        tr.appendChild(idCell);
        let descCell = document.createElement('td');
        descCell.innerHTML = '<td>' + object.description + '</td>';
        tr.appendChild(descCell);        
        let productCell = document.createElement('td');
        let products = object.products;
        let productList = document.createElement('ul')
        products.forEach(function(product) {
        	let productElement = document.createElement('li');
        	productElement.innerHTML = product.name + ': ' + product.price;
        	productList.appendChild(productElement);
        });
        productCell.appendChild(productList);
        tr.appendChild(productCell);
        let priceCell = document.createElement('td');					
        priceCell.innerHTML = '<td>' + object.price + '</td>';
        tr.appendChild(priceCell);
        let startDateCell = document.createElement('td');					
        startDateCell.innerHTML = '<td>' + object.startDate + '</td>';
        tr.appendChild(startDateCell);
        let endDateCell = document.createElement('td');					
        endDateCell.innerHTML = '<td>' + object.endDate + '</td>';
        tr.appendChild(endDateCell);
        let statusCell = document.createElement('td');					
        statusCell.innerHTML = '<td>' + object.status + '</td>';
        tr.appendChild(statusCell);
        table.appendChild(tr);
    });
}

function get_product_data(){
    let json_url = 'http://localhost:8081/products';
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() { 
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            append_product_data(data);
            populate_form(data);
        }
    }
    xmlhttp.open("GET", json_url, true);
    xmlhttp.send();
}

function append_product_data(data){
    let table = document.getElementById('products-body');
    data.forEach(function(object) {
        let tr = document.createElement('tr');
        let idCell = document.createElement('td');
        idCell.innerHTML = '<td>' + object.productId + '</td>';
        tr.appendChild(idCell);
        let nameCell = document.createElement('td');
        nameCell.innerHTML = '<td>' + object.name + '</td>';
        tr.appendChild(nameCell);
        let descCell = document.createElement('td');
        descCell.innerHTML = '<td>' + object.description + '</td>';
        tr.appendChild(descCell);
        let priceCell = document.createElement('td');					
        priceCell.innerHTML = '<td>' + object.price + '</td>';
        tr.appendChild(priceCell);
        table.appendChild(tr);
    });    
}

function populate_form(data) {
	console.log(data)
	let productSelect = document.getElementById('select-product');    
    data.forEach(function(product) {
        let option = document.createElement('input');
        option.type = 'checkbox';
        option.value = product.productId;
        let label = document.createElement('span');
        label.innerHTML = product.name + ': ' + product.price;
        productSelect.appendChild(option);
        productSelect.appendChild(label);
        productSelect.appendChild(document.createElement('br'));
    });
}

function create_new_offer(event) {
	let jsonObject = new Object();
	jsonObject.description = document.getElementById('description').value;
	jsonObject.price = document.getElementById('price').value;
	jsonObject.startDate = document.getElementById('startDate').value;
	jsonObject.endDate = document.getElementById('endDate').value;
	
	let selectDiv = document.getElementById('select-product');
	let selectInputs = selectDiv.getElementsByTagName("input");
	let productIdArray = new Array();
	for (var i = 0; i < selectInputs.length; i++) {
		let input = selectInputs[i];
		if(input.checked === true) {
			console.log('Adding ' + input.value);
			productIdArray.push(input.value);
		} else {
			console.log('Not adding ' + input.value);
		}
	}
	jsonObject.productIds = productIdArray;
	
    let json_url = 'http://localhost:8081/offers';
    xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", json_url, true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(jsonObject));
}
