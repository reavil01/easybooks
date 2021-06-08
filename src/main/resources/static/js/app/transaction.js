var transaction = {
    init: function () {
        var _this = this;

        $("#btn-transaction-save").click(function () {
            _this.saveTransaction();
        });

        $("#btn-transaction-update").click(function () {
            _this.updateTransaction();
        });

        $("#btn-transaction-delete").click(function () {
            _this.deleteTransaction();
        });

        $('#btn-search-transaction-by-date').click(function () {
            _this.searchTransactionByDate();
        });

        $('#btn-search-transaction-by-company-number').click(function () {
            _this.searchTransactionByCompanyNumber();
        });

        $('#btn-search-transaction-by-company-name').click(function () {
            _this.searchTransactionByCompanyName();
        });
    },

    saveTransaction: function () {
        var data = {
            companyNumber: $('#companyNumber').text(),
            type: $('input[name="type"]:checked').val(),
            date: $('#date').val(),
            price: $('#price').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/transaction',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        }).done(function () {
            alert('입/출금 내역이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            if (errorMessageAlert(error)) {
                alert(JSON.stringify(error))
            }
        });
    },

    updateTransaction: function () {
        const id = $('#id').val();
        const data = {
            companyNumber: $('#companyNumber').text(),
            type: $('input[name="type"]:checked').val(),
            date: $('#date').val(),
            price: $('#price').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/transaction/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('입/출금 내역이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            // if(errorMessageAlert(error)) {
            alert(JSON.stringify(error))
            // }
        });
    },

    deleteTransaction: function () {
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/transaction/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('입/출금 내역이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    searchTransactionByCompanyName: function () {
        const name = $('#search-transaction-by-company-name').val();
        location.href = "/transaction/search/companyName=" + name;
    },

    searchTransactionByCompanyNumber: function () {
        const number = $('#search-transaction-by-company-number').val();
        location.href = "/transaction/search/companyNumber=" + number;
    },

    searchTransactionByDate: function () {
        const start = $('#search-transaction-by-date-start').val();
        const end = $('#search-transaction-by-date-end').val();
        location.href = "/transaction/search/startDate=" + start + "&endDate=" + end;
    },
}

transaction.init();