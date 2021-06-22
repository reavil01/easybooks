var report = {
    init: function () {
        var _this = this;

        $('#btn-report-search').click( function () {
            _this.getReport();
        });
    },

    getReport: function () {
        var year = $("select[name=year]").val();
        var month = $("select[name=month]").val();
        var url = "/report/?"

        if (year) url = url + "year=" + year;
        if (month) {
            if (year) url = url + "&";
            url = url + "month=" + month;
        }
        location.href = url;
    },
}

report.init();