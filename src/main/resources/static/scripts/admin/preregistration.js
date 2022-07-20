const API = {
    getAllPreregistration: (pageable) => $.getJSON("/admin/identity/all/preregister", pageable),
    approveRegistration: (id) => $.post(`/admin/identity/approve/${id}`),
    rejectRegistration: (id) => $.post(`/admin/identity/reject/${id}`),
}

class Preregistration {

    constructor() {
        this.$table = $('#table');
        console.log()
    }

    run() {
        this.#renderTable();
    }

    #setPreregistrationData(params) {
        API.getAllPreregistration(new Page(params.data.offset))
            .done((d) => {
                if (d.code === CODE.SUCCESS) {
                    const data = d.data;
                    const tableData = {
                        rows: [],
                        total: data.totalElements,
                        totalNotFiltered: data.totalElements
                    }
                    data.content.forEach((e) => {
                        tableData.rows.push({
                            id: e.id,
                            username: e.username,
                            source: e.source,
                            groupId: e.groupId
                        })
                    });
                    params.success(tableData)
                }
            });
    }

    #renderTable() {
        const operateFormatter = (value, row, index) => [
            '<button type="button" id="approve" class="mx-2 btn btn-outline-success">Approve</button>',
            '<button type="button" id="reject" class="mx-2 btn btn-outline-danger">Reject</button>',
        ].join('');

        window.operateEvents = {
            'click #approve': function (e, value, row, index) {
                $('#approve').prop('disabled', true);
                $('#reject').prop('disabled', true);
                API.approveRegistration(row.id)
                    .done(d => {
                        if (d.code === CODE.SUCCESS) {
                            $('#message-box').removeClass().addClass("alert alert-success alert-dismissible fade show");
                            $('#message-content').text(`Approved (${row.id}) successfully.`);
                            $('#table').bootstrapTable('remove', {
                                field: 'id',
                                values: [row.id]
                            })
                        } else {
                            $('#message-box').removeClass().addClass("alert alert-danger alert-dismissible fade show");
                            $('#message-content').text(`Failed to approve (${row.id}).`);
                            console.error(d.message);
                            $('#approve').prop('disabled', false);
                            $('#reject').prop('disabled', false);
                        }
                    });
            },
            'click #reject': function (e, value, row, index) {
                $('#approve').prop('disabled', true);
                $('#reject').prop('disabled', true);
                API.rejectRegistration(row.id)
                    .done((d) => {
                        if (d.code === CODE.SUCCESS) {
                            $('#message-box').removeClass().addClass("alert alert-success alert-dismissible fade show");
                            $('#message-content').text(`Rejected (${row.id}) successfully.`);
                            $('#table').bootstrapTable('remove', {
                                field: 'id',
                                values: [row.id]
                            })
                        } else {
                            $('#message-box').removeClass().addClass("alert alert-danger alert-dismissible fade show");
                            $('#message-content').text(`Failed to reject (${row.id}).`);
                            console.error(d.message);
                            $('#approve').prop('disabled', false);
                            $('#reject').prop('disabled', false);
                        }
                    });
            }
        };

        this.$table.bootstrapTable({
            pagination: true,
            pageSize: 20,
            search: true,
            sidePagination: "server",
            ajax: this.#setPreregistrationData,
            columns: [{
                field: 'id',
                title: 'ID',
                sortable: true
            }, {
                field: 'username',
                title: 'Username'
            }, {
                field: 'source',
                title: 'Source'
            }, {
                field: 'groupId',
                title: 'groupId'
            }, {
                field: 'operate',
                title: 'Operation',
                align: 'center',
                clickToSelect: false,
                events: window.operateEvents,
                formatter: operateFormatter
            }],
            data: []
        })
    }

}

(function () {
    const preregistration = new Preregistration()
    preregistration.run();
})()