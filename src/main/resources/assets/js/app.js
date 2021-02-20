/*
 Template Name: Agroxa - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesbrand
 Website: www.themesbrand.com
 File: Main js
 */


!function($) {
    "use strict";

    //Мой кусок
    $(document).ready(function () {

        /** изменямемая форма использует библиотеку https://vitalets.github.io/x-editable/
         * изменение статуса ордера в LatestOrder не допилино
         **/
        // $.fn.editableform.buttons =
        //     '<button type="submit" class="btn btn-success editable-submit btn-sm waves-effect waves-light"><i class="mdi mdi-check"></i></button>' +
        //     '<button type="button" class="btn btn-danger editable-cancel btn-sm waves-effect waves-light"><i class="mdi mdi-close"></i></button>';
        //
        // var selectOrders = $('#select-orders option:eq(2)').text();
        //
        // $('.inline-status').editable({
        //     prepend: selectOrders,
        //     mode: 'inline',
        //     inputclass: 'form-control-sm',
        //     source: [
        //         {value: 1, text: selectOrders[0]},
        //         {value: 1, text: ''},
        //         {value: 1, text: ''},
        //         {value: 2, text: 'Female'}
        //     ],
        //     display: function (value, sourceData) {
        //         var colors = {"": "#98a6ad", 1: "#5fbeaa", 2: "#5d9cec"},
        //             elem = $.grep(sourceData, function (o) {
        //                 return o.value == value;
        //             });
        //
        //         if (elem.length) {
        //             $(this).text(elem[0].text).css("color", colors[value]);
        //         } else {
        //             $(this).empty();
        //         }
        //     }
        // });


        // $("button[data-toggle='modal']").on("click");

        // $(document).on('click', '.order-count', function () {
        //
        //
        // });



        $(document).on('click',"button[data-toggle='modal']", function () {
            var orderId = $(this).attr("id");
            $('.'+orderId).modal();
        });

        //обновление таблицы при закрытии модалки ордера
        $('#edit-order-modal').on('hidden.bs.modal', function (e) {
            location.reload();
        });
        //изменение количество товара в оредере
        $(document).on('click', '.order-count', function () {
            // var productCount = $('[name="productCount"]').val();
            // var productId  = $(this).attr('product-id');
            // var orderId  = $(this).attr('order-id');
            // alert(productCount);
            // alert(productId);
            // alert(orderId);
            var productId = $(this).attr('product-id');

            var editOrderItem = {
                "productCount":$('.'+productId).val(),
                "productId": $(this).attr('product-id'),
                "orderId": $(this).attr('order-id')
            };

            $.ajax({
                type: 'POST',
                url: '/ajax/admin/edit-order-item',
                contentType: 'application/json',
                data:   JSON.stringify(editOrderItem),
                success: function (data) {
                    $('#edit-order-modal').html(data);
                    $('#edit-order-modal .bd-example-modal-lg').modal('show');
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
            });

        });

        //проверка новых ордеров
        var interval = 20000;  // 1000 = 1 second, 3000 = 3 seconds
        function doAjax() {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: '/ajax/admin/orderStatus',
                success: function (data) {
                    $('#orderCount').removeClass('hidden');
                    $('#orderCount').text(data.count);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
                complete: function (data) {
                    // Schedule the next
                    setTimeout(doAjax, interval);
                }
            });
        }
        setTimeout(doAjax, interval);

        //здесь будет изменение ордера и вызов модалки edit order
        $(document).on('click', '#ordersTable .edit-order', function () {
            var orderId = $(this).attr("order-id");
            $.ajax({
                type: 'GET',
                url: '/ajax/admin/edit-order?id='+orderId,
                success: function (data) {
                    $('#edit-order-modal').html(data);
                    $('#edit-order-modal .bd-example-modal-lg').modal('show');
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
            });

        });

        $(document).on('click', '.delete-orderItem', function () {
            var orderId = $(this).attr("order-id");
            var orderItemId = $(this).attr("product-id");
            alert("helo"+orderItemId+" "+orderId);
            $.ajax({
                type: 'GET',
                url: '/ajax/admin/orders/delete-item?orderItemId=' + orderItemId + "&orderId=" + orderId,
                success: function (data) {
                    $('#edit-order-modal').html(data);
                    $('#edit-order-modal .bd-example-modal-lg').modal('show');
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
            });

        });

        // //Submit the order changes to a server
        var editClientsOrder = function (orderForm) {

            $.ajax({
                url : '/ajax/admin/edit-order',
                method : 'POST',
                contentType: 'application/json',
                data:   JSON.stringify(orderForm),
                dataType: "html",
                success : function(data) {
                    $('#js-load>.spinner-grow').addClass('d-none');
                    $('#edit-order-modal').html(data);
                    $('#edit-order-modal .bd-example-modal-lg').modal('show');
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error new clients order');
                        alert(JSON.stringify(orderForm));
                    }
                }
            });

        };


        $(document).on('click', '#js-load', function () {

            $('#js-load>.spinner-grow').removeClass('d-none');

            var orderForm = {
                "id": $('[name="id"]').val(),
                "clientFirstName": $('[name="clientFirstName"]').val(),
                "clientLastName": $('[name="clientLastName"]').val(),
                "clientPhone": $('[name="clientPhone"]').val(),
                "clientEmail": $('[name="clientEmail"]').val(),
                "clientStreetAddress": $('[name="clientStreetAddress"]').val(),
                "clientStreetTown": $('[name="clientStreetTown"]').val(),
                "orderStatus": $('[name="orderStatus"]').val()
            };
            editClientsOrder(orderForm);

            // editClientsOrder(orderForm);
        });


        //загрузка ордеров в dashboard
        $(document).on('click', '.page-link', function () {
            var selectOrders  = $('#select-orders').val();
            var pageNumber = $(this).attr("page-number");
            $.ajax({
                type: 'GET',
                url: '/ajax/admin?page=' + pageNumber + '&select=' + selectOrders,
                success: function (data) {

                    $('#ordersTable').html(data);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
            });

        });
        //фильтр ордеров в dashboard
        $(document).on('change', '#select-orders', function () {
            var selectedOrders = $(this).val();

            $.ajax({
                type: 'GET',
                url: '/ajax/admin?page=1'  + '&select=' + selectedOrders,
                success: function (data) {

                    $('#ordersTable').html(data);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                },
            });
        });

        //Поиск товара в админке
        $('#searchItem').on('keyup', function(){
            var $result = $('#itemsTable');
            var search = $(this).val();
            if ((search !== '') && (search.length > 2)){
                $.ajax({
                    type: "GET",
                    url: "/ajax/products/search",
                    data: {'name':search},
                    success: function(msg){
                        $result.html(msg);
                        if(msg !== ''){
                                $result.fadeIn();
                        }
                        else {
                            $result.fadeOut(100);
                        }
                    },
                    error : function(xhr) {
                        if (xhr.status == 400) {
                            alert(xhr.responseJSON.message);
                        } else {
                            alert('Error');
                        }
                    }

                });
            }
            else {
                // $result.html('');
                $result.fadeOut(100);
            }
        });

        //Поиск ордеров
        $('#searchOrder').on('keyup', function(){
            var $result = $('#ordersTable');
            var search = $(this).val();
            if ((search !== '') && (search.length > 2)){
                $.ajax({
                    type: "POST",
                    url: "/ajax/json/search-orders",
                    data: JSON.stringify({
                        searchName: search
                    }),
                    contentType: 'application/json',
                    success: function(msg){
                        $result.html(msg);
                        if(msg !== ''){
                            $result.fadeIn();
                        }
                        else {
                            $result.fadeOut(100);
                        }
                    },
                    error : function(xhr) {
                        if (xhr.status == 400) {
                            alert(xhr.responseJSON.message);
                        } else {
                            alert('Error');
                        }
                    }

                });
            }
            else {
                // $result.html('');
                $result.fadeOut(100);
            }
        });


    });

    var MainApp = function() {};

    MainApp.prototype.intSlimscrollmenu = function () {
        $('.slimscroll-menu').slimscroll({
            height: 'auto',
            position: 'right',
            size: "7px",
            color: '#9ea5ab',
            wheelStep: 5,
            touchScrollStep: 50
        });
    },
    MainApp.prototype.initSlimscroll = function () {
        $('.slimscroll').slimscroll({
            height: 'auto',
            position: 'right',
            size: "5px",
            color: '#9ea5ab',
            touchScrollStep: 50
        });
    },

    MainApp.prototype.initMetisMenu = function () {
        //metis menu
        $("#side-menu").metisMenu();
    },

    MainApp.prototype.initLeftMenuCollapse = function () {
        // Left menu collapse
        $('.button-menu-mobile').on('click', function (event) {
            event.preventDefault();
            $("body").toggleClass("enlarged");
        });
    },

    MainApp.prototype.initEnlarge = function () {
        if ($(window).width() < 1025) {
            $('body').addClass('enlarged');
        } else {
            if ($('body').data('keep-enlarged') != true)
                $('body').removeClass('enlarged');
        }
    },

    MainApp.prototype.initActiveMenu = function () {
        // === following js will activate the menu in left side bar based on url ====
        $("#sidebar-menu a").each(function () {
            var pageUrl = window.location.href.split(/[?#]/)[0];
            if (this.href == pageUrl) {
                $(this).addClass("active");
                $(this).parent().addClass("active"); // add active to li of the current link
                $(this).parent().parent().addClass("in");
                $(this).parent().parent().prev().addClass("active"); // add active class to an anchor
                $(this).parent().parent().parent().addClass("active");
                $(this).parent().parent().parent().parent().addClass("in"); // add active to li of the current link
                $(this).parent().parent().parent().parent().parent().addClass("active");
            }
        });
    },

    MainApp.prototype.initComponents = function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('[data-toggle="popover"]').popover();
    },

    MainApp.prototype.initHeaderCharts = function () {
        $('#header-chart-1').sparkline([8, 6, 4, 7, 10, 12, 7, 4, 9, 12, 13, 11, 12], {
            type: 'bar',
            height: '35',
            barWidth: '5',
            barSpacing: '3',
            barColor: '#35a989'
        });
        $('#header-chart-2').sparkline([8, 6, 4, 7, 10, 12, 7, 4, 9, 12, 13, 11, 12], {
            type: 'bar',
            height: '35',
            barWidth: '5',
            barSpacing: '3',
            barColor: '#ffe082'
        });
    },

    MainApp.prototype.init = function () {
        this.intSlimscrollmenu();
        this.initSlimscroll();
        this.initMetisMenu();
        this.initLeftMenuCollapse();
        this.initEnlarge();
        this.initActiveMenu();
        this.initComponents();
        this.initHeaderCharts();
        Waves.init();
    },

    //init
    $.MainApp = new MainApp, $.MainApp.Constructor = MainApp
}(window.jQuery),

//initializing
function ($) {
    "use strict";
    $.MainApp.init();
}(window.jQuery);
