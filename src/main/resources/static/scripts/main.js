const CODE = {
    SUCCESS: 200,
    FAILURE: -1
}

class Page {

    static defaultSize = 20;

    constructor(index, size) {
        this.index = index;
        if (size) {
            this.size = size;
        } else {
            this.size = Page.defaultSize;
        }
    }

}

class OffsetPage {

    static defaultSize = 20;

    constructor(offset, size) {
        this.index = Math.floor(offset / size);
        if (size) {
            this.size = size;
        } else {
            this.size = Page.defaultSize;
        }
    }

}

class Assert {
    static isTrue(expression, callback) {
        if (!expression) {
            callback();
        }
    }
}
