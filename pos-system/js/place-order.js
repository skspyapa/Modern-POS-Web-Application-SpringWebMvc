$(document).ready(function () {
    loadId();
    loadCustomers();
    loadItems();
    loadArray();
    $("#CusIdBtn").focus();
    $("#OrdId").prop("readonly", true);
    $("#Date").prop("readonly", true);
    $("#CusName").prop("readonly", true);
    $("#Description").prop("readonly", true);
    $("#QtyOnHand").prop("readonly", true);
    $("#UnitPrice").prop("readonly", true);


    $("#Quantity").on('keyup',function (e) {

    validateQuantity();

    });
});
var d = new Date();
$("#Date").val(d.toLocaleDateString());
function customerId(event) {
    var custName=event.keyCode;
    if(custName==13){
        $("#ItemCodeBtn").focus();
    }
}

function itemCode(event) {
    var description=event.keyCode;
    if(description==13){
        $("#Quantity").focus();
    }
}

var clickCount=0;
var s0;
var firstCustomer;
var firstItem;
var itemArray =[];
var itemObject={};
var Grandtotal;


function loadId(){
    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/orders?maxOrderId=true',
        async:true
    };
    var numOfOrders;
    $.ajax(ajaxConfig).done(function (maxOrderId,textStatus,jqxhr) {
        var ordId=maxOrderId;

        if(ordId==="null"){
            ordId="C000";
        }
        var idsubs = parseInt(ordId.substr(1,3));

        if (idsubs < 9) {
            $("#OrdId").val("D00" + ++idsubs);
        } else if (idsubs < 99) {
            $("#OrdId").val("D0" + ++idsubs);
        } else if (idsubs > 999) {
            $("#OrdId").val("D" + ++idsubs);
        }

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);


    });

}
function loadArray() {

    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/items',
        async:true
    };

    $.ajax(ajaxConfig).done(function (items,textStatus,jqxhr) {
console.log(items);
        itemArray=items;

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);
    });

}
function loadCustomers() {
    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/customers',
        async:true
    };
    $.ajax(ajaxConfig).done(function (customers,textStatus,jqxhr) {
        $("CustomerId .dropdown-menu .dropdown-item").remove();
        customers.forEach(function (customer) {

            var html='<a class="dropdown-item" href="#">'+customer.id+'</a>';
            $("#CustomerId .dropdown-menu").append(html);
        });
        //$("#CustomerId button").text($("#CustomerId .dropdown-menu .dropdown-item:first-child").text());
        //firstCustomer=$("#CustomerId .dropdown-menu .dropdown-item:first-child").text();
        firstCustomer="SELECT";
        $("#CustomerId button").text(firstCustomer);
        $("#CustomerId .dropdown-menu .dropdown-item").click(function () {
            $("#CustomerId button").text($(this).text());

            var ajaxConfig={
                method:'GET',
                url:'http://localhost:8080/api/v1/customers/'+$(this).text(),
                async:true
            };

            $.ajax(ajaxConfig).done(function (customer,textStatus,jqxhr) {
                $("#CusName").val(customer.name);

            }).fail(function (jqxhr,textStatus,errorThrown) {
                console.log("Please call DEP");
            });
        });

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);
    });

}

function loadItems() {

    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/items',
        async:true
    };
    $.ajax(ajaxConfig).done(function (items,textStatus,jqxhr) {
        $("#itemCode .dropdown-menu .dropdown-item").remove();

        items.forEach(function (item) {

            var html='<a class="dropdown-item" href="#">'+item.code+'</a>';
            $("#itemCode .dropdown-menu").append(html);
        });
        //$("#itemCode button").text($("#itemCode .dropdown-menu .dropdown-item:first-child").text());
        //firstItem=$("#itemCode .dropdown-menu .dropdown-item:first-child").text();
        firstItem="SELECT";
        $("#itemCode button").text(firstItem);
        $("#itemCode .dropdown-menu .dropdown-item").click(function () {
            $("#itemCode button").text($(this).text());


            itemArray.forEach(function (item) {
                //console.log($("#itemCode button").text());
                if (item.code === $("#itemCode button").text()) {
                $("#Description").val(item.description);
                $("#QtyOnHand").val(item.qtyOnHand);
                $("#UnitPrice").val(item.unitPrice);
            }
                });
        });
    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);
    });


}

