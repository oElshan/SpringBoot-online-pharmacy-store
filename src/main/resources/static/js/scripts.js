(function($) {
    "use strict";


    $(document).ready(function () {

        //----------------------------филтр по цене и по производителю----------------------------
        $(document).on('click', '.filter-button button', function () {
            var priceValues = $( "#price-filter" ).val();
            var checkedProducer= [];


            $("input:checkbox:checked").each(function(){
                var producer  = $(this).val();
                checkedProducer.push(producer)
            });

            var arrayOfStrings = priceValues.split(',');
            var data = {
                'min': arrayOfStrings[0],
                'max': arrayOfStrings[1]
            };
            var filterProduct = {
                "id":$('#idCategory').val(),
                "search":$('#search').val(),
                "price": arrayOfStrings,
                "producers": checkedProducer
            };
            // alert(JSON.stringify(filterProduct));filterProduct

            $.ajax({
                url : window.location.pathname ,
                method : 'POST',
                cache: false,
                contentType: 'application/json',
                data:   JSON.stringify(filterProduct),
                success : function(checkoutOrder) {
                    $('#product-content').html(checkoutOrder);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert(JSON.stringify(ilterProduct));
                    }
                }
            });





            // if (checkedProducer.length==0) {
            //     window.location.href = window.location.pathname + '?search='+$('#search').val()+ '&price=' + arrayOfStrings[0] + ',' + arrayOfStrings[1];
            // } else {
            //     window.location.href = window.location.pathname  +'?search='+$('#search').val()+ '&price=' + arrayOfStrings[0] + ',' + arrayOfStrings[1]+'&producers='+checkedProducer.toString();
            // }
        });


        //------------------------AJAX для заказа---------------------------------
        var newClientsOrder = function (orderForm) {
            $.ajax({
                url : '/ajax/orders/new',
                method : 'POST',
                cache: false,
                contentType: 'application/json',
                data:   JSON.stringify(orderForm),
                success : function(checkoutOrder) {
                    $('#checkout-page').html(checkoutOrder);
                    basketStatus();
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

        // форма заказа слиента
        $(document).on('click', '#btnOrderForm', function () {
            var orderForm = {
                firstName: $('[name="firstName"]').val(),
                lastName: $('[name="lastName"]').val(),
                streetAddress: $('[name="streetAddress"]').val(),
                town: $('[name="town"]').val(),
                zipCode: $('[name="zipCode"]').val(),
                email: $('[name="email"]').val(),
                phone: $('[name="phone"]').val()
            };
            newClientsOrder(orderForm);
        });

        //----------------------------Поиск производителя----------------------------
        $('#searchProducer').on('keyup', function(){
            var $result = $('#producer-list');
            var search = $(this).val();
            if ((search !== '') && (search.length > 2)){
                $.ajax({
                    type: "GET",
                    url: '/ajax/producers/search',
                    data: {name:search},
                    success: function(msg){
                        $result.html(msg);
                        $result.addClass("scroll-filter");
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

        //----------------------------Товары по спец категориям----------------------------
        $(document).on('click', '#products-category a', function () {
            var idCatalog = $(this).attr('id-speccategory');
            showProductListBySpecCatalog(idCatalog);
        });

        var showProductListBySpecCatalog = function (idCatalog) {

            $.ajax({
                url : '/ajax/products?idCatalog='+idCatalog,
                method : 'GET',
                cache: false,
                success : function(productList) {

                    $('#products').html(productList);
                    echo.init({
                        offset: 100,
                        throttle: 250,
                        unload: false
                    });
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                }
            });

        };



        //------------------------------------------------------------------------------------
        var deleteItemfromShoppingCart = function () {
            var idProduct = $(this).attr('data-product_id');
            $.ajax({
                url : '/ajax/shopping-cart?idProduct='+idProduct,
                method : 'DELETE',
                cache: false,
                success : function(shoppingCart) {
                    $('.shoppingCart').html(shoppingCart);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                }
            });

        };

        //
        var deleteItemfromViewShoppingCart = function (idProduct) {

            $.ajax({
                url : '/ajax/shopping-cart?idProduct='+idProduct,
                method : 'DELETE',
                cache: false,
                success : function(shoppingCart) {
                    $('.shoppingCart').html(shoppingCart);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error');
                    }
                }
            });

        };

        $(document).on('click', '.basket-item .close-btn', deleteItemfromShoppingCart);

        $(document).on('click', '.plus', function () {
            var countItems = $('countItems').attr('value');
            alert(countItems);

        });

        $(document).on('click', '#cart-page .close-btn',function () {
            var idProduct = $(this).attr('data-product_id');
            deleteItemfromViewShoppingCart(idProduct);
            $('#cart-page').load('/ajax/shopping-cart',function(){
            });

            // $.ajax({
            //     url : '/ajax/deleteItemFromShoppingCart',
            //     method : 'GET',
            //     cache: false,
            //     success : function(viewCart) {
            //         $('#cart-page').html(viewCart);
            //     },
            //     error : function(xhr) {
            //         if (xhr.status == 400) {
            //             alert(xhr.responseJSON.message);
            //         } else {
            //             alert('Error');
            //         }
            //     }
            // });


        });
        // $(document).on('click', '.cart-item .close-btn',deleteItemfromShoppingCart);



        //----------------------------добавление товара в корзину----------------------------
        var addProductToCart = function() {
            var idProduct = $(this).attr('data-product_id');
            var url = '/ajax/shopping-cart/add?idProduct=' + idProduct;
            // $('#shoppingCart').load(url);
            $.ajax({
                url : url,
                method : 'GET',
                cache: false,
                success : function(shoppingCart) {
                    $('.shoppingCart').html(shoppingCart);
                    $('#add-'+idProduct).text("added!");
                    setTimeout(function () {
                        $('#add-'+idProduct).text("add to cart");
                    },2000);

                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error status shopping cart');
                    }
                }
            });

        };


        $(document).on('click', '.product-item .le-button', addProductToCart);



        //-----------------------------------------------обновление статуса корзины------------------------------------
        var basketStatus = function () {
            $.ajax({
                url : '/ajax/json/shopping-cart',
                method : 'GET',
                cache: false,
                success : function(shoppingCart) {
                    $('#currentShoppingCart .count').text(shoppingCart.totalCount);
                    $('#currentShoppingCart .value').text(shoppingCart.totalCost);
                },
                error : function(xhr) {
                    if (xhr.status == 400) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert('Error status shopping cart');
                    }
                }
            });

        };
        // basketStatus();

    });
    // --------------------------------------------------EnD USER SCRIPT-------------------------------------

    /*===================================================================================*/
    /*  WOW
    /*===================================================================================*/

    $(document).ready(function () {
        new WOW().init();
    });

    /*===================================================================================*/
    /*  OWL CAROUSEL
    /*===================================================================================*/

    $(document).ready(function () {

        var dragging = true;
        var owlElementID = "#owl-main";

        function fadeInReset() {
            if (!dragging) {
                $(owlElementID + " .caption .fadeIn-1, " + owlElementID + " .caption .fadeIn-2, " + owlElementID + " .caption .fadeIn-3").stop().delay(800).animate({ opacity: 0 }, { duration: 400, easing: "easeInCubic" });
            }
            else {
                $(owlElementID + " .caption .fadeIn-1, " + owlElementID + " .caption .fadeIn-2, " + owlElementID + " .caption .fadeIn-3").css({ opacity: 0 });
            }
        }

        function fadeInDownReset() {
            if (!dragging) {
                $(owlElementID + " .caption .fadeInDown-1, " + owlElementID + " .caption .fadeInDown-2, " + owlElementID + " .caption .fadeInDown-3").stop().delay(800).animate({ opacity: 0, top: "-15px" }, { duration: 400, easing: "easeInCubic" });
            }
            else {
                $(owlElementID + " .caption .fadeInDown-1, " + owlElementID + " .caption .fadeInDown-2, " + owlElementID + " .caption .fadeInDown-3").css({ opacity: 0, top: "-15px" });
            }
        }

        function fadeInUpReset() {
            if (!dragging) {
                $(owlElementID + " .caption .fadeInUp-1, " + owlElementID + " .caption .fadeInUp-2, " + owlElementID + " .caption .fadeInUp-3").stop().delay(800).animate({ opacity: 0, top: "15px" }, { duration: 400, easing: "easeInCubic" });
            }
            else {
                $(owlElementID + " .caption .fadeInUp-1, " + owlElementID + " .caption .fadeInUp-2, " + owlElementID + " .caption .fadeInUp-3").css({ opacity: 0, top: "15px" });
            }
        }

        function fadeInLeftReset() {
            if (!dragging) {
                $(owlElementID + " .caption .fadeInLeft-1, " + owlElementID + " .caption .fadeInLeft-2, " + owlElementID + " .caption .fadeInLeft-3").stop().delay(800).animate({ opacity: 0, left: "15px" }, { duration: 400, easing: "easeInCubic" });
            }
            else {
                $(owlElementID + " .caption .fadeInLeft-1, " + owlElementID + " .caption .fadeInLeft-2, " + owlElementID + " .caption .fadeInLeft-3").css({ opacity: 0, left: "15px" });
            }
        }

        function fadeInRightReset() {
            if (!dragging) {
                $(owlElementID + " .caption .fadeInRight-1, " + owlElementID + " .caption .fadeInRight-2, " + owlElementID + " .caption .fadeInRight-3").stop().delay(800).animate({ opacity: 0, left: "-15px" }, { duration: 400, easing: "easeInCubic" });
            }
            else {
                $(owlElementID + " .caption .fadeInRight-1, " + owlElementID + " .caption .fadeInRight-2, " + owlElementID + " .caption .fadeInRight-3").css({ opacity: 0, left: "-15px" });
            }
        }

        function fadeIn() {
            $(owlElementID + " .active .caption .fadeIn-1").stop().delay(500).animate({ opacity: 1 }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeIn-2").stop().delay(700).animate({ opacity: 1 }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeIn-3").stop().delay(1000).animate({ opacity: 1 }, { duration: 800, easing: "easeOutCubic" });
        }

        function fadeInDown() {
            $(owlElementID + " .active .caption .fadeInDown-1").stop().delay(500).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInDown-2").stop().delay(700).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInDown-3").stop().delay(1000).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
        }

        function fadeInUp() {
            $(owlElementID + " .active .caption .fadeInUp-1").stop().delay(500).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInUp-2").stop().delay(700).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInUp-3").stop().delay(1000).animate({ opacity: 1, top: "0" }, { duration: 800, easing: "easeOutCubic" });
        }

        function fadeInLeft() {
            $(owlElementID + " .active .caption .fadeInLeft-1").stop().delay(500).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInLeft-2").stop().delay(700).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInLeft-3").stop().delay(1000).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
        }

        function fadeInRight() {
            $(owlElementID + " .active .caption .fadeInRight-1").stop().delay(500).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInRight-2").stop().delay(700).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
            $(owlElementID + " .active .caption .fadeInRight-3").stop().delay(1000).animate({ opacity: 1, left: "0" }, { duration: 800, easing: "easeOutCubic" });
        }

        $(owlElementID).owlCarousel({

            autoPlay: 5000,
            stopOnHover: true,
            navigation: true,
            pagination: true,
            singleItem: true,
            items: 1,
            addClassActive: true,
            //transitionStyle: "fade",
            navigationText: ["<i class='fa fa-chevron-left'></i>", "<i class='fa fa-chevron-right'></i>"],



            afterInit: function() {
                fadeIn();
                fadeInDown();
                fadeInUp();
                fadeInLeft();
                fadeInRight();
            },

            afterMove: function() {
                fadeIn();
                fadeInDown();
                fadeInUp();
                fadeInLeft();
                fadeInRight();
            },

            afterUpdate: function() {
                fadeIn();
                fadeInDown();
                fadeInUp();
                fadeInLeft();
                fadeInRight();
            },

            startDragging: function() {
                dragging = true;
            },

            afterAction: function() {
                fadeInReset();
                fadeInDownReset();
                fadeInUpReset();
                fadeInLeftReset();
                fadeInRightReset();
                dragging = false;
            }

        });

        if ($(owlElementID).hasClass("owl-one-item")) {
            $(owlElementID + ".owl-one-item").data('owlCarousel').destroy();
        }

        $(owlElementID + ".owl-one-item").owlCarousel({
            items: 1,
            singleItem: true,
            navigation: false,
            pagination: false
        });

        $('#transitionType li a').click(function () {

            $('#transitionType li a').removeClass('active');
            $(this).addClass('active');

            var newValue = $(this).attr('data-transition-type');

            $(owlElementID).data("owlCarousel").transitionTypes(newValue);
            $(owlElementID).trigger("owl.next");

            return false;

        });

        $("#owl-recently-viewed").owlCarousel({
            stopOnHover: true,
            rewindNav: true,
            items: 6,
            pagination: false,
            itemsTablet: [768,3]
        });

        $("#owl-recently-viewed-2").owlCarousel({
            stopOnHover: true,
            rewindNav: true,
            items: 4,
            pagination: false,
            itemsTablet: [768,3],
            itemsDesktopSmall: [1199,3],
        });

        $("#owl-brands").owlCarousel({
            stopOnHover: true,
            rewindNav: true,
            items: 6,
            pagination: false,
            itemsTablet : [768, 4]
        });

        $('#owl-single-product').owlCarousel({
            items: 1,
            singleItem: true,
            pagination: false
        });

        $('#owl-single-product-thumbnails').owlCarousel({
            items: 6,
            pagination: false,
            rewindNav: true,
            itemsTablet : [768, 4]
        });

        $('#owl-recommended-products').owlCarousel({
            rewindNav: true,
            items: 4,
            pagination: false,
            itemsTablet: [768, 3],
            itemsDesktopSmall: [1199,3],
        });

        $('#best-seller-single-product-slider').owlCarousel({
            items: 1,
            singleItem: true,
            pagination: false
        });

        $(".slider-next").click(function () {
            var owl = $($(this).data('target'));
            owl.trigger( 'next.owl.carousel' );
            return false;
        });

        $(".slider-prev").click(function () {
            var owl = $($(this).data('target'));
            owl.trigger( 'prev.owl.carousel' );
            return false;
        });

        $('.single-product-gallery .horizontal-thumb').click(function(){
            var $this = $(this), owl = $($this.data('target')), slideTo = $this.data('slide');
            owl.trigger('to.owl.carousel', slideTo);
            $this.addClass('active').parent().siblings().find('.active').removeClass('active');
            return false;
        });

    });

    /*===================================================================================*/
    /*  STAR RATING
    /*===================================================================================*/

    $(document).ready(function () {

        if ($('.star').length > 0) {
            $('.star').each(function(){
                    var $star = $(this);

                    if($star.hasClass('big')){
                        $star.raty({
                            starOff: '/static/images/star-big-off.png',
                            starOn: '/static/images/star-big-on.png',
                            space: false,
                            score: function() {
                                return $(this).attr('data-score');
                            }
                        });
                    }else{
                     $star.raty({
                        starOff: '/static/images/star-off.png',
                        starOn: '/static/images/star-on.png',
                        space: false,
                        score: function() {
                            return $(this).attr('data-score');
                        }
                    });
                }
            });
        }
    });

    /*===================================================================================*/
    /*  SHARE THIS BUTTONS
    /*===================================================================================*/

    $(document).ready(function () {
        if($('.social-row').length > 0){
            stLight.options({publisher: "2512508a-5f0b-47c2-b42d-bde4413cb7d8", doNotHash: false, doNotCopy: false, hashAddressBar: false});
        }
    });

    /*===================================================================================*/
    /*  CUSTOM CONTROLS
    /*===================================================================================*/

    $(document).ready(function () {

        // Select Dropdown
        if($('.le-select').length > 0){
            $('.le-select select').customSelect({customClass:'le-select-in'});
        }

        // Checkbox
        if($('.le-checkbox').length>0){
            $('.le-checkbox').after('<i class="fake-box"></i>');
        }

        //Radio Button
        if($('.le-radio').length>0){
            $('.le-radio').after('<i class="fake-box"></i>');
        }

        // Buttons
        $('.le-button.disabled').click(function(e){
            e.preventDefault();
        });

        // Quantity Spinner
        $('.le-quantity a').click(function(e){
            e.preventDefault();
            var currentQty= $(this).parent().parent().find('input').val();
            if( $(this).hasClass('minus') && currentQty>0){
                $(this).parent().parent().find('input').val(parseInt(currentQty, 10) - 1);
            }else{
                if( $(this).hasClass('plus')){
                    $(this).parent().parent().find('input').val(parseInt(currentQty, 10) + 1);
                }
            }
        });

        // Price Slider
        var min =parseFloat( $('#price-filter').attr('data-min'));
        var max = parseFloat($('#price-filter').attr('data-max'));

        if ($('.price-slider').length > 0) {
            $('.price-slider').slider({
                min: min,
                max: max,
                step: 1,
                value: [min,max],
                handle: "square"
            });
        }


        $(document).ready(function(){
            $('select.styled').customSelect();
        });

        // Data Placeholder for custom controls

        $('[data-placeholder]').focus(function() {
            var input = $(this);
            if (input.val() == input.attr('data-placeholder')) {
                input.val('');

            }
        }).blur(function() {
            var input = $(this);
            if (input.val() === '' || input.val() == input.attr('data-placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('data-placeholder'));
            }
        }).blur();

        $('[data-placeholder]').parents('form').submit(function() {
            $(this).find('[data-placeholder]').each(function() {
                var input = $(this);
                if (input.val() == input.attr('data-placeholder')) {
                    input.val('');
                }
            });
        });

    });

    /*===================================================================================*/
    /*  LIGHTBOX ACTIVATOR
    /*===================================================================================*/
    $(document).ready(function(){
        if ($('a[data-rel="prettyphoto"]').length > 0) {
            //$('a[data-rel="prettyphoto"]').prettyPhoto();
        }
    });


    /*===================================================================================*/
    /*  SELECT TOP DROP MENU
    /*===================================================================================*/
    $(document).ready(function() {
        $('.top-drop-menu').change(function() {
            var loc = ($(this).find('option:selected').val());
            window.location = loc;
        });
    });

    /*===================================================================================*/
    /*  LAZY LOAD IMAGES USING ECHO
    /*===================================================================================*/
    $(document).ready(function(){
        echo.init({
            offset: 100,
            throttle: 250,
            unload: false
        });
    });

    /*===================================================================================*/
    /*  GMAP ACTIVATOR
    /*===================================================================================*/

    $(document).ready(function(){
        var zoom = 16;
        var latitude = 51.539075;
        var longitude = -0.152424;
        var mapIsNotActive = true;
        setupCustomMap();

        function setupCustomMap() {
            if ($('.map-holder').length > 0 && mapIsNotActive) {

                var styles = [
                    {
                        "featureType": "landscape",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "visibility": "simplified"
                            },
                            {
                                "color": "#E6E6E6"
                            }
                        ]
                    }, {
                        "featureType": "administrative",
                        "stylers": [
                            {
                                "visibility": "simplified"
                            }
                        ]
                    }, {
                        "featureType": "road",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "visibility": "on"
                            },
                            {
                                "saturation": -100
                            }
                        ]
                    }, {
                        "featureType": "road.highway",
                        "elementType": "geometry.fill",
                        "stylers": [
                            {
                                "color": "#808080"
                            },
                            {
                                "visibility": "on"
                            }
                        ]
                    }, {
                        "featureType": "water",
                        "stylers": [
                            {
                                "color": "#CECECE"
                            },
                            {
                                "visibility": "on"
                            }
                        ]
                    }, {
                        "featureType": "poi",
                        "stylers": [
                            {
                                "visibility": "on"
                            }
                        ]
                    }, {
                        "featureType": "poi",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#E5E5E5"
                            },
                            {
                                "visibility": "on"
                            }
                        ]
                    }, {
                        "featureType": "road.local",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#ffffff"
                            },
                            {
                                "visibility": "on"
                            }
                        ]
                    }, {}
                ];

                var lt, ld;
                if ($('.map').hasClass('center')) {
                    lt = (latitude);
                    ld = (longitude);
                } else {
                    lt = (latitude + 0.0027);
                    ld = (longitude - 0.010);
                }

                var options = {
                    mapTypeControlOptions: {
                        mapTypeIds: ['Styled']
                    },
                    center: new google.maps.LatLng(lt, ld),
                    zoom: zoom,
                    disableDefaultUI: true,
                    scrollwheel: false,
                    mapTypeId: 'Styled'
                };
                var div = document.getElementById('map');

                var map = new google.maps.Map(div, options);

                var styledMapType = new google.maps.StyledMapType(styles, {
                    name: 'Styled'
                });

                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(latitude, longitude),
                    map: map
                });

                map.mapTypes.set('Styled', styledMapType);

                mapIsNotActive = false;
            }

        }
    });


        /*===================================================================================*/
        /*  Yamm Dropdown
        /*===================================================================================*/
        $(document).on('click', '.yamm .dropdown-menu', function(e) {
          e.stopPropagation()
        });

})(jQuery);
