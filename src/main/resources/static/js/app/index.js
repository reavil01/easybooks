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

        $('#btn-company-name-search').click(function () {
           _this.searchCompanyByNameWithUnpaid();
        });
        $('#btn-company-number-search').click(function () {
            _this.searchCompanyByNumberWithUnpaid();
        });
        $('#btn-search-ledger-by-company-number').click(function () {
            _this.searchLedgerByCompanyNumber();
        });
        $('#btn-search-ledger-by-company-name').click(function () {
            _this.searchLedgerByCompanyName();
        });
        $('#btn-search-ledger-by-date').click(function () {
            _this.searchLedgerByDate();
        });
        $('#btn-company-search').click(function () {
            window.name = "parentForm";
            openWin = window.open("/company/search&pop=true",
                "childForm",
                "width=800, height=600, location=no, toolbars=no, status=no")
        });
        $("tbody tr").mouseover(function () {
            $(this).css('background-color' , '#f0f0f0');
        });
        $("tbody tr").mouseout(function () {
            $(this).css('background-color' , '#FFFFFF');
        });
        $("#company-select-table tbody tr").dblclick(function () {
            // $(this): 현재 클릭된 Row(<tr>)
            const companyNumber = $(this).children(".company-number").text()
            const companyName = $(this).children(".company-name").text()
            const companyOwner = $(this).children(".company-owner").text()
            opener.$('#companyNumber').text(companyNumber)
            opener.$('#companyName').text(companyName)
            opener.$('#companyOwner').text(companyOwner)
            window.close();
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
            companyNumber: $('#companyNumber').text(),
            type: $('input[name="type"]:checked').val(),
            date: $('#date').val(),
            item: $('#item').val(),
            unitPrice: $('#unitPrice').val(),
            quantity: $('#quantity').val(),
            price: $('#price').val(),
            vat: $('#vat').val(),
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
            // if(errorMessageAlert(error)) {
            alert(JSON.stringify(error))
            // }
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
            vat: $('#vat').val(),
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
            // if(errorMessageAlert(error)) {
            alert(JSON.stringify(error))
            // }
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
    searchCompanyByNameWithUnpaid: function () {
        const name = $('#search-by-company-name').val();
        location.href = '/company/search/unpaid&name=' + name;
    },
    searchCompanyByNumberWithUnpaid: function () {
        const number = $('#search-by-company-number').val();
        location.href = '/company/search/unpaid&number=' + number;
    },
    searchLedgerByCompanyName: function () {
        const name = $('#search-ledger-by-company-name').val();
        location.href = "/ledger/search&companyName=" + name;
    },
    searchLedgerByCompanyNumber: function () {
        const number = $('#search-ledger-by-company-number').val();
        location.href = "/ledger/search&companyNumber=" + number;
    },
    searchLedgerByDate: function () {
        const start = $('#search-ledger-by-date-start').val();
        const end = $('#search-ledger-by-date-end').val();
        location.href = "/ledger/search&startDate=" + start + "&endDate=" + end;
    }

};

index.init();