var isplaceorder=false;
function validateQuantity(){
    $("#Quantity").on("keyup",function () {
        var quantity=$(this).val().trim();
        var checker=/^[0-9]*$/;
        if (quantity.match(checker) && (parseInt($("#QtyOnHand").val())>=parseInt($("#Quantity").val()))) {
            // $("#address").focus();
            $(this).css('border-color', '#ced4da');
        }else if (quantity.length > 0) {
            $(this).focus();
            $(this).select();
            $(this).css('border-color', 'red');
        }else if(quantity.length==0){
            $(this).css('border-color', '#ced4da');
        }
    });
}

$("#BtnSave").click(function (event) {
    if(parseInt($("#QtyOnHand").val())<parseInt($("#Quantity").val())){
            //refresh();

        $("#Quantity").val("");
        $("#Quantity").css('border-color', '#ced4da');
        alert("Please Enter The Correct Value");

    }else{
        var length=$("tbody tr").length;
        if (($("#CusName").val().length != 0) && ($("#Description").val() != 0) && ($("#Quantity").val().trim().length > 0)) {

        var isAlreadyAvailable = false;


        $(this).css('border-color', '#ced4da');
        $("tbody tr").each(function (index, obj) {


            if (obj.firstElementChild.textContent === $("#ItemCodeBtn").text()) {
                isAlreadyAvailable = true;
                rowUpdate($(this));
            }
        });



        if (!isAlreadyAvailable) {
            var html2 = '<tr>' +
                '<td>' + $("#ItemCodeBtn").text() + '</td>' +
                '<td>' + $("#Description").val() + '</td>' +
                '<td>' + $("#Quantity").val() + '</td>' +
                '<td>' + $("#UnitPrice").val() + '</td>' +
                '<td>' + ($("#UnitPrice").val() * $("#Quantity").val()) + '</td>' +
                '<td class="delete">' + '<button class="btn"></button>' + '</td>'
                + '</tr>'

            $("#OrderData").append(html2);

            itemArray.forEach(function (item) {
                //console.log(item.code);
                if (item.code === $("#ItemCodeBtn").text()) {
                    item.qtyOnHand = parseInt(item.qtyOnHand) - parseInt($("#Quantity").val());
                    console.log(parseInt(item.qtyOnHand));
                    console.log(parseInt($("#Quantity").val()));
                    $("#QtyOnHand").val(item.qtyOnHand);
                    console.log(item.qtyOnHand);
                    $("#Quantity").val("");

                }
            });


        }
            if(length==0){
                $('#OrderData').DataTable(
                    {
                        searching: false,
                        pageLength:10
                    }
                );
            }
        refresh();
            rowClick();
            $("#itemCode button").on("click");
            calculateTotal();
    }else {
        alert("Please Select Customer And Item Data Correctly...!");
        refresh();
    }
clickCount=0;
}
});
function rowClick() {
    $("tbody tr").click(function (e) {
        var itemCode=$($(this).children()[0]).text();
        var quantity=$($(this).children()[2]).text();
        var addedquantity=$(this).children();
        if (clickCount < 1) {
            $("#itemCode button").off("click");
            itemArray.forEach(function (item) {

                if (item.code === itemCode) {
                    console.log(item.code+"    "+itemCode);
                    item.qtyOnHand = parseInt(item.qtyOnHand) + parseInt(quantity);
                    console.log(item.qtyOnHand);
                    $("#QtyOnHand").val(item.qtyOnHand);
                    $(addedquantity[2]).text("");
                }
            });


            $("#ItemCodeBtn").text($($(this).children()[0]).text());
            $("#Description").val($($(this).children()[1]).text());
            $("#Quantity").val($($(this).children()[2]).text());
            $("#UnitPrice").val($($(this).children()[3]).text());
        }
        clickCount++;
        //$(this).remove();
    });
}
function refresh() {
    if (isplaceorder) {
        $("#CusIdBtn").text(firstCustomer);
        $("#CusName").val("");
        loadId();
        $("#Total").text("");
    }
    $("#ItemCodeBtn").text(firstItem);
    $("#Description").val("");
    $("#QtyOnHand").val("");
    $("#Quantity").val("");
    $("#UnitPrice").val("");
}


