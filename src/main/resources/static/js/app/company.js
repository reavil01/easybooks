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
            _this.searchCompanyByName();
        });

        $('#btn-company-number-search').click(function () {
            _this.searchCompanyByNumber();
        });
    },

    companySave: function () {
        const json = readFormToJson();

        $.ajax({
            type: 'POST',
            url: '/api/v1/company',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: json
        }).done(function (saved) {
            alert('거래처가 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    companyUpdate: function () {
        const json = readFormToJson();
        const id = $('#id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/company/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: json
        }).done(function () {
            alert('거래처가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    companyDelete: function () {
        const id = $('#id').val();

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

    searchCompanyByName: function () {
        const name = $('#search-by-company-name').val();
        location.href = '/company/search?name=' + name;
    },

    searchCompanyByNumber: function () {
        const number = $('#search-by-company-number').val();
        location.href = '/company/search?number=' + number;
    },

    openCompanySearchPopup: function () {
        window.name = "parentForm";
        openWin = window.open("/company/search?popup=true",
            "childForm",
            "width=800, height=600, location=no, toolbars=no, status=no")
    },

    sendSelectCompanyData: function (row) {
        // $(this): 현재 클릭된 Row(<tr>)
        const companyNumber = $(row).children(".company-number").text()
        const companyName = $(row).children(".company-name").text()
        const companyOwner = $(row).children(".company-owner").text()
        opener.$('#companyNumber').val(companyNumber)
        opener.$('#companyName').val(companyName)
        opener.$('#companyOwner').val(companyOwner)
        window.close();
    },

}

company.init();