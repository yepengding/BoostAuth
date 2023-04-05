/**
 * Preregistration admin page API
 */
const API = {
    getAllPreregistration: (pageable) => $.getJSON("/admin/identity/all/preregister", pageable),
    approveRegistration: (id) => $.post(`/admin/identity/approve/${id}`),
    rejectRegistration: (id) => $.post(`/admin/identity/reject/${id}`),
}

/**
 * Preregistration admin page script
 */
class PreregistrationAdminPage {

    constructor() {
        this.$table = $('#table');
    }

    render() {
        this.#renderTable();
    }

    #setPreregistrationData(params) {
        API.getAllPreregistration(new Page(params.data.offset))
            .done((d) => {
                Assert.isTrue(d.code === CODE.SUCCESS, () => console.log("Failed to fetch all preregistration."));
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
                params.success(tableData);
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
                const setSuccessView = () => {
                    Message.success(`Approved (${row.id}) successfully.`)
                };
                const setFailureView = () => {
                    Message.failure(`Failed to approve (${row.id}).`);
                    $('#approve').prop('disabled', false);
                    $('#reject').prop('disabled', false);
                }
                API.approveRegistration(row.id)
                    .done(d => {
                        Assert.isTrue(d.code === CODE.SUCCESS, setFailureView);
                        setSuccessView();
                        $('#table').bootstrapTable('remove', {
                            field: 'id',
                            values: [row.id]
                        });
                    })
                    .fail(e => {
                        setFailureView();
                        console.error(e.responseJSON.message);
                    });
            },
            'click #reject': function (e, value, row, index) {
                $('#approve').prop('disabled', true);
                $('#reject').prop('disabled', true);
                const setSuccessView = () => {
                    Message.success(`Rejected (${row.id}) successfully.`);
                    $('#table').bootstrapTable('remove', {
                        field: 'id',
                        values: [row.id]
                    });
                };
                const setFailureView = () => {
                    Message.failure(`Failed to reject (${row.id}).`);
                    $('#approve').prop('disabled', false);
                    $('#reject').prop('disabled', false);
                };

                API.rejectRegistration(row.id)
                    .done((d) => {
                        Assert.isTrue(d.code === CODE.SUCCESS, setFailureView);
                        setSuccessView();
                    }).fail(e => {
                    setFailureView();
                    console.error(e.responseJSON.message);
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
    const preregistrationAdminPage = new PreregistrationAdminPage()
    preregistrationAdminPage.render();
})()