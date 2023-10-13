/**
 * Preregistration API
 */
const API = {
    preregister: (preregistration) => $.ajax(`/token/auth/preregister`, {
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(preregistration),
    })
}

/**
 * Preregistration page models
 */
class Preregistration {

    constructor(username, source, password, groupId) {
        this.username = username;
        this.source = source;
        this.password = password;
        this.groupId = groupId;
    }
}

/**
 * Preregistration page events
 */
$("#show-pwd-btn").click(() => {
    const showPwdIcon = $('#show-pwd-icon');
    if (showPwdIcon.hasClass('bi-eye-fill')) {
        showPwdIcon.removeClass('bi-eye-fill').addClass('bi-eye-slash-fill');
    } else {
        showPwdIcon.removeClass('bi-eye-slash-fill').addClass('bi-eye-fill');
    }

    const passwordInput = $('#password-input');

    if (passwordInput.attr('type') === 'password') {
        passwordInput.attr('type', 'text')
    } else {
        passwordInput.attr('type', 'password')
    }
})

$("#preregister-btn").click(() => {
    $('#preregister-btn').prop('disabled', true);
    const setSuccessView = () => {
        Message.success(`Preregistered successfully.`);
    }
    const setFailureView = (message) => {
        Message.failure(message);
        $('#preregister-btn').prop('disabled', false);
    }

    API.preregister(new Preregistration(
        $('#username-input').val(),
        SYSTEM.ID,
        $('#password-input').val(),
        $('#group-id-input').val(),
    )).done(d => {
        Assert.isTrue(d.code === CODE.SUCCESS, setFailureView);
        setSuccessView();
    }).fail(e => {
        setFailureView(e.responseJSON.message);
    })
})