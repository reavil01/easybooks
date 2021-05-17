function calcPrice() {
    const unitPrice = $('#unitPrice').val();
    const quantity = $('#quantity').val();
    const price = unitPrice * quantity;
    $('#price').val(price);

    calcVATandTotal();
}

function calcVATandTotal() {
    const price = parseInt($('#price').val());
    const vat = parseInt(price * 0.1);
    $('#vat').val(vat);
    $('#total').val(price + vat);
}

var index = {
    init : function () {
        var _this = this;
        $('#unitPrice').focusout(function () { calcPrice(); });

        $('#quantity').focusout(function () { calcPrice(); });

        $('#price').focusout(function () {
            const price = $(this).val();
            const calcedPrice = $('#unitPrice').val() * $('#quantity').val();
            if(calcedPrice != price) {
                $('#unitPrice').val(null);
                $('#quantity').val(null);
            }
            calcVATandTotal();
        });



        $("tbody tr").mouseover(function () {
            $(this).css('background-color' , '#f0f0f0');
        });
        $("tbody tr").mouseout(function () {
            $(this).css('background-color' , '#FFFFFF');
        });
    },
};

index.init();