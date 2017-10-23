

/* 사이드 바 */

$(document).ready(function () {
    // All sides
    var sides = ["left", "top", "right", "bottom"];

    // Initialize sidebars
    for (var i = 0; i < sides.length; ++i) {
        var cSide = sides[i];
        $(".sidebar." + cSide).sidebar({side: cSide});
    }

    // Click handlers
    $(".btn[data-action]").on("click", function () {
        var $this = $(this);
        var action = $this.attr("data-action");
        var side = $this.attr("data-side");
        $(".sidebar." + side).trigger("sidebar:" + action);
        return false;
    });
});



/* 메뉴 bg */

$(function(){
    $(".blackbg").hide();
    $(".btn-Mobile").click(function(){
        $("body").addClass('layerfix');    
        $(".blackbg").fadeIn(500);
    });
    $(".btn-Close").click(function(){
        $("body").removeClass('layerfix');
        $(".blackbg").fadeOut(500);
    });
});



$(document).ready(function () {
    
    
    $(".header_inner .gnb>ul>li.user").mouseover(function(){
        $(".gnb .gnb_2depth").show();
        
    });
    $(".header_inner .gnb>ul>li.user").mouseleave(function(){
        $(".gnb .gnb_2depth").hide();

    });
    $(".gnb .gnb_2depth").mouseover(function(){
        $(this).show();
    });
    $(".gnb .gnb_2depth").mouseleave(function(){
        $(this).hide();
    });
    
    
    $(".sidebars > .sidebar .side_Menu li:last-of-type a").click(function(){
        $(".sidebars > .sidebar .side_Menu li:last-of-type ul.mo_hidden_menu").slideToggle();
    });
    
});


$(document).ready(function(){
    var bw=document.documentElement.clientWidth;

    if(bw > 1200){
        $(document).ready(function(){
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                slidesPerView: 4,
                spaceBetween: 20 

            });
        });
    }else if(bw > 769 && bw <= 1200){
        $(document).ready(function(){
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                slidesPerView: 3,
                spaceBetween: 20 

            });
        });
    }else if(bw > 414 && bw <= 769){
        $(document).ready(function(){
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                slidesPerView: 2,
                spaceBetween: 20 

            });
        });
    }else if(bw <= 769){
        $(document).ready(function(){
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                nextButton: '.swiper-button-next',
                prevButton: '.swiper-button-prev',
                slidesPerView: 1,
                spaceBetween: 0, 
                grabCursor: true
            });
        });
    }
    
    $(".card_dp_wrapper .slide_button li:first-child a").click(function(){
        $(".card_dp_wrapper .swiper-button-prev").trigger("click");
    });
    
    $(".card_dp_wrapper .slide_button li:last-child a").click(function(){
        $(".card_dp_wrapper .swiper-button-next").trigger("click");
    });
});