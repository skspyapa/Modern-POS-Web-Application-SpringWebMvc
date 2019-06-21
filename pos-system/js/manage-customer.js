$(document).ready(function () {
    $("#name").focus();
    idGenerator();

    loadCustomers();
    validateFunction();

    $(document).on("click", ".delete-cust", function () {
        var custId = $($(this).parent().children()[0]).text();
        var selectedRow = $(this);
        var response = confirm("Do you Want To Delete");
        if (response) {
        var ajaxConfig = {
            method: 'DELETE',
            url: 'http://localhost:8080/api/v1/customers/' + custId,
            async: true,
        };
        $.ajax(ajaxConfig).done(function (data,textStatus, jqxhr){


            if (jqxhr.status===204) {
                selectedRow.parent().remove();
                newCustomer();
                alert("Successfully Deleted The Customer");
            }

        }).fail(function (jqxhr, textStatus, errorThrown) {
            newCustomer();
            if(jqxhr.status==500){
                alert("This Customer Already Has Orders");
            }
            if(jqxhr.status===404){
                alert("No customer Who has Id "+custId);
            }
        });
    }else{
            alert("OK");
        }
    });
});

var table;
function validateFunction() {
    $("#name").on("keyup",function () {
        var custName = $(this).val().trim();
        var checker = /^[A-Za-z\s]*$/;
        if (custName.match(checker) && (custName.length > 0)) {
            // $("#address").focus();
            $("#name").css('border-color', '#ced4da');
        }else if (custName.length>0) {
            $(this).focus();
            $(this).select();
            $(this).css('border-color', 'red');
        }else if(custName.length==0){
            $(this).css('border-color', '#ced4da');
        }
    });
    $("#address").on("keyup",function () {
        var custAddress = $(this).val().trim();
        var checker = /^[A-Za-z\s]*$/;
        if (custAddress.match(checker) && (custAddress.length > 0)) {
            // $("#btn-save").focus();
            $("#address").css('border-color', '#ced4da');
        }else if (custAddress.length>0) {
            $(this).focus();
            $(this).select();
            $(this).css('border-color', 'red');
        }else if(custAddress.length==0){
            $(this).css('border-color', '#ced4da');
        }
    });
}


function CheckCustAddress(e) {
    var custAddress = $("#address").val().trim();
    var checker = /^[A-Za-z0-9\s]*$/;

        if (custAddress.match(checker) && (custAddress.length > 0)) {
            $("#btn-save").focus();
            $("#address").css('border-color', '#ced4da');
        } else if (custAddress.length > 0) {
            $("#address").focus(function () {
                $(this).select();
                $("#address").css('border-color', 'red');
            });
        }

    if (custAddress.length == 0) {
        $("#address").css('border-color', '#ced4da');
    }
}

function idGenerator() {
    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/customers?maxCustomerId=true',
        async:true
    };
    var numOfCustomers;
    $.ajax(ajaxConfig).done(function (customer,textStatus,jqxhr) {
        var custom=customer;

        if(custom==="null"){
            custom="C000";
        }
        if (jqxhr.status === 200) {
        var idsubs = parseInt(custom.substr(1, 3));

        if (idsubs < 9) {
            $("#id").val("C00" + ++idsubs);
        } else if (idsubs < 99) {
            $("#id").val("C0" + ++idsubs);
        } else if (idsubs > 999) {
            $("#id").val("C" + ++idsubs);
        }
    }
    }).fail(function (jqxhr,textStatus,errorThrown) {

    });

}

function newCustomer() {
    idGenerator();
    $("#name").val("");
    $("#address").val("");
}

