/**
 * Message for the global environment
 */
class Message {
    static {
        this.boxDom = $('#message-box');
        this.contentDom = $('#message-content');
    }

    static success(message) {
        this.boxDom.removeClass().addClass("alert alert-success alert-dismissible fade show");
        this.contentDom.text(message);
    }

    static failure(message) {
        this.boxDom.removeClass().addClass("alert alert-danger alert-dismissible fade show");
        this.contentDom.text(message);
    }
}