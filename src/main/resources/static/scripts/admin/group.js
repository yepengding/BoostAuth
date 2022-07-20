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
        if (d.code === CODE.SUCCESS) {
            $('#message-box').removeClass().addClass("alert alert-success alert-dismissible fade show");
            $('#message-content').text(`Created successfully.`);
            const data = d.data;
            $('#table').bootstrapTable('append', [{
                id: data.id,
                name: data.name,
                description: data.description
            }])
        } else {
            $('#message-box').removeClass().addClass("alert alert-danger alert-dismissible fade show");
            $('#message-content').text(d.message);
            console.error(d.message);
        }
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
                        })
                    });

                    this.$table.bootstrapTable('append', data);
                }
            });
    }

    #renderTable() {
        const operateFormatter = (value, row, index) => [
            // '<button type="button" id="disable" class="mx-2 btn btn-outline-warning">Disable</button>',
            '<button type="button" id="delete" class="mx-2 btn btn-outline-danger">' +
            '<i class="bi bi-trash-fill"></i>' +
            '</button>',
        ].join('');

        window.operateEvents = {
            'click #disable': function (e, value, row, index) {
                $('#disable').prop('disabled', true);
                $('#delete').prop('disabled', true);
                API.disableGroup(row.id)
                    .done((d) => {
                        if (d.code === CODE.SUCCESS) {
                            $('#message-box').removeClass().addClass("alert alert-success alert-dismissible fade show");
                            $('#message-content').text(`Disabled (${row.id}) successfully.`);
                        } else {
                            $('#message-box').removeClass().addClass("alert alert-danger alert-dismissible fade show");
                            $('#message-content').text(`Failed to disable (${row.id}).`);
                            console.error(d.message);
                            $('#edit').prop('disabled', false);
                            $('#delete').prop('disabled', false);
                        }
                    });
            },
            'click #delete': function (e, value, row, index) {
                $('#edit').prop('disabled', true);
                $('#delete').prop('disabled', true);
                API.deleteGroup(row.id)
                    .done((d) => {
                        if (d.code === CODE.SUCCESS) {
                            $('#message-box').removeClass().addClass("alert alert-success alert-dismissible fade show");
                            $('#message-content').text(`Deleted (${row.id}) successfully.`);
                            $('#table').bootstrapTable('remove', {
                                field: 'id',
                                values: [row.id]
                            })
                        } else {
                            $('#message-box').removeClass().addClass("alert alert-danger alert-dismissible fade show");
                            $('#message-content').text(`Failed to delete (${row.id}).`);
                            console.error(d.message);
                            $('#edit').prop('disabled', false);
                            $('#delete').prop('disabled', false);
                        }
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