//delete row & update stock
$(document).off("click",".delete");
$(document).on("click",".delete", function () {

    var result = confirm("Are You Sure To Delete   ");
    if (result){
        var rowItemQuantity = $($(this).parent().children()[2]).text();
    var rowItemCode = $($(this).parent().children()[0]).text();
    console.log(rowItemQuantity + " " + rowItemCode);


    itemArray.forEach(function (item) {
        if (item.code === rowItemCode) {
            console.log(item.code + " " + rowItemCode);
            item.qtyOnHand = parseInt(item.qtyOnHand) + parseInt(rowItemQuantity);
            $("#QtyOnHand").val(item.qtyOnHand);
        }
    });
    $(this).parent().remove();
    calculateTotal();
    refresh();
}
});
function calculateTotal() {
    Grandtotal=0;
    $(document).find("tbody tr").each(function (index,obj) {
        console.log(parseInt(index +"   "+$($(this).children()[4]).text()));
        Grandtotal+=parseInt($($(this).children()[4]).text());
    });
    console.log(Grandtotal);
    $("#Total").text(Grandtotal);
}


function rowUpdate(row) {
    row.find("td:nth-child(2)").text($("#Description").val());
    if(clickCount==0) {
        row.find("td:nth-child(3)").text(parseInt(row.find("td:nth-child(3)").text()) + parseInt($("#Quantity").val()));
    }
    else {
        row.find("td:nth-child(3)").text(parseInt($("#Quantity").val()));
    }
    row.find("td:nth-child(4)").text($("#UnitPrice").val());
    row.find("td:nth-child(5)").text(parseInt(row.find("td:nth-child(5)").text())+(parseInt($("#Quantity").val()) * parseFloat($("#UnitPrice").val())));

    itemArray.forEach(function (item) {
        if($("#ItemCodeBtn").text()==item.code){
            item.qtyOnHand=item.qtyOnHand - $("#Quantity").val();
        }
    });


}

$("#PlaceOrder").click(function () {
    var orderDetailsArray=[];
    var orderObject={};
    $("tbody tr").each(function (index, obj) {

        orderDetailsArray.push({itemCode:obj.children[0].textContent,orderId:$("#OrdId").val(), qty:parseInt(obj.children[2].textContent),
            unitPrice:parseFloat(obj.children[3].textContent)});
    });
    orderObject={id:$("#OrdId").val(),date:$("#Date").val(),customerId:$("#CusIdBtn").text(),itemDetailDTOS:orderDetailsArray};


    var ajaxConfig={
        method:'POST',
        url:'http://localhost:8080/api/v1/orders',
        async:true,
        data:JSON.stringify(orderObject),
        contentType:"application/json"
    };

    $.ajax(ajaxConfig).done(function (response,textStatus,jqxhr) {
        console.log(response);
        if (jqxhr.status===201) {
            loadId();
            refresh();
            alert("Successfully Saved");
        }else {
            alert("Not Successfully Saved");
        }

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);

    });
    isplaceorder=true;
    refresh();
    isplaceorder=false;

    $(document).find("tbody tr").each(function () {
        $(this).parent().remove();
    });

});

$("#NewOrder").click(function () {
    if(clickCount<1) {
        loadId();
        refresh();
    }
    clickCount++;
    });
