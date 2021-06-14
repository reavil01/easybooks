var ledger = {
    init: function () {
        var _this = this;

        $('#btn-ledger-save').on('click', function () {
            _this.saveLedger();
        });

        $('#btn-ledger-update').on('click', function () {
            _this.updateLedger();
        });

        $('#btn-ledger-delete').on('click', function () {
            _this.deleteLedger();
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
    },

    saveLedger: function () {
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

    updateLedger: function () {
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
            url: '/api/v1/ledger/' + id,
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

    deleteLedger: function () {
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/ledger/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('송장이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    searchLedgerByCompanyName: function () {
        const name = $('#search-ledger-by-company-name').val();
        location.href = "/ledger/search?companyName=" + name;
    },

    searchLedgerByCompanyNumber: function () {
        const number = $('#search-ledger-by-company-number').val();
        location.href = "/ledger/search?companyNumber=" + number;
    },

    searchLedgerByDate: function () {
        const start = $('#search-ledger-by-date-start').val();
        const end = $('#search-ledger-by-date-end').val();
        location.href = "/ledger/search?startDate=" + start + "&endDate=" + end;
    },
}

ledger.init();