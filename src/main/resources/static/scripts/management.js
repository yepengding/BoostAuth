const API = {
    getAllPreregistration: () => $.getJSON("/admin/identity/all/preregister"),
    approveRegistration: (id) => $.post(`/admin/identity/approve/${id}`),
    rejectRegistration: (id) => $.post(`/admin/identity/reject/${id}`),
}

class Management {

    constructor() {
        this.$table = $('#table');
    }

    run() {
        this.#renderTable();
        this.#setWaitingData();
    }

    #setWaitingData() {
        API.getAllPreregistration()
            .done((d) => {
                if (d.code === CODE.SUCCESS) {
                    const data = [];
                    d.data.forEach((e) => {
                        data.push({
                            id: e.id,
                            username: e.username,
                            source: e.source
                        })
                    });

                    this.$table.bootstrapTable('append', data);
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
    const management = new Management()
    management.run();
})()