function saveCustomer() {
    var customerObject={id:$("#id").val(),name:$("#name").val(),address:$("#address").val()};
    var cusData = JSON.stringify(customerObject);

    var ajaxConfig={
        method:'POST',
        url:'http://localhost:8080/api/v1/customers',
        async:true,
        data:cusData,
        contentType:"application/json"
    };
    $.ajax(ajaxConfig).done(function (data,textStatus,jqxhr) {
        if (data != null) {
            loadCustomers();
            // table.destroy();
            // tabel=$('#tbl-customer').DataTable({
            //     searching: false,
            //     pageLength: 10
            // });

        html3 = '<tr>' +
            '<td>' + $("#id").val() + '</td>' +
            '<td>' + $("#name").val() + '</td>' +
            '<td>' + $("#address").val() + '</td>' +
            '<td class="delete-cust">' + '<button class="btn"></button>' + '</td>'
            + '</tr>'
        $("#tbl-customer").append(html3);

            $("tbody tr:last-child").click(function (e) {
                console.log($("tbody tr").index(this));
                $("#id").val($($(this).children()[0]).text());
                $("#name").val($($(this).children()[1]).text());
                $("#address").val($($(this).children()[2]).text());

            });
            newCustomer();
            alert("Successfully Saved The Customer");
    }else {
            alert("Customer Not Saved");
        }

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);
    });

}
$("#NewCustomer").on('click', function () {
    newCustomer();
});
$("#btn-save").click(function () {
    var update = false;
    if ($("#name").val().trim().length > 0 && $("#address").val().trim().length > 0) {
        $("tbody tr td:first-child").each(function (index, obj) {

            if ($(this).text() === $("#id").val()) {
                rowUpdate($(this).parent());
                update = true;
            }
        });
    if (!update) {
        saveCustomer();
    }

}else {
        alert("Please Enter All Fields Correctly");
        newCustomer();
    }
});

function loadCustomers() {


    var ajaxConfig={
        method:'GET',
        url:'http://localhost:8080/api/v1/customers',
        async:true
    };
    $.ajax(ajaxConfig).done(function (customers,textStatus,jqxhr) {
        // $('#tbl-customer').DataTable({
        //     searching: false,
        //     pageLength: 10
        // });

        $('#tbl-customer').DataTable().destroy(false);
        $("table tbody tr").remove();

        customers.forEach(function (customer) {
            var html = "<tr>" +
                "<td>" + customer.id + "</td>" +
                "<td>" + customer.name + "</td>" +
                "<td>" + customer.address + "</td>" +
                '<td class="delete-cust">' + '<button class="btn"></button>' + '</td>'+
                // "<td>" + customer.salary + "</td>" +
                "</tr>"
            $("#tbl-customer").append(html);
            $("tbody tr:last-child").click(function (e) {
                console.log($("tbody tr").index(this));
                $("#id").val($($(this).children()[0]).text());
                $("#name").val($($(this).children()[1]).text());
                $("#address").val($($(this).children()[2]).text());

            });
        });


            $('#tbl-customer').DataTable({
                searching: false,
                pageLength: 5
            });

    }).fail(function (jqxhr,textStatus,errorThrown) {
        console.log("Please call DEP");
        alert(jqxhr.responseText);
    });
}

function rowUpdate(row) {
    row.find("td:nth-child(2)").text($("#name").val());
    row.find("td:nth-child(3)").text($("#address").val());

    var customerObject={id:$("#id").val(),name:$("#name").val(),address:$("#address").val()}
    var cusData = JSON.stringify(customerObject);

    var ajaxConfig={
        method:'PUT',
        url:'http://localhost:8080/api/v1/customers/'+$("#id").val(),
        async:true,
        data: cusData,
        contentType:"application/json",
    };
    $.ajax(ajaxConfig).done(function (response,textStatus,jqxhr) {
        if(jqxhr.status===204){
            alert("Successfully Updated The Customer");
            newCustomer();
        }else{
            alert("Your Data Not Correctly Added");
        }
    }).fail(function (jqxhr,textStatus,errorThrown) {
        if(jqxhr.status===500){
            alert("Your Data Not Correctly Added");
        }
    });

}
