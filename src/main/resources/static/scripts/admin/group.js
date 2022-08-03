const API = {
    getAllGroup: () => $.getJSON("/admin/group/all"),
    createGroup: (group) => $.ajax("/admin/group/create", {
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(group),
    }),
    enableGroup: (id) => $.post(`/admin/group/enable/${id}`),
    disableGroup: (id) => $.post(`/admin/group/disable/${id}`),
    deleteGroup: (id) => $.post(`/admin/group/delete/${id}`),
}

const GROUP_STATUS = {
    ENABLED: 1,
    DISABLED: 0,
    DELETED: -1
}

class GroupCreate {

    constructor(name, description) {
        this.name = name;
        this.description = description;
    }
}

$("#create-btn").click(() => {
    API.createGroup(new GroupCreate(
        $('#nameInput').val(),
        $('#descriptionInput').val(),
    )).done(d => {
        Assert.isTrue(d.code === CODE.SUCCESS, () => Message.failure(d.message));
        Message.success("Created successfully.");
        const data = d.data;
        $('#table').bootstrapTable('append', [{
            id: data.id,
            name: data.name,
            description: data.description
        }])
    }).fail(e => {
        Message.failure(e.responseJSON.message);
        console.error(e.responseJSON.message);
    })
})

class Group {

    constructor() {
        this.$table = $('#table');
    }

    run() {
        this.#renderTable();
        this.#setAllGroup();
    }

    #setAllGroup() {
        API.getAllGroup()
            .done((d) => {
                if (d.code === CODE.SUCCESS) {
                    const data = [];
                    d.data.forEach((e) => {
                        data.push({
                            id: e.id,
                            name: e.name,
                            description: e.description,
                            status: e.status
                        })
                    });

                    this.$table.bootstrapTable('append', data);
                }
            });
    }

    #renderTable() {
        const operateFormatter = (value, row, index) => {
            const enableBtn =
                `<button type="button" id="enable" class="mx-2 btn btn-outline-primary" ${row.status === GROUP_STATUS.DISABLED ? '' : 'disabled'}>` +
                '<i class="bi bi-power"></i>' +
                '</button>';

            const disableBtn =
                `<button type="button" id="disable" class="mx-2 btn btn-outline-secondary" ${row.status === GROUP_STATUS.ENABLED ? '' : 'disabled'}>` +
                '<i class="bi bi-slash-circle"></i>' +
                '</button>';

            const deleteBtn =
                '<button type="button" id="delete" class="mx-2 btn btn-outline-danger">' +
                '<i class="bi bi-trash-fill"></i>' +
                '</button>'
            return [enableBtn, disableBtn, deleteBtn].join('');
        }

        window.operateEvents = {
            'click #enable': function (e, value, row, index) {
                $('#enable').prop('disabled', true);
                $('#disable').prop('disabled', true);
                $('#delete').prop('disabled', true);
                const setSuccessView = () => {
                    $('#disable').prop('disabled', false);
                    $('#delete').prop('disabled', false);
                    Message.success(`Disabled (${row.id}) successfully.`);
                };
                const setFailureView = () => {
                    Message.failure(`Failed to disable (${row.id}).`);
                    $('#enable').prop('disabled', false);
                    $('#disable').prop('disabled', false);
                    $('#delete').prop('disabled', false);
                };

                API.enableGroup(row.id)
                    .done((d) => {
                        Assert.isTrue(d.code === CODE.SUCCESS, setFailureView);
                        setSuccessView();
                        $('#table').bootstrapTable('updateCell', {
                            index: index,
                            field: 'status',
                            value: GROUP_STATUS.ENABLED
                        })
                    })
                    .fail(e => {
                        setFailureView();
                        console.error(e.responseJSON.message);
                    });
            },
            'click #disable': function (e, value, row, index) {
                $('#enable').prop('disabled', true);
                $('#disable').prop('disabled', true);
                $('#delete').prop('disabled', true);
                const setSuccessView = () => {
                    $('#enable').prop('disabled', false);
                    $('#delete').prop('disabled', false);
                    Message.success(`Disabled (${row.id}) successfully.`);
                };
                const setFailureView = () => {
                    Message.failure(`Failed to disable (${row.id}).`);
                    $('#enable').prop('disabled', false);
                    $('#disable').prop('disabled', false);
                    $('#delete').prop('disabled', false);
                };

                API.disableGroup(row.id)
                    .done((d) => {
                        Assert.isTrue(d.code === CODE.SUCCESS, setFailureView);
                        setSuccessView();
                        $('#table').bootstrapTable('updateCell', {
                            index: index,
                            field: 'status',
                            value: GROUP_STATUS.DISABLED
                        })
                    })
                    .fail(e => {
                        setFailureView();
                        console.error(e.responseJSON.message);
                    });
            },
            'click #delete': function (e, value, row, index) {
                $('#enable').prop('disabled', true);
                $('#disable').prop('disabled', true);
                $('#delete').prop('disabled', true);
                const setSuccessView = () => {
                    Message.success(`Deleted (${row.id}) successfully.`);
                };
                const setFailureView = () => {
                    Message.failure(`Failed to delete (${row.id}).`);
                    $('#enable').prop('disabled', false);
                    $('#disable').prop('disabled', false);
                    $('#delete').prop('disabled', false);
                }

                API.deleteGroup(row.id)
                    .done((d) => {
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
            }
        };

        this.$table.bootstrapTable({
            toolbar: "#toolbar",
            search: true,
            pagination: true,
            pageSize: 20,
            columns: [{
                field: 'id',
                title: 'ID',
                sortable: true
            }, {
                field: 'name',
                title: 'Group Name'
            }, {
                field: 'description',
                title: 'Description'
            }, {
                field: 'status',
                title: 'Status'
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
    const group = new Group()
    group.run();
})()