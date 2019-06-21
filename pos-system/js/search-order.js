$(document).ready(function () {
    initialLoad();
    loadOrders();

});

function initialLoad() {
    var ajaxConfig;
    if ($("#search").val().trim().length === 0) {
        ajaxConfig = {
            method: 'GET',
            url: 'http://localhost:8080/api/v1/orders?q=a',
            async: true
        };
    } else {
        ajaxConfig = {
            method: 'GET',
            url: 'http://localhost:8080/api/v1/orders?q=' + $("#search").val().trim(),
            async: true
        };
    }
    $.ajax(ajaxConfig).done(function (orders, textStatus, jqxhr) {
        $("table tbody tr").remove();
        console.log(orders);
        orders.forEach(function (order) {
            var html = "<tr>" +
                "<td>" + order.id + "</td>" +
                "<td>" + order.customer_id + "</td>" +
                "<td>" + order.date + "</td>" +
                "<td>" + order.sum + "</td>" +
                "</tr>"
            $("#search-table").append(html);
        });
    }).fail(function (jqxhr, textStatus, errorThrown) {
        console.log("Please call DEP")
    });

}
function loadOrders() {
    $("#search").keyup(function () {
        initialLoad();
    });

}
