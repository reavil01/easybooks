var company = {
    init: function () {
        var _this = this;
        $('#btn-company-search').click(function () {
            _this.openCompanySearchPopup();
        });

        $("#company-select-table tbody tr").dblclick(function () {
            _this.sendSelectCompanyData(this);
        });

        $('#btn-save').click(function () {
            _this.companySave();
        });

        $('#btn-update').click(function () {
            _this.companyUpdate();
        });

        $('#btn-delete').click(function () {
            _this.companyDelete();
        });

        $('#btn-company-name-search').click(function () {
            _this.searchCompanyByNameWithUnpaid();
        });

        $('#btn-company-number-search').click(function () {
            _this.searchCompanyByNumberWithUnpaid();
        });
    },

    companySave : function () {
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

    companyUpdate : function () {
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
            url: '/api/v1/company/' + id,
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

    companyDelete : function() {
            var id = $('#id').val();

            $.ajax({
                type: 'DELETE',
                url: '/api/v1/company/' + id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
            }).done(function () {
                alert('거래처가 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
    },

    searchCompanyByNameWithUnpaid : function () {
        const name = $('#search-by-company-name').val();
        location.href = '/company/search/unpaid&name=' + name;
    },

    searchCompanyByNumberWithUnpaid : function () {
        const number = $('#search-by-company-number').val();
        location.href = '/company/search/unpaid&number=' + number;
    },

    openCompanySearchPopup : function () {
        window.name = "parentForm";
        openWin = window.open("/company/search/pop=true",
            "childForm",
            "width=800, height=600, location=no, toolbars=no, status=no")
    },

    sendSelectCompanyData : function (row) {
        // $(this): 현재 클릭된 Row(<tr>)
        const companyNumber = $(row).children(".company-number").text()
        const companyName = $(row).children(".company-name").text()
        const companyOwner = $(row).children(".company-owner").text()
        opener.$('#companyNumber').text(companyNumber)
        opener.$('#companyName').text(companyName)
        opener.$('#companyOwner').text(companyOwner)
        window.close();
    }
}

company.init();