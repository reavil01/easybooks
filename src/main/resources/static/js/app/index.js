function errorMessageAlert(error) {
    const errorMsg = JSON.stringify(error);
    const errorMsgRegx = /[가-힣| ]+\./g;
    const msg = errorMsgRegx.exec(errorMsg);

    if(errorMsg.indexOf(msg)) {
        alert(msg);
        return false;
    }
    return true;
}

function calcPrice() {
    const unitPrice = $('#unitPrice').val();
    const quantity = $('#quantity').val();
    const price = unitPrice * quantity;
    $('#price').val(price);

    calcVATandTotal();
}


function calcVATandTotal() {
    const price = parseInt($('#price').val());
    const VAT = parseInt(price * 0.1);
    $('#VAT').val(VAT);
    $('#total').val(price + VAT);
}

var index = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-ledger-save').on('click', function () {
            _this.saveLedger();
        });
        $('#btn-ledger-update').on('click', function () {
            _this.updateLedger();
        });
        $('#btn-ledger-delete').on('click', function () {
           _this.deleteLedger();
        });

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
    },
    save : function () {
        var data = {
            number: $('#number').val(),
            name: $('#name').val(),
            owner: $('#owner').val(),
            address: $('#address').val(),
            type: $('#type').val(),
            items: $('#items').val(),
            email: $('#email').val(),
            phone: $('#phone').val(),
            fax: $('#fax').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/company',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('거래처가 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data = {
            number: $('#number').val(),
            name: $('#name').val(),
            owner: $('#owner').val(),
            address: $('#address').val(),
            type: $('#type').val(),
            items: $('#items').val(),
            email: $('#email').val(),
            phone: $('#phone').val(),
            fax: $('#fax').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/company/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('거래처가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/company/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('거래처가 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    saveLedger : function () {
        var data = {
            companyNumber: $('#companyNumber').val(),
            type: $('input[name="type"]:checked').val(),
            date: $('#date').val(),
            item: $('#item').val(),
            unitPrice: $('#unitPrice').val(),
            quantity: $('#quantity').val(),
            price: $('#price').val(),
            VAT: $('#VAT').val(),
            total: $('#total').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/ledger',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        }).done(function () {
            alert('송장이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            if(errorMessageAlert(error)) {
                alert(JSON.stringify(error))
            }
        });
    },
    updateLedger : function () {
        const id = $('#id').val();
        const data = {
            companyNumber: $('#companyNumber').val(),
            type: $('input[name="type"]:checked').val(),
            date: $('#date').val(),
            item: $('#item').val(),
            unitPrice: $('#unitPrice').val(),
            quantity: $('#quantity').val(),
            price: $('#price').val(),
            VAT: $('#VAT').val(),
            total: $('#total').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/ledger/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('송장이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            if(errorMessageAlert(error)) {
                alert(JSON.stringify(error))
            }
        });
    },
    deleteLedger : function () {
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/ledger/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('송장이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


};

index.init();