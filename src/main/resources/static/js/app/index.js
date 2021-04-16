var index = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        })
        $('#btn-delete').on('click', function () {
            _this.delete();
        })
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

        var id = $('#id').val()

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
        var id = $('#id').val()

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
};

index.init();