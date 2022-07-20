const CODE = {
    SUCCESS: 200,
    FAILURE: -1
}

class Page {

    static defaultSize = 20;

    constructor(index, size) {
        this.pageIndex = index;
        if (size) {
            this.pageSize = size;
        } else {
            this.pageSize = Page.defaultSize;
        }
    }

}

class OffsetPage {

    static defaultSize = 20;

    constructor(offset, size) {
        this.pageIndex = Math.floor(offset / size);
        if (size) {
            this.pageSize = size;
        } else {
            this.pageSize = Page.defaultSize;
        }
    }

}

